package com.pratilipi.api.impl.init;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.impl.init.shared.GetInitApiRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.AccessType;
import com.pratilipi.common.type.PageType;
import com.pratilipi.data.BlobAccessor;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DocAccessor;
import com.pratilipi.data.type.AccessToken;
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
	public GenericResponse get( GetInitApiRequest request ) throws UnexpectedServerException {
		
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
		
		long[][] offset = {
//				new long[] {5135361533542400L,2663L},
//				new long[] {5155035268775936L,474L},
//				new long[] {4946718139351040L,0L},
//				new long[] {5822385531912192L,10L},
//				new long[] {5669071953592320L,1609L},
//				new long[] {5693908432453632L,0L},
//				new long[] {5670554354843648L,2119L},
//				new long[] {5691188921237504L,3040L},
//				new long[] {5096266023305216L,1009L},
				
//				new long[] {5073954372845568L,669L},
//				new long[] {5153641115680768L,470L},
//				new long[] {5971619986014208L,1055L},
//				new long[] {5713391385575424L,3734L},
//				new long[] {5181175278600192L,338L},
//				new long[] {5688643247144960L,52L},
//				new long[] {6041301266989056L,1539L},
//				new long[] {5083798974758912L,1239L},
//				new long[] {6246207177359360L,0L},
//				new long[] {6565354117529600L,1535L},
//				new long[] {6257159746617344L,0L},
//				new long[] {5718893775552512L,0L},
//				new long[] {5747531812175872L,144L},
				
				
				new long[] { 4896100288823296L, 385L, 6L },
				new long[] { 5713881188007936L, 8L, 0L },
				new long[] { 5767727608233984L, 130L, 87L },
				new long[] { 6028434421579776L, 164L, 160L },
				new long[] { 5636590961426432L, 454L, 133L },
				new long[] { 6279647541067776L, 19L, 0L },
				new long[] { 6046070882697216L, 4L, 0L },
				new long[] { 6609883877081088L, 418L, 265L },
				new long[] { 5634191987310592L, 17L, 1L },
				new long[] { 5118635873927168L, 1L, 0L },
				new long[] { 5088740947001344L, 0L, 87L },
				new long[] { 4914143572262912L, 363L, 582L },
				
		};
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		Gson gson = new Gson();

		for( long[] offsetCount : offset ) {
		
			Pratilipi pratilipi = dataAccessor.getPratilipi( offsetCount[0] );
	
			AccessToken accessToken = AccessTokenFilter.getAccessToken();
			AuditLog auditLog = dataAccessor.newAuditLogOfy();
			auditLog.setAccessId( accessToken.getId() );
			auditLog.setAccessType( AccessType.PRATILIPI_UPDATE );
			auditLog.setEventDataOld( gson.toJson( pratilipi ) );
			
			pratilipi.setFbLikeShareCountOffset( offsetCount[1] );
			pratilipi.setFbLikeShareCount( offsetCount[2] );
//			pratilipi.setReadCountOffset( offsetCount[1] );
			
			auditLog.setEventDataNew( gson.toJson( pratilipi ) );
	
			pratilipi = dataAccessor.createOrUpdatePratilipi( pratilipi, auditLog );
			
		}


/*		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		String appPropertyId = "Api.Init.UpdateStats";
		AppProperty appProperty = dataAccessor.getAppProperty( appPropertyId );
		if( appProperty == null ) {
			appProperty = dataAccessor.newAppProperty( appPropertyId );
			appProperty.setValue( new Date( 1420051500000L + TimeUnit.DAYS.toMillis( 13 ) ) ); // 01 + 13 Jan 2015, 12:15 AM IST
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
				? new Date()
				: new Date( date.getTime() + TimeUnit.DAYS.toMillis( 1 ) ) );
		appProperty = dataAccessor.createOrUpdateAppProperty( appProperty );
		
*/
		
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
