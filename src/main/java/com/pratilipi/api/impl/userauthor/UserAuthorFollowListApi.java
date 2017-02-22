package com.pratilipi.api.impl.userauthor;

import java.util.ArrayList;
import java.util.List;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.impl.author.AuthorApi;
import com.pratilipi.api.impl.user.UserV1Api;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.client.AuthorData;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.util.UserAuthorDataUtil;

@SuppressWarnings("serial")
@Bind( uri = "/userauthor/follow/list" )
public class UserAuthorFollowListApi extends GenericApi {
	
	public static class GetRequest extends GenericRequest {

		private Long userId;
		private Long authorId;
		private String cursor;
		private Integer offset;
		private Integer resultCount;
		
		public void setUserId( Long userId ) {
			this.userId = userId;
		}

		public void setAuthorId( Long authorId ) {
			this.authorId = authorId;
		}

		public void setCursor( String cursor ) {
			this.cursor = cursor;
		}

		public void setOffset( Integer offset ) {
			this.offset = offset;
		}

		public void setResultCount( Integer resultCount ) {
			this.resultCount = resultCount;
		}

	}
	
	public static class Response extends GenericResponse { 

		private List<UserV1Api.Response> userList;
		private List<AuthorApi.Response> authorList;
		private String cursor;
		private Long numberFound;

		
		private Response() {}
		
		public Response( List<UserData> userList, List<AuthorData> authorList, String cursor, Long numberFound ) {
			
			if( userList != null ) {
				this.userList = new ArrayList<>( userList.size() ); 
				for( UserData user : userList )
					this.userList.add( new UserV1Api.Response( user, UserAuthorFollowListApi.class ) );
				this.numberFound = numberFound;
			}
			
			if( authorList != null ) {
				this.authorList = new ArrayList<>( authorList.size() ); 
				for( AuthorData author : authorList )
					this.authorList.add( new AuthorApi.Response( author, UserAuthorFollowListApi.class ) );
				this.numberFound = numberFound;
			}
			
			this.cursor = cursor;
		
		}


		public List<UserV1Api.Response> getUserList() {
			return userList;
		}

		public List<AuthorApi.Response> getAuthorList() {
			return authorList;
		}

		public String getCursor() {
			return cursor;
		}
		
		public Long getNumberFound() {
			return numberFound;
		}
		
	}

	
	@Get
	public Response get( GetRequest request ) throws InsufficientAccessException {
		
		if( request.userId != null ) {
			DataListCursorTuple<AuthorData> authorListCursorTuple = UserAuthorDataUtil.getUserFollowList(
					request.userId,
					request.cursor,
					request.offset,
					request.resultCount );
			return new Response(
					null,
					authorListCursorTuple.getDataList(),
					authorListCursorTuple.getCursor(),
					authorListCursorTuple.getNumberFound() );
		}
		
		
		if( request.authorId != null ) {
			DataListCursorTuple<UserData> authorListCursorTuple = UserAuthorDataUtil.getAuthorFollowList(
					request.authorId,
					request.cursor,
					request.offset,
					request.resultCount );
			return new Response(
					authorListCursorTuple.getDataList(),
					null,
					authorListCursorTuple.getCursor(),
					authorListCursorTuple.getNumberFound() );
		}

		return new Response();
		
	}
	
}
