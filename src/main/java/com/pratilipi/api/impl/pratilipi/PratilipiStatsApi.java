package com.pratilipi.api.impl.pratilipi;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.impl.pratilipi.shared.PostPratilipiStatsRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.data.util.PratilipiDataUtil;
import com.pratilipi.filter.AccessTokenFilter;

@SuppressWarnings("serial")
@Bind( uri = "/pratilipi/stats" )
public class PratilipiStatsApi extends GenericApi {
	
	@Post
	public GenericResponse post( PostPratilipiStatsRequest request ) throws InsufficientAccessException {

		if( AccessTokenFilter.getAccessToken().getUserId() != 5451511011213312L )
			throw new InsufficientAccessException();
		
		PratilipiDataUtil.updatePratilipiStats( request.getPratilipiId(),
				request.getReadCountOffset(), request.getReadCount(),
				request.getFbLikeShareCountOffset(), request.getFbLikeShareCount() );
		
		return new GenericResponse();
	
	}
	
}
