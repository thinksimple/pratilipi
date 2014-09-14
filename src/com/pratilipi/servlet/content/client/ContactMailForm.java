package com.pratilipi.servlet.content.client;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

public class ContactMailForm extends Composite {
	private TextBox nameInput = new TextBox();
	private TextBox emailInput = new TextBox();
	private TextArea mailBody = new TextArea();
	private Button sendMail = new Button( "Send" );
	
	private ValidateForm validateForm = new ValidateForm();
	
	private InlineLabel emailInputError = new InlineLabel();
	private InlineLabel nameInputError = new InlineLabel();
	private InlineLabel mailBodyError = new InlineLabel();
	
	public ContactMailForm(){
		Panel contactMailForm = new FlowPanel();
		
		nameInput.addStyleName( "form-control" );
		nameInput.getElement().setAttribute("placeholder", "Name");
		nameInput.addBlurHandler( new BlurHandler() {

			@Override
			public void onBlur(BlurEvent event) {
				validateName();
			}});
		
		emailInput.addStyleName( "form-control" );
		emailInput.getElement().setAttribute("placeholder", "Email");
		emailInput.getElement().getStyle().setProperty("marginTop", "15px");
		emailInput.addBlurHandler( new BlurHandler() {

			@Override
			public void onBlur(BlurEvent event) {
				validateEmail();
			}});
		
		mailBody.addStyleName( "form-control" );
		mailBody.getElement().setAttribute("placeholder", "Enter Your Query");
		mailBody.getElement().getStyle().setProperty("marginTop", "15px");
		mailBody.setVisibleLines( 5 );
		mailBody.addBlurHandler( new BlurHandler() {

			@Override
			public void onBlur(BlurEvent event) {
				validateBody();
			}});
		
		sendMail.addStyleName( "btn btn-md btn-success" );
		sendMail.getElement().getStyle().setProperty("marginTop", "15px");
		
		//Error messages
		emailInputError.addStyleName( "errorMessage" );
		nameInputError.addStyleName( "errorMessage" );
		mailBodyError.addStyleName( "errorMessage" );
		
		contactMailForm.add( nameInput );
		contactMailForm.add( nameInputError );
		contactMailForm.add( emailInput );
		contactMailForm.add( emailInputError );
		contactMailForm.add( mailBody );
		contactMailForm.add( mailBodyError );
		contactMailForm.add( sendMail );
		
		initWidget( contactMailForm );
	}
	
	public void addSendButtonClickHandler( ClickHandler clickHandler ) {
		sendMail.addClickHandler( clickHandler );
	}
	
	
	public boolean validateEmail(){
		if( emailInput.getText().isEmpty() ){
			emailInput.addStyleName("textBoxError");
			setEmailInputError("Enter your email");
			showEmailInputError();
			return false;
		}
		else if( validateForm.ValidateEmail( emailInput.getText() ) ){
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
	
	public boolean validateName(){
		if( nameInput.getText().isEmpty() ){
			nameInput.addStyleName( "textBoxError" );
			setSubjectInputError("Enter subject of mail");
			showSubjectInputError();
			return false;
		}
		else{
			nameInput.removeStyleName( "textBoxError" );
			hideSubjectInputError();
			return true;
		}
	}
	
	public boolean validateBody(){
		if( mailBody.getText().isEmpty() ){
			mailBody.addStyleName( "textBoxError" );
			setBodyError("Enter Your Name");
			showBodyError();
			return false;
		}
		else{
			mailBody.removeStyleName( "textBoxError" );
			hideBodyError();
			return true;
		}
	}
	
	public void setEmailInputError(String error){
		emailInputError.setText( error );
	}
	
	public void showEmailInputError(){
		emailInputError.setVisible( true );
	}
	
	public void hideEmailInputError(){
		emailInputError.setVisible( true );
	}
	
	public void setSubjectInputError(String error){
		nameInputError.setText( error );
	}
	
	public void showSubjectInputError(){
		nameInputError.setVisible( true );
	}
	
	public void hideSubjectInputError(){
		nameInputError.setVisible( true );
	}
	
	public void setBodyError(String error){
		mailBodyError.setText( error );
	}
	
	public void showBodyError(){
		mailBodyError.setVisible( true );
	}
	
	public void hideBodyError(){
		mailBodyError.setVisible( true );
	}
}
