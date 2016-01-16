package com.pratilipi.common.util;

import java.util.Date;

import com.pratilipi.common.type.Language;

public class AuthorFilter {
	
	private Language language;
	
	private Date minLastUpdated;
	private Boolean minLastUpdatedInclusive;
	
	private Date nextProcessDateEnd;

	private Boolean orderByContentPublished;
	
	
	public Language getLanguage() {
		return language;
	}

	public void setLanguage( Language language ) {
		this.language = language;
	}

	public Date getMinLastUpdated() {
		return minLastUpdated;
	}

	public boolean isMinLastUpdatedInclusive() {
		return minLastUpdatedInclusive == null ? false : minLastUpdatedInclusive;
	}
	
	public void setMinLastUpdate( Date minLastUpdated, boolean inclusive ) {
		this.minLastUpdated = minLastUpdated;
		this.minLastUpdatedInclusive = inclusive;
	}
	
	public Date getNextProcessDateEnd() {
		return nextProcessDateEnd;
	}

	public void setNextProcessDateEnd( Date nextProcessDateEnd ) {
		this.nextProcessDateEnd = nextProcessDateEnd;
	}
	
	public Boolean getOrderByContentPublished() {
		return orderByContentPublished;
	}

	public void setOrderByContentPublished( Boolean orderByContentPublished ) {
		this.orderByContentPublished = orderByContentPublished;
	}

}
