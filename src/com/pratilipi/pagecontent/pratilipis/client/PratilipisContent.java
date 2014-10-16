package com.pratilipi.pagecontent.pratilipis.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.pratilipi.commons.shared.PratilipiFilter;

public class PratilipisContent implements EntryPoint {

	private PratilipiFilter pratilipiFilter;
	
	
	public void onModuleLoad() {
		
		RootPanel rootPanel =
				RootPanel.get( "PageContent-Pratilipi-List-Preloaded" );
		
		if( rootPanel != null ) {
			String filterStr =
					rootPanel.getElement().getAttribute( "pratilipi-filters" );
			pratilipiFilter = filterStr == null ?
					null : PratilipiFilter.fromString( filterStr );
		}
		
		rootPanel = RootPanel.get( "PageContent-Pratilipi-List" );
		if( rootPanel != null )
			rootPanel.add(
					new PratilipiList(
							RootPanel.get( "PageContent-Pratilipi-List-Preloaded" ),
							pratilipiFilter ) );
	
	}
	
}
