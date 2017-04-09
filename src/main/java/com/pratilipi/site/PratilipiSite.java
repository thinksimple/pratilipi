package com.pratilipi.site;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pratilipi.api.ApiRegistry;
import com.pratilipi.api.batchprocess.BatchProcessListApi;
import com.pratilipi.api.impl.author.AuthorApi;
import com.pratilipi.api.impl.author.AuthorListApi;
import com.pratilipi.api.impl.blogpost.BlogPostApi;
import com.pratilipi.api.impl.blogpost.BlogPostListApi;
import com.pratilipi.api.impl.event.EventApi;
import com.pratilipi.api.impl.event.EventListApi;
import com.pratilipi.api.impl.init.InitV1Api.Response.Section;
import com.pratilipi.api.impl.init.InitV2Api;
import com.pratilipi.api.impl.notification.NotificationListApi;
import com.pratilipi.api.impl.pratilipi.PratilipiContentIndexApi;
import com.pratilipi.api.impl.pratilipi.PratilipiContentV1Api;
import com.pratilipi.api.impl.pratilipi.PratilipiContentV2Api;
import com.pratilipi.api.impl.pratilipi.PratilipiContentV3Api;
import com.pratilipi.api.impl.pratilipi.PratilipiListV2Api;
import com.pratilipi.api.impl.pratilipi.PratilipiV2Api;
import com.pratilipi.api.impl.user.UserV1Api;
import com.pratilipi.api.impl.userauthor.UserAuthorFollowListApi;
import com.pratilipi.api.impl.userauthor.UserAuthorFollowV1Api;
import com.pratilipi.api.impl.userpratilipi.UserPratilipiApi;
import com.pratilipi.api.impl.userpratilipi.UserPratilipiReviewListApi;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.AccessType;
import com.pratilipi.common.type.BatchProcessType;
import com.pratilipi.common.type.BlogPostState;
import com.pratilipi.common.type.EmailType;
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
import com.pratilipi.common.util.SEOTitleUtil;
import com.pratilipi.common.util.SystemProperty;
import com.pratilipi.common.util.ThirdPartyResource;
import com.pratilipi.common.util.UserAccessUtil;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.client.BlogPostData;
import com.pratilipi.data.client.EventData;
import com.pratilipi.data.client.PratilipiData;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Blog;
import com.pratilipi.data.type.Navigation;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.PratilipiContentDoc;
import com.pratilipi.data.type.PratilipiContentDoc.Chapter;
import com.pratilipi.data.util.BlogPostDataUtil;
import com.pratilipi.data.util.EventDataUtil;
import com.pratilipi.data.util.PageDataUtil;
import com.pratilipi.data.util.PratilipiDataUtil;
import com.pratilipi.data.util.PratilipiDocUtil;
import com.pratilipi.data.util.UserDataUtil;
import com.pratilipi.data.util.UserPratilipiDataUtil;
import com.pratilipi.email.EmailTemplateUtil;
import com.pratilipi.filter.AccessTokenFilter;
import com.pratilipi.filter.UxModeFilter;
import com.pratilipi.i18n.I18n;

@SuppressWarnings("serial")
public class PratilipiSite extends HttpServlet {
	
	private static final Logger logger =
			Logger.getLogger( PratilipiSite.class.getName() );

	private static final String templateFilePrefix = "com/pratilipi/site/page/";
	public static final String dataFilePrefix = "page/data/";

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

		String uri = request.getRequestURI();
		String canonicalUrl = "http://" + UxModeFilter.getWebsite().getHostName() + uri;
		String alternateUrl = "http://" + UxModeFilter.getWebsite().getMobileHostName() + uri;

		// User
		UserData userData = UserDataUtil.getCurrentUser();

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
		Set<String> resourceList = getResourceList( basicMode );

		Set<String> deferredResourceList = new HashSet<>();
		if( basicMode ) {
			resourceList.add( ThirdPartyResource.BOOTSTRAP_CSS.getTag() );
		} else {
			deferredResourceList.add( ThirdPartyResource.BOOTSTRAP_CSS.getTag() );
			deferredResourceList.add( ThirdPartyResource.GOOGLE_TRANSLITERATION.getTag() );
		}

		// Load PWA
		Long uId = AccessTokenFilter.getAccessToken().getUserId();
		boolean loadPWA = ! SystemProperty.STAGE.equals( SystemProperty.STAGE_PROD ) || 
				UserAccessUtil.hasUserAccess( uId, null, AccessType.USER_ADD ) ||
				isEligibleForPWA( userData.getEmail(), filterLanguage );

		// Data Model for FreeMarker
		Map<String, Object> dataModel = null;
		String templateName = null;
		
		try {

			// Search Engine Crawlers
			if( uri.equals( "/sitemap" ) && SystemProperty.STAGE.equals( SystemProperty.STAGE_PROD ) ) {
				String content = PageDataUtil.getSitemap( 
						request.getParameter( RequestParameter.SITEMAP_TYPE.getName() ), 
						request.getParameter( RequestParameter.SITEMAP_CURSOR.getName() ), 
						UxModeFilter.getWebsite(),
						basicMode );
				_dispatchResponse( content, "application/xml", "UTF-8", response );
				return;
			}

			// Robots.txt
			if( uri.equals( "/robots.txt" ) ) {
				dataModel = new HashMap<>();
				dataModel.put( "stage", SystemProperty.STAGE );
				templateName = templateFilePrefix + "RobotsTxt.ftl";
				String robotsTxt = FreeMarkerUtil.processTemplate( dataModel, templateName );
				_dispatchResponse( robotsTxt, "text/plain", "UTF-8", response );
				return;
			}

			// Page Entity
			Page page = dataAccessor.getPage( uri );

			// Hard-coded links
			if( uri.equals( "/" ) ) {
				if( UxModeFilter.getWebsite() == Website.ALL_LANGUAGE || UxModeFilter.getWebsite() == Website.GAMMA_ALL_LANGUAGE ) {
					dataModel = createDataModelForMasterHomePage( filterLanguage );
					templateName = "MasterHome.ftl";
				} else {
					if( loadPWA ) {
						dataModel = new HashMap<>();
						templateName = "HomePWA.ftl";
					} else {
						dataModel = createDataModelForHomePage( basicMode, filterLanguage );
						templateName = ( basicMode ? "HomeBasic.ftl" : "Home.ftl" );
					}
				}

			} else if( uri.equals( "/library" ) ) {
				if( loadPWA ) {
					dataModel = new HashMap<>();
					templateName = "LibraryPWA.ftl";
				} else {
					dataModel = createDataModelForLibraryPage( basicMode, filterLanguage );
					templateName = ( basicMode ? "LibraryBasic.ftl" : "Library.ftl" );
				}

			} else if( uri.equals( "/notifications" ) ) {
				if( loadPWA ) {
					dataModel = new HashMap<>();
					templateName = "NotificationsPWA.ftl";
				} else {
					dataModel = createDataModelForNotificationsPage( filterLanguage, basicMode );
					if( request.getParameter( "action" ) != null )
						dataModel.put( "action", request.getParameter( "action" ) );
					templateName = ( basicMode ? "NotificationBasic.ftl" : "Notification.ftl" );	
				}

			} else if( uri.equals( "/search" ) ) {
				if( request.getQueryString() != null ) {
					canonicalUrl = canonicalUrl + "?" + request.getQueryString();
					alternateUrl = alternateUrl + "?" + request.getQueryString();
				}
				if( loadPWA ) {
					dataModel = new HashMap<>();
					templateName = "SearchPWA.ftl";
				} else {
					dataModel = createDataModelForSearchPage( basicMode, filterLanguage, request );
					templateName = ( basicMode ? "SearchBasic.ftl" : "Search.ftl" );
				}

			} else if( uri.equals( "/events" ) ) {
				dataModel = createDataModelForEventsPage( filterLanguage, basicMode );
				templateName = ( basicMode ? "EventListBasic.ftl" : "EventList.ftl" );

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
					dataModel.put( "title", SEOTitleUtil.getFollowersPageTitle( authorId, filterLanguage ) );
				} else {
					dataModel = createDataModelForFollowersPage( authorId, currentPage, filterLanguage, basicMode );
				}

				templateName = ( basicMode ? "FollowersListBasic.ftl" : "FollowersList.ftl" );

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
					dataModel.put( "title", SEOTitleUtil.getFollowersPageTitle( userId, filterLanguage ) );
				} else {
					dataModel = createDataModelForFollowingPage( userId, currentPage, filterLanguage, basicMode );
				}

				templateName = ( basicMode ? "FollowingListBasic.ftl" : "FollowingList.ftl" );

			} else if( uri.equals( "/pratilipi-2016" ) ) {
				dataModel = new HashMap<String, Object>();
				dataModel.put( "title", "Pratilipi in 2016" );
				templateName = ( basicMode ? "Pratilipi2016Basic.ftl" : "Pratilipi2016.ftl" );


			// Master website specific links
			} else if( filterLanguage == null && uri.equals( "/books" ) ) {
				dataModel = createDataModelForListPage( PratilipiType.BOOK, basicMode, displayLanguage, filterLanguage, request );
				templateName = ( basicMode ? "ListBasic.ftl" : "List.ftl" );
				
			} else if( filterLanguage == null && uri.equals( "/stories" ) ) {
				dataModel = createDataModelForListPage( PratilipiType.STORY, basicMode, displayLanguage, filterLanguage, request );
				templateName = ( basicMode ? "ListBasic.ftl" : "List.ftl" );
			
			} else if( filterLanguage == null && uri.equals( "/poems" ) ) {
				dataModel = createDataModelForListPage( PratilipiType.POEM, basicMode, displayLanguage, filterLanguage, request );
				templateName = ( basicMode ? "ListBasic.ftl" : "List.ftl" );
				
			} else if( filterLanguage == null && uri.equals( "/articles" ) ) {
				dataModel = createDataModelForListPage( PratilipiType.ARTICLE, basicMode, displayLanguage, filterLanguage, request );
				templateName = ( basicMode ? "ListBasic.ftl" : "List.ftl" );
				
			} else if( filterLanguage == null && uri.equals( "/magazines" ) ) {
				dataModel = createDataModelForListPage( PratilipiType.MAGAZINE, basicMode, displayLanguage, filterLanguage, request );
				templateName = ( basicMode ? "ListBasic.ftl" : "List.ftl" );

			// Gujarati website specific links
			} else if( filterLanguage == Language.GUJARATI && uri.equals( "/short-stories" ) ) {
				dataModel = createDataModelForListPage( PratilipiType.STORY, basicMode, displayLanguage, filterLanguage, request );
				templateName = ( basicMode ? "ListBasic.ftl" : "List.ftl" );
			
			} else if( filterLanguage == Language.GUJARATI && uri.equals( "/poetry" ) ) {
				dataModel = createDataModelForListPage( PratilipiType.POEM, basicMode, displayLanguage, filterLanguage, request );
				templateName = ( basicMode ? "ListBasic.ftl" : "List.ftl" );
				
			} else if( filterLanguage == Language.GUJARATI && uri.equals( "/non-fiction" ) ) {
				dataModel = createDataModelForListPage( PratilipiType.ARTICLE, basicMode, displayLanguage, filterLanguage, request );
				templateName = ( basicMode ? "ListBasic.ftl" : "List.ftl" );



			// Standard Mode links only
			} else if( ! basicMode && uri.equals( "/pratilipi-write" ) ) {

				if( request.getQueryString() != null ) {
					canonicalUrl = canonicalUrl + "?" + request.getQueryString();
					alternateUrl = alternateUrl + "?" + request.getQueryString();
				}

				resourceList.remove( ThirdPartyResource.POLYMER_ELEMENTS.getTag() );
				resourceList.add( ThirdPartyResource.BOOTSTRAP_CSS.getTag() );
				resourceList.add( ThirdPartyResource.TINYMCE.getTag() );

				

				Long authorId = request.getParameter( RequestParameter.AUTHOR_ID.getName() ) != null 
						? Long.parseLong( request.getParameter( RequestParameter.AUTHOR_ID.getName() ) ) 
						: null;

				Long pratilipiId = Long.parseLong( request.getParameter( RequestParameter.CONTENT_ID.getName() ) ); 

				PratilipiV2Api.GetRequest pratilipiRequest = new PratilipiV2Api.GetRequest();
				pratilipiRequest.setPratilipiId( pratilipiId );
				PratilipiV2Api.Response pratilipiResponse = ApiRegistry.getApi( PratilipiV2Api.class ).get( pratilipiRequest );

				PratilipiContentIndexApi.GetRequest indexReq = new PratilipiContentIndexApi.GetRequest();
				indexReq.setPratilipiId( pratilipiId );
				PratilipiContentIndexApi.Response indexResponse = ApiRegistry.getApi( PratilipiContentIndexApi.class )
														.getIndex( indexReq );

				dataModel = new HashMap<String, Object>();
				dataModel.put( "title", SEOTitleUtil.getWritePageTitle( pratilipiId, filterLanguage ) );
				dataModel.put( "authorId", authorId );
				dataModel.put( "pratilipiId", pratilipiId );
				dataModel.put( "pratilipi", pratilipiResponse );
				dataModel.put( "pratilipiJson", new Gson().toJson( pratilipiResponse ) );
				dataModel.put( "indexJson", new Gson().toJson( indexResponse ) );

				String action = request.getParameter( "action" );
				if( action != null )
					dataModel.put( "action", action );

				templateName = "WriterV2.ftl";



			// Basic Mode links only
			} else if( basicMode && uri.equals( "/account" ) ) {
				canonicalUrl = "http://" + UxModeFilter.getWebsite().getMobileHostName() + uri;
				dataModel = new HashMap<String, Object>();
				dataModel.put( "title", "My Account" );
				templateName = "AccountBasic.ftl";

			} else if( basicMode && uri.equals( "/navigation" ) ) {
				canonicalUrl = "http://" + UxModeFilter.getWebsite().getMobileHostName() + uri;
				dataModel = new HashMap<String, Object>();
				dataModel.put( "title", "Menu" );
				dataModel.put( "navigationList", navigationList );
				templateName = "NavigationBasic.ftl";

			} else if( basicMode && uri.equals( "/updatepassword" ) ) {
				canonicalUrl = "http://" + UxModeFilter.getWebsite().getMobileHostName() + uri;
				dataModel = new HashMap<String, Object>();
				if( request.getParameter( RequestParameter.PASSWORD_RESET_EMAIL_EMAIL.getName() ) != null 
						&& request.getParameter( RequestParameter.PASSWORD_RESET_EMAIL_TOKEN.getName() ) != null ) {
					dataModel.put( "passwordResetFromMail", true );
					dataModel.put( "email", request.getParameter( RequestParameter.PASSWORD_RESET_EMAIL_EMAIL.getName() ) );
					dataModel.put( "verificationToken", request.getParameter( RequestParameter.PASSWORD_RESET_EMAIL_TOKEN.getName() ) );
				}
				dataModel.put( "title", "Update Password" );
				templateName = "PasswordUpdateBasic.ftl";

			} else if( basicMode && uri.equals( "/share" ) ) {
				canonicalUrl = "http://" + UxModeFilter.getWebsite().getMobileHostName() + uri;
				dataModel = new HashMap<String, Object>();
				dataModel.put( "title", "Share" );
				templateName = "ShareBasic.ftl";

			} else if( uri.equals( "/register" ) ) {
				if( basicMode ) {
					canonicalUrl = "http://" + UxModeFilter.getWebsite().getMobileHostName() + uri;
					dataModel = new HashMap<String, Object>();
					dataModel.put( "title", "Register" );
					templateName = "RegisterBasic.ftl";
				} else if( loadPWA ) {
					dataModel = new HashMap<String, Object>();
					templateName = "Register.ftl";
				}

			} else if( uri.equals( "/login" ) ) {
				if( basicMode ) {
					canonicalUrl = "http://" + UxModeFilter.getWebsite().getMobileHostName() + uri;
					dataModel = new HashMap<String, Object>();
					dataModel.put( "title", "Login" );
					templateName = "LoginBasic.ftl";
				} else if( loadPWA ) {
					dataModel = new HashMap<String, Object>();
					templateName = "Login.ftl";
				}

			} else if( uri.equals( "/forgot-password" ) ) {
				if( basicMode ) {
					canonicalUrl = "http://" + UxModeFilter.getWebsite().getMobileHostName() + uri;
					dataModel = new HashMap<String, Object>();
					dataModel.put( "title", "Reset Password" );
					templateName = "PasswordResetBasic.ftl";
				} else if( loadPWA ) {
					dataModel = new HashMap<String, Object>();
					templateName = "ForgotPassword.ftl";
				}

			} else if( uri.equals( "/settings" ) && loadPWA ) {
					dataModel = new HashMap<String, Object>();
					templateName = "SettingsPWA.ftl";

			// Internal links - Standard Version only
			} else if( ! basicMode && uri.startsWith( "/admin" ) ) {

				if( uri.equals( "/admin" ) ) {
					dataModel = new HashMap<>();
					dataModel.put( "title", "Pratilipi - Admin Access" );
					templateName = "Admin.ftl";

				} else if( uri.equals( "/admin/authors" ) ) {
					dataModel = createDataModelForAuthorsPage( filterLanguage );
					templateName = "AuthorList.ftl";

				} else if( uri.equals( "/admin/batch-process" ) ) {
					dataModel = createDataModelForBatchProcessListPage();
					templateName = "BatchProcessList.ftl";

				} else if( uri.equals( "/admin/email-templates" ) ) {
					dataModel = createDataModelForEmailTemplatesPage( filterLanguage );
					templateName = "EmailTemplate.ftl";

				} else if( uri.equals( "/admin/translations" ) ) {
					dataModel = new HashMap<>();
					templateName = "Translation.ftl";
				} 

			} else if( ! basicMode && uri.equals( "/edit-event" ) ){

				resourceList.add( ThirdPartyResource.CKEDITOR.getTag() );
				Long eventId = request.getParameter( RequestParameter.CONTENT_ID.getName() ) != null ? 
						Long.parseLong( request.getParameter( RequestParameter.CONTENT_ID.getName() ) ) : null;

				dataModel = new HashMap<String, Object>();
				dataModel.put( "title", "Create or Edit Event" );
				if( eventId != null ) {
					EventApi.GetRequest eventRequest = new EventApi.GetRequest();
					eventRequest.setEventId( eventId );
					EventApi.Response eventResponse = ApiRegistry
														.getApi( EventApi.class )
														.get( eventRequest );
					dataModel.put( "eventJson", new Gson().toJson( eventResponse ) );
				}

				templateName = "EventEdit.ftl";

			} else if( ! basicMode && uri.equals( "/edit-blog" ) ) {

				resourceList.add( ThirdPartyResource.CKEDITOR.getTag() );
				Long blogPostId = request.getParameter( RequestParameter.CONTENT_ID.getName() ) != null ? 
						Long.parseLong( request.getParameter( RequestParameter.CONTENT_ID.getName() ) ) : null;

				Long blogId = request.getParameter( "blogId" ) != null ? 
						Long.parseLong( request.getParameter( "blogId" ) ) : null;

				dataModel = new HashMap<String, Object>();
				dataModel.put( "title", "Create or Edit Blog" );
				dataModel.put( "blogId", blogId );
				if( blogPostId != null ) {
					BlogPostApi.GetRequest blogPostRequest = new BlogPostApi.GetRequest();
					blogPostRequest.setBlogPostId( blogPostId );
					BlogPostApi.Response blogPostResponse = ApiRegistry
																.getApi( BlogPostApi.class )
																.get( blogPostRequest );
					dataModel.put( "blogPostJson", new Gson().toJson( blogPostResponse ) );
				}

				templateName = "BlogEdit.ftl";



			// Non - hardcoded links
			} else if( page != null && page.getType() == PageType.PRATILIPI ) {
				if( loadPWA ) {
					dataModel = new HashMap<>();
					templateName = "PratilipiPWA.ftl";
				} else {
					resourceList.addAll( createFbOpenGraphTags( page.getPrimaryContentId() ) );
					dataModel = createDataModelForPratilipiPage( page.getPrimaryContentId(), filterLanguage, basicMode, request );
					templateName = ( basicMode ? "PratilipiBasic.ftl" : "Pratilipi.ftl" );					
				}

			} else if( page != null && page.getType() == PageType.AUTHOR ) {
				if( loadPWA ) {
					dataModel = new HashMap<>();
					templateName = "AuthorPWA.ftl";
				} else {
					dataModel = createDataModelForAuthorPage( page.getPrimaryContentId(), filterLanguage, basicMode, request );
					templateName = ( basicMode ? "AuthorBasic.ftl" : "Author.ftl" );					
				}

			} else if( page != null && page.getType() == PageType.EVENT ) {
				dataModel = createDataModelForEventPage( page.getPrimaryContentId(), filterLanguage, basicMode, request );
				templateName = ( basicMode ? "EventBasic.ftl" : "Event.ftl" );

			} else if( page != null && page.getType() == PageType.BLOG ) {
				dataModel = createDataModelForBlogPage( page.getPrimaryContentId(), filterLanguage, basicMode );
				templateName = ( basicMode ? "BlogPostListBasic.ftl" : "BlogPostList.ftl" );

			} else if( page != null && page.getType() == PageType.BLOG_POST ) {
				dataModel = createDataModelForBlogPostPage( page.getPrimaryContentId(), filterLanguage, basicMode );
				templateName = ( basicMode ? "BlogPostBasic.ftl" : "BlogPost.ftl" );

			} else if( page != null && page.getType() == PageType.READ ) {

				if( request.getQueryString() != null ) {
					canonicalUrl = canonicalUrl + "?" + request.getQueryString();
					alternateUrl = alternateUrl + "?" + request.getQueryString();
				}

				Long pratilipiId = Long.parseLong( request.getParameter( RequestParameter.CONTENT_ID.getName() ) );
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

				dataModel = createDataModelForReadPage( pratilipiId, 
														pageNo, 
														request.getParameter( RequestParameter.API_VERSION.getName() ),
														filterLanguage,
														basicMode );

				dataModel.put( "fontSize", fontSize != null ? Integer.parseInt( fontSize ) : 14 );
				dataModel.put( "imageSize", imageSize != null ? Integer.parseInt( imageSize ) : 636 );
				dataModel.put( "action", action );

				templateName = ( basicMode ? "ReadBasic.ftl" : "Read.ftl" );

			} else if( uri.matches( "^/[a-z0-9-]+$" ) && ( dataModel = createDataModelForListPage( uri.substring( 1 ), basicMode, displayLanguage, filterLanguage, request ) ) != null ) {
				if( loadPWA ) {
					dataModel = new HashMap<>();
					templateName = "ListPWA.ftl";
				} else {
					templateName = ( basicMode ? "ListBasic.ftl" : "List.ftl" );
				}

			} else if( uri.matches( "^/[a-z0-9-/]+$" ) && ( dataModel = createDataModelForStaticPage( uri.substring( 1 ).replaceAll( "/", "_" ), displayLanguage ) ) != null ) {
				templateName = ( basicMode ? "StaticBasic.ftl" : "Static.ftl" );

			} else if( uri.matches( "^/[a-z0-9-/]+$" ) && ( dataModel = createDataModelForStaticPage( uri.substring( 1 ).replaceAll( "/", "_" ), Language.ENGLISH ) ) != null ) {
				templateName = ( basicMode ? "StaticBasic.ftl" : "Static.ftl" );

			} else {
				dataModel = new HashMap<String, Object>();
				dataModel.put( "title", "Page Not Found !" );
				templateName = ( basicMode ? "error/PageNotFoundBasic.ftl" : "error/PageNotFound.ftl" );
				response.setStatus( HttpServletResponse.SC_NOT_FOUND );
			}

		} catch( InsufficientAccessException e ) {
			resourceList = getResourceList( basicMode );
			dataModel = new HashMap<String, Object>();
			dataModel.put( "title", "Unauthorized Access !" );
			templateName = ( basicMode ? "error/AuthorizationErrorBasic.ftl" : "error/AuthorizationError.ftl" );
			logger.log( Level.SEVERE, "Unauthorised Exception: ", e );
			response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );

		} catch( InvalidArgumentException | UnexpectedServerException e ) {
			resourceList = getResourceList( basicMode );
			dataModel = new HashMap<String, Object>();
			dataModel.put( "title", "Server Error !" );
			templateName = ( basicMode ? "error/ServerErrorBasic.ftl" : "error/ServerError.ftl" );
			logger.log( Level.SEVERE, "Service Unavailable: ", e );
			response.setStatus( HttpServletResponse.SC_SERVICE_UNAVAILABLE );
		}


		// Adding common data to the Data Model
		Gson gson = new Gson();
		UserV1Api.Response userResponse = new UserV1Api.Response( userData, UserV1Api.class );

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
		dataModel.put( "canonical_url", canonicalUrl );
		dataModel.put( "alternate_url", alternateUrl );
		dataModel.put( "languageMap", gson.toJson( languageMap ) );
		dataModel.put( "_strings", I18n.getStrings( displayLanguage ) );
		dataModel.put( "resourceList", resourceList );
		dataModel.put( "deferredResourceList", deferredResourceList );
		dataModel.put( "user", userResponse );
		dataModel.put( "userJson", gson.toJson( userResponse ) );
		dataModel.put( "pratilipiTypesJson", gson.toJson( pratilipiTypes ) );
		dataModel.put( "navigationListJson", gson.toJson( navigationList ) );
		dataModel.put( "stage", SystemProperty.STAGE );
		dataModel.put( "basicMode", basicMode );

		// Hack: Not to minify the html file
		if( loadPWA )
			dataModel.put( "stage", "pwa" );

		if( basicMode ) {
			StringBuffer requestUrl = new StringBuffer( request.getRequestURI() );
			if( request.getQueryString() != null )
				requestUrl.append( '?' ).append( request.getQueryString() );
			dataModel.put( "requestUrl", URLEncoder.encode( requestUrl.toString(), "UTF-8" ) );
		}

		
		// Generating response html
		String html = null;
		for( int i = 0; i < 2 && html == null; i++ ) {
			try {
				dataModel.put( "templateName", templateName );
				// The magic
				html = FreeMarkerUtil.processTemplate( dataModel, templateFilePrefix + "MainTemplate.ftl" );
			} catch( UnexpectedServerException e ) {
				logger.log( Level.SEVERE, "Exception occured while processing template.", e );
				resourceList = getResourceList( basicMode );
				templateName = ( basicMode ? "error/ServerErrorBasic.ftl" : "error/ServerError.ftl" );
			}
		}

		// Dispatching response
		logger.log( Level.INFO, "Appengine Version : " + SystemProperty.VERSION_ID );
		_dispatchResponse( html, "text/html", "UTF-8", response );

	}

	private void _dispatchResponse( String content, String contentType, String characterEncoding, HttpServletResponse response ) 
			throws IOException {
		response.setContentType( contentType );
		response.setCharacterEncoding( characterEncoding );
		response.getWriter().write( content );
		response.getWriter().close();
	}

	private Set<String> getResourceList( Boolean basicMode ) {
		Set<String> resourceList = new HashSet<>();
		if( basicMode ) {
			resourceList.add( ThirdPartyResource.JQUERY.getTag() );
			resourceList.add( ThirdPartyResource.BOOTSTRAP_JS.getTag() );
			resourceList.add( ThirdPartyResource.BOOTSTRAP_CSS.getTag() );
		} else {
			resourceList.add( ThirdPartyResource.JQUERY_BOOTSTRAP_POLYMER_JS.getTag() );
			resourceList.add( ThirdPartyResource.POLYMER_ELEMENTS.getTag() );
		}
		resourceList.add( ThirdPartyResource.FIREBASE.getTag() );
		return resourceList;
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

		return createPratilipiPageTitle( new PratilipiV2Api.Response( pratilipiData ) );
	}

	private String createPratilipiPageTitle( PratilipiV2Api.Response pratilipiResponse ) {

		if( pratilipiResponse == null )
			return null;
		
		String title = createAuthorPageTitle( pratilipiResponse.getAuthor() );
		title = title == null ? "" : " « " + title;
		
		if( pratilipiResponse.getTitle() != null && pratilipiResponse.getTitleEn() == null )
			return pratilipiResponse.getTitle() + title;
		else if( pratilipiResponse.getTitle() == null && pratilipiResponse.getTitleEn() != null )
			return pratilipiResponse.getTitleEn() + title;
		else if( pratilipiResponse.getTitle() != null && pratilipiResponse.getTitleEn() != null )
			return pratilipiResponse.getTitle() + " / " + pratilipiResponse.getTitleEn() + title;
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

	
	private List<String> createFbOpenGraphTags( Long pratilipiId ) 
			throws InsufficientAccessException, UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Page page = dataAccessor.getPage( PageType.PRATILIPI, pratilipiId );

		PratilipiV2Api.GetRequest pratilipiRequest = new PratilipiV2Api.GetRequest();
		pratilipiRequest.setPratilipiId( pratilipiId );
		PratilipiV2Api.Response pratilipi = ApiRegistry
														.getApi( PratilipiV2Api.class )
														.get( pratilipiRequest );

		String ogFbAppId = FacebookApi.getAppId();
		String ogLocale = pratilipi.getLanguage().getCode() + "_IN";
		String ogType = "books.book";
		String ogAuthor = "http://" + Website.ALL_LANGUAGE.getHostName() + ( pratilipi.getAuthor() == null ? "/team-pratilipi" : pratilipi.getAuthor().getPageUrl() );
		String ogBooksIsbn = pratilipi.getId().toString();
		String ogUrl = "http://" + Website.ALL_LANGUAGE.getHostName() + page.getUri(); // Warning: Changing it to anything else will cause loss of like-share count.
		String ogTitle = createPratilipiPageTitle( pratilipi );
		String ogImage = pratilipi.getCoverImageUrl();
		String ogDescription = "";
		if( pratilipi.getSummary() != null )
			ogDescription = pratilipi.getSummary();

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
		dataModel.put( "title", SEOTitleUtil.getMasterHomePageTitle() );
		return dataModel;

	}
	
	private Map<String, Object> createDataModelForHomePage( boolean basicMode, Language filterLanguage )
			throws InsufficientAccessException, IOException, UnexpectedServerException {

		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "title", SEOTitleUtil.getHomePageTitle( filterLanguage ) );

		if( basicMode ) {
			if( filterLanguage == null )
				filterLanguage = Language.ENGLISH;

			InitV2Api.GetRequest request = new InitV2Api.GetRequest();
			request.setLanguage( filterLanguage );
			List<Section> sections = ApiRegistry.getApi( InitV2Api.class ).get( request ).getSectionList();
			dataModel.put( "sections", sections );
		}

		return dataModel;

	}

	private Map<String, Object> createDataModelForLibraryPage( boolean basicMode, Language filterLanguage ) throws UnexpectedServerException {
		
		DataListCursorTuple<PratilipiData> pratilipiDataListCursorTuple
				= UserPratilipiDataUtil.getUserLibrary( AccessTokenFilter.getAccessToken().getUserId(), null, null, null );
		
		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "title", SEOTitleUtil.getLibraryPageTitle( filterLanguage ) );
		if( basicMode ) {
			dataModel.put( "pratilipiList", toListResponseObject( pratilipiDataListCursorTuple.getDataList() ) );
		} else {
			Gson gson = new Gson();
			dataModel.put( "pratilipiListJson", gson.toJson( toListResponseObject( pratilipiDataListCursorTuple.getDataList() ) ) );
			dataModel.put( "pratilipiListCursor", pratilipiDataListCursorTuple.getCursor() );
		}
		return dataModel;
		
	}

	
	public Map<String, Object> createDataModelForPratilipiPage( Long pratilipiId, Language language, boolean basicMode, HttpServletRequest request )
			throws InsufficientAccessException, UnexpectedServerException {

		PratilipiV2Api.GetRequest pratilipiRequest = new PratilipiV2Api.GetRequest();
		pratilipiRequest.setPratilipiId( pratilipiId );
		PratilipiV2Api.Response pratilipiResponse = ApiRegistry
														.getApi( PratilipiV2Api.class )
														.get( pratilipiRequest );

		UserPratilipiApi.GetRequest userPratilipiRequest = new UserPratilipiApi.GetRequest();
		userPratilipiRequest.setPratilipiId( pratilipiId );
		UserPratilipiApi.Response userPratilipiResponse = ApiRegistry
														.getApi( UserPratilipiApi.class )
														.getUserPratilipi( userPratilipiRequest );
		

		Map<String, Object> dataModel = new HashMap<String, Object>();
		Gson gson = new Gson();
		dataModel.put( "title", SEOTitleUtil.getPratilipiPageTitle( pratilipiId, language ) );
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

				UserPratilipiReviewListApi.GetRequest reviewListRequest = new UserPratilipiReviewListApi.GetRequest();
				reviewListRequest.setPratilipiId( pratilipiId );
				reviewListRequest.setOffset( ( reviewPageCurr - 1 ) * reviewPageSize );
				reviewListRequest.setResultCount( reviewPageSize );
				UserPratilipiReviewListApi.Response reviewListResponse = ApiRegistry
																			.getApi( UserPratilipiReviewListApi.class )
																			.get( reviewListRequest );
				dataModel.put( "reviewList", reviewListResponse.getReviewList() );
				dataModel.put( "reviewListPageCurr", reviewPageCurr );
				if( pratilipiResponse.getReviewCount() != 0 )
					dataModel.put( "reviewListPageMax", (int) Math.ceil( ( (double) pratilipiResponse.getReviewCount() ) / reviewPageSize ) );
				dataModel.put( "reviewParam", reviewParam );
			} else if( reviewParam != null && reviewParam.trim().equals( "write" ) && userPratilipiResponse != null && userPratilipiResponse.hasAccessToReview() ) {
				dataModel.put( "reviewParam", reviewParam );
			} else if( reviewParam != null && reviewParam.trim().equals( "reply" ) ) {
				dataModel.put( "reviewParam", reviewParam );
			} else { // if( reviewParam == null || reviewParam.trim().isEmpty() ) {
				UserPratilipiReviewListApi.GetRequest reviewListRequest = new UserPratilipiReviewListApi.GetRequest();
				reviewListRequest.setPratilipiId( pratilipiId );
				reviewListRequest.setResultCount( 10 );
				UserPratilipiReviewListApi.Response reviewListResponse = ApiRegistry
																			.getApi( UserPratilipiReviewListApi.class )
																			.get( reviewListRequest );
				dataModel.put( "reviewList", reviewListResponse.getReviewList() );
			}
		} else {
			dataModel.put( "pratilipi", pratilipiResponse );
			dataModel.put( "pratilipiJson", gson.toJson( pratilipiResponse ) );
			dataModel.put( "userpratilipiJson", gson.toJson( userPratilipiResponse ) );
		}

		// TODO: CleverTap Boolean - Remove after POC
		dataModel.put( "isContentPage", true );

		return dataModel;
		
	}
	
	public Map<String, Object> createDataModelForAuthorPage( Long authorId, Language language, boolean basicMode, HttpServletRequest request )
			throws InsufficientAccessException, UnexpectedServerException {

		Map<String, Object> dataModel = new HashMap<String, Object>();
		Gson gson = new Gson();

		AuthorApi.GetRequest authorApiGetRequest = new AuthorApi.GetRequest();
		authorApiGetRequest.setAuthorId( authorId );
		AuthorApi.Response authorResponse = ApiRegistry
				.getApi( AuthorApi.class )
				.get( authorApiGetRequest );
		dataModel.put( "title", SEOTitleUtil.getAuthorPageTitle( authorId, language ) );

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
			PratilipiListV2Api.GetRequest pratilipiListRequest = new PratilipiListV2Api.GetRequest();
			pratilipiListRequest.setAuthorId( authorId );
			pratilipiListRequest.setState( pratilipiState );
			pratilipiListRequest.setResultCount( resultCount );
			pratilipiListRequest.setOffset( ( pageCurr - 1 ) * resultCount );
			PratilipiListV2Api.Response pratilipiListResponse = ApiRegistry
								.getApi( PratilipiListV2Api.class )
								.get( pratilipiListRequest );

			dataModel.put( "state", pratilipiState.toString() );
			dataModel.put( "pratilipiList", pratilipiListResponse.getPratilipiList() );
			dataModel.put( "pratilipiListPageCurr", pageCurr );
			Integer pageMax = pratilipiListResponse.getNumberFound() != null ?
					(int) Math.ceil( ( (double) pratilipiListResponse.getNumberFound() ) / resultCount ) : 1;
			dataModel.put( "pratilipiListPageMax", pageMax );

			return dataModel;

		}


		UserAuthorFollowV1Api.GetRequest getRequest = new UserAuthorFollowV1Api.GetRequest();
		getRequest.setAuthorId( authorId );
		UserAuthorFollowV1Api.Response userAuthorResponse = ApiRegistry
				.getApi( UserAuthorFollowV1Api.class )
				.get( getRequest );
		if( basicMode )
			dataModel.put( "userAuthor", userAuthorResponse );
		else
			dataModel.put( "userAuthorJson", gson.toJson( userAuthorResponse ) );


		if( basicMode ) {

			Integer resultCount = 3;
			PratilipiListV2Api.GetRequest publishedPratilipiListRequest = new PratilipiListV2Api.GetRequest();
			publishedPratilipiListRequest.setAuthorId( authorId );
			publishedPratilipiListRequest.setState( PratilipiState.PUBLISHED );
			publishedPratilipiListRequest.setResultCount( resultCount );
			PratilipiListV2Api.Response publishedPratilipiListResponse = ApiRegistry
					.getApi( PratilipiListV2Api.class )
					.get( publishedPratilipiListRequest );

			if( authorResponse.hasAccessToUpdate() ) {
				PratilipiListV2Api.GetRequest draftedPratilipiListRequest = new PratilipiListV2Api.GetRequest();
				draftedPratilipiListRequest.setAuthorId( authorId );
				draftedPratilipiListRequest.setState( PratilipiState.DRAFTED );
				draftedPratilipiListRequest.setResultCount( resultCount );
				PratilipiListV2Api.Response draftedPratilipiListResponse = ApiRegistry
						.getApi( PratilipiListV2Api.class )
						.get( draftedPratilipiListRequest );
				dataModel.put( "draftedPratilipiList", draftedPratilipiListResponse.getPratilipiList() );
			}

			Integer followResultCount = 3;
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
			
			dataModel.put( "followersList", followersList );
			dataModel.put( "followingList", followingList );
			dataModel.put( "publishedPratilipiList", publishedPratilipiListResponse.getPratilipiList() );
		}

		return dataModel;
		
	}

	public Map<String, Object> createDataModelForFollowersPage( Long authorId, Integer currPage, Language language, Boolean basicMode ) 
			throws InsufficientAccessException, UnexpectedServerException {

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
		dataModel.put( "title", SEOTitleUtil.getFollowersPageTitle( authorId, language ) );
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
			throws InsufficientAccessException, UnexpectedServerException {

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
		dataModel.put( "title", SEOTitleUtil.getFollowersPageTitle( userId, language ) );
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
	
	public Map<String, Object> createDataModelForBatchProcessListPage() 
			throws InsufficientAccessException, UnexpectedServerException {

		BatchProcessListApi.GetRequest request = new BatchProcessListApi.GetRequest();
		request.setType( BatchProcessType.NOTIFACTION_BY_AUTHOR_FILTER );
		BatchProcessListApi.Response batchProcessListResponse = ApiRegistry.getApi( BatchProcessListApi.class ).get( request );
		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "title", "Notifications - Admin Access" );
		dataModel.put( "batchProcessListJson", new Gson().toJson( batchProcessListResponse.getBatchProcessList() ) );
		return dataModel;

	}

	public Map<String, Object> createDataModelForEventsPage( Language language, boolean basicMode ) 
			throws InsufficientAccessException, UnexpectedServerException {

		EventData eventData = new EventData();
		eventData.setLanguage( language );
		boolean hasAccessToAdd = EventDataUtil.hasAccessToAddEventData( eventData );

		EventListApi.GetRequest request = new EventListApi.GetRequest();
		request.setLanguage( language );
		EventListApi.GetResponse eventListResponse = ApiRegistry
											.getApi( EventListApi.class )
											.get( request );

		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "title", SEOTitleUtil.getEventsPageTitle( language ) );
		dataModel.put( "hasAccessToAdd", hasAccessToAdd );
		if( basicMode ) {
			dataModel.put( "eventList", eventListResponse.getEventList() );
		} else {
			dataModel.put( "eventListJson", new Gson().toJson( eventListResponse.getEventList() ) );
		}
		return dataModel;
	}
	
	public Map<String, Object> createDataModelForEventPage( Long eventId, Language language, boolean basicMode, HttpServletRequest request )
			throws InsufficientAccessException, UnexpectedServerException {

		Map<String, Object> dataModel = new HashMap<String, Object>();
		Gson gson = new Gson();

		EventApi.GetRequest eventRequest = new EventApi.GetRequest();
		eventRequest.setEventId( eventId );
		EventApi.Response eventResponse = ApiRegistry
											.getApi( EventApi.class )
											.get( eventRequest );

		dataModel.put( "title", SEOTitleUtil.getEventPageTitle( eventId, language ) );
		if( basicMode )
			dataModel.put( "event", eventResponse );
		else
			dataModel.put( "eventJson", gson.toJson( eventResponse ) );


		if( basicMode ) {

			String action = request.getParameter( "action" ) != null ? request.getParameter( "action" ) : "event_page";
			dataModel.put( "action", action );
			Integer pageCurr = request.getParameter( RequestParameter.LIST_PAGE_NUMBER.getName() ) != null
					? Integer.parseInt( request.getParameter( RequestParameter.LIST_PAGE_NUMBER.getName() ) )
					: 1;

			Integer resultCount = 10;
			PratilipiListV2Api.GetRequest PratilipiListV1ApiRequest = new PratilipiListV2Api.GetRequest();
			PratilipiListV1ApiRequest.setEventId( eventId );
			PratilipiListV1ApiRequest.setState( PratilipiState.PUBLISHED );
			PratilipiListV1ApiRequest.setResultCount( resultCount );
			if( action.equals( "list_contents" ) ) {
				PratilipiListV1ApiRequest.setOffset( ( pageCurr - 1 ) * resultCount );
			}

			PratilipiListV2Api.Response PratilipiListV1ApiResponse = ApiRegistry
										.getApi( PratilipiListV2Api.class )
										.get( PratilipiListV1ApiRequest );

			dataModel.put( "pratilipiList", PratilipiListV1ApiResponse.getPratilipiList() );
			dataModel.put( "numberFound", PratilipiListV1ApiResponse.getNumberFound() );
			dataModel.put( "pratilipiListPageCurr", pageCurr );
			dataModel.put( "pratilipiListPageMax", PratilipiListV1ApiResponse.getNumberFound() != null ?
					(int) Math.ceil( ( (double) PratilipiListV1ApiResponse.getNumberFound() ) / resultCount ) : 1 );

		}

		return dataModel;

	}
	
	public Map<String, Object> createDataModelForBlogPage( Long blogId, Language language, boolean basicMode ) 
			throws InsufficientAccessException, UnexpectedServerException {

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

		if( blogId.equals( 5683739602452480L ) ) // Blog
			dataModel.put( "title", SEOTitleUtil.getBlogPageTitle( language ) );
		else if( blogId.equals( 5197509039226880L ) ) // Author Interviews 
			dataModel.put( "title", SEOTitleUtil.getAuthorInterviewPageTitle( language ) );
		else
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

	public Map<String, Object> createDataModelForBlogPostPage( Long blogPostId, Language language, boolean basicMode )
			throws InsufficientAccessException, UnexpectedServerException {

		BlogPostApi.GetRequest request = new BlogPostApi.GetRequest();
		request.setBlogPostId( blogPostId );
		BlogPostApi.Response response = ApiRegistry
				.getApi( BlogPostApi.class )
				.get( request ); 

		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "title", SEOTitleUtil.getBlogPostPageTitle( blogPostId, language ) );
		if( basicMode ) {
			dataModel.put( "blogPost", response );
		} else {
			dataModel.put( "blogPostJson", new Gson().toJson( response ) );
		}
		return dataModel;

	}
	
	public Map<String, Object> createDataModelForReadPage( Long pratilipiId, Integer chapterNo, String version, Language language, boolean basicMode )
			throws InvalidArgumentException, UnexpectedServerException, InsufficientAccessException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
		PratilipiData pratilipiData = PratilipiDataUtil.createPratilipiData( pratilipi );
		PratilipiV2Api.Response pratilipiResponse = new PratilipiV2Api.Response( pratilipiData );
		UserPratilipiApi.GetRequest userPratilipiRequest = new UserPratilipiApi.GetRequest();
		userPratilipiRequest.setPratilipiId( pratilipiId );
		UserPratilipiApi.Response userPratilipiResponse = ApiRegistry
														.getApi( UserPratilipiApi.class )
														.getUserPratilipi( userPratilipiRequest );

		String indexJson = null;
		Integer pageCount = null;
		Object content = null;

		// In case, version is not specified in URL
		if( version == null ) {

			if( SystemProperty.STAGE.equals( SystemProperty.STAGE_PROD )
				&& pratilipi.isOldContent() 
				&& pratilipi.getContentType() == PratilipiContentType.PRATILIPI ) {
				version = "1";

			} else {
				version = "3";

			}

		}

		// Index and pageCount for all contents
		if( version.equals( "1" ) ) {
			indexJson = pratilipi.getIndex();
			pageCount = pratilipi.getPageCount() > 0 ? pratilipi.getPageCount() : 1;

		} else if( version.equals( "2" ) || version.equals( "3" ) ) { // Json format
			PratilipiContentIndexApi.GetRequest indexReq = new PratilipiContentIndexApi.GetRequest();
			indexReq.setPratilipiId( pratilipiId );
			PratilipiContentIndexApi.Response indexRes = ApiRegistry
														.getApi( PratilipiContentIndexApi.class )
														.getIndex( indexReq );
			indexJson = indexRes.getIndex().toString();
			pageCount = indexRes.getIndex().size() > 0 ? indexRes.getIndex().size() : 1;

		}


		if( pratilipi.getContentType() == PratilipiContentType.PRATILIPI ) {

			if( chapterNo < 1 ) chapterNo = 1;
			if( chapterNo > pageCount ) chapterNo = pageCount;

			if( version.equals( "1" ) ) {
				PratilipiContentV1Api.GetRequest req = new PratilipiContentV1Api.GetRequest();
				req.setPratilipiId( pratilipiId );
				req.setChapterNo( chapterNo );
				PratilipiContentV1Api.GetResponse res = (PratilipiContentV1Api.GetResponse) ApiRegistry
																		.getApi( PratilipiContentV1Api.class )
																		.get( req );
				content = res.getContent();

			} else if( version.equals( "2" ) ) {
				PratilipiContentV2Api.GetRequest req = new PratilipiContentV2Api.GetRequest();
				req.setPratilipiId( pratilipiId );
				req.setChapterNo( chapterNo );
				PratilipiContentV2Api.GetResponse res = (PratilipiContentV2Api.GetResponse) ApiRegistry
																		.getApi( PratilipiContentV2Api.class )
																		.get( req );
				content = res.getContent();
				if( res.getChapterTitle() != null )
					content = "<h1>" + res.getChapterTitle() + "</h1>" + content;

			} else if( version.equals( "3" ) ) {
				PratilipiContentV3Api.GetRequest req = new PratilipiContentV3Api.GetRequest();
				req.setPratilipiId( pratilipiId );
				req.setChapterNo( chapterNo );
				PratilipiContentV3Api.GetResponse res = (PratilipiContentV3Api.GetResponse) ApiRegistry
																		.getApi( PratilipiContentV3Api.class )
																		.get( req );
				content = res.getContent();
				if( res.getChapterTitle() != null )
					content = "<h1>" + res.getChapterTitle() + "</h1>" + content;

			}

		} else if( pratilipi.getContentType() == PratilipiContentType.IMAGE ) {

			PratilipiContentV2Api.GetRequest req = new PratilipiContentV2Api.GetRequest();
			req.setPratilipiId( pratilipiId );
			PratilipiContentV2Api.GetResponse res = (PratilipiContentV2Api.GetResponse) ApiRegistry
																		.getApi( PratilipiContentV2Api.class )
																		.get( req );

			PratilipiContentDoc pcDoc = (PratilipiContentDoc) res.getContent();

			// Image books can have more than one page in a chapter
			pageCount = 0;
			for( Chapter chapter : pcDoc.getChapterList() )
				pageCount += chapter.getPageCount();

			if( chapterNo < 1 ) chapterNo = 1;
			if( chapterNo > pageCount ) chapterNo = pageCount;

			if( basicMode ) {
				JsonObject jsonObject = null;
				int c = 0;
				for( int i = 1; i <= pcDoc.getChapterCount(); i++ ) {
					for( int j = 1; j <= pcDoc.getChapter( i ).getPageCount(); j++ ) {
						if( ++c == chapterNo ) {
							jsonObject = pcDoc.getChapter( i ).getPage( j ).getPageletList().get( 0 ).getData();
							break;
						}
					}
				}

				content = PratilipiDocUtil._processImagePagelet( pratilipiId, jsonObject );

			} else {
				content = new Gson().toJson( res.getContent() );
			}

		}

		Gson gson = new Gson();
		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "title", SEOTitleUtil.getReadPageTitle( pratilipiId, language ) );
		dataModel.put( "pageNo", chapterNo );
		dataModel.put( "pageCount", pageCount );
		dataModel.put( "content", content );
		dataModel.put( "version", version );
		dataModel.put( "contentType", pratilipi.getContentType() );
		if( basicMode ) {
			dataModel.put( "pratilipi", pratilipiResponse );
			dataModel.put( "userpratilipi", userPratilipiResponse );
			dataModel.put( "indexList", gson.fromJson( indexJson, Object.class ) );
			
		} else {
			dataModel.put( "pratilipiJson", gson.toJson( pratilipiResponse ) );
			dataModel.put( "userpratilipiJson", gson.toJson( userPratilipiResponse ) );
			dataModel.put( "indexJson", gson.toJson( indexJson ) );
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


		PratilipiListV2Api.GetRequest PratilipiListV1ApiRequest = new PratilipiListV2Api.GetRequest();
		PratilipiListV1ApiRequest.setLanguage( language );
		PratilipiListV1ApiRequest.setState( pratilipiState );
		PratilipiListV1ApiRequest.setResultCount( resultCount );
		PratilipiListV1ApiRequest.setOffset( ( pageCurr - 1 ) * resultCount );
		if( searchQuery != null )
			PratilipiListV1ApiRequest.setSearchQuery( searchQuery );
		if( authorId != null )
			PratilipiListV1ApiRequest.setAuthorId( authorId );
		PratilipiListV2Api.Response response = ApiRegistry
												.getApi( PratilipiListV2Api.class )
												.get( PratilipiListV1ApiRequest );


		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		pratilipiFilter.setLanguage( language );
		pratilipiFilter.setState( pratilipiState );

		Gson gson = new Gson();

		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "title", SEOTitleUtil.getSearchPageTitle( language ) );
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
		dataModel.put( "title", SEOTitleUtil.getNotificationsPageTitle( language ) );
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
		
		PratilipiListV2Api.GetRequest pratilipiListRequest = new PratilipiListV2Api.GetRequest();
		pratilipiListRequest.setListName( listName );
		pratilipiListRequest.setLanguage( filterLanguage );
		pratilipiListRequest.setType( type );
		pratilipiListRequest.setState( PratilipiState.PUBLISHED );
		pratilipiListRequest.setOffset( offset );
		PratilipiListV2Api.Response pratilipiListResponse = ApiRegistry
				.getApi( PratilipiListV2Api.class )
				.get( pratilipiListRequest );
		
		
		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		pratilipiFilter.setLanguage( filterLanguage );
		pratilipiFilter.setType( type );
		pratilipiFilter.setListName( listName );
		pratilipiFilter.setState( PratilipiState.PUBLISHED );

		
		Map<String, Object> dataModel = new HashMap<String, Object>();
		if( listName != null )
			dataModel.put( "title", SEOTitleUtil.getListPageTitle( listName, filterLanguage ) );
		else
			dataModel.put( "title", I18n.getString( "pratilipi", filterLanguage ) );
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

		StringBuilder content = new StringBuilder();
		String staticTitle = null;
		try {
			String fileName = "static." + ( lang == null ? "" : lang.getCode() + "." ) + pageName;
			File file = new File( getClass().getResource( dataFilePrefix + fileName ).toURI() );
			LineIterator it = FileUtils.lineIterator( file, "UTF-8" );
			if( it.hasNext() )
				staticTitle = it.nextLine().trim();
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
		dataModel.put( "title", SEOTitleUtil.getStaticPageTitle( pageName, lang ) );
		dataModel.put( "staticTitle", staticTitle );
		dataModel.put( "content", content.toString() );

		return dataModel;
	}
	
	public Map<String, Object> createDataModelForEmailTemplatesPage( Language language ) 
			throws UnexpectedServerException, InsufficientAccessException {

		if( ! UserAccessUtil.hasUserAccess( AccessTokenFilter.getAccessToken().getUserId(), language, AccessType.I18N_UPDATE ) )
			throw new InsufficientAccessException();

		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "title", "Email Templates - Admin Access" );

		String body = new String();
		for( EmailType eT : EmailType.values() ) {
			body = body + "<h4>" + eT.getDescription() + "</h4>";
			body = body + EmailTemplateUtil.getEmailTemplate( eT, language );
			body = body + "<br>";
		}

		dataModel.put( "body", body );
		return dataModel;

	}

	private List<PratilipiV2Api.Response> toListResponseObject( List<PratilipiData> pratilipiDataList ) {
		List<PratilipiV2Api.Response> pratilipiList = new ArrayList<>( pratilipiDataList.size() );
		for( PratilipiData pratilipiData : pratilipiDataList )
			pratilipiList.add( new PratilipiV2Api.Response( pratilipiData, true ) );
		return pratilipiList;
	}


	public boolean isEligibleForPWA( String email, Language language ) {
		if( email == null )
			return false;

		List<String> lines = new ArrayList<>();
		String fileName = "data/pwa-user-list." + language.getCode();
		try {
			File file = new File( getClass().getResource( fileName ).toURI() );
			LineIterator it = FileUtils.lineIterator( file, "UTF-8" );
			while( it.hasNext() ) lines.add( it.next().trim() );
			LineIterator.closeQuietly( it );
		} catch( URISyntaxException | IOException e ) {
			logger.log( Level.SEVERE, "Exception in reading file: " + fileName, e );
		}

		for( String line : lines ) {
			if( line.isEmpty() ) continue;
			if( email.equals( line ) ) return true;
		}

		return false;

	}

}
