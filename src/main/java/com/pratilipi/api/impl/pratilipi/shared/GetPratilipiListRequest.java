package com.pratilipi.api.impl.pratilipi.shared;

import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.type.PratilipiType;

public class GetPratilipiListRequest extends GenericRequest {

	private String searchQuery;
	private Long authorId;
	private Language language;
	private PratilipiType type;
	private PratilipiState state;

	private String cursor;
	private Integer resultCount;
	private Boolean includeSummaryAndIndex;

	
	public String getSearchQuery() {
		return searchQuery;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public Language getLanguage() {
		return language;
	}

	public PratilipiType getType() {
		return type;
	}

	public PratilipiState getState() {
		return state;
	}

	
	public String getCursor() {
		return cursor;
	}
	
	public Integer getResultCount() {
		return resultCount;
	}
	
	public boolean includeSummaryAndIndex() {
		return includeSummaryAndIndex == null ? false : includeSummaryAndIndex;
	}
	
}
