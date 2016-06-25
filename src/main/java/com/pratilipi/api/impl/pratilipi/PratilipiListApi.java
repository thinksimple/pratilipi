package com.pratilipi.api.impl.pratilipi;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.impl.pratilipi.shared.GetPratilipiListRequest;
import com.pratilipi.api.impl.pratilipi.shared.GetPratilipiListResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.util.PratilipiFilter;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.client.PratilipiData;
import com.pratilipi.data.util.PratilipiDataUtil;

@SuppressWarnings("serial")
@Bind( uri = "/pratilipi/list" )
public class PratilipiListApi extends GenericApi {

	@Get
	public GetPratilipiListResponse getPratilipiList( GetPratilipiListRequest request )
			throws InsufficientAccessException {

		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		pratilipiFilter.setAuthorId( request.getAuthorId() );
		pratilipiFilter.setLanguage( request.getLanguage() );
		pratilipiFilter.setType( request.getType() );
		pratilipiFilter.setListName( request.getListName() );
		pratilipiFilter.setState( request.getState() );
		pratilipiFilter.setOrderByLastUpdate( request.getOrderByLastUpdate() );
		
		DataListCursorTuple<PratilipiData> pratilipiListCursorTuple =
				PratilipiDataUtil.getPratilipiDataList(
						request.getSearchQuery(),
						pratilipiFilter,
						request.getCursor(),
						request.getOffset(),
						request.getResultCount() == null ? 20 : request.getResultCount() );

		// Preparing & returning response object.
		return new GetPratilipiListResponse(
				pratilipiListCursorTuple.getDataList(),
				pratilipiListCursorTuple.getCursor(),
				pratilipiListCursorTuple.getNumberFound() );
		
	}

}
