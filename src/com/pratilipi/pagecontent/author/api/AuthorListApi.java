package com.pratilipi.pagecontent.author.api;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Get;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.data.access.DataListCursorTuple;
import com.pratilipi.commons.shared.AuthorFilter;
import com.pratilipi.data.transfer.shared.AuthorData;
import com.pratilipi.pagecontent.author.AuthorContentHelper;
import com.pratilipi.pagecontent.author.api.shared.GetAuthorListRequest;
import com.pratilipi.pagecontent.author.api.shared.GetAuthorListResponse;

@SuppressWarnings( "serial" )
@Bind( uri = "/author/list" )
public class AuthorListApi extends GenericApi {

	@Get
	public GetAuthorListResponse getAuthorList( GetAuthorListRequest request )
			throws InsufficientAccessException {
		
		AuthorFilter authorFilter = new AuthorFilter();
		authorFilter.setLanguageId( request.getLanguageId() );
		authorFilter.setOrderByContentPublished( request.getOrderByContentPublished() );

		DataListCursorTuple<AuthorData> authorListCursorTuple =
				AuthorContentHelper.getAuthorList(
						authorFilter,
						request.getCursor(),
						request.getResultCount() == null ? 20 : request.getResultCount(),
						this.getThreadLocalRequest() );
		
		return new GetAuthorListResponse(
				authorListCursorTuple.getDataList(),
				authorListCursorTuple.getCursor() );
	}
}
