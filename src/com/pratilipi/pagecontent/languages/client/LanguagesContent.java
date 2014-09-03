package com.pratilipi.pagecontent.languages.client;

import com.claymus.commons.client.ui.Accordion;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.pratilipi.commons.client.LanguageDataInputView;
import com.pratilipi.commons.client.LanguageDataInputViewImpl;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.AddLanguageRequest;
import com.pratilipi.service.shared.AddLanguageResponse;
import com.pratilipi.service.shared.data.LanguageData;

public class LanguagesContent implements EntryPoint, ClickHandler {

	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );
	

	private final Accordion accordion = new Accordion();
	private final LanguageDataInputView languageDataInputView =
			new LanguageDataInputViewImpl();

	
	public void onModuleLoad() {
		if( RootPanel.get( "PageContent-Languages-DataInput" ) == null )
			return;
		
		accordion.setTitle( "Add Language" );
		languageDataInputView.addAddButtonClickHandler( this );

		accordion.add( languageDataInputView );
		RootPanel.get( "PageContent-Languages-DataInput" ).add( accordion );
	}
	
	@Override
	public void onClick( ClickEvent event ) {
		if( ! languageDataInputView.validateInputs() )
			return;
		
		languageDataInputView.setEnabled( false );
		LanguageData languageData = languageDataInputView.getLanguageData();
		AddLanguageRequest request = new AddLanguageRequest( languageData );
		pratilipiService.addLanguage( request, new AsyncCallback<AddLanguageResponse>() {

			@Override
			public void onSuccess( AddLanguageResponse response ) {
				// TODO: Display success message
				Window.Location.reload();
			}
		
			@Override
			public void onFailure( Throwable caught ) {
				languageDataInputView.setEnabled( true );
				
				// TODO: Remove browser alert and show proper error message
				Window.alert( caught.getMessage() );
			}

		});
	}
	
}
