package com.pratilipi.api.impl.author.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;

public class PutAuthorRequest extends GenericRequest {
	
	private Long authorId;
	
	private Long languageId;
	private boolean hasLanguageId;
	
	private String firstName;
	private boolean hasFirstName;
	
	private String lastName;
	private boolean hasLastName;
	
	private String penName;
	private boolean hasPenName;
	
	@Validate( required = true )
	private String firstNameEn;
	private boolean hasFirstNameEn;
	
	private String lastNameEn;
	private boolean hasLastNameEn;
	
	private String penNameEn;
	private boolean hasPenNameEn;
	
	@Validate( regEx = REGEX_EMAIL )
	private String email;
	private boolean hasEmail;

	
	public Long getId() {
		return authorId;
	}
	

	public Long getLanguageId() {
		return languageId;
	}
	
	public boolean hasLanguageId() {
		return hasLanguageId;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public boolean hasFirstName() {
		return hasFirstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public boolean hasLastName() {
		return hasLastName;
	}
	
	public String getPenName() {
		return penName;
	}
	
	public boolean hasPenName() {
		return hasPenName;
	}
	
	public String getFirstNameEn() {
		return firstNameEn;
	}
	
	public boolean hasFirstNameEn() {
		return hasFirstNameEn;
	}
	
	public String getLastNameEn() {
		return lastNameEn;
	}
	
	public boolean hasLastNameEn() {
		return hasLastNameEn;
	}
	
	public String getPenNameEn() {
		return penNameEn;
	}
	
	public boolean hasPenNameEn() {
		return hasPenNameEn;
	}
	
	public String getEmail() {
		return email;
	}
	
	public boolean hasEmail() {
		return hasEmail;
	}

}
