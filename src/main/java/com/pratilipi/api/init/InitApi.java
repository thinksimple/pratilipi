package com.pratilipi.api.init;

import java.io.IOException;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.init.shared.GetInitRequest;
import com.pratilipi.api.shared.GenericResponse;

@SuppressWarnings("serial")
@Bind( uri = "/init" )
public class InitApi extends GenericApi {

	@Get
	public GenericResponse get( GetInitRequest request ) throws IOException {

		return new GenericResponse();
		
	}
	
}