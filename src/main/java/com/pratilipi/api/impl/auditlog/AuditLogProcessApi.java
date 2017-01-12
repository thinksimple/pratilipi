package com.pratilipi.api.impl.auditlog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.AccessType;
import com.pratilipi.common.type.CommentParentType;
import com.pratilipi.common.type.EmailFrequency;
import com.pratilipi.common.type.EmailState;
import com.pratilipi.common.type.EmailType;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.NotificationState;
import com.pratilipi.common.type.NotificationType;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.type.UserFollowState;
import com.pratilipi.common.type.UserReviewState;
import com.pratilipi.common.type.VoteParentType;
import com.pratilipi.common.type.VoteType;
import com.pratilipi.common.util.GsonLongDateAdapter;
import com.pratilipi.common.util.SystemProperty;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.RtdbAccessor;
import com.pratilipi.data.type.AppProperty;
import com.pratilipi.data.type.AuditLog;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Comment;
import com.pratilipi.data.type.Email;
import com.pratilipi.data.type.Notification;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.User;
import com.pratilipi.data.type.UserAuthor;
import com.pratilipi.data.type.UserPratilipi;
import com.pratilipi.data.type.UserPreferenceRtdb;
import com.pratilipi.data.type.Vote;
import com.pratilipi.data.type.gae.PratilipiEntity;
import com.pratilipi.data.type.gae.UserAuthorEntity;
import com.pratilipi.data.type.gae.UserPratilipiEntity;
import com.pratilipi.data.type.gae.VoteEntity;


@SuppressWarnings("serial")
@Bind( uri = "/auditlog/process" )
public class AuditLogProcessApi extends GenericApi {
	
	private static final Logger logger = Logger.getLogger( AuditLogProcessApi.class.getName() );

	@Get
	public GenericResponse get( GenericRequest request ) 
			throws UnexpectedServerException {
		
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
				5000 );


		// Make sets of PrimaryContent ids
		Map<Long, Set<Long>> pratilipiUpdateIds = new HashMap<>();
		Set<String> userPratilipiUpdateIds = new HashSet<>();
		Set<String> userAuthorUpdateIds = new HashSet<>();
		Set<Long> commentUpdateIds = new HashSet<>();
		Set<String> voteUpdateIds = new HashSet<>();

		Gson gson = new GsonBuilder()
					.registerTypeAdapter( Date.class, new GsonLongDateAdapter() )
					.create();

		for( AuditLog auditLog : auditLogDataListCursorTuple.getDataList() ) {
			// TODO: Delete following condition as soon as 'legacy' module is removed
			if( auditLog.getUserId() == null || auditLog.getPrimaryContentId() == null ) {
				continue;
			}
			if( auditLog.getUserId().equals( SystemProperty.SYSTEM_USER_ID ) ) {
				continue;
			}
			if( auditLog.getAccessType() == AccessType.PRATILIPI_UPDATE ) {
				Pratilipi oldPratilipi = gson.fromJson( auditLog.getEventDataOld(), PratilipiEntity.class );
				Pratilipi newPratilipi = gson.fromJson( auditLog.getEventDataNew(), PratilipiEntity.class );
				if( oldPratilipi.getState() == PratilipiState.DRAFTED && newPratilipi.getState() == PratilipiState.PUBLISHED ) {
					Set<Long> userIdSet = pratilipiUpdateIds.get( auditLog.getPrimaryContentIdLong() );
					if( userIdSet == null ) {
						userIdSet = new HashSet<>();
						pratilipiUpdateIds.put( auditLog.getPrimaryContentIdLong(), userIdSet );
					}
					userIdSet.add( auditLog.getUserId() );
				}
			}
			else if( auditLog.getAccessType()  == AccessType.USER_PRATILIPI_REVIEW ) {
				UserPratilipi oldUserPratilipi = gson.fromJson( auditLog.getEventDataOld(), UserPratilipiEntity.class );
				UserPratilipi newUserPratilipi = gson.fromJson( auditLog.getEventDataNew(), UserPratilipiEntity.class );
				if( oldUserPratilipi.getRating() == null && oldUserPratilipi.getReview() == null && 
						( newUserPratilipi.getRating() != null || newUserPratilipi.getReview() != null ) )
					userPratilipiUpdateIds.add( auditLog.getPrimaryContentId() );
			}
			else if( auditLog.getAccessType() == AccessType.USER_AUTHOR_FOLLOWING ) {
				UserAuthor oldUserAuthor = gson.fromJson( auditLog.getEventDataOld(), UserAuthorEntity.class );
				UserAuthor newUserAuthor = gson.fromJson( auditLog.getEventDataNew(), UserAuthorEntity.class );
				if( oldUserAuthor.getFollowState() == null && newUserAuthor.getFollowState() == UserFollowState.FOLLOWING )
					userAuthorUpdateIds.add( auditLog.getPrimaryContentId() );
			}
			else if( auditLog.getAccessType() == AccessType.COMMENT_ADD ) {
				commentUpdateIds.add( auditLog.getPrimaryContentIdLong() );
			}
			else if( auditLog.getAccessType() == AccessType.VOTE ) {
				Vote newVote = gson.fromJson( auditLog.getEventDataNew(), VoteEntity.class );
				if( newVote.getType() == VoteType.LIKE )
					voteUpdateIds.add( auditLog.getPrimaryContentId() );
			}
		}


		// Batch get Vote entities
		logger.log( Level.INFO, "Fetching " + voteUpdateIds.size() + " Vote Entities." );
		Map<String, Vote> votes = dataAccessor.getVotes( voteUpdateIds );


		// Batch get Comment and entities
		Set<Long> commentIds = new HashSet<>( commentUpdateIds );
		for( Vote vote : votes.values() )
			if( vote.getParentType() == VoteParentType.COMMENT )
				commentIds.add( vote.getParentIdLong() );
		logger.log( Level.INFO, "Fetching " + commentIds.size() + " Comment Entities." );
		Map<Long, Comment> comments = dataAccessor.getComments( commentIds );


		// Batch get UserPratilipi entities
		Set<String> userPratilipiIds = new HashSet<>( userPratilipiUpdateIds );
		for( Comment comment : comments.values() )
			if( comment.getParentType() == CommentParentType.REVIEW )
				userPratilipiIds.add( comment.getParentId() );
		for( Vote vote : votes.values() )
			if( vote.getParentType() == VoteParentType.REVIEW )
				userPratilipiIds.add( vote.getParentId() );
		logger.log( Level.INFO, "Fetching " + userPratilipiIds.size() + " UserPratilipi Entities." );
		Map<String, UserPratilipi> userPratilipis = dataAccessor.getUserPratilipis( userPratilipiIds );


		// Batch get Pratilipi entities
		Set<Long> pratilipiIds = new HashSet<>( pratilipiUpdateIds.keySet() );
		for( UserPratilipi userPratilipi : userPratilipis.values() )
			pratilipiIds.add( userPratilipi.getPratilipiId() );
		logger.log( Level.INFO, "Fetching " + pratilipiIds.size() + " Pratilipi Entities." );
		Map<Long, Pratilipi> pratilipis = dataAccessor.getPratilipis( pratilipiIds );


		// Batch get UserAuthor entities
		logger.log( Level.INFO, "Fetching " + userAuthorUpdateIds.size() + " UserAuthor Entities." );
		Map<String, UserAuthor> userAuthors = dataAccessor.getUserAuthors( userAuthorUpdateIds );

		// Batch get Author entities
		Set<Long> authorIds = new HashSet<>();
		for( Pratilipi pratilipi : pratilipis.values() )
			authorIds.add( pratilipi.getAuthorId() );
		for( UserAuthor userAuthor : userAuthors.values() )
			authorIds.add( userAuthor.getAuthorId() );
		logger.log( Level.INFO, "Fetching " + authorIds.size() + " Author Entities." );
		Map<Long, Author> authors = dataAccessor.getAuthors( authorIds );


		List<Email> totalEmailList = new ArrayList<>();

		// auditLog.getAccessType() == AccessType.PRATILIPI_UPDATE
		for( Long pratilipiId : pratilipiUpdateIds.keySet() ) {

			Pratilipi pratilipi = pratilipis.get( pratilipiId );
			List<Long> followerUserIdList = dataAccessor.getUserAuthorFollowList(
					null,
					pratilipi.getAuthorId(),
					null,
					null,
					null ).getDataList();

			Email email = _createPratilipiPublishedEmail( pratilipi, authors.get( pratilipi.getAuthorId() ) );
			if( email != null )
				totalEmailList.add( email );
			totalEmailList.addAll( _createPratilipiPublishedEmails( pratilipi, followerUserIdList ) );

			// Send notification to all AEEs as well
			// only if the content is self-published
			List<Long> aeeUserIdList = _getAeeUserIdList( pratilipi.getLanguage() );
			Set<Long> userIdSet = pratilipiUpdateIds.get( pratilipiId );
			for( Long userId : userIdSet ) {
				if( ! aeeUserIdList.contains( userId ) ) {
					followerUserIdList.addAll( aeeUserIdList );
					break;
				}
			}

			_createPratilipiPublishedNotification( pratilipi, authors.get( pratilipi.getAuthorId() ) );
			_createPratilipiPublishedNotifications( pratilipi, followerUserIdList );

		}



		// auditLog.getAccessType() == AccessType.USER_PRATILIPI_REVIEW
		for( String userPratilipiId : userPratilipiUpdateIds ) {

			UserPratilipi userPratilipi = userPratilipis.get( userPratilipiId );

			if( userPratilipi.getReviewState() != UserReviewState.PUBLISHED )
				continue;

			Long pratilipiId = userPratilipi.getPratilipiId();
			Email email = _createUserPratilipiReviewEmail( userPratilipi, authors.get( pratilipis.get( pratilipiId ).getAuthorId() ) );
			if( email != null )
				totalEmailList.add( email );

		}
		


		// auditLog.getAccessType() == AccessType.USER_AUTHOR_FOLLOWING
		for( String userAuthorId : userAuthorUpdateIds ) {

			UserAuthor userAuthor = userAuthors.get( userAuthorId );

			_createUserAuthorFollowingNotifications( userAuthor, authors.get( userAuthor.getAuthorId() ) );
			Email email = _createUserAuthorFollowingEmail( userAuthor, authors.get( userAuthor.getAuthorId() ) );
			if( email != null )
				totalEmailList.add( email );

		}


		// auditLog.getAccessType() == AccessType.COMMENT_ADD
		for( Long commentId : commentUpdateIds ) {

			Comment comment = comments.get( commentId );

			if( comment.getParentType() != CommentParentType.REVIEW )
				continue;

			UserPratilipi userPratilipi = userPratilipis.get( comment.getParentId() );

			Email email = _createCommentAddedReviewerEmail( userPratilipi, comment );
			if( email != null )
				totalEmailList.add( email );

			/*	// Business call - Not to send CommentAddedAuthorEmail.
			Pratilipi pratilipi = pratilipis.get( userPratilipi.getPratilipiId() );
			Author author = authors.get( pratilipi.getAuthorId() );
			email = _createCommentAddedAuthorEmail( author, comment );
			if( email != null )
				totalEmailList.add( email );
			*/

		}



		// auditLog.getAccessType() == AccessType.VOTE
		for( String voteId : voteUpdateIds ) {

			Vote vote = votes.get( voteId );

			if( vote.getParentType() == VoteParentType.REVIEW ) {

				UserPratilipi userPratilipi = userPratilipis.get( vote.getParentId() );

				// To the reviewer
				Email email = _createVoteOnReviewReviewerEmail( userPratilipi, vote );
				if( email != null )
					totalEmailList.add( email );

			} else if( vote.getParentType() == VoteParentType.COMMENT ) {

				// To the commentor
				Email email = _createVoteOnCommentCommentorEmail( comments.get( vote.getParentIdLong() ), vote );
				if( email != null )
					totalEmailList.add( email );

			}

		}

		_updateEmailTable( totalEmailList );

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
			
		if( notification == null || ( userAuthor.getFollowState() == UserFollowState.FOLLOWING && ! _isToday( notification.getCreationDate() ) ) )
			notification = dataAccessor.newNotification(
					author.getUserId(),
					NotificationType.AUTHOR_FOLLOW,
					author.getId() );

		
		if( userAuthor.getFollowState() == UserFollowState.FOLLOWING && notification.addDataId( userAuthor.getUserId() ) ) {
			if( notification.getState() == NotificationState.READ )
				notification.setState( NotificationState.UNREAD );
			notification.setFcmPending( true );
		} else if( userAuthor.getFollowState() != UserFollowState.FOLLOWING && notification.removeDataId( userAuthor.getUserId() ) ) {
			// Do nothing
		} else {
			return;
		}
			
		notification.setLastUpdated( new Date() );
		notification = dataAccessor.createOrUpdateNotification( notification );

	}
	

	private Email _createPratilipiPublishedEmail( Pratilipi pratilipi, Author author ) {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		if( author.getUserId() == null )
			return null;

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
			return null; // Do nothing
		}

		return email;

	}

	private List<Email> _createPratilipiPublishedEmails( Pratilipi pratilipi, List<Long> followers ) {

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

		// Creating new e-mails
		for( Long follower : followers ) {
			emailList.add( dataAccessor.newEmail(
				follower,
				EmailType.PRATILIPI_PUBLISHED_FOLLOWER,
				pratilipi.getId() ) );
		}

		return emailList;

	}

	private Email _createUserPratilipiReviewEmail( UserPratilipi userPratilipi, Author author ) {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		if( author.getUserId() == null )
			return null;

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
			return null; // Do nothing
		}


		return email;

	}
	
	private Email _createUserAuthorFollowingEmail( UserAuthor userAuthor, Author author ) {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		if( author.getUserId() == null ) // Followed
			return null;

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
			return null; // Do nothing
		}


		return email;

	}
	
	private Email _createCommentAddedReviewerEmail( UserPratilipi userPratilipi, Comment comment ) {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		Email email = dataAccessor.getEmail(
				userPratilipi.getUserId(),
				EmailType.COMMENT_REVIEW_REVIEWER, 
				comment.getId() );


		if( email == null ) {
			email = dataAccessor.newEmail(
					userPratilipi.getUserId(),
					EmailType.COMMENT_REVIEW_REVIEWER, 
					comment.getId() );
		} else if( email.getState() == EmailState.DEFERRED ) {
			email.setState( EmailState.PENDING );
			email.setLastUpdated( new Date() );
		} else {
			return null; // Do nothing
		}


		return email;

	}

	
	private Email _createCommentAddedAuthorEmail( Author author, Comment comment ) {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		if( author.getUserId() == null )
			return null;
		
		Email email = dataAccessor.getEmail(
				author.getUserId(),
				EmailType.COMMENT_REVIEW_AUTHOR, 
				comment.getId() );

		
		if( email == null ) {
			email = dataAccessor.newEmail(
						author.getUserId(),
						EmailType.COMMENT_REVIEW_AUTHOR, 
						comment.getId() );
		} else if( email.getState() == EmailState.DEFERRED ) {
			email.setState( EmailState.PENDING );
			email.setLastUpdated( new Date() );
		} else {
			return null; // Do nothing
		}


		return email;

	}
	
	
	private Email _createVoteOnReviewReviewerEmail( UserPratilipi userPratilipi, Vote vote ) {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		Email email = dataAccessor.getEmail(
				userPratilipi.getUserId(),
				EmailType.VOTE_REVIEW_REVIEWER, 
				vote.getId() );


		if( email == null ) {
			email = dataAccessor.newEmail(
					userPratilipi.getUserId(),
						EmailType.VOTE_REVIEW_REVIEWER, 
						vote.getId() );
		} else if( email.getState() == EmailState.DEFERRED ) {
			email.setState( EmailState.PENDING );
			email.setLastUpdated( new Date() );
		} else {
			return null; // Do nothing
		}

		return email;

	}

	private Email _createVoteOnCommentCommentorEmail( Comment comment, Vote vote ) {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		Email email = dataAccessor.getEmail(
				comment.getUserId(),
				EmailType.VOTE_COMMENT_REVIEW_COMMENTOR, 
				vote.getId() );


		if( email == null ) {
			email = dataAccessor.newEmail(
					comment.getUserId(),
					EmailType.VOTE_COMMENT_REVIEW_COMMENTOR, 
					vote.getId() );
		} else if( email.getState() == EmailState.DEFERRED ) {
			email.setState( EmailState.PENDING );
			email.setLastUpdated( new Date() );
		} else {
			return null; // Do nothing
		}


		return email;

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
	
	private void _updateEmailTable( List<Email> emailList ) throws UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		RtdbAccessor rtdbAccessor = DataAccessorFactory.getRtdbAccessor();

		Map<Long,UserPreferenceRtdb> userPreferenceMap = new HashMap<>();
		List<Email> immediateEmails = new ArrayList<>();
		for( Email email : emailList ) {
			UserPreferenceRtdb preference;
			if( userPreferenceMap.containsKey( email.getUserId() ) ) {
				preference = userPreferenceMap.get( email.getUserId() );
			} else {
				preference = rtdbAccessor.getUserPreference( email.getUserId() );
				userPreferenceMap.put( email.getUserId(), preference );
			}
			if( preference.getEmailFrequency() == null || preference.getEmailFrequency() == EmailFrequency.IMMEDIATELY ) {
				email.setScheduledDate( new Date() );
				immediateEmails.add( email );
			}
		}

		immediateEmails = dataAccessor.createOrUpdateEmailList( immediateEmails );

		/*
		Set<Long> userIds = new HashSet<>();

		Map<Long,UserPreferenceRtdb> userPreferenceMap = new HashMap<>();
		for( Email email : emailList ) {
			UserPreferenceRtdb preference;
			if( userPreferenceMap.containsKey( email.getUserId() ) ) {
				preference = userPreferenceMap.get( email.getUserId() );
			} else {
				preference = rtdbAccessor.getUserPreference( email.getUserId() );
				userPreferenceMap.put( email.getUserId(), preference );
			}

			if( preference.getEmailFrequency() != null && preference.getEmailFrequency() != EmailFrequency.IMMEDIATELY ) // Consolidated
				userIds.add( email.getUserId() );
		}

		Map<Long,User> users = dataAccessor.getUsers( userIds );
		for( Email email : emailList ) {
			UserPreferenceRtdb preference = userPreferenceMap.get( email.getUserId() );
			if( preference.getEmailFrequency() == null || preference.getEmailFrequency() == EmailFrequency.IMMEDIATELY )
				email.setScheduledDate( new Date() );
			else if( preference.getEmailFrequency() == EmailFrequency.NEVER )
				emailList.remove( email );
			else
				email.setScheduledDate( _getScheduleDate( users.get( email.getUserId() ), preference.getEmailFrequency() ) );
		}

		emailList = dataAccessor.createOrUpdateEmailList( emailList );
		*/

	}

	private Date _getScheduleDate( User user, EmailFrequency preference ) {

		// Date lastSentDate = user.getLastEmailedDate();
		Date lastSentDate = new Date();

		if( lastSentDate == null )
			return new Date();

		Integer days = null;
		switch( preference ) {
			case DAILY:
				days = 1;
				break;
			case WEEKLY:
				days = 7;
				break;
			case MONTHLY:
				days = 30;
				break;
			default:
				break;
		}

		Date date = new Date();
		if( date.getTime() > ( lastSentDate.getTime() + TimeUnit.DAYS.toMillis( days ) ) )
			return date;

		return new Date( lastSentDate.getTime() + TimeUnit.DAYS.toMillis( days ) );

	}

}