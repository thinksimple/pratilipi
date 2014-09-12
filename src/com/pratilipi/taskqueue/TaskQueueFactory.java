package com.pratilipi.taskqueue;

import com.claymus.taskqueue.TaskQueue;

public class TaskQueueFactory extends com.claymus.taskqueue.TaskQueueFactory {

	private static final String QUEUE_HTML_TO_PRATILIPI = "html-to-pratilipi";
	
	private static final String QUEUE_WORD_TO_PRATILIPI = "word-to-pratilipi";

	
	public static TaskQueue getHtmlToPrailipiTaskQueue() {
		return getTaskQueue( QUEUE_HTML_TO_PRATILIPI );
	}

	public static TaskQueue getWordToPrailipiTaskQueue() {
		return getTaskQueue( QUEUE_WORD_TO_PRATILIPI );
	}

}
