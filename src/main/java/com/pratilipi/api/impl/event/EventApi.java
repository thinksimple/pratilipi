package com.pratilipi.api.impl.event;

import com.google.gson.Gson;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.impl.event.shared.GenericEventResponse;
import com.pratilipi.api.impl.event.shared.GetEventRequest;
import com.pratilipi.api.impl.event.shared.PostEventRequest;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.client.EventData;
import com.pratilipi.data.type.Event;
import com.pratilipi.data.util.EventDataUtil;

@SuppressWarnings("serial")
@Bind( uri = "/event" )
public class EventApi extends GenericApi {
	
	@Get
	public GenericEventResponse get( GetEventRequest request ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Event event = dataAccessor.getEvent( request.getEventId() );
		
		EventData eventData = EventDataUtil.createEventData( event );

		return new GenericEventResponse( eventData );
		
	}

	@Post
	public GenericEventResponse post( PostEventRequest request )
			throws InvalidArgumentException, InsufficientAccessException {

		Gson gson = new Gson();

		EventData eventData = gson.fromJson( gson.toJson( request ), EventData.class );
		
		eventData = EventDataUtil.saveEventData( eventData );

		return new GenericEventResponse( eventData );
		
	}		

}
