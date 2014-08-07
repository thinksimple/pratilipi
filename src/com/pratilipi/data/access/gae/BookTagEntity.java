package com.pratilipi.data.access.gae;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.pratilipi.data.transfer.BookTag;

@PersistenceCapable( table = "BOOK_TAG" )
public class BookTagEntity implements BookTag {

	@PrimaryKey
	@Persistent( column = "BOOK_TAG_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;
	
	@Persistent( column = "BOOK_ID" )
	private Long bookId;
	
	@Persistent( column = "TAG_ID" )
	private Long tagId;
	
	
	public Long getId() {
		return id;
	}
	
	public Long getBookId() {
		return bookId;
	}
	
	public void setBookId( Long bookId ) {
		this.bookId = bookId;
	}
	
	public Long getTagId() {
		return tagId;
	}
	
	public void setTagId( Long tagId ) {
		this.tagId = tagId;
	}
	
}
