package com.pratilipi.service.shared.data;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PublisherData implements IsSerializable {
	
	private Long id;
	
	private String pageUrl;
	private String pageUrlAlias;
	private String publisherBannerUrl;
	private String publisherBannerUploadUrl;

	private Long userId;

	private Long languageId;
	private boolean hasLanguageId;

	private LanguageData languageData;

	private String name;
	private boolean hasName;

	private String nameEn;
	private boolean hasNameEn;

	private String email;
	private boolean hasEmail;

	private String secret;
	
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

	public String getPageUrlAlias() {
		return pageUrlAlias;
	}

	public void setPageUrlAlias( String pageUrlAlias ) {
		this.pageUrlAlias = pageUrlAlias;
	}

	public String getPublisherBannerUrl() {
		return publisherBannerUrl;
	}

	public void setPublisherBannerUrl( String publisherBannerUrl ) {
		this.publisherBannerUrl = publisherBannerUrl;
	}

	public String getPublisherBannerUploadUrl() {
		return publisherBannerUploadUrl;
	}

	public void setPublisherBannerUploadUrl( String publisherBannerUploadUrl ) {
		this.publisherBannerUploadUrl = publisherBannerUploadUrl;
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
		return name;
	}

	public void setName( String name ) {
		this.name = name;
		this.hasName = true;
	}

	public boolean hasName() {
		return hasName;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn( String nameEn ) {
		this.nameEn = nameEn;
		this.hasNameEn = true;
	}

	public boolean hasNameEn() {
		return hasNameEn;
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
	
	public String getSecret() {
		return secret;
	}

	public void setSecret( String secret ) {
		this.secret = secret;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate( Date registrationDate ) {
		this.registrationDate = registrationDate;
	}

}
