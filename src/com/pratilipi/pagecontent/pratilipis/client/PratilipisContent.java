package com.pratilipi.pagecontent.pratilipis.client;

import com.claymus.commons.client.ui.Accordion;
import com.claymus.commons.client.ui.InfiniteScrollPanel;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.pratilipi.commons.client.PratilipiDataInputView;
import com.pratilipi.commons.client.PratilipiDataInputViewImpl;
import com.pratilipi.commons.client.PratilipiView;
import com.pratilipi.commons.client.PratilipiViewDetailImpl;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.commons.shared.PratilipiUtil;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.GetAuthorListRequest;
import com.pratilipi.service.shared.GetAuthorListResponse;
import com.pratilipi.service.shared.GetLanguageListRequest;
import com.pratilipi.service.shared.GetLanguageListResponse;
import com.pratilipi.service.shared.GetPratilipiListRequest;
import com.pratilipi.service.shared.GetPratilipiListResponse;
import com.pratilipi.service.shared.SavePratilipiRequest;
import com.pratilipi.service.shared.SavePratilipiResponse;
import com.pratilipi.service.shared.data.AuthorData;
import com.pratilipi.service.shared.data.LanguageData;
import com.pratilipi.service.shared.data.PratilipiData;

public class PratilipisContent implements EntryPoint, ClickHandler {

	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );
	

	private final FocusPanel focusPanel = new FocusPanel();
	private final Accordion accordion = new Accordion();
	private PratilipiDataInputView pratilipiDataInputView;
	
	private PratilipiType pratilipiType;
	
	private String cursor;
	private int resultCount = 25;
	
	
	public void onModuleLoad() {
		for( PratilipiType pratilipiType : PratilipiType.values() )
			if( RootPanel.get( "PageContent-" + pratilipiType.getName() + "-List" ) != null )
				this.pratilipiType = pratilipiType;
		
		if( pratilipiType == null )
			return;
		
		
		RootPanel rootPanel = RootPanel.get( "PageContent-" + pratilipiType.getName() + "-DataInput" );
		if( rootPanel != null ) {
			accordion.setTitle( "Add " + pratilipiType.getName() );
			pratilipiDataInputView = new PratilipiDataInputViewImpl( pratilipiType );
			pratilipiDataInputView.addAddButtonClickHandler( this );

			focusPanel.add( accordion );
			accordion.add( pratilipiDataInputView );

			rootPanel.add( focusPanel );
			
			// Load list of authors.
			pratilipiService.getAuthorList( new GetAuthorListRequest( null , 100 ), new AsyncCallback<GetAuthorListResponse>() {

				@Override
				public void onFailure( Throwable caught ) {
					Window.alert(caught.getMessage());
				}

				@Override
				public void onSuccess( GetAuthorListResponse response ) {
					for( AuthorData authorData : response.getAuthorList() )
						pratilipiDataInputView.addAuthorListItem(
								PratilipiUtil.createAuthorName( authorData ),
								authorData.getId().toString() );
				}
				
			});
			
			// Load list of languages
		    pratilipiService.getLanguageList( new GetLanguageListRequest(), new AsyncCallback<GetLanguageListResponse>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(caught.getMessage());
					
				}

				@Override
				public void onSuccess( GetLanguageListResponse response ) {
					for( LanguageData languageData : response.getLanguageList() )
						pratilipiDataInputView.addLanguageListItem(
								PratilipiUtil.createLanguageName( languageData ),
								languageData.getId().toString() );
				}
				
		    });
		
		}

		
		rootPanel = RootPanel.get( "PageContent-" + pratilipiType.getName() + "-List" );
		if( rootPanel != null ) {
			InfiniteScrollPanel infiniteScrollPanel = new InfiniteScrollPanel() {

				@Override
				protected void loadItems() {
					
					pratilipiService.getPratilipiList(
							new GetPratilipiListRequest( pratilipiType, cursor, resultCount ),
							new AsyncCallback<GetPratilipiListResponse>() {
						
						@Override
						public void onSuccess( GetPratilipiListResponse response ) {
	
							for( final PratilipiData pratilipiData : response.getPratilipiDataList() ) {
								final PratilipiView pratilipiView = new PratilipiViewDetailImpl();
								pratilipiView.setPratilipiData( pratilipiData );
								pratilipiView.addEditHyperlinkClickHandler( new ClickHandler() {
									
									@Override
									public void onClick( ClickEvent event ) {
										focusPanel.setFocus( true );
										accordion.show();
										PratilipiData pratilipiData = pratilipiView.getPratilipiData();
										pratilipiDataInputView.setPratilipiData( pratilipiData );
										pratilipiDataInputView.setPratilipiView( pratilipiView );
									}
									
								});
								add( pratilipiView );
							}
							
							cursor = response.getCursor();
							loadSuccessful();
							if( response.getPratilipiDataList().size() < resultCount )
								finishedLoading();
						}
						
						@Override
						public void onFailure( Throwable caught ) {
							loadFailed();
							Window.alert( caught.getMessage() );
						}
						
					} );
					
				}
				
			};
			
			rootPanel.add( infiniteScrollPanel );
		}
	
	}
	
	
	@Override
	public void onClick( ClickEvent event ) {
		if( ! pratilipiDataInputView.validateInputs() )
			return;
		
		final PratilipiData pratilipiData = pratilipiDataInputView.getPratilipiData();
		pratilipiDataInputView.setEnabled( false );
		pratilipiService.savePratilipi(
				new SavePratilipiRequest( pratilipiData ),
				new AsyncCallback<SavePratilipiResponse>(){

			@Override
			public void onFailure(Throwable caught) {
				pratilipiDataInputView.setEnabled( true );
				Window.alert( caught.getMessage() );
			}

			@Override
			public void onSuccess( SavePratilipiResponse result ) {
				accordion.hide();

				PratilipiView pratilipiView = pratilipiDataInputView.getPratilipiView();
				if( pratilipiView == null ) {
					pratilipiView = new PratilipiViewDetailImpl();
					RootPanel.get( "PageContent-" + pratilipiType.getName() + "-List" ).add( pratilipiView );
				}
				pratilipiView.focus();
				pratilipiView.setPratilipiData( pratilipiData );

				pratilipiDataInputView.reset();
				pratilipiDataInputView.setEnabled( true );
			}
			
		});
	}
	
}
