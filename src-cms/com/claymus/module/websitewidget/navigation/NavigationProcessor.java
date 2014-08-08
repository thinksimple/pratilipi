package com.claymus.module.websitewidget.navigation;

import com.claymus.module.websitewidget.WebsiteWidgetProcessor;

public class NavigationProcessor implements WebsiteWidgetProcessor<Navigation> {

	@Override
	public String getHtml( Navigation navigation ) {
		
		String html = "";
		
		html = html + "<div>";

		for( String[] link : navigation.getLinks() )
			html = html + "<a href='" + link[1] + "'>" + link[0] + "</a>&nbsp;";
		
		html = html + "</div>";
		
		return html;
	}
	
}
