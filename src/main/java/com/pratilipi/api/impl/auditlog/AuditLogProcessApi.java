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

import com.googlecode.objectify.ObjectifyService;
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
import com.pratilipi.common.type.UserReviewState;
import com.pratilipi.common.util.SystemProperty;
import com.pratilipi.common.util.UserAccessUtil;
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
import com.pratilipi.data.type.UserPratilipi;


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
		Set<String> userPratilipiReviewIds = new HashSet<>();
		Set<String> userAuthorFollowingIds = new HashSet<>();
		for( AuditLog auditLog : auditLogDataListCursorTuple.getDataList() ) {
			// TODO: Delete following condition as soon as 'legacy' module is removed
			if( auditLog.getUserId() == null || auditLog.getPrimaryContentId() == null ) {
				ObjectifyService.ofy().delete().entity( auditLog ).now();
				continue;
			}
			if( auditLog.getUserId().equals( SystemProperty.SYSTEM_USER_ID ) )
				continue;
			if( auditLog.getAccessType() == AccessType.PRATILIPI_UPDATE )
				pratilipiUpdateIds.add( auditLog.getPrimaryContentIdLong() );
			else if( auditLog.getAccessType()  == AccessType.USER_PRATILIPI_REVIEW )
				userPratilipiReviewIds.add( auditLog.getPrimaryContentId() );
			else if( auditLog.getAccessType() == AccessType.USER_AUTHOR_FOLLOWING )
				userAuthorFollowingIds.add( auditLog.getPrimaryContentId() );
		}

		// Batch get PrimaryContent entities
		Map<String, UserPratilipi> userPratilipiReviews = dataAccessor.getUserPratilipis( userPratilipiReviewIds );
		Map<String, UserAuthor> userAuthorFollowings = dataAccessor.getUserAuthors( userAuthorFollowingIds );
		

		// Batch get Pratilipi entities
		Set<Long> pratilipiIds = new HashSet<>( pratilipiUpdateIds );
		for( UserPratilipi userPratilipi : userPratilipiReviews.values() )
			pratilipiIds.add( userPratilipi.getPratilipiId() );
		Map<Long, Pratilipi> pratilipis = dataAccessor.getPratilipis( pratilipiIds );
		
		// Batch get Author entities
		Set<Long> authorIds = new HashSet<>();
//		for( Long pratilipiId : pratilipiUpdateIds )
//			authorIds.add( pratilipis.get( pratilipiId ).getAuthorId() );
//		for( UserPratilipi userPratilipi : userPratilipiReviews.values() )
//			authorIds.add( pratilipis.get( userPratilipi.getPratilipiId() ).getAuthorId() );
		for( Pratilipi pratilipi : pratilipis.values() )
			authorIds.add( pratilipi.getAuthorId() );
		for( UserAuthor userAuthor : userAuthorFollowings.values() )
			authorIds.add( userAuthor.getAuthorId() );
		Map<Long, Author> authors = dataAccessor.getAuthors( authorIds );

		
		
		// auditLog.getAccessType() == AccessType.PRATILIPI_UPDATE
		for( Long pratilipiId : pratilipiUpdateIds ) {

			Pratilipi pratilipi = pratilipis.get( pratilipiId );
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
			followerUserIdList.addAll( _getAeeUserIdList( pratilipi.getLanguage() ) );
			
			_createPratilipiPublishedNotification( pratilipi, authors.get( pratilipi.getAuthorId() ) );
			_createPratilipiPublishedNotifications( pratilipi, followerUserIdList );
			
		}
		
		
		// auditLog.getAccessType() == AccessType.USER_PRATILIPI_REVIEW
		for( UserPratilipi userPratilipi : userPratilipiReviews.values() ) {

			if( userPratilipi.getReviewState() != UserReviewState.PUBLISHED )
				continue;

			Long pratilipiId = userPratilipi.getPratilipiId();
			_createUserPratilipiReviewEmail( userPratilipi, authors.get( pratilipis.get( pratilipiId ).getAuthorId() ) );

		}


		// auditLog.getAccessType() == AccessType.USER_AUTHOR_FOLLOWING
		for( UserAuthor userAuthor : userAuthorFollowings.values() ) {

			if( ! userAuthor.isFollowing() )
				continue;

			_createUserAuthorFollowingNotifications( userAuthor, authors.get( userAuthor.getAuthorId() ) );
			_createUserAuthorFollowingEmails( userAuthor, authors.get( userAuthor.getAuthorId() ) );

		}

		
		
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

		// TODO: Remove it ASAP
		if( ! UserAccessUtil.hasUserAccess( author.getUserId(), null, AccessType.USER_ADD ) )
			return;

		
		Email email = dataAccessor.getEmail(
				author.getUserId(),
				EmailType.PRATILIPI_PUBLISHED_AUTHOR, 
				pratilipi.getId() );

		if( email == null ) {
			email = dataAccessor.newEmail(
					author.getUserId(),
					EmailType.PRATILIPI_PUBLISHED_AUTHOR,
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
				EmailType.PRATILIPI_PUBLISHED_FOLLOWER,
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

		// Creating new emails
		for( Long follower : followers ) {
			// TODO: Remove check ASAP
			if( UserAccessUtil.hasUserAccess( follower, null, AccessType.USER_ADD ) )
				emailList.add( dataAccessor.newEmail(
					follower,
					EmailType.PRATILIPI_PUBLISHED_FOLLOWER,
					pratilipi.getId() ) );
		}
		
		emailList = dataAccessor.createOrUpdateEmailList( emailList );
		
	}

	private void _createUserPratilipiReviewEmail( UserPratilipi userPratilipi, Author author ) {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		if( author.getUserId() == null )
			return;
		
		// TODO: Remove it ASAP
		if( ! UserAccessUtil.hasUserAccess( author.getUserId(), null, AccessType.USER_ADD ) )
			return;

		
		Email email = dataAccessor.getEmail(
				author.getUserId(),
				EmailType.USER_PRATILIPI_REVIEW, 
				userPratilipi.getId() );

		
		if( email == null ) {
			email = dataAccessor.newEmail(
					author.getUserId(),
					EmailType.USER_PRATILIPI_REVIEW, 
					userPratilipi.getId() );
		} else if( email.getState() == EmailState.DEFERRED ) {
			email.setState( EmailState.PENDING );
			email.setLastUpdated( new Date() );
		} else {
			return; // Do nothing
		}


		email = dataAccessor.createOrUpdateEmail( email );

	}
	
	private void _createUserAuthorFollowingEmails( UserAuthor userAuthor, Author author ) {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		if( author.getUserId() == null ) // Followed
			return;
		
		// TODO: Remove it ASAP
		if( ! UserAccessUtil.hasUserAccess( author.getUserId(), null, AccessType.USER_ADD ) )
			return;

		
		Email email = dataAccessor.getEmail(
				author.getUserId(),
				EmailType.AUTHOR_FOLLOW, 
				userAuthor.getId() );

		
		if( email == null ) {
			email = dataAccessor.newEmail(
					author.getUserId(),
					EmailType.AUTHOR_FOLLOW, 
					userAuthor.getId() );
		} else if( email.getState() == EmailState.DEFERRED ) {
			email.setState( EmailState.PENDING );
			email.setLastUpdated( new Date() );
		} else {
			return; // Do nothing
		}


		email = dataAccessor.createOrUpdateEmail( email );

	}

	
	private boolean _isToday( Date date ) {
		Long time = new Date().getTime();
		time = time - time % TimeUnit.DAYS.toMillis( 1 ); // 00:00 AM GMT
		time = time - TimeUnit.MINUTES.toMillis( 330 ); // 00:00 AM IST
		return date.getTime() > time;
	}
	
	private List<Long> _getAeeUserIdList( Language language ) {

		Long[] userIds = {};
		
		switch( language ) {
			case HINDI:
				userIds = new Long[] {
						4790800105865216L, // veena@
						5743817900687360L, // jitesh@
						5991416564023296L, // sankar@
						5664902681198592L, // shally@
				}; break;
			case GUJARATI:
				userIds = new Long[] {
						5644707593977856L, // nimisha@
						6046961763352576L, // brinda@
						5743817900687360L, // jitesh@
						5991416564023296L, // sankar@
						5664902681198592L, // shally@
				}; break;
			case TAMIL:
				userIds = new Long[] {
						5991416564023296L, // sankar@
						5674672871964672L, // krithiha@
						4900071594262528L, // dileepan@
				}; break;
			case MARATHI:
				userIds = new Long[] {
						4900189601005568L, // vrushali@
						5743817900687360L, // jitesh@
						5991416564023296L, // sankar@
						5664902681198592L, // shally@
				}; break;
			case MALAYALAM:
				userIds = new Long[] {
						5666355716030464L, // vaisakh@
						5674672871964672L, // krithiha@
						5991416564023296L, // sankar@
				}; break;
			case BENGALI:
				userIds = new Long[] {
						6243664397336576L, // moumita@
						5743817900687360L, // jitesh@
						5991416564023296L, // sankar@
						5664902681198592L, // shally@
				}; break;
			case TELUGU:
				userIds = new Long[] {
						5187684625547264L, // johny@
						5674672871964672L, // krithiha@
						5991416564023296L, // sankar@
				}; break;
			case KANNADA:
				userIds = new Long[] {
						5715256422694912L, // aruna@
						5674672871964672L, // krithiha@
						5991416564023296L, // sankar@
				}; break;
			default:
				userIds = new Long[] {
						5705241014042624L, // prashant@
				}; break;
			
		}

		return Arrays.asList( userIds );
		
	}
	
}