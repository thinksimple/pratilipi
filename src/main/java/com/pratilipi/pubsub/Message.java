package com.pratilipi.pubsub;

import java.util.Date;

import com.google.gson.JsonObject;

public interface Message {

	JsonObject getPayload();
	
	Date getPublishTime();

	void acknowledge();
	
}
