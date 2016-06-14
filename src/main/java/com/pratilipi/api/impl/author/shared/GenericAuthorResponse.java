package com.pratilipi.api.impl.author.shared;

import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.type.Gender;
import com.pratilipi.common.type.Language;
import com.pratilipi.data.client.AuthorData;

public class GenericAuthorResponse extends GenericResponse {

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

	private Gender gender;
	private String dateOfBirth;
	
	private Language language;
	private String summary;
	
	private String pageUrl;
	private String imageUrl;

	private Long registrationDateMillis;
	
	private Integer contentPublished;
	private Long totalReadCount;
	private Long totalFbLikeShareCount;
	
	private Boolean hasAccessToUpdate;
	
	@SuppressWarnings("unused")
	private GenericAuthorResponse() { }
	
	public GenericAuthorResponse( AuthorData authorData ) {
		
		this.authorId = authorData.getId();
		
		this.firstName = authorData.getFirstName();
		this.lastName = authorData.getLastName();
		this.penName = authorData.getPenName();
		this.name = authorData.getName();
		this.fullName = authorData.getFullName();
		
		this.firstNameEn = authorData.getFirstNameEn();
		this.lastNameEn = authorData.getLastNameEn();
		this.penNameEn = authorData.getPenNameEn();
		this.nameEn = authorData.getNameEn();
		this.fullNameEn = authorData.getFullNameEn();
		
		this.gender = authorData.getGender();
		this.dateOfBirth = authorData.getDateOfBirth();
		
		this.language = authorData.getLanguage();
		this.summary = authorData.getSummary();
		
		this.pageUrl = authorData.getPageUrl();
		this.imageUrl = authorData.getImageUrl();
		
		this.registrationDateMillis = authorData.getRegistrationDate() != null ? 
				 authorData.getRegistrationDate().getTime() : null;
				 
		this.hasAccessToUpdate = authorData.hasAccessToUpdate();
		
	}
	
	
	public Long getId() {
		return authorId;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
		
	public String getPenName() {
		return penName;
	}
	
	public String getName() {
		return name;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	
	public String getFirstNameEn() {
		return firstNameEn;
	}
	
	public String getLastNameEn() {
		return lastNameEn;
	}
	
	public String getPenNameEn() {
		return penNameEn;
	}
	
	public String getNameEn() {
		return nameEn;
	}
	
	public String getFullNameEn() {
		return fullNameEn;
	}
	
	
	public Gender getGender() {
		return gender;
	}
	
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	
	
	public Language getLanguage() {
		return language;
	}
	
	public String getSummary() {
		return summary;
	}
	
	
	public String getPageUrl() {
		return pageUrl;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public String getImageUrl( int width ) {
		return imageUrl.indexOf( '?' ) == -1
				? imageUrl + "?width=" + width
				: imageUrl + "&width=" + width;
	}
	
	
	public Long getRegistrationDate() {
		return registrationDateMillis;
	}
	
	
	public Integer getContentPublished() {
		return contentPublished != null ? contentPublished : 0;
	}
	
	public Long getTotalReadCount() {
		return totalReadCount != null ? totalReadCount : 0L;
	}
	
	public Long getTotalFbLikeShareCount() {
		return totalFbLikeShareCount != null ? totalFbLikeShareCount : 0L;
	}
	
	
	public boolean hasAccessToUpdate() {
		return hasAccessToUpdate == null ? false : hasAccessToUpdate;
	}
	
}