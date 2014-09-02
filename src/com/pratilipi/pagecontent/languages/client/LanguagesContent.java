package com.pratilipi.pagecontent.languages.client;

import com.claymus.commons.client.ui.formfield.TextInputFormField;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.AddLanguageRequest;
import com.pratilipi.service.shared.AddLanguageResponse;
import com.pratilipi.service.shared.data.LanguageData;

public class LanguagesContent implements EntryPoint, ClickHandler {

	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );
	

	private final TextInputFormField languageInput = new TextInputFormField();
	private final Button addButton = new Button( "Add" );

	
	public void onModuleLoad() {
		languageInput.setRequired( true );
		addButton.setStyleName( "btn btn-default" );
		addButton.addClickHandler( this );
		
		RootPanel.get( "PageContent-Languages-TextInput" ).add( languageInput );
		RootPanel.get( "PageContent-Languages-AddButton" ).add( addButton );
	}
	
	@Override
	public void onClick(ClickEvent event) {
		if( ! languageInput.validate() )
			return;
		
		languageInput.setEnabled( false );
		addButton.setEnabled( false );
		addButton.setText( "Saving ..." );

		LanguageData languageData = new LanguageData();
		languageData.setName( languageInput.getText() );
		AddLanguageRequest request = new AddLanguageRequest( languageData );
		pratilipiService.addLanguage( request, new AsyncCallback<AddLanguageResponse>() {

			@Override
			public void onSuccess( AddLanguageResponse response ) {
				// TODO: Display success message
				Window.Location.reload();
			}
		
			@Override
			public void onFailure( Throwable caught ) {
				languageInput.setEnabled( true );
				addButton.setEnabled( true );
				addButton.setText( "Add" );
				
				// TODO: Remove browser alert and show proper error message
				Window.alert( caught.getMessage() );
			}

		});
	}
	
}
