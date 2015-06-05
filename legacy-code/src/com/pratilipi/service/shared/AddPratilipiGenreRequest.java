package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AddPratilipiGenreRequest implements IsSerializable {

	private Long pratilipiId;
	
	private Long genreId;
	

	@SuppressWarnings("unused")
	private AddPratilipiGenreRequest() {}
	
	public AddPratilipiGenreRequest( Long pratilipiId, Long genreId ) {
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
