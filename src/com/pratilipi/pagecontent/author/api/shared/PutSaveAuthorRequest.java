package com.pratilipi.pagecontent.author.api.shared;

import com.claymus.api.annotation.Validate;
import com.claymus.api.shared.GenericRequest;

@SuppressWarnings( "serial" )
public class PutSaveAuthorRequest extends GenericRequest {
	
	private Long id;
	
	private Long languageId;
	private boolean hasLanguageId;
	
	private String firstName;
	private boolean hasFirstName;
	
	private String lastName;
	private boolean hasLastName;
	
	private String penName;
	private boolean hasPenName;
	
	@Validate( required=true )
	private String firstNameEn;
	private boolean hasFirstNameEn;
	
	private String lastNameEn;
	private boolean hasLastNameEn;
	
	private String penNameEn;
	private boolean hasPenNameEn;
	
	private String email;
	private boolean hasEmail;
	
	public Long getId(){
		return this.id;
	}
	
	public Long getLanguageId(){
		return this.languageId;
	}
	
	public boolean hasLanguageId(){
		return this.hasLanguageId;
	}
	
	public String getFirstName(){
		return this.firstName;
	}
	
	public boolean hasFirstName(){
		return this.hasFirstName;
	}
	
	public String getLastName(){
		return this.lastName;
	}
	
	public boolean hasLastName(){
		return this.hasLastName;
	}
	
	public String getPenName(){
		return this.penName;
	}
	
	public boolean hasPenName(){
		return this.hasPenName;
	}
	
	public String getFirstNameEn(){
		return this.firstNameEn;
	}
	
	public boolean hasFirstNameEn(){
		return this.hasFirstNameEn;
	}
	
	public String getLastNameEn(){
		return this.lastNameEn;
	}
	
	public boolean hasLastNameEn(){
		return this.hasLastNameEn;
	}
	
	public String getPenNameEn(){
		return this.penNameEn;
	}
	
	public boolean hasPenNameEn(){
		return this.hasPenNameEn;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public boolean hasEmail(){
		return this.hasEmail;
	}
}
