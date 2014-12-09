package com.pratilipi.service.shared;

import com.pratilipi.commons.shared.GenericRequest;

public class GetPratilipiContentRequest extends GenericRequest {

	private long pratilipiId;
	private int pageNo;


	private GetPratilipiContentRequest() {
		super( null );
	}

	public GetPratilipiContentRequest( long pratilipiId, int pageNo ) {
		this( null, pratilipiId, pageNo );
	}

	public GetPratilipiContentRequest( String accessToken, long pratilipiId, int pageNo ) {
		super( accessToken );
		this.pratilipiId = pratilipiId;
		this.pageNo = pageNo;
	}

	public Long getPratilipiId() {
		return pratilipiId;
	}

	public int getPageNumber() {
		return pageNo;
	}

}
