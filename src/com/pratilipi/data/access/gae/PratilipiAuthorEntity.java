package com.pratilipi.data.access.gae;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.data.transfer.PratilipiAuthor;

@PersistenceCapable( table = "PRATILIPI_AUTHOR" )
public class PratilipiAuthorEntity implements PratilipiAuthor {

	@PrimaryKey
	@Persistent( column = "PRATILIPI_AUTHOR_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;
	
	@Persistent( column = "PRATILIPI_ID" )
	private Long pratilipiId;
	
	@Persistent( column = "PRATILIPI_TYPE" )
	private PratilipiType pratilipiType;
	
	@Persistent( column = "AUTHOR_ID" )
	private Long authorId;
	
	
	@Override
	public Long getId() {
		return id;
	}
	
	@Override
	public Long getPratilipiId() {
		return pratilipiId;
	}
	
	@Override
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

	@Override
	public Long getAuthorId() {
		return authorId;
	}
	
	@Override
	public void setAuthorId( Long authorId ) {
		this.authorId = authorId;
	}
	
}
