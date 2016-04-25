package com.pratilipi.api.impl.init;

import java.util.List;
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

		List<BlogPostEntity> blogPostList = ObjectifyService.ofy().load().type( BlogPostEntity.class ).list();
		for( BlogPostEntity blogPost : blogPostList )
			if( blogPost.getLanguage() == null )
				blogPost.setLanguage( Language.HINDI );
		
		ObjectifyService.ofy().save().entities( blogPostList ).now();
		
		return new GenericResponse();
	}

}