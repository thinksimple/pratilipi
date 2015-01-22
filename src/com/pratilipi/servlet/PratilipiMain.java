package com.pratilipi.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;

import com.claymus.commons.server.FreeMarkerUtil;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.transfer.Page;
import com.claymus.data.transfer.PageContent;
import com.claymus.data.transfer.User;
import com.claymus.data.transfer.WebsiteWidget;
import com.claymus.pagecontent.PageContentRegistry;
import com.claymus.pagecontent.filebrowser.FileBrowserHelper;
import com.claymus.pagecontent.html.HtmlContent;
import com.claymus.pagecontent.html.HtmlContentHelper;
import com.claymus.servlet.ClaymusMain;
import com.claymus.websitewidget.html.HtmlWidget;
import com.claymus.websitewidget.html.HtmlWidgetHelper;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.commons.shared.PratilipiPageType;
import com.pratilipi.commons.shared.PratilipiState;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.pagecontent.auditlog.AuditLogContentHelper;
import com.pratilipi.pagecontent.author.AuthorContentHelper;
import com.pratilipi.pagecontent.authors.AuthorsContentFactory;
import com.pratilipi.pagecontent.event.EventContentHelper;
import com.pratilipi.pagecontent.genres.GenresContentHelper;
import com.pratilipi.pagecontent.home.HomeContent;
import com.pratilipi.pagecontent.home.HomeContentFactory;
import com.pratilipi.pagecontent.languages.LanguagesContentFactory;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;
import com.pratilipi.pagecontent.pratilipis.PratilipisContentHelper;
import com.pratilipi.pagecontent.publisher.PublisherContentHelper;
import com.pratilipi.pagecontent.reader.ReaderContentHelper;
import com.pratilipi.pagecontent.search.SearchContentHelper;
import com.pratilipi.pagecontent.uploadcontent.UploadContentFactory;
import com.pratilipi.pagecontent.writer.WriterContentHelper;


@SuppressWarnings("serial")
public class PratilipiMain extends ClaymusMain {
	
	private static final Logger logger = 
			Logger.getLogger( PratilipiMain.class.getName() );

	static {
		PageContentRegistry.register( PratilipiContentHelper.class );
		PageContentRegistry.register( AuthorContentHelper.class );
		PageContentRegistry.register( PublisherContentHelper.class );
		PageContentRegistry.register( EventContentHelper.class );		// 5.0
		PageContentRegistry.register( HomeContentFactory.class );
		PageContentRegistry.register( PratilipisContentHelper.class );
		PageContentRegistry.register( ReaderContentHelper.class );		// 5.0
		PageContentRegistry.register( WriterContentHelper.class );		// 5.0
		PageContentRegistry.register( LanguagesContentFactory.class );
		PageContentRegistry.register( AuthorsContentFactory.class );
		PageContentRegistry.register( GenresContentHelper.class );
		PageContentRegistry.register( SearchContentHelper.class );
		PageContentRegistry.register( UploadContentFactory.class );
		PageContentRegistry.register( AuditLogContentHelper.class );
	}


	@Override
	protected Page getPage( HttpServletRequest request ) {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Page page = dataAccessor.newPage();
		
		// Home pages
		String requestUri = request.getRequestURI();
		
		if( requestUri.equals( "/" ) )
			page.setTitle( "Read Hindi and Gujarati Stories, Poems and Books" );
		
		else if( requestUri.equals( "/books" ) )
			page.setTitle( "Books" );
		
		else if( requestUri.equals( "/books/hindi" ) )
			page.setTitle( "Hindi Books" );
		
		else if( requestUri.equals( "/books/gujarati" ) )
			page.setTitle( "Gujarati Books" );
		
		else if( requestUri.equals( "/poems" ) )
			page.setTitle( "Poems" );
		
		else if( requestUri.equals( "/poems/hindi" ) )
			page.setTitle( "Hindi Poems" );
		
		else if( requestUri.equals( "/poems/gujarati" ) )
			page.setTitle( "Gujarati Poems" );
		
		else if( requestUri.equals( "/stories" ) )
			page.setTitle( "Stories" );
		
		else if( requestUri.equals( "/stories/hindi" ) )
			page.setTitle( "Hindi Stories" );
		
		else if( requestUri.equals( "/stories/gujarati" ) )
			page.setTitle( "Gujarati Stories" );
		
		else if( requestUri.equals( "/articles" ) )
			page.setTitle( "Articles" );
		
		else if( requestUri.equals( "/articles/hindi" ) )
			page.setTitle( "Hindi Articles" );
		
		else if( requestUri.equals( "/articles/gujarati" ) )
			page.setTitle( "Gujarati Articles" );
		
		else if( requestUri.startsWith( "/classics/books" ) )
			page.setTitle( "Classic Books" );

		else if( requestUri.startsWith( "/classics/poems" ) )
			page.setTitle( "Classic Poems" );

		else if( requestUri.startsWith( "/classics/stories" ) )
			page.setTitle( "Classic Stories" );

		
		else if( requestUri.startsWith( "/magazines" ) )
			page.setTitle( "Magazines" );

		
		else if( requestUri.equals( "/languages" ) )
			page.setTitle( "Languages" );

		else if( requestUri.equals( "/authors" ) )
			page.setTitle( "Authors" );

		else if( requestUri.equals( "/genres" ) )
			page.setTitle( "Genres" );
		
		else if( requestUri.equals( "/upload" ) )
			page.setTitle( "Upload Content" );

		
		// Static pages
		else if( requestUri.equals( "/contact" ) )
			page.setTitle( "Contact" );
			
		else if( requestUri.equals( "/faq" ) )
			page.setTitle( "FAQ" );
		
		else if( requestUri.equals( "/about/pratilipi" ) )
			page.setTitle( "About Pratilipi" );
		
		else if( requestUri.equals( "/about/team" ) )
			page.setTitle( "About Team" );		

		else if( requestUri.equals( "/about/the-founding-readers" ) )
			page.setTitle( "About The Founding Readers" );
		
		
		else
			page = super.getPage( request );


		return page;
	}
	
	@Override
	protected List<PageContent> getPageContentList( HttpServletRequest request ) {
	
		String requestUri = request.getRequestURI();
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Page page = dataAccessor.getPage( requestUri );
		
		List<PageContent> pageContentList = super.getPageContentList( request );
		
		if( page != null ) {
			
			if( page.getType().equals( PratilipiPageType.PRATILIPI.toString() ) ) {
				pageContentList.add( PratilipiContentHelper.newPratilipiContent( page.getPrimaryContentId() ) );

			} else if( page.getType().equals( PratilipiPageType.AUTHOR.toString() ) ) {
				pageContentList.add( AuthorContentHelper.newAuthorContent( page.getPrimaryContentId() ) );
		
			} else if( page.getType().equals( PratilipiPageType.PUBLISHER.toString() ) ) {
				pageContentList.add( PublisherContentHelper.newPublisherContent( page.getPrimaryContentId() ) );
		
			} else if( page.getType().equals( PratilipiPageType.EVENT.toString() ) ) {
				pageContentList.add( EventContentHelper.newEventContent( page.getPrimaryContentId() ) );
		
			}
			
		}
		
		
		// Home pages
		else if( requestUri.equals( "/" ) )
			pageContentList.add( generateHomePageContent( request ) );

		else if( requestUri.equals( "/books" ) )
			pageContentList.add( PratilipisContentHelper.newPratilipisContent( PratilipiType.BOOK, PratilipiState.PUBLISHED ) );
		
		else if( requestUri.equals( "/books/hindi" ) )
			pageContentList.add( PratilipisContentHelper.newPratilipisContent( PratilipiType.BOOK, 5130467284090880L, PratilipiState.PUBLISHED ) );
		
		else if( requestUri.equals( "/books/gujarati" ) )
			pageContentList.add( PratilipisContentHelper.newPratilipisContent( PratilipiType.BOOK, 5965057007550464L, PratilipiState.PUBLISHED ) );
		
		else if( requestUri.equals( "/books/tamil" ) )
			pageContentList.add( PratilipisContentHelper.newPratilipisContent( PratilipiType.BOOK, 6319546696728576L, PratilipiState.PUBLISHED ) );
		
		else if( requestUri.equals( "/poems" ) )
			pageContentList.add( PratilipisContentHelper.newPratilipisContent( PratilipiType.POEM, PratilipiState.PUBLISHED ) );
		
		else if( requestUri.equals( "/poems/hindi" ) )
			pageContentList.add( PratilipisContentHelper.newPratilipisContent( PratilipiType.POEM, 5130467284090880L, PratilipiState.PUBLISHED ) );
		
		else if( requestUri.equals( "/poems/gujarati" ) )
			pageContentList.add( PratilipisContentHelper.newPratilipisContent( PratilipiType.POEM, 5965057007550464L, PratilipiState.PUBLISHED ) );
		
		else if( requestUri.equals( "/stories" ) )
			pageContentList.add( PratilipisContentHelper.newPratilipisContent( PratilipiType.STORY, PratilipiState.PUBLISHED ) );
		
		else if( requestUri.equals( "/stories/hindi" ) )
			pageContentList.add( PratilipisContentHelper.newPratilipisContent( PratilipiType.STORY, 5130467284090880L, PratilipiState.PUBLISHED ) );
		
		else if( requestUri.equals( "/stories/gujarati" ) )
			pageContentList.add( PratilipisContentHelper.newPratilipisContent( PratilipiType.STORY, 5965057007550464L, PratilipiState.PUBLISHED ) );
		
		else if( requestUri.equals( "/articles" ) )
			pageContentList.add( PratilipisContentHelper.newPratilipisContent( PratilipiType.ARTICLE, PratilipiState.PUBLISHED ) );
		
		else if( requestUri.equals( "/articles/hindi" ) )
			pageContentList.add( PratilipisContentHelper.newPratilipisContent( PratilipiType.ARTICLE, 5130467284090880L, PratilipiState.PUBLISHED ) );
		
		else if( requestUri.equals( "/articles/gujarati" ) )
			pageContentList.add( PratilipisContentHelper.newPratilipisContent( PratilipiType.ARTICLE, 5965057007550464L, PratilipiState.PUBLISHED ) );
		
		else if( requestUri.startsWith( "/classics/books" ) )
			pageContentList.add( PratilipisContentHelper.newPratilipisContent( PratilipiType.BOOK, true, PratilipiState.PUBLISHED ) );

		else if( requestUri.startsWith( "/classics/poems" ) )
			pageContentList.add( PratilipisContentHelper.newPratilipisContent( PratilipiType.POEM, true, PratilipiState.PUBLISHED ) );

		else if( requestUri.startsWith( "/classics/stories" ) )
			pageContentList.add( PratilipisContentHelper.newPratilipisContent( PratilipiType.STORY, true, PratilipiState.PUBLISHED ) );

		
		else if( requestUri.startsWith( "/magazines" ) )
			pageContentList.add( PratilipisContentHelper.newPratilipisContent( PratilipiType.MAGAZINE, PratilipiState.PUBLISHED ) );

		
		else if( requestUri.equals( "/languages" ) )
			pageContentList.add( LanguagesContentFactory.newLanguagesContent() );

		else if( requestUri.equals( "/authors" ) )
			pageContentList.add( AuthorsContentFactory.newAuthorsContent() );

		else if( requestUri.equals( "/genres" ) )
			pageContentList.add( GenresContentHelper.newGenresContent() );

		else if( requestUri.equals( "/filebrowser" ))
			pageContentList.add( FileBrowserHelper.newFileBrowser() );
		
		else if( requestUri.equals( "/upload" ) )
			pageContentList.add( UploadContentFactory.newUploadContent() );

		
		// Static pages
		// Migrate these PageContents to DataStore
		else if( requestUri.equals( "/contact" ) )
			pageContentList.add( generateHtmlContentFromFile( "WEB-INF/classes/com/pratilipi/servlet/content/ContactPageContent.ftl" ) );
			
		else if( requestUri.equals( "/faq" ) )
			pageContentList.add( generateHtmlContentFromFile( "WEB-INF/classes/com/pratilipi/servlet/content/FaqPageContent.ftl" ) );
			
		else if( requestUri.equals( "/about/pratilipi" ) )
			pageContentList.add( generateHtmlContentFromFile( "WEB-INF/classes/com/pratilipi/servlet/content/AboutPratilipiPageContent.ftl" ) );

		else if( requestUri.equals( "/about/team" ) )
			pageContentList.add( generateHtmlContentFromFile( "WEB-INF/classes/com/pratilipi/servlet/content/AboutTeamPageContent.ftl" ) );
		
		else if( requestUri.equals( "/about/the-founding-readers" ) )
			pageContentList.add( generateHtmlContentFromFile( "WEB-INF/classes/com/pratilipi/servlet/content/AboutFoundingReadersPageContent.ftl" ) );
			
		
		return pageContentList;
	}
	
	@Override
	protected List<WebsiteWidget> getWebsiteWidgetList( HttpServletRequest request ) {
		List<WebsiteWidget> websiteWidgetList
				= super.getWebsiteWidgetList( request );

		if( !request.getRequestURI().equals( "/read" )
				&& !request.getRequestURI().equals( "/write" ) ) {
			
			HtmlWidget headerWidget = generateHeaderWidget( request );
			headerWidget.setPosition( "HEADER" );
			websiteWidgetList.add( headerWidget );
			
			HtmlWidget footerWidget;
			try {
				footerWidget = generateHtmlWidgetFromFile( "WEB-INF/classes/com/pratilipi/servlet/content/FooterWidget.ftl" );
				footerWidget.setPosition( "FOOTER" );
				websiteWidgetList.add( footerWidget );
			} catch( UnexpectedServerException e ) {
				// Do nothing
			}
		}
		
		return websiteWidgetList;
	}

	private HtmlContent generateHtmlContentFromFile( String fileName ) {
		File file = new File( fileName );
		List<String> lines;
		try {
			lines = FileUtils.readLines( file, "UTF-8" );
		} catch( IOException e ) {
			logger.log( Level.SEVERE, "Failed to read from file.", e );
			lines = new ArrayList<>( 0 );
		}
		String html = "";
		for( String line : lines )
			html = html + line;
		HtmlContent htmlContent = HtmlContentHelper.newHtmlContent();
		htmlContent.setContent( html );
		return htmlContent;
	}
	
	private HtmlWidget generateHtmlWidgetFromFile( String fileName )
			throws UnexpectedServerException {
		
		try {
			File file = new File( fileName );
			List<String> lines = FileUtils.readLines( file, "UTF-8" );
			String html = "";
			for( String line : lines )
				html = html + line;
			HtmlWidget htmlWidget = HtmlWidgetHelper.newHtmlWidget();
			htmlWidget.setHtml( html );
			return htmlWidget;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new UnexpectedServerException();
		}
	}

	private HtmlWidget generateHeaderWidget( HttpServletRequest request ) {
		
		PratilipiHelper pratilipiHelper = PratilipiHelper.get( request );
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Long userId = pratilipiHelper.getCurrentUserId();
		User user = null;
		Author author = null;
		
		if( userId != 0 ){
			user = dataAccessor.getUser( pratilipiHelper.getCurrentUserId() );
			author = dataAccessor.getAuthorByUserId( pratilipiHelper.getCurrentUserId() );
		}
		
		String authorPageUrl = null;
		if( author != null )
			authorPageUrl = pratilipiHelper.createAuthorData( author, null ).getPageUrl();
		
		Map<String, Object> dataModal = new HashMap<>();
		dataModal.put( "user", user);
		dataModal.put( "authorPageUrl", authorPageUrl );
		dataModal.put( "isUserLoggedIn", pratilipiHelper.isUserLoggedIn() );
		
		String html = "";
		try {
			html = FreeMarkerUtil.processTemplate( dataModal, "com/pratilipi/servlet/content/HeaderWidget.ftl" );
		} catch( UnexpectedServerException e ) {
			// Do Nothing
		}

		HtmlWidget htmlWidget = HtmlWidgetHelper.newHtmlWidget();
		htmlWidget.setHtml( html );
		return htmlWidget;
	}

	private PageContent generateHomePageContent( HttpServletRequest request ) {
		
		List<Long> bookIdList = new LinkedList<>();
		bookIdList.add( 5431799236788224L );
		bookIdList.add( 4858849794195456L );
		bookIdList.add( 6545160791916544L );
		bookIdList.add( 6254483914883072L );
		bookIdList.add( 5706144500678656L );
		bookIdList.add( 5662800294707200L );

		List<Long> poemIdList = new LinkedList<>();
		poemIdList.add( 5201547784880128L );
		poemIdList.add( 5140806746767360L );
		poemIdList.add( 6196023504404480L );
		poemIdList.add( 5720277627437056L );
		poemIdList.add( 5674053578784768L );
		poemIdList.add( 5149879126982656L );
		
		List<Long> storyIdList = new LinkedList<>();
		storyIdList.add( 5435171725639680L );
		storyIdList.add( 5681717746597888L );
		storyIdList.add( 4848992307380224L );
		storyIdList.add( 5705431468998656L );
		storyIdList.add( 5746478739881984L );
		storyIdList.add( 5146519523033088L );

		
		HomeContent homeContent = HomeContentFactory.newHomeContent();
		homeContent.setBookIdList( bookIdList );
		homeContent.setPoemIdList( poemIdList );
		homeContent.setStoryIdList( storyIdList );
		homeContent.setLastUpdated( new Date( 99 ) );
		return homeContent;
	}

}
