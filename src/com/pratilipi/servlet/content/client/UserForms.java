package com.pratilipi.servlet.content.client;

import com.claymus.service.client.ClaymusService;
import com.claymus.service.client.ClaymusServiceAsync;
import com.claymus.service.shared.LoginUserRequest;
import com.claymus.service.shared.LoginUserResponse;
import com.claymus.service.shared.RegisterUserRequest;
import com.claymus.service.shared.RegisterUserResponse;
import com.claymus.service.shared.ResetUserPasswordRequest;
import com.claymus.service.shared.ResetUserPasswordResponse;
import com.claymus.service.shared.SendQueryRequest;
import com.claymus.service.shared.SendQueryResponse;
import com.claymus.service.shared.UpdateUserPasswordRequest;
import com.claymus.service.shared.UpdateUserPasswordResponse;
import com.claymus.service.shared.data.UserData;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class UserForms implements EntryPoint {

	private static final ClaymusServiceAsync claymusService =
			GWT.create( ClaymusService.class );
	
	private boolean isChangePasswordURL = false;
	private String email = null;
	private String password = null;
	
	public void onModuleLoad() {
/*****************************************************User Login form********************************************************************/
		RootPanel loginDiv = RootPanel.get( "login" );
		final FocusPanel loginFormDialog = new FocusPanel();
		loginFormDialog.setStyleName( "modal-body" );
		
		final LoginForm loginForm = new LoginForm();

		ClickHandler loginButtonClickHandler = new ClickHandler() {
			
			@Override
			public void onClick( ClickEvent event ) {
				userLogin( loginForm );	
			}
		};
		
		ClickHandler loginFormLinksClickHandler = new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				hideLoginModal();
				
			}};

		loginForm.addLoginButtonClickHandler( loginButtonClickHandler );
		loginForm.addFormLinksClickHandler( loginFormLinksClickHandler );
		loginFormDialog.add( loginForm );
		loginFormDialog.addKeyDownHandler( new KeyDownHandler(){

			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					userLogin( loginForm );  
				   }
			}});

		loginDiv.add( loginFormDialog );

/*********************************************************User SignUp*****************************************************************/
		RootPanel signupDiv = RootPanel.get( "signup" );
		final FocusPanel registrationFormDialog = new FocusPanel();
		registrationFormDialog.setStyleName( "modal-body" );

		final RegistrationForm registrationForm = new RegistrationForm();

		ClickHandler registerButtonClickHandler = new ClickHandler() {
			
			@Override
			public void onClick( ClickEvent event ) {
				userRegistrationRPC( registrationForm );
			}	
		};
		ClickHandler signinLinkClickHandler = new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				hideSignupModal();
			}};

		registrationForm.addRegisterButtonClickHandler( registerButtonClickHandler );
		registrationForm.addSignLinkClickHandler( signinLinkClickHandler );
		registrationFormDialog.add( registrationForm );
		registrationFormDialog.addKeyDownHandler( new KeyDownHandler(){

			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					userRegistrationRPC( registrationForm );   
					History.newItem( "" );
				   }
			}});
			
		signupDiv.add( registrationFormDialog );
				
/************************************************ Forgot Password ********************************************/
		RootPanel forgotPasswordDiv = RootPanel.get( "forgotPassword" );
		final FocusPanel forgotPasswordFormDialog = new FocusPanel();
		forgotPasswordFormDialog.setStyleName( "modal-body" );

		final ForgotPasswordForm forgotPassword = new ForgotPasswordForm();

		ClickHandler genPasswdButtonClickHandler = new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				forgotPassword.hideServerError();
				forgotPassword( forgotPassword );
			}};

		forgotPassword.addGenPasswdButtonClickHandler( genPasswdButtonClickHandler );
		forgotPasswordFormDialog.add( forgotPassword );
		forgotPasswordFormDialog.addKeyDownHandler( new KeyDownHandler(){

			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					forgotPassword.hideServerError();
					forgotPassword( forgotPassword );   
				   }
			}});
		forgotPasswordDiv.add( forgotPasswordFormDialog );

/************************************************ Change Password ********************************************/
		//Modal Div for change Password forms. we are still using history token for change password 
		//because changepassword form should open after clicking a link which is not possible in normal
		//modal.
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

				final FocusPanel changePasswordFormDialog = new FocusPanel();
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
				changePasswordForm.hideServerError();
				//used when a logged in user chooses to change his password
				if( isChangePasswordURL 
					&& changePasswordForm.validateCurrentPassword()
					&& changePasswordForm.validateNewPassword() 
					&& changePasswordForm.validateConfPassword()) {
					changePasswordForm.setEnable( false );
					changePasswordRPC(changePasswordForm, email, password);
				}
				//used when user click on reset password link sent to his registered email.
				else if( !isChangePasswordURL 
						 && changePasswordForm.validateNewPassword() 
						 && changePasswordForm.validateConfPassword() ){
					changePasswordForm.setEnable( false );
					changePasswordRPC(changePasswordForm, email, password);
				}
			}};
		
		changePasswordForm.addChangePasswdButtonClickHandler( changePasswdButtonClickHandler );
		changePasswordFormDialog.add( changePasswordForm );
		changePasswordFormDialog.addKeyDownHandler( new KeyDownHandler(){

			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					changePasswordForm.hideServerError();
					if( isChangePasswordURL 
							&& changePasswordForm.validateCurrentPassword()
							&& changePasswordForm.validateNewPassword() 
							&& changePasswordForm.validateConfPassword()) {
							changePasswordForm.setEnable( false );
							changePasswordRPC(changePasswordForm, email, password);
						}
						//used when user click on reset password link sent to his registered email.
						else if( !isChangePasswordURL 
								 && changePasswordForm.validateNewPassword() 
								 && changePasswordForm.validateConfPassword() ){
							changePasswordForm.setEnable( false );
							changePasswordRPC(changePasswordForm, email, password);
						}
		           }
			}});
	
		//Adding modal view to root panel
				RootPanel.get().add( modal );
				
		//Showing modal on module load.
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
		
		//History change events
		History.addValueChangeHandler( new ValueChangeHandler<String>() {

			public void onValueChange( ValueChangeEvent<String> event ) {
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
				
				//Logout process
				if( History.getToken().equals( "signout" ) ) {
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
			}
			
		});
		
		//Contact Page mail API
		RootPanel contactForm = RootPanel.get("PageContent-PratilipiContact-Form");
		final ContactMailForm contactMailForm = new ContactMailForm();
		if( contactForm != null ) {
			contactForm.add( contactMailForm );
			ClickHandler sendMailButtonClickHandler = new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					//TODO : hide server message
					if( contactMailForm.validateName()
							&& contactMailForm.validateEmail() 
							&& contactMailForm.validateBody() ) {
						contactMailForm.setEnable( false );
						SendQueryRequest sendQueryRequest = new SendQueryRequest();
						sendQueryRequest.setEmail( contactMailForm.getEmail() );
						sendQueryRequest.setName( contactMailForm.getName() );
						sendQueryRequest.setQuery( contactMailForm.getMailBody() );
						claymusService.sendQuery( sendQueryRequest, new AsyncCallback<SendQueryResponse>() {

							@Override
							public void onFailure(Throwable caught) {
								contactMailForm.setEnable( true );
								contactMailForm.setServerMsg( caught.getMessage() );
								contactMailForm.setServerMsgClass( "alert alert-danger" );
								contactMailForm.setVisibleServerMsg( true );
							}

							@Override
							public void onSuccess(SendQueryResponse result) {
								contactMailForm.setServerMsg( result.getMessage() );
								contactMailForm.setServerMsgClass( "alert alert-success" );
								contactMailForm.setVisibleServerMsg( true );
								contactMailForm.resetForm();
								contactMailForm.setEnable( true );
								Timer time = new Timer() {

									@Override
									public void run() {
										contactMailForm.setVisibleServerMsg( false );
									}};
								time.schedule( 5000 );
							}});
					}
				}};
			
			contactMailForm.addSendButtonClickHandler( sendMailButtonClickHandler );	
		}
		
		//To change visible type of login and signup links after GWT components are loaded.
		RootPanel userAccessDiv = RootPanel.get( "Pratilipi-User-Access" );
		userAccessDiv.getElement().getStyle().setDisplay( Display.INLINE );
	}
	
	public void userRegistrationRPC( final RegistrationForm registrationForm ) {
		registrationForm.hideServerError();
		if(   registrationForm.validateFirstName()
				   && registrationForm.validateLastName()
				   && registrationForm.validateEmail()
				   && registrationForm.validatePassword()
				   && registrationForm.validateConfPassword()){
					registrationForm.setEnable( false );	
					UserData userData = registrationForm.getUser();
					claymusService.registerUser( new RegisterUserRequest( userData ), new AsyncCallback<RegisterUserResponse>() {
						
						@Override
						public void onSuccess( RegisterUserResponse response ) {
							registrationForm.hideForm();
							registrationForm.setServerSuccess( response.getMessage() );
							registrationForm.showServerSuccess();
							Timer time = new Timer() {

								@Override
								public void run() {
									hideSignupModal();
									String url = Window.Location.getPath();
									Window.Location.assign( url + "?action=signup" );
								}};
							time.schedule( 2000 );
						}
						
						@Override
						public void onFailure( Throwable caught ) {
							registrationForm.setServerError( caught.getMessage() );
							registrationForm.showServerError();
							registrationForm.setEnable( true );
						}		
					});
				}
	}
	
	public void changePasswordRPC(final ChangePasswordForm changePasswordForm, String userEmail, String token ){
		claymusService.updateUserPassword(
				new UpdateUserPasswordRequest( userEmail, token, changePasswordForm.getCurrentPassword(), changePasswordForm.getPassword() ),
				new AsyncCallback<UpdateUserPasswordResponse>(){

			@Override
			public void onFailure(Throwable caught) {
				changePasswordForm.setServerError( caught.getMessage() );
				changePasswordForm.showServerError();
				changePasswordForm.setEnable( true );
			}

			@Override
			public void onSuccess(UpdateUserPasswordResponse result) {
				changePasswordForm.hideForm();
				changePasswordForm.setServerSuccess( result.getMessage() );
				changePasswordForm.showServerSuccess();
				Timer timeout = new Timer() {

					@Override
					public void run() {
						hideLoginModal();
						Window.Location.reload();
					}};
				timeout.schedule( 2000 );
			}});
	}
	
	public void userLogin(final LoginForm loginForm){
		if( loginForm.validateEmail() && loginForm.validatePassword() ){
			loginForm.setEnabled( false );
			loginForm.hideServerError();
			claymusService.loginUser( new LoginUserRequest( loginForm.getEmail(), loginForm.getPassword() ), new AsyncCallback<LoginUserResponse>() {
				
				@Override
				public void onSuccess( LoginUserResponse response ) {
					hideLoginModal();
					Window.Location.assign( Window.Location.getHref() + "?action=login" );
				}
				
				@Override
				public void onFailure( Throwable caught ) {
					loginForm.setServerError( caught.getMessage() );
					loginForm.showServerError();
					loginForm.setEnabled( true );
				}
			});
		}
	}
	
	public void forgotPassword( final ForgotPasswordForm forgotPassword ) {
		if( forgotPassword.validateEmail()){
			forgotPassword.setEnable( false );
			claymusService.resetUserPassword(new ResetUserPasswordRequest( forgotPassword.getEmail() ), new AsyncCallback<ResetUserPasswordResponse>(){

				@Override
				public void onFailure(Throwable caught) {
					forgotPassword.setServerError( caught.getMessage() );
					forgotPassword.showServerError();
					forgotPassword.setEnable( true );
				}

				@Override
				public void onSuccess(ResetUserPasswordResponse result) {
					forgotPassword.hideForm();
					forgotPassword.setServerSuccess( result.getMessage() );
					forgotPassword.showServerSuccess();
					Timer time = new Timer() {

						@Override
						public void run() {
							hideForgotPasswordModal();
							Window.Location.reload();
						}};
					time.schedule( 5000 );
				}});
		}
	}
	
	//JQuery function to show and hide bootstrap modal view
	public static native void showModal() /*-{
    		$wnd.jQuery("#myModal").modal("show");
    		
	}-*/;
	
	public static native void hideLoginModal() /*-{
			$wnd.jQuery('#loginModal').modal('hide');
	}-*/;
	
	public static native void hideSignupModal() /*-{
		$wnd.jQuery('#signupModal').modal('hide');
	}-*/;
	
	public static native void hideForgotPasswordModal() /*-{
		$wnd.jQuery('#forgotPasswordModal').modal('hide');
	}-*/;

}
