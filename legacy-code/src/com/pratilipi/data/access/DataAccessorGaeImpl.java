package com.pratilipi.data.access;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.Query;

import com.claymus.data.access.DataListCursorTuple;
import com.claymus.data.access.GaeQueryBuilder;
import com.claymus.data.access.GaeQueryBuilder.Operator;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JDOCursorHelper;
import com.pratilipi.commons.shared.AuthorFilter;
import com.pratilipi.commons.shared.PratilipiFilter;
import com.pratilipi.data.access.gae.AuthorEntity;
import com.pratilipi.data.access.gae.EventEntity;
import com.pratilipi.data.access.gae.EventPratilipiEntity;
import com.pratilipi.data.access.gae.GenreEntity;
import com.pratilipi.data.access.gae.LanguageEntity;
import com.pratilipi.data.access.gae.PratilipiAuthorEntity;
import com.pratilipi.data.access.gae.PratilipiEntity;
import com.pratilipi.data.access.gae.PratilipiGenreEntity;
import com.pratilipi.data.access.gae.PratilipiTagEntity;
import com.pratilipi.data.access.gae.PublisherEntity;
import com.pratilipi.data.access.gae.TagEntity;
import com.pratilipi.data.access.gae.UserPratilipiEntity;
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
public class DataAccessorGaeImpl
		extends com.claymus.data.access.DataAccessorGaeImpl
		implements DataAccessor {

	
	@Override
	public Event newEvent() {
		return new EventEntity();
	}

	@Override
	public Event getEvent( Long id ) {
		try {
			return id == null ? null : getEntity( EventEntity.class, id );
		} catch( JDOObjectNotFoundException e ) {
			return null;
		}
	}
	
	@Override
	public Event createOrUpdateEvent( Event event ) {
		return createOrUpdateEntity( event );
	}
	
}
