package com.pratilipi.servlet.content.client;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.TextAlign;
import com.google.gwt.dom.client.Style.Unit;
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
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;

public class LoginForm extends Composite {
	
	private Label heading = new Label();
	private Button fbLoginAnchor = new Button();
	private Image fbLoginImage = new Image( "/theme.pratilipi/images/fbLogin.png" );
	private Label orLabel = new Label();
	private InlineLabel inlineLabel = new InlineLabel( "OR" );
	private TextBox emailInput = new TextBox();
	private PasswordTextBox password = new PasswordTextBox();
	private Button loginButton = new Button("Sign In");
	private Label formLinksLabel = new Label();
	private Anchor forgotPasswdAnchor = new Anchor( "Forgot Password?" );
	private Anchor newUserAnchor = new Anchor( "Not a member?" );
	
	final ValidateForm validateForm = new ValidateForm();
	
	//Error messages
	private Label serverError = new Label();
	private Label emailInputError = new Label();
	private Label passwordError = new Label();
	
	
	public LoginForm(){
		
		Panel panel = new FlowPanel();
		
		Panel fields = new FlowPanel();
		Panel button = new FlowPanel();
		button.setStyleName( "buttonPanel" );
		

		HTML headingElement= new HTML();
		headingElement.setHTML("<h3>Welcome Back</h3>");
		
		fbLoginAnchor.addStyleName("btn btn-block");
		fbLoginAnchor.getElement().getStyle().setPadding( 0, Unit.PX );
		fbLoginAnchor.getElement().getStyle().setBackgroundColor( "#4F6AA2" );
		fbLoginAnchor.getElement().appendChild( fbLoginImage.getElement() );
		
		
		orLabel.setStyleName( "clearfix" );
		orLabel.getElement().appendChild( inlineLabel.getElement() );
		orLabel.getElement().getStyle().setMargin( 15, Unit.PX );
		orLabel.getElement().getStyle().setTextAlign( TextAlign.CENTER );
		inlineLabel.setStyleName( "hr-line-through" );
		
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
		
		formLinksLabel.setStyleName( "modal-link" );
		
		//New user link in login form
		newUserAnchor.getElement().setAttribute("data-toggle", "modal");
		newUserAnchor.getElement().setAttribute("data-target", "#signupModal");
		formLinksLabel.getElement().appendChild( newUserAnchor.getElement() );
		
		//forgot password link
		forgotPasswdAnchor.getElement().setAttribute("data-toggle", "modal");
		forgotPasswdAnchor.getElement().setAttribute("data-target", "#forgotPasswordModal");
		formLinksLabel.getElement().appendChild( forgotPasswdAnchor.getElement() );
		
		//Error message Style
		serverError.addStyleName( "alert alert-danger" );
		serverError.getElement().setAttribute( "role", "alert") ;
		emailInputError.setStyleName( "errorMessage" );
		passwordError.setStyleName( "errorMessage" );
		
		//All error messages are invisible when page is loaded for first time.
		emailInputError.setVisible(false);
		passwordError.setVisible(false);
		serverError.setVisible( false );
		
		fields.add( heading );
		fields.add( fbLoginAnchor );
		fields.add( orLabel );
		fields.add( serverError );
		fields.add( emailInput );
		fields.add( emailInputError );
		fields.add( password );
		fields.add( passwordError );
		
		button.add( loginButton );		
		button.add( formLinksLabel );
		
		panel.add( fields );
		panel.add( button );
		
		initWidget( panel );
	}
	
	//Click handler functions
	public void addLoginButtonClickHandler( ClickHandler clickHandler ) {
		loginButton.addClickHandler( clickHandler );
	}
	
	public void addFormLinksClickHandler( ClickHandler clickHandler ) {
		formLinksLabel.addClickHandler( clickHandler );
	}
	
	public void addFbLoginClickHandler( ClickHandler clickHandler ) {
		this.fbLoginAnchor.addClickHandler( clickHandler );
	}
	
	//Enable and Disable login form
	public void setEnabled( boolean enabled ) {
		emailInput.setEnabled( enabled );
		password.setEnabled( enabled );
		loginButton.setEnabled( enabled );
		fbLoginAnchor.setEnabled( enabled );
	}
	
	//Login validation functions
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