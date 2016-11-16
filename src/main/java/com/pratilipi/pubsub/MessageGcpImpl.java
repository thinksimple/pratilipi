package com.pratilipi.pubsub;

import java.util.Date;

import com.google.cloud.pubsub.ReceivedMessage;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class MessageGcpImpl implements Message {
	
	private ReceivedMessage message;

	
	MessageGcpImpl( ReceivedMessage message ) {
		this.message = message;
	}
	
	
	@Override
	public JsonObject getPayload() {
		return new Gson()
				.fromJson( message.payloadAsString(), JsonElement.class )
				.getAsJsonObject();
	}

	@Override
	public Date getPublishTime() {
		return new Date( message.publishTime() );
	}

	@Override
	public void acknowledge() {
		message.ack();
	}
	
}
