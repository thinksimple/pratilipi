package com.pratilipi.api.impl.userpratilipi;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.impl.pratilipi.shared.GetPratilipiListResponse;
import com.pratilipi.api.impl.userpratilipi.shared.GetUserPratilipiLibraryListRequest;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.client.PratilipiData;
import com.pratilipi.data.util.UserPratilipiDataUtil;
import com.pratilipi.filter.AccessTokenFilter;

@SuppressWarnings("serial")
@Bind( uri = "/userpratilipi/library/list" )
public class UserPratilipiLibraryListApi extends GenericApi {
	
	@Get
	public static GetPratilipiListResponse get( GetUserPratilipiLibraryListRequest request ) 
			throws InsufficientAccessException {
		
		DataListCursorTuple<PratilipiData> pratilipiDataListCursorTuple
				= UserPratilipiDataUtil.getUserPratilipiLibrary(
						AccessTokenFilter.getAccessToken().getUserId(),
						request.getCursor(),
						null,
						request.getResultCount() == null ? 20 : request.getResultCount() );
		
		return new GetPratilipiListResponse(
				pratilipiDataListCursorTuple.getDataList(),
				pratilipiDataListCursorTuple.getCursor() );
		
	}
	
}
