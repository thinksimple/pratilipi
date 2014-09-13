package com.pratilipi.pagecontent.pratilipis.client;

import com.claymus.commons.client.ui.InfiniteScrollPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pratilipi.commons.client.PratilipiDataInputView;
import com.pratilipi.commons.client.PratilipiView;
import com.pratilipi.commons.client.PratilipiViewDetailImpl;
import com.pratilipi.commons.client.PratilipiViewThumbnailBookImpl;
import com.pratilipi.commons.client.PratilipiViewThumbnailImpl;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.GetPratilipiListRequest;
import com.pratilipi.service.shared.GetPratilipiListResponse;
import com.pratilipi.service.shared.data.PratilipiData;

public class PratilipiList extends InfiniteScrollPanel {

	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );

	private final PratilipiType pratilipiType;
	private final Boolean publicDomain;
	private final PratilipiDataInputView pratilipiDataInputView;
	private String cursor = null;
	private int resultCount = 20;

	
	public PratilipiList( PratilipiType pratilipiType ) {
		this( pratilipiType, null, null );
	}
	
	public PratilipiList( PratilipiType pratilipiType, Boolean publicDomain ) {
		this( pratilipiType, null, null );
	}
	
	public PratilipiList( PratilipiType pratilipiType, Boolean publicDomain,
			PratilipiDataInputView pratilipiDataInputView ) {
		
		this.pratilipiType = pratilipiType;
		this.publicDomain = publicDomain;
		this.pratilipiDataInputView = pratilipiDataInputView;
		
		setStyleName( "row" );
	}

	
	@Override
	protected void loadItems() {
		// TODO: show loading image/text while while waiting for RPC response
		
		pratilipiService.getPratilipiList(
				new GetPratilipiListRequest( pratilipiType, publicDomain, cursor, resultCount ),
				new AsyncCallback<GetPratilipiListResponse>() {
			
			@Override
			public void onSuccess( GetPratilipiListResponse response ) {

				for( final PratilipiData pratilipiData : response.getPratilipiDataList() ) {
					final PratilipiView pratilipiView;
					
					if( pratilipiDataInputView == null && pratilipiType == PratilipiType.BOOK ) {
						pratilipiView = new PratilipiViewThumbnailBookImpl();
						pratilipiView.setPratilipiData( pratilipiData );

					} else if( pratilipiDataInputView == null && pratilipiType != PratilipiType.BOOK ) {
						pratilipiView = new PratilipiViewThumbnailImpl();
						pratilipiView.setPratilipiData( pratilipiData );

					} else {
						pratilipiView = new PratilipiViewDetailImpl();
						pratilipiView.setPratilipiData( pratilipiData );
						pratilipiView.addEditHyperlinkClickHandler( new ClickHandler() {
							
							@Override
							public void onClick( ClickEvent event ) {
								PratilipiData pratilipiData = pratilipiView.getPratilipiData();
								pratilipiDataInputView.setPratilipiData( pratilipiData );
								pratilipiDataInputView.setPratilipiView( pratilipiView );
							}
							
						});

					}
					
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
				// TODO: show manual load button
			}
			
		});
	}

}
