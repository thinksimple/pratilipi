package com.pratilipi.api.userpratilipi;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.userpratilipi.shared.GetPratilipiReviewListRequest;
import com.pratilipi.api.userpratilipi.shared.GetPratilipiReviewResponse;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.client.UserPratilipiData;
import com.pratilipi.data.util.UserPratilipiDataUtil;

@SuppressWarnings("serial")
@Bind( uri = "/pratilipi/review/list" )
public class PratilipiReviewListApi extends GenericApi {

	@Get
	public GetPratilipiReviewResponse getPratilipiReviewList( GetPratilipiReviewListRequest request ) {
		DataListCursorTuple<UserPratilipiData> userPratilipiListCursorTuple =
				UserPratilipiDataUtil.getPratilipiReviewList(
						request.getPratilipiId(),
						request.getCursor(),
						request.getResultCount() == null ? 20 : request.getResultCount() );
				
		return new GetPratilipiReviewResponse(
				userPratilipiListCursorTuple.getDataList(),
				userPratilipiListCursorTuple.getCursor() );
	}		

}
