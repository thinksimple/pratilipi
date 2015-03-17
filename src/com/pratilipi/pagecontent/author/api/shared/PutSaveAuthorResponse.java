package com.pratilipi.pagecontent.author.api.shared;

import com.claymus.api.shared.GenericResponse;

@SuppressWarnings("serial")
public class PutSaveAuthorResponse extends GenericResponse {

private String authorPageUrl;
	
	public PutSaveAuthorResponse( String authorPageUrl ){
		this.authorPageUrl = authorPageUrl;
	}
	
	public String getAuthorPageUrl(){
		return this.authorPageUrl;
	}
}
