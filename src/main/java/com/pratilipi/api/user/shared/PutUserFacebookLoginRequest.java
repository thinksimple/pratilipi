package com.pratilipi.api.user.shared;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.common.type.Gender;

public class PutUserFacebookLoginRequest extends GenericRequest {
	
	private static final Logger logger =
			Logger.getLogger( GenericApi.class.getName() );

	
	@Validate( required = true )
	private String fbUserId; 
	
	@Validate( required = true )
	private String fbUserAccessToken;

	
	private String firstName;
	
	private String lastName;
	
	
	private Gender gender;
	
	private String dateOfBirth;

	
	@Validate( regEx = REGEX_EMAIL )
	private String email;

	
	
	public String getFbUserId() {
		return this.fbUserId;
	}

	public String getFbUserAccessToken() {
		return this.fbUserAccessToken;
	}

	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public Gender getGender() {
		return this.gender;
	}

	public Date getDateOfBirth() {
		DateFormat dateFormat = new SimpleDateFormat( "MM/dd/yyyy" ); 
		
		if( this.dateOfBirth != null ) {
			try {
				return dateFormat.parse( this.dateOfBirth );
			} catch( ParseException e ) {
				logger.log( Level.SEVERE, "Failed to parse Date of Birth.", e );
			}
		}
		
		return null;
	}
	
	public String getEmail() {
		return this.email;
	}
	
}
