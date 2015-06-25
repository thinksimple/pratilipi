package com.pratilipi.api.author.shared;

import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.type.Language;

@SuppressWarnings("unused")
public class GetAuthorResponse extends GenericResponse {

	private Long authorId;

	private String firstName;
	private String lastName;
	private String penName;
	private String name;
	private String fullName;

	private String firstNameEn;
	private String lastNameEn;
	private String penNameEn;
	private String nameEn;
	private String fullNameEn;

	private String email;
	private Language language;
	private String summary;
	
	private String pageUrl;
	private String pageUrlAlias;
	private String imageUrl;

	private Long registrationDate;
	
	private Integer contentPublished;

	
	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPenName(String penName) {
		this.penName = penName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	
	public void setFirstNameEn(String firstNameEn) {
		this.firstNameEn = firstNameEn;
	}

	public void setLastNameEn(String lastNameEn) {
		this.lastNameEn = lastNameEn;
	}

	public void setPenNameEn(String penNameEn) {
		this.penNameEn = penNameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public void setFullNameEn(String fullNameEn) {
		this.fullNameEn = fullNameEn;
	}

	
	public void setEmail(String email) {
		this.email = email;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public void setPageUrlAlias(String pageUrlAlias) {
		this.pageUrlAlias = pageUrlAlias;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	
	public void setRegistrationDate(Long registrationDate) {
		this.registrationDate = registrationDate;
	}

	
	public void setContentPublished(Integer contentPublished) {
		this.contentPublished = contentPublished;
	}
	
}