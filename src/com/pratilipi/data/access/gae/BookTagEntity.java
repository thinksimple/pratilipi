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
	
	@Persistent( column = "ISBN" )
	private String isbn;
	
	@Persistent( column = "TAG_ID" )
	private Long tagId;
	
	
	public Long getId() {
		return id;
	}
	
	public String getIsbn() {
		return isbn;
	}
	
	public void setIsbn( String isbn ) {
		this.isbn = isbn;
	}
	
	public Long getTagId() {
		return tagId;
	}
	
	public void setTagId( Long tagId ) {
		this.tagId = tagId;
	}
	
}
