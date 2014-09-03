package com.pratilipi.data.access.gae;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.pratilipi.data.transfer.Genre;

@PersistenceCapable( table = "GENRE" )
public class GenreEntity implements Genre {

	@PrimaryKey
	@Persistent( column = "GENRE_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;
	
	@Persistent( column = "NAME" )
	private String name;
	
	@Persistent( column = "CREATION_DATE" )
	private Date creationDate;
	
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName( String name ) {
		this.name = name;
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
