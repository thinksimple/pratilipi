package com.pratilipi.api.impl.user;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.impl.user.shared.GenericUserResponse;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.data.util.UserDataUtil;

@SuppressWarnings("serial")
@Bind( uri= "/user/logout" )
public class UserLogoutApi extends GenericApi {

	@Get
	public GenericUserResponse logout( GenericRequest request )
			throws InvalidArgumentException {
		
		return new GenericUserResponse( UserDataUtil.logoutUser() );
		
	}
	
}
