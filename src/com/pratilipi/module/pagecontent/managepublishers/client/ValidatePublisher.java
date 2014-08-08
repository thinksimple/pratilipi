package com.pratilipi.module.pagecontent.managepublishers.client;

import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;

public class ValidatePublisher {
	private ManagePublishersView publisher = new ManagePublishersViewImpl();
	
	//Regular expression for string containing characters and whitespace.
	private String charPattern = new String("^\\w+( \\w+)*$");
	private RegExp charExp = RegExp.compile(charPattern);
	
	//Regular expression for a word.
	private String wordPattern = new String("^\\w*$");
	private RegExp wordExp = RegExp.compile(wordPattern);
		
	//Regular expression for string containing characters and whitespace.
	private String emailPattern = new String("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$");
	private RegExp emailExp = RegExp.compile(emailPattern);
		
	private MatchResult matcher;
		
	//container of validation result.
	private Boolean validated = true;
	
	public ValidatePublisher(ManagePublishersView publisher){
		this.publisher = publisher;
	}
	
	public boolean validatePublisher(){
		return validateName() 
			 & validateEmail();
	}
	
	public boolean validateName(){
		//this.matcher = charExp.exec(author.getName());
		//if(!(this.matcher!=null)){
		if(publisher.getName().isEmpty()){
			publisher.setNameErrorStyle();
			this.validated = this.validated & false;
		}
		else{
			publisher.setNameAcceptStyle();
		}
		return this.validated;
	}
	
	public boolean validateEmail(){
		this.matcher = emailExp.exec(publisher.getEmail());
		if(!(this.matcher!=null)){
			publisher.setEmailErrorStyle();
			this.validated = this.validated & false;
		}
		else{
			publisher.setEmailAcceptStyle();
		}
		return this.validated;
	}

}
