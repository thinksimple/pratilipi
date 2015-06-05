package com.pratilipi.servlet.content.client;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PasswordTextBox;

public class ChangePasswordForm extends Composite {
	
	private Panel modalContent = new FlowPanel();
	private Panel panel = new FlowPanel();

	
	private PasswordTextBox currentPassword = new PasswordTextBox();
	private PasswordTextBox newPassword = new PasswordTextBox();
	private PasswordTextBox confirmPassword = new PasswordTextBox();
	private Button changePasswordButton = new Button("Change Password");
	private Label serverError = new Label();
	private Label serverSuccessMsg = new Label();
	private Label heading = new Label();
	
	final ValidateForm validateForm = new ValidateForm();
	
	//Error messages
	private Label currentPasswordError = new Label();
	private Label newPasswordError = new Label();
	private Label confPassError = new Label();
	
	
	public ChangePasswordForm(){
		
		modalContent.setStyleName( "modal-content" );
		
		panel.setStyleName( "modal-body" );
		
		HTML headingElement= new HTML();
		headingElement.setHTML("<h3>Change Password</h3>");
		
		heading.addStyleName( "page-header" );
		heading.getElement().appendChild( headingElement.getElement() );
		
		Panel buttonPanel = new FlowPanel();
		buttonPanel.addStyleName( "buttonPanel" );
		
		serverError.addStyleName( "alert alert-danger" );
		serverError.getElement().setAttribute( "role", "alert") ;
		
		serverSuccessMsg.addStyleName( "alert-success" );
		serverSuccessMsg.getElement().getStyle().setPadding( 15, Unit.PX );
		serverSuccessMsg.getElement().setAttribute( "role", "alert") ;
		
		currentPassword.getElement().setPropertyString("placeholder", "Current Password");
		currentPassword.addStyleName( "form-control" );
		currentPassword.addBlurHandler(new BlurHandler(){

			@Override
			public void onBlur(BlurEvent event) {
				validateCurrentPassword();
			}});
		currentPassword.addFocusHandler( new FocusHandler() {

			@Override
			public void onFocus(FocusEvent event) {
				currentPasswordError.setVisible( false );
			}} );
		
		newPassword.getElement().getStyle().setDisplay(Display.BLOCK);
		newPassword.getElement().setPropertyString("placeholder", "New Password");
		newPassword.addStyleName( "form-control" );
		newPassword.addBlurHandler(new BlurHandler(){

			@Override
			public void onBlur(BlurEvent event) {
				validateNewPassword();
			}});
		newPassword.addFocusHandler( new FocusHandler() {

			@Override
			public void onFocus(FocusEvent event) {
				newPasswordError.setVisible( false );
				
			}});
		
		confirmPassword.getElement().setPropertyString("placeholder", "Confirm Password");
		confirmPassword.addStyleName( "form-control" );
		confirmPassword.addBlurHandler(new BlurHandler(){

			@Override
			public void onBlur(BlurEvent event) {
				validateConfPassword();
			}});
		confirmPassword.addFocusHandler( new FocusHandler() {

			@Override
			public void onFocus(FocusEvent event) {
				confPassError.setVisible( false );
				
			}});
		
		changePasswordButton.addStyleName("btn btn-lg");
		changePasswordButton.addStyleName("btn-primary");
		changePasswordButton.addStyleName("btn-block");
		changePasswordButton.getElement().getStyle().setDisplay(Display.BLOCK);
		
		//Error message Style
		currentPasswordError.setStyleName("errorMessage");
		newPasswordError.setStyleName("errorMessage");
		confPassError.setStyleName("errorMessage");
				
		//All error messages are invisible when page is loaded for first time.
		serverError.setVisible( false );
		serverSuccessMsg.setVisible( false );
		currentPasswordError.setVisible(false);
		newPasswordError.setVisible(false);
		confPassError.setVisible(false);
		
		//Hide current password for password reset form.
		currentPassword.setVisible( false );
		
		panel.add( heading );
		panel.add( serverError );
		panel.add(currentPassword);
		panel.add(currentPasswordError);
		panel.add(newPassword);
		panel.add(newPasswordError);
		panel.add(confirmPassword);
		panel.add(confPassError);
		buttonPanel.add(changePasswordButton);
		panel.add( buttonPanel );
		
		modalContent.add( panel );
		modalContent.add( serverSuccessMsg );
		
		initWidget(modalContent);
	}
	
	public void hideForm() {
		this.panel.setVisible( false );
	}
	
	public void showForm() {
		this.panel.setVisible( true );
	}
	
	public void setEnable( boolean enabled ) {
		currentPassword.setEnabled( enabled );
		newPassword.setEnabled( enabled );
		confirmPassword.setEnabled( enabled );
		changePasswordButton.setEnabled( enabled );
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
	
	public boolean validateNewPassword(){
		if(newPassword.getText().isEmpty()){
			newPassword.addStyleName("textBoxError");
			setPasswordError("Enter New Password");
			newPasswordError.setVisible(true);
			return false;
		}
		else if( validateForm.ValidatePassword( getPassword() ) ){
			newPassword.addStyleName("textBoxError");
			setPasswordError("Password should be atleast 6 characters long");
			newPasswordError.setVisible(true);
			return false;
		}
		else{
			newPassword.removeStyleName("textBoxError");
			newPasswordError.setVisible( false );
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
		return currentPassword.getText().isEmpty() ? null : currentPassword.getText();
	}

	public String getPassword(){
		return newPassword.getText().trim().isEmpty() ? null : newPassword.getText().trim();
	}
	
	//set error message	
	public void setCurrentPasswordError(String msg){
		currentPasswordError.setText(msg);
	}

	public void setPasswordError(String msg){
		newPasswordError.setText(msg);
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
	
	public void hideServerError(){
		this.serverError.setVisible( false );
	}
	
	public void setServerSuccess( String message ) {
		HTML serverMessage = new HTML();
		serverMessage.setHTML( message );
		this.serverSuccessMsg.getElement().removeAllChildren();
		this.serverSuccessMsg.getElement().appendChild( serverMessage.getElement() );
	}
	
	public void showServerSuccess(){
		this.serverSuccessMsg.setVisible( true );
	}
	
	public void hideServerSuccess(){
		this.serverSuccessMsg.setVisible( false );
	}
}
