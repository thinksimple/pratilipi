package com.pratilipi.api.impl.userpratilipi;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.impl.userpratilipi.shared.GenericUserPratilipiResponse;
import com.pratilipi.api.impl.userpratilipi.shared.PostUserPratilipiLibraryRequest;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.client.UserPratilipiData;
import com.pratilipi.data.util.UserPratilipiDataUtil;
import com.pratilipi.filter.AccessTokenFilter;

@SuppressWarnings("serial")
@Bind( uri = "/userpratilipi/library" )
public class UserPratilipiLibraryApi extends GenericApi {
	
	@Post
	public GenericUserPratilipiResponse post( PostUserPratilipiLibraryRequest request )
			throws InsufficientAccessException, UnexpectedServerException {

		UserPratilipiData userPratilipiData = new UserPratilipiData();
		userPratilipiData.setUserId( AccessTokenFilter.getAccessToken().getUserId() );
		userPratilipiData.setPratilipiId( request.getPratilipiId() );
		userPratilipiData.setAddedToLib( request.isAddedToLib() );
		
		userPratilipiData = UserPratilipiDataUtil.saveUserPratilipi( userPratilipiData );

		return new GenericUserPratilipiResponse( userPratilipiData );
		
	}

}
