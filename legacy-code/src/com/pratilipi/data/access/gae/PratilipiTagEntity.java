package com.pratilipi.data.access.gae;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.data.transfer.PratilipiTag;

@SuppressWarnings("serial")
@PersistenceCapable( table = "PRATILIPI_TAG" )
public class PratilipiTagEntity implements PratilipiTag {

	@PrimaryKey
	@Persistent( column = "PRATILIPI_TAG_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;
	
	@Persistent( column = "PRATILIPI_ID" )
	private Long pratilipiId;
	
	@Persistent( column = "PRATILIPI_TYPE" )
	private PratilipiType pratilipiType;
	
	@Persistent( column = "TAG_ID" )
	private Long tagId;
	
	
	public Long getId() {
		return id;
	}
	
	public Long getPratilipiId() {
		return pratilipiId;
	}
	
	public void setPratilipiId( Long bookId ) {
		this.pratilipiId = bookId;
	}
	
	@Override
	public PratilipiType getPratilipiType() {
		return pratilipiType;
	}
	
	@Override
	public void setPratilipiType( PratilipiType pratilipiType ) {
		this.pratilipiType = pratilipiType;
	}

	public Long getTagId() {
		return tagId;
	}
	
	public void setTagId( Long tagId ) {
		this.tagId = tagId;
	}
	
}
