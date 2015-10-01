package com.pratilipi.data.client;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.pratilipi.common.type.Gender;

public class FacebookUserData {

	private String id;
	
	private String birthday;
	
	private String email;
	
	private String first_name;
	
	private String gender;
	
	private String last_name;
	
	private Boolean verified;
	

	public FacebookUserData() {}
	
	
	public String getFbUserId() {
		return this.id;
	}

	public String getFirstName() {
		return this.first_name;
	}
	
	public String getLastName() {
		return this.last_name;
	}
	
	public Gender getGender() {
		return Gender.valueOf( this.gender.toUpperCase() );
	}

	public Date getDateOfBirth() {
		DateFormat dateFormat = new SimpleDateFormat( "MM/dd/yyyy" ); 
		
		if( this.birthday != null ) {
			try {
				return dateFormat.parse( this.birthday );
			} catch( ParseException e ) {
			}
		}
		
		return null;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public Boolean isVerified() { 
		return this.verified;
	}
	
}
