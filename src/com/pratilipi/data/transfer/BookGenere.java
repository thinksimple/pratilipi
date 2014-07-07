package com.pratilipi.data.transfer;

public interface BookGenere {

	Long getId();
	
	String getIsbn();
	
	void setIsbn( String isbn );
	
	Long getGenereId();
	
	void setGenereId( Long genereId );
	
}
