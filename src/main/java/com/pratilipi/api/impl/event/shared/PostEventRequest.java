package com.pratilipi.api.impl.event.shared;

import java.util.List;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.common.type.Language;

public class PostEventRequest extends GenericRequest {

	@Validate( minLong = 1L )
	private Long eventId;

	private String name;
	private boolean hasName;
	
	private String nameEn;
	private boolean hasNameEn;
	
	private Language language;
	private boolean hasLanguage;

	private String description;
	private boolean hasDescription;
	
	private List<String> pratilipiUrlList;
	private boolean hasPratilipiUrlList;
	
	
	public Long getId() {
		return eventId;
	}

	public String getName() {
		return name;
	}

	public boolean hasName() {
		return hasName;
	}

	public String getNameEn() {
		return nameEn;
	}

	public boolean hasNameEn() {
		return hasNameEn;
	}

	public Language getLanguage() {
		return language;
	}

	public boolean hasLanguage() {
		return hasLanguage;
	}
	
	public String getDescription() {
		return description;
	}
	
	public boolean hasDescription() {
		return hasDescription;
	}
	
	public List<String> getPratilipiUrlList() {
		return pratilipiUrlList;
	}
	
	public boolean hasPratilipiUrlList() {
		return hasPratilipiUrlList;
	}
	
}
