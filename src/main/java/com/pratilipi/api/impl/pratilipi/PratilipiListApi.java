package com.pratilipi.api.impl.pratilipi;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.impl.pratilipi.shared.GetPratilipiListRequest;
import com.pratilipi.api.impl.pratilipi.shared.GetPratilipiListResponse;
import com.pratilipi.api.impl.pratilipi.shared.GenericPratilipiResponse;
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
		pratilipiFilter.setType( request.getType() );
		pratilipiFilter.setLanguage( request.getLanguage() );
		pratilipiFilter.setAuthorId( request.getAuthorId() );
		pratilipiFilter.setState( request.getState() );
		
		DataListCursorTuple<PratilipiData> pratilipiListCursorTuple =
				PratilipiDataUtil.getPratilipiDataList(
						request.getSearchQuery(),
						pratilipiFilter,
						request.includeSummaryAndIndex(),
						request.getCursor(),
						request.getResultCount() == null ? 20 : request.getResultCount() );

		List<GenericPratilipiResponse> pratilipiResponseList =
				new ArrayList<>( pratilipiListCursorTuple.getDataList().size() );
		
		Gson gson = new Gson();
		for( PratilipiData pratilipiData : pratilipiListCursorTuple.getDataList() )
			pratilipiResponseList.add( gson.fromJson( gson.toJson( pratilipiData ), GenericPratilipiResponse.class ) );
		
		return new GetPratilipiListResponse(
				pratilipiResponseList,
				pratilipiListCursorTuple.getCursor() );
		
	}		

}
