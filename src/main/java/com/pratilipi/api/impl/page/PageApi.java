package com.pratilipi.api.impl.page;

import com.google.gson.JsonObject;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.type.PageType;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.Page;

@SuppressWarnings( "serial" )
@Bind( uri = "/page" )
public class PageApi extends GenericApi {

	public static class GetRequest extends GenericRequest {
		
		@Validate( required = true )
		private String uri;
		
	}
	
	@SuppressWarnings("unused")
	public static class Response extends GenericResponse {

		private PageType pageType;
		
		private Long primaryContentId;

		
		private Response() {}
		
		private Response( PageType pageType, Long primaryContentId ) {
			this.pageType = pageType;
			this.primaryContentId = primaryContentId;
		}
		
	}
	
	
	@Get
	public Response get( GetRequest request ) throws InvalidArgumentException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		Page page = dataAccessor.getPage( request.uri );
		
		if( page == null ) {
			JsonObject errorMessages = new JsonObject();
			errorMessages.addProperty( "uri", "Invalid uri !" );
			throw new InvalidArgumentException( errorMessages );
		}
		
		return new Response( page.getType(), page.getPrimaryContentId() );
		
	}
	
}
