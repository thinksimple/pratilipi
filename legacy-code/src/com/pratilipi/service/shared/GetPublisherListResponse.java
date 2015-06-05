package com.pratilipi.service.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.service.shared.data.PublisherData;

public class GetPublisherListResponse implements IsSerializable {
	
	private List<PublisherData> publisherDataList;
	
	
	@SuppressWarnings("unused")
	private GetPublisherListResponse() {}
	
	public GetPublisherListResponse( List<PublisherData> publisherDataList ) {
		this.publisherDataList = publisherDataList;
	}
	
	
	public List<PublisherData> getPublisherList() {
		return this.publisherDataList;
	}

}
