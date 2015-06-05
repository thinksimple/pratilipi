package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.service.shared.data.GenreData;

public class SaveGenreRequest implements IsSerializable {

	private GenreData genreData;


	@SuppressWarnings("unused")
	private SaveGenreRequest() {}
	
	public SaveGenreRequest( GenreData genreData ) {
		this.genreData = genreData;
	}
	
	
	public GenreData getGenre() {
		return genreData;
	}

}
