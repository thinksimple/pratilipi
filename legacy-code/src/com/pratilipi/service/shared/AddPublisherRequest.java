package com.pratilipi.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.service.shared.data.PublisherData;

public class AddPublisherRequest implements IsSerializable {
	private PublisherData publisherData;


	@SuppressWarnings("unused")
	private AddPublisherRequest() {}
	
	public AddPublisherRequest( PublisherData publisherData ) {
		this.publisherData = publisherData;
	}
	
	
	public PublisherData getPublisher() {
		return this.publisherData;
	}

}
