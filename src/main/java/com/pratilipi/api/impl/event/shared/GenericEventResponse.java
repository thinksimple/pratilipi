package com.pratilipi.api.impl.event.shared;

import java.util.List;

import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.type.Language;

public class GenericEventResponse extends GenericResponse {
	
	private Long eventId;
	
	private String name;
	private String nameEn;
	
	private Language language;
	private String summary;
	private List<Long> pratilipiIdList;
	private List<String> pratilipiUrlList;
	
	private String bannerImageUrl;
	
	private Boolean hasAccessToUpdate;

	
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
		return summary;
	}

	public List<Long> getPratilipiIdList() {
		return pratilipiIdList;
	}

	public List<String> getPratilipiUrlList() {
		return pratilipiUrlList;
	}

	
	public String getBannerImageUrl() {
		return bannerImageUrl;
	}

	
	public Boolean hasAccessToUpdate() {
		return hasAccessToUpdate;
	}
	
}
