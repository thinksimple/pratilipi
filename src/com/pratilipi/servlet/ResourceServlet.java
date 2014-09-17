package com.pratilipi.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.claymus.data.transfer.BlobEntry;
import com.claymus.taskqueue.Task;
import com.claymus.taskqueue.TaskQueue;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
public class ResourceServlet extends com.claymus.servlet.ResourceServlet {
	
	@Override
	protected void postUpload( HttpServletRequest request ) {

		String url = request.getRequestURI();
		String pratilipiIdStr = url.substring( url.lastIndexOf( '/' ) + 1 );
		
		Task task = TaskQueueFactory.newTask();
		task.addParam( "pratilipiId", pratilipiIdStr );

		for( PratilipiType pratilipiType : PratilipiType.values() ) {
			
			if( url.startsWith( pratilipiType.getContentHtmlUrl() ) ) {
				task.addParam( "pratilipiType", pratilipiType.toString() );
				TaskQueue taskQueue = TaskQueueFactory.getHtmlToPrailipiTaskQueue();
				taskQueue.add( task );
				break;
			
			}
			
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
