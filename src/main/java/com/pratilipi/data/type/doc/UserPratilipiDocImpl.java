package com.pratilipi.data.type.doc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pratilipi.data.type.CommentDoc;
import com.pratilipi.data.type.UserPratilipiDoc;

public class UserPratilipiDocImpl implements UserPratilipiDoc {
	
	private Long userId;
	private Long pratilipiId;
	
	private Integer rating;
	private String review;
	private Long reviewDate;
	
	private List<CommentDoc> comments;
	private List<Long> likedByUserIds;
	
	
	@Override
	public Long getUserId() {
		return userId;
	}
	
	@Override
	public void setUserId( Long userId ) {
		this.userId = userId;
	}
	
	@Override
	public Long getPratilipiId() {
		return pratilipiId;
	}

	@Override
	public void setPratilipiId( Long pratilipiId ) {
		this.pratilipiId = pratilipiId;
	}
	
	
	@Override
	public Integer getRating() {
		return rating;
	}
	
	@Override
	public void setRating( Integer rating ) {
		this.rating = rating;
	}
	
	@Override
	public String getReview() {
		return review;
	}

	@Override
	public void setReview( String review ) {
		this.review = review;
	}
	
	@Override
	public Date getReviewDate() {
		return reviewDate == null ? null : new Date( reviewDate );
	}

	@Override
	public void setReviewDate( Date reviewDate ) {
		this.reviewDate = reviewDate == null ? null : reviewDate.getTime();
	}

	
	@Override
	public List<CommentDoc> getComments() {
		return comments == null ? new ArrayList<CommentDoc>( 0 ) : comments;
	}
	
	@Override
	public void setComments( List<CommentDoc> comments ) {
		this.comments = comments == null || comments.size() == 0 ? null : comments;
	}

	@Override
	public List<Long> getLikedByUserIds() {
		return likedByUserIds == null ? new ArrayList<Long>( 0 ) : likedByUserIds;
	}
	
	@Override
	public void setLikedByUserIds( List<Long> likedByUserIds ) {
		this.likedByUserIds = likedByUserIds == null || likedByUserIds.size() == 0 ? null : likedByUserIds;
	}
	
}