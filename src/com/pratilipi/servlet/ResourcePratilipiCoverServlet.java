package com.pratilipi.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.data.transfer.BlobEntry;
import com.claymus.servlet.ResourceServlet;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;

//Servlet Version: 4.0; Owner Module: PratilipiContent;
@SuppressWarnings("serial")
public class ResourcePratilipiCoverServlet extends ResourceServlet {
	
	@Override
	public void doPost(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {
		
		String url = request.getRequestURI();
		String pratilipiIdStr = url.substring( url.lastIndexOf( '/' ) + 1 );
		Long pratilipiId = Long.parseLong( pratilipiIdStr );

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );

		if( ! PratilipiContentHelper.hasRequestAccessToUpdatePratilipiData( request, pratilipi ) ) {
			response.setStatus( HttpServletResponse.SC_FORBIDDEN );
			return;
		}
		
		super.doPost( request, response );
	}
	
	@Override
	protected void postUpload( HttpServletRequest request ) throws IOException {

		// Creating a 300px wide copy
		BlobEntry blobEntry = super.getBlobEntry( request );

		Map<String, String> metaDataMap = new HashMap<>();
		metaDataMap.put( "default", "false" );
		
		ImagesService imagesService = ImagesServiceFactory.getImagesService();
		Transform resize = ImagesServiceFactory.makeResize( 300, 1000 );
		Image oldImage = ImagesServiceFactory.makeImage( blobEntry.getData() );
		Image newImage = imagesService.applyTransform( resize, oldImage );
		
		DataAccessorFactory.getBlobAccessor().createBlob(
				getFileName( request ).replace( "/original/", "/300/" ),
				blobEntry.getMimeType(), newImage.getImageData(),
				"public-read", metaDataMap );
	}
	
}
