package com.pratilipi.data.type.doc;

import java.util.ArrayList;
import java.util.List;

import com.pratilipi.data.type.PratilipiReviewsDoc;
import com.pratilipi.data.type.UserPratilipiDoc;

public class PratilipiReviewsDocImpl implements PratilipiReviewsDoc {
	
	private List<UserPratilipiDocImpl> reviews;
	
	
	@Override
	public List<UserPratilipiDoc> getReviews() {
		return reviews == null
				? new ArrayList<UserPratilipiDoc>( 0 )
				: new ArrayList<UserPratilipiDoc>( reviews );
	}
	
	@Override
	public void setReviews( List<UserPratilipiDoc> reviews ) {
		if( reviews == null || reviews.size() == 0 ) {
			this.reviews = null;
		} else {
			this.reviews = new ArrayList<>( reviews.size() );
			for( UserPratilipiDoc reviewDoc : reviews )
				this.reviews.add( (UserPratilipiDocImpl) reviewDoc );
		}
	}

}