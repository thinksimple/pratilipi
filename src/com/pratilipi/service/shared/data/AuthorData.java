package com.pratilipi.service.shared.data;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AuthorData implements IsSerializable {

	private Long id;
	
	private Long languageId;

	private String languageName;

	private String firstName;
	
	private String lastName;
	
	private String penName;
	
	private String firstNameEn;
	
	private String lastNameEn;
	
	private String penNameEn;
	
	private String email;

	private Date registrationDate;
	
	
	public Long getId() {
		return id;
	}

	public void setId( Long id ) {
		this.id = id;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId( Long languageId ) {
		this.languageId = languageId;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName( String languageName ) {
		this.languageName = languageName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName( String firstName ) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName( String lastName ) {
		this.lastName = lastName;
	}

	public String getPenName() {
		return penName;
	}

	public void setPenName( String penName ) {
		this.penName = penName;
	}

	public String getFirstNameEn() {
		return firstNameEn;
	}

	public void setFirstNameEn( String firstNameEn ) {
		this.firstNameEn = firstNameEn;
	}

	public String getLastNameEn() {
		return lastNameEn;
	}

	public void setLastNameEn( String lastNameEn ) {
		this.lastNameEn = lastNameEn;
	}

	public String getPenNameEn() {
		return penNameEn;
	}

	public void setPenNameEn( String penNameEn ) {
		this.penNameEn = penNameEn;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail( String email ) {
		this.email = email;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate( Date registrationDate ) {
		this.registrationDate = registrationDate;
	}

}
