package com.pratilipi.pagecontent.book.gae;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.claymus.data.access.gae.PageContentEntity;
import com.pratilipi.pagecontent.book.BookContent;

@PersistenceCapable
public class BookContentEntity extends PageContentEntity implements BookContent {

	@Persistent( column = "X_COL_0" )
	private Long bookId;

	
	@Override
	public Long getBookId() {
		return bookId;
	}

	@Override
	public void setBookId( Long bookId ) {
		this.bookId = bookId;
	} 

}
