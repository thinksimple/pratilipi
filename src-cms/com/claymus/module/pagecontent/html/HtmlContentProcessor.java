package com.claymus.module.pagecontent.html;

import com.claymus.module.pagecontent.PageContentProcessor;

public class HtmlContentProcessor extends PageContentProcessor<HtmlContent> {

	@Override
	protected String getTemplateName() {
		return "com/claymus/module/pagecontent/html/HtmlContent.ftl";
	}
	
}
