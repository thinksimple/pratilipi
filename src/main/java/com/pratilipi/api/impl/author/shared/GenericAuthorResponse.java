package com.pratilipi.api.impl.author.shared;

import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.type.Gender;
import com.pratilipi.common.type.Language;

@SuppressWarnings( "unused" )
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
	
	private Boolean hasAccessToUpdate;
	
}