package com.pratilipi.data.access.gae;

import java.util.Date;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;
import com.pratilipi.commons.shared.SellerType;
import com.pratilipi.commons.shared.UserReviewState;
import com.pratilipi.data.transfer.UserPratilipi;

@SuppressWarnings("serial")
@PersistenceCapable( table = "USER_PRATILIPI" )
public class UserPratilipiEntity implements UserPratilipi {

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

	@Persistent( column = "PURCHASED_FROM" )
	private SellerType purchasedFrom;
	
	@Persistent( column = "PURCHASE_DATE" )
	private Date purchaseDate;

	@Persistent( column = "RATING" )
	private Integer rating;
	
	@Persistent( column = "REVIEW" )
	private Text review;
	
	@Persistent( column = "REVIEW_STATE" )
	private UserReviewState reviewState;
	
	@Persistent( column = "REVIEW_DATE" )
	private Date reviewDate;
	
	@Persistent( column = "BOOKMARKS" )
	private Text bookmarks; 

	
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
	public SellerType getPurchasedFrom() {
		return purchasedFrom;
	}
	
	@Override
	public void setPurchasedFrom( SellerType purchasedFrom ) {
		this.purchasedFrom = purchasedFrom;
	}

	@Override
	public Date getPurchaseDate() {
		return this.purchaseDate;
	}

	@Override
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
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
	public String getBookmarks() {
		return bookmarks == null ? null : bookmarks.getValue();
	}

	@Override
	public void setBookmarks( String bookmarks) {
		this.bookmarks = new Text( bookmarks );
	}

}
