package com.pratilipi.api.impl.pratilipi;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.impl.pratilipi.shared.PostPratilipiStatsRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.data.util.PratilipiDataUtil;

@SuppressWarnings("serial")
@Bind( uri = "/pratilipi/stats" )
public class PratilipiStatsApi extends GenericApi {
	
	@Post
	public GenericResponse post( PostPratilipiStatsRequest request ) {

		PratilipiDataUtil.updatePratilipiStats( request.getPratilipiId(),
				request.getReadCountOffset(), request.getReadCount(),
				request.getFbLikeShareCountOffset(), request.getFbLikeShareCount() );
		
		return new GenericResponse();
	
	}
	
}
