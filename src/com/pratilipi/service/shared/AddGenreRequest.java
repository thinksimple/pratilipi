package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.service.shared.data.GenreData;

public class AddGenreRequest implements IsSerializable {

	private GenreData genreData;


	@SuppressWarnings("unused")
	private AddGenreRequest() {}
	
	public AddGenreRequest( GenreData genreData ) {
		this.genreData = genreData;
	}
	
	
	public GenreData getGenre() {
		return genreData;
	}

}
