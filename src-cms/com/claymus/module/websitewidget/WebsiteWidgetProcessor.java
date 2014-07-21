package com.claymus.module.websitewidget;

import com.claymus.data.transfer.WebsiteWidget;

public interface WebsiteWidgetProcessor<T extends WebsiteWidget> {

	String getHtml( T websiteWidget );
	
}
