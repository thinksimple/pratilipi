package com.pratilipi.api;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.pratilipi.api.annotation.Delete;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.annotation.Put;
import com.pratilipi.api.shared.GenericFileDownloadResponse;
import com.pratilipi.api.shared.GenericFileUploadRequest;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;

@SuppressWarnings("serial")
public abstract class GenericApi extends HttpServlet {

	private static final Logger logger =
			Logger.getLogger( GenericApi.class.getName() );

	private Method getMethod;
	private Method putMethod;
	private Method postMethod;
	private Method deleteMethod;
	
	private Class<? extends GenericRequest> getMethodParameterType;
	private Class<? extends GenericRequest> putMethodParameterType;
	private Class<? extends GenericRequest> postMethodParameterType;
	private Class<? extends GenericRequest> deleteMethodParameterType;

	
	@SuppressWarnings("unchecked")
	@Override
	public void init() throws ServletException {
		for( Method method : this.getClass().getMethods() ) {
			if( method.getAnnotation( Get.class ) != null ) {
				getMethod = method;
				getMethodParameterType = (Class<? extends GenericRequest>) method.getParameterTypes()[0];
			} else if( method.getAnnotation( Put.class ) != null ) {
				putMethod = method;
				putMethodParameterType = (Class<? extends GenericRequest>) method.getParameterTypes()[0];
			} else if( method.getAnnotation( Post.class ) != null ) {
				postMethod = method;
				postMethodParameterType = (Class<? extends GenericRequest>) method.getParameterTypes()[0];
			} else if( method.getAnnotation( Delete.class ) != null ) {
				deleteMethod = method;
				deleteMethodParameterType = (Class<? extends GenericRequest>) method.getParameterTypes()[0];
			}
		}
	}
	

	@SuppressWarnings("unchecked")
	@Override
	protected final void service(
			HttpServletRequest request,
			HttpServletResponse response ) throws ServletException, IOException {
		
		String method = request.getMethod();
		Gson gson = new Gson();

		// Creating JsonObject from request parameters
		Enumeration<String> queryParams = request.getParameterNames();
		StringBuilder queryParamsStr = new StringBuilder( "{" );
		while( queryParams.hasMoreElements() ) {
			String param = queryParams.nextElement();
			String value = request.getParameter( param );
			if( value.startsWith( "{" ) || value.startsWith( "[" ) )
				queryParamsStr.append( "\"" + param + "\":" + value + "," );
			else
				queryParamsStr.append( "\"" + param + "\":\"" + value + "\"," );
		}
		if( queryParamsStr.length() > 1 )
			queryParamsStr.setCharAt( queryParamsStr.length() - 1, '}' );
		else
			queryParamsStr.append( "}" );
		JsonObject queryParamsJson = gson.fromJson( queryParamsStr.toString(), JsonElement.class ).getAsJsonObject();

		// Creating JsonObject from request body (JSON)
		JsonObject requestBodyJson = method.equals( "PUT" )
				? gson.fromJson( IOUtils.toString( request.getInputStream(), "UTF-8" ), JsonElement.class ).getAsJsonObject()
				: new JsonObject();

		
		JsonObject requestPayloadJson = new JsonObject();
		for( Entry<String, JsonElement> entry : queryParamsJson.entrySet() ) {
			requestPayloadJson.add( entry.getKey(), entry.getValue() );
			requestPayloadJson.addProperty( "has" + Character.toUpperCase( entry.getKey().charAt( 0 ) ) + entry.getKey().substring( 1 ), true );
		}
		for( Entry<String, JsonElement> entry : requestBodyJson.entrySet() ) {
			requestPayloadJson.add( entry.getKey(), entry.getValue() );
			requestPayloadJson.addProperty( "has" + Character.toUpperCase( entry.getKey().charAt( 0 ) ) + entry.getKey().substring( 1 ), true );
		}
		
		
		logger.log( Level.INFO, "Request Payload: " + requestPayloadJson );
		
		
		// Invoking get/put method for API response
		Object apiResponse = null;
		if( method.equals( "GET" ) && getMethod != null )
			apiResponse = executeApi( getMethod, requestPayloadJson, getMethodParameterType, request );
		else if( method.equals( "PUT" ) && putMethod != null )
			apiResponse = executeApi( putMethod, requestPayloadJson, putMethodParameterType, request );
		else if( method.equals( "POST" ) && postMethod != null )
			apiResponse = executeApi( postMethod, requestPayloadJson, postMethodParameterType, request );
		else if( method.equals( "DELETE" ) && deleteMethod != null )
			apiResponse = executeApi( deleteMethod, requestPayloadJson, deleteMethodParameterType, request );
		else
			apiResponse = new InvalidArgumentException( "Invalid resource or method." );

		
		// Dispatching API response
		dispatchApiResponse( apiResponse, request, response );
	}
	
	
	private Object executeApi( Method apiMethod, JsonObject requestPayloadJson,
			Class<? extends GenericRequest> apiMethodParameterType, HttpServletRequest request ) {
		
		try {
			GenericRequest apiRequest = new Gson().fromJson( requestPayloadJson, apiMethodParameterType );
			
			if( apiRequest instanceof GenericFileUploadRequest ) {
				GenericFileUploadRequest gfuRequest = (GenericFileUploadRequest) apiRequest;
				try {
					ServletFileUpload upload = new ServletFileUpload();
					FileItemIterator iterator = upload.getItemIterator( request );
					while( iterator.hasNext() ) {
						FileItemStream fileItemStream = iterator.next();
						if( ! fileItemStream.isFormField() ) {
							gfuRequest.setName( fileItemStream.getName() );
							gfuRequest.setData( IOUtils.toByteArray( fileItemStream.openStream() ) );
							gfuRequest.setMimeType( fileItemStream.getContentType() );
							break;
						}
					}
				} catch( IOException | FileUploadException e ) {
					throw new UnexpectedServerException();
				}
			}

			JsonObject errorMessages = apiRequest.validate();
			if( errorMessages.entrySet().size() > 0 )
				return new InvalidArgumentException( errorMessages );
			else
				return apiMethod.invoke( this, apiRequest );

		} catch( JsonSyntaxException e ) {
			logger.log( Level.SEVERE, "Invalid JSON in request body.", e );
			return new InvalidArgumentException( "Invalid JSON in request body." );
		
		} catch( UnexpectedServerException e) {
			return e;
		
		} catch( InvocationTargetException e ) {
			Throwable te = e.getTargetException();
			if( te instanceof InvalidArgumentException
					|| te instanceof InsufficientAccessException
					|| te instanceof UnexpectedServerException ) {
				return te;
			
			} else {
				logger.log( Level.SEVERE, "Failed to execute API.", te );
				return new UnexpectedServerException();
			}
			
		} catch( IllegalAccessException | IllegalArgumentException e ) {
			logger.log( Level.SEVERE, "Failed to execute API.", e );
			return new UnexpectedServerException();
		}
		
	}
	
	private void dispatchApiResponse( Object apiResponse, HttpServletRequest request,
			HttpServletResponse response ) throws IOException {
		
		if( apiResponse instanceof GenericFileDownloadResponse ) {
			GenericFileDownloadResponse gfdResponse = (GenericFileDownloadResponse) apiResponse;

			String eTag = request.getHeader( "If-None-Match" );
			if( eTag == null )
				logger.log( Level.INFO, "No eTag found !" );
				
			if( eTag != null && eTag.equals( gfdResponse.getETag() ) ) {
				response.setStatus( HttpServletResponse.SC_NOT_MODIFIED );
			
			} else {
				response.setContentType( gfdResponse.getMimeType() );
				response.setHeader( "Cache-Control", "max-age=315360000" );
				response.setHeader( "ETag", gfdResponse.getETag() );

				OutputStream out = response.getOutputStream();
				out.write( gfdResponse.getData() );
				out.close();
			}
			
		} else if( apiResponse instanceof GenericResponse ) {
			response.setCharacterEncoding( "UTF-8" );
			PrintWriter writer = response.getWriter();
			writer.println( new Gson().toJson( apiResponse ) );
			writer.close();
		
		} else if( apiResponse instanceof Throwable ) {
			response.setCharacterEncoding( "UTF-8" );
			PrintWriter writer = response.getWriter();

			if( apiResponse instanceof InvalidArgumentException )
				response.setStatus( HttpServletResponse.SC_BAD_REQUEST );
			else if( apiResponse instanceof InsufficientAccessException )
				response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
			else if( apiResponse instanceof UnexpectedServerException )
				response.setStatus( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
			else
				response.setStatus( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
			
			writer.println( ((Throwable) apiResponse ).getMessage() );
			writer.close();

		}
		
	}
	
}
