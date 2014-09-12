package com.pratilipi.data.access.gae;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.pratilipi.data.transfer.Author;

@PersistenceCapable( table = "AUTHOR" )
public class AuthorEntity implements Author {
	
	@PrimaryKey
	@Persistent( column = "AUTHOR_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;

	@Persistent( column = "USER_ID" )
	private Long userId;
	
	@Persistent( column = "LANGUAGE_ID" )
	private Long languageId;

	@Persistent( column = "FIRST_NAME" )
	private String firstName;
	
	@Persistent( column = "LAST_NAME" )
	private String lastName;

	@Persistent( column = "PEN_NAME" )
	private String penName;
	
	@Persistent( column = "FIRST_NAME_EN" )
	private String firstNameEn;
	
	@Persistent( column = "LAST_NAME_EN" )
	private String lastNameEn;

	@Persistent( column = "PEN_NAME_EN" )
	private String penNameEn;
	
	@Persistent( column = "SUMMARY" )
	private String summary;
	
	@Persistent( column = "EMAIL" )
	private String email;

	@Persistent( column = "REGISTRATION_DATE" )
	private Date registrationDate;

	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public Long getUserId() {
		return userId;
	}

	@Override
	public void setUserId( Long userId ) {
		this.userId = userId;
	}

	@Override
	public Long getLanguageId() {
		return languageId;
	}

	@Override
	public void setLanguageId( Long languageId ) {
		this.languageId = languageId;
	}

	@Override
	public String getFirstName() {
		return firstName;
	}

	@Override
	public void setFirstName( String firstName ) {
		this.firstName = firstName;
	}

	@Override
	public String getLastName() {
		return lastName;
	}

	@Override
	public void setLastName( String lastName ) {
		this.lastName = lastName;
	}

	@Override
	public String getPenName() {
		return penName;
	}

	@Override
	public void setPenName( String penName ) {
		this.penName = penName;
	}

	@Override
	public String getFirstNameEn() {
		return firstNameEn;
	}

	@Override
	public void setFirstNameEn( String firstNameEn ) {
		this.firstNameEn = firstNameEn;
	}

	@Override
	public String getLastNameEn() {
		return lastNameEn;
	}

	@Override
	public void setLastNameEn( String lastNameEn ) {
		this.lastNameEn = lastNameEn;
	}

	@Override
	public String getPenNameEn() {
		return penNameEn;
	}

	@Override
	public void setPenNameEn( String penNameEn ) {
		this.penNameEn = penNameEn;
	}

	@Override
	public String getSummary() {
		return this.summary;
	}

	@Override
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	@Override
	public String getEmail() {
		return email;
	}
	
	@Override
	public void setEmail( String email ) {
		this.email = email;
	}

	@Override
	public Date getRegistrationDate() {
		return registrationDate;
	}

	@Override
	public void setRegistrationDate( Date registrationDate ) {
		this.registrationDate = registrationDate;
	}

}
