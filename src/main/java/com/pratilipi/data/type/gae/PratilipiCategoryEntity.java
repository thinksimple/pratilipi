package com.pratilipi.data.type.gae;

import java.util.Date;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.pratilipi.data.type.PratilipiCategory;

@SuppressWarnings("serial")
@PersistenceCapable( table = "PRATILIPI_CATEGORY" )
public class PratilipiCategoryEntity implements PratilipiCategory {

	@PrimaryKey
	@Persistent( column = "PRATILIPI_CATEGORY_ID" )
	private String id;
	
	@Persistent( column = "PRATILIPI_ID" )
	private Long pratilipiId;
	
	@Persistent( column = "CATEGORY_ID" )
	private Long categoryId;
	
	@Persistent( column = "CREATION_DATE" )
	private Date creationDate;
	
	public void setId( String id ){
		this.id = id;
	}
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public Long getPratilipiId() {
		return pratilipiId;
	}

	@Override
	public void setPratilipiId( Long pratilipiId ) {
		this.pratilipiId = pratilipiId;
	}

	@Override
	public Long getCategoryId() {
		return categoryId;
	}

	@Override
	public void setCategoryId( Long categoryId ) {
		this.categoryId = categoryId;
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
