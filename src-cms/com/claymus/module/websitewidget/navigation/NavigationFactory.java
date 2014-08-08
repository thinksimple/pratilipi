package com.claymus.module.websitewidget.navigation;

import com.claymus.module.websitewidget.WebsiteWidgetFactory;
import com.claymus.module.websitewidget.navigation.gae.NavigationEntity;

public class NavigationFactory
		implements WebsiteWidgetFactory<Navigation, NavigationProcessor> {
	
	public static Navigation newNavigation() {
		return new NavigationEntity();
	}

}
