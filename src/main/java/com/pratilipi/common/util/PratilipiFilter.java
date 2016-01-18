package com.pratilipi.common.util;

import java.io.Serializable;
import java.util.Date;

import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.type.PratilipiType;

public class PratilipiFilter implements Serializable {
	
	private static final long serialVersionUID = 6769002082197329230L;

	
	private Long authorId;
	private Language language;
	private PratilipiType type;
	private PratilipiState state;
	
	private Date nextProcessDateEnd;

	private Boolean orderByReadCount;
	private Boolean orderByLastUpdated;
	
	
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

	public void setType( PratilipiType pratilipiType ) {
		this.type = pratilipiType;
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
	
	public Boolean getOrderByLastUpdate() {
		return orderByLastUpdated;
	}

	public void setOrderByLastUpdate( Boolean orderByLastUpdated ) {
		this.orderByLastUpdated = orderByLastUpdated;
	}

	
	public String toUrlEncodedString() {
		String urlEncodedString = "";
		
		if( language != null )
			urlEncodedString += "&language=" + language.toString();
		if( authorId != null )
			urlEncodedString += "&authorId=" + authorId;
		if( type != null )
			urlEncodedString += "&type=" + type.toString();
		
		return urlEncodedString.length() == 0 ? "" : urlEncodedString.substring( 1 );
	}
	
}
