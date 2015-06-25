package com.pratilipi.api.author.shared;

import java.util.List;

import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.data.client.AuthorData;

@SuppressWarnings("unused")
public class GetAuthorListResponse extends GenericResponse {

	private List<AuthorData> authorList;
	private String cursor;

	
	private GetAuthorListResponse() {}
	
	public GetAuthorListResponse( List<AuthorData> authorList, String cursor ) {
		this.authorList = authorList;
		this.cursor = cursor;
	}
}
