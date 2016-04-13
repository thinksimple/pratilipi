package com.pratilipi.api.impl.blogpost;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.impl.blogpost.shared.GenericBlogPostResponse;
import com.pratilipi.api.impl.blogpost.shared.PostBlogPostRequest;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.data.client.BlogPostData;
import com.pratilipi.data.util.BlogPostDataUtil;
import com.pratilipi.filter.UxModeFilter;

@SuppressWarnings("serial")
@Bind( uri = "/blogpost" )
public class BlogPostApi extends GenericApi {

	@Post
	public GenericBlogPostResponse post( PostBlogPostRequest request )
			throws InvalidArgumentException, InsufficientAccessException {
		
		BlogPostData blogPostData = new BlogPostData( request.getId() );
		blogPostData.setBlogId( request.getBlogId() );
		blogPostData.setTitle( request.getTitle() );
		blogPostData.setTitleEn( request.getTitleEn() );
		blogPostData.setContent( request.getContent() );
		blogPostData.setLanguage( UxModeFilter.getFilterLanguage() );
		blogPostData.setState( request.getState() );
		
		blogPostData = BlogPostDataUtil.saveBlogPostData( blogPostData );
		
		return new GenericBlogPostResponse( blogPostData );
		
	}

}
