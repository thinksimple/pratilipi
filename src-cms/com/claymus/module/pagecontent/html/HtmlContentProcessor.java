package com.claymus.module.pagecontent.html;

import com.claymus.module.pagecontent.PageContentProcessor;

public class HtmlContentProcessor extends PageContentProcessor<HtmlContent> {

	@Override
	public String getHtml( HtmlContent htmlContent ) {
		return htmlContent.getHtml();
	}
	
}
