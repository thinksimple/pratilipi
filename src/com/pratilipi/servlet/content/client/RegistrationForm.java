package com.pratilipi.servlet.content.client;

import com.claymus.service.shared.data.UserData;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;

public class RegistrationForm extends Composite {
	
	private Panel modalContent = new FlowPanel();
	private Panel panel = new FlowPanel();
	
	private TextBox firstNameInput = new TextBox();
	private TextBox lastNameInput = new TextBox();
	private TextBox emailInput = new TextBox();
	private PasswordTextBox password = new PasswordTextBox();
	private PasswordTextBox confirmPassword = new PasswordTextBox();
	private Button registerButton = new Button("Sign up");
	private Label heading = new Label();
	private Label signin = new Label( "Already a member?" );
	
	final ValidateForm validateForm = new ValidateForm();
	
	//Error messages
	private Label serverError = new Label();
	private Label serverSuccess = new Label();
	private Label nameInputError = new Label();
	private Label emailInputError = new Label();
	private Label passwordError = new Label();
	private Label confPassError = new Label();
	
	
	public RegistrationForm(){
		
		modalContent.setStyleName( "modal-content" );
		
		panel.setStyleName( "modal-body" );
		
		FlowPanel namePanel = new FlowPanel();
		
		HTML headingElement= new HTML();
		headingElement.setHTML("<h3>Sign Up</h3>");
		
		heading.addStyleName( "page-header" );
		heading.getElement().appendChild( headingElement.getElement() );
		
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
		firstNameInput.addFocusHandler( new FocusHandler() {

			@Override
			public void onFocus(FocusEvent event) {
				nameInputError.setVisible( false );
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
		lastNameInput.addFocusHandler( new FocusHandler() {

			@Override
			public void onFocus(FocusEvent event) {
				if( validateFirstName() ) 
					nameInputError.setVisible( false );
			}});
		
		
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
				validatePassword();
			}});
		password.addFocusHandler( new FocusHandler() {

			@Override
			public void onFocus(FocusEvent event) {
				passwordError.setVisible( false );
				
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
		
		registerButton.addStyleName("btn btn-lg");
		registerButton.addStyleName("btn-primary");
		registerButton.addStyleName("btn-block");
		registerButton.getElement().getStyle().setDisplay(Display.BLOCK);
		
		serverSuccess.addStyleName( "alert-success" );
		serverSuccess.getElement().getStyle().setPadding( 15, Unit.PX );
		serverSuccess.getElement().setAttribute( "role", "alert" );
		serverSuccess.setVisible( false );
		
		//Error message Style
		serverError.addStyleName( "alert alert-danger" );
		serverError.getElement().setAttribute( "role", "alert") ;
		nameInputError.setStyleName("errorMessage");
		emailInputError.setStyleName("errorMessage");
		passwordError.setStyleName("errorMessage");
		confPassError.setStyleName("errorMessage");
		
		//Sign in link
		signin.setStyleName( "modal-link" );
		
		//New user link in login form
		Anchor signinAnchor = new Anchor( "Sign In" );
		signinAnchor.setHref( "#signin" );
		signin.getElement().appendChild( signinAnchor.getElement() );
		
		//All error messages are invisible when page is loaded for first time.
		serverError.setVisible( false );
		nameInputError.setVisible(false);
		emailInputError.setVisible(false);
		passwordError.setVisible(false);
		confPassError.setVisible(false);
		
		namePanel.add(firstNameInput);
		namePanel.add(lastNameInput);
		panel.add( heading );
		panel.add( serverError );
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
		panel.add( signin );
		
		modalContent.add( panel );
		modalContent.add( serverSuccess );
		
		initWidget(modalContent);
	}
	
	public UserData getUser() {
		UserData userData = new UserData();
		if( firstNameInput.getText().trim().length() != 0 )
			userData.setFirstName( firstNameInput.getText().trim() );
		if( lastNameInput.getText().trim().length() != 0 )
			userData.setLastName( lastNameInput.getText().trim() );
		userData.setPassword( password.getText());
		userData.setEmail( emailInput.getText().trim() );
		userData.setCampaign( "PreLaunch" );
		userData.setReferer( Window.Location.getParameter( "ref" ) );
		
		return userData;
	}
	
	//Clickhandler  functions
	public void addRegisterButtonClickHandler( ClickHandler clickHandler ) {
		registerButton.addClickHandler( clickHandler );
	}
	
	public void setEnable( boolean enabled ) {
		firstNameInput.setEnabled( enabled );
		lastNameInput.setEnabled( enabled );
		emailInput.setEnabled( enabled );
		password.setEnabled( enabled );
		confirmPassword.setEnabled( enabled );
	}
	
	public void showForm() {
		this.panel.setVisible( true );
	}
	
	public void hideForm() {
		this.panel.setVisible( false );
	}
	
	
	//Form validation functions
	public boolean validateFirstName(){
		if( validateForm.ValidateFirstName( getFirstName() ) ){
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
			if( validateFirstName() )
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
			setconfPassError("Please confirm your password");
			showConfPassError();
			return false;
		}
		else if( validateForm.validateConfirmPassword( getPassword(), getConfPassword() ) ){
			setConfPasswordErrorStyle();
			setconfPassError("These passwords don't match. Please try again.");
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
	
	//Server Error function
	public void setServerError( String error ){
		this.serverError.getElement().removeAllChildren();
		HTML msg = new HTML();
		msg.setHTML( error );
		this.serverError.getElement().appendChild( msg.getElement() );
	}
	
	public void setServerSuccess( String message ) {
		HTML msg = new HTML();
		msg.setHTML( message );
		this.serverSuccess.getElement().removeAllChildren();
		this.serverSuccess.getElement().appendChild( msg.getElement() );
	}
	
	public void showServerError(){
		this.serverError.setVisible( true );
	}
	
	public void hideServerError(){
		serverError.setVisible( false );
	}

	public void showServerSuccess(){
		this.serverSuccess.setVisible( true );
	}
	
	public void hideServerSuccess(){
		this.serverSuccess.setVisible( false );
	}
}
