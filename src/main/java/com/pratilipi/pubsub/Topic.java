package com.pratilipi.pubsub;

import java.util.List;

import com.google.gson.JsonObject;

public interface Topic {

	void publish( JsonObject payload );

	void publishAll( JsonObject... payloads );

	void publishAll( List<JsonObject> payloadList );
	
}
