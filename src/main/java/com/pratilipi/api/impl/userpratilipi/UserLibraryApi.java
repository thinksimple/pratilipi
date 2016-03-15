package com.pratilipi.api.impl.userpratilipi;

import java.util.List;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.impl.pratilipi.shared.GetPratilipiListResponse;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.data.util.PratilipiDataUtil;
import com.pratilipi.data.util.UserPratilipiDataUtil;
import com.pratilipi.filter.AccessTokenFilter;

@SuppressWarnings("serial")
@Bind( uri = "/userpratilipi/list" )
public class UserLibraryApi extends GenericApi {
	
	@Get
	public static GetPratilipiListResponse getUserPratilipiList( GenericRequest request ) 
			throws InsufficientAccessException {
		
		List<Long> pratilipiIdList = UserPratilipiDataUtil.getUserLibraryPratilipiList( AccessTokenFilter.getAccessToken().getUserId() );
		if( pratilipiIdList == null || pratilipiIdList.size() == 0 )
			return null;
		
		return new GetPratilipiListResponse( PratilipiDataUtil.createPratilipiDataList( pratilipiIdList, true ), null );
	}
}
