package com.pratilipi.api.user;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.api.user.shared.PostUserValidationFacebookRequest;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.util.FacebookApi;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.User;
import com.pratilipi.data.util.UserDataUtil;

@SuppressWarnings("serial")
@Bind( uri= "/user/facebook/validation" )
public class UserValidationFacebookApi extends GenericApi {
	
	@Post
	public static GenericResponse validateRequest( PostUserValidationFacebookRequest request )
			throws UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = dataAccessor.getUser( request.getUserId() );
		
		if( ! FacebookApi.validateUser( user.getFacebookId(), request.getFbAccessToken() ) ) 
			UserDataUtil.logoutUser( dataAccessor.getAccessToken( request.getPratilipiAccessToken() ) );
		
		return new GenericResponse();
	}
	
}
