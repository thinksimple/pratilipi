package com.pratilipi.pagecontent.event.gae;

import com.claymus.data.access.gae.PageContentEntity;
import com.pratilipi.pagecontent.event.EventContent;

@SuppressWarnings("serial")
public class EventContentEntity extends PageContentEntity
		implements EventContent {

	public EventContentEntity( Long eventId ) {
		super.setId( eventId );
	}
	
}
