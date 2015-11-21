package com.pratilipi.api.init;

import java.io.IOException;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.init.shared.InitApiRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.UnexpectedServerException;

@SuppressWarnings("serial")
@Bind( uri = "/init" )
public class InitApi extends GenericApi {
	
	@Get
	public GenericResponse get( InitApiRequest request ) throws IOException, UnexpectedServerException {
		
		return new GenericResponse();
		
	}
	
}