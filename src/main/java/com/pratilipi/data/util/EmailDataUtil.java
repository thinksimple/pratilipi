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
import com.pratilipi.common.util.HtmlUtil;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.client.PratilipiData;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.User;
import com.pratilipi.email.EmailUtil;

public class EmailDataUtil {

	@SuppressWarnings("unused")
	private static final Logger logger =
			Logger.getLogger( EmailDataUtil.class.getName() );
	
	public static EmailState sendPratilipiPublisedAuthorEmail( Long pratilipiId, Long userId, EmailType emailType ) 
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
							emailType,
							pratilipi.getLanguage(), 
							dataModel );

		return EmailState.SENT;

	}

	public static EmailState sendPratilipiPublishedFollowerEmail( Long pratilipiId, Long userId, EmailType emailType ) 
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
								emailType, 
								pratilipi.getLanguage(),
								dataModel );

		return EmailState.SENT;

	}

}