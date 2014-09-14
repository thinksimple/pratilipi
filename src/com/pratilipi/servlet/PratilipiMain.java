package com.pratilipi.servlet;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.data.transfer.PageContent;
import com.claymus.data.transfer.WebsiteWidget;
import com.claymus.module.pagecontent.html.HtmlContent;
import com.claymus.module.pagecontent.html.HtmlContentFactory;
import com.claymus.module.websitewidget.footer.FooterWidget;
import com.claymus.module.websitewidget.footer.FooterWidgetFactory;
import com.claymus.module.websitewidget.html.HtmlWidget;
import com.claymus.module.websitewidget.html.HtmlWidgetFactory;
import com.claymus.servlet.ClaymusMain;
import com.pratilipi.commons.shared.PratilipiHelper;
import com.pratilipi.commons.shared.PratilipiType;
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
	
	@SuppressWarnings("unused")
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
			pageContentList.add( generateHtmlContentFromFile( "WEB-INF/classes/com/pratilipi/servlet/content/HomePageContent.ftl" ) );

		else if( requestUri.equals( "/books" ) )
			pageContentList.add( PratilipisContentFactory.newPratilipisContent( PratilipiType.BOOK ) );
		
		else if( requestUri.equals( "/poems" ) )
			pageContentList.add( PratilipisContentFactory.newPratilipisContent( PratilipiType.POEM ) );
		
		else if( requestUri.equals( "/stories" ) )
			pageContentList.add( PratilipisContentFactory.newPratilipisContent( PratilipiType.STORY ) );
		
		else if( requestUri.equals( "/articles" ) )
			pageContentList.add( PratilipisContentFactory.newPratilipisContent( PratilipiType.ARTICLE ) );
		
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
		
		String requestUri = request.getRequestURI();
		List<WebsiteWidget> websiteWidgetList
				= super.getWebsiteWidgetList( request );

		ClaymusHelper claymusHelper = new ClaymusHelper( request );
		
		
		/*
		if( ! requestUri.equals( "/home" ) ) {
			HeaderWidget headerWidget = HeaderWidgetFactory.newHeaderWidget();
			headerWidget.setBrand( "Pratilipi" );
			headerWidget.setTagLine( "you become what you read ..." );

			headerWidget.setRightNavItems( new Object[][] {
					{ "Classics", null, new String[][] {
							{ "Classic Books", "/classics/books" },
							{ "Classic Poems", "/classics/poems" },
							{ "Classic Stories", "/classics/stories" },
							{ "Classic Articles", "/classics/articles" }}},
					{ "Books", "/books", null },
					{ "Poems", "/poems", null },
					{ "Stories", "/stories", null },
					{ "Articles", "/articles", null },
					{ "Languages", "/languages", null },
					{ "Authors", "/authors" },
					{ "Genres", "/genres" },
					{ "About", null, new String[][] {
							{ "Pratilipi", "/about/pratilipi" },
							{ "Team", "/about/team" },
							{ "The Founding Readers", "/about/the-founding-readers" }}},
					{ claymusHelper.isUserLoggedIn() ? "Sign Out" : "Sign In", claymusHelper.isUserLoggedIn() ? "#signout" : "#signin", null },
					{ "Sign up", "#signup", null },
			});
			headerWidget.setPosition( "HEADER" );
		}
*/
		
		HtmlWidget headerWidget = generateHeaderWidget( request );
		headerWidget.setPosition( "HEADER" );
		websiteWidgetList.add( headerWidget );
		
		FooterWidget footerWidget = FooterWidgetFactory.newFooterWidget();
		footerWidget.setLinks( new String[][] {
				{ "About", "/about" },
				{ "Contact", "/contact" },
				{ "FAQs", "/faq" }
		} );
		footerWidget.setCopyrightNote( "&copy; 2014 pratilipi.com" );
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

}
