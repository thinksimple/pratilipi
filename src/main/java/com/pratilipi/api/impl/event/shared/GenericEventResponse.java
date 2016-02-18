package com.pratilipi.api.impl.event.shared;

import java.util.List;

import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.type.Language;
import com.pratilipi.data.client.EventData;

public class GenericEventResponse extends GenericResponse {
	
	private Long eventId;
	
	private String name;
	private String nameEn;
	
	private Language language;
	private String description;
	private List<String> pratilipiUrlList;
	
	private String pageUrl;
	private String bannerImageUrl;
	
	private Boolean hasAccessToUpdate;

	
	public GenericEventResponse( EventData eventData ) {
		this.eventId = eventData.getId();
		this.name = eventData.getName();
		this.nameEn = eventData.getNameEn();
		this.language = eventData.getLanguage();
		this.description = eventData.getDescription();
		this.pratilipiUrlList = eventData.getPratilipiUrlList();
		this.pageUrl = eventData.getPageUrl();
		this.bannerImageUrl = eventData.getBannerImageUrl();
		this.hasAccessToUpdate = eventData.hasAccessToUpdate();
	}
	
	
	public Long getId() {
		return eventId;
	}
	
	
	public String getName() {
		return name;
	}
	
	public String getNameEn() {
		return nameEn;
	}
	
	
	public Language getLanguage() {
		return language;
	}
	
	public String getSummary() {
		return description;
	}

	public List<String> getPratilipiUrlList() {
		return pratilipiUrlList;
	}

	
	public String getPageUrl() {
		return pageUrl;
	}

	public String getBannerImageUrl() {
		return bannerImageUrl;
	}

	
	public Boolean hasAccessToUpdate() {
		return hasAccessToUpdate;
	}
	
}
