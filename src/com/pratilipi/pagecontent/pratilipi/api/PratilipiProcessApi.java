package com.pratilipi.pagecontent.pratilipi.api;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Post;
import com.claymus.api.shared.GenericResponse;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;
import com.pratilipi.pagecontent.pratilipi.api.shared.PratilipiProcessPostRequest;

@SuppressWarnings("serial")
@Bind( uri = "/pratilipi/process" )
public class PratilipiProcessApi extends GenericApi {

	@Post
	public GenericResponse postPratilipiProcess( PratilipiProcessPostRequest request )
			throws InvalidArgumentException, UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		Pratilipi pratilipi = dataAccessor.getPratilipi( request.getPratilipiId() );
		
		if( request.processData() ) {
			// Update search index
		}
		
		if( request.processContent() ) {
			if( pratilipi.getType() == PratilipiType.BOOK )
				PratilipiContentHelper.updatePratilipiIndex( request.getPratilipiId(), this.getThreadLocalRequest() );
		}
		
		return new GenericResponse();
	}

}