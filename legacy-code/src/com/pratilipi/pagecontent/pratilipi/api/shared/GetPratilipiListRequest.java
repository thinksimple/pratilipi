package com.pratilipi.pagecontent.pratilipi.api.shared;

import com.claymus.api.annotation.Validate;
import com.claymus.api.shared.GenericRequest;
import com.pratilipi.commons.shared.PratilipiState;
import com.pratilipi.commons.shared.PratilipiType;

@SuppressWarnings("serial")
public class GetPratilipiListRequest extends GenericRequest {

	private PratilipiType type;
	
	private Long languageId;
	
	private Long authorId;

	@Validate( required = true )
	private PratilipiState state;

	private String cursor;
	
	private Integer resultCount;

	
	public PratilipiType getType() {
		return type;
	}

	public Long getLanguageId() {
		return languageId;
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
