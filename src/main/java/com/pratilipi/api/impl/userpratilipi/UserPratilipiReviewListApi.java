package com.pratilipi.api.impl.userpratilipi;

import java.util.ArrayList;
import java.util.List;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.impl.userpratilipi.shared.GenericReviewResponse;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.client.UserPratilipiData;
import com.pratilipi.data.util.UserPratilipiDataUtil;

@SuppressWarnings("serial")
@Bind( uri = "/userpratilipi/review/list" )
public class UserPratilipiReviewListApi extends GenericApi {

	public static class GetRequest extends GenericRequest {

		@Validate( required = true )
		private Long pratilipiId;
		
		private String cursor;
		
		private Integer resultCount;

	}
	
	@SuppressWarnings("unused")
	public static class Response extends GenericResponse {
		
		private List<GenericReviewResponse> reviewList;
		private String cursor;

		
		Response() {}
		
		Response( List<UserPratilipiData> reviewList, String cursor ) {
			this.reviewList = new ArrayList<>( reviewList.size() );
			for( UserPratilipiData review : reviewList )
				this.reviewList.add( new GenericReviewResponse( review ) );
			this.cursor = cursor;
		}
		
	}

	
	@Get
	public Response get( GetRequest request ) throws UnexpectedServerException {
		
		DataListCursorTuple<UserPratilipiData> userPratilipiListCursorTuple =
				UserPratilipiDataUtil.getPratilipiReviewList(
						request.pratilipiId,
						request.cursor,
						request.resultCount == null ? 20 : request.resultCount );
		
		return new Response(
				userPratilipiListCursorTuple.getDataList(),
				userPratilipiListCursorTuple.getCursor() );
	
	}

}
