package com.pratilipi.api.pratilipi.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.type.PratilipiType;

public class GetPratilipiListRequest extends GenericRequest {

	private PratilipiType type;
	
	private Language language;
	
	private Long authorId;

	@Validate( required = true )
	private PratilipiState state;

	private String cursor;
	
	private Integer resultCount;

	
	public PratilipiType getType() {
		return type;
	}

	public Language getLanguage() {
		return language;
	}

	public Long getAuthorId() {
		return authorId;
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
	
}
