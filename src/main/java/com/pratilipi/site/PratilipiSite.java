package com.pratilipi.site;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

import com.google.gson.Gson;
import com.pratilipi.api.impl.blogpost.shared.GenericBlogPostResponse;
import com.pratilipi.api.impl.event.shared.GenericEventResponse;
import com.pratilipi.api.impl.pratilipi.shared.GenericPratilipiResponse;
import com.pratilipi.api.impl.pratilipi.shared.GetPratilipiListResponse;
import com.pratilipi.api.impl.user.shared.GenericUserResponse;
import com.pratilipi.api.impl.userpratilipi.shared.GenericReviewResponse;
import com.pratilipi.api.impl.userpratilipi.shared.GenericUserPratilipiResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.BlogPostState;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PageType;
import com.pratilipi.common.type.PratilipiContentType;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.type.PratilipiType;
import com.pratilipi.common.type.RequestParameter;
import com.pratilipi.common.type.Website;
import com.pratilipi.common.util.AuthorFilter;
import com.pratilipi.common.util.BlogPostFilter;
import com.pratilipi.common.util.FacebookApi;
import com.pratilipi.common.util.FreeMarkerUtil;
import com.pratilipi.common.util.PratilipiFilter;
import com.pratilipi.common.util.ThirdPartyResource;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.client.AuthorData;
import com.pratilipi.data.client.BlogPostData;
import com.pratilipi.data.client.EventData;
import com.pratilipi.data.client.PratilipiData;
import com.pratilipi.data.client.UserAuthorData;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.client.UserPratilipiData;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Blog;
import com.pratilipi.data.type.BlogPost;
import com.pratilipi.data.type.Event;
import com.pratilipi.data.type.Navigation;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.util.AuthorDataUtil;
import com.pratilipi.data.util.BlogPostDataUtil;
import com.pratilipi.data.util.EventDataUtil;
import com.pratilipi.data.util.PratilipiDataUtil;
import com.pratilipi.data.util.UserAuthorDataUtil;
import com.pratilipi.data.util.UserDataUtil;
import com.pratilipi.data.util.UserPratilipiDataUtil;
import com.pratilipi.filter.AccessTokenFilter;
import com.pratilipi.filter.UxModeFilter;
import com.pratilipi.i18n.I18n;

@SuppressWarnings("serial")
public class PratilipiSite extends HttpServlet {
	
	private static final Logger logger =
			Logger.getLogger( PratilipiSite.class.getName() );

	private static final String templateFilePrefix = "com/pratilipi/site/page/";
	private static final String dataFilePrefix = "page/data/";
	
	
	public void doGet( HttpServletRequest request, HttpServletResponse response )
			throws IOException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		// Page Entity
		String uri = request.getRequestURI();
		Page page = dataAccessor.getPage( uri );

		// BasicMode
		boolean basicMode = UxModeFilter.isBasicMode();
		
		// Language
		Language displayLanguage = UxModeFilter.getDisplayLanguage();
		Language filterLanguage = UxModeFilter.getFilterLanguage();

		// Navigation List
		List<Navigation> navigationList = dataAccessor.getNavigationList(
				filterLanguage == null ? Language.ENGLISH : filterLanguage
		);

		
		// Common resource list
		List<String> resourceList = new LinkedList<>();
		if( basicMode ) {
			resourceList.add( ThirdPartyResource.JQUERY.getTag() );
			resourceList.add( ThirdPartyResource.BOOTSTRAP.getTag() );
		} else {
			resourceList.add( ThirdPartyResource.JQUERY.getTag() );
			resourceList.add( ThirdPartyResource.BOOTSTRAP.getTag() );
			resourceList.add( ThirdPartyResource.FONT_AWESOME.getTag() );
			resourceList.add( ThirdPartyResource.TINYMCE.getTag() );
			resourceList.add( ThirdPartyResource.POLYMER.getTag() );
			resourceList.add( ThirdPartyResource.POLYMER_IRON_AJAX.getTag() );
			resourceList.add( ThirdPartyResource.POLYMER_IRON_ICONS.getTag() );
			resourceList.add( ThirdPartyResource.POLYMER_IRON_OVERLAY_BEHAVIOR.getTag() );
			resourceList.add( ThirdPartyResource.POLYMER_IRON_RESIZABLE_BEHAVIOR.getTag() );
			resourceList.add( ThirdPartyResource.POLYMER_PAPER_CARD.getTag() );
			resourceList.add( ThirdPartyResource.POLYMER_PAPER_DROPDOWN_MENU.getTag() );
			resourceList.add( ThirdPartyResource.POLYMER_PAPER_ICON_BUTTON.getTag() );
			resourceList.add( ThirdPartyResource.POLYMER_PAPER_INPUT.getTag() );
			resourceList.add( ThirdPartyResource.POLYMER_PAPER_MENU.getTag() );
			resourceList.add( ThirdPartyResource.POLYMER_PAPER_MENU_BUTTON.getTag() );
			resourceList.add( ThirdPartyResource.POLYMER_PAPER_ITEM.getTag() );
			resourceList.add( ThirdPartyResource.POLYMER_PAPER_SPINNER.getTag() );
		}

		
		// Data Model for FreeMarker
		Map<String, Object> dataModel = null;
		String templateName = null;
		
		try {
			
			if( uri.equals( "/" ) ) {
				dataModel = createDataModelForHomePage( basicMode, filterLanguage );
				templateName = templateFilePrefix + ( basicMode ? "HomeBasic.ftl" : "Home.ftl" );
			
			} else if( uri.equals( "/library" ) ) {
				dataModel = createDataModelForLibraryPage( basicMode, filterLanguage );
				templateName = templateFilePrefix + ( basicMode ? "LibraryBasic.ftl" : "Library.ftl" );

			} else if( basicMode && uri.equals( "/account" ) ) { // BasicMode only
				dataModel = new HashMap<String, Object>();
				dataModel.put( "title", "My Account" );
				templateName = templateFilePrefix + "AccountBasic.ftl";
			
			} else if( basicMode && uri.equals( "/navigation" ) ) { // BasicMode only
				dataModel = new HashMap<String, Object>();
				dataModel.put( "title", "Menu" );
				dataModel.put( "navigationList", navigationList );
				templateName = templateFilePrefix + "NavigationBasic.ftl";
			
			} else if( basicMode && uri.equals( "/updatepassword" ) ) { // BasicMode only
				dataModel = new HashMap<String, Object>();
				dataModel.put( "title", "Update Password" );
				templateName = templateFilePrefix + "PasswordUpdateBasic.ftl";
				
			} else if( page != null && page.getType() == PageType.PRATILIPI ) {
				resourceList.addAll( createFbOpenGraphTags( page.getPrimaryContentId() ) );
				dataModel = createDataModelForPratilipiPage( page.getPrimaryContentId(), basicMode, request );
				templateName = templateFilePrefix + ( basicMode ? "PratilipiBasic.ftl" : "Pratilipi.ftl" );
				
			} else if( page != null && page.getType() == PageType.AUTHOR ) {
				dataModel = createDataModelForAuthorPage( page.getPrimaryContentId(), basicMode );
				templateName = templateFilePrefix + ( basicMode ? "AuthorBasic.ftl" : "Author.ftl" );
			
			} else if( page != null && page.getType() == PageType.EVENT ) {
				dataModel = createDataModelForEventPage( page.getPrimaryContentId(), basicMode );
				templateName = templateFilePrefix + ( basicMode ? "EventBasic.ftl" : "Event.ftl" );
			
			} else if( page != null && page.getType() == PageType.BLOG ) {
				dataModel = createDataModelForBlogPage( page.getPrimaryContentId(), filterLanguage, basicMode );
				templateName = templateFilePrefix + ( basicMode ? "BlogPostListBasic.ftl" : "BlogPostList.ftl" );
			
			} else if( page != null && page.getType() == PageType.BLOG_POST ) {
				dataModel = createDataModelForBlogPostPage( page.getPrimaryContentId(), basicMode );
				templateName = templateFilePrefix + ( basicMode ? "BlogPostBasic.ftl" : "BlogPost.ftl" );
			
			} else if( uri.equals( "/pratilipireader" ) ) {
				if( !basicMode ) {
					resourceList.add( ThirdPartyResource.POLYMER_IRON_COLLAPSE.getTag() );
					resourceList.add( ThirdPartyResource.POLYMER_IRON_A11Y_KEYS.getTag() );
					resourceList.add( ThirdPartyResource.POLYMER_PAPER_FAB.getTag() );
					resourceList.add( ThirdPartyResource.POLYMER_PAPER_SLIDER.getTag() );
				}
				dataModel = createDataModelForReadPage( Long.parseLong( request.getParameter( "id" ) ), 1, basicMode );
				templateName = templateFilePrefix + ( basicMode ? "ReadBasic.ftl" : "Read.ftl" );
			
			} else if( uri.equals( "/search" ) ) {
				dataModel = createDataModelForSearchPage( basicMode, filterLanguage, request );
				templateName = templateFilePrefix + ( basicMode ? "SearchBasic.ftl" : "Search.ftl" );
				
			
			// Master website specific links
			
			} else if( filterLanguage == null && uri.equals( "/books" ) ) {
				dataModel = createDataModelForListPage( PratilipiType.BOOK, basicMode, displayLanguage, filterLanguage, request );
				templateName = templateFilePrefix + ( basicMode ? "ListBasic.ftl" : "List.ftl" );
				
			} else if( filterLanguage == null && uri.equals( "/stories" ) ) {
				dataModel = createDataModelForListPage( PratilipiType.STORY, basicMode, displayLanguage, filterLanguage, request );
				templateName = templateFilePrefix + ( basicMode ? "ListBasic.ftl" : "List.ftl" );
			
			} else if( filterLanguage == null && uri.equals( "/poems" ) ) {
				dataModel = createDataModelForListPage( PratilipiType.POEM, basicMode, displayLanguage, filterLanguage, request );
				templateName = templateFilePrefix + ( basicMode ? "ListBasic.ftl" : "List.ftl" );
				
			} else if( filterLanguage == null && uri.equals( "/articles" ) ) {
				dataModel = createDataModelForListPage( PratilipiType.ARTICLE, basicMode, displayLanguage, filterLanguage, request );
				templateName = templateFilePrefix + ( basicMode ? "ListBasic.ftl" : "List.ftl" );
				
			} else if( filterLanguage == null && uri.equals( "/magazines" ) ) {
				dataModel = createDataModelForListPage( PratilipiType.MAGAZINE, basicMode, displayLanguage, filterLanguage, request );
				templateName = templateFilePrefix + ( basicMode ? "ListBasic.ftl" : "List.ftl" );

			
			// Gujarati website specific links
			
			} else if( filterLanguage == Language.GUJARATI && uri.equals( "/short-stories" ) ) {
				dataModel = createDataModelForListPage( PratilipiType.STORY, basicMode, displayLanguage, filterLanguage, request );
				templateName = templateFilePrefix + ( basicMode ? "ListBasic.ftl" : "List.ftl" );
			
			} else if( filterLanguage == Language.GUJARATI && uri.equals( "/poetry" ) ) {
				dataModel = createDataModelForListPage( PratilipiType.POEM, basicMode, displayLanguage, filterLanguage, request );
				templateName = templateFilePrefix + ( basicMode ? "ListBasic.ftl" : "List.ftl" );
				
			} else if( filterLanguage == Language.GUJARATI && uri.equals( "/non-fiction" ) ) {
				dataModel = createDataModelForListPage( PratilipiType.ARTICLE, basicMode, displayLanguage, filterLanguage, request );
				templateName = templateFilePrefix + ( basicMode ? "ListBasic.ftl" : "List.ftl" );

				
				
			} else if( uri.matches( "^/[a-z0-9-]+$" ) && ( dataModel = createDataModelForListPage( uri.substring( 1 ), basicMode, displayLanguage, filterLanguage, request ) ) != null ) {
				templateName = templateFilePrefix + ( basicMode ? "ListBasic.ftl" : "List.ftl" );
				
			} else if( uri.matches( "^/[a-z0-9-/]+$" ) && ( dataModel = createDataModelForStaticPage( uri.substring( 1 ).replaceAll( "/", "_" ), displayLanguage ) ) != null ) {
				templateName = templateFilePrefix + ( basicMode ? "StaticBasic.ftl" : "Static.ftl" );
				
			} else if( uri.matches( "^/[a-z0-9-/]+$" ) && ( dataModel = createDataModelForStaticPage( uri.substring( 1 ).replaceAll( "/", "_" ), Language.ENGLISH ) ) != null ) {
				templateName = templateFilePrefix + ( basicMode ? "StaticBasic.ftl" : "Static.ftl" );
			
			} else if( uri.equals( "/register" ) ) {
				dataModel = new HashMap<String, Object>();
				dataModel.put( "title", "Register" );
				templateName = templateFilePrefix + ( basicMode ? "RegisterBasic.ftl" : "Register.ftl" );
				
			} else if( uri.equals( "/login" ) ) {
				dataModel = new HashMap<String, Object>();
				dataModel.put( "title", "Login" );
				templateName = templateFilePrefix + ( basicMode ? "LoginBasic.ftl" : "Login.ftl" );
				
			} else if( uri.equals( "/logout" ) ) {
				dataModel = new HashMap<String, Object>();
				dataModel.put( "title", "Logout" );
				templateName = templateFilePrefix + ( basicMode ? "LogoutBasic.ftl" : "Logout.ftl" );
				
			} else if( uri.equals( "/resetpassword" ) ) {
				dataModel = new HashMap<String, Object>();
				dataModel.put( "title", "Reset Password" );
				templateName = templateFilePrefix + ( basicMode ? "PasswordResetBasic.ftl" : "PasswordReset.ftl" );
				
				
				
			// Internal links
				
			} else if( ! basicMode && uri.equals( "/authors" ) ) {
				dataModel = createDataModelForAuthorsPage( filterLanguage );
				templateName = templateFilePrefix + "AuthorList.ftl";
			
			} else if( uri.equals( "/events" ) ) {
				dataModel = createDataModelForEventsPage( filterLanguage, basicMode );
				templateName = templateFilePrefix + ( basicMode ? "EventListBasic.ftl" : "EventList.ftl" );

			
			
			} else {
				dataModel = new HashMap<String, Object>();
				dataModel.put( "title", "Page Not Found !" );
				templateName = templateFilePrefix + ( basicMode ? "error/PageNotFoundBasic.ftl" : "error/PageNotFound.ftl" );
				response.setStatus( HttpServletResponse.SC_NOT_FOUND );
			}

		} catch( InsufficientAccessException e ) {
			dataModel = new HashMap<String, Object>();
			dataModel.put( "title", "Unauthorized Access !" );
			templateName = templateFilePrefix + ( basicMode ? "error/AuthorizationErrorBasic.ftl" : "error/AuthorizationError.ftl" );
			response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );

		} catch( InvalidArgumentException | UnexpectedServerException e ) {
			dataModel = new HashMap<String, Object>();
			dataModel.put( "title", "Server Error !" );
			templateName = templateFilePrefix + ( basicMode ? "error/ServerErrorBasic.ftl" : "error/ServerError.ftl" );
			response.setStatus( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
		}

		
		// Adding common data to the Data Model
		UserData userData = UserDataUtil.getCurrentUser();
		GenericUserResponse userResponse = new GenericUserResponse( userData );

		Map<PratilipiType, Map<String, String>> pratilipiTypes = new HashMap<>();
		for( PratilipiType pratilipiType : PratilipiType.values() ) {
			Map<String, String> pratilipiTypeMap = new HashMap<>();
			pratilipiTypeMap.put( "name", I18n.getString( pratilipiType.getStringId(), displayLanguage ) );
			pratilipiTypeMap.put( "namePlural", I18n.getString( pratilipiType.getPluralStringId(), displayLanguage ) );
			pratilipiTypes.put( pratilipiType, pratilipiTypeMap );
		}
		
		
		dataModel.put( "ga_userId", userData.getId().toString() );
		dataModel.put( "ga_website", UxModeFilter.getWebsite().toString() );
		dataModel.put( "ga_websiteMode", UxModeFilter.isBasicMode() ? "Basic" : "Standard" );
		dataModel.put( "ga_websiteVersion", "Mark-6" );
		
		dataModel.put( "lang", displayLanguage.getCode() );
		dataModel.put( "language", displayLanguage );
		dataModel.put( "_strings", I18n.getStrings( displayLanguage ) );
		dataModel.put( "resourceList", resourceList );
		dataModel.put( "user", userResponse );
		if( basicMode ) {
			dataModel.put( "requestUrl", "http://" + request.getServerName() + request.getRequestURI() );
			dataModel.put( "pratilipiTypes", pratilipiTypes );
		} else {
			Gson gson = new Gson();
			dataModel.put( "userJson", gson.toJson( userResponse ) );
			dataModel.put( "pratilipiTypesJson", gson.toJson( pratilipiTypes ) );
			dataModel.put( "navigationList", gson.toJson( navigationList ) );
		}

		
		// Generating response html
		String html = null;
		for( int i = 0; i < 2 && html == null; i++ ) {
			try {
				// The magic
				html = FreeMarkerUtil.processTemplate( dataModel, templateName );
			} catch( UnexpectedServerException e ) {
				logger.log( Level.SEVERE, "Exception occured while processing template.", e );
				templateName = templateFilePrefix + ( basicMode ? "error/ServerErrorBasic.ftl" : "error/ServerError.ftl" );
			}
		}
		
		
		// Dispatching response
		response.setContentType( "text/html" );
		response.setCharacterEncoding( "UTF-8" );
		response.getWriter().write( html );
		response.getWriter().close();
		
	}
	
	
	private String createPageTitle( String contentTitle, String contentTitleEn ) {
		return createPageTitle( contentTitle, contentTitleEn, UxModeFilter.getDisplayLanguage() );
	}
	
	private String createPageTitle( String contentTitle, String contentTitleEn, Language lang ) {
		String pageTitle = ( contentTitle == null ? "" : contentTitle + " - " ) + I18n.getString( "pratilipi", lang );
		if( lang != Language.ENGLISH )
			pageTitle += " | " + ( contentTitleEn == null ? "" : contentTitleEn + " - " ) + I18n.getString( "pratilipi", Language.ENGLISH );
		return pageTitle;
	}
	
	
	private String createPratilipiPageTitle( PratilipiData pratilipiData ) {
		if( pratilipiData == null )
			return null;
		
		String title = createAuthorPageTitle( pratilipiData.getAuthor() );
		title = title == null ? "" : " « " + title;
		
		if( pratilipiData.getTitle() != null && pratilipiData.getTitleEn() == null )
			return pratilipiData.getTitle() + title;
		else if( pratilipiData.getTitle() == null && pratilipiData.getTitleEn() != null )
			return pratilipiData.getTitleEn() + title;
		else if( pratilipiData.getTitle() != null && pratilipiData.getTitleEn() != null )
			return pratilipiData.getTitle() + " / " + pratilipiData.getTitleEn() + title;
		return null;
	}
	
	private String createAuthorPageTitle( AuthorData authorData ) {
		if( authorData == null )
			return null;
		
		if( authorData.getName() != null && authorData.getNameEn() == null )
			return authorData.getName();
		else if( authorData.getName() == null && authorData.getNameEn() != null )
			return authorData.getNameEn();
		else if( authorData.getName() != null && authorData.getNameEn() != null )
			return authorData.getName() + " / " + authorData.getNameEn();
		return null;
	}

	private String createReadPageTitle( PratilipiData pratilipiData, int pageNo, int pageCount ) {
		String title = createPratilipiPageTitle( pratilipiData );
		title = title == null ? "" : " « " + title;
		return "Page " + pageNo + " of " + pageCount + title;
	}

	
	private String getListTitle( String listName, Language lang ) {
		String listTitle = null;
		try {
			String fileName = "list." + lang.getCode() + "." + listName;
			InputStream inputStream = DataAccessor.class.getResource( "curated/" + fileName ).openStream();
			LineIterator it = IOUtils.lineIterator( inputStream, "UTF-8" );
			listTitle = it.nextLine().trim();
			LineIterator.closeQuietly( it );
		} catch( NullPointerException | IOException e ) {
			logger.log( Level.SEVERE, "Exception while reading from data file.", e );
		}
		return listTitle;
	}

	
	@SuppressWarnings("deprecation")
	private List<String> createFbOpenGraphTags( Long pratilipiId ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
		Page pratilipiPage = dataAccessor.getPage( PageType.PRATILIPI, pratilipiId );
		Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
		PratilipiData pratilipiData = PratilipiDataUtil.createPratilipiData( pratilipi, author, false );

		String ogFbAppId = FacebookApi.getAppId();
		String ogLocale = pratilipi.getLanguage().getCode() + "_IN";
		String ogType = "books.book";
		String ogAuthor = "http://" + Website.ALL_LANGUAGE.getHostName() + ( author == null ? "/team-pratilipi" : pratilipiData.getAuthor().getPageUrl() );
		String ogBooksIsbn = pratilipi.getId().toString();
		String ogUrl = "http://" + Website.ALL_LANGUAGE.getHostName() + pratilipiPage.getUri(); // Warning: Changing it to anything else will cause loss of like-share count.
		String ogTitle = createPratilipiPageTitle( pratilipiData );
		String ogImage = pratilipiData.getCoverImageUrl();
		String ogDescription = "";
		if( pratilipi.getType() == PratilipiType.BOOK && pratilipi.getSummary() != null ) {
			ogDescription = pratilipi.getSummary();
			Pattern htmlPattern = Pattern.compile( "<[^>]+>" );
			Matcher matcher = htmlPattern.matcher( pratilipi.getSummary() );
			while( matcher.find() )
				ogDescription = ogDescription.replace( matcher.group(), "" );
		}
		
		List<String> fbOgTags = new LinkedList<String>();
		fbOgTags.add( "<meta property='fb:app_id' content='" + ogFbAppId + "' />" );
		fbOgTags.add( "<meta property='og:locale' content='" + ogLocale + "' />" );
		fbOgTags.add( "<meta property='og:type' content='" + ogType + "' />" );
		fbOgTags.add( "<meta property='books:author' content='" + ogAuthor + "' />" );
		fbOgTags.add( "<meta property='books:isbn' content='" + ogBooksIsbn + "' />" );
		fbOgTags.add( "<meta property='og:url' content='" + ogUrl + "' />" );
		fbOgTags.add( "<meta property='og:title' content='" + ogTitle + "' />" );
		fbOgTags.add( "<meta property='og:image' content='" + ogImage + "' />" );
		fbOgTags.add( "<meta property='og:description' content='" + ogDescription + "' />" );
		return fbOgTags;
	}
	
	
	private Map<String, Object> createDataModelForHomePage( boolean basicMode, Language filterLanguage )
			throws InsufficientAccessException {

		Language listLanguage = filterLanguage == null ? Language.ENGLISH : filterLanguage;
		
		Gson gson = new Gson();
		
		String[] listNames = new String[] {};
		if( filterLanguage == Language.HINDI )
			listNames = new String[] { "ankahi-dastan", "romanchak-duniya", "shrestha-sahitya", "chakmak-chhoti-duniya", "swad-ka-tadka" };
		else if( filterLanguage == Language.GUJARATI )
			listNames = new String[] { "horror", "navi-prakashit-rachnao", "hasya-killol", "film-and-music", "bal-sahitya" };
		else if( filterLanguage == Language.TAMIL )
			listNames = new String[] { "featured", "fiction", "magazines" };
		else if( filterLanguage == Language.TELUGU )
			listNames = new String[] { "books", "stories", "articles" };
		else if( filterLanguage == Language.MALAYALAM )
			listNames = new String[] { "books", "stories", "poems" };
		else if( filterLanguage == Language.BENGALI )
			listNames = new String[] { "books", "premkahini", "aleek-kahini", "rahashyogalpo" };
		else if( filterLanguage == Language.MARATHI )
			listNames = new String[] { "kathajag", "lovestories", "swadishta-pakkala", "vaicharik", "sair-sapata" };
		ArrayList<Map<String, Object>> sections = new ArrayList<>( listNames.length );
		
		for( String listName : listNames ) {
			
			String title = getListTitle( listName, listLanguage );
			if( title == null )
				continue;
			
			if( title.indexOf( '|' ) != -1 )
				title = title.substring( 0, title.indexOf( '|' ) ).trim();
			
			PratilipiFilter pratilipiFilter = new PratilipiFilter();
			pratilipiFilter.setLanguage( listLanguage );
			pratilipiFilter.setListName( listName );
			pratilipiFilter.setState( PratilipiState.PUBLISHED );
			
			DataListCursorTuple<PratilipiData> pratilipiDataListCursorTuple =
					PratilipiDataUtil.getPratilipiDataList( pratilipiFilter, null, 6 );

			if( pratilipiDataListCursorTuple.getDataList().size() == 0 )
				continue;
			
			Map<String, Object> section = new HashMap<String, Object>();
			section.put( "title", title );
			section.put( "listPageUrl", "/" + listName );
			section.put( "pratilipiList", toListResponseObject( pratilipiDataListCursorTuple.getDataList() ) );
			sections.add( section );
			
		}
		
		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "title", createPageTitle( I18n.getString( "home_page_title", UxModeFilter.getDisplayLanguage() ), null ) );
		if( basicMode )
			dataModel.put( "sections", sections );
		else
			dataModel.put( "sectionsJson", gson.toJson( sections ) );
		return dataModel;
		
	}

	private Map<String, Object> createDataModelForLibraryPage( boolean basicMode, Language filterLanguage ) {
		
		DataListCursorTuple<PratilipiData> pratilipiDataListCursorTuple
				= UserPratilipiDataUtil.getUserPratilipiLibrary( AccessTokenFilter.getAccessToken().getUserId(), null, null, null );
		
		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "title", "My Library" );
		if( basicMode ) {
			dataModel.put( "pratilipiList", toListResponseObject( pratilipiDataListCursorTuple.getDataList() ) );
		} else {
			Gson gson = new Gson();
			dataModel.put( "pratilipiListJson", gson.toJson( toListResponseObject( pratilipiDataListCursorTuple.getDataList() ) ) );
			dataModel.put( "pratilipiListCursor", pratilipiDataListCursorTuple.getCursor() );
		}
		return dataModel;
		
	}

	
	public Map<String, Object> createDataModelForPratilipiPage( Long pratilipiId, boolean basicMode, HttpServletRequest request )
			throws InsufficientAccessException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
		if( ! PratilipiDataUtil.hasAccessToReadPratilipiContent( pratilipi ) )
			throw new InsufficientAccessException();
		
		Gson gson = new Gson();
		
		Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
		PratilipiData pratilipiData = PratilipiDataUtil.createPratilipiData( pratilipi, author, false );
		UserPratilipiData userPratilipiData = UserPratilipiDataUtil.getUserPratilipi( pratilipiId );

		GenericPratilipiResponse pratilipiResponse = new GenericPratilipiResponse( pratilipiData );
		GenericUserPratilipiResponse userPratilipiResponse = new GenericUserPratilipiResponse( userPratilipiData );
		

		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "title", createPratilipiPageTitle( pratilipiData ) );
		if( basicMode ) {
			dataModel.put( "pratilipi", pratilipiResponse );
			dataModel.put( "userpratilipi", userPratilipiResponse );
			String reviewParam = request.getParameter( RequestParameter.PRATILIPI_REVIEW.getName() );
			if( reviewParam != null && reviewParam.trim().equals( "list" ) ) {
				int reviewPageCurr = 1;
				int reviewPageSize = 20;
				String pageNoStr = request.getParameter( RequestParameter.PAGE_NUMBER.getName() );
				if( pageNoStr != null && ! pageNoStr.trim().isEmpty() )
					reviewPageCurr = Integer.parseInt( pageNoStr );
				DataListCursorTuple<UserPratilipiData> reviewListCursorTuple =
						UserPratilipiDataUtil.getPratilipiReviewList( pratilipiId, null, ( reviewPageCurr - 1 ) * reviewPageSize, reviewPageSize );
				dataModel.put( "reviewList", toGenericReviewResponseList( reviewListCursorTuple.getDataList() ) );
				dataModel.put( "reviewListPageCurr", reviewPageCurr );
				if( pratilipi.getReviewCount() != 0 )
					dataModel.put( "reviewListPageMax", (int) Math.ceil( ( (double) pratilipi.getReviewCount() ) / reviewPageSize ) );
				dataModel.put( "reviewParam", reviewParam );
			} else if( reviewParam != null && reviewParam.trim().equals( "write" ) && userPratilipiData != null && userPratilipiData.hasAccessToReview() ) {
				dataModel.put( "reviewParam", reviewParam );
			} else { // if( reviewParam == null || reviewParam.trim().isEmpty() ) {
				DataListCursorTuple<UserPratilipiData> reviewListCursorTuple =
						UserPratilipiDataUtil.getPratilipiReviewList( pratilipiId, null, null, 10 );
				dataModel.put( "reviewList", toGenericReviewResponseList( reviewListCursorTuple.getDataList() ) );
			}
		} else {
			DataListCursorTuple<UserPratilipiData> reviewListCursorTuple =
					UserPratilipiDataUtil.getPratilipiReviewList( pratilipiId, null, null, 20 );
			dataModel.put( "pratilipi", pratilipiResponse );
			dataModel.put( "pratilipiJson", gson.toJson( pratilipiResponse ) );
			dataModel.put( "userpratilipiJson", gson.toJson( userPratilipiResponse ) );
			dataModel.put( "reviewListJson", gson.toJson( toGenericReviewResponseList( reviewListCursorTuple.getDataList() ) ) );
			dataModel.put( "reviewListCursor", reviewListCursorTuple.getCursor() );
		}
		return dataModel;
		
	}
	
	public Map<String, Object> createDataModelForAuthorPage( Long authorId, boolean basicMode )
			throws InsufficientAccessException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Author author = dataAccessor.getAuthor( authorId );
		AuthorData authorData = AuthorDataUtil.createAuthorData( author );
		UserAuthorData userAuthorData = UserAuthorDataUtil.getUserAuthor( AccessTokenFilter.getAccessToken().getUserId(), authorId );

		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		pratilipiFilter.setAuthorId( authorId );
		pratilipiFilter.setState( PratilipiState.PUBLISHED );
		DataListCursorTuple<PratilipiData> pratilipiDataListCursorTuple =
				PratilipiDataUtil.getPratilipiDataList( pratilipiFilter, null, 20 );
		
		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "title", createAuthorPageTitle( authorData ) );
		if( basicMode ) {
			dataModel.put( "author", authorData );
			dataModel.put( "publishedPratilipiList", pratilipiDataListCursorTuple.getDataList() );
			if( pratilipiDataListCursorTuple.getDataList().size() == 20 && pratilipiDataListCursorTuple.getCursor() != null )
				dataModel.put( "publishedPratilipiListSearchQuery", pratilipiFilter.toUrlEncodedString() );
		} else {
			Gson gson = new Gson();
			dataModel.put( "authorJson", gson.toJson( authorData ) );
			dataModel.put( "userAuthorJson", gson.toJson( userAuthorData ) );
			dataModel.put( "publishedPratilipiListJson", gson.toJson( pratilipiDataListCursorTuple.getDataList() ) );
			dataModel.put( "publishedPratilipiListFilterJson", gson.toJson( pratilipiFilter ) );
			dataModel.put( "publishedPratilipiListCursor", pratilipiDataListCursorTuple.getCursor() );
		}
		return dataModel;
		
	}

	public Map<String, Object> createDataModelForAuthorsPage( Language lang )
			throws InsufficientAccessException {
		
		AuthorFilter authorFilter = new AuthorFilter();
		authorFilter.setLanguage( lang );
		
		DataListCursorTuple<AuthorData> authorDataListCursorTuple =
				AuthorDataUtil.getAuthorDataList( null, authorFilter, null, 20 );
		
		Gson gson = new Gson();
		
		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "title", "Authors" );
		dataModel.put( "authorListJson", gson.toJson( authorDataListCursorTuple.getDataList() ) );
		dataModel.put( "authorListFilterJson", gson.toJson( authorFilter ) );
		dataModel.put( "authorListCursor", authorDataListCursorTuple.getCursor() );
		return dataModel;
		
	}
	
	public Map<String, Object> createDataModelForEventsPage( Language lang, boolean basicMode ) {
		EventData eventData = new EventData();
		eventData.setLanguage( lang );
		
		boolean hasAccessToAdd = EventDataUtil.hasAccessToAddEventData( eventData );
		
		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "title", "Events" );
		if( basicMode )
			dataModel.put( "eventList", EventDataUtil.getEventDataList( lang ) );
		else
			dataModel.put( "eventListJson", new Gson().toJson( EventDataUtil.getEventDataList( lang ) ) );
		dataModel.put( "hasAccessToAdd", hasAccessToAdd );
		return dataModel;
	}
	
	public Map<String, Object> createDataModelForEventPage( Long eventId, boolean basicMode )
			throws InsufficientAccessException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Event event = dataAccessor.getEvent( eventId );
		EventData eventData = EventDataUtil.createEventData( event );

		Gson gson = new Gson();
		
		GenericEventResponse eventResponse = new GenericEventResponse( eventData );
		List<PratilipiData> pratilipiDataList = PratilipiDataUtil.createPratilipiDataList( event.getPratilipiIdList(), true );
		
		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "title", createPageTitle( eventData.getName(), eventData.getNameEn() ) );
		if( basicMode ) {
			dataModel.put( "event", eventData );
			dataModel.put( "pratilipiList", toListResponseObject( pratilipiDataList ) );
		} else {
			dataModel.put( "eventJson", gson.toJson( eventResponse ) );
			dataModel.put( "pratilipiListJson", gson.toJson( toListResponseObject( pratilipiDataList ) ) );
		}
		return dataModel;
		
	}
	
	public Map<String, Object> createDataModelForBlogPage( Long blogId, Language lang, boolean basicMode ) {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		Blog blog = dataAccessor.getBlog( blogId );
		
		BlogPostFilter blogPostFilter = new BlogPostFilter();
		blogPostFilter.setBlogId( blogId );
		blogPostFilter.setLanguage( lang );
		blogPostFilter.setState( BlogPostState.PUBLISHED );
		
		DataListCursorTuple<BlogPostData> blogPostDataListCursorTuple
				= BlogPostDataUtil.getBlogPostDataList( blogPostFilter, null, 0, 10 );

		List<GenericBlogPostResponse> blogPostList
				= new ArrayList<>( blogPostDataListCursorTuple.getDataList().size() );
		for( BlogPostData blogPostData : blogPostDataListCursorTuple.getDataList() )
			blogPostList.add( new GenericBlogPostResponse( blogPostData ) );
		
		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "title", blog.getTitle() );
		if( basicMode ) {
			dataModel.put( "blogPostList", blogPostList );
		} else {
			Gson gson = new Gson();
			dataModel.put( "blogPostListJson", gson.toJson( blogPostList ) );
			dataModel.put( "blogPostFilterJson", gson.toJson( blogPostFilter ) );
			dataModel.put( "blogPostListCursor", blogPostDataListCursorTuple.getCursor() );
		}
		return dataModel;
	}
	
	public Map<String, Object> createDataModelForBlogPostPage( Long blogId, boolean basicMode )
			throws InsufficientAccessException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		BlogPost blogPost = dataAccessor.getBlogPost( blogId );
		BlogPostData blogPostData = BlogPostDataUtil.createBlogPostData( blogPost );

		Gson gson = new Gson();
		
		GenericBlogPostResponse eventResponse = new GenericBlogPostResponse( blogPostData );
		
		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "title", createPageTitle( blogPostData.getTitle(), blogPostData.getTitleEn() ) );
		if( basicMode ) {
			dataModel.put( "blogPost", blogPostData );
		} else {
			dataModel.put( "blogPostJson", gson.toJson( eventResponse ) );
		}
		return dataModel;
		
	}
	
	@SuppressWarnings("deprecation")
	public Map<String, Object> createDataModelForReadPage( Long pratilipiId, Integer pageNo, boolean basicMode )
			throws InvalidArgumentException, UnexpectedServerException, InsufficientAccessException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
		Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
		PratilipiData pratilipiData = PratilipiDataUtil.createPratilipiData( pratilipi, author, false );
		
		Object content = PratilipiDataUtil.getPratilipiContent( pratilipiId, null, pageNo, PratilipiContentType.PRATILIPI );
		
		Gson gson = new Gson();
		GenericPratilipiResponse pratilipiResponse =
				gson.fromJson( gson.toJson( pratilipiData ), GenericPratilipiResponse.class );
		
		Map<String, Object> dataModel = new HashMap<String, Object>();
		if( basicMode ) {
			dataModel.put( "title", createReadPageTitle( pratilipiData, 1, 1 ) );
			dataModel.put( "pratilipi", pratilipiData );
		} else {
			dataModel.put( "title", createReadPageTitle( pratilipiData, 1, 1 ) );
			dataModel.put( "pratilipiJson", gson.toJson( pratilipiResponse ) );
			dataModel.put( "pageNo", pageNo );
			dataModel.put( "pageCount", pratilipi.getPageCount() );
			dataModel.put( "indexJson", gson.toJson( pratilipi.getIndex() ) );
			dataModel.put( "contentHTML", content );
		}
		return dataModel;
		
	}

	
	private Map<String, Object> createDataModelForSearchPage( boolean basicMode, Language lang, HttpServletRequest request )
			throws InsufficientAccessException {
		
		String searchQuery = request.getParameter( RequestParameter.SEARCH_QUERY.getName() );
		if( searchQuery != null && searchQuery.trim().isEmpty() )
			searchQuery = null;
		
		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		pratilipiFilter.setLanguage( lang );
		pratilipiFilter.setState( PratilipiState.PUBLISHED );
		
		int pageCurr = 1;
		int pageSize = 20;
		
		if( basicMode ) {
			String pageNoStr = request.getParameter( RequestParameter.PAGE_NUMBER.getName() );
			if( pageNoStr != null && ! pageNoStr.trim().isEmpty() )
				pageCurr = Integer.parseInt( pageNoStr );
		}
		
		DataListCursorTuple<PratilipiData> pratilipiDataListCursorTuple =
				PratilipiDataUtil.getPratilipiDataList( searchQuery, pratilipiFilter, null, (pageCurr - 1) * pageSize, pageSize );

		Gson gson = new Gson();

		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "title", "Search" );
		if( basicMode ) {
			dataModel.put( "pratilipiList", toListResponseObject( pratilipiDataListCursorTuple.getDataList() ) );
			dataModel.put( "pratilipiListSearchQuery", searchQuery );
			dataModel.put( "pratilipiListPageCurr", pageCurr );
			if( pratilipiDataListCursorTuple.getNumberFound() != null )
				dataModel.put( "pratilipiListPageMax", (int) Math.ceil( ( (double) pratilipiDataListCursorTuple.getNumberFound() ) / pageSize ) );
		} else {
			dataModel.put( "pratilipiListJson", gson.toJson( toListResponseObject( pratilipiDataListCursorTuple.getDataList() ) ) );
			dataModel.put( "pratilipiListSearchQuery", searchQuery );
			dataModel.put( "pratilipiListFilterJson", gson.toJson( pratilipiFilter ) );
			dataModel.put( "pratilipiListCursor", pratilipiDataListCursorTuple.getCursor() );
		}
		return dataModel;
		
	}
	
	private Map<String, Object> createDataModelForListPage( PratilipiType type,
			boolean basicMode, Language displayLanguage, Language filterLanguage,
			HttpServletRequest request ) throws InsufficientAccessException {
		
		return createDataModelForListPage( type, null, basicMode, displayLanguage, filterLanguage, request );
	}

	private Map<String, Object> createDataModelForListPage( String listName,
			boolean basicMode, Language displayLanguage, Language filterLanguage,
			HttpServletRequest request ) throws InsufficientAccessException {

		return createDataModelForListPage( null, listName, basicMode, displayLanguage, filterLanguage, request );
		
	}
	
	private Map<String, Object> createDataModelForListPage( PratilipiType type, String listName,
			boolean basicMode, Language displayLanugage, Language filterLanguage,
			HttpServletRequest request ) throws InsufficientAccessException {

		String listTitle = null;
		String listTitleEn = null;

		if( listName == null ) {
			listTitle = I18n.getString( type.getPluralStringId(), displayLanugage );
			listTitleEn = displayLanugage == Language.ENGLISH ? null : I18n.getString( type.getPluralStringId(), Language.ENGLISH );
		} else {
			String title = getListTitle( listName, filterLanguage );
			if( title == null )
				return null;
			if( title.indexOf( '|' ) == -1 ) {
				listTitle = title.trim();
				listTitleEn = null;
			} else {
				listTitle = title.substring( 0, title.indexOf( '|' ) ).trim();
				listTitleEn = title.substring( title.indexOf( '|' ) + 1 ).trim();
			}
		}
		
		String title = createPageTitle( listTitle, listTitleEn, displayLanugage );
		
		
		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		pratilipiFilter.setLanguage( filterLanguage );
		pratilipiFilter.setType( type );
		pratilipiFilter.setListName( listName );
		pratilipiFilter.setState( PratilipiState.PUBLISHED );
		
		int pageCurr = 1;
		int pageSize = 20;
		
		if( basicMode ) {
			String pageNoStr = request.getParameter( RequestParameter.PAGE_NUMBER.getName() );
			if( pageNoStr != null && ! pageNoStr.trim().isEmpty() )
				pageCurr = Integer.parseInt( pageNoStr );
		}
		
		DataListCursorTuple<PratilipiData> pratilipiDataListCursorTuple =
				PratilipiDataUtil.getPratilipiDataList( pratilipiFilter, null, ( pageCurr - 1 ) * pageSize, pageSize );

		
		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "title", title );
		dataModel.put( "pratilipiListTitle", listTitle );
		if( basicMode ) {
			dataModel.put( "pratilipiList", toListResponseObject( pratilipiDataListCursorTuple.getDataList() ) );
			dataModel.put( "pratilipiListPageCurr", pageCurr );
			if( pratilipiDataListCursorTuple.getNumberFound() != null )
				dataModel.put( "pratilipiListPageMax", (int) Math.ceil( ( (double) pratilipiDataListCursorTuple.getNumberFound() ) / pageSize ) );
		} else {
			Gson gson = new Gson();
			dataModel.put( "pratilipiListJson", gson.toJson( toListResponseObject( pratilipiDataListCursorTuple.getDataList() ) ) );
			dataModel.put( "pratilipiListFilterJson", gson.toJson( pratilipiFilter ) );
			dataModel.put( "pratilipiListCursor", pratilipiDataListCursorTuple.getCursor() );
		}
		return dataModel;
		
	}
	
	
	public Map<String, Object> createDataModelForStaticPage( String pageName, Language lang )
			throws UnexpectedServerException {

		String title = null;
		StringBuilder content = new StringBuilder();
		try {
			String fileName = "static." + ( lang == null ? "" : lang.getCode() + "." ) + pageName;
			File file = new File( getClass().getResource( dataFilePrefix + fileName ).toURI() );
			LineIterator it = FileUtils.lineIterator( file, "UTF-8" );
			if( it.hasNext() )
				title = it.nextLine().trim();
			while( it.hasNext() )
				content.append( it.nextLine() + "<br/>" );
			LineIterator.closeQuietly( it );
		} catch( NullPointerException e ) {
			return null;
		} catch( URISyntaxException | IOException e ) {
			logger.log( Level.SEVERE, "Exception while reading from data file.", e );
			throw new UnexpectedServerException();
		}

		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "title", title );
		dataModel.put( "content", content.toString() );
		return dataModel;
	}


	private List<GetPratilipiListResponse.Pratilipi> toListResponseObject( List<PratilipiData> pratilipiDataList ) {
		List<GetPratilipiListResponse.Pratilipi> pratilipiList = new ArrayList<>( pratilipiDataList.size() );
		for( PratilipiData pratilipiData : pratilipiDataList )
			pratilipiList.add( new GetPratilipiListResponse.Pratilipi( pratilipiData ) );
		return pratilipiList;
	}

	private List<GenericReviewResponse> toGenericReviewResponseList( List<UserPratilipiData> userPratilipiList ) {
		List<GenericReviewResponse> reviewList = new ArrayList<>( userPratilipiList.size() );
		for( UserPratilipiData userPratilipiData : userPratilipiList )
			reviewList.add( new GenericReviewResponse( userPratilipiData ) );
		return reviewList;
	}
	
}
