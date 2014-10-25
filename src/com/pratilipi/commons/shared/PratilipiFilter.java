package com.pratilipi.commons.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PratilipiFilter implements IsSerializable {
	
	private PratilipiType type;

	private Boolean publicDomain;
	
	private Long languageId;
	
	private Long authorId;

	private PratilipiState state;
	

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
	
	public PratilipiState getState() {
		return state;
	}

	public void setState( PratilipiState state ) {
		this.state = state;
	}
	
}
