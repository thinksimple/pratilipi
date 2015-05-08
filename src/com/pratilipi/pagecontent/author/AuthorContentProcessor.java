package com.pratilipi.pagecontent.author;

import java.util.ArrayList;
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
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataListCursorTuple;
import com.claymus.pagecontent.PageContentProcessor;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.commons.shared.PratilipiFilter;
import com.pratilipi.commons.shared.PratilipiState;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Language;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.service.shared.data.AuthorData;
import com.pratilipi.service.shared.data.PratilipiData;

public class AuthorContentProcessor extends PageContentProcessor<AuthorContent> {

	private static final String DOMAIN = ClaymusHelper.getSystemProperty( "domain" );
	
	@Override
	public Resource[] getDependencies( AuthorContent authorContent, HttpServletRequest request ) {
		
		Author author = DataAccessorFactory
								.getDataAccessor( request )
								.getAuthor( authorContent.getId() );
		Language language = author.getLanguageId() == null ? 
								null :
								DataAccessorFactory
									.getDataAccessor( request )
									.getLanguage( author.getLanguageId() );
		com.pratilipi.data.transfer.shared.AuthorData authorData = 
									AuthorContentHelper.createAuthorData( author, language, request );

		
		String ogFbAppId = FacebookApi.getAppId( request );
		String ogLocale = authorData.getLanguage() == null ? 
								"hi_IN" : 
									authorData.getLanguage().getNameEn().toLowerCase().substring( 0,2 ) + "_IN";
		String ogType = "profile";
		String profileFirstName = authorData.getFirstNameEn() == null ? "" : authorData.getFirstNameEn();
		String profileLastName = authorData.getLastNameEn() == null ? "" : authorData.getLastNameEn();
		String ogUrl = "http://" + DOMAIN + authorData.getPageUrl();
		String ogTitle = authorData.getFullNameEn();
		String ogImage = authorData.getAuthorImageUrl();
		if( ! ogImage.startsWith( "http:" ) )
			ogImage = "http:" + ogImage;
		String ogPublisher = null;
		if( authorData.getLanguage() != null && authorData.getLanguage().getNameEn().equals( "Tamil" ))
			ogPublisher = "https://www.facebook.com/pages/%E0%AE%AA%E0%AF%8D%E0%AE%B0%E0%AE%A4%E0%AE%BF%E0%AE%B2%E0%AE%BF%E0%AE%AA%E0%AE%BF/448203822022932";
		else if( authorData.getLanguage() != null && authorData.getLanguage().getNameEn().equals( "Gujarati" ))
			ogPublisher = "https://www.facebook.com/pratilipiGujarati";
		else
			ogPublisher = "https://www.facebook.com/Pratilipidotcom";
		String summarySubstr = authorData.getSummary();
		if( summarySubstr != null ){
			Pattern htmlPattern = Pattern.compile( "<[^>]+>" );
			Matcher matcher = htmlPattern.matcher( authorData.getSummary() );
			while( matcher.find() ){
				summarySubstr = summarySubstr.replace( matcher.group(), "" );
			}
			if( summarySubstr.length() > 150 )
				summarySubstr = summarySubstr.substring( 0, 150 );
		}
		String ogDescription = summarySubstr;
		
		final String fbOgTags = "<meta property='fb:app_id' content='" + ogFbAppId + "' />"
				+ "<meta property='og:locale' content='" + ogLocale + "' />"
				+ "<meta property='og:type' content='" + ogType + "' />"
				+ "<meta property='profile:first_name' content='" + profileFirstName + "' />"
				+ "<meta property='profile:last_name' content='" + profileLastName + "' />"
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
	public String generateTitle( AuthorContent authorContent, HttpServletRequest request ) {
		AuthorData authorData = PratilipiHelper.get( request ).createAuthorData( authorContent.getId() );
		if( authorData.getFullName() != null )
			return authorData.getFullName() + " (" + authorData.getFullNameEn() + ")";
		else
			return authorData.getFullNameEn();
	}
	
	@Override
	public String generateHtml(
			AuthorContent authorContent,
			HttpServletRequest request ) throws UnexpectedServerException {
		
		PratilipiHelper pratilipiHelper = PratilipiHelper.get( request );
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );

		Long authorId = authorContent.getId();
		Author author = dataAccessor.getAuthor( authorId );

		boolean showEditOption = AuthorContentHelper
				.hasRequestAccessToUpdateAuthorData( request, author );

		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		pratilipiFilter.setAuthorId( authorId );

		pratilipiFilter.setState( PratilipiState.DRAFTED );
		DataListCursorTuple<Pratilipi> draftedPratilipiListCursorTuple = showEditOption
				? (DataListCursorTuple<Pratilipi>) dataAccessor.getPratilipiList( pratilipiFilter, null, 1000 )
				: new DataListCursorTuple<Pratilipi>( new ArrayList<Pratilipi>(0), null );

		pratilipiFilter.setState( PratilipiState.SUBMITTED );
		DataListCursorTuple<Pratilipi> submittedPratilipiListCursorTuple = showEditOption
				? (DataListCursorTuple<Pratilipi>) dataAccessor.getPratilipiList( pratilipiFilter, null, 1000 )
				: new DataListCursorTuple<Pratilipi>( new ArrayList<Pratilipi>(0), null );
				
		pratilipiFilter.setState( PratilipiState.PUBLISHED );

		pratilipiFilter.setType( PratilipiType.BOOK );
		DataListCursorTuple<Pratilipi> bookListCursorTuple =
				dataAccessor.getPratilipiList( pratilipiFilter, null, 1000 );  

		pratilipiFilter.setType( PratilipiType.POEM );
		DataListCursorTuple<Pratilipi> poemListCursorTuple =
				dataAccessor.getPratilipiList( pratilipiFilter, null, 1000 );  

		pratilipiFilter.setType( PratilipiType.STORY );
		DataListCursorTuple<Pratilipi> storyListCursorTuple =
				dataAccessor.getPratilipiList( pratilipiFilter, null, 1000 );  

		pratilipiFilter.setType( PratilipiType.ARTICLE );
		DataListCursorTuple<Pratilipi> articleListCursorTuple =
				dataAccessor.getPratilipiList( pratilipiFilter, null, 1000 );
		
		pratilipiFilter.setType( PratilipiType.MAGAZINE );
		DataListCursorTuple<Pratilipi> magazineListCursorTuple = 
				dataAccessor.getPratilipiList( pratilipiFilter, null, 1000 );

		
		AuthorData authorData = pratilipiHelper.createAuthorData( author.getId() );

		List<PratilipiData> draftedPratilipiDataList =
				pratilipiHelper.createPratilipiDataList(
						draftedPratilipiListCursorTuple.getDataList(), false, false, false );
		
		List<PratilipiData> submittedPratilipiDataList =
				pratilipiHelper.createPratilipiDataList(
						submittedPratilipiListCursorTuple.getDataList(), false, false, false );
		
		List<PratilipiData> bookDataList =
				pratilipiHelper.createPratilipiDataList(
						bookListCursorTuple.getDataList(), false, false, false );
		
		List<PratilipiData> poemDataList =
				pratilipiHelper.createPratilipiDataList(
						poemListCursorTuple.getDataList(), false, false, false );
		
		List<PratilipiData> storyDataList =
				pratilipiHelper.createPratilipiDataList(
						storyListCursorTuple.getDataList(), false, false, false );

		List<PratilipiData> articleDataList =
				pratilipiHelper.createPratilipiDataList(
						articleListCursorTuple.getDataList(), false, false, false );
		
		List<PratilipiData> magazineDataList =
				pratilipiHelper.createPratilipiDataList(
						magazineListCursorTuple.getDataList(), false, false, false );
		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "timeZone", pratilipiHelper.getCurrentUserTimeZone() );
		dataModel.put( "authorData", authorData );
		dataModel.put( "authorDataEncodedStr", SerializationUtil.encode( authorData ) );
		dataModel.put( "draftedPratilipiDataList", draftedPratilipiDataList );
		dataModel.put( "submittedPratilipiDataList", submittedPratilipiDataList );
		dataModel.put( "bookDataList", bookDataList );
		dataModel.put( "poemDataList", poemDataList );
		dataModel.put( "storyDataList", storyDataList );
		dataModel.put( "articleDataList", articleDataList );
		dataModel.put( "magazineDataList", magazineDataList );
		dataModel.put( "domain", ClaymusHelper.getSystemProperty( "domain" ) );
		dataModel.put( "showEditOption", showEditOption );
		
		
		// Processing template
		return FreeMarkerUtil.processTemplate( dataModel, getTemplateName() );
	}
	
}
