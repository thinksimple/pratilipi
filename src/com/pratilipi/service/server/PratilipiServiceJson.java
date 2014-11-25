package com.pratilipi.service.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;


@SuppressWarnings("serial")
public class PratilipiServiceJson extends HttpServlet {

	private static final Logger logger =
			Logger.getLogger( PratilipiServiceJson.class.getName() );

	private static final Gson gson = new GsonBuilder().create();
	private static final Map<String, Class<?>> apiRequestHelper = new HashMap<>();

	static {
		apiRequestHelper.put( "getPratilipiContent", PratilipiContentHelper.class );
	}
	
	
	@Override
	public void doPost(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {

		
		try {
			String apiName = request.getParameter( "api" );
			String apiRequestClassName = "com.pratilipi.service.shared."
					+ apiName.substring( 0, 1 ).toUpperCase()
					+ apiName.substring( 1 )
					+ "Request";
			Class<?> apiRequestClass = Class.forName( apiRequestClassName );

			String requestPayload = IOUtils.toString( request.getInputStream() );
			logger.log( Level.INFO, "Request Payload: " + requestPayload );
			Object apiRequest = gson.fromJson( requestPayload, apiRequestClass );

			Method api = apiRequestHelper.get( apiName )
					.getMethod( apiName, apiRequestClass, HttpServletRequest.class );
			
			Object apiResponse = api.invoke( null, apiRequest, request );
			String responsePayload = gson.toJson( apiResponse );
			logger.log( Level.INFO, "Response Payload: " + responsePayload );
			
			response.setCharacterEncoding( "UTF-8" );
			PrintWriter writer = response.getWriter();
			writer.println( responsePayload );
			writer.close();
		} catch( JsonSyntaxException | ClassNotFoundException
				| NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			
			logger.log( Level.SEVERE, "Failed to execute API.", e );
			response.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
		}

	}
	
}
