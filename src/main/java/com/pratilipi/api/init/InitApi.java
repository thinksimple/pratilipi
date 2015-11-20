package com.pratilipi.api.init;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.UnexpectedServerException;

@SuppressWarnings("serial")
@Bind( uri = "/init" )
public class InitApi extends GenericApi {
	
	private static final Logger logger = Logger.getLogger( InitApi.class.getName() );
	
	public static String excutePostCall( String targetURL, String urlParameters ) throws UnexpectedServerException {
		URL url = null;
		HttpURLConnection connection = null;	
		try {
			//Create connection
			url = new URL( targetURL );
			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod( "POST" );
			connection.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded" );
			connection.setRequestProperty( "Content-Length", "" + Integer.toString( urlParameters.getBytes().length ) );
			connection.setUseCaches( false );
			connection.setDoInput( true );
			connection.setDoOutput( true );

			//Send request
			DataOutputStream dataOutputStream = new DataOutputStream( connection.getOutputStream() );
			dataOutputStream.writeBytes( urlParameters );
			dataOutputStream.flush();
			dataOutputStream.close();

			//Get Response	
			InputStream inputStream = connection.getInputStream();
			BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( inputStream ) );
			String line;
			StringBuffer response = new StringBuffer();
			while( ( line = bufferedReader.readLine() ) != null )
				response.append( line + "\n" );
			
			bufferedReader.close();
			return new Gson().fromJson( response.toString(), JsonElement.class ).toString();
		
		} catch ( IOException e ) {
			logger.log( Level.SEVERE, "POST call failed for URL : " + targetURL + "/?" + urlParameters );
			throw new UnexpectedServerException();
			
		} finally {
			if( connection != null ) 
				connection.disconnect(); 
		}
	}
	
	@Get
	public GenericResponse get( GenericRequest request ) throws IOException, UnexpectedServerException {
		String response = excutePostCall( "https://graph.facebook.com:443", "id=http://www.pratilipi.com/pon-kulendiren/azhagu&scrape=true" );
		logger.log( Level.INFO, response );
		return new GenericResponse();
		
	}
	
}