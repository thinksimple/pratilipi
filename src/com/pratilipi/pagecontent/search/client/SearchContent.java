package com.pratilipi.pagecontent.search.client;

import com.claymus.commons.client.ui.InfiniteScrollPanel;
import com.claymus.commons.client.ui.Search;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.pratilipi.commons.client.PratilipiView;
import com.pratilipi.commons.client.PratilipiViewThumbnailImpl;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.GetSearchRequest;
import com.pratilipi.service.shared.GetSearchResponse;
import com.pratilipi.service.shared.data.PratilipiData;


public class SearchContent implements EntryPoint {

	private static final PratilipiServiceAsync pratilipiService =
				GWT.create( PratilipiService.class );
	
	private Search search = new Search();
	private String cursor = null;
	private int resultCount = 1;
	private Label searchEndMsg = new Label();
	
	private final Image loadingImage = new Image( "/theme.pratilipi/images/loading.gif" );
	
	public void onModuleLoad() {
		RootPanel searchBoxRootPanel = RootPanel.get( "PageContent-Search-SearchBox" );
		if( searchBoxRootPanel != null ){
			searchBoxRootPanel.add( search );
			ClickHandler searchButtonClickHandler = new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					setSearchResults();
				}};
				
			KeyDownHandler searchBoxKeyDownHandler = new KeyDownHandler(){

				@Override
				public void onKeyDown(KeyDownEvent event) {
					if( event.getNativeKeyCode() == KeyCodes.KEY_ENTER ){
						setSearchResults();
					}
					
				}};
				
			search.addSearchButtonClickHandler( searchButtonClickHandler );
			search.addSearchBoxKeyDownHandler( searchBoxKeyDownHandler );
			
		}
	}

	private void setSearchResults(){
		if( search.validate() ){
			
			InfiniteScrollPanel infiniteScrollPanel = new InfiniteScrollPanel() {
				
				@Override
				protected void loadItems() {
					
					add( loadingImage );
					
					pratilipiService.getSearchResults(
							new GetSearchRequest( search.getSearchQuery(), cursor, resultCount ),
							new AsyncCallback<GetSearchResponse>() {
						
						@Override
						public void onSuccess( GetSearchResponse response ) {

							loadingImage.removeFromParent();
							
							for( final PratilipiData pratilipiData : response.getPratilipiDataList() ) {
								PratilipiView pratilipiView = new PratilipiViewThumbnailImpl();
								pratilipiView.setPratilipiData( pratilipiData );
								add( pratilipiView );
							}
							
							cursor = response.getCursor();
							if( response.getPratilipiDataList().size() < resultCount || cursor == null ){
								loadFinished();
								searchEndMsg.getElement().setInnerHTML( response.getServerMsg() );
								searchEndMsg.setStyleName( "col-xs-12" );
								add( searchEndMsg );
							}
								
							loadSuccessful();
						}
						
						@Override
						public void onFailure( Throwable caught ) {
							loadingImage.removeFromParent();
							loadFailed();
						}
					});
				}
			};
			
			RootPanel searchResultsRootPanel = RootPanel.get( "PageContent-Search-SearchResult" );
			if( searchResultsRootPanel != null ){
				searchResultsRootPanel.getElement().removeAllChildren();
				searchResultsRootPanel.add( infiniteScrollPanel );
			}
		}
	}
}
