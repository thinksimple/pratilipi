package com.pratilipi.api.user;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.api.user.shared.PostUserValidationFacebookRequest;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.util.FacebookApi;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.util.UserDataUtil;

@SuppressWarnings("serial")
@Bind( uri= "/user/facebook/validation" )
public class UserFacebookValidationApi extends GenericApi {
	
	@Post
	public static GenericResponse validateRequest( PostUserValidationFacebookRequest request )
			throws UnexpectedServerException {
		
		if( ! FacebookApi.validateUserAccessToken( request.getFbAccessToken() ) ) 
			UserDataUtil.logoutUser( DataAccessorFactory.getDataAccessor().getAccessToken( request.getPratilipiAccessToken() ) );
		
		return new GenericResponse();
	}
	
}
