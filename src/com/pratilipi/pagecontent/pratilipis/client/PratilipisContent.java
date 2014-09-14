package com.pratilipi.pagecontent.pratilipis.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.pratilipi.commons.client.PratilipiDataInputView;
import com.pratilipi.commons.client.PratilipiDataInputViewAccordionImpl;
import com.pratilipi.commons.client.PratilipiView;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.commons.shared.PratilipiUtil;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.GetAuthorListRequest;
import com.pratilipi.service.shared.GetAuthorListResponse;
import com.pratilipi.service.shared.GetLanguageListRequest;
import com.pratilipi.service.shared.GetLanguageListResponse;
import com.pratilipi.service.shared.SavePratilipiRequest;
import com.pratilipi.service.shared.SavePratilipiResponse;
import com.pratilipi.service.shared.data.AuthorData;
import com.pratilipi.service.shared.data.LanguageData;
import com.pratilipi.service.shared.data.PratilipiData;

public class PratilipisContent implements EntryPoint, ClickHandler {

	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );
	

	private PratilipiDataInputView pratilipiDataInputView;
	
	private PratilipiType pratilipiType;
	
	
	public void onModuleLoad() {
		for( PratilipiType pratilipiType : PratilipiType.values() )
			if( RootPanel.get( "PageContent-" + pratilipiType.getName() + "-List" ) != null )
				this.pratilipiType = pratilipiType;
		
		if( pratilipiType == null )
			return;
		
		
		RootPanel rootPanel = RootPanel.get( "PageContent-" + pratilipiType.getName() + "-DataInput" );
		if( rootPanel != null ) {
			pratilipiDataInputView = new PratilipiDataInputViewAccordionImpl( pratilipiType );
			pratilipiDataInputView.addAddButtonClickHandler( this );

			rootPanel.add( pratilipiDataInputView );
			
			// Load list of authors.
			pratilipiService.getAuthorList( new GetAuthorListRequest( null , 250 ), new AsyncCallback<GetAuthorListResponse>() {

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
			rootPanel.clear();
			String filters = rootPanel.getElement().getAttribute( "pratilipi-filters" );
			if( filters != null && filters.equals( "CLASSICS" ) )
				rootPanel.add( new PratilipiList( pratilipiType, true, pratilipiDataInputView ) );
			else
				rootPanel.add( new PratilipiList( pratilipiType, null, pratilipiDataInputView ) );
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
			public void onSuccess( SavePratilipiResponse response ) {
				final PratilipiView pratilipiView = pratilipiDataInputView.getPratilipiView();
				if( pratilipiView == null ) {
					Window.Location.replace( pratilipiType.getPageUrl() + response.getPratilipiId() );

				} else {
					pratilipiDataInputView.reset();
					pratilipiDataInputView.setEnabled( true );
					new Timer() { // Wait for the pratilipiDataInputView to collapse
						@Override
						public void run() {
							pratilipiView.focus();
							pratilipiView.setPratilipiData( pratilipiData );
						}
					}.schedule( 100 );
				}
			}
			
		});
	}
	
}
