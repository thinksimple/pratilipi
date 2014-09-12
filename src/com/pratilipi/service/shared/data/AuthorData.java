package com.pratilipi.service.shared.data;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AuthorData implements IsSerializable {

	private Long id;
	
	private Long languageId;
	
	private boolean hasLanguageId;
	private String languageName;

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
	
	private String summary;
	private boolean hasSummary;
	
	private String email;
	private boolean hasEmail;

	private Date registrationDate;
	private boolean hasRegistrationDate;
	
	
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
		this.hasLanguageId = true;
		this.languageId = languageId;
	}
	
	public boolean hasLanguageId(){
		return hasLanguageId;
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
		this.hasFirstName = true;
		this.firstName = firstName;
	}

	public boolean hasFirstName(){
		return hasFirstName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName( String lastName ) {
		this.hasLastName = true;
		this.lastName = lastName;
	}
	
	public boolean hasLastName(){
		return hasLastName;
	}

	public String getPenName() {
		return penName;
	}

	public void setPenName( String penName ) {
		this.hasPenName = true;
		this.penName = penName;
	}
	
	public boolean hasPenName(){
		return hasPenName;
	}

	public String getFirstNameEn() {
		return firstNameEn;
	}

	public void setFirstNameEn( String firstNameEn ) {
		this.hasFirstNameEn = true;
		this.firstNameEn = firstNameEn;
	}
	
	public boolean hasFirstNameEn(){
		return hasFirstNameEn;
	}

	public String getLastNameEn() {
		return lastNameEn;
	}

	public void setLastNameEn( String lastNameEn ) {
		this.hasLastNameEn = true;
		this.lastNameEn = lastNameEn;
	}
	
	public boolean hasLastNameEn(){
		return hasLastNameEn;
	}

	public String getPenNameEn() {
		return penNameEn;
	}

	public void setPenNameEn( String penNameEn ) {
		this.hasPenNameEn = true;
		this.penNameEn = penNameEn;
	}
	
	public boolean hasPenNameEn(){
		return hasPenNameEn;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.hasSummary = true;
		this.summary = summary;
	}
	
	public boolean hasSummary(){
		return this.hasSummary;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail( String email ) {
		this.hasEmail = true;
		this.email = email;
	}
	
	public boolean hasEmail(){
		return hasEmail;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate( Date registrationDate ) {
		this.hasRegistrationDate = true;
		this.registrationDate = registrationDate;
	}
	
	public boolean hasRegistrationDate(){
		return hasRegistrationDate;
	}

}
