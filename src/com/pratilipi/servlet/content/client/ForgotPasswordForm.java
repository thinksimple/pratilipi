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
import com.google.gwt.user.client.ui.TextBox;

public class ForgotPasswordForm extends Composite {
	
	private Panel modalContent = new FlowPanel();
	private Panel form = new FlowPanel();
	
	private Label heading = new Label();
	private TextBox emailInput = new TextBox();
	private Label emailInputError = new Label();
	private Button genPasswdButton = new Button( "Generate Password" );
	private Label serverError = new Label();
	private Label serverSuccess = new Label();
	
	private ValidateForm validateForm = new ValidateForm();
	
	public ForgotPasswordForm(){
		
		this.form.getElement().setId( "forgotPasswordForm" );
		
		//Modal heading
		HTML headingElement= new HTML();
		headingElement.setHTML( "<h3>Password Reset</h3>" );
		headingElement.addStyleName( "page-header" );
		
		HTML msgElement = new HTML();
		msgElement.setHTML("<p>To reset your password, enter the email address you use to sign in to Pratilipi. Click on the link in email to change your password.</p>");
		
		heading.getElement().appendChild( headingElement.getElement() );
		heading.getElement().appendChild( msgElement.getElement() );
		
		serverError.addStyleName( "alert alert-danger" );
		serverError.getElement().setAttribute( "role", "alert") ;
		serverError.setVisible( false );
		
		serverSuccess.addStyleName( "alert-success" );
		serverSuccess.getElement().getStyle().setPadding( 15, Unit.PX );
		serverSuccess.getElement().setAttribute( "role", "alert" );
		serverSuccess.setVisible( false );
		
		//text box formatting
		emailInput.getElement().setAttribute("placeholder", "Enter registered email");
		emailInput.setStyleName( "form-control" );
		emailInput.addStyleName( "emailTextBox" );
		emailInput.addBlurHandler(new BlurHandler(){

			@Override
			public void onBlur(BlurEvent event) {
				if( emailInput.getText().isEmpty() ){
					emailInput.addStyleName("textBoxError");
					setEmailInputError("Enter registered email");
					showEmailInputError();
				}
				else if( validateForm.ValidateEmail( emailInput.getText() ) ){
					emailInput.addStyleName("textBoxError");
					setEmailInputError("Email not in proper format");
					showEmailInputError();
				}
				else{
					emailInput.removeStyleName("textBoxError");
					hideEmailInputError();
				}
			}});
		emailInput.addFocusHandler( new FocusHandler() {

			@Override
			public void onFocus(FocusEvent event) {
				emailInputError.setVisible( false );
			}});
		//password generate button. 
		genPasswdButton.getElement().getStyle().setDisplay( Display.INLINE_BLOCK );
		genPasswdButton.addStyleName( "col-xs-1" );
		genPasswdButton.setStyleName( "btn btn-primary btn-block" );
		genPasswdButton.addStyleName( "generatePasswordButton" );
		
		//Error message formating
		emailInputError.setStyleName( "errorMessage" );
		emailInputError.setVisible( false );
		
		this.form.add( heading );
		this.form.add( serverError );
		this.form.add( emailInput );
		this.form.add( genPasswdButton );
		this.form.add( emailInputError );
		
		modalContent.add( form );
		modalContent.add( serverSuccess );
		
		initWidget( modalContent );
	}
	
	public void showForm() {
		this.form.setVisible( true );
	}
	
	public void hideForm() {
		this.form.setVisible( false );
	}
	
	public void setEmailInputError( String error ){
		emailInputError.setText( error );
	}
	
	public void showEmailInputError(){
		emailInputError.setVisible(true);
	}
	
	public void hideEmailInputError(){
		emailInputError.setVisible(false);
	}
	
	public String getEmail(){
		return emailInput.getText();
	}
	
	//Setting server messages.
	public void setServerError( String error ){
		HTML msg = new HTML();
		msg.setHTML( error );
		this.serverError.getElement().removeAllChildren();
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
		this.serverError.setVisible( false );
	}
	
	public void showServerSuccess(){
		this.serverSuccess.setVisible( true );
	}
	
	public void hideServerSuccess(){
		this.serverSuccess.setVisible( false );
	}
	
	//Button ClickHandler
	public void addGenPasswdButtonClickHandler( ClickHandler clickHandler ) {
		genPasswdButton.addClickHandler( clickHandler );
	}
	
	//Validate Email function
	public boolean validateEmail(){
		if( getEmail().isEmpty() ){
			emailInput.addStyleName("textBoxError");
			setEmailInputError("Enter registered email");
			showEmailInputError();
			return false;
		}
		else if( validateForm.ValidateEmail( getEmail() ) ){
			emailInput.addStyleName("textBoxError");
			setEmailInputError("Email not in proper format");
			showEmailInputError();
			return false;
		}
		else{
			emailInput.removeStyleName("textBoxError");
			hideEmailInputError();
			return true;
		}
		
	}

	public void setEnable( boolean enabled ) {
		emailInput.setEnabled( enabled );
		genPasswdButton.setEnabled( enabled );
	}
}

