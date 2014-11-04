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
import com.google.gwt.user.client.Window;
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
	
	private final RootPanel preloadedListPanel =
			RootPanel.get( "PageContent-Search-Preloaded" );
	
	private boolean hidden = false;
	private Search search = new Search();
	private String cursor = null;
	private int resultCount = 20;
	private Label searchEndMsg = new Label();
	private String modifiedSearchQuery;
	
	private final Image loadingImage = new Image( "/theme.pratilipi/images/loading.gif" );
	
	public void onModuleLoad() {
		RootPanel searchBoxRootPanel = RootPanel.get( "PageContent-Search-SearchBox" );
		if( searchBoxRootPanel != null ){
			searchBoxRootPanel.add( search );
			
			String sectionParameter = Window.Location.getParameter( "section" );
			if( sectionParameter != null )
				search.setPrefix( sectionParameter );
			
			String queryParameter = Window.Location.getParameter( "query" );
			if( queryParameter != null ) {
				search.setSearchQuery( queryParameter );
				setSearchResults();
			}
			
			ClickHandler searchButtonClickHandler = new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					updateUrl();
				}};
				
			KeyDownHandler searchBoxKeyDownHandler = new KeyDownHandler(){

				@Override
				public void onKeyDown(KeyDownEvent event) {
					if( event.getNativeKeyCode() == KeyCodes.KEY_ENTER ){
						updateUrl();
					}
					
				}};
				
			search.addSearchButtonClickHandler( searchButtonClickHandler );
			search.addSearchBoxKeyDownHandler( searchBoxKeyDownHandler );
			
		}
	}

	private void setSearchResults(){
			
		String[] query = search.getSearchQuery().toLowerCase().split( " " );
		
		modifiedSearchQuery = "";
		
		for( int i=0; i< query.length; i++ ) {
			if( ( query[i].toUpperCase().equals( "AND" ) ||
							query[i].toUpperCase().equals( "NOT" ) ) && i != ( query.length - 1 ) ) {
				modifiedSearchQuery += ( " " + query[i].toUpperCase() + " " + query[++i] );
				continue;
			}else if( query[i].toUpperCase().equals( "OR" ))
				continue;
			else {
				if( i == 0 )
					modifiedSearchQuery += query[i];
				else
					modifiedSearchQuery += ( " OR " + query[i] );
			}
		}
		
		if( !search.getPrefix().equals( "All" ) )
			modifiedSearchQuery = search.getPrefix() + " AND " + modifiedSearchQuery;
		
		InfiniteScrollPanel infiniteScrollPanel = new InfiniteScrollPanel() {
			
			@Override
			protected void loadItems() {
				
				add( loadingImage );
				
				pratilipiService.getSearchResults(
						new GetSearchRequest( modifiedSearchQuery, cursor, resultCount ),
						new AsyncCallback<GetSearchResponse>() {
					
					@Override
					public void onSuccess( GetSearchResponse response ) {

						loadingImage.removeFromParent();
						
						if( ! hidden ) {
							preloadedListPanel.setVisible( false );
							hidden = true;
						}
						
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
	
	private void updateUrl() {

		if( search.validate() ) {
			//Setting new query parameter in url.
			String url = Window.Location.getHref();
			int queryParameterIndex = url.indexOf( "?" );
			String newUrl;
			if( queryParameterIndex != -1 )
				newUrl = url.substring(0, url.indexOf( "?" ) ) + "?section=" + search.getPrefix()
												+ "&query=" + search.getSearchQuery();
			else
				newUrl = url + "?section=" + search.getPrefix()	+ "&query=" + search.getSearchQuery();
			
			Window.Location.assign( newUrl );
		}
		
	}
}
