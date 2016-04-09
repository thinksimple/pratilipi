package com.pratilipi.api.impl.event;

import java.util.List;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.impl.init.shared.GetEventListRequest;
import com.pratilipi.api.impl.init.shared.GetEventListResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.data.client.EventData;
import com.pratilipi.data.util.EventDataUtil;

@SuppressWarnings("serial")
@Bind( uri = "/event/list" )
public class EventListApi extends GenericApi {

	@Get
	public GetEventListResponse getAuthorList( GetEventListRequest request )
			throws InsufficientAccessException {
		
		List<EventData> eventList =
				EventDataUtil.getEventDataList( request.getLanguage() );
		
		return new GetEventListResponse( eventList );
		
	}

}
