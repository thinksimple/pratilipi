package com.pratilipi.pagecontent.search.client;

import com.claymus.commons.client.ui.InfiniteScrollPanel;
import com.claymus.commons.client.ui.Search;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.pratilipi.commons.client.PratilipiView;
import com.pratilipi.commons.client.PratilipiViewThumbnailImpl;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.GetSearchRequest;
import com.pratilipi.service.shared.GetSearchResponse;
import com.pratilipi.service.shared.data.PratilipiData;

public class SearchContent implements EntryPoint, ClickHandler {

	private static final PratilipiServiceAsync pratilipiService =
				GWT.create( PratilipiService.class );

	private Search search = new Search();
	private InfiniteScrollPanel infiniteScrollPanel;
	
	private final Image loadingImage = new Image( "/theme.pratilipi/images/loading.gif" );
	
	public void onModuleLoad() {
		RootPanel searchBoxRootPanel = RootPanel.get( "PageContent-Search-SearchBox" );
		if( searchBoxRootPanel != null ){
			this.search.addSearchButtonClickHandler( this );
			searchBoxRootPanel.add( this.search );
			
		}
		
		RootPanel searchResultsRootPanel = RootPanel.get( "PageContent-Search-SearchResult" );
		if( searchResultsRootPanel != null )
			searchResultsRootPanel.add( infiniteScrollPanel );
		
	}

	@Override
	public void onClick(ClickEvent event) {
		String url = Window.Location.getPath();
		if( url.startsWith( "/search" ) ){
			
		} else {
			//TODO : click handler when parent page is not search page.
		}
		
		setSearchResults();
		
	}
	
	private void setSearchResults(){
		this.infiniteScrollPanel = new InfiniteScrollPanel(){

			@Override
			protected void loadItems() {
				
				add( loadingImage );
				
				pratilipiService.getSearchResults(new GetSearchRequest( search.getSearchQuery() ), 
								new AsyncCallback<GetSearchResponse>(){

									@Override
									public void onFailure(Throwable caught) {
										loadingImage.removeFromParent();
										loadFailed();
										
									}

									@Override
									public void onSuccess( GetSearchResponse response ) {
										if( !response.isLoadFinished() ){
											for( final PratilipiData pratilipiData : response.getPratilipiDataList() ) {
												PratilipiView pratilipiView = new PratilipiViewThumbnailImpl();
												pratilipiView.setPratilipiData( pratilipiData );
												add( pratilipiView );
											}
										} else {
											loadFinished();
										}
										loadSuccessful();
									}});
				
			}};
	}

}
