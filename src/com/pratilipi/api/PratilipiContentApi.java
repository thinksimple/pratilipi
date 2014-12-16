package com.pratilipi.api;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Get;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.pratilipi.api.shared.GetPratilipiContentRequest;
import com.pratilipi.api.shared.GetPratilipiContentResponse;
import com.pratilipi.commons.shared.PratilipiContentType;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;


@SuppressWarnings("serial")
public class PratilipiContentApi extends GenericApi {

	@Get
	public Object getPratilipiContent( GetPratilipiContentRequest request )
			throws InvalidArgumentException, InsufficientAccessException,
			UnexpectedServerException {

		Object content = PratilipiContentHelper.getPratilipiContent(
				request.getPratilipiId(), request.getPageNumber(),
				request.getContentType(), this.getThreadLocalRequest() );
		
		if( request.getContentType() == PratilipiContentType.PRATILIPI ) {
			return new GetPratilipiContentResponse(
					request.getPratilipiId(),
					request.getPageNumber(), 
					request.getContentType(), (String) content );
		
		} else {
			return content;
		}

	}

}
