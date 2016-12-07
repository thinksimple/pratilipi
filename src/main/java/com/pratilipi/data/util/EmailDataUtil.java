package com.pratilipi.data.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Logger;

import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.EmailState;
import com.pratilipi.common.type.EmailType;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.util.HtmlUtil;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.client.AuthorData;
import com.pratilipi.data.client.CommentData;
import com.pratilipi.data.client.PratilipiData;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.client.UserPratilipiData;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Comment;
import com.pratilipi.data.type.Email;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.User;
import com.pratilipi.data.type.UserAuthor;
import com.pratilipi.data.type.UserPratilipi;
import com.pratilipi.email.EmailUtil;

public class EmailDataUtil {

	@SuppressWarnings("unused")
	private static final Logger logger =
			Logger.getLogger( EmailDataUtil.class.getName() );


	private static String _getDomainName( Language language ) {
		return "http://" + language.getHostName();
	}

	private static String _getDateFormat( Date date ) {

		DateFormat dateFormat = new SimpleDateFormat( "dd MMM yyyy" );
		dateFormat.setTimeZone( TimeZone.getTimeZone( "IST" ) );

		return dateFormat.format( date );

	}

	private static void _sendMail( 
			String recipientName, String recipientEmail, EmailType emailType, Language language, Map<String, String> dataModel ) 
			throws UnexpectedServerException {

		dataModel.put( "language", language.toString() );
		dataModel.put( "contact_email", language == null || language == Language.ENGLISH ? 
								"contact@pratilipi.com" : language.toString().toLowerCase() + "@pratilipi.com" );

		EmailUtil.sendMail(
				recipientName,
				recipientEmail,
				emailType,
				dataModel );

	}

	
	public static void sendEmail( Long emailId ) throws UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		Email email = dataAccessor.getEmail( emailId );
		if( email.getState() != EmailState.PENDING )
			return;

		
		EmailState emailState = null;
		if( dataAccessor.getUser( email.getUserId() ).getEmail() == null )
			emailState = EmailState.INVALID_EMAIL;
		else if( email.getType() == EmailType.PRATILIPI_PUBLISHED_AUTHOR )
			emailState = sendPratilipiPublisedEmail( email.getUserId(), email.getPrimaryContentIdLong(), email.getType() );
		else if( email.getType() == EmailType.PRATILIPI_PUBLISHED_FOLLOWER )
			emailState = sendPratilipiPublisedEmail( email.getUserId(), email.getPrimaryContentIdLong(), email.getType() );
		else if( email.getType() == EmailType.AUTHOR_FOLLOW )
			emailState = sendAuthorFollowEmail( email.getUserId(), email.getPrimaryContentId() );
		else if( email.getType() == EmailType.USER_PRATILIPI_REVIEW )
			emailState = sendUserPratilipiReviewEmail( email.getUserId(), email.getPrimaryContentId() );
		else if( email.getType() == EmailType.COMMENT_ON_REVIEW_REVIEWER )
			emailState = sendCommentAddedEmail( email.getUserId(), email.getPrimaryContentIdLong(), email.getType() );
		else if( email.getType() == EmailType.COMMENT_ON_REVIEW_AUTHOR )
			emailState = sendCommentAddedEmail( email.getUserId(), email.getPrimaryContentIdLong(), email.getType() );

		
		email.setState( emailState );
		email.setLastUpdated( new Date() );
		dataAccessor.createOrUpdateEmail( email );

	}
	
	
	private static EmailState sendPratilipiPublisedEmail( Long userId, Long pratilipiId, EmailType type )
			throws UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		User user = dataAccessor.getUser( userId );
		UserData userData = UserDataUtil.createUserData( user );
		
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
		Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
		PratilipiData pratilipiData = PratilipiDataUtil.createPratilipiData( pratilipi, author );
		AuthorData authorData = pratilipiData.getAuthor();
		
		String domain = "http://" + pratilipiData.getLanguage().getHostName();
		
		Map<String, String> dataModel = new HashMap<String, String>();
		dataModel.put( "pratilipi_title", pratilipiData.getTitle() != null ? pratilipiData.getTitle() : pratilipiData.getTitleEn() );
		dataModel.put( "pratilipi_cover_image_url", pratilipiData.getCoverImageUrl( 150 ) );
		dataModel.put( "pratilipi_listing_date", _getDateFormat( pratilipiData.getListingDate() ) );
		dataModel.put( "pratilipi_summary", HtmlUtil.truncateText( pratilipiData.getSummary(), 250 ) );
		dataModel.put( "pratilipi_page_url", domain + pratilipiData.getPageUrl() );
		dataModel.put( "author_name", authorData.getName() != null ? authorData.getName() : authorData.getNameEn() );
		dataModel.put( "author_page_url", domain + authorData.getPageUrl() );

		_sendMail( userData.getDisplayName(), user.getEmail(), type, pratilipiData.getLanguage(), dataModel );

		return EmailState.SENT;

	}
	
	private static EmailState sendAuthorFollowEmail( Long userId, String userAuthorId ) 
			throws UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		UserAuthor userAuthor = dataAccessor.getUserAuthor( userAuthorId );
		
		User followed = dataAccessor.getUser( userId );
		Author followedAuthor = dataAccessor.getAuthor( userAuthor.getAuthorId() );

		Author followerAuthor = dataAccessor.getAuthorByUserId( userAuthor.getUserId() );
		AuthorData followerAuthorData = AuthorDataUtil.createAuthorData( followerAuthor );
		
		String domain = "http://" + followerAuthorData.getLanguage().getHostName();
		
		Map<String, String> dataModel = new HashMap<String, String>();
		dataModel.put( "follower_name", followerAuthorData.getName() != null ?  followerAuthorData.getName() : followerAuthorData.getNameEn() );
		dataModel.put( "follower_page_url", domain + followerAuthorData.getPageUrl() );
		dataModel.put( "follower_profile_image_url", domain + followerAuthorData.getProfileImageUrl( 50 ) );
		if( followerAuthorData.getFollowCount() > 0 )
			dataModel.put( "follower_followers_count", followerAuthorData.getFollowCount().toString() );

		_sendMail( UserDataUtil.createUserData( followed ).getDisplayName(), followed.getEmail(), EmailType.AUTHOR_FOLLOW, followedAuthor.getLanguage() != null ? followedAuthor.getLanguage() : Language.ENGLISH, dataModel );

		return EmailState.SENT;

	}

	private static EmailState sendUserPratilipiReviewEmail( Long userId, String userPratilipiId )
			throws UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		UserPratilipiData userPratilipiData = UserPratilipiDataUtil.createUserPratilipiData( dataAccessor.getUserPratilipi( userPratilipiId ) );
		PratilipiData pratilipiData = PratilipiDataUtil.createPratilipiData( dataAccessor.getPratilipi( userPratilipiData.getPratilipiId() ) );

		UserData reviewerUserData = UserDataUtil.createUserData( dataAccessor.getUser( userPratilipiData.getUserId() ) );
		UserData authorUserData = UserDataUtil.createUserData( dataAccessor.getUser( userId ) );

		String domain = "http://" + pratilipiData.getLanguage().getHostName();

		Map<String, String> dataModel = new HashMap<String, String>();

		dataModel.put( "pratilipi_title", pratilipiData.getTitle() != null ? pratilipiData.getTitle() : pratilipiData.getTitleEn() );
		dataModel.put( "pratilipi_page_url", domain + pratilipiData.getPageUrl() );
		
		dataModel.put( "user_pratilipi_name", reviewerUserData.getAuthor().getName() != null 
												? reviewerUserData.getAuthor().getName() 
												: reviewerUserData.getAuthor().getNameEn() );
		dataModel.put( "user_pratilipi_page_url", domain + reviewerUserData.getProfilePageUrl() );
		dataModel.put( "user_pratilipi_image_url", reviewerUserData.getAuthor().getImageUrl( 100 ) );
		dataModel.put( "user_pratilipi_creation_date", _getDateFormat( userPratilipiData.getReviewDate() ) );

		if( userPratilipiData.getRating() != null )
			dataModel.put( "rating", userPratilipiData.getRating().toString() );
		
		if( userPratilipiData.getReview() != null )
			dataModel.put( "user_pratilipi_review", HtmlUtil.truncateText( userPratilipiData.getReview(), 250 ) );
		
		if( userPratilipiData.getCommentCount() != null )
			dataModel.put( "user_pratilipi_comment_count", userPratilipiData.getCommentCount().toString() );


		_sendMail( authorUserData.getDisplayName(), 
					authorUserData.getEmail(), 
					EmailType.USER_PRATILIPI_REVIEW, 
					pratilipiData.getLanguage() != null ? pratilipiData.getLanguage() : Language.ENGLISH, 
					dataModel );


		return EmailState.SENT;

	}
	
	private static EmailState sendCommentAddedEmail( Long userId, Long commentId, EmailType emailType ) 
			throws UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		CommentData comment = CommentDataUtil.createCommentData( dataAccessor.getComment( commentId ) );
		UserPratilipiData review = UserPratilipiDataUtil.createUserPratilipiData( dataAccessor.getUserPratilipi( comment.getParentId() ) );
		PratilipiData pratilipi = PratilipiDataUtil.createPratilipiData( dataAccessor.getPratilipi( review.getPratilipiId() ) );

		UserData user = UserDataUtil.createUserData( dataAccessor.getUser( userId ) ); 


		Map<String, String> dataModel = new HashMap<String, String>();

		dataModel.put( "pratilipi_title", pratilipi.getTitle() != null ? pratilipi.getTitle() : pratilipi.getTitleEn() );
		dataModel.put( "pratilipi_page_url", _getDomainName( pratilipi.getLanguage() ) + pratilipi.getPageUrl() );

		dataModel.put( "review_name", review.getUser().getAuthor().getName() != null ?
								review.getUser().getAuthor().getName() : review.getUser().getAuthor().getNameEn() );
		dataModel.put( "review_page_url", _getDomainName( review.getUser().getAuthor().getLanguage() ) + review.getUser().getProfilePageUrl() );
		dataModel.put( "review_image_url", review.getUser().getAuthor().getImageUrl( 64 ) );
		dataModel.put( "review_date", _getDateFormat( review.getReviewDate() ) );
		dataModel.put( "review_review", HtmlUtil.truncateText( review.getReview(), 250 ) );

		dataModel.put( "comment_name", comment.getUser().getAuthor().getName() != null 
										? comment.getUser().getAuthor().getName()
										: comment.getUser().getAuthor().getNameEn() );
		dataModel.put( "comment_page_url", _getDomainName( comment.getUser().getAuthor().getLanguage() ) + comment.getUser().getProfilePageUrl() );
		dataModel.put( "comment_image_url", comment.getUser().getAuthor().getImageUrl( 50 ) );
		dataModel.put( "comment_date", _getDateFormat( comment.getCreationDate() ) );
		dataModel.put( "comment_content", HtmlUtil.truncateText( comment.getContent(), 200 ) );

		_sendMail( user.getDisplayName(), 
				user.getEmail(), 
				emailType, 
				pratilipi.getLanguage() != null ? pratilipi.getLanguage() : Language.ENGLISH, 
				dataModel );

		return EmailState.SENT;

	}

}