package com.claymus.data.transfer;

import java.util.Date;

public interface BlobEntry {
	
	String getName();

	byte[] getData();

	String getMimeType();

	String getETag();
	
	Date getLastModified();

}
