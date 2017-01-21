package com.pratilipi.api.impl.test;

import java.util.logging.Logger;

import com.googlecode.objectify.ObjectifyService;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.type.gae.PageEntity;

@SuppressWarnings( "serial" )
@Bind( uri = "/test" )
public class TestApi extends GenericApi {

	private static final Logger logger = Logger.getLogger( TestApi.class.getName() );

	@Get
	public GenericResponse get( GenericRequest request ) throws UnexpectedServerException {

		System.out.println( ObjectifyService.ofy().load()
				.type( PageEntity.class )
				.keys()
				.first()
				.now()
				.getId() );

		System.out.println( ObjectifyService.ofy().load()
				.type( PageEntity.class )
				.order( "-__key__" )
				.keys()
				.first()
				.now()
				.getId() );

		return new GenericResponse();

	}

}
