package com.pratilipi.api.impl.init;

import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.impl.init.shared.GetInitRequest;
import com.pratilipi.api.impl.init.shared.PostInitRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.AccessType;
import com.pratilipi.common.type.PageType;
import com.pratilipi.data.BlobAccessor;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DocAccessor;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.type.AppProperty;
import com.pratilipi.data.type.AuditLog;
import com.pratilipi.data.type.BlobEntry;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.PratilipiGoogleAnalyticsDoc;
import com.pratilipi.data.util.PratilipiDocUtil;
import com.pratilipi.filter.AccessTokenFilter;

@SuppressWarnings("serial")
@Bind( uri = "/init" )
public class InitApi extends GenericApi {
	
	private static final Logger logger =
			Logger.getLogger( InitApi.class.getName() );

	@Get
	public GenericResponse get( GetInitRequest request ) throws UnexpectedServerException {
		
/*		
		List<Long> authorIdList = DataAccessorFactory.getDataAccessor()
				.getAuthorIdList( new AuthorFilter(), null, null )
				.getDataList();

		List<Task> taskList = new ArrayList<Task>( authorIdList.size() );
		for( Long authorId : authorIdList ) {
			Task task = TaskQueueFactory.newTask()
					.setUrl( "/author/process" )
					.addParam( "authorId", authorId.toString() )
					.addParam( "processData", "true" );
			taskList.add( task );
		}
		TaskQueueFactory.getAuthorTaskQueue().addAll( taskList );
		logger.log( Level.INFO, "Added " + taskList.size() + " tasks in the queue." );
*/
		
/*		
		List<Long> pratilipiIdList = DataAccessorFactory.getDataAccessor()
				.getPratilipiIdList( new PratilipiFilter(), null, null )
				.getDataList();

		List<Task> taskList = new ArrayList<Task>( pratilipiIdList.size() );
		for( Long pratilipiId : pratilipiIdList ) {
			Task task = TaskQueueFactory.newTask()
					.setUrl( "/pratilipi/process" )
					.addParam( "pratilipiId", pratilipiId.toString() )
					.addParam( "processData", "true" );
			taskList.add( task );
		}
		TaskQueueFactory.getPratilipiTaskQueue().addAll( taskList );
		logger.log( Level.INFO, "Added " + taskList.size() + " tasks in the queue." );
*/

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		String appPropertyId = "Api.Init.UpdateStats";
		AppProperty appProperty = dataAccessor.getAppProperty( appPropertyId );
		if( appProperty == null ) {
			appProperty = dataAccessor.newAppProperty( appPropertyId );
			appProperty.setValue( new Date( 1420051500000L + TimeUnit.DAYS.toMillis( 13 ) ) ); // 14 Jan 2015, 12:15 AM IST
		}
		
		Date date = (Date) appProperty.getValue();
		
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone( TimeZone.getTimeZone( "IST" ) );
		cal.setTime( new Date( date.getTime() - TimeUnit.MINUTES.toMillis( 15 ) ) ); // Google updates reports every 10 min
		
		int year = request.getYear() != null ? request.getYear() : cal.get( Calendar.YEAR );
		int month = request.getMonth() != null ? request.getMonth() : cal.get( Calendar.MONTH ) + 1;
		int day = request.getDay() != null ? request.getDay() :  cal.get( Calendar.DAY_OF_MONTH );

		
		updatePratilipiGoogleAnalyticsPageViews( year, month, day );
		
		
		if( request.getYear() != null || request.getMonth() != null || request.getDay() != null )
			return new GenericResponse();
		
		
		appProperty.setValue( date.getTime() + TimeUnit.DAYS.toMillis( 1 ) > new Date().getTime()
				? new Date( 1420051500000L + TimeUnit.DAYS.toMillis( 13 ) ) // 14 Jan 2015, 12:15 AM IST
				: new Date( date.getTime() + TimeUnit.DAYS.toMillis( 1 ) ) );
		appProperty = dataAccessor.createOrUpdateAppProperty( appProperty );
		
		
		return new GenericResponse();
		
	}
	
	@Post
	public GenericResponse post( PostInitRequest request ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		Gson gson = new Gson();
		
		Pratilipi pratilipi = dataAccessor.getPratilipi( request.getPratilipiId() );

		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		AuditLog auditLog = dataAccessor.newAuditLogOfy();
		auditLog.setAccessId( accessToken.getId() );
		auditLog.setAccessType( AccessType.PRATILIPI_UPDATE );
		auditLog.setEventDataOld( gson.toJson( pratilipi ) );
		
		if( request.getReadCountOffset() != null )
			pratilipi.setReadCountOffset( request.getReadCountOffset() );
		if( request.getReadCount() != null )
			pratilipi.setReadCount( request.getReadCount() );
		if( request.getFbLikeShareCountOffset() != null )
			pratilipi.setFbLikeShareCountOffset( request.getFbLikeShareCountOffset() );
		if( request.getFbLikeShareCount() != null )
			pratilipi.setFbLikeShareCount( request.getFbLikeShareCount() );
		
		auditLog.setEventDataNew( gson.toJson( pratilipi ) );

		pratilipi = dataAccessor.createOrUpdatePratilipi( pratilipi, auditLog );

		
		return new GenericResponse();
		
	}
	
	
	public void updatePratilipiGoogleAnalyticsPageViews( int year, int month, int day )
			throws UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
		DocAccessor docAccessor = DataAccessorFactory.getDocAccessor();
		
		
		Gson gson = new Gson();
		
		String dateStr = year
				+ ( month < 10 ? "-0" + month : "-" + month )
				+ ( day < 10 ? "-0" + day : "-" + day );
		
		String fileName = "pratilipi-google-analytics/page-views/" + dateStr;
		BlobEntry blobEntry = blobAccessor.getBlob( fileName );
		logger.log( Level.INFO, fileName );
		
		Map<String, Integer> uriViewsMap = gson.fromJson(
				new String( blobEntry.getData(), Charset.forName( "UTF-8" ) ),
				new TypeToken<Map<String, Integer>>(){}.getType() );
		
		
		Map<Long, Integer> pageViewsMap = new HashMap<>();
		Map<Long, Integer> readPageViewsMap = new HashMap<>();
		
		for( Entry<String, Integer> entry : uriViewsMap.entrySet() ) {
			
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
			PratilipiGoogleAnalyticsDoc gaDoc = docAccessor.getPratilipiGoogleAnalyticsDoc( entry.getKey() );
			if( readPageViewsMap.get( entry.getKey() ) == null ) {
				if( gaDoc.getPageViews( year, month, day ) != entry.getValue() ) {
					logger.log( Level.WARNING,
							"Year:" + year
							+ " Month:" + month
							+ " Day:" + day
							+ " Count:" + gaDoc.getPageViews( year, month, day ) + " --> " + entry.getValue() );
					PratilipiDocUtil.updatePratilipiGoogleAnalyticsPageViews( entry.getKey(), year, month, day, entry.getValue(), 0 );
				}
			} else {
				if( gaDoc.getPageViews( year, month, day ) != entry.getValue()
						|| gaDoc.getReadPageViews( year, month, day ) != readPageViewsMap.get( entry.getKey() ) ) {
					logger.log( Level.WARNING,
							"Year:" + year
							+ " Month:" + month
							+ " Day:" + day
							+ " Count:" + gaDoc.getPageViews( year, month, day ) + " --> " + entry.getValue()
							+ " Read Count:" + gaDoc.getReadPageViews( year, month, day ) + " --> " + readPageViewsMap.get( entry.getKey() ) );
					PratilipiDocUtil.updatePratilipiGoogleAnalyticsPageViews( entry.getKey(), year, month, day, entry.getValue(), readPageViewsMap.get( entry.getKey() ) );
				}
				readPageViewsMap.remove( entry.getKey() );
			}
		}
		
		for( Entry<Long, Integer> entry : readPageViewsMap.entrySet() ) {
			PratilipiGoogleAnalyticsDoc gaDoc = docAccessor.getPratilipiGoogleAnalyticsDoc( entry.getKey() );
			if( gaDoc.getReadPageViews( year, month, day ) != entry.getValue() ) {
				logger.log( Level.WARNING,
						"Year:" + year
						+ " Month:" + month
						+ " Day:" + day
						+ " Read Count:" + gaDoc.getReadPageViews( year, month, day ) + " --> " + entry.getValue() );
				PratilipiDocUtil.updatePratilipiGoogleAnalyticsPageViews( entry.getKey(), year, month, day, 0, entry.getValue() );
			}
		}
		
	}
	
}
