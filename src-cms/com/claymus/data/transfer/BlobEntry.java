package com.claymus.data.transfer;

import java.util.Date;

public interface BlobEntry {
	
	public enum Source {
		AMAZON_S3,
		GOOGLE_APP_ENGINE,
		GOOGLE_CLOUD_STORAGE,
	}

	public enum Type {
		TXT, PDF, DOC,
	}

	
	Long getId();

	String getName();
	
	void setName( String name );

	Type getType();
	
	void setType( Type type );
	
	Long getSize();
	
	void setSize( Long size );
	
	Source getSource();
	
	void setSource( Source source );
	
	String getBlobId();
	
	void setBlobId( String blobId );

	Date getCreationDate();
	
	void setCreationDate( Date creationDate );
	
}
