package com.pratilipi.data.transfer;

public interface BookAuthor {

	Long getId();
	
	Long getBookId();
	
	void setBookId( Long bookId );
	
	Long getAuthorId();
	
	void setAuthorId( Long authorId );
	
}
