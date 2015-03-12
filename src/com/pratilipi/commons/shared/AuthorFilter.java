package com.pratilipi.commons.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AuthorFilter implements IsSerializable {
	
	private Long languageId;
	
	private Date nextProcessDateEnd;

	
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
	
}
