package com.pratilipi.taskqueue;

import com.claymus.taskqueue.TaskQueue;

public class TaskQueueFactory extends com.claymus.taskqueue.TaskQueueFactory {

	private static final String QUEUE_PRATILIPI = "pratilipi";

	private static final String QUEUE_AUTHOR = "author";

	private static final String QUEUE_CREATE_OR_UPDATE_DEFAULT_COVER = "create-or-update-default-cover";

	
	public static TaskQueue getPratilipiTaskQueue() {
		return getTaskQueue( QUEUE_PRATILIPI );
	}

	public static TaskQueue getAuthorTaskQueue() {
		return getTaskQueue( QUEUE_AUTHOR );
	}

	public static TaskQueue getCreateOrUpdateDefaultCoverTaskQueue() {
		return getTaskQueue( QUEUE_CREATE_OR_UPDATE_DEFAULT_COVER );
	}

}
