package com.pratilipi.api.impl.blogpost;

import java.util.ArrayList;
import java.util.List;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.type.BlogPostState;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.util.BlogPostFilter;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.client.BlogPostData;
import com.pratilipi.data.util.BlogPostDataUtil;

@SuppressWarnings("serial")
@Bind( uri = "/blogpost/list" )
public class BlogPostListApi extends GenericApi {

	public static class GetRequest extends GenericRequest {

		@Validate( required = true )
		private Long blogId;
		private Language language;
		private BlogPostState state;
		
		private String cursor;
		private Integer resultCount;
		
	}

	@SuppressWarnings("unused")
	public static class Response extends GenericResponse {

		private List<BlogPostApi.Response> blogPostList;
		private String cursor;

		
		private Response() {}
		
		public Response( List<BlogPostData> blogPostList, String cursor ) {
			List<BlogPostApi.Response> genericBlogPostResponseList = new ArrayList<>( blogPostList.size() );
			for( BlogPostData blogPostData : blogPostList )
				genericBlogPostResponseList.add( new BlogPostApi.Response( blogPostData, BlogPostListApi.class ) );
			this.blogPostList = genericBlogPostResponseList;
			this.cursor = cursor;
		}
		
	}
	
	
	@Get
	public Response get( GetRequest request )
			throws InsufficientAccessException {
		
		BlogPostFilter blogPostFilter = new BlogPostFilter();
		blogPostFilter.setBlogId( request.blogId );
		blogPostFilter.setLanguage( request.language );
		blogPostFilter.setState( request.state );
		
		DataListCursorTuple<BlogPostData> blogPostDataListCursorTuple
				= BlogPostDataUtil.getBlogPostDataList(
						blogPostFilter,
						request.cursor,
						0,
						request.resultCount == null ? 10 : request.resultCount );
		
		return new Response(
				blogPostDataListCursorTuple.getDataList(),
				blogPostDataListCursorTuple.getCursor() );
		
	}

}
