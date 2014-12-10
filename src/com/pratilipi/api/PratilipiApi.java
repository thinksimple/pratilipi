package com.pratilipi.api;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.api.GenericApi;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.google.gson.JsonObject;
import com.pratilipi.api.shared.GetPratilipiContentRequest;
import com.pratilipi.api.shared.GetPratilipiContentResponse;
import com.pratilipi.commons.shared.PratilipiContentType;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;


@SuppressWarnings("serial")
public class PratilipiApi extends GenericApi {

	private static final String RESOURCE_PRATILIPI_CONTENT = "/pratilipi/content";
	

	@Override
	protected void executeGet(
			JsonObject requestPayloadJson,
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException, UnexpectedServerException {

		String requestUri = request.getRequestURI();
		
		if( requestUri.endsWith( RESOURCE_PRATILIPI_CONTENT ) ) {
			GetPratilipiContentRequest apiRequest =
					gson.fromJson( requestPayloadJson, GetPratilipiContentRequest.class );
			GetPratilipiContentResponse apiResponse =
					PratilipiContentHelper.getPratilipiContent( apiRequest, request );
			
			if( apiRequest.getContentType() == PratilipiContentType.PRATILIPI )
				serveJson( gson.toJson( apiResponse ), request, response );
			
			else if( apiRequest.getContentType() == PratilipiContentType.IMAGE )
				serveBlob( (byte[]) apiResponse.getPageContent(), apiResponse.getPageContentMimeType(), request, response);
			
		}
			
	}

}
