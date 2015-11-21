package com.pratilipi.api.init;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.init.shared.InitApiRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.util.NetworkCallsUtil;

@SuppressWarnings("serial")
@Bind( uri = "/init" )
public class InitApi extends GenericApi {
	
	private static final Logger logger = Logger.getLogger( InitApi.class.getName() );
	
	@Get

	public GenericResponse get( InitApiRequest request ) throws IOException, UnexpectedServerException {
		
		String response = null;
		if( request.getMethod().equalsIgnoreCase( "POST" ) )
			response = NetworkCallsUtil.makePostCall( request.getTargetURL(), request.getURLParameters() );
		else if( request.getMethod().equalsIgnoreCase( "GET" ) )
			response = NetworkCallsUtil.makeGetCall( request.getTargetURL(), request.getURLParameters() );
		
		logger.log( Level.INFO, "Response = " + response );
		return new GenericResponse();
		
	}
	
}