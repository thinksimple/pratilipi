package com.pratilipi.service.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;
import com.pratilipi.service.shared.GetPratilipiContentRequest;
import com.pratilipi.service.shared.GetPratilipiContentResponse;


@SuppressWarnings("serial")
public class PratilipiService extends HttpServlet {

	private static final Logger logger =
			Logger.getLogger( PratilipiService.class.getName() );

	private static final Gson gson = new GsonBuilder().create();

	private static final String RESOURCE_PRATILIPI_CONTENT_PRTAILIPI =
			"/pratilipi/content/pratilipi";
	private static final String RESOURCE_PRATILIPI_CONTENT_IMAGE =
			"/pratilipi/content/image";


	@Override
	public void doGet(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {

		String requestPayload = IOUtils.toString( request.getInputStream() );
		logger.log( Level.INFO, "Request Payload: " + requestPayload );

		JsonObject jsonObj = requestPayload == null || requestPayload.isEmpty()
				? new JsonObject()
				: gson.fromJson( requestPayload, JsonElement.class ).getAsJsonObject();
		Enumeration<String> queryParams = request.getParameterNames();
		while( queryParams.hasMoreElements() ) {
			String param = queryParams.nextElement();
			jsonObj.addProperty( param, request.getParameter( param ) );
		}

		requestPayload = jsonObj.toString();
		logger.log( Level.INFO, "Request Payload: " + requestPayload );

		String responsePayload = null;
		
		String resourceName = request.getRequestURI().substring( "/service.pratilipi".length() );
		try {
			
			if( resourceName.equals( RESOURCE_PRATILIPI_CONTENT_PRTAILIPI ) ) {
				GetPratilipiContentRequest apiRequest =
						gson.fromJson( requestPayload, GetPratilipiContentRequest.class );
				GetPratilipiContentResponse apiResponse =
						PratilipiContentHelper.getPratilipiContent( apiRequest, request );
				responsePayload = gson.toJson( apiResponse );
			
			} else if( resourceName.equals( RESOURCE_PRATILIPI_CONTENT_IMAGE ) ) {
				// TODO: implementation
			}
			
		} catch( UnexpectedServerException e ) {
			logger.log( Level.SEVERE, "Failed to execute API.", e );
			response.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
			return;
		}
		
		logger.log( Level.INFO, "Response Payload: " + responsePayload );
		response.setCharacterEncoding( "UTF-8" );
		PrintWriter writer = response.getWriter();
		writer.println( responsePayload );
		writer.close();
	}
	
}
