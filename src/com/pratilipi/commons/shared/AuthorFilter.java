package com.pratilipi.commons.shared;

import java.util.Date;

public class AuthorFilter {
	
	private Long languageId;
	
	private Date nextProcessDateEnd;

	private Boolean orderByContentPublished;
	
	
	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId( Long languageId ) {
		this.languageId = languageId;
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
