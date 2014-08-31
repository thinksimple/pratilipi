package com.claymus.taskqueue;

import java.util.HashMap;
import java.util.Map;

public class TaskQueueFactory {

	private static final Map<String, TaskQueue> taskQueueMap = new HashMap<>();
	
	private static final String QUEUE_INVITE_USER = "invite-user";
	private static final String QUEUE_RESET_PASSWORD = "reset-password";
	
	
	public static TaskQueue getResetPasswordTaskQueue() {
		return getTaskQueue( QUEUE_RESET_PASSWORD );
	}

	public static TaskQueue getInviteUserTaskQueue() {
		return getTaskQueue( QUEUE_INVITE_USER );
	}

	private static TaskQueue getTaskQueue( String taskQueueName ) {
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
