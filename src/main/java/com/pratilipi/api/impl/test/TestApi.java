package com.pratilipi.api.impl.test;

import java.util.Date;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.AppProperty;

@SuppressWarnings("serial")
@Bind( uri = "/test" )
public class TestApi extends GenericApi {
	
	@Get
	public GenericResponse get( GenericRequest request ) throws InsufficientAccessException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		String appPropertyId = "Api.PratilipiProcess.ValidateData";
		AppProperty appProperty = dataAccessor.getAppProperty( appPropertyId );
		appProperty.setValue( new Date( 0 ) );
		appProperty = dataAccessor.createOrUpdateAppProperty( appProperty );

		appPropertyId = "Api.AuthorProcess.ValidateData";
		appProperty = dataAccessor.getAppProperty( appPropertyId );
		appProperty.setValue( new Date( 0 ) );
		appProperty = dataAccessor.createOrUpdateAppProperty( appProperty );
		
		appPropertyId = "Api.UserProcess.ValidateData";
		appProperty = dataAccessor.getAppProperty( appPropertyId );
		appProperty.setValue( new Date( 0 ) );
		appProperty = dataAccessor.createOrUpdateAppProperty( appProperty );
		
		return new GenericResponse();
		
	}
	
}
