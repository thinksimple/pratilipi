package com.pratilipi.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.data.transfer.BlobEntry;
import com.claymus.taskqueue.Task;
import com.claymus.taskqueue.TaskQueue;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.pagecontent.author.AuthorContentHelper;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
public class ResourceServlet extends com.claymus.servlet.ResourceServlet {
	
	@Override
	public void doPost(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {
		
		String url = request.getRequestURI();
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		// Checking if user has access to upload Cover Image
		for( PratilipiType pratilipiType : PratilipiType.values() ) {
			if( url.startsWith( PratilipiHelper.getCoverImageUrl( pratilipiType, null, true ) ) ) {
				String pratilipiIdStr = url.substring( PratilipiHelper.getCoverImageUrl( pratilipiType, null, true ).length() );
				Long pratilipiId = Long.parseLong( pratilipiIdStr );
				Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
				
				if( PratilipiContentHelper.hasRequestAccessToUpdatePratilipiData( request, pratilipi ) ) {
					super.doPost( request, response );
					return;
				}
			}
		}
			
		response.setStatus( HttpServletResponse.SC_FORBIDDEN );
	}
	
	@Override
	protected void postUpload( HttpServletRequest request ) throws IOException {

		String url = request.getRequestURI();
		String pratilipiIdStr = url.substring( url.lastIndexOf( '/' ) + 1 );
		Long pratilipiId = Long.parseLong( pratilipiIdStr );
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
		PratilipiType pratilipiType = pratilipi.getType();
		
		// Pratilipi Cover: creating a 300px wide copy
		if( url.equals( PratilipiHelper.getCoverImageUrl( pratilipiType, pratilipiId ) ) ) {
			BlobEntry blobEntry = super.getBlobEntry( request );

			Map<String, String> metaDataMap = new HashMap<>();
			metaDataMap.put( "default", "false" );
			
			ImagesService imagesService = ImagesServiceFactory.getImagesService();
			Transform resize = ImagesServiceFactory.makeResize( 300, 1000 );
			Image oldImage = ImagesServiceFactory.makeImage( blobEntry.getData() );
			Image newImage = imagesService.applyTransform( resize, oldImage );
			
			DataAccessorFactory.getBlobAccessor().createBlob(
					PratilipiHelper.getCoverImage300( pratilipiId ),
					blobEntry.getMimeType(), newImage.getImageData(),
					"public-read", metaDataMap );

			
		// Pratilipi Content (Html Format)
		} else if( url.equals( PratilipiHelper.getContentHtmlUrl( pratilipiId ) ) ) {
			Task task = TaskQueueFactory.newTask();
			task.addParam( "pratilipiId", pratilipiIdStr );
			task.addParam( "pratilipiType", pratilipiType.toString() );
			TaskQueue taskQueue = TaskQueueFactory.getHtmlToPrailipiTaskQueue();
			taskQueue.add( task );
			
			
		// Pratilipi Content (Word Format)
		} else if( url.equals( PratilipiHelper.getContentWordUrl( pratilipiId ) ) ) {
			Task task = TaskQueueFactory.newTask();
			task.addParam( "pratilipiId", pratilipiIdStr );
			task.addParam( "pratilipiType", pratilipiType.toString() );
			TaskQueue taskQueue = TaskQueueFactory.getWordToPrailipiTaskQueue();
			taskQueue.add( task );
		
		
		}
		
		dataAccessor.destroy();
	}
	
}
