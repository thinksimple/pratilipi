package com.pratilipi.servlet.content.client;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;

public class LoginForm extends Composite {
	
	private TextBox emailInput = new TextBox();
	private PasswordTextBox password = new PasswordTextBox();
	private Button loginButton = new Button("Sign In");
	private Label forgotPassword = new Label();
	
	//Error messages
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
		
		emailInput.getElement().setPropertyString("placeholder", "Email");
		emailInput.addStyleName( "form-control" );
		emailInput.addBlurHandler(new BlurHandler(){

			@Override
			public void onBlur(BlurEvent event) {
				validateEmail();
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
		
		loginButton.addStyleName("btn");
		loginButton.addStyleName("btn-lg");
		loginButton.addStyleName("btn-primary");
		loginButton.addStyleName("btn-block");
		loginButton.getElement().getStyle().setDisplay(Display.BLOCK);
		
		//forgot password link
		forgotPassword.setStyleName( "forgotPassword" );
		Anchor link = new Anchor( "Forgot Password?" );
		link.setHref( "#forgotpassword" );
		forgotPassword.getElement().appendChild( link.getElement() );
		
		//Error message Style
		emailInputError.setStyleName("errorMessage");
		passwordError.setStyleName("errorMessage");
		
		//All error messages are invisible when page is loaded for first time.
		emailInputError.setVisible(false);
		passwordError.setVisible(false);
		
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
	
	//show error messages	
	public void showEmailInputError(){
		emailInputError.setVisible(true);
	}
	
	public void showPasswordError(){
		passwordError.setVisible(true);
	}
	
	//hide error messages	
	public void hideEmailInputError(){
		emailInputError.setVisible(false);
	}
	
	public void hidePasswordError(){
		passwordError.setVisible(false);
	}

}