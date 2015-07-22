package com.pratilipi.taskqueue;

import java.util.HashMap;
import java.util.Map;

public class TaskQueueFactory {

	private static final Map<String, TaskQueue> taskQueueMap = new HashMap<>();
	
	private static final String QUEUE_PRATILIPI = "pratilipi";
	private static final String QUEUE_AUTHOR = "author";
	private static final String QUEUE_TEST = "updateBackEnd";
	
	
	public static TaskQueue getPratilipiTaskQueue() {
		return getTaskQueue( QUEUE_PRATILIPI );
	}

	public static TaskQueue getAuthorTaskQueue() {
		return getTaskQueue( QUEUE_AUTHOR );
	}
	
	public static TaskQueue getTestTaskQueue() {
		return getTaskQueue( QUEUE_TEST );
	}
	
	protected static TaskQueue getTaskQueue( String taskQueueName ) {
		TaskQueue taskQueue = taskQueueMap.get( taskQueueName );
		if( taskQueue == null ) {
			taskQueue = new TaskQueueGaeImpl( taskQueueName );
			taskQueueMap.put( taskQueueName, taskQueue );
		}
		return taskQueue;
	}
	
	public static Task newTask() {
		return new TaskGaeImpl();
	}
	
}
