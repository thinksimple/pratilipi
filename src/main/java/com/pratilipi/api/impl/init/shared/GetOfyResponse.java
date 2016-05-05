package com.pratilipi.api.impl.init.shared;

import java.util.List;

import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.data.client.AuthorData;
import com.pratilipi.data.client.EventData;

@SuppressWarnings("unused")
public class GetOfyResponse extends GenericResponse {

	private Integer count;

	
	private GetOfyResponse() {}
	
	public GetOfyResponse( Integer count ) {
		this.count = count;
	}
	
}
