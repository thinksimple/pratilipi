package com.pratilipi.taskqueue;

import com.claymus.taskqueue.TaskQueue;

public class TaskQueueFactory extends com.claymus.taskqueue.TaskQueueFactory {

	private static final String QUEUE_PRATILIPI = "pratilipi";

	private static final String QUEUE_CREATE_OR_UPDATE_DEFAULT_COVER = "create-or-update-default-cover";
	
	private static final String QUEUE_HTML_TO_PRATILIPI = "html-to-pratilipi";
	
	private static final String QUEUE_WORD_TO_PRATILIPI = "word-to-pratilipi";

	private static final String QUEUE_UPDATE_INDEX_PRATILIPI = "update-index-pratilipi";

	private static final String QUEUE_UPDATE_INDEX_AUTHOR = "update-index-author";

	
	public static TaskQueue getPratilipiTaskQueue() {
		return getTaskQueue( QUEUE_PRATILIPI );
	}

	public static TaskQueue getCreateOrUpdateDefaultCoverTaskQueue() {
		return getTaskQueue( QUEUE_CREATE_OR_UPDATE_DEFAULT_COVER );
	}

	public static TaskQueue getHtmlToPrailipiTaskQueue() {
		return getTaskQueue( QUEUE_HTML_TO_PRATILIPI );
	}

	public static TaskQueue getWordToPrailipiTaskQueue() {
		return getTaskQueue( QUEUE_WORD_TO_PRATILIPI );
	}

	public static TaskQueue getUpdatePratilipiIndexQueue() {
		return getTaskQueue( QUEUE_UPDATE_INDEX_PRATILIPI );
	}

	public static TaskQueue getUpdateAuthorIndexQueue() {
		return getTaskQueue( QUEUE_UPDATE_INDEX_AUTHOR );
	}

}
