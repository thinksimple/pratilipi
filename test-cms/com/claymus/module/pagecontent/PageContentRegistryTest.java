package com.claymus.module.pagecontent;

import org.junit.Assert;
import org.junit.Test;

import com.claymus.data.transfer.PageContent;
import com.claymus.module.pagecontent.html.HtmlContent;
import com.claymus.module.pagecontent.html.HtmlContentFactory;
import com.claymus.module.pagecontent.html.HtmlContentProcessor;

public class PageContentRegistryTest {

    @Test
    public void testRegister() {
    	
    	PageContentRegistry.register( HtmlContentFactory.class );
    	
    	PageContentProcessor<? extends PageContent> pageContentProcessor =
    			PageContentRegistry.getPageContentProcessor( HtmlContent.class );
    	
    	Assert.assertNotNull( pageContentProcessor );
    	Assert.assertEquals( HtmlContentProcessor.class, pageContentProcessor.getClass() );
    	
    }
    
}
