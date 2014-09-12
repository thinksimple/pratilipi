package com.pratilipi.servlet.content.client;

import com.claymus.commons.client.ui.InfiniteScrollPanel;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.pratilipi.commons.client.PratilipiView;
import com.pratilipi.commons.client.PratilipiViewThumbnailImpl;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.GetPratilipiListRequest;
import com.pratilipi.service.shared.GetPratilipiListResponse;
import com.pratilipi.service.shared.data.PratilipiData;

public class HomePageContent implements EntryPoint {

	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );
	

	private String cursor;
	private int resultCount = 25;

	
	public void onModuleLoad() {

		InfiniteScrollPanel infiniteScrollPanel = new InfiniteScrollPanel() {

			@Override
			protected void loadItems() {
				
				pratilipiService.getPratilipiList(
						new GetPratilipiListRequest( PratilipiType.BOOK, cursor, resultCount ),
						new AsyncCallback<GetPratilipiListResponse>() {
					
					@Override
					public void onSuccess( GetPratilipiListResponse response ) {

						for( final PratilipiData pratilipiData : response.getPratilipiDataList() ) {
							final PratilipiView pratilipiView = new PratilipiViewThumbnailImpl();
							pratilipiView.setPratilipiData( pratilipiData );
							add( pratilipiView );
						}
						
						cursor = response.getCursor();
						loadSuccessful();
						if( response.getPratilipiDataList().size() < resultCount )
							loadFinished();
					}
					
					@Override
					public void onFailure( Throwable caught ) {
						loadFailed();
						Window.alert( caught.getMessage() );
					}
					
				} );
				
			}
			
		};
		
		RootPanel.get( "PageContent-PratilipiHome" ).add( infiniteScrollPanel );
	
	}
	
}
