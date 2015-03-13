package com.pratilipi.pagecontent.pratilipi.api;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Get;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataListCursorTuple;
import com.pratilipi.commons.shared.PratilipiFilter;
import com.pratilipi.data.transfer.shared.PratilipiData;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;
import com.pratilipi.pagecontent.pratilipi.api.shared.GetPratilipiListRequest;
import com.pratilipi.pagecontent.pratilipi.api.shared.GetPratilipiListResponse;

@SuppressWarnings("serial")
@Bind( uri = "/pratilipi/list" )
public class PratilipiListApi extends GenericApi {

	@Get
	public GetPratilipiListResponse getPratilipiList( GetPratilipiListRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		pratilipiFilter.setType( request.getType() );
		pratilipiFilter.setLanguageId( request.getLanguageId() );
		pratilipiFilter.setAuthorId( request.getAuthorId() );
		pratilipiFilter.setState( request.getState() );
		
		DataListCursorTuple<PratilipiData> pratilipiListCursorTuple =
				PratilipiContentHelper.getPratilipiList(
						pratilipiFilter,
						request.getCursor(),
						request.getResultCount() == null ? 20 : request.getResultCount(),
						this.getThreadLocalRequest() );
				
		return new GetPratilipiListResponse(
				pratilipiListCursorTuple.getDataList(),
				pratilipiListCursorTuple.getCursor() );
	}		

}
