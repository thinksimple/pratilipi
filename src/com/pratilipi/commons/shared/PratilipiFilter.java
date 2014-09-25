package com.pratilipi.commons.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PratilipiFilter implements IsSerializable {
	
	private PratilipiType type;

	private Boolean publicDomain;
	
	private Long languageId;
	
	private Long authorId;
	
	
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
	
	@Override
	public String toString() {
		return type + ":" + publicDomain + ":" + languageId + ":" + authorId;
	}

	public static PratilipiFilter fromString( String filterStr ) {
		String[] filters = filterStr.split( ":" );
		String typeStr = filters[0];
		String publicDomainStr = filters[1];
		String languageIdStr = filters[2];
		String authorIdStr = filters[3];
		
		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		pratilipiFilter.setType(
				typeStr.equals( "null" ) ? null : PratilipiType.valueOf( typeStr ) );
		pratilipiFilter.setPublicDomain(
				publicDomainStr.equals( "null" ) ? null : Boolean.parseBoolean( publicDomainStr ) );
		pratilipiFilter.setLanguageId(
				languageIdStr.equals( "null" ) ? null : Long.parseLong( languageIdStr ) );
		pratilipiFilter.setAuthorId(
				authorIdStr.equals( "null" ) ? null : Long.parseLong( authorIdStr ) );
		return pratilipiFilter;
	}

}
