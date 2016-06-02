package com.pratilipi.data.type.doc;

import java.util.ArrayList;
import java.util.List;

import com.pratilipi.data.type.PratilipiReviewsDoc;
import com.pratilipi.data.type.UserPratilipiDoc;

public class PratilipiReviewsDocImpl implements PratilipiReviewsDoc {
	
	private List<UserPratilipiDoc> reviews;
	
	
	@Override
	public List<UserPratilipiDoc> getReviews() {
		return reviews == null ? new ArrayList<UserPratilipiDoc>( 0 ) : reviews;
	}
	
	@Override
	public void setReviews( List<UserPratilipiDoc> reviews ) {
		this.reviews = reviews == null || reviews.size() == 0 ? null : reviews;
	}

}