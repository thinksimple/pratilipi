package com.pratilipi.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.data.transfer.BlobEntry;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.pagecontent.author.AuthorContentHelper;

// Servlet Version: 4.0; Owner Module: AuthorContent;
@SuppressWarnings("serial")
public class ResourceAuthorImageServlet extends com.claymus.servlet.ResourceServlet {
	
	@Override
	public void doPost(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {
		
		String url = request.getRequestURI();
		String authorIdStr = url.substring( url.lastIndexOf( '/' ) + 1 );
		Long authorId = Long.parseLong( authorIdStr );

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Author author = dataAccessor.getAuthor( authorId );
		
		if( ! AuthorContentHelper.hasRequestAccessToUpdateAuthorData( request, author ) ) {
			response.setStatus( HttpServletResponse.SC_FORBIDDEN );
			return;
		}
		
		super.doPost( request, response );
	}
	
	@Override
	protected BlobEntry getBlobEntry( HttpServletRequest request ) throws IOException {

		BlobEntry blobEntry = super.getBlobEntry( request );
		if( blobEntry == null ) { // Setting default image
			String url = request.getRequestURI();
			String fileName = url.substring( 0, url.lastIndexOf( '/' ) + 1 ) + "author";
			blobEntry = DataAccessorFactory.getBlobAccessor().getBlob( fileName );
		}
		return blobEntry;
	}
	
}
