package com.pratilipi.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.claymus.data.transfer.BlobEntry;
import com.claymus.servlet.ResourceServlet;
import com.pratilipi.data.access.DataAccessorFactory;

// Servlet Version: 4.0; Owner Module: AuthorContent;
@SuppressWarnings("serial")
public class ResourceAuthorImageServlet extends ResourceServlet {
	
	@Override
	protected BlobEntry getBlobEntry( HttpServletRequest request ) throws IOException {

		BlobEntry blobEntry = super.getBlobEntry( request );
		if( blobEntry == null ) { // Setting default image
			String fileName = getFileName( request );
			fileName = fileName.substring( 0, fileName.lastIndexOf( '/' ) + 1 ) + "author";
			blobEntry = DataAccessorFactory.getBlobAccessor().getBlob( fileName );
		}
		return blobEntry;
		
	}
	
}
