package com.pratilipi.api.impl.init;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.AccessType;
import com.pratilipi.common.type.PageType;
import com.pratilipi.data.BlobAccessor;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DocAccessor;
import com.pratilipi.data.type.AuditLog;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.BlobEntry;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.PratilipiGoogleAnalyticsDoc;
import com.pratilipi.data.type.gae.AuditLogEntityOfy;
import com.pratilipi.data.type.gae.AuthorEntity;
import com.pratilipi.data.type.gae.PratilipiEntity;
import com.pratilipi.data.util.PratilipiDocUtil;

@SuppressWarnings("serial")
@Bind( uri = "/init" )
public class InitApi extends GenericApi {
	
	private static final Logger logger =
			Logger.getLogger( InitApi.class.getName() );

	
	public static class GetRequest extends GenericRequest {

		private Long pratilipiId;
		private Integer year;
		private Integer month;
		private Integer day;

		
		public Integer getYear() {
			return year;
		}
		
		public Integer getMonth() {
			return month;
		}

		public Integer getDay() {
			return day;
		}
		
	}

	
	@Get
	public GenericResponse get( GetRequest request ) throws UnexpectedServerException {
		
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

/*
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
*/

/*		QueryKeys<PratilipiEntity> queryKeys = ObjectifyService.ofy().load().type( PratilipiEntity.class ).keys();
		List<Task> taskList = new LinkedList<>();
		for( Key<PratilipiEntity> key : queryKeys.iterable() ) {
			Task task = TaskQueueFactory.newTask()
					.setUrl( "/pratilipi/process" )
					.addParam( "pratilipiId", key.getId() + "" )
					.addParam( "updateReviewsDoc", "true" )
					.addParam( "updateUserPratilipiStats", "true" );
			taskList.add( task );
		}
		TaskQueueFactory.getPratilipiOfflineTaskQueue().addAll( taskList );
*/

		
/*		QueryKeys<PratilipiEntity> queryKeys = ObjectifyService.ofy().load()
				.type( PratilipiEntity.class )
				.filter( "TOTAL_RATING ==", null )
				.keys();
		
		for( Key<PratilipiEntity> key : queryKeys.iterable() ) {
			Pratilipi pratilipi = (Pratilipi) ObjectifyService.ofy().load().key( key );
			if( pratilipi.getTotalRating() > 0 )
				ObjectifyService.ofy().save().entity( pratilipi ).now();
		}
*/		
		
/*		Query<AuthorEntity> query = ObjectifyService.ofy()
				.load()
				.type( AuthorEntity.class )
				.filter( "LANGUAGE_ID !=", null )
				.limit( 1000 );

		QueryResultIterator <AuthorEntity> iterator = query.iterator();
		while( iterator.hasNext() ) {
			
			AuthorEntity author = iterator.next();
			author.getLanguage();
			author.getState();
			author.hasCustomImage();
			
			ObjectifyService.ofy().save().entity( author );
			
		}
*/

		Gson gson = new Gson();
		
		Query<AuditLogEntityOfy> query = ObjectifyService.ofy()
				.load()
				.type( AuditLogEntityOfy.class )
				.filter( "CREATION_DATE", null )
				.limit( 1000 );

		QueryResultIterator <AuditLogEntityOfy> iterator = query.iterator();
		while( iterator.hasNext() ) {
			
			AuditLog auditLog = iterator.next();
			
			if( auditLog.getCreationDate() != null ) {
				
				logger.log( Level.INFO, "Creation date is not null for " + auditLog.getId() );
				
			} else if( auditLog.getAccessType() == AccessType.AUTHOR_ADD ) {
				
				Author a2 = gson.fromJson( auditLog.getEventDataNew(), AuthorEntity.class );
				if( a2.getRegistrationDate() != null ) {
					( (AuditLogEntityOfy) auditLog ).setCreationDate( a2.getRegistrationDate() );
					ObjectifyService.ofy().save().entity( auditLog );
				}
				
			} else if( auditLog.getAccessType() == AccessType.PRATILIPI_ADD ) {
				
				Pratilipi p2 = gson.fromJson( auditLog.getEventDataNew(), PratilipiEntity.class );
				if( p2.getLastUpdated() != null ) {
					( (AuditLogEntityOfy) auditLog ).setCreationDate( p2.getLastUpdated() );
					ObjectifyService.ofy().save().entity( auditLog );
				}
				
			} else if( auditLog.getAccessType() == AccessType.PRATILIPI_UPDATE ) {
				
				Pratilipi p1 = gson.fromJson( auditLog.getEventDataOld(), PratilipiEntity.class );
				Pratilipi p2 = gson.fromJson( auditLog.getEventDataNew(), PratilipiEntity.class );
				
				if( p2.getLastUpdated().equals( p1.getLastUpdated() ) ) {
					logger.log( Level.INFO, "Deleting audit log " + auditLog.getId() );
					ObjectifyService.ofy().delete().entity( auditLog );
				} else {
					logger.log( Level.INFO, "Setting creation date for " + auditLog.getId() + " to " + p2.getLastUpdated() );
					( (AuditLogEntityOfy) auditLog ).setCreationDate( p2.getLastUpdated() );
					ObjectifyService.ofy().save().entity( auditLog );
				}
				
			} else {
				
				logger.log( Level.INFO, "Skipping audit log of type " + auditLog.getAccessType() );
				
			}
			
		}

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
