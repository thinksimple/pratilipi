package com.pratilipi.data.transfer;

import java.io.Serializable;
import java.util.Date;

import com.pratilipi.commons.shared.SellerType;
import com.pratilipi.commons.shared.UserReviewState;

public interface UserPratilipi extends Serializable {

	String getId();
	
	Long getUserId();
	
	void setUserId( Long userId );
	
	Long getPratilipiId();
	
	void setPratilipiId( Long pratilipiId );
	
	Integer getLastOpenedPage();
	
	void setLastOpenedPage( Integer lastOpenedPage );

	Date getLastOpenedDate();
	
	void setLastOpenedDate( Date lastOpenedDate );
	
	SellerType getPurchasedFrom();
	
	void setPurchasedFrom( SellerType purchasedFrom );
	
	Date getPurchaseDate();
	
	void setPurchaseDate( Date purchaseDate );
	
	Integer getRating();
	
	void setRating( Integer rating );
	
	String getReview();
	
	void setReview( String review );
	
	UserReviewState getReviewState();
	
	void setReviewState( UserReviewState reviewState );
	
	Date getReviewDate();
	
	void setReviewDate( Date reviewDate );
	
	String getBookmarks();
	
	void setBookmarks( String bookmarks );
	
	Boolean isAddedtoLib();
	
	void setAddedToLib( Boolean addedToLib );
	
}
