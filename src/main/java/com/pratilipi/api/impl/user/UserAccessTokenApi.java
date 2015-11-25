package com.pratilipi.api.impl.user;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.impl.user.shared.GetUserAccessTokenRequest;
import com.pratilipi.api.impl.user.shared.GetUserAccessTokenResponse;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.util.AccessTokenDataUtil;

@SuppressWarnings("serial")
@Bind( uri= "/user/accesstoken" )
public class UserAccessTokenApi extends GenericApi {

	@Get
	public GetUserAccessTokenResponse get( GetUserAccessTokenRequest request )
			throws InvalidArgumentException {
		
		AccessToken accessToken;
		if( request.getAccessToken() == null || request.getAccessToken().isEmpty() )
			accessToken = AccessTokenDataUtil.newUserAccessToken();
		else
			accessToken = AccessTokenDataUtil.renewUserAccessToken( request.getAccessToken() );
		
		return new GetUserAccessTokenResponse( accessToken.getId(), accessToken.getExpiry() );
		
	}
	
}
