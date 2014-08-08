package com.claymus.module.websitewidget.header;

import com.claymus.module.websitewidget.WebsiteWidgetProcessor;

public class HeaderProcessor extends WebsiteWidgetProcessor<Header> {

	@Override
	public String getTemplateName() {
		return "com/claymus/module/websitewidget/header/Header.ftl";
	}

}
