package com.pratilipi.data.access.gae;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.pratilipi.data.transfer.BookAuthor;

@PersistenceCapable( table = "BOOK_AUTHOR" )
public class BookAuthorEntity implements BookAuthor {

	@PrimaryKey
	@Persistent( column = "BOOK_AUTHOR_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;
	
	@Persistent( column = "BOOK_ID" )
	private Long bookId;
	
	@Persistent( column = "AUTHOR_ID" )
	private Long authorId;
	
	
	@Override
	public Long getId() {
		return id;
	}
	
	@Override
	public Long getBookId() {
		return bookId;
	}
	
	@Override
	public void setBookId( Long bookId ) {
		this.bookId = bookId;
	}
	
	@Override
	public Long getAuthorId() {
		return authorId;
	}
	
	@Override
	public void setAuthorId( Long authorId ) {
		this.authorId = authorId;
	}
	
}
