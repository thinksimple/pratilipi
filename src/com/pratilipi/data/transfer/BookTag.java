package com.pratilipi.data.transfer;

public interface BookTag {

	Long getId();
	
	String getIsbn();
	
	void setIsbn( String isbn );
	
	Long getTagId();
	
	void setTagId( Long tag );
	
}
