package com.pratilipi.pagecontent.pratilipi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.server.FacebookApi;
import com.claymus.commons.server.FreeMarkerUtil;
import com.claymus.commons.server.SerializationUtil;
import com.claymus.commons.shared.Resource;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.transfer.User;
import com.claymus.pagecontent.PageContentProcessor;
import com.claymus.service.shared.data.UserData;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.commons.shared.PratilipiContentType;
import com.pratilipi.commons.shared.PratilipiState;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.commons.shared.UserReviewState;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Language;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.data.transfer.UserPratilipi;
import com.pratilipi.data.transfer.shared.AuthorData;
import com.pratilipi.pagecontent.author.AuthorContentHelper;
import com.pratilipi.service.shared.data.PratilipiData;

public class PratilipiContentProcessor extends PageContentProcessor<PratilipiContent> {

	
	private static final String DOMAIN = ClaymusHelper.getSystemProperty( "domain" );
	
	
	@Override
	public Resource[] getDependencies( PratilipiContent pratilipiContent, HttpServletRequest request ) {
		
		Pratilipi pratilipi = DataAccessorFactory
				.getDataAccessor( request )
				.getPratilipi( pratilipiContent.getId() );
		Author author = pratilipi.getAuthorId() == null ? 
							null : 
							DataAccessorFactory
								.getDataAccessor( request )
								.getAuthor( pratilipi.getAuthorId() );
		Language language = DataAccessorFactory
				.getDataAccessor( request )
				.getLanguage( pratilipi.getLanguageId() );
		com.pratilipi.data.transfer.shared.PratilipiData pratilipiData =
				PratilipiContentHelper.createPratilipiData( pratilipi, language, author, request );

		
		String ogFbAppId = FacebookApi.getAppId( request );
		String ogLocale = pratilipiData.getLanguage() == null ? 
								"hi_IN" : 
								pratilipiData.getLanguage().getNameEn().toLowerCase().substring( 0,2 ) + "_IN";
		String ogType = "book";
		String ogAuthor = author == null ? null : pratilipiData.getAuthor().getFullNameEn();
		String ogBooksIsbn = pratilipi.getId() + "";
		String ogUrl = "http://" + DOMAIN + 
							( pratilipiData.getPageUrlAlias() == null ? pratilipiData.getPageUrl() : pratilipiData.getPageUrlAlias() );
		String ogTitle = pratilipi.getTitle() + ( pratilipi.getTitleEn() == null ? "" : " / " + pratilipi.getTitleEn() );
		String ogImage = "http://" + DOMAIN + "/api/pratilipi/cover?pratilipiId=" + pratilipi.getId();
		if( ! ogImage.startsWith( "http:" ) )
			ogImage = "http:" + ogImage;
		String ogPublisher = null;
		if( pratilipiData.getLanguage() != null && pratilipiData.getLanguage().getNameEn().equals( "Tamil" ))
			ogPublisher = "https://www.facebook.com/pages/%E0%AE%AA%E0%AF%8D%E0%AE%B0%E0%AE%A4%E0%AE%BF%E0%AE%B2%E0%AE%BF%E0%AE%AA%E0%AE%BF/448203822022932";
		else if( pratilipiData.getLanguage() != null && pratilipiData.getLanguage().getNameEn().equals( "Gujarati" ))
			ogPublisher = "https://www.facebook.com/pratilipiGujarati";
		else
			ogPublisher = "https://www.facebook.com/Pratilipidotcom";
		String summarySubstr = pratilipi.getSummary();
		if( summarySubstr != null ){
			Pattern htmlPattern = Pattern.compile( "<[^>]+>" );
			Matcher matcher = htmlPattern.matcher( pratilipi.getSummary() );
			while( matcher.find() ){
				summarySubstr = summarySubstr.replace( matcher.group(), "" );
			}
		}
		String ogDescription = pratilipi.getType() == PratilipiType.BOOK ? summarySubstr : pratilipi.getTitle();
		
		final String fbOgTags = "<meta property='fb:app_id' content='" + ogFbAppId + "' />"
				+ "<meta property='og:locale' content='" + ogLocale + "' />"
				+ "<meta property='og:type' content='" + ogType + "' />"
				+ "<meta property='book:author' content='" + ogAuthor + "' />"
				+ "<meta property='book:isbn' content='" + ogBooksIsbn + "' />"
				+ "<meta property='og:url' content='" + ogUrl + "' />"
				+ "<meta property='og:title' content='" + ogTitle + "' />"
				+ "<meta property='og:image' content='" + ogImage + "' />"
				+ "<meta property='og:image:height' content='auto' />"
				+ "<meta property='og:image:width' content='auto' />"
				+ "<meta property='og:publisher' content='" + ogPublisher + "' />"
				+ "<meta property='og:description' content='" + ogDescription + "' />";
		
		
		return new Resource[] {

			new Resource() {
				@Override
				public String getTag() {
					return fbOgTags;
				}
			},

		};
		
	}

	@Override
	public String generateTitle( PratilipiContent pratilipiContent, HttpServletRequest request ) {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiContent.getId() );
		String pratilipiTitle = pratilipi.getTitle()
				+ ( pratilipi.getTitleEn() == null ? "" : " / " + pratilipi.getTitleEn() );
		
		Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
		if( author != null ) {
			AuthorData authorData = AuthorContentHelper.createAuthorData( author, null, request );
			String authorName = authorData.getName()
					+ ( authorData.getNameEn() == null ? "" : " / " + authorData.getNameEn() );
			pratilipiTitle = authorName + " » " + pratilipiTitle;
		}

		return pratilipiTitle;
	}
	
	@Override
	public String generateHtml( PratilipiContent pratilipiContent, HttpServletRequest request )
			throws InsufficientAccessException, UnexpectedServerException {

		PratilipiHelper pratilipiHelper = PratilipiHelper.get( request );
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		
		Long pratilipiId = pratilipiContent.getId();
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
		boolean showEditOption = PratilipiContentHelper
				.hasRequestAccessToUpdatePratilipiData( request, pratilipi );

		if( pratilipi.getState() != PratilipiState.PUBLISHED && !showEditOption )
			throw new InsufficientAccessException();
		
		
		UserPratilipi userPratilipi = null;
		if( pratilipiHelper.isUserLoggedIn() )
			userPratilipi = dataAccessor.getUserPratilipi( pratilipiHelper.getCurrentUserId(), pratilipiId );
		List<UserPratilipi> reviewList = dataAccessor.getUserPratilipiList( pratilipiId );
		
		Map<String, String> userIdNameMap = new HashMap<>();
		for( UserPratilipi review : reviewList ) {
			User user = dataAccessor.getUser( review.getUserId() );
			UserData userData = pratilipiHelper.createUserData( user );
			userIdNameMap.put( userData.getId().toString(), userData.getName() );
		}
		
		PratilipiData pratilipiData = pratilipiHelper.createPratilipiData(
				pratilipiId,
				PratilipiContentHelper.hasRequestAccessToReadPratilipiMetaData( request ) );
		

		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "timeZone", pratilipiHelper.getCurrentUserTimeZone() );
		dataModel.put( "userData", pratilipiHelper.createUserData( pratilipiHelper.getCurrentUser() ) );
		dataModel.put( "pratilipiData", pratilipiData );
		dataModel.put( "pratilipiDataEncodedStr", SerializationUtil.encode( pratilipiData ) );
		dataModel.put( "reviewList", reviewList );
		dataModel.put( "userIdNameMap", userIdNameMap );
		dataModel.put( "domain", ClaymusHelper.getSystemProperty( "domain" ) );
		dataModel.put( "showEditOptions", showEditOption );
		dataModel.put( "showWriterOption", showEditOption && pratilipiData.getContentType() != PratilipiContentType.IMAGE );
		dataModel.put( "showReviewedMessage",
				userPratilipi != null 
				&& userPratilipi.getReview() != null
				&& userPratilipi.getReviewState() != UserReviewState.NOT_SUBMITTED );
		dataModel.put( "showReviewOption",
				( userPratilipi == null || userPratilipi.getReview() == null || userPratilipi.getReviewState() == UserReviewState.NOT_SUBMITTED )
				&& PratilipiContentHelper.hasRequestAccessToAddPratilipiReview( request, pratilipi ) );
		dataModel.put( "showRatingOption", PratilipiContentHelper.hasRequestAccessToAddPratilipiReview( request, pratilipi ) );


		return FreeMarkerUtil.processTemplate( dataModel, getTemplateName() );
	}
	
}
