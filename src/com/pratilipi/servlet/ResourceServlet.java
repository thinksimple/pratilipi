package com.pratilipi.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.data.transfer.BlobEntry;
import com.claymus.taskqueue.Task;
import com.claymus.taskqueue.TaskQueue;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
public class ResourceServlet extends com.claymus.servlet.ResourceServlet {
	
	@Override
	protected void postUpload( HttpServletRequest request ) throws IOException {

		String url = request.getRequestURI();
		String pratilipiIdStr = url.substring( url.lastIndexOf( '/' ) + 1 );
		
		Task task = TaskQueueFactory.newTask();
		task.addParam( "pratilipiId", pratilipiIdStr );

		for( PratilipiType pratilipiType : PratilipiType.values() ) {

			// Pratilipi Cover: creating a 300px wide copy
			if( url.startsWith( pratilipiType.getCoverImageUrl() ) ) {
				BlobEntry blobEntry = super.getBlobEntry( request );

				Map<String, String> metaDataMap = new HashMap<>();
				metaDataMap.put( "default", "false" );
				
				ImagesService imagesService = ImagesServiceFactory.getImagesService();
		        Transform resize = ImagesServiceFactory.makeResize( 300, 1000 );
		        Image oldImage = ImagesServiceFactory.makeImage( blobEntry.getData() );
		        Image newImage = imagesService.applyTransform( resize, oldImage );
		        
		        DataAccessorFactory.getBlobAccessor().createBlob(
		        		pratilipiType.getCoverImage300Resource() + pratilipiIdStr,
		        		blobEntry.getMimeType(), newImage.getImageData(),
		        		"public-read", metaDataMap );
		        
		        break;
			}

			// Pratilipi Content (Html Format)
			if( url.startsWith( pratilipiType.getContentHtmlUrl() ) ) {
				task.addParam( "pratilipiType", pratilipiType.toString() );
				TaskQueue taskQueue = TaskQueueFactory.getHtmlToPrailipiTaskQueue();
				taskQueue.add( task );
				break;
			
			}
			
			// Pratilipi Content (Word Format)
			if( url.startsWith( pratilipiType.getContentWordUrl() ) ) {
				task.addParam( "pratilipiType", pratilipiType.toString() );
				TaskQueue taskQueue = TaskQueueFactory.getWordToPrailipiTaskQueue();
				taskQueue.add( task );
				break;
			}
			
		}
	}
	
	@Override
	protected BlobEntry getBlobEntry( HttpServletRequest request ) throws IOException {
		BlobEntry blobEntry = super.getBlobEntry( request );
		if( blobEntry == null ) {
			String url = request.getRequestURI();
			String pratilipiIdStr = url.substring( url.lastIndexOf( '/' ) + 1 );
			Long pratilipiId = Long.parseLong( pratilipiIdStr );
			for( PratilipiType pratilipiType : PratilipiType.values() ) {
				if( url.startsWith( pratilipiType.getCoverImageUrl() ) ) {
					DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
					Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId ); 
					dataAccessor.destroy();
					
					String fileName = pratilipiType.getCoverImageResource();
					if( pratilipi.isPublicDomain() )
						fileName = fileName + "pratilipi-classic-" + pratilipi.getLanguageId();
					else
						fileName = fileName + "pratilipi";
					blobEntry = DataAccessorFactory.getBlobAccessor().getBlob( fileName );
				}
			}
		}
		return blobEntry;
	}

}
