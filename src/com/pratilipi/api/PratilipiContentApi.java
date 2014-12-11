package com.pratilipi.api;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Get;
import com.claymus.commons.shared.exception.IllegalArgumentException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.pratilipi.api.shared.GetPratilipiContentRequest;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;


@SuppressWarnings("serial")
public class PratilipiContentApi extends GenericApi {

	@Get
	public Object getPratilipiContent( GetPratilipiContentRequest request )
			throws UnexpectedServerException, IllegalArgumentException {

		return PratilipiContentHelper.getPratilipiContent( request, this.getThreadLocalRequest() );
	}

}
