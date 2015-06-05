package com.pratilipi.taskqueue;

import com.claymus.taskqueue.TaskQueue;

public class TaskQueueFactory extends com.claymus.taskqueue.TaskQueueFactory {

	private static final String QUEUE_PRATILIPI = "pratilipi";

	private static final String QUEUE_AUTHOR = "author";
	
	private static final String QUEUE_NOTIFICATION = "notification";

	
	public static TaskQueue getPratilipiTaskQueue() {
		return getTaskQueue( QUEUE_PRATILIPI );
	}

	public static TaskQueue getAuthorTaskQueue() {
		return getTaskQueue( QUEUE_AUTHOR );
	}
	
	public static TaskQueue getNotificationTaskQueue(){
		return getTaskQueue( QUEUE_NOTIFICATION );
	}

}
