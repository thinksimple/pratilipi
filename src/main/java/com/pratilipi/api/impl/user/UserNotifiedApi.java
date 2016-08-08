package com.pratilipi.api.impl.user;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.Notification;
import com.pratilipi.data.type.User;
import com.pratilipi.filter.AccessTokenFilter;

@SuppressWarnings("serial")
@Bind( uri= "/user/notified" )
public class UserNotifiedApi extends GenericApi {

	public static class PostRequest extends GenericRequest {

		@Validate( required = true, minLong = 1L )
		private Long lastNotificationId;
		
	}
	
	
	@Post
	public GenericResponse post( PostRequest request ) {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		Long userId = AccessTokenFilter.getAccessToken().getUserId();
		User user = dataAccessor.getUser( userId );
		if( user != null ) {
			Notification notification = dataAccessor.getNotification( request.lastNotificationId );
			user.setLastNotified( notification.getLastUpdated() );
			user = dataAccessor.createOrUpdateUser( user );
		}
		
		return new GenericResponse();
		
	}
	
}
