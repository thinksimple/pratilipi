package com.pratilipi.data.transfer;

public interface BookAuthor {

	Long getId();
	
	String getIsbn();
	
	void setIsbn( String isbn );
	
	Long getAuthorId();
	
	void setAuthorId( Long authorId );
	
}
