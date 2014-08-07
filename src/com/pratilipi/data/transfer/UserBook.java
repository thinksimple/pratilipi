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
	
	Long getBookId();
	
	void setBookId( Long bookId );
	
	Long getRating();
	
	void setRating( Long rating );
	
	ReviewState getReviewState();
	
	void setReviewState( ReviewState reviewState );
	
	Date getReviewDate();
	
	void setReviewDate( Date reviewDate );

}
