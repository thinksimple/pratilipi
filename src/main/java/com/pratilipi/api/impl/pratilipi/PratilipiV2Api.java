package com.pratilipi.api.impl.pratilipi;

import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.util.PratilipiDataUtil;

@SuppressWarnings("serial")
@Bind( uri = "/pratilipi", ver = "2" )
public class PratilipiV2Api extends PratilipiV1Api {
	
	@Get
	public Response get( GetRequest request ) throws UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Pratilipi pratilipi = dataAccessor.getPratilipi( request.pratilipiId );
		Author author = pratilipi.getAuthorId() == null
				? null
				: dataAccessor.getAuthor( pratilipi.getAuthorId() );

		return new Response( PratilipiDataUtil.createPratilipiData( pratilipi, author ) );

	}

}
