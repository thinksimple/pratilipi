package com.pratilipi.api.impl.userpratilipi;

import java.util.List;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.impl.pratilipi.shared.GetPratilipiListResponse;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.data.client.PratilipiData;
import com.pratilipi.data.util.PratilipiDataUtil;
import com.pratilipi.data.util.UserPratilipiDataUtil;
import com.pratilipi.filter.AccessTokenFilter;

@SuppressWarnings("serial")
@Bind( uri = "/userpratilipi/list" )
public class UserLibraryApi extends GenericApi {
	
	@Get
	public static GetPratilipiListResponse getUserPratilipiList( GenericRequest request ) 
			throws InsufficientAccessException {
		
		Long userId = AccessTokenFilter.getAccessToken().getUserId();
		if( userId.equals( 0L ) )
			return new GetPratilipiListResponse( null, null );
		
		List<Long> pratilipiIdList = UserPratilipiDataUtil.getUserLibraryPratilipiList( userId );
		if( pratilipiIdList == null || pratilipiIdList.size() == 0 )
			return new GetPratilipiListResponse( null, null );
		List<PratilipiData> pratilipiList = PratilipiDataUtil.createPratilipiDataList( pratilipiIdList, true );
		return new GetPratilipiListResponse( pratilipiList, null );
	}
}
