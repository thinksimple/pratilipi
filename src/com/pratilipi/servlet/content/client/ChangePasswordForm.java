package com.pratilipi.servlet.content.client;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PasswordTextBox;

public class ChangePasswordForm extends Composite {
	
	private PasswordTextBox currentPassword = new PasswordTextBox();
	private PasswordTextBox password = new PasswordTextBox();
	private PasswordTextBox confirmPassword = new PasswordTextBox();
	private Button changePasswordButton = new Button("Change Password");
	private Label serverError = new Label();
	private Label heading = new Label();
	
	final ValidateForm validateForm = new ValidateForm();
	
	//Error messages
	private Label currentPasswordError = new Label();
	private Label passwordError = new Label();
	private Label confPassError = new Label();
	
	
	public ChangePasswordForm(){
		
		Panel modalContent = new FlowPanel();
		modalContent.setStyleName( "modal-content" );
		
		Panel panel = new FlowPanel();
		panel.setStyleName( "modal-body" );
		
		HTML headingElement= new HTML();
		headingElement.setHTML("<h3>Change Password</h3>");
		
		heading.addStyleName( "page-header" );
		heading.getElement().appendChild( headingElement.getElement() );
		
		Panel buttonPanel = new FlowPanel();
		buttonPanel.addStyleName( "buttonPanel" );
		
		serverError.addStyleName( "alert alert-danger" );
		serverError.getElement().setAttribute( "role", "alert") ;
		
		currentPassword.getElement().setPropertyString("placeholder", "Current Password");
		currentPassword.addStyleName( "form-control" );
		currentPassword.addBlurHandler(new BlurHandler(){

			@Override
			public void onBlur(BlurEvent event) {
				validateCurrentPassword();
			}});
		
		password.getElement().getStyle().setDisplay(Display.BLOCK);
		password.getElement().setPropertyString("placeholder", "New Password");
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
		
		changePasswordButton.addStyleName("btn btn-lg");
		changePasswordButton.addStyleName("btn-primary");
		changePasswordButton.addStyleName("btn-block");
		changePasswordButton.getElement().getStyle().setDisplay(Display.BLOCK);
		
		//Error message Style
		currentPasswordError.setStyleName("errorMessage");
		passwordError.setStyleName("errorMessage");
		confPassError.setStyleName("errorMessage");
				
		//All error messages are invisible when page is loaded for first time.
		serverError.setVisible( false );
		currentPasswordError.setVisible(false);
		passwordError.setVisible(false);
		confPassError.setVisible(false);
		
		//Hide current password for password reset form.
		currentPassword.setVisible( false );
		
		panel.add( heading );
		panel.add( serverError );
		panel.add(currentPassword);
		panel.add(currentPasswordError);
		panel.add(password);
		panel.add(passwordError);
		panel.add(confirmPassword);
		panel.add(confPassError);
		buttonPanel.add(changePasswordButton);
		panel.add( buttonPanel );
		
		modalContent.add( panel );
		
		initWidget(modalContent);
	}
	
	public void showCurrentPassword(){
		this.currentPassword.setVisible( true );
	}
	
	public void hideCurrentPassword(){
		this.currentPassword.setVisible( false );
	}
	
	public void showCurrentPasswordError(){}
	
	public void hideCurrentPasswordError(){
		this.currentPasswordError.setVisible( false );
	}
	
	//Clickhandler  functions
	public void addChangePasswdButtonClickHandler( ClickHandler clickHandler ) {
		changePasswordButton.addClickHandler( clickHandler );
	}
	
	//Form validation functions
	public boolean validateCurrentPassword(){
		if( currentPassword.getText().isEmpty() ){
			currentPassword.addStyleName("textBoxError");
			setCurrentPasswordError("Enter your current password");
			currentPasswordError.setVisible(true);
			return false;
		}
		else{
			currentPassword.removeStyleName("textBoxError");
			currentPasswordError.setVisible( false );
			return true;
		}
	}
	
	public boolean validatePassword(){
		if(password.getText().isEmpty()){
			password.addStyleName("textBoxError");
			setPasswordError("Enter New Password");
			passwordError.setVisible(true);
			return false;
		}
		else if( validateForm.ValidatePassword( getPassword() ) ){
			password.addStyleName("textBoxError");
			setPasswordError("Password should be atleast 6 characters long");
			passwordError.setVisible(true);
			return false;
		}
		else{
			password.removeStyleName("textBoxError");
			passwordError.setVisible( false );
			return true;
		}
	}
	
	public boolean validateConfPassword(){
		if(confirmPassword.getText().isEmpty()){
			confirmPassword.addStyleName("textBoxError");
			setconfPassError("Please confirm your password");
			confPassError.setVisible(true);
			return false;
		}
		else if( validateForm.validateConfirmPassword( getPassword(), confirmPassword.getText() ) ){
			confirmPassword.addStyleName("textBoxError");
			setconfPassError("These passwords don't match. Please try again.");
			confPassError.setVisible(true);
			return false;
		}
		else{
			confirmPassword.removeStyleName("textBoxError");
			confPassError.setVisible( false );
			return true;
		}
	}
	
	//Get text from input fields.
	public String getCurrentPassword(){
		return currentPassword.getText().trim().isEmpty() ? null : currentPassword.getText().trim();
	}

	public String getPassword(){
		return password.getText().trim().isEmpty() ? null : password.getText().trim();
	}
	
	//set error message	
	public void setCurrentPasswordError(String msg){
		currentPasswordError.setText(msg);
	}

	public void setPasswordError(String msg){
		passwordError.setText(msg);
	}

	public void setconfPassError(String msg){
		confPassError.setText(msg);
	}
	
	//show error messages	
	public String getServerError() {
		return serverError.getText();
	}

	public void setServerError(String error) {
		this.serverError.getElement().removeAllChildren();
		HTML errorMsg = new HTML();
		errorMsg.setHTML( error );
		this.serverError.getElement().appendChild( errorMsg.getElement() );
	}
	
	public void showServerError(){
		this.serverError.setVisible( true );
	}
	
	public void hideServerError(){}
}
