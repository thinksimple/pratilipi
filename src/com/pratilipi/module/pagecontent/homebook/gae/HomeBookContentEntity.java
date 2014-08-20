package com.pratilipi.module.pagecontent.homebook.gae;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.claymus.data.access.gae.PageContentEntity;
import com.pratilipi.module.pagecontent.homebook.HomeBookContent;

@PersistenceCapable
public class HomeBookContentEntity extends PageContentEntity implements HomeBookContent {

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
