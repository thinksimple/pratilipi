package com.pratilipi.pagecontent.books.gae;

import javax.jdo.annotations.PersistenceCapable;

import com.claymus.data.access.gae.PageContentEntity;
import com.pratilipi.pagecontent.books.BooksContent;

@PersistenceCapable
public class BooksContentEntity extends PageContentEntity
		implements BooksContent { }
