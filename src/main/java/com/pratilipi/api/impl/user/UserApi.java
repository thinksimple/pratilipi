package com.pratilipi.api.impl.user;

import java.util.LinkedList;
import java.util.List;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.impl.author.AuthorApi;
import com.pratilipi.api.impl.user.shared.GenericUserResponse;
import com.pratilipi.api.impl.user.shared.PostUserRequest;
import com.pratilipi.api.impl.userauthor.UserAuthorFollowListApi;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.type.UserState;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.util.AuthorDataUtil;
import com.pratilipi.data.util.UserDataUtil;
import com.pratilipi.filter.UxModeFilter;
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
@Bind( uri= "/user" )
public class UserApi extends GenericApi {

	public static class Response {
		
		private Long userId;
		
		private AuthorApi.Response author;
		
		private String displayName;
		private String profilePageUrl;
		private String profileImageUrl;
		
		private Long followCount;
		private Boolean following;
	
		
		@SuppressWarnings("unused")
		private Response() {}
		
		public Response( UserData userData, boolean embed ) {
			userId = userData.getId();
			displayName = userData.getDisplayName();
			profilePageUrl = userData.getProfilePageUrl();
			profileImageUrl = userData.getProfileImageUrl();
		}

		public Response( UserData userData, Class<? extends GenericApi> clazz ) {
			
			if( clazz == AuthorApi.class ) {
				
				this.userId = userData.getId();
				
			} else if( clazz == UserAuthorFollowListApi.class ) {

				if( userData.getAuthor() == null ) {
					this.userId = userData.getId();
					this.followCount = userData.getFollowCount();
					this.following = userData.isFollowing();
				} else {
					this.userId = userData.getId();
					this.author = new AuthorApi.Response( userData.getAuthor(), clazz );
					this.displayName = userData.getDisplayName();
					this.profilePageUrl = userData.getProfilePageUrl();
					this.profileImageUrl = userData.getProfileImageUrl();
					this.followCount = userData.getFollowCount();
					this.following = userData.isFollowing();
				}
			
			}
			
		}
		
		
		public Long getId() {
			return userId;
		}

		public AuthorApi.Response getAuthor() {
			return author;
		}

		public String getDisplayName() {
			return displayName;
		}

		public String getProfilePageUrl() {
			return profilePageUrl;
		}
		
		public String getProfileImageUrl() {
			return profileImageUrl;
		}
		
		public String getUserImageUrl( int width ) {
			return profileImageUrl.indexOf( '?' ) == -1
					? profileImageUrl + "?width=" + width
					: profileImageUrl + "&width=" + width;
		}
		
		public Long getFollowCount() {
			return followCount;
		}
		
		public boolean isFollowing() {
			return following;
		}
		
	}

	
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
		
		
		List<Task> taskList = new LinkedList<>();
		Long authorId = null;
		
		
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
			authorId = AuthorDataUtil.createAuthorProfile( userData, UxModeFilter.getFilterLanguage() );
			
			userData.setProfilePageUrl( "/author/" + authorId );
			
			
			// Send welcome mail to the user
			Task task = TaskQueueFactory.newTask()
					.setUrl( "/user/email" )
					.addParam( "userId", userData.getId().toString() )
					.addParam( "language", UxModeFilter.getDisplayLanguage().toString() )
					.addParam( "sendWelcomeMail", "true" );
			taskList.add( task );
			
		} else {
			
			authorId = DataAccessorFactory.getDataAccessor()
					.getAuthorByUserId( userData.getId() )
					.getId();
			
		}
		

		// Send verification mail if user sate is REGISTERED
		if( userData.getState() == UserState.REGISTERED ) {
			Task task = TaskQueueFactory.newTask()
					.setUrl( "/user/email" )
					.addParam( "userId", userData.getId().toString() )
					.addParam( "language", UxModeFilter.getDisplayLanguage().toString() )
					.addParam( "sendEmailVerificationMail", "true" );
			taskList.add( task );
		}
		
		
		TaskQueueFactory.getUserTaskQueue().addAll( taskList );
		
		
		// Process Author data
		Task task = TaskQueueFactory.newTask()
				.setUrl( "/author/process" )
				.addParam( "authorId", authorId.toString() )
				.addParam( "processData", "true" );
		TaskQueueFactory.getAuthorTaskQueue().add( task );
		
		
		return new GenericUserResponse( userData );
		
	}
	
}
