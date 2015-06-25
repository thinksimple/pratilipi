package com.pratilipi.api.pratilipi;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.pratilipi.shared.GetPratilipiListRequest;
import com.pratilipi.api.pratilipi.shared.GetPratilipiListResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.util.PratilipiFilter;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.client.PratilipiData;
import com.pratilipi.data.util.PratilipiDataUtil;

@SuppressWarnings("serial")
@Bind( uri = "/pratilipi/list" )
public class PratilipiListApi extends GenericApi {

	@Get
	public GetPratilipiListResponse getPratilipiList( GetPratilipiListRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		pratilipiFilter.setType( request.getType() );
		pratilipiFilter.setLanguage( request.getLanguage() );
		pratilipiFilter.setAuthorId( request.getAuthorId() );
		pratilipiFilter.setState( request.getState() );
		
		DataListCursorTuple<PratilipiData> pratilipiListCursorTuple =
				PratilipiDataUtil.getPratilipiList(
						pratilipiFilter,
						request.getCursor(),
						request.getResultCount() == null ? 20 : request.getResultCount() );
				
		return new GetPratilipiListResponse(
				pratilipiListCursorTuple.getDataList(),
				pratilipiListCursorTuple.getCursor() );
	}		

}
