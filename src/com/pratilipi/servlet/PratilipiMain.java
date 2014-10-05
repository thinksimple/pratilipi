package com.pratilipi.servlet;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
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
import com.claymus.module.pagecontent.html.HtmlContent;
import com.claymus.module.pagecontent.html.HtmlContentFactory;
import com.claymus.module.websitewidget.html.HtmlWidget;
import com.claymus.module.websitewidget.html.HtmlWidgetFactory;
import com.claymus.servlet.ClaymusMain;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.pagecontent.author.AuthorContentFactory;
import com.pratilipi.pagecontent.authors.AuthorsContentFactory;
import com.pratilipi.pagecontent.genres.GenresContentFactory;
import com.pratilipi.pagecontent.home.HomeContent;
import com.pratilipi.pagecontent.home.HomeContentFactory;
import com.pratilipi.pagecontent.languages.LanguagesContentFactory;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentFactory;
import com.pratilipi.pagecontent.pratilipis.PratilipisContentFactory;
import com.pratilipi.pagecontent.reader.ReaderContentFactory;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@SuppressWarnings("serial")
public class PratilipiMain extends ClaymusMain {
	
	private static final Logger logger = 
			Logger.getLogger( PratilipiMain.class.getName() );

	static {
		PAGE_CONTENT_REGISTRY.register( HomeContentFactory.class );
		PAGE_CONTENT_REGISTRY.register( PratilipisContentFactory.class );
		PAGE_CONTENT_REGISTRY.register( PratilipiContentFactory.class );
		PAGE_CONTENT_REGISTRY.register( ReaderContentFactory.class );
		PAGE_CONTENT_REGISTRY.register( LanguagesContentFactory.class );
		PAGE_CONTENT_REGISTRY.register( AuthorContentFactory.class );
		PAGE_CONTENT_REGISTRY.register( AuthorsContentFactory.class );
		PAGE_CONTENT_REGISTRY.register( GenresContentFactory.class );
	}


	@Override
	protected String getTemplateName() {
		return "com/pratilipi/servlet/PratilipiTemplate.ftl";
	}

	@Override
	protected Page getPage( HttpServletRequest request ) {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Page page = dataAccessor.newPage();
		page.setTitle( "Pratilipi" );
		
		// Home pages
		String requestUri = request.getRequestURI();
		
		if( requestUri.equals( "/" ) )
			page.setTitle( page.getTitle() + " | Read Free and Paid Hindi and Gujarati Stories, Poems and Books" );
		
		else if( requestUri.equals( "/books" ) )
			page.setTitle( "Books | " + page.getTitle() );
		
		else if( requestUri.equals( "/books/hindi" ) )
			page.setTitle( "Hindi Books | " + page.getTitle() );
		
		else if( requestUri.equals( "/books/gujarati" ) )
			page.setTitle( "Gujarati Books | " + page.getTitle() );
		
		else if( requestUri.equals( "/poems" ) )
			page.setTitle( "Poems | " + page.getTitle() );
		
		else if( requestUri.equals( "/poems/hindi" ) )
			page.setTitle( "Hindi Poems | " + page.getTitle() );
		
		else if( requestUri.equals( "/poems/gujarati" ) )
			page.setTitle( "Gujarati Poems | " + page.getTitle() );
		
		else if( requestUri.equals( "/stories" ) )
			page.setTitle( "Stories | " + page.getTitle() );
		
		else if( requestUri.equals( "/stories/hindi" ) )
			page.setTitle( "Hindi Stories | " + page.getTitle() );
		
		else if( requestUri.equals( "/stories/gujarati" ) )
			page.setTitle( "Gujarati Stories | " + page.getTitle() );
		
		else if( requestUri.equals( "/articles" ) )
			page.setTitle( "Articles | " + page.getTitle() );
		
		else if( requestUri.equals( "/articles/hindi" ) )
			page.setTitle( "Hindi Articles | " + page.getTitle() );
		
		else if( requestUri.equals( "/articles/gujarati" ) )
			page.setTitle( "Gujarati Articles | " + page.getTitle() );
		
		else if( requestUri.startsWith( "/classics/books" ) )
			page.setTitle( "Classic Books | " + page.getTitle() );

		else if( requestUri.startsWith( "/classics/poems" ) )
			page.setTitle( "Classic Poems | " + page.getTitle() );

		else if( requestUri.startsWith( "/classics/stories" ) )
			page.setTitle( "Classic Stories | " + page.getTitle() );

		else if( requestUri.startsWith( "/classics/articles" ) )
			page.setTitle( "Classic Articles | " + page.getTitle() );

		
		else if( requestUri.equals( "/languages" ) )
			page.setTitle( "Languages | " + page.getTitle() );

		else if( requestUri.equals( "/authors" ) )
			page.setTitle( "Authors | " + page.getTitle() );

		else if( requestUri.equals( "/genres" ) )
			page.setTitle( "Genres | " + page.getTitle() );

		
		// Individual item's pages
		else if( requestUri.startsWith( PratilipiHelper.getPageUrl( PratilipiType.BOOK, null ) ) ) {
			Long pratilipiId = Long.parseLong( requestUri.substring( requestUri.lastIndexOf( '/' ) + 1 ) );
			Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
			page.setTitle( pratilipi.getTitle() + " | " + page.getTitle() );

		} else if( requestUri.startsWith( PratilipiHelper.getPageUrl( PratilipiType.POEM, null ) ) ) {
			Long pratilipiId = Long.parseLong( requestUri.substring( requestUri.lastIndexOf( '/' ) + 1 ) );
			Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
			page.setTitle( pratilipi.getTitle() + " | " + page.getTitle() );

		} else if( requestUri.startsWith( PratilipiHelper.getPageUrl( PratilipiType.STORY, null ) ) ) {
			Long pratilipiId = Long.parseLong( requestUri.substring( requestUri.lastIndexOf( '/' ) + 1 ) );
			Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
			page.setTitle( pratilipi.getTitle() + " | " + page.getTitle() );

		} else if( requestUri.startsWith( PratilipiHelper.getPageUrl( PratilipiType.ARTICLE, null ) ) ) {
			Long pratilipiId = Long.parseLong( requestUri.substring( requestUri.lastIndexOf( '/' ) + 1 ) );
			Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
			page.setTitle( pratilipi.getTitle() + " | " + page.getTitle() );


		} else if( requestUri.startsWith( PratilipiHelper.URL_AUTHOR_PAGE ) ) {
			Long authorId = Long.parseLong( requestUri.substring( PratilipiHelper.URL_AUTHOR_PAGE.length() ) );
			Author author = dataAccessor.getAuthor( authorId );
			String authorName = author.getFirstName();
			if( author.getLastName() != null )
				authorName += " " + author.getLastName();
			page.setTitle( authorName + " | " + page.getTitle() );
		}
		
		
		// Individual item's readers
		else if( requestUri.startsWith( PratilipiHelper.getReaderPageUrl( PratilipiType.BOOK, null ) ) ) {
			Long pratilipiId = Long.parseLong( requestUri.substring( requestUri.lastIndexOf( '/' ) + 1 ) );
			Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
			page.setTitle( pratilipi.getTitle() + " | " + page.getTitle() );
		
		} else if( requestUri.startsWith( PratilipiHelper.getReaderPageUrl( PratilipiType.POEM, null ) ) ) {
			Long pratilipiId = Long.parseLong( requestUri.substring( requestUri.lastIndexOf( '/' ) + 1 ) );
			Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
			page.setTitle( pratilipi.getTitle() + " | " + page.getTitle() );
		
		} else if( requestUri.startsWith( PratilipiHelper.getReaderPageUrl( PratilipiType.STORY, null ) ) ) {
			Long pratilipiId = Long.parseLong( requestUri.substring( requestUri.lastIndexOf( '/' ) + 1 ) );
			Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
			page.setTitle( pratilipi.getTitle() + " | " + page.getTitle() );
		
		} else if( requestUri.startsWith( PratilipiHelper.getReaderPageUrl( PratilipiType.ARTICLE, null ) ) ) {
			Long pratilipiId = Long.parseLong( requestUri.substring( requestUri.lastIndexOf( '/' ) + 1 ) );
			Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
			page.setTitle( pratilipi.getTitle() + " | " + page.getTitle() );
		
		} 

		
		// Static pages
		else if( requestUri.equals( "/contact" ) )
			page.setTitle( "Contact | " + page.getTitle() );
			
		else if( requestUri.equals( "/faq" ) )
			page.setTitle( "FAQ | " + page.getTitle() );
		
		else if( requestUri.equals( "/about/pratilipi" ) )
			page.setTitle( "About Pratilipi | " + page.getTitle() );
		
		else if( requestUri.equals( "/about/team" ) )
			page.setTitle( "About Team | " + page.getTitle() );		

		else if( requestUri.equals( "/about/the-founding-readers" ) )
			page.setTitle( "About The Founding Readers | " + page.getTitle() );
		
		dataAccessor.destroy();

		return page;
	}
	
	@Override
	protected List<PageContent> getPageContentList(
			HttpServletRequest request ) throws IOException {
	
		List<PageContent> pageContentList
				= super.getPageContentList( request );
		
		// Home pages
		String requestUri = request.getRequestURI();
		if( requestUri.equals( "/" ) )
			pageContentList.add( generateHomePageContent( request ) );

		else if( requestUri.equals( "/books" ) )
			pageContentList.add( PratilipisContentFactory.newPratilipisContent( PratilipiType.BOOK ) );
		
		else if( requestUri.equals( "/books/hindi" ) )
			pageContentList.add( PratilipisContentFactory.newPratilipisContent( PratilipiType.BOOK, 5130467284090880L ) );
		
		else if( requestUri.equals( "/books/gujarati" ) )
			pageContentList.add( PratilipisContentFactory.newPratilipisContent( PratilipiType.BOOK, 5965057007550464L ) );
		
		else if( requestUri.equals( "/poems" ) )
			pageContentList.add( PratilipisContentFactory.newPratilipisContent( PratilipiType.POEM ) );
		
		else if( requestUri.equals( "/poems/hindi" ) )
			pageContentList.add( PratilipisContentFactory.newPratilipisContent( PratilipiType.POEM, 5130467284090880L ) );
		
		else if( requestUri.equals( "/poems/gujarati" ) )
			pageContentList.add( PratilipisContentFactory.newPratilipisContent( PratilipiType.POEM, 5965057007550464L ) );
		
		else if( requestUri.equals( "/stories" ) )
			pageContentList.add( PratilipisContentFactory.newPratilipisContent( PratilipiType.STORY ) );
		
		else if( requestUri.equals( "/stories/hindi" ) )
			pageContentList.add( PratilipisContentFactory.newPratilipisContent( PratilipiType.STORY, 5130467284090880L ) );
		
		else if( requestUri.equals( "/stories/gujarati" ) )
			pageContentList.add( PratilipisContentFactory.newPratilipisContent( PratilipiType.STORY, 5965057007550464L ) );
		
		else if( requestUri.equals( "/articles" ) )
			pageContentList.add( PratilipisContentFactory.newPratilipisContent( PratilipiType.ARTICLE ) );
		
		else if( requestUri.equals( "/articles/hindi" ) )
			pageContentList.add( PratilipisContentFactory.newPratilipisContent( PratilipiType.ARTICLE, 5130467284090880L ) );
		
		else if( requestUri.equals( "/articles/gujarati" ) )
			pageContentList.add( PratilipisContentFactory.newPratilipisContent( PratilipiType.ARTICLE, 5965057007550464L ) );
		
		else if( requestUri.startsWith( "/classics/books" ) )
			pageContentList.add( PratilipisContentFactory.newPratilipisContent( PratilipiType.BOOK, true ) );

		else if( requestUri.startsWith( "/classics/poems" ) )
			pageContentList.add( PratilipisContentFactory.newPratilipisContent( PratilipiType.POEM, true ) );

		else if( requestUri.startsWith( "/classics/stories" ) )
			pageContentList.add( PratilipisContentFactory.newPratilipisContent( PratilipiType.STORY, true ) );

		else if( requestUri.startsWith( "/classics/articles" ) )
			pageContentList.add( PratilipisContentFactory.newPratilipisContent( PratilipiType.ARTICLE, true ) );

		
		else if( requestUri.equals( "/languages" ) )
			pageContentList.add( LanguagesContentFactory.newLanguagesContent() );

		else if( requestUri.equals( "/authors" ) )
			pageContentList.add( AuthorsContentFactory.newAuthorsContent() );

		else if( requestUri.equals( "/genres" ) )
			pageContentList.add( GenresContentFactory.newGenresContent() );

		
		// Individual item's pages
		else if( requestUri.startsWith( PratilipiHelper.getPageUrl( PratilipiType.BOOK, null ) ) )
			pageContentList.add( PratilipiContentFactory.newPratilipiContent( Long.parseLong( requestUri.substring( requestUri.lastIndexOf( '/' ) + 1 ) ), PratilipiType.BOOK ) );

		else if( requestUri.startsWith( PratilipiHelper.getPageUrl( PratilipiType.POEM, null ) ) )
			pageContentList.add( PratilipiContentFactory.newPratilipiContent( Long.parseLong( requestUri.substring( requestUri.lastIndexOf( '/' ) + 1 ) ), PratilipiType.POEM ) );

		else if( requestUri.startsWith( PratilipiHelper.getPageUrl( PratilipiType.STORY, null ) ) )
			pageContentList.add( PratilipiContentFactory.newPratilipiContent( Long.parseLong( requestUri.substring( requestUri.lastIndexOf( '/' ) + 1 ) ), PratilipiType.STORY ) );

		else if( requestUri.startsWith( PratilipiHelper.getPageUrl( PratilipiType.ARTICLE, null ) ) )
			pageContentList.add( PratilipiContentFactory.newPratilipiContent( Long.parseLong( requestUri.substring( requestUri.lastIndexOf( '/' ) + 1 ) ), PratilipiType.ARTICLE ) );

		
		else if( requestUri.startsWith( PratilipiHelper.URL_AUTHOR_PAGE ) )
			pageContentList.add( AuthorContentFactory.newAuthorContent( Long.parseLong( requestUri.substring( PratilipiHelper.URL_AUTHOR_PAGE.length() ) ) ) );

		
		// Individual item's readers
		else if( requestUri.startsWith( PratilipiHelper.getReaderPageUrl( PratilipiType.BOOK, null ) ) )
			pageContentList.add( ReaderContentFactory.newReaderContent( PratilipiType.BOOK ) );

		else if( requestUri.startsWith( PratilipiHelper.getReaderPageUrl( PratilipiType.POEM, null ) ) )
			pageContentList.add( ReaderContentFactory.newReaderContent( PratilipiType.POEM ) );
		
		else if( requestUri.startsWith( PratilipiHelper.getReaderPageUrl( PratilipiType.STORY, null ) ) )
			pageContentList.add( ReaderContentFactory.newReaderContent( PratilipiType.STORY ) );
		
		else if( requestUri.startsWith( PratilipiHelper.getReaderPageUrl( PratilipiType.ARTICLE, null ) ) )
			pageContentList.add( ReaderContentFactory.newReaderContent( PratilipiType.ARTICLE ) );

		
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
		HtmlContent htmlContent = HtmlContentFactory.newHtmlContent();
		htmlContent.setHtml( html );
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
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
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
		bookIdList.add( 4920152667717632L );
		bookIdList.add( 6229935693758464L );
		bookIdList.add( 5144374119759872L );
		bookIdList.add( 5083798974758912L );
		bookIdList.add( 6029594259881984L );
		bookIdList.add( 5703274724327424L );

		List<Long> poemIdList = new LinkedList<>();
		poemIdList.add( 4699503957377024L );
		poemIdList.add( 5744125232021504L );
		poemIdList.add( 5405337406406656L );
		poemIdList.add( 5764577597849600L );
		poemIdList.add( 6000473039437824L );
		poemIdList.add( 4768179276480512L );
		
		List<Long> storyIdList = new LinkedList<>();
		storyIdList.add( 6041301266989056L );
		storyIdList.add( 4917249001390080L );
		storyIdList.add( 5190163571408896L );
		storyIdList.add( 5100102670614528L );
		storyIdList.add( 5092688483319808L );
		storyIdList.add( 6311069236592640L );

		
		HomeContent homeContent = HomeContentFactory.newHomeContent();
		homeContent.setBookIdList( bookIdList );
		homeContent.setPoemIdList( poemIdList );
		homeContent.setStoryIdList( storyIdList );
		return homeContent;
	}

}
