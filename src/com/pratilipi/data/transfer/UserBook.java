package com.pratilipi.data.transfer;

import java.util.Date;

public interface UserBook {
	
	public enum ReviewState {
		NOT_SUBMITTED,
		PENDING_APPROVAL,
		APPROVED,
		AUTO_APPROVED
	}

	String getUserId();
	
	void setUserId( String userId );
	
	String getIsbn();
	
	void setIsbn( String isbn );
	
	Long getRating();
	
	void setRating( Long rating );
	
	ReviewState getReviewState();
	
	void setReviewState( ReviewState reviewState );
	
	Date getReviewDate();
	
	void setReviewDate( Date reviewDate );

}
