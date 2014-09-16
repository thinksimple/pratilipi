package com.pratilipi.servlet.content.client;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;

public class LoginForm extends Composite {
	
	private Label heading = new Label();
	private TextBox emailInput = new TextBox();
	private PasswordTextBox password = new PasswordTextBox();
	private Button loginButton = new Button("Sign In");
	private Label forgotPassword = new Label();
	
	//Error messages
	private Label serverError = new Label();
	private Label emailInputError = new Label();
	private Label passwordError = new Label();
	
	
	public LoginForm(){
		
		Panel modalContent = new FlowPanel();
		modalContent.setStyleName( "modal-content" );
		
		Panel panel = new FlowPanel();
		panel.setStyleName( "modal-body" );
		
		Panel fields = new FlowPanel();
		Panel button = new FlowPanel();
		button.setStyleName( "buttonPanel" );
		

		HTML headingElement= new HTML();
		headingElement.setHTML("<h3>Welcome Back</h3>");
		
		heading.addStyleName( "page-header" );
		heading.getElement().appendChild( headingElement.getElement() );
		
		emailInput.getElement().setPropertyString("placeholder", "Email");
		emailInput.addStyleName( "form-control" );
		emailInput.addBlurHandler(new BlurHandler(){

			@Override
			public void onBlur(BlurEvent event) {
				validateEmail();
			}});
		emailInput.addFocusHandler( new FocusHandler() {

			@Override
			public void onFocus(FocusEvent event) {
				emailInputError.setVisible( false );
			}});
		
		password.getElement().getStyle().setDisplay(Display.BLOCK);
		password.getElement().setPropertyString("placeholder", "Password");
		password.addStyleName( "form-control" );
		password.addBlurHandler(new BlurHandler(){

			@Override
			public void onBlur(BlurEvent event) {
				if(getPassword().isEmpty()){
					setPasswordErrorStyle();
					setPasswordError("Enter Password");
					showPasswordError();
				}
				else{
					setPasswordAcceptStyle();
					hidePasswordError();
				}
			}});
		password.addFocusHandler( new FocusHandler() {
			
			@Override
			public void onFocus(FocusEvent event) {
				passwordError.setVisible( false );
			}
		});
		
		loginButton.addStyleName("btn");
		loginButton.addStyleName("btn-lg");
		loginButton.addStyleName("btn-primary");
		loginButton.addStyleName("btn-block");
		loginButton.getElement().getStyle().setDisplay(Display.BLOCK);
		
		forgotPassword.setStyleName( "modal-link" );
		
		//New user link in login form
		Anchor newUserAnchor = new Anchor( "Not a member?" );
		newUserAnchor.setHref( "#signup" );
		forgotPassword.getElement().appendChild( newUserAnchor.getElement() );
		
		//forgot password link
		Anchor forgotPasswdAnchor = new Anchor( "Forgot Password?" );
		forgotPasswdAnchor.setHref( "#forgotpassword" );
		forgotPassword.getElement().appendChild( forgotPasswdAnchor.getElement() );
		
		//Error message Style
		serverError.addStyleName( "alert alert-danger" );
		serverError.getElement().setAttribute( "role", "alert") ;
		emailInputError.setStyleName("errorMessage");
		passwordError.setStyleName("errorMessage");
		
		//All error messages are invisible when page is loaded for first time.
		emailInputError.setVisible(false);
		passwordError.setVisible(false);
		serverError.setVisible( false );
		
		fields.add( heading );
		fields.add( serverError );
		fields.add(emailInput);
		fields.add(emailInputError);
		fields.add(password);
		fields.add(passwordError);
		
		button.add(loginButton);		
		button.add( forgotPassword );
		
		panel.add( fields );
		panel.add( button );
		
		modalContent.add( panel );
			
		initWidget( modalContent );
	}
	
	//Click handler functions
	public void addLoginButtonClickHandler( ClickHandler clickHandler ) {
		loginButton.addClickHandler( clickHandler );
	}
	
	//Enable and Disable login form
	public void setEnabled( boolean enabled ) {
		emailInput.setEnabled( enabled );
		password.setEnabled( enabled );
		loginButton.setEnabled( enabled );
	}
	
	//Login validation functions
	public boolean validateEmail(){
		if( getEmail().isEmpty() ){
			setEmailErrorStyle();
			setEmailInputError("Enter your email");
			showEmailInputError();
			return false;
		}
		else{
			setEmailAcceptStyle();
			hideEmailInputError();
			return true;
		}
	}
	
	public boolean validatePassword(){
		if(getPassword().isEmpty()){
			setPasswordErrorStyle();
			setPasswordError("Enter Password");
			showPasswordError();
			return false;
		}
		else{
			setPasswordAcceptStyle();
			hidePasswordError();
			return true;
		}
	}
	
	public String getEmail(){
		return emailInput.getText();
	}
	
	public String getPassword(){
		return password.getText();
	}
	
	public void setEmailErrorStyle(){
		emailInput.addStyleName("textBoxError");
	}
	
	public void setEmailAcceptStyle(){
		emailInput.removeStyleName("textBoxError");
	}
	
	public void setPasswordErrorStyle(){
		password.addStyleName("textBoxError");
	}
	
	public void setPasswordAcceptStyle(){
		password.removeStyleName("textBoxError");
	}
	
	//set error message	
	public void setEmailInputError(String msg){
		emailInputError.setText(msg);
	}
	
	public void setPasswordError(String msg){
		passwordError.setText(msg);
	}
	
	public void setServerError( String error ){
		this.serverError.getElement().removeAllChildren();
		HTML msg = new HTML();
		msg.setHTML( error );
		this.serverError.getElement().appendChild( msg.getElement() );
	}
	
	//show error messages	
	public void showEmailInputError(){
		emailInputError.setVisible(true);
	}
	
	public void showPasswordError(){
		passwordError.setVisible(true);
	}
	
	public void showServerError(){
		this.serverError.setVisible( true );
	}
	
	//hide error messages	
	public void hideEmailInputError(){
		emailInputError.setVisible(false);
	}
	
	public void hidePasswordError(){
		passwordError.setVisible(false);
	}

	public void hideServerError(){
		this.serverError.setVisible( false );
	}
}