package com.pratilipi.data.type;

import java.io.Serializable;
import java.util.Date;

import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PratilipiContentType;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.type.PratilipiType;

public interface Pratilipi extends GenericOfyType, Serializable {

	Long getId();
	
	String getTitle();
	
	void setTitle( String title );
	
	String getTitleEn();
	
	void setTitleEn( String titleEn );
	
	Language getLanguage();
	
	void setLanguage( Language language );
	
	Long getAuthorId();
	
	void setAuthorId( Long authorId );
	
	@Deprecated
	String getSummary();
	
	@Deprecated
	void setSummary( String summary );


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


	@Deprecated
	String getIndex();
	
	@Deprecated
	void setIndex( String index );
	
	Integer getPageCount();
	
	void setPageCount( Integer pageCount );
	
	
	Long getReviewCount();
	
	void setReviewCount( Long reviewCount );
	
	Long getRatingCount();
	
	void setRatingCount( Long ratingCount );
	
	Long getTotalRating();
	
	void setTotalRating( Long totalRating );

	
	Long getReadCountOffset();

	void setReadCountOffset( Long readCountOffset );
		
	Long getReadCount();

	void setReadCount( Long readCount );
	
	Long getFbLikeShareCountOffset();

	Long getFbLikeShareCount();

	void setFbLikeShareCount( Long fbLikeShareCount );

}