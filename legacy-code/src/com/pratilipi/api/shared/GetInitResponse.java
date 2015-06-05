package com.pratilipi.api.shared;

import com.claymus.api.shared.GenericResponse;

@SuppressWarnings("serial")
public class GetInitResponse extends GenericResponse { 
	
	private String str;

	
	@SuppressWarnings("unused")
	private GetInitResponse() {}
	
	public GetInitResponse( String str ) {
		this.str = str;
	}
	
	
	public String getAccessToken() {
		return str;
	}
	
}
