package com.claymus.data.access;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.data.transfer.BlobEntry;

public interface BlobAccessor {
	
	String createUploadUrl( String fileName );

	boolean createBlob( HttpServletRequest request, String fileName );
	
	BlobEntry getBlob( String fileName ) throws IOException;

	@Deprecated
	void serveBlob(
			String fileName,
			HttpServletResponse response ) throws IOException;
	
}
