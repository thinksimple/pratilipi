package com.pratilipi.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.claymus.data.transfer.PageContent;
import com.claymus.data.transfer.WebsiteWidget;
import com.claymus.module.websitewidget.header.Header;
import com.claymus.module.websitewidget.header.HeaderFactory;
import com.claymus.module.websitewidget.navigation.Navigation;
import com.claymus.module.websitewidget.navigation.NavigationFactory;
import com.claymus.module.websitewidget.user.UserInfoFactory;
import com.claymus.servlet.ClaymusMain;
import com.pratilipi.module.pagecontent.bookdatainput.BookDataInputFactory;
import com.pratilipi.module.pagecontent.booklist.BookListFactory;
import com.pratilipi.module.pagecontent.manageauthors.ManageAuthorsFactory;
import com.pratilipi.module.pagecontent.managelanguages.ManageLanguagesFactory;

@SuppressWarnings("serial")
public class PratilipiMain extends ClaymusMain {
	
	static {
		PAGE_CONTENT_REGISTRY.register( BookDataInputFactory.class );
		PAGE_CONTENT_REGISTRY.register( BookListFactory.class );
		PAGE_CONTENT_REGISTRY.register( ManageLanguagesFactory.class );
		PAGE_CONTENT_REGISTRY.register( ManageAuthorsFactory.class );
	}
	

	@Override
	protected List<PageContent> getPageContentList(
			HttpServletRequest request ) {
	
		List<PageContent> pageContentList
				= super.getPageContentList( request );
		
		String requestUri = request.getRequestURI();
		if( requestUri.equals( "/manage/languages" ) )
			pageContentList.add( ManageLanguagesFactory.newManageLanguages() );
		else if( requestUri.equals( "/manage/books/new" ) )
			pageContentList.add( BookDataInputFactory.newBookDataInput() );
		else if( requestUri.equals( "/manage/authors/new" ) )
			pageContentList.add( ManageAuthorsFactory.newAuthorDataInput() );
		else
			pageContentList.add( BookListFactory.newBookList() );
		
		return pageContentList;
	}
	
	@Override
	protected List<WebsiteWidget> getWebsiteWidgetList(
			HttpServletRequest request ) {
		
		List<WebsiteWidget> websiteWidgetList
				= super.getWebsiteWidgetList( request );

		Header header = HeaderFactory.newUserInfo();
		header.setTitle( "Pratilipi" );
		header.setTagLine( "You become what you read ..." );

		Navigation navigation = NavigationFactory.newNavigation();
		navigation.setLinks( new String[][] {
				{ "Home", "/" },
				{ "About", "/about" },
				{ "Contact", "/contact" }
		} );
		
		websiteWidgetList.add( header );
		websiteWidgetList.add( navigation );
		websiteWidgetList.add( UserInfoFactory.newUserInfo() );

		return websiteWidgetList;
	}

}
