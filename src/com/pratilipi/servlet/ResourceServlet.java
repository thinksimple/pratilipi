package com.pratilipi.servlet;

import javax.servlet.http.HttpServletRequest;

import com.claymus.taskqueue.Task;
import com.claymus.taskqueue.TaskQueue;
import com.pratilipi.commons.shared.PratilipiType;
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
			if( url.startsWith( pratilipiType.getContentWordUrl() ) ) {
				task.addParam( "pratilipiType", pratilipiType.toString() );
				break;
			}
		}
		
		TaskQueue taskQueue = TaskQueueFactory.getWordToPrailipiTaskQueue();
		taskQueue.add( task );
	}
	
}
