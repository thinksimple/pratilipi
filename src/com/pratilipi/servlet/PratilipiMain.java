package com.pratilipi.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.transfer.PageContent;
import com.claymus.data.transfer.RoleAccess;
import com.claymus.data.transfer.WebsiteWidget;
import com.claymus.module.pagecontent.fileupload.FileUploadContent;
import com.claymus.module.pagecontent.fileupload.FileUploadContentFactory;
import com.claymus.module.pagecontent.html.HtmlContent;
import com.claymus.module.pagecontent.html.HtmlContentFactory;
import com.claymus.module.websitewidget.footer.FooterWidget;
import com.claymus.module.websitewidget.footer.FooterWidgetFactory;
import com.claymus.module.websitewidget.header.HeaderWidget;
import com.claymus.module.websitewidget.header.HeaderWidgetFactory;
import com.claymus.servlet.ClaymusMain;
import com.pratilipi.module.pagecontent.bookdatainput.BookDataInputFactory;
import com.pratilipi.module.pagecontent.booklist.BookListFactory;
import com.pratilipi.module.pagecontent.homebook.HomeBookContentFactory;
import com.pratilipi.module.pagecontent.homebook.HomeBookContentProcessor;
import com.pratilipi.module.pagecontent.manageauthors.ManageAuthorsFactory;
import com.pratilipi.module.pagecontent.managebooks.ManageBooksFactory;
import com.pratilipi.module.pagecontent.managelanguages.ManageLanguagesFactory;
import com.pratilipi.module.pagecontent.managepublishers.ManagePublishersFactory;

@SuppressWarnings("serial")
public class PratilipiMain extends ClaymusMain {
	
	@SuppressWarnings("unused")
	private static final Logger logger = 
			Logger.getLogger( PratilipiMain.class.getName() );

	static {
		PAGE_CONTENT_REGISTRY.register( BookDataInputFactory.class );
		PAGE_CONTENT_REGISTRY.register( BookListFactory.class );
		
		PAGE_CONTENT_REGISTRY.register( HomeBookContentFactory.class );
		
		PAGE_CONTENT_REGISTRY.register( ManageBooksFactory.class );
		PAGE_CONTENT_REGISTRY.register( ManageLanguagesFactory.class );
		PAGE_CONTENT_REGISTRY.register( ManageAuthorsFactory.class );
		PAGE_CONTENT_REGISTRY.register( ManagePublishersFactory.class );
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		RoleAccess roleAccess = dataAccessor.newRoleAccess();
		roleAccess.setRoleId( "member" );
		roleAccess.setAccessId( HomeBookContentProcessor.ACCESS_ID_BOOK_REVIEW_ADD );
		roleAccess.setAccess( true );
		dataAccessor.createOrUpdateRoleAccess( roleAccess );
		
		dataAccessor.destroy();
	}


	private HttpServlet newUserQueueServlet;

	@Override
	protected void service(
			HttpServletRequest request,
			HttpServletResponse response ) throws ServletException, IOException {
		
		String requestUri = request.getRequestURI();
		
		if( requestUri.equals( "/_ah/queue/new-user" ) ) {
			if( newUserQueueServlet == null ) {
				newUserQueueServlet = new NewUserQueueServlet();
				newUserQueueServlet.init( this.getServletConfig() );
			}
			newUserQueueServlet.service( request, response );
		
		} else {
			super.service( request, response );
		}

	}
	
	@Override
	protected String getTemplateName() {
		return "com/pratilipi/servlet/content/PratilipiTemplate.ftl";
	}

	@Override
	protected List<PageContent> getPageContentList(
			HttpServletRequest request ) throws IOException {
	
		List<PageContent> pageContentList
				= super.getPageContentList( request );
		
		String requestUri = request.getRequestURI();
		if( requestUri.equals( "/" ) )
			pageContentList.add(
					generateHtmlContentFromFile(
							"WEB-INF/classes/com/pratilipi/servlet/content/HomePageContent.ftl" ) );

		else if( requestUri.equals( "/give-away" ) )
			pageContentList.add(
					generateHtmlContentFromFile(
							"WEB-INF/classes/com/pratilipi/servlet/content/GiveAwayPageContent.ftl" ) );

		else if( requestUri.equals( "/invite" ) )
			pageContentList.add(
					generateHtmlContentFromFile(
							"WEB-INF/classes/com/pratilipi/servlet/content/InvitePageContent.ftl" ) );
			
		else if( requestUri.equals( "/about" ) )
			pageContentList.add(
					generateHtmlContentFromFile(
							"WEB-INF/classes/com/pratilipi/servlet/content/AboutPageContent.ftl" ) );
			
		else if( requestUri.equals( "/contact" ) )
			pageContentList.add(
					generateHtmlContentFromFile(
							"WEB-INF/classes/com/pratilipi/servlet/content/ContactPageContent.ftl" ) );
			
		else if( requestUri.equals( "/faq" ) )
			pageContentList.add(
					generateHtmlContentFromFile(
							"WEB-INF/classes/com/pratilipi/servlet/content/FaqPageContent.ftl" ) );
			
		
		else if( requestUri.equals( "/home" ) )
			pageContentList.add( BookListFactory.newBookList() );
		
		
		else if( requestUri.startsWith( "/book/" ) ) {
			String bookIdStr = requestUri.substring( 6 );
			Long bookId = Long.parseLong( bookIdStr );
			pageContentList.add( HomeBookContentFactory.newHomeBookContent( bookId ) );
		}
		
		else if( requestUri.equals( "/manage/books" ) )
			pageContentList.add( ManageBooksFactory.newManageBooks() );
		else if( requestUri.equals( "/manage/authors" ) )
			pageContentList.add( ManageAuthorsFactory.newAuthorDataInput() );
		else if( requestUri.equals( "/manage/publishers" ) )
			pageContentList.add( ManagePublishersFactory.newPublisherDataInput() );
		else if( requestUri.equals( "/manage/languages" ) )
			pageContentList.add( ManageLanguagesFactory.newManageLanguages() );

		
		else if( requestUri.equals( "/manage/books/new" ) )
			pageContentList.add( BookDataInputFactory.newBookDataInput() );
		else if( requestUri.equals( "/manage/books/update" ) )
			pageContentList.add( BookDataInputFactory.newBookDataInput() );
		else if( requestUri.startsWith( "/uploads/" ) ) {
			FileUploadContent fileUploadContent = FileUploadContentFactory.newFileUploadContent();
			fileUploadContent.setFileName( requestUri.substring( 9 ) );
			pageContentList.add( fileUploadContent );
		}
		return pageContentList;
	}
	
	@Override
	protected List<WebsiteWidget> getWebsiteWidgetList(
			HttpServletRequest request ) {
		
		String requestUri = request.getRequestURI();
		List<WebsiteWidget> websiteWidgetList
				= super.getWebsiteWidgetList( request );

		if( ! requestUri.equals( "/" ) ) {
			HeaderWidget headerWidget = HeaderWidgetFactory.newHeaderWidget();
			headerWidget.setBrand( "Pratilipi" );
			headerWidget.setTagLine( "you become what you read ..." );
			if( ClaymusHelper.isUserAdmin() )
				headerWidget.setRightNavItems( new Object[][] {
						{ "Give Away", "/give-away", null },
						{ "Subscribe", "#subscribe", null },
						{ "About", null, new String[][] {
								{ "Pratilipi", "/about/pratilipi" },
								{ "Team", "/about/team" },
								{ "The Founding Readers", "/about/the-founding-readers" }}},
						{ "Manage", null, new String[][] {
								{ "Books", "/manage/books" },
								{ "Authors", "/manage/authors" },
								{ "Publishers", "/manage/publishers" },
								{ "Languages", "/manage/languages" }}},
						{ "Log Out", new ClaymusHelper( request ).createLogoutURL(), null },
				});
			else
				headerWidget.setRightNavItems( new Object[][] {
						{ "Give Away", "/give-away", null },
						{ "Subscribe", "#subscribe", null },
						{ "About", null, new String[][] {
								{ "Pratilipi", "/about/pratilipi" },
								{ "Team", "/about/team" },
								{ "The Founding Readers", "/about/the-founding-readers" }}},
				});
			headerWidget.setPosition( "HEADER" );
			websiteWidgetList.add( headerWidget );
		}
		
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
	
}
