package com.pratilipi.servlet.content.client;

import com.claymus.service.client.ClaymusService;
import com.claymus.service.client.ClaymusServiceAsync;
import com.claymus.service.shared.AddUserRequest;
import com.claymus.service.shared.AddUserResponse;
import com.claymus.service.shared.data.UserData;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class HomePageContent implements EntryPoint {

	private static final ClaymusServiceAsync claymusService =
			GWT.create( ClaymusService.class );
	
	
	public void onModuleLoad() {
		
		final Panel opaqueOverlay = new SimplePanel();
		final Panel transparentOverlay = new SimplePanel();
		
		final SubscriptionForm subscriptionForm = new SubscriptionForm();
		
		ClickHandler cancelButtonClickHandler = new ClickHandler() {
			
			@Override
			public void onClick( ClickEvent event ) {
				opaqueOverlay.setVisible( false );
				transparentOverlay.setVisible( false );
				History.newItem( "" );
			}
			
		};
		
		ClickHandler subscribeButtonClickHandler = new ClickHandler() {
			
			@Override
			public void onClick( ClickEvent event ) {
				subscriptionForm.setEnabled( false );
				UserData userData = subscriptionForm.getUser();
				claymusService.addUser( new AddUserRequest( userData ), new AsyncCallback<AddUserResponse>() {
					
					@Override
					public void onSuccess( AddUserResponse response ) {
						Window.Location.assign( "/invite" );
					}
					
					@Override
					public void onFailure( Throwable caught ) {
						Window.alert( caught.getMessage() );
					}
					
				});
			}
		};

		subscriptionForm.addCancelButtonClickHandler( cancelButtonClickHandler );
		subscriptionForm.addSubscribeButtonClickHandler( subscribeButtonClickHandler );

		transparentOverlay.add( subscriptionForm );

		opaqueOverlay.addStyleName( "opaqueOverlay" );
		transparentOverlay.addStyleName( "transparentOverlay" );

		if( ! History.getToken().equals( "subscribe" ) ) {
			opaqueOverlay.setVisible( false );
			transparentOverlay.setVisible( false );
		}
	
		RootPanel.get( "PageContent-PratilipiHome" ).add( opaqueOverlay );
		RootPanel.get( "PageContent-PratilipiHome" ).add( transparentOverlay );

		History.addValueChangeHandler( new ValueChangeHandler<String>() {

			public void onValueChange( ValueChangeEvent<String> event ) {
				String historyToken = event.getValue();
				if( historyToken.equals( "subscribe" ) ) {
					opaqueOverlay.setVisible(true);
					transparentOverlay.setVisible(true);
				}
			}
			
		});
		
	}

}
