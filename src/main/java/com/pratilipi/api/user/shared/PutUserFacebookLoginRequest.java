package com.pratilipi.api.user.shared;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.pratilipi.api.shared.GenericRequest;

public class PutUserFacebookLoginRequest extends GenericRequest {
	
	private String userId; 
	
	private String firstName;
	
	private String lastName;
	
	private String emailId;
	
	private String gender;
	
	private String birthday;
	
	private Date birthdayDate;
	
	private String accessToken;
	
	public PutUserFacebookLoginRequest() {}
	
	public String getUserId() {
		return this.userId;
	}
	
	public void setUserId( String id ) {
		this.userId = id.trim();
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public String getFullName() {
		if( this.lastName != null )
			return ( this.firstName + " " + this.lastName ).trim();
		else
			return this.firstName;
	}
	
	public void SetName( String firstName, String lastName ) {
		this.firstName = firstName.trim();
		this.lastName = lastName.trim();
	}
	
	public void setName( String name ) {
		name = name.trim();
		if ( name.contains( " " ) ) {
			this.firstName = name.substring( 0, name.indexOf( ' ' ) );
			this.lastName = name.substring( name.indexOf( ' ' ) + 1 );
		}
		else {
			this.firstName = name;
			this.lastName = null;
		}	 
	}
	
	public String getEmailId() {
		return this.emailId;
	}
	
	public void setEmailId( String emailId ) {
		this.emailId = emailId.trim();
	}
	
	public String getGender() {
		return this.gender;
	}
	
	public void setGender( String gender ) {
		this.gender = gender.trim();
	}
	
	public String getBirthday() {
		return this.birthday;
	}
	
	public Date getBirthdayDate() {
		if( this.birthdayDate != null )
			return this.birthdayDate;
		
		// String to Date format.
		DateFormat dateFormat = new SimpleDateFormat( "MM/dd/yyyy" ); 
		if( this.birthday != null) {
			try {
				this.birthdayDate = dateFormat.parse( this.birthday );
			} catch ( ParseException e ) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return this.birthdayDate;
		}
        return null;
	}
	
	public void setBirthday( String birthday ) {
		this.birthday = birthday;
		// String to Date format.
		DateFormat dateFormat = new SimpleDateFormat( "MM/dd/yyyy" ); 
        try {
			this.birthdayDate = dateFormat.parse( birthday );
		} catch ( ParseException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	public String getAccessToken() {
		return this.accessToken;
	}
	
}
