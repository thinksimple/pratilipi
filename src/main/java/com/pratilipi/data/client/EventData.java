package com.pratilipi.data.client;

import java.util.List;

import com.pratilipi.common.type.Language;

public class EventData {
	
	private Long eventId;
	
	private String name;
	private boolean hasName;
	
	private String nameEn;
	private boolean hasNameEn;
	
	private Language language;
	private boolean hasLanguage;
	
	private String summary;
	private boolean hasSummary;
	
	private List<Long> pratilipiIdList;

	private List<String> pratilipiUrlList;
	private boolean hasPratilipiUrlList;

	private String bannerImageUrl;
	
	private Boolean hasAccessToUpdate;

	
	public Long getId() {
		return eventId;
	}

	public void setId( Long id ) {
		this.eventId = id;
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

	public Language getLanguage() {
		return language;
	}

	public void setLanguage( Language language ) {
		this.language = language;
		this.hasLanguage = true;
	}

	public boolean hasLanguage() {
		return hasLanguage;
	}
	
	public String getSummary() {
		return summary;
	}
	
	public void setSummary( String summary ) {
		this.summary = summary;
		this.hasSummary = true;
	}

	public boolean hasSummary() {
		return hasSummary;
	}
	
	public List<Long> getPratilipiIdList() {
		return pratilipiIdList;
	}
	
	public void setPratilipiIdList( List<Long> pratilipiIdList ) {
		this.pratilipiIdList = pratilipiIdList;
	}

	public List<String> getPratilipiUrlList() {
		return pratilipiUrlList;
	}

	public void setPratilipiUrlList( List<String> pratilipiUrlList ) {
		this.pratilipiUrlList = pratilipiUrlList;
		this.hasPratilipiUrlList = true;
	}
	
	public boolean hasPratilipiUrlList() {
		return hasPratilipiUrlList;
	}
	
	public String getBannerImageUrl() {
		return bannerImageUrl;
	}

	public void setBannerImageUrl( String bannerImageUrl ) {
		this.bannerImageUrl = bannerImageUrl;
	}
	
	public boolean hasAccessToUpdate() {
		return hasAccessToUpdate == null ? false : hasAccessToUpdate;
	}
	
	public void setAccessToUpdate( Boolean hasAccessToUpdate ) {
		this.hasAccessToUpdate = hasAccessToUpdate;
	}

}
