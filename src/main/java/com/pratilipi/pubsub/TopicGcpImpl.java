package com.pratilipi.pubsub;

import java.util.ArrayList;
import java.util.List;

import com.google.cloud.pubsub.Message;
import com.google.cloud.pubsub.PubSubOptions;
import com.google.gson.JsonObject;

public class TopicGcpImpl implements Topic {

	private final com.google.cloud.pubsub.Topic topic;
	
	
	public TopicGcpImpl( String topicName ) {
		topic = PubSubOptions.defaultInstance().service().getTopic( topicName );
	}


	@Override
	public void publish( JsonObject payload ) {
		topic.publish( Message.of( payload.toString() ) );
	}

	@Override
	public void publishAll( JsonObject... payloads ) {
		List<Message> messageList = new ArrayList<>( payloads.length );
		for( JsonObject payload : payloads )
			messageList.add( Message.of( payload.toString() ) );
		topic.publish( messageList );
	}

	@Override
	public void publishAll( List<JsonObject> payloadList ) {
		List<Message> messageList = new ArrayList<>( payloadList.size() );
		for( JsonObject payload : payloadList )
			messageList.add( Message.of( payload.toString() ) );
		topic.publish( messageList );
	}
	
}
