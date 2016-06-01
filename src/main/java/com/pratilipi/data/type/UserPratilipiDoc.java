package com.pratilipi.data.type;

import java.util.Date;
import java.util.List;

public interface UserPratilipiDoc {
	
	Long getUserId();
	
	void setUserId( Long userId );
	
	Long getPratilipiId();
	
	void setPratilipiId( Long pratilipiId );

	
	Integer getRating();
	
	void setRating( Integer rating );
	
	String getReview();
	
	void setReview( String review );
	
	Date getReviewDate();
	
	void setReviewDate( Date reviewDate );

	
	List<Long> getLikedByUserIds();
	
	void setLikedByUserIds( List<Long> likedByUserIds );
	
	List<CommentDoc> getComments();
	
	void setComments( List<CommentDoc> comments );
	
}
