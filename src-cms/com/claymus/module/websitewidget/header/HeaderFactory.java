package com.claymus.module.websitewidget.header;

import com.claymus.module.websitewidget.WebsiteWidgetFactory;
import com.claymus.module.websitewidget.header.gae.HeaderEntity;

public class HeaderFactory
		implements WebsiteWidgetFactory<Header, HeaderProcessor> {
	
	public static Header newUserInfo() {
		return new HeaderEntity();
	}

}
