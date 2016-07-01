package com.pratilipi.api.impl.userpratilipi;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.impl.pratilipi.PratilipiListApi;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.client.PratilipiData;
import com.pratilipi.data.util.UserPratilipiDataUtil;
import com.pratilipi.filter.AccessTokenFilter;

@SuppressWarnings("serial")
@Bind( uri = "/userpratilipi/library/list" )
public class UserPratilipiLibraryListApi extends GenericApi {
	
	public static class GetRequest extends GenericRequest {

		private String cursor;
		private Integer resultCount;
		
	}
	
	
	@Get
	public static PratilipiListApi.Response get( GetRequest request ) 
			throws InsufficientAccessException {
		
		DataListCursorTuple<PratilipiData> pratilipiDataListCursorTuple
				= UserPratilipiDataUtil.getUserLibrary(
						AccessTokenFilter.getAccessToken().getUserId(),
						request.cursor,
						null,
						request.resultCount == null ? 20 : request.resultCount );
		
		return new PratilipiListApi.Response(
				pratilipiDataListCursorTuple.getDataList(),
				pratilipiDataListCursorTuple.getCursor(),
				pratilipiDataListCursorTuple.getNumberFound() );
		
	}
	
}
