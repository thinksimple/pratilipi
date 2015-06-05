package com.pratilipi.pagecontent.author.api.shared;

import com.claymus.api.shared.GenericRequest;

@SuppressWarnings( "serial" )
public class GetAuthorListRequest extends GenericRequest {

	private Long languageId;

	private Boolean orderByContentPublished;

	private String cursor;
	
	private Integer resultCount;
	
	
	public Long getLanguageId(){
		return this.languageId;
	}
	
	public void setLanguageId( Long languageId ) {
		this.languageId = languageId;
	}
	
	public Boolean getOrderByContentPublished() {
		return orderByContentPublished;
	}

	public void setOrderByContentPublished( Boolean orderByContentPublished ) {
		this.orderByContentPublished = orderByContentPublished;
	}

	public String getCursor() {
		return cursor;
	}
	
	public void setCursor( String cursor ) {
		this.cursor = cursor;
	}
	
	public Integer getResultCount() {
		return resultCount;
	}
	
	public void setResultCount( Integer resultCount ) {
		this.resultCount = resultCount;
	}
	
}
