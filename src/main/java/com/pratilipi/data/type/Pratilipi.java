package com.pratilipi.data.type;

import java.io.Serializable;
import java.util.Date;

import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PratilipiContentType;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.type.PratilipiType;

public interface Pratilipi extends Serializable {

	Long getId();
	
	String getTitle();
	
	void setTitle( String title );
	
	public String getTitleEn();
	
	public void setTitleEn( String titleEn );
	
	Language getLanguage();
	
	void setLanguage( Language language );
	
	Long getAuthorId();
	
	void setAuthorId( Long authorId );
	
	String getSummary();
	
	void setSummary( String summary );

	Integer getPublicationYear();
	
	void setPublicationYear( Integer publicationYear );


	PratilipiType getType();
	
	void setType( PratilipiType pratilipiType );
	
	PratilipiContentType getContentType();
	
	void setContentType( PratilipiContentType contentType );

	PratilipiState getState();
	
	void setState( PratilipiState state );
	
	Boolean hasCustomCover();

	void setCustomCover( Boolean customCover );

	Date getListingDate();
	
	void setListingDate( Date listingDate );

	Date getLastUpdated();
	
	void setLastUpdated( Date lastUpdated );


	String getIndex();
	
	void setIndex( String index );
	
	String getKeywords();
	
	void setKeywords( String keywords );
	
	Long getWordCount();
	
	void setWordCount( Long wordCount );

	Integer getPageCount();
	
	void setPageCount( Integer pageCount );
	
	
	Long getReviewCount();
	
	void setReviewCount( Long reviewCount );
	
	Long getRatingCount();
	
	void setRatingCount( Long ratingCount );
	
	Long getTotalRating();
	
	void setTotalRating( Long totalRating );

	
	Long getReadCount();
	
	void setReadCount( Long readCount );
	
	Long getFbLikeShareCount();

	void setFbLikeShareCount( Long fbLikeShareCount );

	
	Date getLastProcessDate();

	void setLastProcessDate( Date lastProcessDate );

	Date getNextProcessDate();

	void setNextProcessDate( Date nextProcessDate );

}