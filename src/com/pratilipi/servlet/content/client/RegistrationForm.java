package com.pratilipi.servlet.content.client;

import com.claymus.commons.shared.UserStatus;
import com.claymus.service.shared.data.RegistrationData;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
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
	private Button registerButton = new Button("Sign up");
	
	final ValidateForm validateForm = new ValidateForm();
	
	//Error messages
	private Label nameInputError = new Label();
	private Label emailInputError = new Label();
	private Label passwordError = new Label();
	private Label confPassError = new Label();
	
	
	public RegistrationForm(){
		
		Panel modalContent = new FlowPanel();
		modalContent.setStyleName( "modal-content" );
		
		Panel panel = new FlowPanel();
		panel.setStyleName( "modal-body" );
		
		FlowPanel namePanel = new FlowPanel();
		//namePanel.getElement().getStyle().setDisplay( Display.BLOCK );
		
		Panel buttonPanel = new FlowPanel();
		buttonPanel.addStyleName( "buttonPanel" );
		
		firstNameInput.getElement().setPropertyString("placeholder", "First Name");
		firstNameInput.addStyleName( "col-xs-1" );
		firstNameInput.addStyleName( "form-control" );
		firstNameInput.addStyleName( "nameTextBox" );
		
		firstNameInput.addBlurHandler(new BlurHandler(){

			@Override
			public void onBlur(BlurEvent event) {
				validateFirstName();
			}});
		
		lastNameInput.getElement().getStyle().setDisplay( Display.INLINE_BLOCK );
		lastNameInput.getElement().setPropertyString("placeholder", "Last Name");
		lastNameInput.addStyleName( "col-xs-1" );
		lastNameInput.addStyleName( "form-control" );
		lastNameInput.addStyleName( "nameTextBox" );
		lastNameInput.addBlurHandler(new BlurHandler(){

			@Override
			public void onBlur(BlurEvent event) {
				validateLastName();
			}});
		
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
				validatePassword();
			}});
		
		confirmPassword.getElement().setPropertyString("placeholder", "Confirm Password");
		confirmPassword.addStyleName( "form-control" );
		confirmPassword.addBlurHandler(new BlurHandler(){

			@Override
			public void onBlur(BlurEvent event) {
				validateConfPassword();
			}});
		
		registerButton.addStyleName("btn btn-lg");
		registerButton.addStyleName("btn-primary");
		registerButton.addStyleName("btn-block");
		registerButton.getElement().getStyle().setDisplay(Display.BLOCK);
		
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
		panel.add( namePanel );
		panel.add(nameInputError);
		panel.add(emailInput);
		panel.add(emailInputError);
		panel.add(password);
		panel.add(passwordError);
		panel.add(confirmPassword);
		panel.add(confPassError);
		buttonPanel.add(registerButton);
		panel.add( buttonPanel );
		
		modalContent.add( panel );
		
		initWidget(modalContent);
	}
	
	public RegistrationData getUser() {
		RegistrationData registrationData = new RegistrationData();
		if( firstNameInput.getText().trim().length() != 0 )
			registrationData.setFirstName( firstNameInput.getText().trim() );
		if( lastNameInput.getText().trim().length() != 0 )
			registrationData.setLastName( lastNameInput.getText().trim() );
		registrationData.setPassword( password.getText());
		registrationData.setEmail( emailInput.getText().trim() );
		registrationData.setCampaign( "preLaunch" );
		registrationData.setReferer( Window.Location.getParameter( "ref" ) );
		registrationData.setStatus( UserStatus.REGISTERED );
		
		return registrationData;
	}
	
	//Clickhandler  functions
	public void addRegisterButtonClickHandler( ClickHandler clickHandler ) {
		registerButton.addClickHandler( clickHandler );
	}
	
	//Form validation functions
	public boolean validateFirstName(){
		if( getFirstName().isEmpty() ){
			setFirstNameErrorStyle();
			setNameInputError("Enter first name");
			showNameInputError();
			return false;
		}
		else if( validateForm.ValidateFirstName( getFirstName() ) ){
			setFirstNameErrorStyle();
			setNameInputError("First Name cannot contain special characters and integers");
			showNameInputError();
			return false;
		}
		else{
			setFirstNameAcceptStyle();
			hideNameInputError();
			return true;
		}
	}
	
	public boolean validateLastName(){
		if( validateForm.ValidateLastName( getLastName() ) ){
			setLastNameErrorStyle();
			setNameInputError("Last Name cannot contain special characters and integers");
			showNameInputError();
			return false;
		}
		else{
			setLastNameAcceptStyle();
			hideNameInputError();
			return true;
		}
	}
	
	public boolean validateEmail(){
		if( getEmail().isEmpty() ){
			setEmailErrorStyle();
			setEmailInputError("Enter your email");
			showEmailInputError();
			return false;
		}
		else if( validateForm.ValidateEmail( getEmail() ) ){
			setEmailErrorStyle();
			setEmailInputError("Email not in proper format");
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
		else if( validateForm.ValidatePassword( getPassword() ) ){
			setPasswordErrorStyle();
			setPasswordError("Password should be atleast 6 characters long");
			showPasswordError();
			return false;
		}
		else{
			setPasswordAcceptStyle();
			hidePasswordError();
			return true;
		}
	}
	
	public boolean validateConfPassword(){
		if(getConfPassword().isEmpty()){
			setConfPasswordErrorStyle();
			setconfPassError("Please re-enter your password");
			showConfPassError();
			return false;
		}
		else if( validateForm.validateConfirmPassword( getPassword(), getConfPassword() ) ){
			setConfPasswordErrorStyle();
			setconfPassError("Password should be atleast 6 characters long");
			showConfPassError();
			return false;
		}
		else{
			setConfPasswordAcceptStyle();
			hideConfPassError();
			return true;
		}
	}
	
	//Get text from input fields.
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
	
	//Set and remove error styling.
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
