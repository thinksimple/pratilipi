package com.pratilipi.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.common.exception.InvalidArgumentException;

@SuppressWarnings("serial")
@Bind( uri = "/" )
public class GenericBatchApi extends GenericApi {

	@Override
	protected final void service(
			HttpServletRequest request,
			HttpServletResponse response ) throws ServletException, IOException {
		
		String method = request.getMethod();
		Object apiResponse = null;
		
		// Invoking batch get methods for API response map
		if( method.equals( "GET" ) ) {
			
			Gson gson = new Gson();
			
			JsonObject apiReqs = gson.fromJson( request.getParameter( "reqs" ), JsonElement.class ).getAsJsonObject();
			Map<String, Object> apiResps = new HashMap<>();
			for( Entry<String, JsonElement> apiReq : apiReqs.entrySet() ) {
				String reqUrl = apiReq.getValue().getAsString();
				int index = reqUrl.indexOf( '?' );
				GenericApi api = index == -1
						? ApiRegistry.getApi( reqUrl )
						: ApiRegistry.getApi( reqUrl.substring( 0, index ) );
				JsonObject reqPayloadJson = index == -1
						? new JsonObject()
						: createRequestPayloadJson( reqUrl.substring( index + 1 ) );
				apiResps.put(
						apiReq.getKey(),
						executeApi( api, api.getMethod, reqPayloadJson, api.getMethodParameterType, request ) );
			}
			
			apiResponse = apiResps;

		// Invoking get/put method for API response
		} else {
			
			apiResponse = new InvalidArgumentException( "Invalid resource or method." );
		
		}
		
		// Dispatching API response
		dispatchApiResponse( apiResponse, request, response );
		
	}
	
}
