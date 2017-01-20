package com.pratilipi.common.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

import com.google.gson.JsonObject;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.BlobEntry;

public class HttpUtil {
	
	private static final Logger logger = Logger.getLogger( HttpUtil.class.getName() );
	
	
	private static String createQueryString( Map<String, String> params )
			throws UnsupportedEncodingException {
	
		String queryStr = "";
		for( Map.Entry<String, String> entry : params.entrySet() )
			queryStr = queryStr + "&" + entry.getKey() + "=" + URLEncoder.encode( entry.getValue(), "UTF-8" );
		    
		return queryStr.substring( 1 );
	}


	public static BlobEntry doGet( String targetURL ) throws UnexpectedServerException {
		return doGet( targetURL, null, null );
	}
	
	public static BlobEntry doGet( String targetURL, Map<String, String> headersMap, Map<String, String> paramsMap ) 
			throws UnexpectedServerException {
		
		try {
			if( paramsMap != null && paramsMap.size() != 0 )
				targetURL = targetURL + "?" + createQueryString( paramsMap );
			logger.log( Level.INFO, "Http GET Request: " + targetURL );
			URLConnection urlConn = new URL( targetURL ).openConnection();
			if( headersMap != null )
				for( Entry<String, String> entry : headersMap.entrySet() )
					urlConn.setRequestProperty( entry.getKey(), entry.getValue() );
			String mimeType = urlConn.getContentType();
			byte[] data = IOUtils.toByteArray( urlConn );
			logger.log( Level.INFO, "Http GET Response Type: " + mimeType + " & Length: "  + data.length );
			logger.log( Level.INFO, "Http GET Response: " + new String( data, "UTF-8" ) );
			return DataAccessorFactory.getBlobAccessor().newBlob( null, data, mimeType );
		} catch( IOException e ) {
			logger.log( Level.SEVERE, "Failed to execute Http Get call.", e );
			throw new UnexpectedServerException();
		}

	}

	@Deprecated
	public static String doGet( String targetURL, Map<String, String> paramsMap ) 
			throws UnexpectedServerException {
		
		try {
			String requestUrl = paramsMap == null ? targetURL : targetURL + "?" + createQueryString( paramsMap );
			logger.log( Level.INFO, "Http GET Request: " + requestUrl );
			String response = URLDecoder.decode( IOUtils.toString( new URL( requestUrl ).openStream(), "UTF-8" ), "UTF-8" );
			logger.log( Level.INFO, "Http GET Response Length: "  + response.length() );
			return response;
		} catch( IOException e ) {
			logger.log( Level.SEVERE, "Failed to execute Http Get call.", e );
			throw new UnexpectedServerException();
		}

	}
	
	public static String doPost( String targetURL, Map<String, String> paramsMap )
			throws UnexpectedServerException {
		
		HttpURLConnection connection = null;
		
		try {
			
			// Forming content from request parameters
			byte[] content = createQueryString( paramsMap ).getBytes( Charset.forName( "UTF-8" ) );
			
			// Create connection
			connection = (HttpURLConnection) new URL( targetURL ).openConnection();
			connection.setRequestMethod( "POST" );
			connection.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded" );
			connection.setRequestProperty( "Content-Length", content.length + "" );
			connection.setConnectTimeout( 60000 );	//60 Seconds
			connection.setReadTimeout( 60000 );		//60 Seconds
			connection.setUseCaches( false );
			connection.setDoInput( true );
			connection.setDoOutput( true );

			// Send request
			OutputStream outputStream = new DataOutputStream( connection.getOutputStream() );
			outputStream.write( content );
			outputStream.flush();
			outputStream.close();
			
			logger.log( Level.INFO, "Http POST Request: " + targetURL );

			// Response
			return _processPostResponse( connection );
		
		} catch( IOException e ) {
			logger.log( Level.SEVERE, "Failed to execute Http Post call.", e );
			throw new UnexpectedServerException();
			
		} finally {
			if( connection != null ) 
				connection.disconnect(); 
		}
		
	}
	
	public static String doPost( String targetURL, Map<String, String> headersMap, JsonObject jsonBody )
			throws UnexpectedServerException {
		
		HttpURLConnection connection = null;
		
		try {
			
			// Forming request parameters
			byte[] body = jsonBody.toString().getBytes( Charset.forName( "UTF-8" ) );
			
			// Create connection
			URL url = new URL( targetURL );
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod( "POST" );
			connection.setRequestProperty( "Content-Type", "application/json" );
			connection.setRequestProperty( "Content-Length", body.length + "" );
			for( Entry<String, String> entry : headersMap.entrySet() )
				connection.setRequestProperty( entry.getKey(), entry.getValue() );
			connection.setConnectTimeout( 60000 );	//60 Seconds
			connection.setReadTimeout( 60000 );		//60 Seconds
			connection.setUseCaches( false );
			connection.setDoInput( true );
			connection.setDoOutput( true );

			// Send request
			OutputStream outputStream = new DataOutputStream( connection.getOutputStream() );
			outputStream.write( body );
			outputStream.flush();
			outputStream.close();
			
			// Response
			logger.log( Level.INFO, "Http POST Request: " + targetURL );

			return _processPostResponse( connection );
		
		} catch( IOException e ) {
			logger.log( Level.SEVERE, "Failed to execute Http Post call.", e );
			throw new UnexpectedServerException();
			
		} finally {
			if( connection != null ) 
				connection.disconnect(); 
		}
		
	}
	
	private static String _processPostResponse( HttpURLConnection connection ) throws IOException {
		String response = URLDecoder.decode( IOUtils.toString( connection.getInputStream(), "UTF-8" ), "UTF-8" );
		logger.log( Level.INFO, "Http POST Response Type: " + response + " & Length: "  + response.length() );
		return response;
	}
	
}
