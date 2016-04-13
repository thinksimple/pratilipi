package com.pratilipi.api.impl.blogpost;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.impl.blogpost.shared.GetBlogPostListRequest;
import com.pratilipi.api.impl.blogpost.shared.GetBlogPostListResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.type.BlogPostState;
import com.pratilipi.common.util.BlogPostFilter;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.client.BlogPostData;
import com.pratilipi.data.util.BlogPostDataUtil;
import com.pratilipi.filter.UxModeFilter;

@SuppressWarnings("serial")
@Bind( uri = "/blogpost/list" )
public class BlogPostListApi extends GenericApi {

	@Get
	public GetBlogPostListResponse get( GetBlogPostListRequest request )
			throws InsufficientAccessException {
		
		BlogPostFilter blogPostFilter = new BlogPostFilter();
		blogPostFilter.setBlogId( request.getBlogId() );
		blogPostFilter.setLanguage( UxModeFilter.getFilterLanguage() );
		if( ! BlogPostDataUtil.hasAccessToListBlogPostData( blogPostFilter ) )
			blogPostFilter.setState( BlogPostState.PUBLISHED );
		
		DataListCursorTuple<BlogPostData> blogPostDataListCursorTuple
				= BlogPostDataUtil.getBlogPostDataList(
						blogPostFilter,
						request.getCursor(),
						0,
						request.getResultCount() == null ? 20 : request.getResultCount() );
		
		return new GetBlogPostListResponse(
				blogPostDataListCursorTuple.getDataList(),
				blogPostDataListCursorTuple.getCursor() );
		
	}

}
