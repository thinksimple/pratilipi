package com.pratilipi.data.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.PageType;
import com.pratilipi.common.util.GoogleAnalyticsApi;
import com.pratilipi.data.BlobAccessor;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DocAccessor;
import com.pratilipi.data.type.BlobEntry;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.PratilipiGoogleAnalyticsDoc;


public class PratilipiDocUtil {
	
	private static final Logger logger =
			Logger.getLogger( PratilipiDocUtil.class.getName() );
	
	
	public static List<Long> updatePratilipiGoogleAnalyticsPageViews( int year, int month, int day )
			throws UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
		
		Gson gson = new Gson();
		
		String dateStr = year
				+ ( month < 10 ? "-0" + month : "-" + month )
				+ ( day < 10 ? "-0" + day : "-" + day );
		
		String fileName = "pratilipi-google-analytics/page-views/" + dateStr;
		BlobEntry blobEntry = blobAccessor.getBlob( fileName );
		if( blobEntry == null ) {
			try {
				blobEntry = blobAccessor.newBlob( fileName, "{}".getBytes( "UTF-8" ), "application/json" );
			} catch( UnsupportedEncodingException e ) {
				logger.log( Level.SEVERE, e.getMessage() );
				throw new UnexpectedServerException();
			}
		}
		
		
		@SuppressWarnings("serial")
		Map<String, Integer> oldPageViewsMap = gson.fromJson(
				new String( blobEntry.getData(), Charset.forName( "UTF-8" ) ),
				new TypeToken<Map<String, Integer>>(){}.getType() );
		
		Map<String, Integer> newPageViewsMap =
				GoogleAnalyticsApi.getPageViews( dateStr );
		
		Map<String, Integer> diffPageViewsMap = new HashMap<>();
		for( Entry<String, Integer> entry : newPageViewsMap.entrySet() )
			if( ! entry.getValue().equals( oldPageViewsMap.get( entry.getKey() ) ) )
				diffPageViewsMap.put( entry.getKey(), entry.getValue() );
		
		
		Map<Long, Integer> pageViewsMap = new HashMap<>();
		Map<Long, Integer> readPageViewsMap = new HashMap<>();
		
		for( Entry<String, Integer> entry : diffPageViewsMap.entrySet() ) {
			
			String uri = entry.getKey();
			
			if( ! uri.startsWith( "/read?id=" ) ) { // Summary Page
				
				if( uri.indexOf( '?' ) != -1 )
					uri = uri.substring( 0, uri.indexOf( '?' ) );
				
				Page page = dataAccessor.getPage( uri );
				if( page != null && page.getType() == PageType.PRATILIPI ) {
					Long pratilpiId = page.getPrimaryContentId();
					if( pageViewsMap.get( pratilpiId ) == null )
						pageViewsMap.put( pratilpiId, entry.getValue() );
					else
						pageViewsMap.put( pratilpiId, pageViewsMap.get( pratilpiId ) + entry.getValue() );
				}
				
			} else { // Reader
				
				String patilipiIdStr = uri.indexOf( '&' ) == -1
						? uri.substring( "/read?id=".length() )
						: uri.substring( "/read?id=".length(), uri.indexOf( '&' ) );
						
				try {
					Long pratilpiId = Long.parseLong( patilipiIdStr );
					if( readPageViewsMap.get( pratilpiId ) == null )
						readPageViewsMap.put( pratilpiId, entry.getValue() );
					else
						readPageViewsMap.put( pratilpiId, readPageViewsMap.get( pratilpiId ) + entry.getValue() );
				} catch( NumberFormatException e ) {
					logger.log( Level.SEVERE, "Exception while processing reader uri " + uri, e );
				}
				
			}
			
		}
		
		
		for( Entry<Long, Integer> entry : pageViewsMap.entrySet() ) {
			if( readPageViewsMap.get( entry.getKey() ) == null ) {
				updatePratilipiGoogleAnalyticsPageViews( entry.getKey(), year, month, day, entry.getValue(), 0 );
			} else {
				updatePratilipiGoogleAnalyticsPageViews( entry.getKey(), year, month, day, entry.getValue(), readPageViewsMap.get( entry.getKey() ) );
				readPageViewsMap.remove( entry.getKey() );
			}
		}
		
		for( Entry<Long, Integer> entry : readPageViewsMap.entrySet() )
			updatePratilipiGoogleAnalyticsPageViews( entry.getKey(), year, month, day, 0, entry.getValue() );
		
		
		if( diffPageViewsMap.size() > 0 ) {
			try {
				blobEntry.setData( gson.toJson( newPageViewsMap ).getBytes( "UTF-8" ) );
				blobAccessor.createOrUpdateBlob( blobEntry );
			} catch( UnsupportedEncodingException e ) {
				logger.log( Level.SEVERE, e.getMessage() );
				throw new UnexpectedServerException();
			}
		}

		
		ArrayList<Long> updatedPratilipiIdList = new ArrayList<>( pageViewsMap.size() + readPageViewsMap.size() );
		updatedPratilipiIdList.addAll( pageViewsMap.keySet() );
		updatedPratilipiIdList.addAll( readPageViewsMap.keySet() );
		
		return updatedPratilipiIdList;
		
	}
	
	public static void updatePratilipiGoogleAnalyticsPageViews( Long pratilipiId, int year, int month, int day, int pageViews, int readPageViews )
			throws UnexpectedServerException {
		
		DocAccessor docAccessor = DataAccessorFactory.getDocAccessor();
		
		PratilipiGoogleAnalyticsDoc gaDoc = docAccessor.getPratilipiGoogleAnalyticsDoc( pratilipiId );
		
		gaDoc.setPageViews( year, month, day, pageViews );
		gaDoc.setReadPageViews( year, month, day, readPageViews );

		docAccessor.save( pratilipiId, gaDoc );
		
	}
	
}
