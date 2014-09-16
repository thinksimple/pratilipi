package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.commons.shared.PratilipiType;

public class GetPratilipiListRequest implements IsSerializable {

	private PratilipiType pratilipiType;
	
	private Boolean publicDomain;
	
	private Long languageId;
	
	private String cursor;
	
	private int resultCount;
	
	
	@SuppressWarnings("unused")
	private GetPratilipiListRequest() {}

	public GetPratilipiListRequest(
			String cursor, int resultCount ) {
		
		this( null, cursor, resultCount );
	}

	public GetPratilipiListRequest(
			PratilipiType pratilipiType,
			String cursor, int resultCount ) {
		
		this( pratilipiType, null, null, cursor, resultCount );
	}

	public GetPratilipiListRequest(
			PratilipiType pratilipiType, Boolean publicDomain, Long languageId,
			String cursor, int resultCount ) {
		
		this.pratilipiType = pratilipiType;
		this.publicDomain = publicDomain;
		this.languageId = languageId;
		this.cursor = cursor;
		this.resultCount = resultCount;
	}

	
	public PratilipiType getPratilipiType() {
		return pratilipiType;
	}

	public Boolean getPublicDomain() {
		return publicDomain;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public String getCursor() {
		return cursor;
	}
	
	public int getResultCount() {
		return resultCount;
	}

}
