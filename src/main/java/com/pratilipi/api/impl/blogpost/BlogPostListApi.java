package com.pratilipi.api.impl.blogpost;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.impl.blogpost.shared.GetBlogPostListRequest;
import com.pratilipi.api.impl.blogpost.shared.GetBlogPostListResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.client.BlogPostData;
import com.pratilipi.data.util.BlogPostDataUtil;

@SuppressWarnings("serial")
@Bind( uri = "/blogpost/list" )
public class BlogPostListApi extends GenericApi {

	@Get
	public GetBlogPostListResponse get( GetBlogPostListRequest request )
			throws InsufficientAccessException {
		
		DataListCursorTuple<BlogPostData> blogPostDataListCursorTuple
				= BlogPostDataUtil.getBlogPostDataList(
						request.getBlogId(),
						request.getCursor(),
						0,
						request.getResultCount() == null ? 20 : request.getResultCount() );
		
		return new GetBlogPostListResponse(
				blogPostDataListCursorTuple.getDataList(),
				blogPostDataListCursorTuple.getCursor() );
		
	}

}
