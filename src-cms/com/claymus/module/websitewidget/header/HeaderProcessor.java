package com.claymus.module.websitewidget.header;

import com.claymus.module.websitewidget.WebsiteWidgetProcessor;

public class HeaderProcessor implements WebsiteWidgetProcessor<Header> {

	@Override
	public String getHtml( Header header ) {
		return "<div>" +
					"<h1>" + header.getTitle() + "</h1>" +
					"<h3>" + header.getTagLine() + "</h3>" +
				"</div>";
	}
	
}
