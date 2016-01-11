package com.pratilipi.api.impl.author;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.impl.author.shared.GetAuthorListRequest;
import com.pratilipi.api.impl.author.shared.GetAuthorListResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.util.AuthorFilter;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.client.AuthorData;
import com.pratilipi.data.util.AuthorDataUtil;

@SuppressWarnings("serial")
@Bind( uri = "/author/list" )
public class AuthorListApi extends GenericApi {

	@Get
	public GetAuthorListResponse getAuthorList( GetAuthorListRequest request )
			throws InsufficientAccessException {
		
		AuthorFilter authorFilter = new AuthorFilter();
		authorFilter.setLanguage( request.getLanguage() );
		authorFilter.setOrderByContentPublished( request.getOrderByContentPublished() );

		DataListCursorTuple<AuthorData> authorListCursorTuple =
				AuthorDataUtil.getAuthorDataList(
						authorFilter,
						request.getCursor(),
						request.getResultCount() == null ? 20 : request.getResultCount() );
		
		return new GetAuthorListResponse(
				authorListCursorTuple.getDataList(),
				authorListCursorTuple.getCursor() );
		
	}

}
