package com.pratilipi.data.transfer;

import java.io.Serializable;
import java.util.Date;

import com.pratilipi.commons.shared.PratilipiState;
import com.pratilipi.commons.shared.PratilipiType;

public interface Pratilipi extends Serializable {

	Long getId();
	
	PratilipiType getType();
	
	void setType( PratilipiType pratilipiType );
	
	boolean isPublicDomain();
	
	void setPublicDomain( boolean isPublicDomain );

	String getTitle();
	
	void setTitle( String title );
	
	public String getTitleEn();
	
	public void setTitleEn(String titleEn);
	
	Long getLanguageId();
	
	void setLanguageId( Long languageId );
	
	Long getAuthorId();
	
	void setAuthorId( Long authorId );
	
	Long getPublisherId();
	
	void setPublisherId( Long publisherId );
	
	Long getPublicationYear();
	
	void setPublicationYear( Long publicationYear );
	
	Date getListingDate();
	
	void setListingDate( Date listingDate );
	
	String getSummary();
	
	void setSummary( String summary );
	
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
	
	PratilipiState getState();
	
	void setState( PratilipiState state );
	
	String getContentType();
	
	void setContentType( String contentType );
	
	Date getLastUpdated();
	
	void setLastUpdated( Date lastUpdated );

}
