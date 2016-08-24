package com.pratilipi.api.impl.user;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.data.util.AccessTokenDataUtil;

@SuppressWarnings("serial")
@Bind( uri= "/user/accesstoken/fcmtoken" )
public class UserAccessTokenFcmTokenApi extends GenericApi {

	public static class Request extends GenericRequest {
		
		@Validate( required = true )
		private String fcmToken;

	}
	
	
	@Get
	public GenericResponse post( Request request ) throws InvalidArgumentException {
		AccessTokenDataUtil.setFcmToken( request.fcmToken );
		return new GenericResponse();
	}
	
}
