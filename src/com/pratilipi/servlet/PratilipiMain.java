package com.pratilipi.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.claymus.data.transfer.PageContent;
import com.claymus.data.transfer.WebsiteWidget;
import com.claymus.module.pagecontent.PageContentRegistry;
import com.claymus.module.websitewidget.header.Header;
import com.claymus.module.websitewidget.header.HeaderFactory;
import com.claymus.module.websitewidget.user.UserInfoFactory;
import com.claymus.servlet.ClaymusMain;
import com.pratilipi.module.pagecontent.bookdatainput.BookDataInputFactory;
import com.pratilipi.module.pagecontent.booklist.BookListFactory;
import com.pratilipi.module.pagecontent.managelanguages.ManageLanguagesFactory;

@SuppressWarnings("serial")
public class PratilipiMain extends ClaymusMain {
	
	static {
		PageContentRegistry.register( BookDataInputFactory.class );
		PageContentRegistry.register( BookListFactory.class );
		PageContentRegistry.register( ManageLanguagesFactory.class );
	}
	

	@Override
	protected List<WebsiteWidget> getWebsiteWidgetList(
			HttpServletRequest request ) {
		
		List<WebsiteWidget> websiteWidgetList
				= super.getWebsiteWidgetList( request );

		Header header = HeaderFactory.newUserInfo();
		header.setTitle( "Pratilipi" );
		header.setTagLine( "You become what you read ..." );

		websiteWidgetList.add( header );
		websiteWidgetList.add( UserInfoFactory.newUserInfo() );

		return websiteWidgetList;
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
		else
			pageContentList.add( BookListFactory.newBookList() );
		
		return pageContentList;
	}
	
}
