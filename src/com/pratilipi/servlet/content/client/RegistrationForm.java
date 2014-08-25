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

public class RegistrationForm extends Composite {
	private TextBox firstNameInput = new TextBox();
	private TextBox lastNameInput = new TextBox();
	private TextBox emailInput = new TextBox();
	private PasswordTextBox password = new PasswordTextBox();
	private PasswordTextBox confirmPassword = new PasswordTextBox();
	private Button register = new Button("SIGN UP");
	
	//Error messages
	private Label nameInputError = new Label();
	private Label emailInputError = new Label();
	private Label passwordError = new Label();
	private Label confPassError = new Label();
	
	
	public RegistrationForm(){
		
		FormPanel form = new FormPanel();
		Panel panel = new FlowPanel();
		
		final ValidateForm validateForm = new ValidateForm();
		
		FlowPanel namePanel = new FlowPanel();
		namePanel.getElement().getStyle().setDisplay( Display.BLOCK );
		
		firstNameInput.getElement().setPropertyString("placeholder", "First Name");
		firstNameInput.addStyleName( "col-xs-1" );
		firstNameInput.addStyleName( "form-control" );
		firstNameInput.addStyleName( "nameTextBox" );
		
		firstNameInput.addBlurHandler(new BlurHandler(){

			@Override
			public void onBlur(BlurEvent event) {
				if( getFirstName().isEmpty() ){
					setFirstNameErrorStyle();
					setNameInputError("Enter first name");
					showNameInputError();
				}
				else if( validateForm.ValidateFirstName( getFirstName() ) ){
					setFirstNameErrorStyle();
					setNameInputError("First Name cannot contain special characters and integers");
					showNameInputError();
				}
				else{
					setFirstNameAcceptStyle();
					hideNameInputError();
				}
			}});
		
		lastNameInput.getElement().setPropertyString("placeholder", "Last Name");
		lastNameInput.addStyleName( "col-xs-1" );
		lastNameInput.addStyleName( "form-control" );
		lastNameInput.addStyleName( "nameTextBox" );
		lastNameInput.addBlurHandler(new BlurHandler(){

			@Override
			public void onBlur(BlurEvent event) {
				if( validateForm.ValidateLastName( getLastName() ) ){
					setLastNameErrorStyle();
					setNameInputError("Last Name cannot contain special characters and integers");
					showNameInputError();
				}
				else{
					setLastNameAcceptStyle();
					hideNameInputError();
				}
			}});
		
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
				else if( validateForm.ValidatePassword( getPassword() ) ){
					setPasswordErrorStyle();
					setPasswordError("Password should be atleast 6 characters long");
					showPasswordError();
				}
				else{
					setPasswordAcceptStyle();
					hidePasswordError();
				}
			}});
		
		confirmPassword.getElement().setPropertyString("placeholder", "Confirm Password");
		confirmPassword.addStyleName( "form-control" );
		confirmPassword.addBlurHandler(new BlurHandler(){

			@Override
			public void onBlur(BlurEvent event) {
				if(getConfPassword().isEmpty()){
					setConfPasswordErrorStyle();
					setconfPassError("Please re-enter your password");
					showConfPassError();
				}
				else if( validateForm.validateConfirmPassword( getPassword(), getConfPassword() ) ){
					setConfPasswordErrorStyle();
					setconfPassError("Password should be atleast 6 characters long");
					showConfPassError();
				}
				else{
					setConfPasswordAcceptStyle();
					hideConfPassError();
				}
			}});
		
		register.addStyleName("btn btn-lg");
		register.addStyleName("btn-primary");
		register.addStyleName("btn-block");
		register.getElement().getStyle().setDisplay(Display.BLOCK);
		
		//Error message Style
		nameInputError.setStyleName("errorMessage");
		emailInputError.setStyleName("errorMessage");
		passwordError.setStyleName("errorMessage");
		confPassError.setStyleName("errorMessage");
		
		//All error messages are invisible when page is loaded for first time.
		nameInputError.setVisible(false);
		emailInputError.setVisible(false);
		passwordError.setVisible(false);
		confPassError.setVisible(false);
		
		namePanel.add(firstNameInput);
		namePanel.add(lastNameInput);
		panel.add(namePanel);
		panel.add(nameInputError);
		panel.add(emailInput);
		panel.add(emailInputError);
		panel.add(password);
		panel.add(passwordError);
		panel.add(confirmPassword);
		panel.add(confPassError);
		panel.add(register);
		
		form.add(panel);
		
		initWidget(form);
	}
	
	public String getFirstName(){
		return firstNameInput.getText();
	}
	
	public String getLastName(){
		return lastNameInput.getText();
	}
	
	public String getEmail(){
		return emailInput.getText();
	}
	
	public String getPassword(){
		return password.getText();
	}
	
	public String getConfPassword(){
		return confirmPassword.getText();
	}
	
	public void setFirstNameErrorStyle(){
		firstNameInput.addStyleName("textBoxError");
	}
	
	public void setFirstNameAcceptStyle(){
		firstNameInput.removeStyleName("textBoxError");
	}
	
	public void setLastNameErrorStyle(){
		lastNameInput.addStyleName("textBoxError");
	}
	
	public void setLastNameAcceptStyle(){
		lastNameInput.removeStyleName("textBoxError");
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
	
	public void setConfPasswordErrorStyle(){
		confirmPassword.addStyleName("textBoxError");
	}
	
	public void setConfPasswordAcceptStyle(){
		confirmPassword.removeStyleName("textBoxError");
	}
	
	//set error message
	public void setNameInputError(String msg){
		nameInputError.setText(msg);
	}
	
	public void setEmailInputError(String msg){
		emailInputError.setText(msg);
	}
	
	public void setPasswordError(String msg){
		passwordError.setText(msg);
	}
	
	public void setconfPassError(String msg){
		confPassError.setText(msg);
	}
	
	//show error messages
	public void showNameInputError(){
		nameInputError.setVisible(true);
	}
	
	public void showEmailInputError(){
		emailInputError.setVisible(true);
	}
	
	public void showPasswordError(){
		passwordError.setVisible(true);
	}
	
	public void showConfPassError(){
		confPassError.setVisible(true);
	}
	
	//hide error messages
	public void hideNameInputError(){
		nameInputError.setVisible(false);
	}
	
	public void hideEmailInputError(){
		emailInputError.setVisible(false);
	}
	
	public void hidePasswordError(){
		passwordError.setVisible(false);
	}
	
	public void hideConfPassError(){
		confPassError.setVisible(false);
	}

}
