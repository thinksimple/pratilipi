package com.pratilipi.servlet;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;

import com.claymus.data.transfer.Page;
import com.claymus.data.transfer.PageContent;
import com.claymus.data.transfer.User;
import com.claymus.data.transfer.WebsiteWidget;
import com.claymus.module.websitewidget.html.HtmlWidget;
import com.claymus.module.websitewidget.html.HtmlWidgetFactory;
import com.claymus.pagecontent.PageContentRegistry;
import com.claymus.pagecontent.filebrowser.FileBrowserHelper;
import com.claymus.pagecontent.html.HtmlContent;
import com.claymus.pagecontent.html.HtmlContentHelper;
import com.claymus.servlet.ClaymusMain;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.commons.shared.PratilipiPageType;
import com.pratilipi.commons.shared.PratilipiState;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.pagecontent.author.AuthorContentHelper;
import com.pratilipi.pagecontent.authors.AuthorsContentFactory;
import com.pratilipi.pagecontent.genres.GenresContentHelper;
import com.pratilipi.pagecontent.home.HomeContent;
import com.pratilipi.pagecontent.home.HomeContentFactory;
import com.pratilipi.pagecontent.languages.LanguagesContentFactory;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;
import com.pratilipi.pagecontent.pratilipis.PratilipisContentFactory;
import com.pratilipi.pagecontent.reader.ReaderContentFactory;
import com.pratilipi.pagecontent.search.SearchContentHelper;
import com.pratilipi.pagecontent.uploadcontent.UploadContentFactory;

import freemarker.template.Template;
import freemarker.template.TemplateException;


@SuppressWarnings("serial")
public class PratilipiMain extends ClaymusMain {
	
	private static final Logger logger = 
			Logger.getLogger( PratilipiMain.class.getName() );

	static {
		PageContentRegistry.register( PratilipiContentHelper.class );
		PageContentRegistry.register( AuthorContentHelper.class );
		PageContentRegistry.register( HomeContentFactory.class );
		PageContentRegistry.register( PratilipisContentFactory.class );
		PageContentRegistry.register( ReaderContentFactory.class );
		PageContentRegistry.register( LanguagesContentFactory.class );
		PageContentRegistry.register( AuthorsContentFactory.class );
		PageContentRegistry.register( GenresContentHelper.class );
		PageContentRegistry.register( SearchContentHelper.class );
		PageContentRegistry.register( UploadContentFactory.class );
	}


	@Override
	protected String getTemplateName() {
		return "com/pratilipi/servlet/PratilipiTemplate.ftl";
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

		
		// Individual item's readers
		else if( requestUri.startsWith( PratilipiHelper.getReaderPageUrl( PratilipiType.BOOK, null ) ) ) {
			Long pratilipiId = Long.parseLong( requestUri.substring( requestUri.lastIndexOf( '/' ) + 1 ) );
			Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
			page.setTitle( pratilipi.getTitle() );
		
		} else if( requestUri.startsWith( PratilipiHelper.getReaderPageUrl( PratilipiType.POEM, null ) ) ) {
			Long pratilipiId = Long.parseLong( requestUri.substring( requestUri.lastIndexOf( '/' ) + 1 ) );
			Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
			page.setTitle( pratilipi.getTitle() );
		
		} else if( requestUri.startsWith( PratilipiHelper.getReaderPageUrl( PratilipiType.STORY, null ) ) ) {
			Long pratilipiId = Long.parseLong( requestUri.substring( requestUri.lastIndexOf( '/' ) + 1 ) );
			Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
			page.setTitle( pratilipi.getTitle() );
		
		} else if( requestUri.startsWith( PratilipiHelper.getReaderPageUrl( PratilipiType.ARTICLE, null ) ) ) {
			Long pratilipiId = Long.parseLong( requestUri.substring( requestUri.lastIndexOf( '/' ) + 1 ) );
			Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
			page.setTitle( pratilipi.getTitle() );
		
		} 

		
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
	protected List<PageContent> getPageContentList(
			HttpServletRequest request ) throws IOException {
	
		String requestUri = request.getRequestURI();
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Page page = dataAccessor.getPage( requestUri );
		
		List<PageContent> pageContentList = super.getPageContentList( request );
		
		if( page != null && page.getType().equals( PratilipiPageType.AUTHOR.toString() ) ) {
			pageContentList.add( AuthorContentHelper.newAuthorContent( page.getPrimaryContentId() ) );
		
		} else if( page != null && page.getType().equals( PratilipiPageType.PRATILIPI.toString() ) ) {
			pageContentList.add( PratilipiContentHelper.newPratilipiContent( page.getPrimaryContentId() ) );
		}
		
		
		// Home pages
		else if( requestUri.equals( "/" ) )
			pageContentList.add( generateHomePageContent( request ) );

		else if( requestUri.equals( "/books" ) )
			pageContentList.add( PratilipisContentFactory.newPratilipisContent( PratilipiType.BOOK, PratilipiState.PUBLISHED ) );
		
		else if( requestUri.equals( "/books/hindi" ) )
			pageContentList.add( PratilipisContentFactory.newPratilipisContent( PratilipiType.BOOK, 5130467284090880L, PratilipiState.PUBLISHED ) );
		
		else if( requestUri.equals( "/books/gujarati" ) )
			pageContentList.add( PratilipisContentFactory.newPratilipisContent( PratilipiType.BOOK, 5965057007550464L, PratilipiState.PUBLISHED ) );
		
		else if( requestUri.equals( "/books/tamil" ) )
			pageContentList.add( PratilipisContentFactory.newPratilipisContent( PratilipiType.BOOK, 6319546696728576L, PratilipiState.PUBLISHED ) );
		
		else if( requestUri.equals( "/poems" ) )
			pageContentList.add( PratilipisContentFactory.newPratilipisContent( PratilipiType.POEM, PratilipiState.PUBLISHED ) );
		
		else if( requestUri.equals( "/poems/hindi" ) )
			pageContentList.add( PratilipisContentFactory.newPratilipisContent( PratilipiType.POEM, 5130467284090880L, PratilipiState.PUBLISHED ) );
		
		else if( requestUri.equals( "/poems/gujarati" ) )
			pageContentList.add( PratilipisContentFactory.newPratilipisContent( PratilipiType.POEM, 5965057007550464L, PratilipiState.PUBLISHED ) );
		
		else if( requestUri.equals( "/stories" ) )
			pageContentList.add( PratilipisContentFactory.newPratilipisContent( PratilipiType.STORY, PratilipiState.PUBLISHED ) );
		
		else if( requestUri.equals( "/stories/hindi" ) )
			pageContentList.add( PratilipisContentFactory.newPratilipisContent( PratilipiType.STORY, 5130467284090880L, PratilipiState.PUBLISHED ) );
		
		else if( requestUri.equals( "/stories/gujarati" ) )
			pageContentList.add( PratilipisContentFactory.newPratilipisContent( PratilipiType.STORY, 5965057007550464L, PratilipiState.PUBLISHED ) );
		
		else if( requestUri.equals( "/articles" ) )
			pageContentList.add( PratilipisContentFactory.newPratilipisContent( PratilipiType.ARTICLE, PratilipiState.PUBLISHED ) );
		
		else if( requestUri.equals( "/articles/hindi" ) )
			pageContentList.add( PratilipisContentFactory.newPratilipisContent( PratilipiType.ARTICLE, 5130467284090880L, PratilipiState.PUBLISHED ) );
		
		else if( requestUri.equals( "/articles/gujarati" ) )
			pageContentList.add( PratilipisContentFactory.newPratilipisContent( PratilipiType.ARTICLE, 5965057007550464L, PratilipiState.PUBLISHED ) );
		
		else if( requestUri.startsWith( "/classics/books" ) )
			pageContentList.add( PratilipisContentFactory.newPratilipisContent( PratilipiType.BOOK, true, PratilipiState.PUBLISHED ) );

		else if( requestUri.startsWith( "/classics/poems" ) )
			pageContentList.add( PratilipisContentFactory.newPratilipisContent( PratilipiType.POEM, true, PratilipiState.PUBLISHED ) );

		else if( requestUri.startsWith( "/classics/stories" ) )
			pageContentList.add( PratilipisContentFactory.newPratilipisContent( PratilipiType.STORY, true, PratilipiState.PUBLISHED ) );

		
		else if( requestUri.startsWith( "/magazines" ) )
			pageContentList.add( PratilipisContentFactory.newPratilipisContent( PratilipiType.MAGAZINE, PratilipiState.PUBLISHED ) );

		
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

		
		// Individual item's readers
		else if( requestUri.startsWith( PratilipiHelper.getReaderPageUrl( PratilipiType.BOOK, null ) ) )
			pageContentList.add( ReaderContentFactory.newReaderContent( PratilipiType.BOOK ) );

		else if( requestUri.startsWith( PratilipiHelper.getReaderPageUrl( PratilipiType.POEM, null ) ) )
			pageContentList.add( ReaderContentFactory.newReaderContent( PratilipiType.POEM ) );
		
		else if( requestUri.startsWith( PratilipiHelper.getReaderPageUrl( PratilipiType.STORY, null ) ) )
			pageContentList.add( ReaderContentFactory.newReaderContent( PratilipiType.STORY ) );
		
		else if( requestUri.startsWith( PratilipiHelper.getReaderPageUrl( PratilipiType.ARTICLE, null ) ) )
			pageContentList.add( ReaderContentFactory.newReaderContent( PratilipiType.ARTICLE ) );

		else if( requestUri.startsWith( PratilipiHelper.getReaderPageUrl( PratilipiType.MAGAZINE, null ) ) )
			pageContentList.add( ReaderContentFactory.newReaderContent( PratilipiType.MAGAZINE ) );

		
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
	protected List<WebsiteWidget> getWebsiteWidgetList(
			HttpServletRequest request ) throws IOException {
		
		List<WebsiteWidget> websiteWidgetList
				= super.getWebsiteWidgetList( request );

		HtmlWidget headerWidget = generateHeaderWidget( request );
		headerWidget.setPosition( "HEADER" );
		websiteWidgetList.add( headerWidget );
		
		HtmlWidget footerWidget = generateHtmlWidgetFromFile( "WEB-INF/classes/com/pratilipi/servlet/content/FooterWidget.ftl" );
		footerWidget.setPosition( "FOOTER" );
		websiteWidgetList.add( footerWidget );
		
		return websiteWidgetList;
	}

	private HtmlContent generateHtmlContentFromFile(
			String fileName ) throws IOException {
		
		File file = new File( fileName );
		List<String> lines = FileUtils.readLines( file, "UTF-8" );
		String html = "";
		for( String line : lines )
			html = html + line;
		HtmlContent htmlContent = HtmlContentHelper.newHtmlContent();
		htmlContent.setContent( html );
		return htmlContent;
	}
	
	private HtmlWidget generateHtmlWidgetFromFile(
			String fileName ) throws IOException {
		
		File file = new File( fileName );
		List<String> lines = FileUtils.readLines( file, "UTF-8" );
		String html = "";
		for( String line : lines )
			html = html + line;
		HtmlWidget htmlWidget = HtmlWidgetFactory.newHtmlWidget();
		htmlWidget.setHtml( html );
		return htmlWidget;
	}

	private HtmlWidget generateHeaderWidget(
			HttpServletRequest request ) throws IOException {
		
		Writer writer = new StringWriter();
		Template template = FREEMARKER_CONFIGURATION
				.getTemplate( "com/pratilipi/servlet/content/HeaderWidget.ftl" );
		
		PratilipiHelper pratilipiHelper = PratilipiHelper.get( request );
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Long userId = pratilipiHelper.getCurrentUserId();
		User user = null;
		
		if( userId != 0 )
			user = dataAccessor.getUser( pratilipiHelper.getCurrentUserId() );
		
		Map<String, Object> dataModal = new HashMap<>();
		dataModal.put( "user", user);
		dataModal.put( "isUserLoggedIn", pratilipiHelper.isUserLoggedIn() );
		
		
		try {
			template.process( dataModal , writer );
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.log( Level.SEVERE, "", e );
		}

		HtmlWidget htmlWidget = HtmlWidgetFactory.newHtmlWidget();
		htmlWidget.setHtml( writer.toString() );
		return htmlWidget;
	}

	private PageContent generateHomePageContent(
			HttpServletRequest request ) throws IOException {
		
		List<Long> bookIdList = new LinkedList<>();
		bookIdList.add( 5157903266742272L );
		bookIdList.add( 5662800294707200L );
		bookIdList.add( 6029594259881984L );
		bookIdList.add( 5169145746292736L );
		bookIdList.add( 5202396712009728L );
		bookIdList.add( 4724779168825344L );

		List<Long> poemIdList = new LinkedList<>();
		poemIdList.add( 5677675511283712L );
		poemIdList.add( 5713144022302720L );
		poemIdList.add( 5753568388710400L );
		poemIdList.add( 5733831940243456L );
		poemIdList.add( 5752142325350400L );
		poemIdList.add( 5696014753202176L );
		
		List<Long> storyIdList = new LinkedList<>();
		storyIdList.add( 5639221461123072L );
		storyIdList.add( 5713144022302720L );
		storyIdList.add( 5753568388710400L );
		storyIdList.add( 5733831940243456L );
		storyIdList.add( 5752142325350400L );
		storyIdList.add( 5696014753202176L );

		
		HomeContent homeContent = HomeContentFactory.newHomeContent();
		homeContent.setBookIdList( bookIdList );
		homeContent.setPoemIdList( poemIdList );
		homeContent.setStoryIdList( storyIdList );
		homeContent.setLastUpdated( new Date( 30 ) );
		return homeContent;
	}

}
