package com.pratilipi.servlet.content.client;

import com.claymus.service.client.ClaymusService;
import com.claymus.service.client.ClaymusServiceAsync;
import com.claymus.service.shared.AddUserRequest;
import com.claymus.service.shared.AddUserResponse;
import com.claymus.service.shared.LoginUserRequest;
import com.claymus.service.shared.LoginUserResponse;
import com.claymus.service.shared.RegisterUserRequest;
import com.claymus.service.shared.RegisterUserResponse;
import com.claymus.service.shared.ResetUserPasswordRequest;
import com.claymus.service.shared.ResetUserPasswordResponse;
import com.claymus.service.shared.data.RegistrationData;
import com.claymus.service.shared.data.UserData;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class HomePageContent implements EntryPoint {

	private static final ClaymusServiceAsync claymusService =
			GWT.create( ClaymusService.class );
	
	
	public void onModuleLoad() {
		
/****************************************************** User Subscription ***********************************************/
		final Panel opaqueOverlay = new SimplePanel();
		final Panel transparentOverlay = new SimplePanel();
		
		final SubscriptionForm subscriptionForm = new SubscriptionForm();
		
		ClickHandler subscribeButtonClickHandler = new ClickHandler() {
			
			@Override
			public void onClick( ClickEvent event ) {
				subscriptionForm.setEnabled( false );
				UserData userData = subscriptionForm.getUser();
				claymusService.addUser( new AddUserRequest( userData ), new AsyncCallback<AddUserResponse>() {
					
					@Override
					public void onSuccess( AddUserResponse response ) {
						Window.Location.assign( "/invite?id=" + response.getUserId() );
					}
					
					@Override
					public void onFailure( Throwable caught ) {
						Window.alert( caught.getMessage() );
					}
					
				});
			}
		};

		ClickHandler cancelButtonClickHandler = new ClickHandler() {
			
			@Override
			public void onClick( ClickEvent event ) {
				opaqueOverlay.setVisible( false );
				transparentOverlay.setVisible( false );
				History.newItem( "" );
			}
			
		};
		
		subscriptionForm.addSubscribeButtonClickHandler( subscribeButtonClickHandler );
		subscriptionForm.addCancelButtonClickHandler( cancelButtonClickHandler );

		transparentOverlay.add( subscriptionForm );

		opaqueOverlay.addStyleName( "opaqueOverlay" );
		transparentOverlay.addStyleName( "transparentOverlay" );

		if( ! History.getToken().equals( "subscribe" ) ) {
			opaqueOverlay.setVisible( false );
			transparentOverlay.setVisible( false );
		}
	
		RootPanel.get().add( opaqueOverlay );
		RootPanel.get().add( transparentOverlay );

		History.addValueChangeHandler( new ValueChangeHandler<String>() {

			public void onValueChange( ValueChangeEvent<String> event ) {
				String historyToken = event.getValue();
				if( historyToken.equals( "subscribe" ) ) {
					opaqueOverlay.setVisible(true);
					transparentOverlay.setVisible(true);
				}
			}
			
		});
	
/* =========================================================================================================================== */
		
		//Modal Div for registration and login forms
		final FocusPanel modal = new FocusPanel();
		modal.addStyleName( "modal fade" );
		modal.getElement().setId( "myModal" );
		modal.getElement().setAttribute("tabindex", "-1");
		modal.getElement().setAttribute("role", "dialog");
		modal.getElement().setAttribute("aria-labelledby", "myModalLabel");
		modal.getElement().setAttribute("aria-hidden", "true");
		
		//Set focus on Modal div
		modal.addMouseOverHandler( new MouseOverHandler(){

			@Override
			public void onMouseOver(MouseOverEvent event) {
				modal.setFocus(true);
			}});
				
		//Click handler to change history item when modal div is clicked.
		modal.addClickHandler( new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				History.newItem( "" );
				
			}});
				
		//change history item when ESC key is pressed.
		modal.addKeyDownHandler( new KeyDownHandler(){

			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
		               History.newItem( "" );
		           }
			}});
		
/******************************************** User Registration ********************************************/
		final Panel registrationFormDialog = new FlowPanel();
		registrationFormDialog.setStyleName( "modal-dialog" );
		
		final RegistrationForm registrationForm = new RegistrationForm();
		
		ClickHandler registerButtonClickHandler = new ClickHandler() {
			
			@Override
			public void onClick( ClickEvent event ) {
				if(   registrationForm.validateFirstName()
				   && registrationForm.validateLastName()
				   && registrationForm.validateEmail()
				   && registrationForm.validatePassword()
				   && registrationForm.validateConfPassword()){
						
					RegistrationData registerData = registrationForm.getUser();
					claymusService.registerUser( new RegisterUserRequest( registerData ), new AsyncCallback<RegisterUserResponse>() {
						
						@Override
						public void onSuccess( RegisterUserResponse response ) {
							hideModal();
							Window.alert( "Sign up successfull!" );
						}
						
						@Override
						public void onFailure( Throwable caught ) {
							Window.alert( caught.getMessage() );
						}		
					});
				}
			}
		};
		
		registrationForm.addRegisterButtonClickHandler( registerButtonClickHandler );
		registrationFormDialog.add( registrationForm );
				
/************************************************** User Login **************************************************/
		final Panel loginFormDialog = new FlowPanel();
		loginFormDialog.setStyleName( "modal-dialog" );
		
		final LoginForm loginForm = new LoginForm();
		
		ClickHandler loginButtonClickHandler = new ClickHandler() {
			
			@Override
			public void onClick( ClickEvent event ) {
				if( loginForm.validateEmail() && loginForm.validatePassword() ){
					claymusService.loginUser( new LoginUserRequest( loginForm.getEmail(), loginForm.getPassword() ), new AsyncCallback<LoginUserResponse>() {
						
						@Override
						public void onSuccess( LoginUserResponse response ) {
							hideModal();
							Window.Location.reload();
						}
						
						@Override
						public void onFailure( Throwable caught ) {
							Window.alert( caught.getMessage() );
						}
					});
				}	
			}
		};
		
		loginForm.addLoginButtonClickHandler( loginButtonClickHandler );
		loginFormDialog.add( loginForm );
		
/************************************************ Forgot Password ********************************************/
		final Panel forgotPasswordFormDialog = new FlowPanel();
		forgotPasswordFormDialog.setStyleName( "modal-dialog" );
		
		final ForgotPasswordForm forgotPassword = new ForgotPasswordForm();
		
		ClickHandler genPasswdButtonClickHandler = new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				if( forgotPassword.validateEmail()){
					claymusService.resetUserPassword(new ResetUserPasswordRequest( forgotPassword.getEmail() ), new AsyncCallback<ResetUserPasswordResponse>(){
	
						@Override
						public void onFailure(Throwable caught) {
							forgotPassword.setEmailInputError( caught.getMessage() );
							forgotPassword.showEmailInputError();
						}
	
						@Override
						public void onSuccess(ResetUserPasswordResponse result) {
							Window.Location.assign( "/" );
						}});
				}
			}};
		
		forgotPassword.addGenPasswdButtonClickHandler( genPasswdButtonClickHandler );
		forgotPasswordFormDialog.add( forgotPassword );
/* ================================================================================================================================== */

		History.addValueChangeHandler( new ValueChangeHandler<String>() {

			public void onValueChange( ValueChangeEvent<String> event ) {
				String historyToken = event.getValue();
				if( historyToken.equals( "signup" ) ) {
					modal.remove( loginFormDialog );
					modal.remove( forgotPasswordFormDialog );
					modal.remove( registrationFormDialog );
					modal.add( registrationFormDialog );
					showModal();
				}
				
				if( historyToken.equals( "signin" ) ) {
					modal.remove( registrationFormDialog );
					modal.remove( forgotPasswordFormDialog );
					modal.remove( loginFormDialog );
					modal.add( loginFormDialog );
					showModal();
				}
				
				if( historyToken.equals( "signout" ) ) {
					claymusService.logoutUser( new AsyncCallback(){

						@Override
						public void onFailure(Throwable caught) {
							Window.alert( caught.getMessage() );
						}

						@Override
						public void onSuccess(Object result) {
							Window.Location.replace( "/" );
						}} );
				}
				
				if( historyToken.equals( "forgotpassword" ) ) {
					modal.remove( registrationFormDialog );
					modal.remove( loginFormDialog );
					modal.remove( forgotPasswordFormDialog );
					modal.add( forgotPasswordFormDialog );
					showModal();
				}
			}
			
		});
		
		//Adding modal view to root panel
		RootPanel.get().add( modal );
	}
	
	//JQuery function to show and hide bootstrap modal view
	public static native void showModal() /*-{
    		$wnd.jQuery("#myModal").modal("show");
	}-*/;
	
	public static native void hideModal() /*-{
			$wnd.jQuery('#myModal').modal('hide');
	}-*/;

}
