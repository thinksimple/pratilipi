package com.pratilipi.api.impl.event;

import java.util.ArrayList;
import java.util.List;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.type.Language;
import com.pratilipi.data.client.EventData;
import com.pratilipi.data.util.EventDataUtil;

@SuppressWarnings("serial")
@Bind( uri = "/event/list" )
public class EventListApi extends GenericApi {

	public static class GetRequest extends GenericRequest {

		private Language language;
		
		public void setLanguage( Language language ) {
			this.language = language;
		}

	}
	
	public static class GetResponse extends GenericResponse {

		private List<EventApi.Response> eventList;

		
		private GetResponse() {}
		
		private GetResponse( List<EventData> eventList ) {
			List<EventApi.Response> eventListResponse = new ArrayList<EventApi.Response>( eventList.size() );
			for( EventData eventData : eventList )
				eventListResponse.add( new EventApi.Response( eventData, EventListApi.class ) );
			this.eventList = eventListResponse;
		}
		
		
		public List<EventApi.Response> getEventList() {
			return eventList;
		}

	}

	
	@Get
	public GetResponse get( GetRequest request ) {
		
		List<EventData> eventList =
				EventDataUtil.getEventDataList( request.language );
		
		return new GetResponse( eventList );
		
	}

}
