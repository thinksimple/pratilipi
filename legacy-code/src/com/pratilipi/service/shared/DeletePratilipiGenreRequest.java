package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DeletePratilipiGenreRequest implements IsSerializable {

	private Long pratilipiId;
	
	private Long genreId;
	

	@SuppressWarnings("unused")
	private DeletePratilipiGenreRequest() {}
	
	public DeletePratilipiGenreRequest( Long pratilipiId, Long genreId ) {
		this.pratilipiId = pratilipiId;
		this.genreId = genreId;
	}
	
	
	public Long getPratilipiId() {
		return pratilipiId;
	}

	public Long getGenreId() {
		return genreId;
	}

}
