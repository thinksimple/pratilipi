package com.pratilipi.api.impl.blogpost.shared;

import java.util.ArrayList;
import java.util.List;

import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.data.client.AuthorData;
import com.pratilipi.data.client.BlogPostData;

@SuppressWarnings("unused")
public class GetBlogPostListResponse extends GenericResponse {

	private List<GenericBlogPostResponse> blogPostList;
	private String cursor;

	
	private GetBlogPostListResponse() {}
	
	public GetBlogPostListResponse( List<BlogPostData> blogPostList, String cursor ) {
		List<GenericBlogPostResponse> genericBlogPostResponseList = new ArrayList<>( blogPostList.size() );
		for( BlogPostData blogPostData : blogPostList )
			genericBlogPostResponseList.add( new GenericBlogPostResponse( blogPostData ) );
		this.blogPostList = genericBlogPostResponseList;
		this.cursor = cursor;
	}
	
}
