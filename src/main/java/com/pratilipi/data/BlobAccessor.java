package com.pratilipi.data;

import java.io.IOException;
import java.util.List;

import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.type.BlobEntry;

public interface BlobAccessor {
	
	BlobEntry newBlob( String fileName );

	BlobEntry newBlob( String fileName, byte[] data, String mimeType );

	List<String> getNameList( String prefix ) throws IOException;
	
	BlobEntry getBlob( String fileName ) throws UnexpectedServerException;

	void createOrUpdateBlob( BlobEntry blobEntry ) throws UnexpectedServerException;
	
	void deleteBlob( BlobEntry blobEntry ) throws UnexpectedServerException;

}
