package com.pratilipi.api.impl.event.shared;

import java.util.ArrayList;
import java.util.List;

import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.data.client.AuthorData;
import com.pratilipi.data.client.EventData;

@SuppressWarnings("unused")
public class GetEventListResponse extends GenericResponse {

	private List<GenericEventResponse> eventList;

	
	private GetEventListResponse() {}
	
	public GetEventListResponse( List<EventData> eventList ) {
		List<GenericEventResponse> eventListResponse = new ArrayList<GenericEventResponse>( eventList.size() );
		for( EventData eventData : eventList )
			eventListResponse.add( new GenericEventResponse( eventData ) );
		this.eventList = eventListResponse;
	}
	
}
