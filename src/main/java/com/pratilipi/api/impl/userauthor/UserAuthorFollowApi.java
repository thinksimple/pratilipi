package com.pratilipi.api.impl.userauthor;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.data.client.UserAuthorData;
import com.pratilipi.data.util.UserAuthorDataUtil;
import com.pratilipi.filter.AccessTokenFilter;
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
@Bind( uri = "/userauthor/follow" )
public class UserAuthorFollowApi extends GenericApi {
	
	public static class GetRequest extends GenericRequest {

		@Validate( required = true, minLong = 1L )
		private Long authorId;
		
		public void setAuthorId( Long authorId ) {
			this.authorId = authorId;
		}
		
	}

	public static class PostRequest extends GenericRequest {

		@Validate( required = true, minLong = 1L )
		private Long authorId;
		
		@Validate( required = true )
		private Boolean following;

	}
	
	public static class Response extends GenericResponse {

		private Long authorId;
		
		private Boolean following;
		
		
		@SuppressWarnings("unused")
		private Response() { }
		
		Response( UserAuthorData userAuthorData ) {
			if( userAuthorData != null ) {
				this.authorId = userAuthorData.getAuthorId();
				this.following = userAuthorData.isFollowing();
			}
		}
		
		
		public Long getAuthorId() {
			return authorId;
		}
		
		public Boolean isFollowing() {
			return following;
		}
		
	}
	
	
	@Get
	public Response get( GetRequest request ) {
		UserAuthorData userAuthorData = UserAuthorDataUtil.getUserAuthor( 
				AccessTokenFilter.getAccessToken().getUserId(), 
				request.authorId );
		return new Response( userAuthorData );
	}
	
	
	@Post
	public Response post( PostRequest request )
			throws InvalidArgumentException, InsufficientAccessException {
		
		Long userId = AccessTokenFilter.getAccessToken().getUserId();
		
		UserAuthorData userAuthorData = UserAuthorDataUtil.saveUserAuthorFollow(
				userId,
				request.authorId,
				request.following );
		
		Task task_1 = TaskQueueFactory.newTask()
				.setUrl( "/author/process" )
				.addParam( "authorId", request.authorId.toString() )
				.addParam( "updateUserAuthorStats", "true" );
		Task task_2 = TaskQueueFactory.newTask()
				.setUrl( "/user/process" )
				.addParam( "userId", userId.toString() )
				.addParam( "updateFollowsDoc", "true" )
				.addParam( "updateUserAuthorStats", "true" );
		TaskQueueFactory.getUserAuthorTaskQueue().addAll( task_1, task_2 );
		
		return new Response( userAuthorData );
	
	}

}
