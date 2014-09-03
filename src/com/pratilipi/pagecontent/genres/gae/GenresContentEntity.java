package com.pratilipi.pagecontent.genres.gae;

import javax.jdo.annotations.PersistenceCapable;

import com.claymus.data.access.gae.PageContentEntity;
import com.pratilipi.pagecontent.genres.GenresContent;

@PersistenceCapable
public class GenresContentEntity extends PageContentEntity
		implements GenresContent { }
