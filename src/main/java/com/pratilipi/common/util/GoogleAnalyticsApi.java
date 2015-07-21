package com.pratilipi.common.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.Analytics.Data.Ga.Get;
import com.google.api.services.analytics.AnalyticsScopes;
import com.google.api.services.analytics.model.GaData;
import com.pratilipi.common.exception.UnexpectedServerException;

public class GoogleAnalyticsApi {
	
	private static final Logger logger =
			Logger.getLogger( GoogleAnalyticsApi.class.getName() );

	
	private static Analytics getAnalytics() throws UnexpectedServerException {
		List<String> scopes = new LinkedList<>();
		scopes.add( AnalyticsScopes.ANALYTICS_READONLY );
		return GoogleApi.getAnalytics( scopes );
	}
	
	
	public static long getPratilipiReadCount( long pratilipiId ) throws UnexpectedServerException {
		long pratilipiReadCount = 0;
		
		try {
			Get apiQuery = getAnalytics().data().ga()
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
			logger.log( Level.SEVERE, "Failed to fetch data from Google Analytics.", e );
			throw new UnexpectedServerException();
		}
		
		return pratilipiReadCount;
	}
	
	public static Map<Long, Long> getPratilipiReadCount( List<Long> pratilipiList ) throws UnexpectedServerException {
		int idsPerRequest = 80;
		Map <Long, Long> idCountMap = new HashMap<Long, Long>();
		
		for( int i = 0; i < pratilipiList.size(); i = i + idsPerRequest ) {
			String filters = "";
			for( int j = 0; i + j < pratilipiList.size() && j < idsPerRequest; j++ )
				filters = filters + "ga:eventCategory==Pratilipi:" + pratilipiList.get( i + j ) + ",";
			filters = filters.substring( 0, filters.length() - 1 );
			filters = filters + ";ga:eventAction=~^ReadTimeSec:.*";
			
			try {
				Get apiQuery = getAnalytics().data().ga()
						.get( "ga:89762686",		// Table Id.
								"2015-01-01",		// Start Date.
								"today",			// End Date.
								"ga:uniqueEvents" )	// Metrics.
						.setDimensions( "ga:eventCategory,ga:eventAction" )
						.setFilters( filters );
				
				GaData gaData = apiQuery.execute();
				if( gaData.getRows() != null ) {
					for( List<String> row : gaData.getRows() ) {
						Long pratilipiId = Long.parseLong( row.get( 0 ).substring( 10 ) );
						long readCount = Long.parseLong( row.get( 2 ) );
						if( idCountMap.containsKey( pratilipiId ) )
							idCountMap.put( pratilipiId , Math.max( readCount, idCountMap.get( pratilipiId ) ) );
						else
							idCountMap.put( pratilipiId , readCount );
					}
				}
			} catch( IOException e ) {
				logger.log( Level.SEVERE, "Failed to fetch data from Google Analytics.", e );
				throw new UnexpectedServerException();
			}
		}
		
		return idCountMap;
	}
	
}