package com.pratilipi.pagecontent.genres;

import com.claymus.module.pagecontent.PageContentFactory;
import com.pratilipi.pagecontent.genres.gae.GenresContentEntity;

public class GenresContentFactory
		implements PageContentFactory<GenresContent, GenresContentProcessor> {
	
	public static GenresContent newGenresContent() {
		
		return new GenresContentEntity();
		
	}
	
}
