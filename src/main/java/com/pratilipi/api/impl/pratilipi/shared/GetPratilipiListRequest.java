package com.pratilipi.api.impl.pratilipi.shared;

import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.type.PratilipiType;

public class GetPratilipiListRequest extends GenericRequest {

	private String searchQuery;
	private Long authorId;
	private Language language;
	private PratilipiType type;
	private String listName;
	private PratilipiState state;

	private Boolean orderByLastUpdated;

	private String cursor;
	private Integer offset;
	private Integer resultCount;


	public String getSearchQuery() {
		return searchQuery;
	}

	public void setSearchQuery( String searchQuery ) {
		this.searchQuery = searchQuery;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId( Long authorId ) {
		this.authorId = authorId;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage( Language language ) {
		this.language = language;
	}

	public PratilipiType getType() {
		return type;
	}

	public void setType( PratilipiType type ) {
		this.type = type;
	}

	public String getListName() {
		return listName;
	}

	public void setListName( String listName ) {
		this.listName = listName;
	}

	public PratilipiState getState() {
		return state;
	}

	public void setState( PratilipiState state ) {
		this.state = state;
	}


	public Boolean getOrderByLastUpdate() {
		return orderByLastUpdated;
	}

	public void setOrderByLastUpdate( Boolean orderByLastUpdated ) {
		this.orderByLastUpdated = orderByLastUpdated;
	}


	public String getCursor() {
		return cursor;
	}

	public void setCursor( String cursor ) {
		this.cursor = cursor;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset( Integer offset ) {
		this.offset = offset;
	}

	public Integer getResultCount() {
		return resultCount;
	}

	public void setResultCount( Integer resultCount ) {
		this.resultCount = resultCount;
	}

}
