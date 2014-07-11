package com.claymus.module.pagecontent.html;

import com.claymus.module.pagecontent.html.gae.HtmlContentEntity;

public class ModuleFactory {
	
	public static HtmlContent newHtmlContent() {
		return new HtmlContentEntity();
	}

}
