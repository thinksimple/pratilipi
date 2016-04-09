package com.pratilipi.api.impl.init.shared;

import java.util.List;

import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.data.client.AuthorData;
import com.pratilipi.data.client.EventData;

@SuppressWarnings("unused")
public class GetEventListResponse extends GenericResponse {

	private List<EventData> eventList;

	
	private GetEventListResponse() {}
	
	public GetEventListResponse( List<EventData> eventList ) {
		this.eventList = eventList;
	}
	
}
