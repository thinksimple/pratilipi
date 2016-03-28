package com.pratilipi.api.impl.user;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.impl.user.shared.GenericUserResponse;
import com.pratilipi.api.impl.user.shared.PostUserRequest;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.util.AuthorDataUtil;
import com.pratilipi.data.util.UserDataUtil;
import com.pratilipi.filter.UxModeFilter;

@SuppressWarnings("serial")
@Bind( uri= "/user" )
public class UserApi extends GenericApi {

	@Post
	public GenericUserResponse post( PostUserRequest request )
			throws InvalidArgumentException, InsufficientAccessException {

		UserData userData = new UserData( request.getId() );
		if( request.hasEmail() )
			userData.setEmail( request.getEmail() );
		if( request.hasPhone() )
			userData.setPhone( request.getPhone() );
		
		// Save UserData.
		userData = UserDataUtil.saveUserData( userData );
		
		
		if( request.getId() == null ) { // New user
			
			String firstName = request.getName().trim();
			String lastName = null;
			if( firstName.lastIndexOf( ' ' ) != -1 ) {
				lastName = firstName.substring( firstName.lastIndexOf( ' ' ) + 1 );
				firstName = firstName.substring( 0, firstName.lastIndexOf( ' ' ) );
			}
			
			userData.setFirstName( firstName );
			userData.setLastName( lastName );
			
			// Create Author profile for the User.
			Long authorId = AuthorDataUtil.createAuthorProfile( userData, UxModeFilter.getFilterLanguage() );
			
			userData.setProfilePageUrl( "/author/" + authorId );
			
		}
		
		
		return new GenericUserResponse( userData );
		
	}
	
}
