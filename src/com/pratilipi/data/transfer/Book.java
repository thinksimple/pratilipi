package com.pratilipi.data.transfer;

import java.util.Date;

public interface Book {

	Long getId();
	
	String getTitle();
	
	void setTitle( String title );
	
	Long getLanguageId();
	
	void setLanguageId( Long languageId );
	
	Long getAuthorId();
	
	void setAuthorId( Long authorId );

	Long getPublisherId();
	
	void setPublisherId( Long publisherId );
	
	Date getPublicationDate();
	
	void setPublicationDate( Date publicationDate );
	
	Date getListingDate();
	
	void setListingDate( Date listingDate );
	
	Long getWordCount();
	
	void setWordCount( Long wordCount );
	
}
