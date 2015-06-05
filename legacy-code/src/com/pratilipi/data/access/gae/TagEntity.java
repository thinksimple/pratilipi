package com.pratilipi.data.access.gae;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.pratilipi.data.transfer.Tag;

@SuppressWarnings("serial")
@PersistenceCapable( table = "TAG" )
public class TagEntity implements Tag {

	@PrimaryKey
	@Persistent( column = "TAG_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;
	
	@Persistent( column = "NAME" )
	private String name;
	
	@Persistent( column = "CREATION_DATE" )
	private Date creationDate;
	
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName( String name ) {
		this.name = name;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate( Date creationDate ) {
		this.creationDate = creationDate;
	}
	
}
