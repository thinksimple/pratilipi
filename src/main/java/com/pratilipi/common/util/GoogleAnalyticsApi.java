package com.pratilipi.common.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.AnalyticsScopes;
import com.google.api.services.analytics.Analytics.Data.Ga.Get;
import com.google.api.services.analytics.model.GaData;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.util.GoogleApi;



public class GoogleAnalyticsApi {
	
	public static Map<Long, Long> getReadCount( long[] pratilipiId ) throws UnexpectedServerException {
		
		int max_limit = 80;
		Map <Long, Long> readCount = new HashMap<Long, Long>();
		int batches_count = ( pratilipiId.length % max_limit == 0 ) ? pratilipiId.length / max_limit : pratilipiId.length / max_limit + 1;
		
		for( int limit = 0; limit < batches_count ; limit ++ ) {
			// String processing for filters.
			String filter = "";
			int start_index = limit * max_limit;
			for( int temp =  0; temp < Math.min ( max_limit, pratilipiId.length - (limit * max_limit) ); temp ++ ) {
				filter = filter + "ga:eventCategory==Pratilipi:" + pratilipiId[ temp + start_index ] + ",";
			}
			filter = filter.substring( 0, filter.length() - 1 );
			filter = filter.concat( ";ga:eventAction=~^ReadTimeSec:.*" );
			
			// Get data from analytics.
			List<String> scopes = new LinkedList<>();
			scopes.add( AnalyticsScopes.ANALYTICS_READONLY );
			Analytics analytics = GoogleApi.getAnalytics( scopes );
			
			try {
				Get apiQuery = analytics.data().ga()
						.get( "ga:89762686",		// Table Id.
								"2015-01-01",		// Start Date.
								"today",			// End Date.
								"ga:uniqueEvents" )	// Metrics.
						.setDimensions( "ga:eventCategory,ga:eventAction" )
						.setFilters( filter );
				GaData gaData = apiQuery.execute();
				if( gaData.getRows() != null ) {
					for( List<String> row : gaData.getRows() ) {
						// Converting pratilipiId from string to long.
						String stringKey = row.get( 0 );
						Long key = Long.parseLong( stringKey.substring( 10, stringKey.length() ) );
						long value = Long.parseLong( row.get( 2 ));
						if( readCount.containsKey( key ) )
							readCount.put( key , Math.max( value, readCount.get( key ) ));
						else
							readCount.put( key , value );
					}
				}
			} catch( IOException e ) {
				throw new UnexpectedServerException();
			}
		}
		
		return readCount;
	}
	
	public static long getReadCount( Long pratilipiId ) throws UnexpectedServerException {
		
		List<String> scopes = new LinkedList<>();
		scopes.add( AnalyticsScopes.ANALYTICS_READONLY );
		Analytics analytics = GoogleApi.getAnalytics( scopes );

		long pratilipiReadCount = 0;
		try {
			Get apiQuery = analytics.data().ga()
					.get( "ga:89762686",		// Table Id.
							"2015-01-01",		// Start Date.
							"today",			// End Date.
							"ga:uniqueEvents" )	// Metrics.
					.setDimensions( "ga:eventCategory,ga:eventAction" )
					.setFilters( "ga:eventCategory==Pratilipi:" + pratilipiId + ";ga:eventAction=~^ReadTimeSec:.*" );

			GaData gaData = apiQuery.execute();
			if( gaData.getRows() != null ) {
				for( List<String> row : gaData.getRows() ) {
					long readCount = Long.parseLong( row.get( 2 ) );
					if( readCount > pratilipiReadCount )
						pratilipiReadCount = readCount;
				}
			}
		} catch( IOException e ) {
			
			throw new UnexpectedServerException();
		}
		
		return pratilipiReadCount;
	}
}