package com.claymus.module.pagecontent.html;

import com.claymus.module.pagecontent.PageContentFactory;
import com.claymus.module.pagecontent.html.gae.HtmlContentEntity;

public class HtmlContentFactory
		implements PageContentFactory<HtmlContent, HtmlContentProcessor> {
	
	public static HtmlContent newHtmlContent() {
		return new HtmlContentEntity();
	}

}
