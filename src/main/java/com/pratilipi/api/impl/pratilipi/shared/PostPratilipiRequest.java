package com.pratilipi.api.impl.pratilipi.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PratilipiContentType;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.type.PratilipiType;

public class PostPratilipiRequest extends GenericRequest {

	@Validate( minLong = 1L )
	private Long pratilipiId;
	
	private String title;
	private transient boolean hasTitle;
	
	private String titleEn;
	private transient boolean hasTitleEn;
	
	private Language language;
	private transient boolean hasLanguage;
	
	private Long authorId;
	private transient boolean hasAuthorId;
	
	@Deprecated
	private String summary;
	@Deprecated
	private transient boolean hasSummary;
	
	private Integer publicationYear;
	private transient boolean hasPublicationYear;
	
	
	private PratilipiType type;
	private transient boolean hasType;

	private PratilipiContentType contentType;
	private transient boolean hasContentType;

	private PratilipiState state;
	private transient boolean hasState;

	
	
	public Long getId() {
		return pratilipiId;
	}

	public String getTitle() {
		return title;
	}

	public boolean hasTitle() {
		return hasTitle;
	}

	public String getTitleEn() {
		return titleEn;
	}

	public boolean hasTitleEn() {
		return hasTitleEn;
	}

	public Language getLanguage() {
		return language;
	}

	public boolean hasLanguage() {
		return hasLanguage;
	}
	
	public Long getAuthorId() {
		return authorId;
	}

	public boolean hasAuthorId() {
		return hasAuthorId;
	}
	
	public String getSummary() {
		return summary;
	}
	
	public boolean hasSummary() {
		return hasSummary;
	}
	
	public Integer getPublicationYear() {
		return publicationYear;
	}

	public boolean hasPublicationYear() {
		return hasPublicationYear;
	}
	
	
	public PratilipiType getType() {
		return type;
	}
	
	public boolean hasType() {
		return hasType;
	}
	
	public PratilipiContentType getContentType() {
		return contentType;
	}
	
	public boolean hasContentType() {
		return hasContentType;
	}
	
	public PratilipiState getState() {
		return state;
	}
	
	public boolean hasState() {
		return hasState;
	}
	
}
