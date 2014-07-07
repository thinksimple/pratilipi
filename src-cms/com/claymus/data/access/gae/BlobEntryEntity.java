package com.claymus.data.access.gae;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.claymus.data.transfer.BlobEntry;

@PersistenceCapable( table = "BLOB_ENTRY" )
public class BlobEntryEntity implements BlobEntry {
	
	@PrimaryKey
	@Persistent( column = "BLOB_ENTRY_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;
	
	@Persistent( column = "NAME" )
	private String name;
	
	@Persistent( column = "SOURCE" )
	private Source source;
	
	@Persistent( column = "BLOB_ID" )
	private String blobId;
	
	@Persistent( column = "CREATION_DATE" )
	private Date creationDate;

	
	@Override
	public Long getId() {
		return id;
	}
	
	public void setId( Long id ) {
		this.id = id;
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
	public Source getSource() {
		return source;
	}
	
	@Override
	public void setSource( Source source ) {
		this.source = source;
	}
	
	@Override
	public String getBlobId() {
		return blobId;
	}
	
	@Override
	public void setBlobId( String blobId ) {
		this.blobId = blobId;
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
