package com.pratilipi.data.transfer;

import java.io.Serializable;
import java.util.Date;

import com.pratilipi.commons.shared.PurchasedFrom;
import com.pratilipi.commons.shared.UserReviewState;

public interface UserPratilipi extends Serializable {

	String getId();
	
	Long getUserId();
	
	void setUserId( Long userId );
	
	Long getPratilipiId();
	
	void setPratilipiId( Long pratilipiId );
	
	PurchasedFrom getPurchasedFrom();
	
	void setPurchasedFrom( PurchasedFrom purchasedFrom );
	
	Long getRating();
	
	void setRating( Long rating );
	
	String getReview();
	
	void setReview( String review );
	
	UserReviewState getReviewState();
	
	void setReviewState( UserReviewState reviewState );
	
	Date getReviewDate();
	
	void setReviewDate( Date reviewDate );
	
	Date getPurchaseDate();
	
	void setPurchaseDate( Date purchaseDate );

}
