package com.pratilipi.api.author.shared;

import java.util.Date;

import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.type.PratilipiType;
import com.pratilipi.data.client.AuthorData;
import com.pratilipi.data.client.PratilipiData;

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
	private String langCode;
	private String summary;
	
	private String pageUrl;
	private String pageUrlAlias;
	private String imageUrl;

	private Long registrationDate;
	
	private Integer contentPublished;

	
	public GetAuthorResponse( AuthorData author ) {
		authorId = author.getId();
		
		firstName = author.getFirstName();
		lastName = author.getLastName();
		penName = author.getPenName();
		name = author.getName();
		fullName = author.getFullName();
		
		firstNameEn = author.getFirstNameEn();
		lastNameEn = author.getLastNameEn();
		penNameEn = author.getPenNameEn();
		nameEn = author.getNameEn();
		fullNameEn = author.getFullNameEn();
		
		email = author.getEmail();
		langCode = author.getLanguageCode();
		summary = author.getSummary();
		
		pageUrl = author.getPageUrl();
		pageUrlAlias = author.getPageUrlAlias();
		imageUrl = author.getImageUrl();
		
		registrationDate = author.getRegistrationDate().getTime();
		
		contentPublished = author.getContentPublished();
	}
	
}