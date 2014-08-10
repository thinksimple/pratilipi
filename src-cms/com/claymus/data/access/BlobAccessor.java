package com.claymus.data.access;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BlobAccessor {
	
	String createUploadUrl( String fileName );

	boolean createBlob( HttpServletRequest request );
	
	boolean serveBlob( HttpServletRequest request, HttpServletResponse response );
	
}
