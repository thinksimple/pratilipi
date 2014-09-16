package com.pratilipi.data.transfer;

import java.io.Serializable;
import java.util.Date;

import com.pratilipi.commons.shared.PratilipiType;

public interface Pratilipi extends Serializable {

	Long getId();
	
	PratilipiType getType();
	
	void setType( PratilipiType pratilipiType );
	
	boolean isPublicDomain();
	
	void setPublicDomain( boolean isPublicDomain );

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
	
	String getContent();
	
	void setContent( String content );
	
	Long getWordCount();
	
	void setWordCount( Long wordCount );

	Long getPageCount();
	
	void setPageCount( Long pageCount );
	
	Long getReviewCount();
	
	void setReviewCount( Long reviewCount );
	
	Long getRatingCount();
	
	void setRatingCount( Long ratingCount );
	
	Long getStarCount();
	
	void setStarCount( Long starCount );
	
	Date getLastUpdated();
	
	void setLastUpdated( Date lastUpdated );

}
