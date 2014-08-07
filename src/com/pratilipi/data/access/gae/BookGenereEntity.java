package com.pratilipi.data.access.gae;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.pratilipi.data.transfer.BookGenere;

@PersistenceCapable( table = "BOOK_GENERE" )
public class BookGenereEntity implements BookGenere {

	@PrimaryKey
	@Persistent( column = "BOOK_GENERE_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;
	
	@Persistent( column = "BOOK_ID" )
	private Long bookId;
	
	@Persistent( column = "GENERE_ID" )
	private Long genereId;
	
	
	public Long getId() {
		return id;
	}
	
	public Long getBookId() {
		return bookId;
	}
	
	public void setBookId( Long bookId ) {
		this.bookId = bookId;
	}
	
	public Long getGenereId() {
		return genereId;
	}
	
	public void setGenereId( Long genereId ) {
		this.genereId = genereId;
	}
	
}
