package com.claymus.data.access;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BlobAccessor {
	
	String createUploadUrl( String fileName );

	boolean createBlob( HttpServletRequest request );
	
	void serveBlob(
			String fileName,
			HttpServletResponse response ) throws IOException;
	
}
