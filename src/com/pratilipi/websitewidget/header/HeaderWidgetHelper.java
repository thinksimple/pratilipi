package com.pratilipi.websitewidget.header;

import com.claymus.websitewidget.WebsiteWidgetHelper;
import com.pratilipi.websitewidget.header.gae.HeaderWidgetEntity;

public class HeaderWidgetHelper extends WebsiteWidgetHelper<
		HeaderWidget,
		HeaderWidgetProcessor> {
	
	@Override
	public String getModuleName() {
		return "Header (Pratilipi)";
	}

	@Override
	public Double getModuleVersion() {
		return 6.0;
	}

	
	public static HeaderWidget newHeaderWidget() {
		return new HeaderWidgetEntity();
	}

}
