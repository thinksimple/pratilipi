package com.pratilipi.pagecontent.pratilipis.client;

import com.claymus.commons.client.ui.InfiniteScrollPanel;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamFactory;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.pratilipi.commons.client.PratilipiView;
import com.pratilipi.commons.client.PratilipiViewThumbnailImpl;
import com.pratilipi.commons.shared.PratilipiFilter;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.GetPratilipiListRequest;
import com.pratilipi.service.shared.GetPratilipiListResponse;
import com.pratilipi.service.shared.data.PratilipiData;

public class PratilipisContent implements EntryPoint {

	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );


	private final RootPanel listPanel =
			RootPanel.get( "PageContent-Pratilipis" );
	private final RootPanel preloadedListPanel =
			RootPanel.get( "PageContent-Pratilipis-Preloaded" );

	private final Image loadingImage = new Image( "/theme.pratilipi/images/loading.gif" );

	private boolean hidden = false;
	private String cursor = null;
	private int resultCount = 50;

	private PratilipiFilter pratilipiFilter;
	
	
	public void onModuleLoad() {
		
		RootPanel encodedDataPanel =
				RootPanel.get( "PageContent-Pratilipis-EncodedData" );
		String filterEncodedStr = encodedDataPanel.getElement().getInnerText();
		try {
			SerializationStreamReader streamReader =
					( (SerializationStreamFactory) pratilipiService )
							.createStreamReader( filterEncodedStr );
			pratilipiFilter = (PratilipiFilter) streamReader.readObject();
		} catch( SerializationException e ) {
			Window.alert( e.getMessage() );
		}
		
		InfiniteScrollPanel infiniteScrollPanel = new InfiniteScrollPanel() {
			
			@Override
			protected void loadItems() {
				
				add( loadingImage );
				
				pratilipiService.getPratilipiList(
						new GetPratilipiListRequest( pratilipiFilter, cursor, resultCount ),
						new AsyncCallback<GetPratilipiListResponse>() {
					
					@Override
					public void onSuccess( GetPratilipiListResponse response ) {

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
						if( response.getPratilipiDataList().size() < resultCount )
							loadFinished();
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
		
		infiniteScrollPanel.setStyleName( "row" );
		
		listPanel.add( infiniteScrollPanel );
	}
	
}
