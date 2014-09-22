package com.pratilipi.pagecontent.pratilipis.client;

import com.claymus.commons.client.ui.InfiniteScrollPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.pratilipi.commons.client.PratilipiDataInputView;
import com.pratilipi.commons.client.PratilipiView;
import com.pratilipi.commons.client.PratilipiViewDetailImpl;
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
	private final Long languageId;
	private final PratilipiDataInputView pratilipiDataInputView;
	private boolean cleared = false;
	private String cursor = null;
	private int resultCount = 20;

	private Image loadingImage = new Image( "/theme.pratilipi/images/loading.gif" );
	
	
	public PratilipiList( Panel panel, PratilipiType pratilipiType ) {
		this( panel, pratilipiType, null, null, null );
	}
	
	public PratilipiList( Panel panel, PratilipiType pratilipiType, Boolean publicDomain ) {
		this( panel, pratilipiType, publicDomain, null, null );
	}
	
	public PratilipiList( Panel panel, PratilipiType pratilipiType, Boolean publicDomain,
			Long languageId, PratilipiDataInputView pratilipiDataInputView ) {
		
		super( panel );
		
		this.pratilipiType = pratilipiType;
		this.publicDomain = publicDomain;
		this.languageId = languageId;
		this.pratilipiDataInputView = pratilipiDataInputView;
		
		setStyleName( "row" );
	}

	
	@Override
	protected void loadItems() {
		// TODO: show loading image/text while while waiting for RPC response
		
		add( loadingImage );
		
		pratilipiService.getPratilipiList(
				new GetPratilipiListRequest( pratilipiType, publicDomain, languageId, cursor, resultCount ),
				new AsyncCallback<GetPratilipiListResponse>() {
			
			@Override
			public void onSuccess( GetPratilipiListResponse response ) {

				loadingImage.removeFromParent();
				
				if( ! cleared ) {
					clear();
					cleared = true;
				}
					
				for( final PratilipiData pratilipiData : response.getPratilipiDataList() ) {
					final PratilipiView pratilipiView;
					
					if( pratilipiDataInputView == null ) {
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
				if( response.getPratilipiDataList().size() < resultCount )
					loadFinished();
				loadSuccessful();
			}
			
			@Override
			public void onFailure( Throwable caught ) {
				loadingImage.removeFromParent();
				loadFailed();
				// TODO: show manual load button
			}
			
		});
	}

}
