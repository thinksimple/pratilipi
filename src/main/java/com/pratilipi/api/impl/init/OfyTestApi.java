package com.pratilipi.api.impl.init;

import java.util.List;
import java.util.logging.Logger;

import com.googlecode.objectify.ObjectifyService;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.impl.init.shared.GetInitApiRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.type.Language;
import com.pratilipi.data.type.gae.EventEntity;

@SuppressWarnings("serial")
@Bind( uri = "/ofy" )
public class OfyTestApi extends GenericApi {
	
	private static final Logger logger =
			Logger.getLogger( OfyTestApi.class.getName() );

	@Get
	public GenericResponse get( GetInitApiRequest request ) throws InvalidArgumentException, InsufficientAccessException {

		List<EventEntity> eventList = ObjectifyService.ofy().load().type( EventEntity.class ).filter( "LANGUAGE", Language.HINDI ).list();
		for( EventEntity event : eventList )
			if( event.getLanguage() == null )
				event.setLanguage( Language.HINDI );
		
		ObjectifyService.ofy().save().entities( eventList ).now();
		
		return new GenericResponse();
	}

}