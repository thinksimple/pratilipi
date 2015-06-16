package com.pratilipi.common.util;

import java.util.Date;

import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.type.PratilipiType;

public class PratilipiFilter {
	
	private PratilipiType type;

	private String langCode;
	
	private Long authorId;

	private PratilipiState state;
	
	private Date nextProcessDateEnd;

	private Boolean orderByReadCount;
	
	
	public PratilipiType getType() {
		return type;
	}

	public void setType( PratilipiType pratilipiType ) {
		this.type = pratilipiType;
	}

	public String getLanguageCode() {
		return langCode;
	}

	public void setLanguageCode( String langCode ) {
		this.langCode = langCode;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId( Long authorId ) {
		this.authorId = authorId;
	}
	
	public PratilipiState getState() {
		return state;
	}

	public void setState( PratilipiState state ) {
		this.state = state;
	}
	
	public Date getNextProcessDateEnd() {
		return nextProcessDateEnd;
	}

	public void setNextProcessDateEnd( Date nextProcessDateEnd ) {
		this.nextProcessDateEnd = nextProcessDateEnd;
	}
	
	public Boolean getOrderByReadCount() {
		return orderByReadCount;
	}

	public void setOrderByReadCount( Boolean orderByReadCount ) {
		this.orderByReadCount = orderByReadCount;
	}
	
}
