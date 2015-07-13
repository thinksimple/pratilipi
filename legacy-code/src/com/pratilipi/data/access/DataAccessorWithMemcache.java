package com.pratilipi.data.access;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.claymus.data.access.DataListCursorTuple;
import com.claymus.data.access.Memcache;
import com.pratilipi.commons.shared.AuthorFilter;
import com.pratilipi.commons.shared.PratilipiFilter;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Event;
import com.pratilipi.data.transfer.EventPratilipi;
import com.pratilipi.data.transfer.Genre;
import com.pratilipi.data.transfer.Language;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.data.transfer.PratilipiAuthor;
import com.pratilipi.data.transfer.PratilipiGenre;
import com.pratilipi.data.transfer.PratilipiTag;
import com.pratilipi.data.transfer.Publisher;
import com.pratilipi.data.transfer.Tag;
import com.pratilipi.data.transfer.UserPratilipi;

@SuppressWarnings("serial")
public class DataAccessorWithMemcache
		extends com.claymus.data.access.DataAccessorWithMemcache
		implements DataAccessor {
	
	private static final String PREFIX_EVENT = "Event-";

	
	@Override
	public Event newEvent() {
		return dataAccessor.newEvent();
	}

	@Override
	public Event getEvent( Long id ) {
		Event event = memcache.get( PREFIX_EVENT + id );
		if( event == null ) {
			event = dataAccessor.getEvent( id );
			if( event != null )
				memcache.put( PREFIX_EVENT + id, event );
		}
		return event;
	}
	
	@Override
	public Event createOrUpdateEvent( Event event ) {
		event = dataAccessor.createOrUpdateEvent( event );
		memcache.put( PREFIX_EVENT + event.getId(), event );
		return event;
	}
	
}
