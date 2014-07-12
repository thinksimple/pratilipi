package com.claymus.module.pagecontent.html;

import com.claymus.module.pagecontent.PageContentProcessor;

public class HtmlContentProcessor implements PageContentProcessor<HtmlContent> {

	@Override
	public String getHtml( HtmlContent htmlContent ) {
		return htmlContent.getHtml();
	}
	
}
