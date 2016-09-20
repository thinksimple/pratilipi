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
import com.pratilipi.api.impl.author.AuthorListApi;
import com.pratilipi.api.impl.blogpost.BlogPostApi;
import com.pratilipi.api.impl.blogpost.BlogPostListApi;
import com.pratilipi.api.impl.event.EventApi;
import com.pratilipi.api.impl.event.EventListApi;
import com.pratilipi.api.impl.notification.NotificationListApi;
import com.pratilipi.api.impl.pratilipi.PratilipiApi;
import com.pratilipi.api.impl.pratilipi.PratilipiListApi;
import com.pratilipi.api.impl.user.UserApi;
import com.pratilipi.api.impl.userauthor.UserAuthorFollowApi;
import com.pratilipi.api.impl.userauthor.UserAuthorFollowListApi;
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
import com.pratilipi.common.util.FacebookApi;
import com.pratilipi.common.util.FreeMarkerUtil;
import com.pratilipi.common.util.PratilipiFilter;
import com.pratilipi.common.util.ThirdPartyResource;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.client.BlogPostData;
import com.pratilipi.data.client.EventData;
import com.pratilipi.data.client.PratilipiData;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.client.UserPratilipiData;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Blog;
import com.pratilipi.data.type.Navigation;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;
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
			resourceList.add( ThirdPartyResource.BOOTSTRAP_JS.getTag() );
			resourceList.add( ThirdPartyResource.BOOTSTRAP_CSS.getTag() );
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

			} else if( uri.equals( "/create-content" ) ) { // Standard Mode only
				dataModel = new HashMap<String, Object>();
				dataModel.put( "title", "Create Content" );
				templateName = templateFilePrefix + "Content.ftl";

			} else if( uri.equals( "/edit-content" ) ) { // Standard Mode only

				Long pratilipiId = Long.parseLong( request.getParameter( RequestParameter.PRATILIPI_ID.getName() ) );
				PratilipiApi.GetRequest pratilipiRequest = new PratilipiApi.GetRequest();
				pratilipiRequest.setPratilipiId( pratilipiId );
				PratilipiApi.Response pratilipiResponse = ApiRegistry.getApi( PratilipiApi.class ).get( pratilipiRequest );

				dataModel = new HashMap<String, Object>();
				dataModel.put( "title", "Edit Content" );
				dataModel.put( "pratilipiJson", new Gson().toJson( pratilipiResponse ) );
				templateName = templateFilePrefix + "Content.ftl";

			} else if( uri.equals( "/followers" ) ) {

				Long authorId = null;

				if( request.getParameter( RequestParameter.AUTHOR_ID.getName() ) != null ) {
					authorId = Long.parseLong( request.getParameter( RequestParameter.AUTHOR_ID.getName() ) );
				} else {
					Long userId = AccessTokenFilter.getAccessToken().getUserId();
					if( userId != null && userId != 0L )
						authorId = dataAccessor.getAuthorByUserId( userId ).getId();
				}

				Integer currentPage = 	request.getParameter( RequestParameter.LIST_PAGE_NUMBER.getName() ) != null &&
										! request.getParameter( RequestParameter.LIST_PAGE_NUMBER.getName() ).trim().isEmpty() ? 
						Integer.parseInt( request.getParameter( RequestParameter.LIST_PAGE_NUMBER.getName() ) ) : 1;

				if( authorId == null ) {
					dataModel = new HashMap<String, Object>();
					dataModel.put( "title", I18n.getString( "author_followers", filterLanguage ) );
				} else {
					dataModel = createDataModelForFollowersPage( authorId, currentPage, filterLanguage, basicMode );
				}

				templateName = templateFilePrefix + ( basicMode ? "FollowersListBasic.ftl" : "FollowersList.ftl" );

			} else if( uri.equals( "/following" ) ) {

				Long userId = null;

				if( request.getParameter( RequestParameter.USER_ID.getName() ) != null )
					userId = Long.parseLong( request.getParameter( RequestParameter.USER_ID.getName() ) ); 
				else
					userId = AccessTokenFilter.getAccessToken().getUserId();

				Integer currentPage = 	request.getParameter( RequestParameter.LIST_PAGE_NUMBER.getName() ) != null &&
										! request.getParameter( RequestParameter.LIST_PAGE_NUMBER.getName() ).trim().isEmpty() ? 
						Integer.parseInt( request.getParameter( RequestParameter.LIST_PAGE_NUMBER.getName() ) ) : 1;

				if( userId == null || userId == 0L ) {
					dataModel = new HashMap<String, Object>();
					dataModel.put( "title", I18n.getString( "author_following", filterLanguage ) );
				} else {
					dataModel = createDataModelForFollowingPage( userId, currentPage, filterLanguage, basicMode );
				}

				templateName = templateFilePrefix + ( basicMode ? "FollowingListBasic.ftl" : "FollowingList.ftl" );

			} else if( page != null && page.getType() == PageType.PRATILIPI ) {
				resourceList.addAll( createFbOpenGraphTags( page.getPrimaryContentId() ) );
				dataModel = createDataModelForPratilipiPage( page.getPrimaryContentId(), basicMode, request );
				templateName = templateFilePrefix + ( basicMode ? "PratilipiBasic.ftl" : "Pratilipi.ftl" );
				
			} else if( page != null && page.getType() == PageType.AUTHOR ) {
				dataModel = createDataModelForAuthorPage( page.getPrimaryContentId(), basicMode, request );
				templateName = templateFilePrefix + ( basicMode ? "AuthorBasic.ftl" : "Author.ftl" );

			} else if( page != null && page.getType() == PageType.EVENT ) {
				dataModel = createDataModelForEventPage( page.getPrimaryContentId(), basicMode, request );
				templateName = templateFilePrefix + ( basicMode ? "EventBasic.ftl" : "Event.ftl" );
			
			} else if( page != null && page.getType() == PageType.BLOG ) {
				dataModel = createDataModelForBlogPage( page.getPrimaryContentId(), filterLanguage, basicMode );
				templateName = templateFilePrefix + ( basicMode ? "BlogPostListBasic.ftl" : "BlogPostList.ftl" );
			
			} else if( page != null && page.getType() == PageType.BLOG_POST ) {
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
			
			} else if( uri.equals( "/search" ) ) {
				dataModel = createDataModelForSearchPage( basicMode, filterLanguage, request );
				templateName = templateFilePrefix + ( basicMode ? "SearchBasic.ftl" : "Search.ftl" );

			} else if( uri.equals( "/share" ) ) {
				dataModel = new HashMap<String, Object>();
				dataModel.put( "title", "Share" );
				templateName = templateFilePrefix + ( basicMode ? "ShareBasic.ftl" : "Share.ftl" );
			} else if( uri.equals( "/notifications" ) ) {
				dataModel = createDataModelForNotificationsPage( filterLanguage, basicMode );
				templateName = templateFilePrefix + ( basicMode ? "NotificationBasic.ftl" : "Notification.ftl" );

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
				
			} else if( uri.equals( "/pratilipi-write" ) ) {
				dataModel = new HashMap<String, Object>();
				dataModel.put( "title", "Writer Panel" );

				if( request.getParameter( "action" ) != null )
					dataModel.put( "action", request.getParameter( "action" ) );

				if( request.getParameter( RequestParameter.READER_CONTENT_ID.getName() ) != null )
					dataModel.put( "pratilipiId", Long.parseLong( request.getParameter( RequestParameter.READER_CONTENT_ID.getName() ) ) );

				if( request.getParameter( RequestParameter.AUTHOR_ID.getName() ) != null )
					dataModel.put( "authorId", Long.parseLong( request.getParameter( RequestParameter.AUTHOR_ID.getName() ) ) );

				templateName = templateFilePrefix + "Writer.ftl";
				
			// Internal links
				
			} else if( ! basicMode && uri.equals( "/authors" ) ) {
				dataModel = createDataModelForAuthorsPage( filterLanguage );
				templateName = templateFilePrefix + "AuthorList.ftl";
			
			} else if( uri.equals( "/events" ) ) {
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
		UserApi.Response userResponse = new UserApi.Response( userData, UserApi.class );

		Map<PratilipiType, Map<String, String>> pratilipiTypes = new HashMap<>();
		for( PratilipiType pratilipiType : PratilipiType.values() ) {
			Map<String, String> pratilipiTypeMap = new HashMap<>();
			pratilipiTypeMap.put( "name", I18n.getString( pratilipiType.getStringId(), displayLanguage ) );
			pratilipiTypeMap.put( "namePlural", I18n.getString( pratilipiType.getPluralStringId(), displayLanguage ) );
			pratilipiTypes.put( pratilipiType, pratilipiTypeMap );
		}

		Map<String, String> languageMap = new HashMap<String, String>();
		for( Website website : Website.values() ) {
			if( ! website.toString().contains( "GAMMA" ) && 
					! website.toString().contains( "DEVO" ) &&
					! website.toString().contains( "ALPHA" ) &&
					website != Website.ALL_LANGUAGE ) {
				languageMap.put( website.toString(), website.getFilterLanguage().getName() );
			}
		}

		dataModel.put( "ga_userId", userData.getId().toString() );
		dataModel.put( "ga_website", UxModeFilter.getWebsite().toString() );
		dataModel.put( "ga_websiteMode", UxModeFilter.isBasicMode() ? "Basic" : "Standard" );
		dataModel.put( "ga_websiteVersion", "Mark-6" );
		
		dataModel.put( "lang", displayLanguage.getCode() );
		dataModel.put( "language", displayLanguage );
		dataModel.put( "website_host", UxModeFilter.getWebsite().getHostName() );
		dataModel.put( "website_mobile_host", UxModeFilter.getWebsite().getMobileHostName() );
		dataModel.put( "languageMap", new Gson().toJson( languageMap ) );
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
		
		String title = createAuthorPageTitle( new AuthorApi.Response( pratilipiData.getAuthor(), AuthorListApi.class ) );
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
	private List<String> createFbOpenGraphTags( Long pratilipiId ) throws UnexpectedServerException {
		
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
			throws InsufficientAccessException, IOException, UnexpectedServerException {

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

	private Map<String, Object> createDataModelForLibraryPage( boolean basicMode, Language filterLanguage ) throws UnexpectedServerException {
		
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
			String action = request.getParameter( "action" ) != null ? request.getParameter( "action" ) : "content_page";
			dataModel.put( "action", action );
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
	
	public Map<String, Object> createDataModelForAuthorPage( Long authorId, boolean basicMode, HttpServletRequest request )
			throws InsufficientAccessException, UnexpectedServerException {

		Map<String, Object> dataModel = new HashMap<String, Object>();
		Gson gson = new Gson();

		AuthorApi.GetRequest authorApiGetRequest = new AuthorApi.GetRequest();
		authorApiGetRequest.setAuthorId( authorId );
		AuthorApi.Response authorResponse = ApiRegistry
				.getApi( AuthorApi.class )
				.get( authorApiGetRequest );
		dataModel.put( "title", createAuthorPageTitle( authorResponse ) );
		if( basicMode )
			dataModel.put( "author", authorResponse );
		else
			dataModel.put( "authorJson", gson.toJson( authorResponse ) );

		String action = request.getParameter( "action" ) != null ? request.getParameter( "action" ) : "author_page";
		dataModel.put( "action", action );

		if( basicMode && action.equals( "list_contents" ) ) {

			Integer pageCurr = request.getParameter( RequestParameter.LIST_PAGE_NUMBER.getName() ) != null
							? Integer.parseInt( request.getParameter( RequestParameter.LIST_PAGE_NUMBER.getName() ) )
							: 1;

			PratilipiState pratilipiState = request.getParameter( RequestParameter.PRATILIPI_STATE.getName() ) != null
										? PratilipiState.valueOf( request.getParameter( RequestParameter.PRATILIPI_STATE.getName() ) )
										: PratilipiState.PUBLISHED;

			Integer resultCount = 10;
			PratilipiListApi.GetRequest pratilipiListRequest = new PratilipiListApi.GetRequest();
			pratilipiListRequest.setAuthorId( authorId );
			pratilipiListRequest.setState( pratilipiState );
			pratilipiListRequest.setResultCount( resultCount );
			pratilipiListRequest.setOffset( ( pageCurr - 1 ) * resultCount );
			PratilipiListApi.Response pratilipiListResponse = ApiRegistry
								.getApi( PratilipiListApi.class )
								.get( pratilipiListRequest );

			dataModel.put( "state", pratilipiState.toString() );
			dataModel.put( "pratilipiList", pratilipiListResponse.getPratilipiList() );
			dataModel.put( "pratilipiListPageCurr", pageCurr );
			Integer pageMax = pratilipiListResponse.getNumberFound() != null ?
					(int) Math.ceil( ( (double) pratilipiListResponse.getNumberFound() ) / resultCount ) : 1;
			dataModel.put( "pratilipiListPageMax", pageMax );

			return dataModel;

		}

		UserAuthorFollowApi.GetRequest getRequest = new UserAuthorFollowApi.GetRequest();
		getRequest.setAuthorId( authorId );
		UserAuthorFollowApi.Response userAuthorResponse = ApiRegistry
				.getApi( UserAuthorFollowApi.class )
				.get( getRequest );

		Integer followResultCount = basicMode ? 3 : 20;
		UserAuthorFollowListApi.GetRequest followersListRequest = new UserAuthorFollowListApi.GetRequest();
		followersListRequest.setAuthorId( authorId );
		followersListRequest.setResultCount( followResultCount );
		UserAuthorFollowListApi.Response followersList = ApiRegistry
				.getApi( UserAuthorFollowListApi.class )
				.get( followersListRequest );

		UserAuthorFollowListApi.GetRequest followingListRequest = new UserAuthorFollowListApi.GetRequest();
		followingListRequest.setUserId( authorResponse.getUser().getId() );
		followingListRequest.setResultCount( followResultCount );
		UserAuthorFollowListApi.Response followingList= ApiRegistry
				.getApi( UserAuthorFollowListApi.class )
				.get( followingListRequest );

		Integer resultCount = basicMode ? 3 : 12;
		PratilipiListApi.GetRequest publishedPratilipiListRequest = new PratilipiListApi.GetRequest();
		publishedPratilipiListRequest.setAuthorId( authorId );
		publishedPratilipiListRequest.setState( PratilipiState.PUBLISHED );
		publishedPratilipiListRequest.setResultCount( resultCount );
		PratilipiListApi.Response publishedPratilipiListResponse = ApiRegistry
				.getApi( PratilipiListApi.class )
				.get( publishedPratilipiListRequest );

		if( basicMode ) {
			dataModel.put( "userAuthor", userAuthorResponse );
			dataModel.put( "followersList", followersList );
			dataModel.put( "followingList", followingList );
			dataModel.put( "publishedPratilipiList", publishedPratilipiListResponse.getPratilipiList() );
		} else {
			dataModel.put( "userAuthorJson", gson.toJson( userAuthorResponse ) );
			dataModel.put( "followersListJson", gson.toJson( followersList ) );
			dataModel.put( "followingListJson", gson.toJson( followingList ) );
			dataModel.put( "publishedPratilipiListObjectJson", gson.toJson( publishedPratilipiListResponse ) );
		}

		if( basicMode && authorResponse.hasAccessToUpdate() ) {
			PratilipiListApi.GetRequest draftedPratilipiListRequest = new PratilipiListApi.GetRequest();
			draftedPratilipiListRequest.setAuthorId( authorId );
			draftedPratilipiListRequest.setState( PratilipiState.DRAFTED );
			draftedPratilipiListRequest.setResultCount( resultCount );
			PratilipiListApi.Response draftedPratilipiListResponse = ApiRegistry
					.getApi( PratilipiListApi.class )
					.get( draftedPratilipiListRequest );
			dataModel.put( "draftedPratilipiList", draftedPratilipiListResponse.getPratilipiList() );
		}

		return dataModel;
		
	}

	public Map<String, Object> createDataModelForFollowersPage( Long authorId, Integer currPage, Language language, Boolean basicMode ) 
			throws InsufficientAccessException {

		AuthorApi.GetRequest authorApiGetRequest = new AuthorApi.GetRequest();
		authorApiGetRequest.setAuthorId( authorId );
		AuthorApi.Response authorResponse = ApiRegistry
				.getApi( AuthorApi.class )
				.get( authorApiGetRequest );

		Integer resultCount = 10;
		UserAuthorFollowListApi.GetRequest followersListRequest = new UserAuthorFollowListApi.GetRequest();
		followersListRequest.setAuthorId( authorId );
		followersListRequest.setResultCount( resultCount );
		followersListRequest.setOffset( ( currPage - 1 ) * resultCount );
		UserAuthorFollowListApi.Response followersList = ApiRegistry
				.getApi( UserAuthorFollowListApi.class )
				.get( followersListRequest );

		Map<String, Object> dataModel = new HashMap<String, Object>();
		Gson gson = new Gson();
		dataModel.put( "title", I18n.getString( "author_followers_heading", language ) + " | " + createAuthorPageTitle( authorResponse ) );
		if( basicMode ) {
			dataModel.put( "author", authorResponse );
			dataModel.put( "followersList", followersList );
			dataModel.put( "currPage", currPage );
			dataModel.put( "maxPage", followersList.getNumberFound() % resultCount == 0 ? 
					followersList.getNumberFound() / resultCount :  followersList.getNumberFound() / resultCount + 1 );
		} else {
			dataModel.put( "authorJson", gson.toJson( authorResponse ) );
			dataModel.put( "followersObjectJson", gson.toJson( followersList ) );
		}

		return dataModel;

	}

	public Map<String, Object> createDataModelForFollowingPage( Long userId, Integer currPage, Language language, Boolean basicMode ) 
			throws InsufficientAccessException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Long authorId = dataAccessor.getAuthorByUserId( userId ).getId();

		AuthorApi.GetRequest authorApiGetRequest = new AuthorApi.GetRequest();
		authorApiGetRequest.setAuthorId( authorId );
		AuthorApi.Response authorResponse = ApiRegistry
				.getApi( AuthorApi.class )
				.get( authorApiGetRequest );

		Integer resultCount = 10;
		UserAuthorFollowListApi.GetRequest followingListRequest = new UserAuthorFollowListApi.GetRequest();
		followingListRequest.setUserId( userId );
		followingListRequest.setResultCount( resultCount );
		followingListRequest.setOffset( ( currPage - 1 ) * resultCount );
		UserAuthorFollowListApi.Response followingList= ApiRegistry
				.getApi( UserAuthorFollowListApi.class )
				.get( followingListRequest );

		Map<String, Object> dataModel = new HashMap<String, Object>();
		Gson gson = new Gson();
		dataModel.put( "title", I18n.getString( "author_following_heading", language ) + " | " + createAuthorPageTitle( authorResponse ) );
		if( basicMode ) {
			dataModel.put( "author", authorResponse );
			dataModel.put( "followingList", followingList );
			dataModel.put( "currPage", currPage );
			dataModel.put( "maxPage", followingList.getNumberFound() % resultCount == 0 ? 
					followingList.getNumberFound() / resultCount :  followingList.getNumberFound() / resultCount + 1 );
		} else {
			dataModel.put( "authorJson", gson.toJson( authorResponse ) );
			dataModel.put( "followingObjectJson", gson.toJson( followingList ) );
		}

		return dataModel;
	}

	public Map<String, Object> createDataModelForAuthorsPage( Language language )
			throws InsufficientAccessException {

		AuthorListApi.GetRequest request = new AuthorListApi.GetRequest();
		request.setLanguage( language );
		AuthorListApi.Response response = ApiRegistry
											.getApi( AuthorListApi.class )
											.get( request );

		Gson gson = new Gson();
		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "title", "Authors" );
		dataModel.put( "authorListJson", gson.toJson( response.getAuthorList() ) );
		dataModel.put( "authorListFilterJson", gson.toJson( request ) );
		dataModel.put( "authorListCursor", response.getCursor() );
		return dataModel;
		
	}
	
	public Map<String, Object> createDataModelForEventsPage( Language language, boolean basicMode ) throws InsufficientAccessException {

		EventData eventData = new EventData();
		eventData.setLanguage( language );
		boolean hasAccessToAdd = EventDataUtil.hasAccessToAddEventData( eventData );

		EventListApi.GetRequest request = new EventListApi.GetRequest();
		request.setLanguage( language );
		EventListApi.GetResponse eventListResponse = ApiRegistry
											.getApi( EventListApi.class )
											.get( request );

		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "title", I18n.getString( "event_events", language ) );
		dataModel.put( "hasAccessToAdd", hasAccessToAdd );
		if( basicMode ) {
			dataModel.put( "eventList", eventListResponse.getEventList() );
		} else {
			dataModel.put( "eventListJson", new Gson().toJson( eventListResponse.getEventList() ) );
		}
		return dataModel;
	}
	
	public Map<String, Object> createDataModelForEventPage( Long eventId, boolean basicMode, HttpServletRequest request )
			throws InsufficientAccessException, UnexpectedServerException {

		Map<String, Object> dataModel = new HashMap<String, Object>();
		Gson gson = new Gson();

		EventApi.GetRequest eventRequest = new EventApi.GetRequest();
		eventRequest.setEventId( eventId );
		EventApi.Response eventResponse = ApiRegistry
											.getApi( EventApi.class )
											.get( eventRequest );

		Integer resultCount = basicMode ? 10 : 12;
		PratilipiListApi.GetRequest pratilipiListApiRequest = new PratilipiListApi.GetRequest();
		pratilipiListApiRequest.setEventId( eventId );
		pratilipiListApiRequest.setState( PratilipiState.PUBLISHED );
		pratilipiListApiRequest.setResultCount( resultCount );

		String action = request.getParameter( "action" ) != null ? request.getParameter( "action" ) : "event_page";
		Integer pageCurr = null;
		if( basicMode && action.equals( "list_contents" ) ) {
			pageCurr = request.getParameter( RequestParameter.LIST_PAGE_NUMBER.getName() ) != null
					? Integer.parseInt( request.getParameter( RequestParameter.LIST_PAGE_NUMBER.getName() ) )
					: 1;
			pratilipiListApiRequest.setOffset( ( pageCurr - 1 ) * resultCount );
		}

		PratilipiListApi.Response pratilipiListApiResponse = ApiRegistry
									.getApi( PratilipiListApi.class )
									.get( pratilipiListApiRequest );

		dataModel.put( "title", createPageTitle( eventResponse.getName(), eventResponse.getNameEn() ) );

		if( basicMode ) {
			dataModel.put( "action", action );
			dataModel.put( "event", eventResponse );
			dataModel.put( "pratilipiList", pratilipiListApiResponse.getPratilipiList() );
			dataModel.put( "numberFound", pratilipiListApiResponse.getNumberFound() );
			dataModel.put( "pratilipiListPageCurr", pageCurr );
			Integer pageMax = pratilipiListApiResponse.getNumberFound() != null ?
					(int) Math.ceil( ( (double) pratilipiListApiResponse.getNumberFound() ) / resultCount ) : 1;
			dataModel.put( "pratilipiListPageMax", pageMax );
		} else {
			dataModel.put( "eventJson", gson.toJson( eventResponse ) );
			dataModel.put( "pratilipiListObjectJson", gson.toJson( pratilipiListApiResponse ) );
		}
		return dataModel;
		
	}
	
	public Map<String, Object> createDataModelForBlogPage( Long blogId, Language language, boolean basicMode ) 
			throws InsufficientAccessException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Blog blog = dataAccessor.getBlog( blogId );

		BlogPostListApi.GetRequest request = new BlogPostListApi.GetRequest();
		request.setBlogId( blogId );
		request.setLangugage( language );
		request.setState( BlogPostState.PUBLISHED );
		BlogPostListApi.Response blogPostList = ApiRegistry
				.getApi( BlogPostListApi.class )
				.get( request );

		BlogPostData blogPostLanguage = new BlogPostData();
		blogPostLanguage.setLanguage( language );
		boolean hasAccessToAdd = BlogPostDataUtil.hasAccessToAddBlogPostData( blogPostLanguage );

		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "title", blog.getTitle() );
		if( basicMode ) {
			dataModel.put( "blogPostList", blogPostList.getBlogPostList() );
		} else {
			Gson gson = new Gson();
			dataModel.put( "blogId", blogId );
			dataModel.put( "hasAccessToAdd", hasAccessToAdd );
			dataModel.put( "blogPostListJson", gson.toJson( blogPostList.getBlogPostList() ) );
			dataModel.put( "blogPostFilterJson", gson.toJson( request ) );
			dataModel.put( "blogPostListCursor", blogPostList.getCursor() );
		}
		return dataModel;
	}

	public Map<String, Object> createDataModelForBlogPostPage( Long blogPostId, boolean basicMode )
			throws InsufficientAccessException {

		BlogPostApi.GetRequest request = new BlogPostApi.GetRequest();
		request.setBlogPostId( blogPostId );
		BlogPostApi.Response response = ApiRegistry
				.getApi( BlogPostApi.class )
				.get( request ); 

		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "title", createPageTitle( response.getTitle(), response.getTitleEn() ) );
		if( basicMode ) {
			dataModel.put( "blogPost", response );
		} else {
			dataModel.put( "blogPostJson", new Gson().toJson( response ) );
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

	
	private Map<String, Object> createDataModelForSearchPage( boolean basicMode, Language language, HttpServletRequest request )
			throws InsufficientAccessException, UnexpectedServerException {
		
		String searchQuery = request.getParameter( RequestParameter.SEARCH_QUERY.getName() );
		if( searchQuery == null || searchQuery.trim().isEmpty() )
			searchQuery = null;

		Long authorId = null;
		String authorIdString = request.getParameter( RequestParameter.AUTHOR_ID.getName() );
		if( authorIdString != null && ! authorIdString.trim().isEmpty() )
			authorId = Long.parseLong( authorIdString );

		PratilipiState pratilipiState = PratilipiState.PUBLISHED;
		String state = request.getParameter( RequestParameter.PRATILIPI_STATE.getName() );
		if( state != null && ! state.trim().isEmpty() )
			pratilipiState = PratilipiState.valueOf( state );

		Integer resultCount = 20;
		String resultCountString = request.getParameter( RequestParameter.RESULT_COUNT.getName() );
		if( resultCountString != null && ! resultCountString.trim().isEmpty() )
			resultCount = Integer.parseInt( resultCountString );

		Integer pageCurr = 1;
		String pageNoString = request.getParameter( RequestParameter.LIST_PAGE_NUMBER.getName() );
		if( pageNoString != null && ! pageNoString.trim().isEmpty() )
			pageCurr = Integer.parseInt( pageNoString );


		PratilipiListApi.GetRequest pratilipiListApiRequest = new PratilipiListApi.GetRequest();
		pratilipiListApiRequest.setLanguage( language );
		pratilipiListApiRequest.setState( pratilipiState );
		pratilipiListApiRequest.setResultCount( resultCount );
		pratilipiListApiRequest.setOffset( ( pageCurr - 1 ) * resultCount );
		if( searchQuery != null )
			pratilipiListApiRequest.setSearchQuery( searchQuery );
		if( authorId != null )
			pratilipiListApiRequest.setAuthorId( authorId );
		PratilipiListApi.Response response = ApiRegistry
												.getApi( PratilipiListApi.class )
												.get( pratilipiListApiRequest );


		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		pratilipiFilter.setLanguage( language );
		pratilipiFilter.setState( pratilipiState );

		Gson gson = new Gson();

		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "title", "Search" );
		if( basicMode ) {
			dataModel.put( "pratilipiList", response.getPratilipiList() );
			dataModel.put( "pratilipiListSearchQuery", searchQuery );
			dataModel.put( "pratilipiListPageCurr", pageCurr );
			Integer pageMax = response.getNumberFound() != null ?
					(int) Math.ceil( ( (double) response.getNumberFound() ) / resultCount ) : 1;
			dataModel.put( "pratilipiListPageMax", pageMax );
		} else {
			dataModel.put( "pratilipiListJson", gson.toJson( response.getPratilipiList() ) );
			dataModel.put( "pratilipiListSearchQuery", searchQuery );
			dataModel.put( "pratilipiListFilterJson", gson.toJson( pratilipiFilter ) );
			dataModel.put( "pratilipiListCursor", response.getCursor() );
		}
		return dataModel;
		
	}

	private Map<String, Object> createDataModelForNotificationsPage( Language language, Boolean basicMode ) 
			throws InsufficientAccessException, UnexpectedServerException {

		NotificationListApi.Response response = ApiRegistry
				.getApi( NotificationListApi.class )
				.get( new NotificationListApi.GetRequest() );

		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "title", I18n.getString( "notification_notifications", language ) );
		dataModel.put( "notificationList", response.getNotificationList() );
		dataModel.put( "notificationListJson", new Gson().toJson( response ) );

		return dataModel;
	}

	private Map<String, Object> createDataModelForListPage( PratilipiType type,
			boolean basicMode, Language displayLanguage, Language filterLanguage,
			HttpServletRequest request ) throws InsufficientAccessException, UnexpectedServerException {
		
		return createDataModelForListPage( type, null, basicMode, displayLanguage, filterLanguage, request );
	}

	private Map<String, Object> createDataModelForListPage( String listName,
			boolean basicMode, Language displayLanguage, Language filterLanguage,
			HttpServletRequest request ) throws InsufficientAccessException, UnexpectedServerException {

		return createDataModelForListPage( null, listName, basicMode, displayLanguage, filterLanguage, request );
		
	}
	
	private Map<String, Object> createDataModelForListPage( PratilipiType type, String listName,
			boolean basicMode, Language displayLanugage, Language filterLanguage,
			HttpServletRequest request ) throws InsufficientAccessException, UnexpectedServerException {

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
