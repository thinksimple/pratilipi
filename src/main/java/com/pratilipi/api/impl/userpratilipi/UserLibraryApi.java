package com.pratilipi.api.impl.userpratilipi;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
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
	
	private static final Logger logger =
			Logger.getLogger( UserLibraryApi.class.getName() );
	
	@Get
	public GetPratilipiListResponse getUserPratilipiList( GenericRequest request ) 
			throws InsufficientAccessException {
		
		logger.log( Level.INFO, "Generating User Library..." );
		Long userId = AccessTokenFilter.getAccessToken().getUserId();
		logger.log( Level.INFO, "userId = " + userId );
		if( userId.equals( 0L ) )
			return null;
		
		List<Long> pratilipiIdList = UserPratilipiDataUtil.getUserLibraryPratilipiList( userId );
		logger.log( Level.INFO, "pratilipiIdList = " + new Gson().toJson( pratilipiIdList ) ) ;
		List<PratilipiData> pratilipiList = PratilipiDataUtil.createPratilipiDataList( pratilipiIdList, true );
		logger.log( Level.INFO, "pratilipiList = " + new Gson().toJson( pratilipiList ) ) ;
		return new GetPratilipiListResponse( pratilipiList, null );
	}
}
