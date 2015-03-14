package com.pratilipi.pagecontent.author.api.shared;

import java.util.List;

import com.claymus.api.shared.GenericResponse;
import com.pratilipi.data.transfer.shared.AuthorData;

@SuppressWarnings("serial")
public class GetAuthorListResponse extends GenericResponse {

	@SuppressWarnings("unused")
	private List<AuthorData> authorList;
	
	@SuppressWarnings("unused")
	private String cursor;

	
	@SuppressWarnings("unused")
	private GetAuthorListResponse() {}
	
	public GetAuthorListResponse( List<AuthorData> authorList, String cursor ) {
		this.authorList = authorList;
		this.cursor = cursor;
	}
}
