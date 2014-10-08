package com.pratilipi.service.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.service.shared.data.GenreData;

public class GetGenreListResponse implements IsSerializable {

	private List<GenreData> genreDataList;
	
	
	@SuppressWarnings("unused")
	private GetGenreListResponse() {}
	
	public GetGenreListResponse( List<GenreData> genreDataList ) {
		
		this.genreDataList = genreDataList;
	}
	
	
	public List<GenreData> getGenreDataList() {
		return genreDataList;
	}
	
}
