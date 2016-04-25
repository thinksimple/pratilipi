package com.pratilipi.api.impl.init;

import java.util.Map;
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
import com.pratilipi.data.type.gae.BlogPostEntity;

@SuppressWarnings("serial")
@Bind( uri = "/ofy" )
public class OfyTestApi extends GenericApi {
	
	private static final Logger logger =
			Logger.getLogger( OfyTestApi.class.getName() );

	@Get
	public GenericResponse get( GetInitApiRequest request ) throws InvalidArgumentException, InsufficientAccessException {

		Long[] ids = new Long[] {
				5254427646623744L,
				5206027842617344L,
				5440671993298944L
		};
		
		Map<Long, BlogPostEntity> blogPosts = ObjectifyService.ofy().load().type( BlogPostEntity.class ).ids( ids );
		for( Long id : ids )
			blogPosts.get( id ).setLanguage( Language.TAMIL );
		
		ObjectifyService.ofy().save().entities( blogPosts.values() ).now();
		
		return new GenericResponse();
	}

}