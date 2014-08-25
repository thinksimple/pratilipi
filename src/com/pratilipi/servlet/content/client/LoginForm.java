package com.pratilipi.servlet.content.client;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;

public class LoginForm extends Composite {
	
	private TextBox emailInput = new TextBox();
	private PasswordTextBox password = new PasswordTextBox();
	private Button loginButton = new Button("SIGN IN");
	
	//Error messages
	private Label emailInputError = new Label();
	private Label passwordError = new Label();
	
	
	public LoginForm(){
		
		FormPanel form = new FormPanel();
		Panel panel = new FlowPanel();
		
		final ValidateForm validateForm = new ValidateForm();
		
		FlowPanel namePanel = new FlowPanel();
		namePanel.getElement().getStyle().setDisplay( Display.BLOCK );
		
		emailInput.getElement().setPropertyString("placeholder", "Email");
		emailInput.addStyleName( "form-control" );
		emailInput.addBlurHandler(new BlurHandler(){

			@Override
			public void onBlur(BlurEvent event) {
				if( getEmail().isEmpty() ){
					setEmailErrorStyle();
					setEmailInputError("Enter your email");
					showEmailInputError();
				}
				else if( validateForm.ValidateEmail( getEmail() ) ){
					setEmailErrorStyle();
					setEmailInputError("Email not in proper format");
					showEmailInputError();
				}
				else{
					setEmailAcceptStyle();
					hideEmailInputError();
				}
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
				else if( validateForm.ValidatePassword( getPassword() )){
					setPasswordErrorStyle();
					setPasswordError("Password should be atleast 6 characters long");
					showPasswordError();
				}
				else{
					setPasswordAcceptStyle();
					hidePasswordError();
				}
			}});
		
		loginButton.addStyleName("btn btn-lg");
		loginButton.addStyleName("btn-primary");
		loginButton.addStyleName("btn-block");
		loginButton.getElement().getStyle().setDisplay(Display.BLOCK);
		
		//Error message Style
		emailInputError.setStyleName("errorMessage");
		passwordError.setStyleName("errorMessage");
		
		//All error messages are invisible when page is loaded for first time.
		emailInputError.setVisible(false);
		passwordError.setVisible(false);
		
		panel.add(emailInput);
		panel.add(emailInputError);
		panel.add(password);
		panel.add(passwordError);
		panel.add(loginButton);
		
		form.add(panel);
		
		initWidget(form);
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