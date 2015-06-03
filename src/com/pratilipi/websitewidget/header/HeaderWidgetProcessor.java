package com.pratilipi.websitewidget.header;

import com.claymus.commons.shared.ClaymusResource;
import com.claymus.commons.shared.Resource;
import com.claymus.servlet.UxModeFilter;
import com.claymus.websitewidget.WebsiteWidgetProcessor;

public class HeaderWidgetProcessor extends WebsiteWidgetProcessor<HeaderWidget> {
	
	public Resource[] getDependencies( HeaderWidget pageContent ) {
		if( UxModeFilter.isModeBasic() ) {
			return new Resource[] { };
		
		} else {
			return new Resource[] {
					ClaymusResource.JQUERY_2,
					ClaymusResource.BOOTSTRAP,
					ClaymusResource.POLYMER_1,
					new Resource() {
						@Override
						public String getTag() {
							return "<link rel='import' href='/polymer." + UxModeFilter.getUserLanguage() + "/websitewidget-header.html'>"
								 + "<link rel='import' href='/polymer." + UxModeFilter.getUserLanguage() + "/pagecontent-search-bar.html'>";
						}
					},
			};
		}
	}

	protected CacheLevel getCacheLevel() {
		return CacheLevel.USER;
	}

	protected boolean hasBasicMode() {
		return false;
	}

}
