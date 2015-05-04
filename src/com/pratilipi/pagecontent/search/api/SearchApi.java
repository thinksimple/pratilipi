package com.pratilipi.pagecontent.search.api;

import java.util.List;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Get;
import com.claymus.data.access.DataListCursorTuple;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.access.SearchAccessor;
import com.pratilipi.data.transfer.shared.PratilipiData;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;
import com.pratilipi.pagecontent.search.shared.GetSearchResultsRequest;
import com.pratilipi.pagecontent.search.shared.GetSearchResultsResponse;

@SuppressWarnings( "serial" )
@Bind( uri = "/search" )
public class SearchApi extends GenericApi {

	@Get
	public GetSearchResultsResponse getSearchResults( GetSearchResultsRequest request ){
		
		SearchAccessor searchAccessor = DataAccessorFactory.getSearchAccessor();
		DataListCursorTuple<Long> pratilipiIdListCursorTuple = 
							searchAccessor.searchQuery( request.getQuery(), request.getCursor(), request.getResultCount() );
		List<PratilipiData> pratilipiDataList = PratilipiContentHelper.createPratilipiDataList( 
															pratilipiIdListCursorTuple.getDataList(),
															false,
															false,
															this.getThreadLocalRequest() );
		
		return new GetSearchResultsResponse( pratilipiDataList, pratilipiIdListCursorTuple.getCursor() );
	}
}
