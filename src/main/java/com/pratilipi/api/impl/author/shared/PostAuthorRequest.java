package com.pratilipi.api.impl.author.shared;

import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.common.type.Gender;
import com.pratilipi.common.type.Language;

public class PostAuthorRequest extends GenericRequest {
	
	private Long authorId;
	
	private String firstName;
	private boolean hasFirstName;
	
	private String lastName;
	private boolean hasLastName;
	
	private String penName;
	private boolean hasPenName;
	
	private String firstNameEn;
	private boolean hasFirstNameEn;
	
	private String lastNameEn;
	private boolean hasLastNameEn;
	
	private String penNameEn;
	private boolean hasPenNameEn;
	

	private Gender gender;
	private boolean hasGender;
	
	private String dateOfBirth;
	private boolean hasDateOfBirth;

	
	private Language language;
	private boolean hasLanguage;
	
	private String summary;
	private boolean hasSummary;
	

	
	public Long getId() {
		return authorId;
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
	
	
	public Gender getGender() {
		return gender;
	}
	
	public boolean hasGender() {
		return hasGender;
	}
	
	public String getDateOfBirthStr() {
		return dateOfBirth;
	}
	
	public boolean hasDateOfBirth() {
		return hasDateOfBirth;
	}
	
	
	public Language getLanguage() {
		return language;
	}

	public boolean hasLanguage() {
		return hasLanguage;
	}
	
	public String getSummary() {
		return summary;
	}

	public boolean hasSummary() {
		return hasSummary;
	}
	
}
