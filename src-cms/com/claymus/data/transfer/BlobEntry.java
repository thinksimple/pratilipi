package com.claymus.data.transfer;

import java.util.Date;

public interface BlobEntry {
	
	public enum Source {
		AMAZON_S3,
		GOOGLE_APP_ENGINE,
		GOOGLE_CLOUD_STORAGE,
	}

	Long getId();

	String getName();
	
	void setName( String name );
	
	Source getSource();
	
	void setSource( Source source );
	
	String getBlobId();
	
	void setBlobId( String blobId );

	Date getCreationDate();
	
	void setCreationDate( Date creationDate );
	
}
