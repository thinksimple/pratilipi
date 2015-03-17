package com.pratilipi.pagecontent.author.api.shared;

import com.claymus.api.annotation.Validate;
import com.claymus.api.shared.GenericRequest;

@SuppressWarnings( "serial" )
public class PutSaveAuthorRequest extends GenericRequest {
	
	@Validate( required=true )
	private String firstNameEn;
	
	private String lastNameEn;
	
	private String email;
	
	public String getFirstNameEn(){
		return this.firstNameEn;
	}
	
	public String getLastNameEn(){
		return this.lastNameEn;
	}
	
	public String getEmail(){
		return this.email;
	}
}
