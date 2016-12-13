package com.pratilipi.api.impl.user;

import java.util.LinkedList;
import java.util.List;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.impl.author.AuthorApi;
import com.pratilipi.api.impl.author.AuthorListApi;
import com.pratilipi.api.impl.blogpost.BlogPostApi;
import com.pratilipi.api.impl.blogpost.BlogPostListApi;
import com.pratilipi.api.impl.comment.CommentApi;
import com.pratilipi.api.impl.userauthor.UserAuthorFollowListApi;
import com.pratilipi.api.impl.userpratilipi.UserPratilipiApi;
import com.pratilipi.api.impl.userpratilipi.UserPratilipiReviewListApi;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.UserState;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.client.AuthorData;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.util.AuthorDataUtil;
import com.pratilipi.data.util.UserDataUtil;
import com.pratilipi.filter.AccessTokenFilter;
import com.pratilipi.filter.UxModeFilter;
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
@Bind( uri= "/user" )
public class UserApi extends GenericApi {

	public static class PostRequest extends GenericRequest {

		@Validate( minLong = 0L )
		private Long userId; // userId == 0L, for adding new user
		
		private String name;
		private boolean hasName;
		
		@Validate( regEx = REGEX_EMAIL, regExErrMsg = ERR_EMAIL_INVALID )
		private String email;
		private boolean hasEmail;
		
		@Validate( regEx = REGEX_PHONE, regExErrMsg = ERR_PHONE_INVALID )
		private String phone;
		private boolean hasPhone;
		
		private Language language;
		private boolean hasLanguage;
		
		
		public Long getId() {
			return userId;
		}
		
		public String getName() {
			return name;
		}
		
		public boolean hasName() {
			return hasName;
		}

		public String getEmail() {
			return email;
		}
		
		public boolean hasEmail() {
			return hasEmail;
		}
		
		public String getPhone() {
			return phone;
		}
		
		public boolean hasPhone() {
			return hasPhone;
		}
		
	}
	
	@SuppressWarnings("unused")
	public static class Response extends GenericResponse {
		
		private Long userId;
		@Deprecated
		private Long authorId;
		private AuthorApi.Response author;
		private String displayName;
		private String email;
		private String phone;
		private UserState state;

		private Boolean isGuest;
		private Boolean isEmailVerified;
		
		private String profilePageUrl;
		private String profileImageUrl;

		private Long followCount;
		private Boolean following;
		
		private String firebaseToken;

		
		private Response() {}
		
		private Response( UserData userData ) {
			this( userData, UserApi.class );
		}
		
		public Response( UserData userData, Class<? extends GenericApi> clazz ) {
			
			if( clazz == UserApi.class
					|| clazz == UserLoginApi.class || clazz == UserLoginFacebookApi.class || clazz == UserLoginGoogleApi.class
					|| clazz == UserLogoutApi.class
					|| clazz == UserRegisterApi.class
					|| clazz == UserPasswordUpdateApi.class
					|| clazz == UserVerificationApi.class ) {
				
				this.userId = userData.getId();
				this.authorId = userData.getAuthor().getId();
				this.author = new AuthorApi.Response( userData.getAuthor(), UserLoginApi.class );
				this.displayName = userData.getDisplayName();
				this.email = userData.getEmail();
				this.phone = userData.getPhone();
				this.state = userData.getState();
				this.isGuest = userData.getState() == UserState.GUEST;
				this.isEmailVerified = userData.getState() == UserState.ACTIVE;
				this.profilePageUrl = userData.getProfilePageUrl();
				this.profileImageUrl = userData.getProfileImageUrl();
				this.firebaseToken = userData.getFirebaseToken();
				
			} else if( clazz == AuthorApi.class ) {
				
				this.userId = userData.getId();
				
			} else if( clazz == AuthorListApi.class ) {
				
				this.userId = userData.getId();
				this.email = userData.getEmail();
				this.phone = userData.getPhone();
				this.state = userData.getState();
				
			} else if( clazz == BlogPostApi.class || clazz == BlogPostListApi.class ) {
				
				this.displayName = userData.getDisplayName();
				this.profilePageUrl = userData.getProfilePageUrl();
				this.profileImageUrl = userData.getProfileImageUrl();
				
			} else if( clazz == UserPratilipiApi.class || clazz == UserPratilipiReviewListApi.class || clazz == CommentApi.class ) {
				
				this.userId = userData.getId();
				if( UxModeFilter.isAndroidApp() )
					this.author = new AuthorApi.Response( userData.getAuthor(), clazz );
				this.displayName = userData.getDisplayName();
				this.profilePageUrl = userData.getProfilePageUrl();
				this.profileImageUrl = userData.getProfileImageUrl();
				
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

		public String getEmail() {
			return email;
		}

		public String getPhone() {
			return phone;
		}

		public UserState getState() {
			return state;
		}

		public Boolean isGuest() {
			return isGuest;
		}

		public Boolean isEmailVerified() {
			return isEmailVerified;
		}
		
		public String getProfilePageUrl() {
			return profilePageUrl;
		}
		
		public String getProfileImageUrl() {
			return profileImageUrl;
		}
		
		public String getProfileImageUrl( int width ) {
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
	public Response post( PostRequest request )
			throws InvalidArgumentException, InsufficientAccessException {

		UserData userData = request.userId == null
				? new UserData( AccessTokenFilter.getAccessToken().getUserId() )
				: new UserData( request.userId.equals( 0L ) ? null : request.userId );
		
		if( request.hasEmail() )
			userData.setEmail( request.getEmail() );
		if( request.hasPhone() )
			userData.setPhone( request.getPhone() );
		if( request.hasLanguage )
			userData.setLanguage( request.language );
		
		// Save UserData.
		userData = UserDataUtil.saveUserData( userData );
		
		
		List<Task> taskList = new LinkedList<>();
		Long authorId = null;
		
		
		if( request.getId() != null && request.getId().equals( 0L ) ) { // New user
			
			String firstName = request.getName().trim();
			String lastName = null;
			if( firstName.lastIndexOf( ' ' ) != -1 ) {
				lastName = firstName.substring( firstName.lastIndexOf( ' ' ) + 1 );
				firstName = firstName.substring( 0, firstName.lastIndexOf( ' ' ) );
			}
			
			userData.setFirstName( firstName );
			userData.setLastName( lastName );
			
			// Create Author profile for the User.
			authorId = AuthorDataUtil.createAuthorProfile(
					userData,
					request.language == null ? UxModeFilter.getFilterLanguage() : request.language );
			
			userData.setAuthor( new AuthorData( authorId ) );
			userData.setProfilePageUrl( "/author/" + authorId );
			
			
			// Send welcome mail to the user
			Task task = TaskQueueFactory.newTask()
					.setUrl( "/user/email" )
					.addParam( "userId", userData.getId().toString() )
					.addParam( "language", request.language == null ? UxModeFilter.getDisplayLanguage().toString() : request.language.toString() )
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
		
		
		return new Response( userData );
		
	}
	
}
