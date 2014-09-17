package com.pratilipi.servlet;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.data.transfer.PageContent;
import com.claymus.data.transfer.WebsiteWidget;
import com.claymus.module.pagecontent.html.HtmlContent;
import com.claymus.module.pagecontent.html.HtmlContentFactory;
import com.claymus.module.websitewidget.html.HtmlWidget;
import com.claymus.module.websitewidget.html.HtmlWidgetFactory;
import com.claymus.servlet.ClaymusMain;
import com.pratilipi.commons.shared.PratilipiHelper;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Language;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.pagecontent.author.AuthorContentFactory;
import com.pratilipi.pagecontent.authors.AuthorsContentFactory;
import com.pratilipi.pagecontent.genres.GenresContentFactory;
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
		PAGE_CONTENT_REGISTRY.register( AuthorContentFactory.class );
		PAGE_CONTENT_REGISTRY.register( AuthorsContentFactory.class );
		PAGE_CONTENT_REGISTRY.register( LanguagesContentFactory.class );
		PAGE_CONTENT_REGISTRY.register( GenresContentFactory.class );

		PAGE_CONTENT_REGISTRY.register( PratilipisContentFactory.class );
		PAGE_CONTENT_REGISTRY.register( PratilipiContentFactory.class );
		PAGE_CONTENT_REGISTRY.register( ReaderContentFactory.class );
	}


	@Override
	protected String getTemplateName() {
		return "com/pratilipi/servlet/PratilipiTemplate.ftl";
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
		else if( requestUri.startsWith( PratilipiType.BOOK.getPageUrl() ) )
			pageContentList.add( PratilipiContentFactory.newPratilipiContent( Long.parseLong( requestUri.substring( PratilipiType.BOOK.getPageUrl().length() ) ), PratilipiType.BOOK ) );

		else if( requestUri.startsWith( PratilipiType.POEM.getPageUrl() ) )
			pageContentList.add( PratilipiContentFactory.newPratilipiContent( Long.parseLong( requestUri.substring( PratilipiType.POEM.getPageUrl().length() ) ), PratilipiType.POEM ) );

		else if( requestUri.startsWith( PratilipiType.STORY.getPageUrl() ) )
			pageContentList.add( PratilipiContentFactory.newPratilipiContent( Long.parseLong( requestUri.substring( PratilipiType.STORY.getPageUrl().length() ) ), PratilipiType.STORY ) );

		else if( requestUri.startsWith( PratilipiType.ARTICLE.getPageUrl() ) )
			pageContentList.add( PratilipiContentFactory.newPratilipiContent( Long.parseLong( requestUri.substring( PratilipiType.ARTICLE.getPageUrl().length() ) ), PratilipiType.ARTICLE ) );

		
		else if( requestUri.startsWith( PratilipiHelper.URL_AUTHOR_PAGE ) )
			pageContentList.add( AuthorContentFactory.newAuthorContent( Long.parseLong( requestUri.substring( PratilipiHelper.URL_AUTHOR_PAGE.length() ) ) ) );

		
		// Individual item's readers
		else if( requestUri.startsWith( PratilipiType.BOOK.getReaderPageUrl() ) )
			pageContentList.add( ReaderContentFactory.newReaderContent( PratilipiType.BOOK ) );

		else if( requestUri.startsWith( PratilipiType.POEM.getReaderPageUrl() ) )
			pageContentList.add( ReaderContentFactory.newReaderContent( PratilipiType.POEM ) );
		
		else if( requestUri.startsWith( PratilipiType.STORY.getReaderPageUrl() ) )
			pageContentList.add( ReaderContentFactory.newReaderContent( PratilipiType.STORY ) );
		
		else if( requestUri.startsWith( PratilipiType.ARTICLE.getReaderPageUrl() ) )
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
		try {
			template.process( new ClaymusHelper( request ) , writer );
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.log( Level.SEVERE, "", e );
		}

		HtmlWidget htmlWidget = HtmlWidgetFactory.newHtmlWidget();
		htmlWidget.setHtml( writer.toString() );
		return htmlWidget;
	}

	private HtmlContent generateHomePageContent(
			HttpServletRequest request ) throws IOException {
		
		ClaymusHelper claymusHelper = new ClaymusHelper( request );

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		List<Pratilipi> bookDataList = new ArrayList<>(6);
		bookDataList.add( dataAccessor.getPratilipi( 5255539013451776L ) );
		bookDataList.add( dataAccessor.getPratilipi( 5543928887508992L ) );
		bookDataList.add( dataAccessor.getPratilipi( 4818859890573312L ) );
		bookDataList.add( dataAccessor.getPratilipi( 5331129229901824L ) );
		bookDataList.add( dataAccessor.getPratilipi( 6319699436503040L ) );
		bookDataList.add( dataAccessor.getPratilipi( 5345197126844416L ) );
		
		Map<String, String> bookCoverMap = new HashMap<>();
		Map<String, String> bookUrlMap = new HashMap<>();
		for( Pratilipi book : bookDataList ){
			if( bookCoverMap.get( book.getId() ) == null ){
				bookCoverMap.put( book.getId().toString(),
								  PratilipiHelper.URL_BOOK_COVER + book.getId() );
			}
			if( bookUrlMap.get( book.getId() ) == null ){
				bookUrlMap.put( book.getId().toString(), 
								PratilipiHelper.URL_BOOK_PAGE + book.getId() );
			}
		}
		
		List<Pratilipi> poemDataList = new ArrayList<>( 6 );
		poemDataList.add( dataAccessor.getPratilipi( 6528878302461952L ) );
		poemDataList.add( dataAccessor.getPratilipi( 5989123856793600L ) );
		poemDataList.add( dataAccessor.getPratilipi( 5834154725867520L ) );
		poemDataList.add( dataAccessor.getPratilipi( 5676982813589504L ) );
		poemDataList.add( dataAccessor.getPratilipi( 6480374163046400L ) );
		poemDataList.add( dataAccessor.getPratilipi( 4917068344328192L ) );

		Map<String, String> poemCoverMap = new HashMap<>();
		Map<String, String> poemUrlMap = new HashMap<>();
		for( Pratilipi poem : poemDataList ){
			if( poemCoverMap.get( poem.getId() ) == null ){
				poemCoverMap.put( poem.getId().toString(),
								  PratilipiHelper.URL_POEM_COVER + poem.getId() );
			}
			if( poemUrlMap.get( poem.getId() ) == null ){
				poemUrlMap.put( poem.getId().toString(),
								PratilipiHelper.URL_POEM_PAGE + poem.getId() );
			}
		}
		
		List<Pratilipi> storyDataList = new ArrayList<>( 6 );
		storyDataList.add( dataAccessor.getPratilipi( 5898452667990016L ) );
		storyDataList.add( dataAccessor.getPratilipi( 5100102670614528L ) );
		storyDataList.add( dataAccessor.getPratilipi( 6043148908232704L ) );
		storyDataList.add( dataAccessor.getPratilipi( 5162535120535552L ) );
		storyDataList.add( dataAccessor.getPratilipi( 5690091590647808L ) );
		storyDataList.add( dataAccessor.getPratilipi( 4752448690323456L ) );
		
		Map<String, String> storyCoverMap = new HashMap<>();
		Map<String, String> storyUrlMap = new HashMap<>();
		for( Pratilipi story : storyDataList ){
			if( storyCoverMap.get( story.getId() ) == null ){
				storyCoverMap.put( story.getId().toString(),
								   PratilipiHelper.URL_STORY_COVER + story.getId() );
			}
			if( storyUrlMap.get( story.getId() ) == null ){
				storyUrlMap.put( story.getId().toString(),
								 PratilipiHelper.URL_STORY_PAGE + story.getId() );
			}
		}
		//Fetching language list
		List<Language> languageList = dataAccessor.getLanguageList();
		Map<String, String> languageMap = new HashMap<>();
		for( Language language : languageList ){
			if( languageMap.get( language.getId() ) == null ){
				languageMap.put( language.getId().toString(),
								 language.getName() + " (" + language.getNameEn() + ")" );
			}
		}
		
		dataAccessor.destroy();
		
		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "bookDataList", bookDataList );
		dataModel.put( "bookCoverMap",  bookCoverMap );
		dataModel.put( "bookUrlMap",  bookUrlMap );
		dataModel.put( "poemDataList", poemDataList );
		dataModel.put( "poemCoverMap",  poemCoverMap );
		dataModel.put( "poemUrlMap",  poemUrlMap );
		dataModel.put( "storyDataList", storyDataList );
		dataModel.put( "storyCoverMap", storyCoverMap );
		dataModel.put( "storyUrlMap", storyUrlMap );
		dataModel.put( "languageMap", languageMap );
		dataModel.put( "timeZone", claymusHelper.getCurrentUserTimeZone() );
		
		
		Writer writer = new StringWriter();
		Template template = FREEMARKER_CONFIGURATION
				.getTemplate( "com/pratilipi/servlet/content/HomePageContent.ftl" );
		try {
			template.process( dataModel , writer );
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			logger.log( Level.SEVERE, "", e );
		}

		HtmlContent htmlContent = HtmlContentFactory.newHtmlContent();
		htmlContent.setHtml( writer.toString() );
		return htmlContent;
	}

}
