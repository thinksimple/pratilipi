package com.pratilipi.api.impl.userpratilipi;

import java.util.ArrayList;
import java.util.List;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.impl.userpratilipi.shared.GenericReviewResponse;
import com.pratilipi.api.impl.userpratilipi.shared.GetUserPratilipiReviewListRequest;
import com.pratilipi.api.impl.userpratilipi.shared.GetUserPratilipiReviewListResponse;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.client.UserPratilipiData;
import com.pratilipi.data.util.UserPratilipiDataUtil;

@SuppressWarnings("serial")
@Bind( uri = "/userpratilipi/review/list" )
public class UserPratilipiReviewListApi extends GenericApi {

	@Get
	public GetUserPratilipiReviewListResponse get( GetUserPratilipiReviewListRequest request ) {
		
		DataListCursorTuple<UserPratilipiData> userPratilipiListCursorTuple =
				UserPratilipiDataUtil.getPratilipiReviewList(
						request.getPratilipiId(),
						request.getCursor(),
						request.getResultCount() == null ? 20 : request.getResultCount() );
		
		List<GenericReviewResponse> reviewList = new ArrayList<>( userPratilipiListCursorTuple.getDataList().size() );
		for( UserPratilipiData userPratilipiData : userPratilipiListCursorTuple.getDataList() )
			reviewList.add( new GenericReviewResponse( userPratilipiData ) );
		
		return new GetUserPratilipiReviewListResponse(
				reviewList,
				userPratilipiListCursorTuple.getCursor() );
	
	}

}
