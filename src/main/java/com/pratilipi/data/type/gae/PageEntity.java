package com.pratilipi.data.type.gae;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.pratilipi.common.type.PageType;
import com.pratilipi.data.type.Page;

@SuppressWarnings("serial")
@PersistenceCapable( table = "PAGE" )
public class PageEntity implements Page {

	@PrimaryKey
	@Persistent( column = "PAGE_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;
	
	@Persistent( column = "PAGE_TYPE" )
	private PageType type;

	@Persistent( column = "URI" )
	private String uri;

	@Persistent( column = "URI_ALIAS" )
	private String uriAlias;

	@Persistent( column = "PRIMARY_CONTENT_ID" )
	private Long primaryContentId;
	
	@Persistent( column = "CREATION_DATE" )
	private Date creationDate;
	
	
	public PageEntity() {}
	
	public PageEntity( Long id ) {
		this.id = id;
	}
	
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public PageType getType() {
		return type;
	}

	@Override
	public void setType( PageType type ) {
		this.type = type;
	}

	@Override
	public String getUri() {
		return uri;
	}

	@Override
	public void setUri( String uri ) {
		this.uri = uri;
	}

	@Override
	public String getUriAlias() {
		return uriAlias;
	}

	@Override
	public void setUriAlias( String uri ) {
		this.uriAlias = uri;
	}

	@Override
	public Long getPrimaryContentId() {
		return primaryContentId;
	}

	@Override
	public void setPrimaryContentId( Long pageContentId ) {
		this.primaryContentId = pageContentId;
	}

	@Override
	public Date getCreationDate() {
		return creationDate;
	}

	@Override
	public void setCreationDate( Date creationDate ) {
		this.creationDate = creationDate;
	}

}
