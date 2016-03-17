package com.pratilipi.common.util;

public class UserPratilipiFilter {
	
	private Long userId;

	private Long pratilipiId;
	
	private Boolean addedToLib;
	
	private Boolean orderByAddedToLibDate;

	
	public Long getUserId() {
		return userId;
	}

	public void setUserId( Long userId ) {
		this.userId = userId;
	}
	
	public Long getPratilipiId() {
		return pratilipiId;
	}

	public void setPratilipiId( Long pratilipiId ) {
		this.pratilipiId = pratilipiId;
	}
	
	public Boolean getAddedToLib() {
		return addedToLib;
	}
	
	public void setAddedToLib( Boolean addedToLib ) {
		this.addedToLib = addedToLib;
	}
	
	public Boolean getOrderByAddedToLibDate() {
		return orderByAddedToLibDate;
	}
	
	public void setOrderByAddedToLibDate( Boolean orderByAddedToLibDate ) {
		this.orderByAddedToLibDate = orderByAddedToLibDate;
	}

}
