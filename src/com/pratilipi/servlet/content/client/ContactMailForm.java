package com.pratilipi.servlet.content.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

public class ContactMailForm extends Composite {
	private TextBox emailInput = new TextBox();
	private TextBox subjectInput = new TextBox();
	private InlineLabel bodyLabel = new InlineLabel( "Enter Your Message Below:" );
	private TextArea mailBody = new TextArea();
	private Button sendMail = new Button();
	
	private ValidateForm validateForm = new ValidateForm();
	
	private InlineLabel emailInputError = new InlineLabel();
	private InlineLabel subjectInputError = new InlineLabel();
	private InlineLabel mailBodyError = new InlineLabel();
	
	public ContactMailForm(){
		Panel contactMailForm = new FlowPanel();
		contactMailForm.addStyleName( "form-group" );
		
		emailInput.addStyleName( "form-control contactPage-TextBox" );
		emailInput.getElement().setAttribute("placeholder", "Enter Your Email");
		
		bodyLabel.addStyleName( "contactPage-Label" );
		
		subjectInput.addStyleName( "form-control contactPage-TextBox" );
		subjectInput.getElement().setAttribute("placeholder", "Subject");
		
		mailBody.addStyleName( "form-control contactPage-TextArea" );
		mailBody.setVisibleLines( 5 );
		
		sendMail.addStyleName( "btn btn-info btn-md contactPage-Button" );
		
		//Error messages
		emailInputError.addStyleName( "errorMessage" );
		subjectInputError.addStyleName( "errorMessage" );
		mailBodyError.addStyleName( "errorMessage" );
		
		contactMailForm.add( emailInput );
		contactMailForm.add( subjectInput );
		contactMailForm.add( bodyLabel );
		contactMailForm.add( mailBody );
		
		initWidget( contactMailForm );
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
	
	public boolean validateSubject(){
		if( subjectInput.getText().isEmpty() ){
			subjectInput.addStyleName( "textBoxError" );
			setSubjectInputError("Enter subject of mail");
			showSubjectInputError();
			return false;
		}
		else{
			subjectInput.removeStyleName( "textBoxError" );
			hideSubjectInputError();
			return true;
		}
	}
	
	public boolean validateBody(){
		if( mailBody.getText().isEmpty() ){
			mailBody.addStyleName( "textBoxError" );
			setBodyError("Enter subject of mail");
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
		subjectInputError.setText( error );
	}
	
	public void showSubjectInputError(){
		subjectInputError.setVisible( true );
	}
	
	public void hideSubjectInputError(){
		subjectInputError.setVisible( true );
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
