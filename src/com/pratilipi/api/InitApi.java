package com.pratilipi.api;

import java.util.Date;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Get;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.data.transfer.Page;
import com.pratilipi.api.shared.GetInitRequest;
import com.pratilipi.api.shared.GetInitResponse;
import com.pratilipi.commons.shared.PratilipiPageType;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Event;

@SuppressWarnings("serial")
@Bind( uri= "/init" )
public class InitApi extends GenericApi {

	@Get
	public GetInitResponse getInit( GetInitRequest request )
			throws InvalidArgumentException, InsufficientAccessException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );

		
/*		// START :: CREATE NEW EVENT :: START
		
		Event event = dataAccessor.newEvent();
		event.setName( "New Event" );
		event.setNameEn( "New Event" );
		event.setStartDate( new Date() );
		event.setEndDate( new Date() );
		event.setCreationDate( new Date() );
		event = dataAccessor.createOrUpdateEvent( event );
		
		Page page = dataAccessor.newPage();
		page.setType( PratilipiPageType.EVENT.toString() );
		page.setUri( PratilipiPageType.EVENT.getUrlPrefix() + event.getId() );
		page.setPrimaryContentId( event.getId() );
		page.setCreationDate( new Date() );
		page = dataAccessor.createOrUpdatePage( page );

		return new GetInitResponse( page.getUri() );

		// END :: CREATE NEW EVENT :: END									*/

		
		return new GetInitResponse( "Nothing to initialize !" );
	}
	
}
