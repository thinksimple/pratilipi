package com.pratilipi.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.util.SystemProperty;

@SuppressWarnings("serial")
@Bind( uri = "/" )
public class GenericBatchApi extends GenericApi {

	private static final Logger logger =
			Logger.getLogger( GenericBatchApi.class.getName() );

	
	@Override
	protected final void service( HttpServletRequest request, HttpServletResponse response )
			throws ServletException, IOException {
		
		// Logging
		if( SystemProperty.STAGE != SystemProperty.STAGE_PROD )
			logHeaders( request );
		logRequestParams( request );

		
		String method = request.getMethod();
		Object apiResponse = null;
		
		if( method.equals( "GET" ) ) {
			
			Gson gson = new Gson();
			
			JsonObject apiReqs = gson.fromJson(
					request.getParameter( "requests" ),
					JsonElement.class ).getAsJsonObject();
			
			Map<String, Object> apiResps = new HashMap<>();
			for( Entry<String, JsonElement> apiReq : apiReqs.entrySet() ) {
				String reqUri = apiReq.getValue().getAsString();
				int index = reqUri.indexOf( '?' );
				GenericApi api = index == -1
						? ApiRegistry.getApi( reqUri )
						: ApiRegistry.getApi( reqUri.substring( 0, index ) );
				JsonObject reqPayloadJson = index == -1
						? new JsonObject()
						: createRequestPayloadJson( reqUri.substring( index + 1 ), request );
				apiResps.put(
						apiReq.getKey(),
						executeApi( api, api.getMethod, reqPayloadJson, api.getMethodParameterType, request ) );
			}
			
			dispatchApiResponse( apiResps, request, response );

		} else {
			
			apiResponse = new InvalidArgumentException( "Invalid resource or method." );
			dispatchApiResponse( apiResponse, request, response );
		
		}
		
		
	}
	
	final JsonObject createRequestPayloadJson( String queryStr, HttpServletRequest request ) {
		JsonObject requestPayloadJson = new JsonObject();
		for( String paramValue : queryStr.split( "&" ) ) {
			int index = paramValue.indexOf( '=' );
			String param = index == -1 ? paramValue : paramValue.substring( 0, index );
			String value = index == -1 ? null : paramValue.substring( index + 1 );
			requestPayloadJson.addProperty( param, value );
			requestPayloadJson.addProperty( "has" + Character.toUpperCase( param.charAt( 0 ) ) + param.substring( 1 ), true );
		}
		return requestPayloadJson;
	}

	final void dispatchApiResponse( Map<String, Object> apiResps, HttpServletRequest request,
			HttpServletResponse response ) throws IOException {
		
		response.setContentType( "text/html" );
		response.setCharacterEncoding( "UTF-8" );
		
		Gson gson = new Gson();
		boolean bool = true;
		
		PrintWriter writer = response.getWriter();
		writer.print( "{" );
		for( Entry<String, Object> apiResp : apiResps.entrySet() ) {
			if( bool )
				bool = false;
			else
				writer.print( "," );
			writer.print( "\"" + apiResp.getKey() + "\":{" );
			if( apiResp.getValue() instanceof GenericResponse ) {
				writer.print( "\"status\":" + HttpServletResponse.SC_OK + "," );
				writer.print( "\"response\":" + gson.toJson( apiResp.getValue() ) );
			} else if( apiResp.getValue() instanceof InvalidArgumentException ) {
				logger.log( Level.INFO, ((Throwable) apiResp.getValue() ).getMessage() );
				writer.print( "\"status\":" + HttpServletResponse.SC_BAD_REQUEST + "," );
				writer.print( "\"response\":" + ((Throwable) apiResp.getValue() ).getMessage() );
			} else if( apiResp.getValue() instanceof InsufficientAccessException ) {
				logger.log( Level.INFO, ((Throwable) apiResp.getValue() ).getMessage() );
				writer.print( "\"status\":" + HttpServletResponse.SC_UNAUTHORIZED + "," );
				writer.print( "\"response\":" + ((Throwable) apiResp.getValue() ).getMessage() );
			} else if( apiResp.getValue() instanceof UnexpectedServerException ) {
				writer.print( "\"status\":" + HttpServletResponse.SC_INTERNAL_SERVER_ERROR + "," );
				writer.print( "\"response\":" + ((Throwable) apiResp.getValue() ).getMessage() );
			} else {
				writer.print( "\"status\":" + HttpServletResponse.SC_INTERNAL_SERVER_ERROR + "," );
				writer.print( "\"response\":" + ((Throwable) apiResp.getValue() ).getMessage() );
			}
			writer.print( "}" );
		}
		writer.print( "}" );
		writer.close();
	
	}
	
}
