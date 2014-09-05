package com.pratilipi.data.transfer;

import java.util.Date;

import com.pratilipi.commons.shared.PratilipiType;

public interface Pratilipi {

	Long getId();
	
	PratilipiType getPratilipiType();
	
	void setPratilipiType( PratilipiType pratilipiType );
	
	String getTitle();
	
	void setTitle( String title );
	
	Long getLanguageId();
	
	void setLanguageId( Long languageId );
	
	Long getAuthorId();
	
	void setAuthorId( Long authorId );

	Long getPublicationYear();
	
	void setPublicationYear( Long publicationYear );
	
	Date getListingDate();
	
	void setListingDate( Date listingDate );
	
	String getSummary();
	
	void setSummary( String summary );
	
	Long getWordCount();
	
	void setWordCount( Long wordCount );
	
}
