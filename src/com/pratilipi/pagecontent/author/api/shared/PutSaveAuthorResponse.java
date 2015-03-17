package com.pratilipi.pagecontent.author.api.shared;

import com.claymus.api.shared.GenericResponse;
import com.pratilipi.data.transfer.shared.AuthorData;

@SuppressWarnings("serial")
public class PutSaveAuthorResponse extends GenericResponse {

	private AuthorData authorData;
	
	public PutSaveAuthorResponse( AuthorData authorData ){
		this.authorData = authorData;
	}
	
	public AuthorData getAuthorData(){
		return this.authorData;
	}
}
