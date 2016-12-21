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
import com.pratilipi.data.client.VoteData;
import com.pratilipi.data.type.Email;
import com.pratilipi.data.type.UserAuthor;
import com.pratilipi.data.type.Vote;
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
	
	private static String _getContactEmail( Language language ) {
		return language == null || language == Language.ENGLISH ? 
				"contact@pratilipi.com" : language.name().toLowerCase() + "@pratilipi.com";
	}

	public static void sendEmail( Long emailId ) throws UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		Email email = dataAccessor.getEmail( emailId );

		if( email.getState() != EmailState.PENDING )
			return;

		UserData user = UserDataUtil.createUserData( dataAccessor.getUser( email.getUserId() ) );

		if( user.getEmail() == null ) {
			email.setState( EmailState.INVALID_EMAIL );
			email.setLastUpdated( new Date() );
			dataAccessor.createOrUpdateEmail( email );
			return;
		}


		Map<String, Object> dataModel = null;

		if( email.getType() == EmailType.PRATILIPI_PUBLISHED_AUTHOR 
				|| email.getType() == EmailType.PRATILIPI_PUBLISHED_FOLLOWER )
			dataModel = createDataModelForPratilipiPublishedEmail( email.getPrimaryContentIdLong() );

		else if( email.getType() == EmailType.AUTHOR_FOLLOW )
			dataModel = createDataModelForAuthorFollowEmail( email.getPrimaryContentId() );

		else if( email.getType() == EmailType.USER_PRATILIPI_REVIEW )
			dataModel = createDataModelForUserPratilipiReviewEmail( email.getPrimaryContentId() );

		else if( email.getType() == EmailType.COMMENT_REVIEW_REVIEWER 
					|| email.getType() == EmailType.COMMENT_REVIEW_AUTHOR )
			dataModel = createDataModelForCommentReviewEmail( email.getPrimaryContentIdLong() );

		else if( email.getType() == EmailType.VOTE_REVIEW_REVIEWER 
					|| email.getType() == EmailType.VOTE_REVIEW_AUTHOR )
			dataModel = createDataModelForVoteReviewEmail( email.getPrimaryContentId() );

		else if( email.getType() == EmailType.VOTE_COMMENT_REVIEW_COMMENTOR )
			dataModel = createDataModelForVoteCommentEmail( email.getPrimaryContentId() );


		// Defaulting to user's language
		if( ! dataModel.containsKey( "language" ) ) {
			dataModel.put( "language", user.getAuthor().getLanguage() );
			dataModel.put( "contact_email", _getContactEmail( user.getAuthor().getLanguage() ) );
		}

		EmailUtil.sendMail(
				user.getDisplayName(),
				user.getEmail(),
				email.getType(),
				dataModel );

		email.setState( EmailState.SENT );
		email.setLastUpdated( new Date() );

		dataAccessor.createOrUpdateEmail( email );

	}
	
	
	private static Map<String, Object> createDataModelForPratilipiPublishedEmail( Long pratilipiId )
			throws UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		PratilipiData pratilipi = PratilipiDataUtil.createPratilipiData( dataAccessor.getPratilipi( pratilipiId ) );

		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "pratilipi_title", pratilipi.getTitle() != null ? pratilipi.getTitle() : pratilipi.getTitleEn() );
		dataModel.put( "pratilipi_cover_image_url", pratilipi.getCoverImageUrl( 150 ) );
		dataModel.put( "pratilipi_listing_date", _getDateFormat( pratilipi.getListingDate() ) );
		dataModel.put( "pratilipi_summary", HtmlUtil.truncateText( pratilipi.getSummary(), 250 ) );
		dataModel.put( "pratilipi_page_url", _getDomainName( pratilipi.getLanguage() ) + pratilipi.getPageUrl() );
		dataModel.put( "author_name", pratilipi.getAuthor().getName() != null ? pratilipi.getAuthor().getName() : pratilipi.getAuthor().getNameEn() );
		dataModel.put( "author_page_url", _getDomainName( pratilipi.getLanguage() ) + pratilipi.getAuthor().getPageUrl() );
		dataModel.put( "language", pratilipi.getLanguage() );
		dataModel.put( "contact_email", _getContactEmail( pratilipi.getLanguage() ) );
		return dataModel;

	}

	private static Map<String, Object> createDataModelForAuthorFollowEmail( String userAuthorId ) 
			throws UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		UserAuthor userAuthor = dataAccessor.getUserAuthor( userAuthorId );
		AuthorData follower = AuthorDataUtil.createAuthorData( dataAccessor.getAuthorByUserId( userAuthor.getUserId() ) );

		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "follower_name", follower.getName() != null ?  follower.getName() : follower.getNameEn() );
		dataModel.put( "follower_page_url", _getDomainName( follower.getLanguage() ) + follower.getPageUrl() );
		dataModel.put( "follower_profile_image_url", _getDomainName( follower.getLanguage() ) + follower.getProfileImageUrl( 50 ) );
		if( follower.getFollowCount() > 0 )
			dataModel.put( "follower_followers_count", follower.getFollowCount() );

		return dataModel;

	}

	private static Map<String, Object> createDataModelForUserPratilipiReviewEmail( String userPratilipiId )
			throws UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		UserPratilipiData userPratilipi = UserPratilipiDataUtil.createUserPratilipiData( dataAccessor.getUserPratilipi( userPratilipiId ) );
		PratilipiData pratilipi = PratilipiDataUtil.createPratilipiData( dataAccessor.getPratilipi( userPratilipi.getPratilipiId() ) );

		UserData reviewer = UserDataUtil.createUserData( dataAccessor.getUser( userPratilipi.getUserId() ) );

		Map<String, Object> dataModel = new HashMap<>();

		dataModel.put( "pratilipi_title", pratilipi.getTitle() != null ? pratilipi.getTitle() : pratilipi.getTitleEn() );
		dataModel.put( "pratilipi_page_url", _getDomainName( pratilipi.getLanguage() ) + pratilipi.getPageUrl() );
		
		dataModel.put( "user_pratilipi_name", reviewer.getAuthor().getName() != null 
												? reviewer.getAuthor().getName() 
												: reviewer.getAuthor().getNameEn() );
		dataModel.put( "user_pratilipi_page_url", _getDomainName( pratilipi.getLanguage() ) + reviewer.getProfilePageUrl() );
		dataModel.put( "user_pratilipi_image_url", reviewer.getAuthor().getImageUrl( 100 ) );
		dataModel.put( "user_pratilipi_creation_date", _getDateFormat( userPratilipi.getReviewDate() ) );

		if( userPratilipi.getRating() != null )
			dataModel.put( "rating", userPratilipi.getRating() );

		if( userPratilipi.getReview() != null )
			dataModel.put( "user_pratilipi_review", HtmlUtil.truncateText( userPratilipi.getReview(), 250 ) );

		if( userPratilipi.getCommentCount() != null )
			dataModel.put( "user_pratilipi_comment_count", userPratilipi.getCommentCount().toString() );

		dataModel.put( "language", pratilipi.getLanguage() );
		dataModel.put( "contact_email", _getContactEmail( pratilipi.getLanguage() ) );

		return dataModel;

	}
	
	private static Map<String, Object> createDataModelForCommentReviewEmail( Long commentId ) 
			throws UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		CommentData comment = CommentDataUtil.createCommentData( dataAccessor.getComment( commentId ) );
		comment.setUser( UserDataUtil.createUserData( dataAccessor.getUser( comment.getUserId() ) ) );
		UserPratilipiData review = UserPratilipiDataUtil.createUserPratilipiData( dataAccessor.getUserPratilipi( comment.getParentId() ) );
		PratilipiData pratilipi = PratilipiDataUtil.createPratilipiData( dataAccessor.getPratilipi( review.getPratilipiId() ) );

		Map<String, Object> dataModel = new HashMap<>();

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

		dataModel.put( "language", pratilipi.getLanguage() );
		dataModel.put( "contact_email", _getContactEmail( pratilipi.getLanguage() ) );

		return dataModel;

	}
	
	private static Map<String, Object> createDataModelForVoteReviewEmail( String voteId ) 
			throws UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		Vote vote = dataAccessor.getVote( voteId );

		UserPratilipiData userPratilipi = UserPratilipiDataUtil.createUserPratilipiData( dataAccessor.getUserPratilipi( vote.getParentId() ) );
		PratilipiData pratilipi = PratilipiDataUtil.createPratilipiData( dataAccessor.getPratilipi( userPratilipi.getPratilipiId() ) );
		UserData voter = UserDataUtil.createUserData( dataAccessor.getUser( vote.getUserId() ) );

		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "pratilipi_title", pratilipi.getTitle() != null ? pratilipi.getTitle() : pratilipi.getTitleEn() );
		dataModel.put( "pratilipi_page_url", pratilipi.getPageUrl() );
		dataModel.put( "user_pratilipi_page_url", userPratilipi.getUser().getProfilePageUrl() );
		dataModel.put( "user_pratilipi_image_url", userPratilipi.getUser().getAuthor().getImageUrl( 64 ) );
		dataModel.put( "user_pratilipi_name", userPratilipi.getUser().getAuthor().getName() != null
												? userPratilipi.getUser().getAuthor().getName() 
												: userPratilipi.getUser().getAuthor().getNameEn() );
		dataModel.put( "user_pratilipi_creation_date", _getDateFormat( userPratilipi.getReviewDate() ) );
		dataModel.put( "user_pratilipi_review", HtmlUtil.toPlainText( userPratilipi.getReview() ) );

		if( userPratilipi.getRating() != null )
			dataModel.put( "rating", userPratilipi.getRating() );

		dataModel.put( "vote_name", voter.getAuthor().getName() != null
				? voter.getAuthor().getName()
				: voter.getAuthor().getNameEn() );

		return dataModel;

	}
	
	private static Map<String, Object> createDataModelForVoteCommentEmail( String voteId ) 
			throws UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		VoteData vote = VoteDataUtil.createVoteData( dataAccessor.getVote( voteId ) );
		CommentData comment = CommentDataUtil.createCommentData( dataAccessor.getComment( vote.getParentIdLong() ) );
		UserPratilipiData review = UserPratilipiDataUtil.createUserPratilipiData( dataAccessor.getUserPratilipi( comment.getParentId() ) );
		PratilipiData pratilipi = PratilipiDataUtil.createPratilipiData( dataAccessor.getPratilipi( review.getPratilipiId() ) );

		Map<String, Object> dataModel = new HashMap<>();

		dataModel.put( "vote_name", vote.getUser().getAuthor().getName() != null
									? vote.getUser().getAuthor().getName()
									: vote.getUser().getAuthor().getNameEn() );

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

		dataModel.put( "language", pratilipi.getLanguage() );
		dataModel.put( "contact_email", _getContactEmail( pratilipi.getLanguage() ) );

		return dataModel;

	}

}