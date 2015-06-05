package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SaveGenreResponse implements IsSerializable {

	private Long genreId;


	@SuppressWarnings("unused")
	private SaveGenreResponse() {}
	
	public SaveGenreResponse( Long genreId ) {
		this.genreId = genreId;
	}

	
	public Long getGenreId() {
		return genreId;
	}
	
}
