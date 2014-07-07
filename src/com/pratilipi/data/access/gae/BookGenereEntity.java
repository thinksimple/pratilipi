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
	
	@Persistent( column = "ISBN" )
	private String isbn;
	
	@Persistent( column = "GENERE_ID" )
	private Long genereId;
	
	
	public Long getId() {
		return id;
	}
	
	public String getIsbn() {
		return isbn;
	}
	
	public void setIsbn( String isbn ) {
		this.isbn = isbn;
	}
	
	public Long getGenereId() {
		return genereId;
	}
	
	public void setGenereId( Long genereId ) {
		this.genereId = genereId;
	}
	
}
