package com.pratilipi.module.pagecontent.manageauthors.client;

import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;

public class ValidateAuthor {
	private ManageAuthorsView author = new ManageAuthorsViewImpl();
	
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
	
	public ValidateAuthor(ManageAuthorsView author){
		this.author = author;
	}
	
	public boolean validateAuthor(){
		return validateFirstName() 
			 & validateLastName() 
			 //& validatePenName() 
			 & validateEmail();
	}
	
	public boolean validateFirstName(){
		this.matcher = charExp.exec(author.getFirstName());
		if(!(this.matcher!=null)){
			author.setFirstNameErrorStyle();
			this.validated = this.validated & false;
		}
		else{
			author.setFirstNameAcceptStyle();
		}
		return this.validated;
	}
	
	public boolean validateLastName(){
		this.matcher = wordExp.exec(author.getLastName());
		if(!(this.matcher!=null)){
			author.setLastNameErrorStyle();
			this.validated = this.validated & false;
		}
		else{
			author.setLastNameAcceptStyle();
		}
		return this.validated;
	}
	
	/*public boolean validatePenName(){
		this.matcher = charExp.exec(author.getPenName());
		if(!(this.matcher!=null)){
			author.setPenNameErrorStyle();
			this.validated = this.validated & false;
		}
		else{
			author.setPenNameAcceptStyle();
		}
		return this.validated;
	}*/
	
	public boolean validateEmail(){
		this.matcher = emailExp.exec(author.getEmail());
		if(!(this.matcher!=null)){
			author.setEmailErrorStyle();
			this.validated = this.validated & false;
		}
		else{
			author.setEmailAcceptStyle();
		}
		return this.validated;
	}

}
