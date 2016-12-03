package com.pratilipi.api.impl.auditlog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.type.AccessType;
import com.pratilipi.common.type.EmailState;
import com.pratilipi.common.type.EmailType;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.NotificationState;
import com.pratilipi.common.type.NotificationType;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.util.SystemProperty;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.type.AppProperty;
import com.pratilipi.data.type.AuditLog;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Email;
import com.pratilipi.data.type.Notification;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.UserAuthor;


@SuppressWarnings("serial")
@Bind( uri = "/auditlog/process" )
public class AuditLogProcessApi extends GenericApi {

	@Get
	public GenericResponse get( GenericRequest request ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		// Fetching AppProperty
		String appPropertyId = "Api.AuditLogProcess";
		AppProperty appProperty = dataAccessor.getAppProperty( appPropertyId );
		if( appProperty == null )
			appProperty = dataAccessor.newAppProperty( appPropertyId );

		// Fetching list of audit logs
		DataListCursorTuple<AuditLog> auditLogDataListCursorTuple = dataAccessor.getAuditLogList(
				new Date( 1469989800000L ), // Mon Aug 01 00:00:00 IST 2016
				(String) appProperty.getValue(),
				10000 );


		// Make sets of PrimaryContent ids
		Set<Long> pratilipiUpdateIds = new HashSet<>();
		Set<String> userAuthorFollowingIds = new HashSet<>();
		for( AuditLog auditLog : auditLogDataListCursorTuple.getDataList() ) {
			if( auditLog.getUserId().equals( SystemProperty.SYSTEM_USER_ID ) )
				continue;
			if( auditLog.getAccessType() == AccessType.PRATILIPI_UPDATE )
				pratilipiUpdateIds.add( auditLog.getPrimaryContentIdLong() );
			else if( auditLog.getAccessType() == AccessType.USER_AUTHOR_FOLLOWING )
				userAuthorFollowingIds.add( auditLog.getPrimaryContentId() );
		}

		// Batch get PratilipiContent entities
		Map<Long, Pratilipi> pratilipiUpdates = dataAccessor.getPratilipis( pratilipiUpdateIds );
		Map<String, UserAuthor> userAuthorFollowings = dataAccessor.getUserAuthors( userAuthorFollowingIds );

		
		// Make sets of required entities' ids
		Set<Long> authorIds = new HashSet<>();
		for( Pratilipi pratilipi : pratilipiUpdates.values() ) {
			authorIds.add( pratilipi.getAuthorId() );
			authorIds.addAll( _getAeeAuthorIdList( pratilipi.getLanguage() ) ); // Required for notifying AEEs
		}
		for( UserAuthor userAuthor : userAuthorFollowings.values() )
			authorIds.add( userAuthor.getAuthorId() );
		
		// Batch get required entites
		Map<Long, Author> authors = dataAccessor.getAuthors( authorIds );

		
		
		// auditLog.getAccessType() == AccessType.PRATILIPI_UPDATE
		for( Pratilipi pratilipi : pratilipiUpdates.values() ) {
				
			if( pratilipi.getState() != PratilipiState.PUBLISHED )
				continue;

			List<Long> followerUserIdList = dataAccessor.getUserAuthorFollowList(
					null,
					pratilipi.getAuthorId(),
					null,
					null,
					null ).getDataList();
			
			_createPratilipiPublishedEmail( pratilipi, authors.get( pratilipi.getAuthorId() ) );
			_createPratilipiPublishedEmails( pratilipi, followerUserIdList );
			
			// Sent notification to all AEEs all well
			followerUserIdList.addAll( _getAeeAuthorIdList( pratilipi.getLanguage() ) );
			
			_createPratilipiPublishedNotification( pratilipi, authors.get( pratilipi.getAuthorId() ) );
			_createPratilipiPublishedNotifications( pratilipi, followerUserIdList );
			
		}
		

		
		// auditLog.getAccessType() == AccessType.USER_AUTHOR_FOLLOWING
		

		
		for( UserAuthor userAuthor : userAuthorFollowings.values() )
			_createUserAuthorFollowingNotifications( userAuthor, authors.get( userAuthor.getAuthorId() ) );

		
		
		// Updating AppProperty.
		if( auditLogDataListCursorTuple.getDataList().size() > 0 ) {
			appProperty.setValue( auditLogDataListCursorTuple.getCursor() );
			appProperty = dataAccessor.createOrUpdateAppProperty( appProperty );
		}
	
		
		return new GenericResponse();
		
	}
	

	private void _createPratilipiPublishedNotification( Pratilipi pratilipi, Author author ) {

		if( author.getUserId() == null )
			return;
		
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		Notification notification = dataAccessor.getNotification(
				author.getUserId(),
				NotificationType.PRATILIPI_PUBLISHED_AUTHOR,
				pratilipi.getId() );
		
		if( notification != null )
			return;

		
		notification = dataAccessor.newNotification(
				author.getUserId(),
				NotificationType.PRATILIPI_PUBLISHED_AUTHOR,
				pratilipi.getId() );

		notification = dataAccessor.createOrUpdateNotification( notification );

	}
	
	private void _createPratilipiPublishedNotifications( Pratilipi pratilipi, List<Long> followers ) {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		followers = new ArrayList<Long>( followers );

		List<Notification> existingNotificationList = dataAccessor.getNotificationListIterator(
				null,
				NotificationType.PRATILIPI_PUBLISHED_FOLLOWER,
				pratilipi.getId(),
				null,
				null ).list();

		
		for( Notification notification : existingNotificationList )
			followers.remove( notification.getUserId() );

		
		List<Notification> notificationList = new ArrayList<>( followers.size() );
		for( Long followerUserId : followers )
			notificationList.add( dataAccessor.newNotification(
					followerUserId,
					NotificationType.PRATILIPI_PUBLISHED_FOLLOWER,
					pratilipi.getId() ) );
		
		notificationList = dataAccessor.createOrUpdateNotificationList( notificationList );

	}

	private void _createUserAuthorFollowingNotifications( UserAuthor userAuthor, Author author ) {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		if( author.getUserId() == null ) // Followed
			return;
			
		
		Notification notification = dataAccessor.getNotification(
				author.getUserId(),
				NotificationType.AUTHOR_FOLLOW,
				author.getId() );
			
		if( notification == null || ( userAuthor.isFollowing() && ! _isToday( notification.getCreationDate() ) ) )
			notification = dataAccessor.newNotification(
					author.getUserId(),
					NotificationType.AUTHOR_FOLLOW,
					author.getId() );

		
		if( userAuthor.isFollowing() && notification.addDataId( userAuthor.getUserId() ) ) {
			if( notification.getState() == NotificationState.READ )
				notification.setState( NotificationState.UNREAD );
			notification.setFcmPending( true );
		} else if( ! userAuthor.isFollowing() && notification.removeDataId( userAuthor.getUserId() ) ) {
			// Do nothing
		} else {
			return;
		}
			
		notification.setLastUpdated( new Date() );
		notification = dataAccessor.createOrUpdateNotification( notification );

	}
	
	
	private void _createPratilipiPublishedEmail( Pratilipi pratilipi, Author author ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
	
		if( author.getUserId() == null )
			return;

		
		Email email = dataAccessor.getEmail(
				author.getUserId(),
				EmailType.PRATILIPI_PUBLISHED_AUTHOR_EMAIL, 
				pratilipi.getId() );

		if( email == null ) {
			email = dataAccessor.newEmail(
					author.getUserId(),
					EmailType.PRATILIPI_PUBLISHED_AUTHOR_EMAIL,
					pratilipi.getId() );
		} else if( email.getState() == EmailState.DEFERRED ) {
			email.setState( EmailState.PENDING );
			email.setLastUpdated( new Date() );
		} else {
			return; // Do nothing
		}
		

		email = dataAccessor.createOrUpdateEmail( email );

	}
	
	private void _createPratilipiPublishedEmails( Pratilipi pratilipi, List<Long> followers ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		followers = new ArrayList<Long>( followers );
		
		List<Email> existingEmailList = dataAccessor.getEmailList(
				null,
				EmailType.PRATILIPI_PUBLISHED_FOLLOWER_EMAIL,
				pratilipi.getId(),
				null,
				null );

		
		List<Email> emailList = new LinkedList<>();

		for( Email email : existingEmailList ) {
			followers.remove( email.getUserId() );
			if( email.getState() == EmailState.DEFERRED ) { // Updating existing email state, if required
				email.setState( EmailState.PENDING );
				email.setLastUpdated( new Date() );
				emailList.add( email );
			}
		}

		for( Long follower : followers ) // Creating new emails
			emailList.add( dataAccessor.newEmail(
					follower,
					EmailType.PRATILIPI_PUBLISHED_FOLLOWER_EMAIL,
					pratilipi.getId() ) );

		
		emailList = dataAccessor.createOrUpdateEmailList( emailList );
		
	}

	
	private boolean _isToday( Date date ) {
		Long time = new Date().getTime();
		time = time - time % TimeUnit.DAYS.toMillis( 1 ); // 00:00 AM GMT
		time = time - TimeUnit.MINUTES.toMillis( 330 ); // 00:00 AM IST
		return date.getTime() > time;
	}
	
	private List<Long> _getAeeAuthorIdList( Language language ) {

		Long[] authorIds = {};
		
		switch( language ) {
			case HINDI:
				authorIds = new Long[] {
					4826853940396032L, // veena@
					5695576559583232L, // jitesh@
					5740985543819264L, // sankar@
					5757071102312448L, // shally@
			}; break;
			case GUJARATI:
				authorIds = new Long[] {
					5715779974594560L, // nimisha@
					6468145583751168L, // brinda@
					5695576559583232L, // jitesh@
					5740985543819264L, // sankar@
					5757071102312448L, // shally@
			}; break;
			case TAMIL:
				authorIds = new Long[] {
					5740985543819264L, // sankar@
					5747218191482880L, // krithiha@
					5719092866580480L, // dileepan@
			}; break;
			case MARATHI:
				authorIds = new Long[] {
					4880483888398336L, // vrushali@
					5695576559583232L, // jitesh@
					5740985543819264L, // sankar@
					5757071102312448L, // shally@
			}; break;
			case MALAYALAM:
				authorIds = new Long[] {
					5755723784912896L, // vaisakh@
					5747218191482880L, // krithiha@
					5740985543819264L, // sankar@
			}; break;
			case BENGALI:
				authorIds = new Long[] {
					6263963754954752L, // moumita@
					5695576559583232L, // jitesh@
					5740985543819264L, // sankar@
					5757071102312448L, // shally@
			}; break;
			case TELUGU:
				authorIds = new Long[] {
					5735028278427648L, // johny@
					5747218191482880L, // krithiha@
					5740985543819264L, // sankar@
			}; break;
			case KANNADA:
				authorIds = new Long[] {
					5761847154180096L, // aruna@
					5747218191482880L, // krithiha@
					5740985543819264L, // sankar@
			}; break;
			case ENGLISH:
				authorIds = new Long[] {
					5678034745032704L, // prashant@
			}; break;
			
		}

		return Arrays.asList( authorIds );
		
	}
	
}