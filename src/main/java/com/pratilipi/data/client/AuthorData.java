package com.pratilipi.data.client;

import java.util.Date;

public class AuthorData {

	private Long id;

	private String firstName;
	private transient boolean hasFirstName;
	
	private String lastName;
	private transient boolean hasLastName;
	
	private String penName;
	private transient boolean hasPenName;

	private String name;

	private String fullName;

	
	private String firstNameEn;
	private transient boolean hasFirstNameEn;
	
	private String lastNameEn;
	private transient boolean hasLastNameEn;
	
	private String penNameEn;
	private transient boolean hasPenNameEn;
	
	private String nameEn;

	private String fullNameEn;


	private String email;
	private transient boolean hasEmail;

	private String langCode;
	private transient boolean hasLangCode;
	
	private String summary;
	private transient boolean hasSummary;
	

	private String pageUrl;
	private String pageUrlAlias;
	private String imageUrl;

	
	private Long registrationDate;
	
	private Integer contentPublished;
	
	
	public Long getId() {
		return id;
	}

	public void setId( Long id ) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}

	public void setFirstName( String firstName ) {
		this.firstName = firstName;
		this.hasFirstName = true;
	}

	public boolean hasFirstName() {
		return hasFirstName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName( String lastName ) {
		this.lastName = lastName;
		this.hasLastName = true;
	}
	
	public boolean hasLastName() {
		return hasLastName;
	}

	public String getPenName() {
		return penName;
	}

	public void setPenName( String penName ) {
		this.penName = penName;
		this.hasPenName = true;
	}
	
	public boolean hasPenName() {
		return hasPenName;
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName( String fullName ) {
		this.fullName = fullName;
	}

	public String getFirstNameEn() {
		return firstNameEn;
	}

	public void setFirstNameEn( String firstNameEn ) {
		this.firstNameEn = firstNameEn;
		this.hasFirstNameEn = true;
	}
	
	public boolean hasFirstNameEn() {
		return hasFirstNameEn;
	}

	public String getLastNameEn() {
		return lastNameEn;
	}

	public void setLastNameEn( String lastNameEn ) {
		this.lastNameEn = lastNameEn;
		this.hasLastNameEn = true;
	}
	
	public boolean hasLastNameEn() {
		return hasLastNameEn;
	}

	public String getPenNameEn() {
		return penNameEn;
	}

	public void setPenNameEn( String penNameEn ) {
		this.penNameEn = penNameEn;
		this.hasPenNameEn = true;
	}
	
	public boolean hasPenNameEn() {
		return hasPenNameEn;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn( String nameEn ) {
		this.nameEn = nameEn;
	}

	public String getFullNameEn() {
		return fullNameEn;
	}

	public void setFullNameEn( String fullNameEn ) {
		this.fullNameEn = fullNameEn;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail( String email ) {
		this.email = email;
		this.hasEmail = true;
	}
	
	public boolean hasEmail() {
		return hasEmail;
	}

	public String getLanguageCode() {
		return langCode;
	}

	public void setLanguageCode( String langCode ) {
		this.langCode = langCode;
		this.hasLangCode = true;
	}
	
	public boolean hasLanguageCode() {
		return hasLangCode;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary( String summary ) {
		this.summary = summary;
		this.hasSummary = true;
	}
	
	public boolean hasSummary() {
		return this.hasSummary;
	}

	
	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl( String pageUrl ) {
		this.pageUrl = pageUrl;
	}

	public String getPageUrlAlias() {
		return pageUrlAlias;
	}

	public void setPageUrlAlias( String pageUrlAlias ) {
		this.pageUrlAlias = pageUrlAlias;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl( String imageUrl ) {
		this.imageUrl = imageUrl;
	}

	
	public Date getRegistrationDate() {
		return registrationDate == null ? null : new Date( registrationDate );
	}

	public void setRegistrationDate( Date registrationDate ) {
		this.registrationDate = registrationDate == null ? null : registrationDate.getTime();
	}
	
	public Integer getContentPublished() {
		return contentPublished;
	}

	public void setContentPublished( Integer contentPublished ) {
		this.contentPublished = contentPublished;
	}

}