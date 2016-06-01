package com.pratilipi.data.type.doc;

import java.util.List;

import com.pratilipi.data.type.PratilipiReviewsDoc;
import com.pratilipi.data.type.UserPratilipiDoc;

public class PratilipiReviewsDocImpl implements PratilipiReviewsDoc {
	
	private List<UserPratilipiDoc> reviews;
	
	
	@Override
	public List<UserPratilipiDoc> getReviews() {
		return reviews;
	}
	
	@Override
	public void setReviews( List<UserPratilipiDoc> reviews ) {
		this.reviews = reviews;
	}

}