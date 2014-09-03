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
import com.google.gwt.user.client.ui.TextBox;

public class ForgotPasswordForm extends Composite {
	
	private Label heading = new Label();
	private TextBox emailInput = new TextBox();
	private Label emailInputError = new Label();
	private Button genPasswdButton = new Button( "Generate Password" );
	private Label serverError = new Label();
	
	private ValidateForm validateForm = new ValidateForm();
	
	public ForgotPasswordForm(){
		Panel modalContent = new FlowPanel();
		modalContent.setStyleName( "modal-content" );
		
		Panel form = new FlowPanel();
		form.setStyleName( "modal-body" );
		form.getElement().setId( "forgotPasswordForm" );
		
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
		
		//password generate button. 
		genPasswdButton.getElement().getStyle().setDisplay( Display.INLINE_BLOCK );
		genPasswdButton.addStyleName( "col-xs-1" );
		genPasswdButton.setStyleName( "btn btn-primary btn-block" );
		genPasswdButton.addStyleName( "generatePasswordButton" );
		
		//Error message formating
		emailInputError.setStyleName( "errorMessage" );
		emailInputError.setVisible( false );
		
		form.add( heading );
		form.add( serverError );
		form.add( emailInput );
		form.add( genPasswdButton );
		form.add( emailInputError );
		
		modalContent.add( form );
		
		initWidget( modalContent );
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
	
	public void setServerError( String error ){
		HTML msg = new HTML();
		msg.setHTML( error );
		this.serverError.getElement().appendChild( msg.getElement() );
	}
	
	public void showServerError(){
		this.serverError.setVisible( true );
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
		else{
			emailInput.removeStyleName("textBoxError");
			hideEmailInputError();
			return true;
		}
	}

}

