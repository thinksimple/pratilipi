package com.pratilipi.pagecontent.search.client;

import com.claymus.commons.client.ui.InfiniteScrollPanel;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.ui.RootPanel;
import com.pratilipi.commons.client.PratilipiView;
import com.pratilipi.commons.client.PratilipiViewThumbnailImpl;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.SearchRequest;
import com.pratilipi.service.shared.SearchResponse;
import com.pratilipi.service.shared.data.PratilipiData;

public class SearchContent implements EntryPoint {

	private static final PratilipiServiceAsync pratilipiService =
				GWT.create( PratilipiService.class );
	
	
	private final RootPanel searchResultPanel =
			RootPanel.get( "PageContent-Search-Result" );
	
	private final RootPanel searchLoaderPanel =
			RootPanel.get( "PageContent-Search-Loader" );

	private final String query = Window.Location.getParameter( "q" );
	private final String docType = Window.Location.getParameter( "dt" );
	private String cursor = searchResultPanel.getElement().getAttribute( "data-cursor" );
	private final int resultCount = 4;
	

	public void onModuleLoad() {
		if( cursor == null || cursor.isEmpty() )
			return;
		
		InfiniteScrollPanel infiniteScrollPanel = new InfiniteScrollPanel() {
			
			@Override
			protected void loadItems() {
				
				pratilipiService.search(
						new SearchRequest( query, docType, cursor, resultCount ),
						new AsyncCallback<SearchResponse>() {
					
					@Override
					public void onSuccess( SearchResponse response ) {

						for( final IsSerializable data : response.getDataList() ) {
							if( data.getClass() == PratilipiData.class ) {
								PratilipiView pratilipiView = new PratilipiViewThumbnailImpl();
								pratilipiView.setPratilipiData( (PratilipiData) data );
								searchResultPanel.add( pratilipiView );
							}
						}
						
						cursor = response.getCursor();
						if( cursor == null ) {
							loadFinished();
							searchLoaderPanel.setVisible( false );
						}
						loadSuccessful();
					}
					
					@Override
					public void onFailure( Throwable caught ) {
						loadFailed();
					}
					
				});
			}
		};
		
		searchLoaderPanel.add( infiniteScrollPanel );
	}
	
}
