package com.claymus.module.pagecontent;

import org.junit.Assert;
import org.junit.Test;

import com.claymus.data.transfer.PageContent;
import com.claymus.pagecontent.PageContentProcessor;
import com.claymus.pagecontent.PageContentRegistry;
import com.claymus.pagecontent.html.HtmlContent;
import com.claymus.pagecontent.html.HtmlContentFactory;
import com.claymus.pagecontent.html.HtmlContentProcessor;

public class PageContentRegistryTest {

    @Test
    public void testRegister() {
    	
    	PageContentRegistry pageContentRegistry = new PageContentRegistry();
    	pageContentRegistry.register( HtmlContentFactory.class );
    	
    	PageContentProcessor<? extends PageContent> pageContentProcessor =
    			pageContentRegistry.getPageContentProcessor( HtmlContent.class );
    	
    	Assert.assertNotNull( pageContentProcessor );
    	Assert.assertEquals( HtmlContentProcessor.class, pageContentProcessor.getClass() );
    	
    }
    
}
