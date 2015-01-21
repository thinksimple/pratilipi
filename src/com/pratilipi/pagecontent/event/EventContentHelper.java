package com.pratilipi.pagecontent.event;

import com.claymus.pagecontent.PageContentHelper;
import com.pratilipi.pagecontent.event.gae.EventContentEntity;
import com.pratilipi.pagecontent.event.shared.EventContentData;

public class EventContentHelper extends PageContentHelper<
		EventContent,
		EventContentData,
		EventContentProcessor> {
	
	@Override
	public String getModuleName() {
		return "Event";
	}

	@Override
	public Double getModuleVersion() {
		return 5.0;
	}

	
	public static EventContent newEventContent( Long eventId ) {
		return new EventContentEntity( eventId );
	}

}
