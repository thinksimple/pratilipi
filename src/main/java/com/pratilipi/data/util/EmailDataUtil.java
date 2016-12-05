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
import com.pratilipi.common.util.HtmlUtil;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.client.AuthorData;
import com.pratilipi.data.client.PratilipiData;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Email;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.User;
import com.pratilipi.data.type.UserAuthor;
import com.pratilipi.email.EmailUtil;

public class EmailDataUtil {

	@SuppressWarnings("unused")
	private static final Logger logger =
			Logger.getLogger( EmailDataUtil.class.getName() );

	
	public static void sendEmail( Long emailId ) throws UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		Email email = dataAccessor.getEmail( emailId );
		if( email.getState() != EmailState.PENDING )
			return;

		
		EmailState emailState = null;
		if( dataAccessor.getUser( email.getUserId() ).getEmail() == null )
			emailState = EmailState.INVALID_EMAIL;
		else if( email.getType() == EmailType.PRATILIPI_PUBLISHED_AUTHOR_EMAIL )
			emailState = sendPratilipiPublisedEmail( email.getUserId(), email.getPrimaryContentIdLong(), email.getType() );
		else if( email.getType() == EmailType.PRATILIPI_PUBLISHED_FOLLOWER_EMAIL )
			emailState = sendPratilipiPublisedEmail( email.getUserId(), email.getPrimaryContentIdLong(), email.getType() );
		else if( email.getType() == EmailType.AUTHOR_FOLLOW_EMAIL )
			emailState = sendAuthorFollowEmail( email.getUserId(), email.getPrimaryContentId() );

		
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
		
		DateFormat dateFormat = new SimpleDateFormat( "dd MMM yyyy" );
		dateFormat.setTimeZone( TimeZone.getTimeZone( "IST" ) );

		String domain = "http://" + pratilipiData.getLanguage().getHostName();
		
		Map<String, String> dataModel = new HashMap<String, String>();
		dataModel.put( "pratilipi_title", pratilipiData.getTitle() != null ? pratilipiData.getTitle() : pratilipiData.getTitleEn() );
		dataModel.put( "pratilipi_cover_image_url", pratilipiData.getCoverImageUrl( 150 ) );
		dataModel.put( "pratilipi_listing_date", dateFormat.format( pratilipiData.getListingDate() ) );
		dataModel.put( "pratilipi_summary", HtmlUtil.truncateText( pratilipiData.getSummary(), 250 ) );
		dataModel.put( "pratilipi_page_url", domain + pratilipiData.getPageUrl() );
		dataModel.put( "author_name", authorData.getName() != null ? authorData.getName() : authorData.getNameEn() );
		dataModel.put( "author_page_url", domain + authorData.getPageUrl() );

		EmailUtil.sendMail(
				userData.getDisplayName(),
				user.getEmail(),
				type,
				dataModel );

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

		EmailUtil.sendMail(
				UserDataUtil.createUserData( followed ).getDisplayName(), 
				followed.getEmail(), 
				EmailType.AUTHOR_FOLLOW_EMAIL, 
//				followedAuthor.getLanguage() != null ? followedAuthor.getLanguage() : Language.ENGLISH,
				dataModel );
		
		return EmailState.SENT;

	}

}