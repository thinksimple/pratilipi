package com.pratilipi.site;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URLEncoder;
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
import com.pratilipi.api.ApiRegistry;
import com.pratilipi.api.impl.author.AuthorApi;
import com.pratilipi.api.impl.blogpost.shared.GenericBlogPostResponse;
import com.pratilipi.api.impl.event.shared.GenericEventResponse;
import com.pratilipi.api.impl.pratilipi.PratilipiApi;
import com.pratilipi.api.impl.pratilipi.PratilipiListApi;
import com.pratilipi.api.impl.user.shared.GenericUserResponse;
import com.pratilipi.api.impl.userauthor.UserAuthorFollowApi;
import com.pratilipi.api.impl.userpratilipi.UserPratilipiApi;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.BlogPostState;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PageType;
import com.pratilipi.common.type.PratilipiContentType;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.type.PratilipiType;
import com.pratilipi.common.type.RequestCookie;
import com.pratilipi.common.type.RequestParameter;
import com.pratilipi.common.type.Website;
import com.pratilipi.common.util.AuthorFilter;
import com.pratilipi.common.util.BlogPostFilter;
import com.pratilipi.common.util.FacebookApi;
import com.pratilipi.common.util.FreeMarkerUtil;
import com.pratilipi.common.util.PratilipiFilter;
import com.pratilipi.common.util.SystemProperty;
import com.pratilipi.common.util.ThirdPartyResource;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.client.AuthorData;
import com.pratilipi.data.client.BlogPostData;
import com.pratilipi.data.client.EventData;
import com.pratilipi.data.client.PratilipiData;
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

		// Setting user's author profile's default language, if not set already
		if( UxModeFilter.getFilterLanguage() != null ) {
			Author author = dataAccessor.getAuthorByUserId( AccessTokenFilter.getAccessToken().getUserId() );
			if( author != null && author.getLanguage() == null ) {
				author.setLanguage( UxModeFilter.getFilterLanguage() );
				dataAccessor.createOrUpdateAuthor( author );
			}
		}
		
		
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
			resourceList.add( ThirdPartyResource.CKEDITOR.getTag() );
			resourceList.add( ThirdPartyResource.POLYMER.getTag() );
//			resourceList.add( ThirdPartyResource.POLYMER_IRON_AJAX.getTag() );
//			resourceList.add( ThirdPartyResource.POLYMER_IRON_ICONS.getTag() );
//			resourceList.add( ThirdPartyResource.POLYMER_IRON_OVERLAY_BEHAVIOR.getTag() );
//			resourceList.add( ThirdPartyResource.POLYMER_IRON_RESIZABLE_BEHAVIOR.getTag() );
//			resourceList.add( ThirdPartyResource.POLYMER_PAPER_CARD.getTag() );
//			resourceList.add( ThirdPartyResource.POLYMER_PAPER_DROPDOWN_MENU.getTag() );
//			resourceList.add( ThirdPartyResource.POLYMER_PAPER_ICON_BUTTON.getTag() );
//			resourceList.add( ThirdPartyResource.POLYMER_PAPER_INPUT.getTag() );
//			resourceList.add( ThirdPartyResource.POLYMER_PAPER_MENU.getTag() );
//			resourceList.add( ThirdPartyResource.POLYMER_PAPER_MENU_BUTTON.getTag() );
//			resourceList.add( ThirdPartyResource.POLYMER_PAPER_ITEM.getTag() );
//			resourceList.add( ThirdPartyResource.POLYMER_PAPER_SPINNER.getTag() );
		}

		
		// Data Model for FreeMarker
		Map<String, Object> dataModel = null;
		String templateName = null;
		
		try {
			
			if( uri.equals( "/" ) ) {
				if( UxModeFilter.getWebsite() == Website.ALL_LANGUAGE || UxModeFilter.getWebsite() == Website.GAMMA_ALL_LANGUAGE ) {
					dataModel = createDataModelForMasterHomePage( filterLanguage );
					templateName = templateFilePrefix + "MasterHome.ftl";
				} else {
					dataModel = createDataModelForHomePage( basicMode, filterLanguage );
					templateName = templateFilePrefix + ( basicMode ? "HomeBasic.ftl" : "Home.ftl" );
				}
				
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
				if( request.getParameter( RequestParameter.PASSWORD_RESET_EMAIL_EMAIL.getName() ) != null 
						&& request.getParameter( RequestParameter.PASSWORD_RESET_EMAIL_TOKEN.getName() ) != null ) {
					dataModel.put( "passwordResetFromMail", true );
					dataModel.put( "email", request.getParameter( RequestParameter.PASSWORD_RESET_EMAIL_EMAIL.getName() ) );
					dataModel.put( "verificationToken", request.getParameter( RequestParameter.PASSWORD_RESET_EMAIL_TOKEN.getName() ) );
				}
				dataModel.put( "title", "Update Password" );
				templateName = templateFilePrefix + "PasswordUpdateBasic.ftl";
				
			} else if( page != null && page.getType() == PageType.PRATILIPI ) {
				resourceList.addAll( createFbOpenGraphTags( page.getPrimaryContentId() ) );
				dataModel = createDataModelForPratilipiPage( page.getPrimaryContentId(), basicMode, request );
				templateName = templateFilePrefix + ( basicMode ? "PratilipiBasic.ftl" : "Pratilipi.ftl" );
				
			} else if( page != null && page.getType() == PageType.AUTHOR ) {
				dataModel = createDataModelForAuthorPage( page.getPrimaryContentId(), basicMode );
				if( SystemProperty.STAGE.equals( "gamma" ) )
					templateName = templateFilePrefix + "AuthorPage.ftl";
				else
					templateName = templateFilePrefix + ( basicMode ? "AuthorBasic.ftl" : "Author.ftl" );
			
			} else if( page != null && page.getType() == PageType.EVENT ) {
				if( ! basicMode )
					resourceList.add( ThirdPartyResource.TINYMCE.getTag() );
				dataModel = createDataModelForEventPage( page.getPrimaryContentId(), basicMode );
				templateName = templateFilePrefix + ( basicMode ? "EventBasic.ftl" : "Event.ftl" );
			
			} else if( page != null && page.getType() == PageType.BLOG ) {
				if( ! basicMode )
					resourceList.add( ThirdPartyResource.TINYMCE.getTag() );
				dataModel = createDataModelForBlogPage( page.getPrimaryContentId(), filterLanguage, basicMode );
				templateName = templateFilePrefix + ( basicMode ? "BlogPostListBasic.ftl" : "BlogPostList.ftl" );
			
			} else if( page != null && page.getType() == PageType.BLOG_POST ) {
				if( ! basicMode )
					resourceList.add( ThirdPartyResource.TINYMCE.getTag() );
				dataModel = createDataModelForBlogPostPage( page.getPrimaryContentId(), basicMode );
				templateName = templateFilePrefix + ( basicMode ? "BlogPostBasic.ftl" : "BlogPost.ftl" );
			
			} else if( uri.equals( "/read" ) ) {
				if( !basicMode ) {
//					resourceList.add( ThirdPartyResource.POLYMER_PAPER_FAB.getTag() );
//					resourceList.add( ThirdPartyResource.POLYMER_PAPER_SLIDER.getTag() );
//					resourceList.add( ThirdPartyResource.POLYMER_IRON_ICONS_SOCIAL.getTag() );
//					resourceList.add( ThirdPartyResource.POLYMER_IRON_ICONS_AV.getTag() );
//					resourceList.add( ThirdPartyResource.POLYMER_IRON_ICONS_EDITOR.getTag() );
				}

				Long pratilipiId = Long.parseLong( request.getParameter( RequestParameter.READER_CONTENT_ID.getName() ) );
				String fontSize = AccessTokenFilter.getCookieValue( RequestCookie.FONT_SIZE.getName(), request );
				String imageSize = AccessTokenFilter.getCookieValue( RequestCookie.IMAGE_SIZE.getName(), request );
				String action = request.getParameter( "action" ) != null ? request.getParameter( "action" ) : "read";
				String pageNoPattern = "reader_page_number_" + pratilipiId;

				Integer pageNo = null;
				if( request.getParameter( RequestParameter.READER_PAGE_NUMBER.getName() ) != null )
					pageNo = Integer.parseInt( request.getParameter( RequestParameter.READER_PAGE_NUMBER.getName() ) );
				else if( AccessTokenFilter.getCookieValue( pageNoPattern, request ) != null )
					pageNo = Integer.parseInt( AccessTokenFilter.getCookieValue( pageNoPattern, request ) );
				else
					pageNo = 1;

				dataModel = createDataModelForReadPage( pratilipiId, pageNo, basicMode );
				dataModel.put( "fontSize", fontSize != null ? Integer.parseInt( fontSize ) : 14 );
				dataModel.put( "imageSize", imageSize != null ? Integer.parseInt( imageSize ) : 636 );
				dataModel.put( "action", action );
				templateName = templateFilePrefix + ( basicMode ? "ReadBasic.ftl" : "Read.ftl" );
			
			} else if( uri.equals( "/pratilipi-project" ) ) {
				// Test Book
				Long pratilipiId = 5630253470842880L;
				Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
				
				if( ! PratilipiDataUtil.hasAccessToReadPratilipiContent( pratilipi ) )
					throw new InsufficientAccessException();
				
				Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
				PratilipiData pratilipiData = PratilipiDataUtil.createPratilipiData( pratilipi, author, false );
				PratilipiApi.Response pratilipiResponse = new PratilipiApi.Response( pratilipiData );
				UserPratilipiData userPratilipiData = UserPratilipiDataUtil.getUserPratilipi( AccessTokenFilter.getAccessToken().getUserId(), pratilipiId );
				DataListCursorTuple<UserPratilipiData> reviewListCursorTuple =
						UserPratilipiDataUtil.getPratilipiReviewList( pratilipiId, null, null, 20 );
				
				Gson gson = new Gson();
				dataModel = new HashMap<String, Object>();
				dataModel.put( "title", "Pratilipi Project" );
				dataModel.put( "pratilipiJson", gson.toJson( pratilipiResponse ) );
				dataModel.put( "addedToLib", ( userPratilipiData == null || userPratilipiData.isAddedToLib() == null ) ? 
									false : userPratilipiData.isAddedToLib() );
				dataModel.put( "reviewListJson", gson.toJson( toGenericReviewResponseList( reviewListCursorTuple.getDataList() ) ) );
				dataModel.put( "reviewListCursor", reviewListCursorTuple.getCursor() );
				templateName = templateFilePrefix + "Project.ftl";

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
				if( ! basicMode )
					resourceList.add( ThirdPartyResource.TINYMCE.getTag() );
				dataModel = createDataModelForEventsPage( filterLanguage, basicMode );
				templateName = templateFilePrefix + ( basicMode ? "EventListBasic.ftl" : "EventList.ftl" );
			
			} else if( uri.matches( "^/[a-z0-9-]+$" ) && ( dataModel = createDataModelForListPage( uri.substring( 1 ), basicMode, displayLanguage, filterLanguage, request ) ) != null ) {
				templateName = templateFilePrefix + ( basicMode ? "ListBasic.ftl" : "List.ftl" );
				
			} else if( uri.matches( "^/[a-z0-9-/]+$" ) && ( dataModel = createDataModelForStaticPage( uri.substring( 1 ).replaceAll( "/", "_" ), displayLanguage ) ) != null ) {
				templateName = templateFilePrefix + ( basicMode ? "StaticBasic.ftl" : "Static.ftl" );
				
			} else if( uri.matches( "^/[a-z0-9-/]+$" ) && ( dataModel = createDataModelForStaticPage( uri.substring( 1 ).replaceAll( "/", "_" ), Language.ENGLISH ) ) != null ) {
				templateName = templateFilePrefix + ( basicMode ? "StaticBasic.ftl" : "Static.ftl" );
			

			
			
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
			response.setStatus( HttpServletResponse.SC_SERVICE_UNAVAILABLE );
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
		dataModel.put( "website_host", UxModeFilter.getWebsite().getHostName() );
		dataModel.put( "website_mobile_host", UxModeFilter.getWebsite().getMobileHostName() );
		dataModel.put( "_strings", I18n.getStrings( displayLanguage ) );
		dataModel.put( "resourceList", resourceList );
		dataModel.put( "user", userResponse );
		dataModel.put( "pratilipiTypesJson", new Gson().toJson( pratilipiTypes ) );
		if( basicMode ) {
			StringBuffer requestUrl = new StringBuffer( request.getRequestURI() );
			if( request.getQueryString() != null )
				requestUrl.append( '?' ).append( request.getQueryString() );
			dataModel.put( "requestUrl", URLEncoder.encode( requestUrl.toString() ) );
		} else {
			Gson gson = new Gson();
			dataModel.put( "userJson", gson.toJson( userResponse ) );
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
		String pageTitle = ( contentTitle == null ? "" : contentTitle + " « " ) + I18n.getString( "pratilipi", lang );
		if( lang != Language.ENGLISH )
			pageTitle += " | " + ( contentTitleEn == null ? "" : contentTitleEn + " « " ) + I18n.getString( "pratilipi", Language.ENGLISH );
		return pageTitle;
	}
	
	
	private String createPratilipiPageTitle( PratilipiData pratilipiData ) {
		if( pratilipiData == null )
			return null;
		
		String title = createAuthorPageTitle( new AuthorApi.Response( pratilipiData.getAuthor() ) );
		title = title == null ? "" : " « " + title;
		
		if( pratilipiData.getTitle() != null && pratilipiData.getTitleEn() == null )
			return pratilipiData.getTitle() + title;
		else if( pratilipiData.getTitle() == null && pratilipiData.getTitleEn() != null )
			return pratilipiData.getTitleEn() + title;
		else if( pratilipiData.getTitle() != null && pratilipiData.getTitleEn() != null )
			return pratilipiData.getTitle() + " / " + pratilipiData.getTitleEn() + title;
		return null;
	}
	
	private String createAuthorPageTitle( AuthorApi.Response authorData ) {
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
		if( title == null )
			title = "";
		String pratilipiText = I18n.getString( "pratilipi", UxModeFilter.getDisplayLanguage() ) + " / " + "Pratilipi";
		return title + " « " + pratilipiText;
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
	
	
	private Map<String, Object> createDataModelForMasterHomePage( Language filterLanguage )
			throws InsufficientAccessException {
		
		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "title", createPageTitle( I18n.getString( "home_page_title", filterLanguage ), null ) );
		return dataModel;

	}
	
	private Map<String, Object> createDataModelForHomePage( boolean basicMode, Language filterLanguage )
			throws InsufficientAccessException, IOException {

		if( filterLanguage == null )
			filterLanguage = Language.ENGLISH;
		
		List<Map<String, Object>> sections = new LinkedList<>();
		
		String fileName = "home." + filterLanguage.getCode();
		InputStream inputStream = DataAccessor.class.getResource( "curated/" + fileName ).openStream();
		LineIterator it = IOUtils.lineIterator( inputStream, "UTF-8" );
		while( it.hasNext() ) {
			
			String listName = it.next().trim();
			if( listName.isEmpty() )
				continue;
			
			String title = getListTitle( listName, filterLanguage );
			if( title == null )
				continue;
			
			if( title.indexOf( '|' ) != -1 )
				title = title.substring( 0, title.indexOf( '|' ) ).trim();
			
			PratilipiFilter pratilipiFilter = new PratilipiFilter();
			pratilipiFilter.setLanguage( filterLanguage );
			pratilipiFilter.setListName( listName );
			pratilipiFilter.setState( PratilipiState.PUBLISHED );
			
			DataListCursorTuple<PratilipiData> pratilipiDataListCursorTuple =
					PratilipiDataUtil.getPratilipiDataList( null, pratilipiFilter, null, null, 6 );

			if( pratilipiDataListCursorTuple.getDataList().size() == 0 )
				continue;
			
			Map<String, Object> section = new HashMap<String, Object>();
			section.put( "title", title );
			section.put( "listPageUrl", "/" + listName );
			section.put( "pratilipiList", toListResponseObject( pratilipiDataListCursorTuple.getDataList() ) );
			sections.add( section );
			
		}
		LineIterator.closeQuietly( it );
		
		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "title", createPageTitle( I18n.getString( "home_page_title", filterLanguage ), null ) );
		if( basicMode )
			dataModel.put( "sections", sections );
		else
			dataModel.put( "sectionsJson", new Gson().toJson( sections ) );
		return dataModel;
		
	}

	private Map<String, Object> createDataModelForLibraryPage( boolean basicMode, Language filterLanguage ) {
		
		DataListCursorTuple<PratilipiData> pratilipiDataListCursorTuple
				= UserPratilipiDataUtil.getUserLibrary( AccessTokenFilter.getAccessToken().getUserId(), null, null, null );
		
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
			throws InsufficientAccessException, UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
		if( ! PratilipiDataUtil.hasAccessToReadPratilipiContent( pratilipi ) )
			throw new InsufficientAccessException();
		
		Gson gson = new Gson();
		
		Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
		PratilipiData pratilipiData = PratilipiDataUtil.createPratilipiData( pratilipi, author, false );
		UserPratilipiData userPratilipiData = UserPratilipiDataUtil.getUserPratilipi( AccessTokenFilter.getAccessToken().getUserId(), pratilipiId );

		PratilipiApi.Response pratilipiResponse = new PratilipiApi.Response( pratilipiData );
		UserPratilipiApi.Response userPratilipiResponse = userPratilipiData == null
				? null : new UserPratilipiApi.Response( userPratilipiData );
		

		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "title", createPratilipiPageTitle( pratilipiData ) );
		if( basicMode ) {
			dataModel.put( "pratilipi", pratilipiResponse );
			dataModel.put( "userpratilipi", userPratilipiResponse );
			String reviewParam = request.getParameter( RequestParameter.PRATILIPI_REVIEW.getName() );
			if( reviewParam != null && reviewParam.trim().equals( "list" ) ) {
				int reviewPageCurr = 1;
				int reviewPageSize = 20;
				String pageNoStr = request.getParameter( RequestParameter.LIST_PAGE_NUMBER.getName() );
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
			} else if( reviewParam != null && reviewParam.trim().equals( "reply" ) ) {
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
		
		AuthorApi.GetRequest authorApiGetRequest = new AuthorApi.GetRequest();
		authorApiGetRequest.setAuthorId( authorId );
		AuthorApi.Response authorResponse = ApiRegistry.getApi( AuthorApi.class ).get( authorApiGetRequest );

		UserAuthorFollowApi.GetRequest getRequest = new UserAuthorFollowApi.GetRequest();
		getRequest.setAuthorId( authorId );
		UserAuthorFollowApi.Response userAuthorData = ApiRegistry
				.getApi( UserAuthorFollowApi.class )
				.get( getRequest );

		PratilipiListApi.GetRequest pratilipiListRequest = new PratilipiListApi.GetRequest();
		pratilipiListRequest.setAuthorId( authorId );
		pratilipiListRequest.setState( PratilipiState.PUBLISHED );

		PratilipiListApi.Response pratilipiListResponse = ApiRegistry
				.getApi( PratilipiListApi.class )
				.get( pratilipiListRequest );
		
		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "title", createAuthorPageTitle( authorResponse ) );
		if( basicMode ) {
			dataModel.put( "author", authorResponse );
			dataModel.put( "publishedPratilipiList", pratilipiListResponse.getPratilipiList() );
			if( pratilipiListResponse.getPratilipiList().size() == 20 && pratilipiListResponse.getCursor() != null )
				dataModel.put( "publishedPratilipiListSearchQuery", "authorId=" + authorId + "&state=" + PratilipiState.PUBLISHED );
		} else {
			Gson gson = new Gson();
			dataModel.put( "authorJson", gson.toJson( authorResponse ) );
			dataModel.put( "userAuthorJson", gson.toJson( userAuthorData ) );
			dataModel.put( "publishedPratilipiListJson", gson.toJson( pratilipiListResponse.getPratilipiList() ) );
			dataModel.put( "publishedPratilipiListFilterJson", gson.toJson( pratilipiListRequest ) );
			dataModel.put( "publishedPratilipiListCursor", pratilipiListResponse.getCursor() );
			dataModel.put( "publishedPratilipiListObjectJson", gson.toJson( pratilipiListResponse ) );
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
		
		BlogPostData blogPostLanguage = new BlogPostData();
		blogPostLanguage.setLanguage( lang );
		boolean hasAccessToAdd = BlogPostDataUtil.hasAccessToAddBlogPostData( blogPostLanguage );
		
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
			dataModel.put( "blogId", blogId );
			dataModel.put( "hasAccessToAdd", hasAccessToAdd );
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
		UserPratilipiData userPratilipiData = UserPratilipiDataUtil.getUserPratilipi( AccessTokenFilter.getAccessToken().getUserId(), pratilipiId );
		Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
		PratilipiData pratilipiData = PratilipiDataUtil.createPratilipiData( pratilipi, author, false );
		
		pageNo = pageNo < 1 ? 1 : pageNo;
		pageNo = pageNo > pratilipi.getPageCount() ? pratilipi.getPageCount() : pageNo;
		Object content = null;
		if( pratilipi.getContentType() == PratilipiContentType.PRATILIPI )
			content = PratilipiDataUtil.getPratilipiContent( pratilipiId, null, pageNo, pratilipi.getContentType() );
		else if( pratilipi.getContentType() == PratilipiContentType.IMAGE )
			content = "<img src=\"/api/pratilipi/content?pratilipiId=" + pratilipi.getId() + "&pageNo=" + pageNo + "&chapterNo=" + pageNo + "\" />";
		
		Gson gson = new Gson();
		PratilipiApi.Response pratilipiResponse = new PratilipiApi.Response( pratilipiData );
		UserPratilipiApi.Response userPratilipiResponse = userPratilipiData != null ?
						new UserPratilipiApi.Response( userPratilipiData ) : null;
		
		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "title", createReadPageTitle( pratilipiData, 1, 1 ) );
		dataModel.put( "pageNo", pageNo );
		dataModel.put( "pageCount", pratilipi.getPageCount() );
		dataModel.put( "content", content );
		dataModel.put( "contentType", pratilipi.getContentType() );
		if( basicMode ) {
			dataModel.put( "pratilipi", pratilipiResponse );
			dataModel.put( "userpratilipi", userPratilipiResponse );
			dataModel.put( "indexList", gson.fromJson( pratilipi.getIndex(), Object.class ) );
			
		} else {
			dataModel.put( "pratilipiJson", gson.toJson( pratilipiResponse ) );
			dataModel.put( "userpratilipiJson", gson.toJson( userPratilipiResponse ) );
			dataModel.put( "indexJson", gson.toJson( pratilipi.getIndex() ) );
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
			String pageNoStr = request.getParameter( RequestParameter.LIST_PAGE_NUMBER.getName() );
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
		
		Integer offset = null;
		Integer pageCurr = 1;
		Integer pageSize = 20;
		
		if( basicMode ) {
			String pageNoStr = request.getParameter( RequestParameter.LIST_PAGE_NUMBER.getName() );
			if( pageNoStr != null && ! pageNoStr.trim().isEmpty() ) {
				pageCurr = Integer.parseInt( pageNoStr );
				offset = ( pageCurr - 1 ) * pageSize;
			}
		}
		
		PratilipiListApi.GetRequest pratilipiListRequest = new PratilipiListApi.GetRequest();
		pratilipiListRequest.setListName( listName );
		pratilipiListRequest.setLanguage( filterLanguage );
		pratilipiListRequest.setType( type );
		pratilipiListRequest.setState( PratilipiState.PUBLISHED );
		pratilipiListRequest.setOffset( offset );
		PratilipiListApi.Response pratilipiListResponse = ApiRegistry
				.getApi( PratilipiListApi.class )
				.get( pratilipiListRequest );
		
		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		pratilipiFilter.setLanguage( filterLanguage );
		pratilipiFilter.setType( type );
		pratilipiFilter.setListName( listName );
		pratilipiFilter.setState( PratilipiState.PUBLISHED );

		
		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "title", title );
		dataModel.put( "pratilipiListTitle", listTitle );
		if( basicMode ) {
			dataModel.put( "pratilipiList", pratilipiListResponse.getPratilipiList() );
			dataModel.put( "pratilipiListPageCurr", pageCurr );
			if( pratilipiListResponse.getNumberFound() != null )
				dataModel.put( "pratilipiListPageMax", (int) Math.ceil( ( (double) pratilipiListResponse.getNumberFound() ) / pageSize ) );
		} else {
			Gson gson = new Gson();
			dataModel.put( "pratilipiListJson", gson.toJson( pratilipiListResponse.getPratilipiList() ) );
			dataModel.put( "pratilipiListFilterJson", gson.toJson( pratilipiFilter ) );
			dataModel.put( "pratilipiListCursor", pratilipiListResponse.getCursor() );
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


	private List<PratilipiApi.Response> toListResponseObject( List<PratilipiData> pratilipiDataList ) {
		List<PratilipiApi.Response> pratilipiList = new ArrayList<>( pratilipiDataList.size() );
		for( PratilipiData pratilipiData : pratilipiDataList )
			pratilipiList.add( new PratilipiApi.Response( pratilipiData, true ) );
		return pratilipiList;
	}

	private List<UserPratilipiApi.Response> toGenericReviewResponseList( List<UserPratilipiData> userPratilipiList ) {
		List<UserPratilipiApi.Response> reviewList = new ArrayList<>( userPratilipiList.size() );
		for( UserPratilipiData userPratilipiData : userPratilipiList )
			reviewList.add( new UserPratilipiApi.Response( userPratilipiData, true ) );
		return reviewList;
	}
	
}
