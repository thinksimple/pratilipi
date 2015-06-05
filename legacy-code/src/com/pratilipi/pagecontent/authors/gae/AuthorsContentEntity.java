package com.pratilipi.pagecontent.authors.gae;

import javax.jdo.annotations.PersistenceCapable;

import com.claymus.data.access.gae.PageContentEntity;
import com.pratilipi.pagecontent.authors.AuthorsContent;

@SuppressWarnings("serial")
@PersistenceCapable
public class AuthorsContentEntity extends PageContentEntity
		implements AuthorsContent { }
