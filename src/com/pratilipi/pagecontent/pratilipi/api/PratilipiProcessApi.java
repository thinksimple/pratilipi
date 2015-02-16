package com.pratilipi.pagecontent.pratilipi.api;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Post;
import com.claymus.api.shared.GenericResponse;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;
import com.pratilipi.pagecontent.pratilipi.api.shared.PratilipiProcessPostRequest;

@SuppressWarnings("serial")
@Bind( uri = "/pratilipi/process" )
public class PratilipiProcessApi extends GenericApi {

	@Post
	public GenericResponse postPratilipiProcess( PratilipiProcessPostRequest request )
			throws InvalidArgumentException, UnexpectedServerException {

		if( request.processData() ) {
			// Update search index
		}
		
		if( request.processContent() ) {
			PratilipiContentHelper.updatePratilipiIndex(
					request.getPratilipiId(),
					this.getThreadLocalRequest() );
		}
		
		return new GenericResponse();
	}

}