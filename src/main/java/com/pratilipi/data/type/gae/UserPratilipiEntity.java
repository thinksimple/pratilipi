package com.pratilipi.data.type.gae;

import java.util.Date;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;
import com.pratilipi.common.type.SellerType;
import com.pratilipi.common.type.UserReviewState;
import com.pratilipi.data.type.UserPratilipi;

@PersistenceCapable( table = "USER_PRATILIPI" )
public class UserPratilipiEntity implements UserPratilipi {

	private static final long serialVersionUID = 2207445050785647416L;

	@PrimaryKey
	@Persistent( column = "USER_PRATILIPI_ID" )
	private String id;
	
	@Persistent( column = "USER_ID" )
	private Long userId;
	
	@Persistent( column = "PRATILIPI_ID" )
	private Long pratilipiId;

	
	@Persistent( column = "LAST_OPENED_PAGE" )
	private Integer lastOpenedPage;
	
	@Persistent( column = "LAST_OPENED_DATE" )
	private Date lastOpenedDate;

	
	@Deprecated
	@Persistent( column = "PURCHASED_FROM" )
	private SellerType purchasedFrom;
	
	@Deprecated
	@Persistent( column = "PURCHASE_DATE" )
	private Date purchaseDate;

	
	@Persistent( column = "RATING" )
	private Integer rating;
	
	@Persistent( column = "RATING_DATE" )
	private Date ratingDate;

	@Persistent( column = "REVIEW_TITLE" )
	private String reviewTitle;

	@Persistent( column = "REVIEW" )
	private Text review;
	
	@Persistent( column = "REVIEW_STATE" )
	private UserReviewState reviewState;
	
	@Persistent( column = "REVIEW_DATE" )
	private Date reviewDate;

	
	@Persistent( column = "ADDED_TO_LIB" )
	private Boolean addedToLib;
	
	
	public UserPratilipiEntity() {}
	
	public UserPratilipiEntity( Long userId, Long pratilipiId ) {
		this.id = userId + "-" + pratilipiId;
		this.userId = userId;
		this.pratilipiId = pratilipiId;
	}

	
	public void setId( String id ) {
		this.id = id;
	}
	
	@Override
	public String getId() {
		return this.id;
	}
	
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
	public Integer getLastOpenedPage() {
		return lastOpenedPage;
	}
	
	@Override
	public void setLastOpenedPage( Integer lastOpenedPage ) {
		this.lastOpenedPage = lastOpenedPage;
	}

	@Override
	public Date getLastOpenedDate() {
		return lastOpenedDate;
	}
	
	@Override
	public void setLastOpenedDate( Date lastOpenedDate ) {
		this.lastOpenedDate = lastOpenedDate;
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
	public Date getRatingDate() {
		return ratingDate;
	}
	
	@Override
	public void setRatingDate( Date ratingDate ) {
		this.ratingDate = ratingDate;
	}
	

	@Override
	public String getReviewTitle() {
		return reviewTitle;
	}

	@Override
	public void setReviewTitle( String reviewTitle ) {
		this.reviewTitle = reviewTitle;
	}
	
	@Override
	public String getReview() {
		return review == null ? null : review.getValue();
	}

	@Override
	public void setReview( String review ) {
		this.review = new Text( review );
	}
	
	@Override
	public UserReviewState getReviewState() {
		return reviewState;
	}
	
	@Override
	public void setReviewState( UserReviewState reviewState ) {
		this.reviewState = reviewState;
	}

	@Override
	public Date getReviewDate() {
		return reviewDate;
	}
	
	@Override
	public void setReviewDate( Date reviewDate ) {
		this.reviewDate = reviewDate;
	}
	

	@Override
	public Boolean isAddedtoLib() {
		return this.addedToLib;
	}

	@Override
	public void setAddedToLib(Boolean addedToLib) {
		this.addedToLib = addedToLib;
	}

}
