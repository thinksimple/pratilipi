package com.claymus.module.pagecontent.html.gae;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.access.gae.PageContentEntity;
import com.claymus.data.transfer.PageContent;
import com.claymus.module.pagecontent.html.HtmlContent;
import com.claymus.module.pagecontent.html.ModuleFactory;

public abstract class HtmlContentModuleTest {

    @Test
    public void testHtmlContent() {
    	
    	Long id = null;
    	Long pageId = 123L;
    	String position = "position";
    	String html = "html";
    	
    	HtmlContent htmlContent = ModuleFactory.newHtmlContent();
    	htmlContent.setPageId( pageId );
    	htmlContent.setPosition( position );
    	htmlContent.setHtml( html );
    	
    	DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
    	htmlContent = (HtmlContent) dataAccessor.createOrUpdatePageContent( (PageContentEntity) htmlContent );
    	id = htmlContent.getId();
    	dataAccessor.destroy();
    	
    	dataAccessor = DataAccessorFactory.getDataAccessor();
    	List<PageContent> pageContentList = dataAccessor.getPageContentList( pageId );
    	
    	Assert.assertEquals( 1, pageContentList.size() );
    	
    	htmlContent = (HtmlContent) pageContentList.get( 0 );
    	
    	Assert.assertEquals( id, htmlContent.getId() );
    	Assert.assertEquals( pageId, htmlContent.getPageId() );
    	Assert.assertEquals( position, htmlContent.getPosition() );
    	Assert.assertEquals( html, htmlContent.getHtml() );
    	
    	dataAccessor.destroy();
    	
    }
	
}
