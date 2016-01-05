package com.pratilipi.api.impl.author.shared;

import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.type.Language;

@SuppressWarnings( "unused" )
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
	
	private Boolean hasAccessToUpdate;
	
}