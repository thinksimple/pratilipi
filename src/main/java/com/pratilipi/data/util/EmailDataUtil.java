package com.pratilipi.data.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.pratilipi.data.client.PratilipiData;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.User;
import com.pratilipi.email.EmailUtil;

public class EmailDataUtil {

	@SuppressWarnings("unused")
	private static final Logger logger =
			Logger.getLogger( EmailDataUtil.class.getName() );
	
	public static EmailState sendPratilipiPublisedAuthorEmail( Long pratilipiId, Long userId ) 
			throws UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = dataAccessor.getUser( userId );

		if( user.getEmail() == null )
			return EmailState.INVALID_EMAIL;

		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
		Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
		PratilipiData pratilipiData = PratilipiDataUtil.createPratilipiData( pratilipi, author );

		DateFormat dateFormat = new SimpleDateFormat( "dd MMM yyyy" );
		dateFormat.setTimeZone( TimeZone.getTimeZone( "IST" ) );

		Map<String, String> dataModel = new HashMap<String, String>();
		dataModel.put( "pratilipi_title", pratilipiData.getTitle() != null ? 
					pratilipiData.getTitle() : pratilipiData.getTitleEn() );
		dataModel.put( "pratilipi_cover_image_url", PratilipiDataUtil.createPratilipiCoverUrl( pratilipi, 150 ) );
		dataModel.put( "pratilipi_listing_date", dateFormat.format( pratilipiData.getListingDate() ) );
		dataModel.put( "pratilipi_summary", HtmlUtil.truncateText( pratilipiData.getSummary(), 250 ) );
		dataModel.put( "pratilipi_page_url", "http://" + pratilipiData.getLanguage().getHostName() + pratilipiData.getPageUrl() );
		dataModel.put( "author_name", pratilipiData.getAuthor().getName() != null ? 
					pratilipiData.getAuthor().getName() : pratilipiData.getAuthor().getNameEn() );
		dataModel.put( "author_page_url", "http://" + author.getLanguage().getHostName() + pratilipiData.getAuthor().getPageUrl() );


		EmailUtil.sendMail( user.getEmail(),
							UserDataUtil.createUserData( user ).getDisplayName(),
							EmailType.PRATILIPI_PUBLISHED_AUTHOR_EMAIL,
							pratilipi.getLanguage(), 
							dataModel );

		return EmailState.SENT;

	}

	public static EmailState sendPratilipiPublishedFollowerEmail( Long pratilipiId, Long userId ) 
			throws UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = dataAccessor.getUser( userId );

		if( user.getEmail() == null )
			return EmailState.INVALID_EMAIL;

		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
		Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
		PratilipiData pratilipiData = PratilipiDataUtil.createPratilipiData( pratilipi, author );

		DateFormat dateFormat = new SimpleDateFormat( "dd MMM yyyy" );
		dateFormat.setTimeZone( TimeZone.getTimeZone( "IST" ) );

		Map<String, String> dataModel = new HashMap<String, String>();
		dataModel.put( "pratilipi_title", pratilipiData.getTitle() != null ? 
					pratilipiData.getTitle() : pratilipiData.getTitleEn() );
		dataModel.put( "pratilipi_cover_image_url", PratilipiDataUtil.createPratilipiCoverUrl( pratilipi, 150 ) );
		dataModel.put( "pratilipi_listing_date", dateFormat.format( pratilipiData.getListingDate() ) );
		dataModel.put( "pratilipi_summary", HtmlUtil.truncateText( pratilipiData.getSummary(), 250 ) );
		dataModel.put( "pratilipi_page_url", "http://" + pratilipiData.getLanguage().getHostName() + pratilipiData.getPageUrl() );
		dataModel.put( "author_name", pratilipiData.getAuthor().getName() != null ? 
					pratilipiData.getAuthor().getName() : pratilipiData.getAuthor().getNameEn() );
		dataModel.put( "author_page_url", "http://" + author.getLanguage().getHostName() + pratilipiData.getAuthor().getPageUrl() );


		EmailUtil.sendMail( user.getEmail(), 
								UserDataUtil.createUserData( user ).getDisplayName(), 
								EmailType.PRATILIPI_PUBLISHED_FOLLOWER_EMAIL, 
								pratilipi.getLanguage(),
								dataModel );

		return EmailState.SENT;

	}
	
	public static EmailState sendAuthorFollowEmail( String userAuthorId, Long userId ) 
			throws UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = dataAccessor.getUser( userId );

		if( user.getEmail() == null )
			return EmailState.INVALID_EMAIL;

		Author followed = dataAccessor.getAuthor( Long.parseLong( userAuthorId.substring( '-' ) + 1 ) );
		Author follower = dataAccessor.getAuthorByUserId( Long.parseLong( userAuthorId.substring( 0 , userAuthorId.indexOf( '-' ) ) ) );
		AuthorData followerAuthorData = AuthorDataUtil.createAuthorData( follower );

		Map<String, String> dataModel = new HashMap<String, String>();
		dataModel.put( "follower_name", followerAuthorData.getName() != null ? 
				followerAuthorData.getName() : followerAuthorData.getNameEn() );
		dataModel.put( "follower_page_url", followerAuthorData.getPageUrl() );
		dataModel.put( "follower_profile_image_url", followerAuthorData.getProfileImageUrl( 50 ) );

		if( followerAuthorData.getFollowCount() > 0 )
			dataModel.put( "follower_followers_count", followerAuthorData.getFollowCount().toString() );

		EmailUtil.sendMail( user.getEmail(), 
				UserDataUtil.createUserData( user ).getDisplayName(), 
				EmailType.AUTHOR_FOLLOW_EMAIL, 
				followed.getLanguage() != null ? followed.getLanguage() : Language.ENGLISH,
				dataModel );
		
		
		return EmailState.SENT;

	}

}