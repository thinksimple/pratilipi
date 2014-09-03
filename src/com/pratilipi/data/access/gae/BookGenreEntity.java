package com.pratilipi.data.access.gae;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.pratilipi.data.transfer.BookGenre;

@PersistenceCapable( table = "BOOK_GENRE" )
public class BookGenreEntity implements BookGenre {

	@PrimaryKey
	@Persistent( column = "BOOK_GENRE_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;
	
	@Persistent( column = "BOOK_ID" )
	private Long bookId;
	
	@Persistent( column = "GENRE_ID" )
	private Long genreId;
	
	
	public Long getId() {
		return id;
	}
	
	public Long getBookId() {
		return bookId;
	}
	
	public void setBookId( Long bookId ) {
		this.bookId = bookId;
	}
	
	public Long getGenreId() {
		return genreId;
	}
	
	public void setGenreId( Long genreId ) {
		this.genreId = genreId;
	}
	
}
