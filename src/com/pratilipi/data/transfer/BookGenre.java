package com.pratilipi.data.transfer;

public interface BookGenre {

	Long getId();
	
	Long getBookId();
	
	void setBookId( Long bookId );
	
	Long getGenreId();
	
	void setGenreId( Long genreId );
	
}
