package com.pratilipi.service.shared.data;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AuthorData implements IsSerializable {

	private Long id;

	private String pageUrl;
	private String authorImageUrl;
	
	private Long userId;

	private Long languageId;
	private boolean hasLanguageId;

	private LanguageData languageData;

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
	
	
	public Long getId() {
		return id;
	}

	public void setId( Long id ) {
		this.id = id;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl( String pageUrl ) {
		this.pageUrl = pageUrl;
	}

	public String getAuthorImageUrl() {
		return authorImageUrl;
	}

	public void setAuthorImageUrl( String authorImageUrl ) {
		this.authorImageUrl = authorImageUrl;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId( Long userId ) {
		this.userId = userId;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId( Long languageId ) {
		this.languageId = languageId;
		this.hasLanguageId = true;
	}
	
	public boolean hasLanguageId() {
		return hasLanguageId;
	}

	public LanguageData getLanguageData() {
		return languageData;
	}

	public void setLanguageData( LanguageData languageData ) {
		this.languageData = languageData;
	}

	public String getName() {
		return firstName + ( lastName == null ? "" : " " + lastName );
	}

	public String getFullName() {
		return penName == null ? getName() : getName() + " '" + penName + "'";
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

	public String getNameEn() {
		return firstNameEn + ( lastNameEn == null ? "" : " " + lastNameEn );
	}

	public String getFullNameEn() {
		return penNameEn == null ? getNameEn() : getNameEn() + " '" + penNameEn + "'";
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

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate( Date registrationDate ) {
		this.registrationDate = registrationDate;
	}

}
