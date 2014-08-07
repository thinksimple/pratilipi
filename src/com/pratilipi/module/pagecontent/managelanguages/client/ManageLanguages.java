package com.pratilipi.module.pagecontent.managelanguages.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.AddLanguageRequest;
import com.pratilipi.service.shared.AddLanguageResponse;
import com.pratilipi.service.shared.GetLanguageListRequest;
import com.pratilipi.service.shared.GetLanguageListResponse;
import com.pratilipi.service.shared.data.LanguageData;

public class ManageLanguages implements EntryPoint {

	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );
	
	public void onModuleLoad() {
		
		final VerticalPanel verticalPanel = new VerticalPanel();

		FlowPanel flowPanel = new FlowPanel();
		final TextBox textBox = new TextBox();
		final Button button = new Button( "Add Language" );
		flowPanel.add( textBox );
		flowPanel.add( button );
		
		RootPanel.get( "PageContent-ManageLanguages" ).add( verticalPanel );
		RootPanel.get( "PageContent-ManageLanguages" ).add( flowPanel );
		
		pratilipiService.getLanguageList( new GetLanguageListRequest(), new AsyncCallback<GetLanguageListResponse>() {
			
			@Override
			public void onSuccess( GetLanguageListResponse response ) {

				for( LanguageData languageData : response.getLanguageList() )
					verticalPanel.add( new Label( languageData.getName() ) );
				
			}
			
			@Override
			public void onFailure( Throwable caught ) {
				Window.alert( caught.getMessage() );
			}
			
		} );
		
		button.addClickHandler( new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				button.setEnabled( false );
				LanguageData languageData = new LanguageData();
				languageData.setName( textBox.getText().trim() );
				AddLanguageRequest request = new AddLanguageRequest( languageData );
				pratilipiService.addLanguage( request, new AsyncCallback<AddLanguageResponse>() {

					@Override
					public void onSuccess( AddLanguageResponse response ) {
						Window.alert( "Language added successfully !" );
						Window.Location.reload();
					}
				
					@Override
					public void onFailure( Throwable caught ) {
						Window.alert( caught.getMessage() );
					}

				});
			}
			
		});

	}
	
}
