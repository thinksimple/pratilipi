package com.claymus.module.pagecontent.html;

import com.claymus.commons.server.Access;
import com.claymus.module.pagecontent.PageContentFactory;
import com.claymus.module.pagecontent.html.gae.HtmlContentEntity;

public class HtmlContentFactory
		implements PageContentFactory<HtmlContent, HtmlContentProcessor> {
	
	@Override
	public String getModuleName() {
		return "Html Content";
	}

	@Override
	public Access[] getAccessList() {
		return new Access[] {};
	}
	
	
	public static HtmlContent newHtmlContent() {
		return new HtmlContentEntity();
	}

}
