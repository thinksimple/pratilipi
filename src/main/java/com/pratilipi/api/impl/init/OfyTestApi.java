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

		Long[] ids = new Long[] { 5097310579064832L,
				5722489978093568L,
				5671063549640704L,
				4757949029285888L,
				5703597413105664L,
				5677450516234240L,
				6015661243367424L,
				5761505479884800L,
				5429848977702912L,
				5284679938736128L,
				5187492647010304L,
				5212753337778176L,
				5478636643680256L,
				6672001628372992L,
				5677424071147520L,
				5767762198659072L,
				5737275447050240L,
				5769873309302784L,
				5112572856500224L,
				5680376676614144L,
				5730377779904512L,
				5650882632876032L,
				5131432846426112L,
				5735393697726464L,
				5632984900173824L };
		
		Map<Long, BlogPostEntity> blogPosts = ObjectifyService.ofy().load().type( BlogPostEntity.class ).ids( ids );
		for( Long id : ids )
			blogPosts.get( id ).setLanguage( Language.GUJARATI );
		
		ObjectifyService.ofy().save().entities( blogPosts.values() ).now();
		
		return new GenericResponse();
	}

}