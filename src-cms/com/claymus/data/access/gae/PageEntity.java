package com.claymus.data.access.gae;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.claymus.data.transfer.Page;

@PersistenceCapable( table = "PAGE" )
public class PageEntity implements Page {

	@PrimaryKey
	@Persistent( column = "PAGE_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;
	
	@Persistent( column = "URI" )
	private String uri;

	@Persistent( column = "TITLE" )
	private String title;
	
	@Persistent( column = "LAYOUT_ID" )
	private Long layoutId;
	
	@Persistent( column = "CREATION_DATE" )
	private Date creationDate;
	
	
	@Override
	public Long getId() {
		return id;
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
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public Long getLayoutId() {
		return layoutId;
	}

	@Override
	public void setLayout( Long layoutId ) {
		this.layoutId = layoutId;
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
