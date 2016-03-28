package com.pratilipi.api.impl.user;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.impl.user.shared.PostUserFacebookValidationRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.util.FacebookApi;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.util.UserDataUtil;

@SuppressWarnings("serial")
@Bind( uri= "/user/facebook/validation" )
public class UserFacebookValidationApi extends GenericApi {
	
	@Post
	public GenericResponse post( PostUserFacebookValidationRequest request )
			throws UnexpectedServerException {
		
		if( ! FacebookApi.validateUserAccessToken( request.getFbAccessToken() ) ) 
			UserDataUtil.logoutUser( DataAccessorFactory.getDataAccessor().getAccessToken( request.getPratilipiAccessToken() ) );
		
		return new GenericResponse();
	}
	
}
