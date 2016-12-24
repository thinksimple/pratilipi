package com.pratilipi.api.impl.page;

import java.io.File;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.JsonObject;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.PageType;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.Page;

@SuppressWarnings( "serial" )
@Bind( uri = "/page" )
public class PageApi extends GenericApi {

	private static final Logger logger = Logger.getLogger( PageApi.class.getName() );
	
	
	public static class GetRequest extends GenericRequest {
		
		@Validate( required = true )
		private String uri;
		
	}
	
	@SuppressWarnings("unused")
	public static class Response extends GenericResponse {

		private PageType pageType;
		
		private Long primaryContentId;
		
		private String primaryContentName;

		
		private Response() {}
		
		private Response( PageType pageType, Long primaryContentId ) {
			this.pageType = pageType;
			this.primaryContentId = primaryContentId;
		}
		
		private Response( PageType pageType, String primaryContentName ) {
			this.pageType = pageType;
			this.primaryContentName = primaryContentName;
		}
		
	}
	
	
	@Get
	public Response get( GetRequest request ) throws InvalidArgumentException, UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		if( request.uri.startsWith( "/read?id=" ) ) { // TODO: Remove this as soon as READ uri(s) are added to Page table.

			String pratilipiId = request.uri.substring( "/read?id=".length() );
			if( pratilipiId.indexOf( '&' ) != -1 )
				pratilipiId = pratilipiId.substring( 0, pratilipiId.indexOf( '&' ) );
			return new Response( PageType.READ, Long.parseLong( pratilipiId ) );

		} 

		Page page = dataAccessor.getPage( request.uri.contains( "?" )
										? request.uri.substring( 0, request.uri.indexOf( "?" ) )
										: request.uri );

		if( page != null )
			return new Response( page.getType(), page.getPrimaryContentId() );


		if( request.uri.matches( "^/[a-z0-9-]+$" ) ) { // TODO: Remove this as soon as CATEGORY_LIST uri(s) are added to Page table.

			try {
				String folder = DataAccessor.class.getResource( "curated/" ).toURI().getPath();
				for( String fileName : new File( folder ).list() )
					if( fileName.matches( "list[.]\\w\\w[.]" + request.uri.substring( 1 ) ) )
						return new Response( PageType.CATEGORY_LIST, fileName.substring( fileName.lastIndexOf( '.' ) + 1 ) );
			} catch( URISyntaxException e ) {
				logger.log( Level.SEVERE, "Failed to list category list files.", e );
				throw new UnexpectedServerException();
			}

		}

		JsonObject errorMessages = new JsonObject();
		errorMessages.addProperty( "uri", "Invalid uri !" );
		throw new InvalidArgumentException( errorMessages );

	}

}
