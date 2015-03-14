package com.pratilipi.pagecontent.author.api;

import java.util.ArrayList;
import java.util.List;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Get;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataListCursorTuple;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.commons.shared.AuthorFilter;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.pagecontent.author.api.shared.GetAuthorListRequest;
import com.pratilipi.pagecontent.author.api.shared.GetAuthorListResponse;
import com.pratilipi.service.shared.data.AuthorData;

@SuppressWarnings( "serial" )
@Bind( uri = "/authors" )
public class AuthorListApi extends GenericApi {

	@Get
	public GetAuthorListResponse getAuthorList( GetAuthorListRequest request )
		throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {
		
		AuthorFilter authorFilter = new AuthorFilter();
		authorFilter.setLanguageId( request.getLanguageId() );
		authorFilter.setOrderByContentPublished( true );

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		PratilipiHelper pratilipiHelper = PratilipiHelper.get( this.getThreadLocalRequest() );

		DataListCursorTuple<Author> authorListCursorTuple =
					dataAccessor.getAuthorList( authorFilter, 
								request.getCursor(), 
								request.getResultCount() == null ? 20 : request.getResultCount() );
		
		List<AuthorData> authorDataList = new ArrayList<AuthorData>();
		for( Author author : authorListCursorTuple.getDataList() )
			authorDataList.add( pratilipiHelper.createAuthorData( author.getId() ) );
		
		return new GetAuthorListResponse( authorDataList, authorListCursorTuple.getCursor() );
	}
}
