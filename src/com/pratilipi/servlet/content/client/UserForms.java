package com.pratilipi.servlet.content.client;

import com.claymus.service.client.ClaymusService;
import com.claymus.service.client.ClaymusServiceAsync;
import com.claymus.service.shared.InviteUserRequest;
import com.claymus.service.shared.InviteUserResponse;
import com.claymus.service.shared.LoginUserRequest;
import com.claymus.service.shared.LoginUserResponse;
import com.claymus.service.shared.RegisterUserRequest;
import com.claymus.service.shared.RegisterUserResponse;
import com.claymus.service.shared.ResetUserPasswordRequest;
import com.claymus.service.shared.ResetUserPasswordResponse;
import com.claymus.service.shared.UpdateUserPasswordRequest;
import com.claymus.service.shared.UpdateUserPasswordResponse;
import com.claymus.service.shared.data.UserData;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
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

public class UserForms implements EntryPoint {

	private static final ClaymusServiceAsync claymusService =
			GWT.create( ClaymusService.class );
	
	private boolean isChangePasswordURL = false;
	private String email = null;
	private String password = null;
	
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
				claymusService.inviteUser( new InviteUserRequest( userData ), new AsyncCallback<InviteUserResponse>() {
					
					@Override
					public void onSuccess( InviteUserResponse response ) {
						Window.Location.assign( "/invite" );
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
				if (event.getNativeKeyCode() == KeyCodes.KEY_ESCAPE) {
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
						
					UserData userData = registrationForm.getUser();
					claymusService.registerUser( new RegisterUserRequest( userData ), new AsyncCallback<RegisterUserResponse>() {
						
						@Override
						public void onSuccess( RegisterUserResponse response ) {
							hideModal();
							Window.alert( "Sign up successfull!" );
						}
						
						@Override
						public void onFailure( Throwable caught ) {
							registrationForm.setServerError( caught.getMessage() );
							registrationForm.showServerError();
						}		
					});
				}
			}
		};
		
		registrationForm.addRegisterButtonClickHandler( registerButtonClickHandler );
		registrationFormDialog.add( registrationForm );
				
/************************************************** User Login **************************************************/
		final FocusPanel loginFormDialog = new FocusPanel();
		loginFormDialog.setStyleName( "modal-dialog" );
		
		final LoginForm loginForm = new LoginForm();
		
		ClickHandler loginButtonClickHandler = new ClickHandler() {
			
			@Override
			public void onClick( ClickEvent event ) {
				userLogin( loginForm );	
			}
		};
		
		loginForm.addLoginButtonClickHandler( loginButtonClickHandler );
		loginFormDialog.add( loginForm );
		loginFormDialog.addKeyDownHandler( new KeyDownHandler(){

			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
		            userLogin( loginForm );   
					History.newItem( "" );
		           }
			}});
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
							forgotPassword.setServerError( caught.getMessage() );
							forgotPassword.showServerError();
						}
	
						@Override
						public void onSuccess(ResetUserPasswordResponse result) {
							Window.Location.assign( "/" );
						}});
				}
			}};
		
		forgotPassword.addGenPasswdButtonClickHandler( genPasswdButtonClickHandler );
		forgotPasswordFormDialog.add( forgotPassword );
		
/************************************************ Change Password ********************************************/
		final Panel changePasswordFormDialog = new FlowPanel();
		changePasswordFormDialog.setStyleName( "modal-dialog" );
		
		final ChangePasswordForm changePasswordForm = new ChangePasswordForm();
		
		//This will through exception when URL contains email address and password is absent 
		email = ( Window.Location.getHash().lastIndexOf( "-" ) != Window.Location.getHash().indexOf( "-" ) )
						? Window.Location.getHash().substring(Window.Location.getHash().indexOf( "-" )+1, 
								 Window.Location.getHash().lastIndexOf( "-" )): null;
		password = ( Window.Location.getHash().lastIndexOf( "-" ) != Window.Location.getHash().indexOf( "-" ) )
						? Window.Location.getHash().substring( Window.Location.getHash().lastIndexOf( "-" )+1 ) : null;
						
		if ( email != null && password == null){
			Window.alert( "Please check the URL. Try again with correct URL" );
			Window.Location.replace( " " );
		}
		
		this.isChangePasswordURL = History.getToken().equals( "changepassword" );
		
		ClickHandler changePasswdButtonClickHandler = new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				//used when a logged in user chooses to change his password
				if( isChangePasswordURL 
					&& changePasswordForm.validateCurrentPassword()
					&& changePasswordForm.validatePassword() 
					&& changePasswordForm.validateConfPassword())
					changePasswordRPC(changePasswordForm, email, password);
				//used when user click on reset password link sent to his registered email.
				else if( !isChangePasswordURL 
						 && changePasswordForm.validatePassword() 
						 && changePasswordForm.validateConfPassword() )
							changePasswordRPC(changePasswordForm, email, password);
			}};
		
		changePasswordForm.addChangePasswdButtonClickHandler( changePasswdButtonClickHandler );
		changePasswordFormDialog.add( changePasswordForm );
/* ================================================================================================================================== */
		//Used when user clicks on links.
		History.addValueChangeHandler( new ValueChangeHandler<String>() {

			public void onValueChange( ValueChangeEvent<String> event ) {
				String historyToken = event.getValue();
				if( historyToken.equals( "signup" ) ) {
					modal.remove( loginFormDialog );
					modal.remove( forgotPasswordFormDialog );
					modal.remove( changePasswordFormDialog );
					modal.remove( registrationFormDialog );
					modal.add( registrationFormDialog );
					showModal();
				}
				
				if( historyToken.equals( "signin" ) ) {
					modal.remove( registrationFormDialog );
					modal.remove( forgotPasswordFormDialog );
					modal.remove( changePasswordFormDialog );
					modal.remove( loginFormDialog );
					modal.add( loginFormDialog );
					showModal();
				}
				
				if( historyToken.equals( "signout" ) ) {
					claymusService.logoutUser( new AsyncCallback<Void>(){

						@Override
						public void onFailure(Throwable caught) {
							Window.alert( caught.getMessage() );
						}

						@Override
						public void onSuccess(Void result) {
							Window.Location.replace( "/" );
						}} );
				}
				
				if( historyToken.equals( "forgotpassword" ) ) {
					modal.remove( registrationFormDialog );
					modal.remove( loginFormDialog );
					modal.remove( changePasswordFormDialog );
					modal.remove( forgotPasswordFormDialog );
					modal.add( forgotPasswordFormDialog );
					showModal();
				}
				
				if( History.getToken().startsWith( "changepassword" ) ) {
					email = ( Window.Location.getHash().lastIndexOf( "-" ) != Window.Location.getHash().indexOf( "-" ) )
							? Window.Location.getHash().substring(Window.Location.getHash().indexOf( "-" )+1, 
									 Window.Location.getHash().lastIndexOf( "-" )): null;
					password = ( Window.Location.getHash().lastIndexOf( "-" ) != Window.Location.getHash().indexOf( "-" ) )
									? Window.Location.getHash().substring( Window.Location.getHash().lastIndexOf( "-" )+1 ) : null;
									
					if ( email != null && password == null){
						Window.alert( "Please check the URL. Try again with correct URL" );
						Window.Location.replace( " " );
					}
					
					isChangePasswordURL = History.getToken().equals( "changepassword" );
					
					if( History.getToken().equals( "changepassword" ) )
						changePasswordForm.showCurrentPassword();
					else{
						changePasswordForm.hideCurrentPassword();
						changePasswordForm.hideCurrentPasswordError();
					}
					modal.remove( registrationFormDialog );
					modal.remove( loginFormDialog );
					modal.remove( forgotPasswordFormDialog );
					modal.remove( changePasswordFormDialog );
					modal.add( changePasswordFormDialog );
					showModal();
				}
			}
			
		});
		
		//Adding modal view to root panel
		RootPanel.get().add( modal );
		
		//Showing modal on module load.
		if( History.getToken().equals( "signup" ) ) {
			modal.remove( loginFormDialog );
			modal.remove( forgotPasswordFormDialog );
			modal.remove( changePasswordFormDialog );
			modal.remove( registrationFormDialog );
			modal.add( registrationFormDialog );
			showModal();
		}
				
		if( History.getToken().equals( "signin" ) ) {
			modal.remove( registrationFormDialog );
			modal.remove( forgotPasswordFormDialog );
			modal.remove( changePasswordFormDialog );
			modal.remove( loginFormDialog );
			modal.add( loginFormDialog );
			showModal();
		}
				
		if( History.getToken().equals( "forgotpassword" ) ) {
			modal.remove( registrationFormDialog );
			modal.remove( loginFormDialog );
			modal.remove( changePasswordFormDialog );
			modal.remove( forgotPasswordFormDialog );
			modal.add( forgotPasswordFormDialog );
			showModal();
		}
		
		if( History.getToken().startsWith( "changepassword" ) ) {
			if( History.getToken().equals( "changepassword" ) )
				changePasswordForm.showCurrentPassword();
			modal.remove( registrationFormDialog );
			modal.remove( loginFormDialog );
			modal.remove( forgotPasswordFormDialog );
			modal.remove( changePasswordFormDialog );
			modal.add( changePasswordFormDialog );
			showModal();
		}
		

		RootPanel contactForm = RootPanel.get("PageContent-PratilipiContact-Form");
		ContactMailForm contactMailForm = new ContactMailForm();
		if( contactForm != null )
			contactForm.add( contactMailForm );
	}
	
	public void changePasswordRPC(final ChangePasswordForm changePasswordForm, String userEmail, String token ){
		claymusService.updateUserPassword(
				new UpdateUserPasswordRequest( userEmail, token, changePasswordForm.getCurrentPassword(), changePasswordForm.getPassword() ),
				new AsyncCallback<UpdateUserPasswordResponse>(){

			@Override
			public void onFailure(Throwable caught) {
				changePasswordForm.setServerError( caught.getMessage() );
				changePasswordForm.showServerError();
			}

			@Override
			public void onSuccess(UpdateUserPasswordResponse result) {
				Window.alert( "Password Changed successfully." );
				Window.Location.replace( "/" );
			}});
	}
	
	public void userLogin(final LoginForm loginForm){
		if( loginForm.validateEmail() && loginForm.validatePassword() ){
			claymusService.loginUser( new LoginUserRequest( loginForm.getEmail(), loginForm.getPassword() ), new AsyncCallback<LoginUserResponse>() {
				
				@Override
				public void onSuccess( LoginUserResponse response ) {
					hideModal();
					Window.Location.reload();
				}
				
				@Override
				public void onFailure( Throwable caught ) {
					loginForm.setServerError( caught.getMessage() );
					loginForm.showServerError();
				}
			});
		}
	}
	
	//JQuery function to show and hide bootstrap modal view
	public static native void showModal() /*-{
    		$wnd.jQuery("#myModal").modal("show");
	}-*/;
	
	public static native void hideModal() /*-{
			$wnd.jQuery('#myModal').modal('hide');
	}-*/;

}
