package com.pratilipi.servlet.content.client;

import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;

public class ValidateForm {
	
	//Regular expression for string containing characters and whitespace.
	private String charPattern = new String("^[a-zA-Z\\s]+$");
	private RegExp charExp = RegExp.compile(charPattern);
	
	private String lastNamePattern = new String("^[a-zA-Z]*$");
	private RegExp lastNameExp = RegExp.compile(lastNamePattern);
	
	//Regular expression for string containing characters and whitespace.
	private String emailPattern = new String("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$");
	private RegExp emailExp = RegExp.compile(emailPattern);
	
	public ValidateForm(){}
	
	public boolean ValidateFirstName(String firstName){
		MatchResult matcher = charExp.exec( firstName );
		Boolean matchFound = (matcher!=null);
		if(!matchFound)
			return true;
		else
			return false;
	}
	
	public boolean ValidateLastName( String lastName ){
		MatchResult matcher = lastNameExp.exec( lastName );
		Boolean matchFound = (matcher!=null);
		if(!matchFound)
			return true;
		else
			return false;
	}
	
	public boolean ValidateEmail(String email){
		MatchResult matcher = emailExp.exec( email );
		Boolean matchFound = (matcher!=null);
		if(!matchFound){
			return true;
		}
		else
			return false;
	}
	
	public boolean ValidatePassword(String password){
		if(password.length()<6){
			return true;
		}
		else
			return false;
	}
	
	public boolean validateConfirmPassword( String password, String confPass ){
		if(!(confPass.equals( password )))
			return true;
		else
			return false;
	}

}

