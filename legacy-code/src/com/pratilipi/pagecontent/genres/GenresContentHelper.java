package com.pratilipi.pagecontent.genres;

import com.claymus.commons.server.Access;
import com.claymus.pagecontent.PageContentHelper;
import com.pratilipi.pagecontent.genres.gae.GenresContentEntity;
import com.pratilipi.pagecontent.genres.shared.GenresContentData;

public class GenresContentHelper extends PageContentHelper<
		GenresContent,
		GenresContentData,
		GenresContentProcessor> {
	
	@Override
	public String getModuleName() {
		return "Genre List";
	}

	@Override
	public Double getModuleVersion() {
		return 2.0;
	}

	@Override
	public Access[] getAccessList() {
		return new Access[] {};
	}
	
	
	public static GenresContent newGenresContent() {
		return new GenresContentEntity();
	}

}
