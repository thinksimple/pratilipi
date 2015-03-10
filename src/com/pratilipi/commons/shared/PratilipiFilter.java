package com.pratilipi.commons.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PratilipiFilter implements IsSerializable {
	
	private PratilipiType type;

	private Boolean publicDomain;
	
	private Long languageId;
	
	private Long authorId;

	private Long publisherId;

	private PratilipiState state;
	
	private Date nextUpdateEnd;

	
	public PratilipiType getType() {
		return type;
	}

	public void setType( PratilipiType pratilipiType ) {
		this.type = pratilipiType;
	}

	public Boolean getPublicDomain() {
		return publicDomain;
	}

	public void setPublicDomain( Boolean publicDomain ) {
		this.publicDomain = publicDomain;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId( Long languageId ) {
		this.languageId = languageId;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId( Long authorId ) {
		this.authorId = authorId;
	}
	
	public Long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId( Long publisherId ) {
		this.publisherId = publisherId;
	}
	
	public PratilipiState getState() {
		return state;
	}

	public void setState( PratilipiState state ) {
		this.state = state;
	}
	
	public Date getNextUpdateEnd() {
		return nextUpdateEnd;
	}

	public void setNextUpdateEnd( Date nextUpdateEnd ) {
		this.nextUpdateEnd = nextUpdateEnd;
	}
	
}
