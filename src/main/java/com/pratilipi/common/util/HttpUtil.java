package com.pratilipi.common.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

import com.pratilipi.common.exception.UnexpectedServerException;

public class HttpUtil {
	
	private static final Logger logger = Logger.getLogger( HttpUtil.class.getName() );
	
	
	private static String createQueryString( Map<String, String> params )
			throws UnsupportedEncodingException {
	
		String queryStr = "";
		for( Map.Entry<String, String> entry : params.entrySet() )
			queryStr = queryStr + "&" + entry.getKey() + "=" + URLEncoder.encode( entry.getValue(), "UTF-8" );
		    
		return queryStr.substring( 1 );
	}

	
	public static String doGet( String targetURL, Map<String, String> paramsMap ) 
			throws UnexpectedServerException {
		
		try {
			String requestUrl = targetURL + "?" + createQueryString( paramsMap );
			logger.log( Level.INFO, "Http GET Request: " + requestUrl );
			String response = URLDecoder.decode( IOUtils.toString( new URL( requestUrl ).openStream(), "UTF-8" ), "UTF-8" );
			logger.log( Level.INFO, "Http GET Response: " + response );
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
			// Forming request parameters
			String urlParams = createQueryString( paramsMap );
			
			// Create connection
			URL url = new URL( targetURL );
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod( "POST" );
			connection.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded" );
			connection.setRequestProperty( "Content-Length", urlParams.getBytes().length + "" );
			connection.setUseCaches( false );
			connection.setDoInput( true );
			connection.setDoOutput( true );

			// Send request
			OutputStream outputStream = new DataOutputStream( connection.getOutputStream() );
			outputStream.write( urlParams.getBytes() );
			outputStream.flush();
			outputStream.close();
			
			logger.log( Level.INFO, "Http POST Request: " + targetURL + "?" + urlParams );

			// Response	
			InputStream inputStream = connection.getInputStream();
			BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( inputStream ) );
			String line;
			StringBuffer responseBuffer = new StringBuffer();
			while( ( line = bufferedReader.readLine() ) != null )
				responseBuffer.append( line + "\n" );
			bufferedReader.close();
			
			String response = URLDecoder.decode( responseBuffer.toString(), "UTF-8" );
			
			logger.log( Level.INFO, "Http POST Response: " + response );
			
			return response;
		
		} catch( IOException e ) {
			logger.log( Level.SEVERE, "Failed to execute Http Post call.", e );
			throw new UnexpectedServerException();
			
		} finally {
			if( connection != null ) 
				connection.disconnect(); 
		}
		
	}
	
}
