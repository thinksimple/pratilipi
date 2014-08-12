package com.pratilipi.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;

import com.claymus.data.transfer.PageContent;
import com.claymus.data.transfer.WebsiteWidget;
import com.claymus.module.pagecontent.fileupload.FileUploadContent;
import com.claymus.module.pagecontent.fileupload.FileUploadContentFactory;
import com.claymus.module.pagecontent.html.HtmlContent;
import com.claymus.module.pagecontent.html.HtmlContentFactory;
import com.claymus.module.websitewidget.header.HeaderWidget;
import com.claymus.module.websitewidget.header.HeaderWidgetFactory;
import com.claymus.module.websitewidget.navigation.NavigationWidget;
import com.claymus.module.websitewidget.navigation.NavigationWidgetFactory;
import com.claymus.module.websitewidget.user.UserWidgetFactory;
import com.claymus.servlet.ClaymusMain;
import com.pratilipi.module.pagecontent.bookdatainput.BookDataInputFactory;
import com.pratilipi.module.pagecontent.booklist.BookListFactory;
import com.pratilipi.module.pagecontent.manageauthors.ManageAuthorsFactory;
import com.pratilipi.module.pagecontent.managelanguages.ManageLanguagesFactory;
import com.pratilipi.module.pagecontent.managepublishers.ManagePublishersFactory;

@SuppressWarnings("serial")
public class PratilipiMain extends ClaymusMain {
	
	static {
		PAGE_CONTENT_REGISTRY.register( BookDataInputFactory.class );
		PAGE_CONTENT_REGISTRY.register( BookListFactory.class );
		PAGE_CONTENT_REGISTRY.register( ManageLanguagesFactory.class );
		PAGE_CONTENT_REGISTRY.register( ManageAuthorsFactory.class );
		PAGE_CONTENT_REGISTRY.register( ManagePublishersFactory.class );
	}


	@Override
	protected List<PageContent> getPageContentList(
			HttpServletRequest request ) throws IOException {
	
		List<PageContent> pageContentList
				= super.getPageContentList( request );
		
		String requestUri = request.getRequestURI();
		if( requestUri.equals( "/books" ) )
			pageContentList.add( BookListFactory.newBookList() );
		else if( requestUri.equals( "/manage/languages" ) )
			pageContentList.add( ManageLanguagesFactory.newManageLanguages() );
		else if( requestUri.equals( "/manage/books/new" ) )
			pageContentList.add( BookDataInputFactory.newBookDataInput() );
		else if( requestUri.equals( "/manage/authors/new" ) )
			pageContentList.add( ManageAuthorsFactory.newAuthorDataInput() );
		else if( requestUri.equals( "/manage/publishers/new" ) )
			pageContentList.add( ManagePublishersFactory.newPublisherDataInput() );
		else if( requestUri.startsWith( "/uploads/" ) ) {
			FileUploadContent fileUploadContent = FileUploadContentFactory.newFileUploadContent();
			fileUploadContent.setFileName( requestUri.substring( 9 ) );
			pageContentList.add( fileUploadContent );
		} else {
			File file = new File("WEB-INF/classes/com/pratilipi/servlet/content/PratilipiMain.ftl");
			List<String> lines = FileUtils.readLines( file, "UTF-8" );
			String html = "";
			for( String line : lines )
				html = html + line;
			HtmlContent htmlContent = HtmlContentFactory.newHtmlContent();
			htmlContent.setHtml( html );
			
			pageContentList.add( htmlContent );
		}
		return pageContentList;
	}
	
	@Override
	protected List<WebsiteWidget> getWebsiteWidgetList(
			HttpServletRequest request ) {
		
		List<WebsiteWidget> websiteWidgetList
				= super.getWebsiteWidgetList( request );

		String requestUri = request.getRequestURI();
		if( requestUri.equals( "/" ) )
			return websiteWidgetList;

		HeaderWidget headerWidget = HeaderWidgetFactory.newHeaderWidget();
		headerWidget.setTitle( "Pratilipi" );
		headerWidget.setTagLine( "you become what you read ..." );

		NavigationWidget navigationWidget = NavigationWidgetFactory.newNavigationWidget();
		navigationWidget.setLinks( new String[][] {
				{ "Home", "/" },
				{ "About", "/about" },
				{ "Contact", "/contact" }
		} );
	
		NavigationWidget navigationWidget_2 = NavigationWidgetFactory.newNavigationWidget();
		navigationWidget_2.setLinks( new String[][] {
				{ "Books", "/manage/books/new" },
				{ "Languages", "/manage/languages" },
				{ "Authors", "/manage/authors/new" },
				{ "Publishers", "/manage/publishers/new" }
		} );
	
		websiteWidgetList.add( headerWidget );
		websiteWidgetList.add( UserWidgetFactory.newUserWidget() );
		websiteWidgetList.add( navigationWidget );
		websiteWidgetList.add( navigationWidget_2 );

		return websiteWidgetList;
	}

}
