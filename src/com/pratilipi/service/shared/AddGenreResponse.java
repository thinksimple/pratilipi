package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AddGenreResponse implements IsSerializable {

	private Long genreId;


	@SuppressWarnings("unused")
	private AddGenreResponse() {}
	
	public AddGenreResponse( Long genreId ) {
		this.genreId = genreId;
	}

	
	public Long getLanguageId() {
		return genreId;
	}
	
}
