package com.pratilipi.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.data.access.BlobAccessor;
import com.claymus.data.transfer.BlobEntry;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Pratilipi;

@SuppressWarnings("serial")
public class QueueCreateOrUpdateDefaultCoverServlet extends HttpServlet {
	
	@Override
	public void doPost(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {

		String pratilipiIdStr = request.getParameter( "pratilipiId" );
		Long pratilipiId = Long.parseLong( pratilipiIdStr );

		
		// Fetching Pratilipi entity
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
	
		
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
		String coverImage = PratilipiHelper.getCoverImage300( pratilipiId );
		BlobEntry blobEntry = blobAccessor.getBlob( coverImage );
		if( blobEntry == null || blobEntry.getMetaData( "default" ).equals( "true" ) ) {
			String originalCoverImage =
					PratilipiHelper.getCoverImage( null ) + "pratilipi" ;
			if( pratilipi.isPublicDomain() )
				originalCoverImage += "-classic-" + pratilipi.getLanguageId();
			blobEntry = blobAccessor.getBlob( originalCoverImage );
			
			ImagesService imagesService = ImagesServiceFactory.getImagesService();
			Transform resize = ImagesServiceFactory.makeResize( 300, 1000 );
			Image oldImage = ImagesServiceFactory.makeImage( blobEntry.getData() );
			Image newImage = imagesService.applyTransform( resize, oldImage );
			
			Map<String, String> metaDataMap = new HashMap<>();
			metaDataMap.put( "default", "true" );
			blobAccessor.createBlob(
					coverImage,
					blobEntry.getMimeType(), newImage.getImageData(),
					"public-read", metaDataMap );
		}

	}

}
