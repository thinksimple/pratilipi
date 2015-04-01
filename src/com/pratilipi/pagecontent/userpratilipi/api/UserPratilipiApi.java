package com.pratilipi.pagecontent.userpratilipi.api;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Put;
import com.claymus.data.transfer.AccessToken;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.pagecontent.userpratilipi.api.shared.PutUserPratilipiRequest;
import com.pratilipi.pagecontent.userpratilipi.api.shared.PutUserPratilipiResponse;
import com.pratilipi.service.shared.data.UserPratilipiData;

@SuppressWarnings( "serial" )
@Bind( uri = "/userpratilipi" )
public class UserPratilipiApi extends GenericApi {
	
	@Put
	public PutUserPratilipiResponse addUserPratilipi( PutUserPratilipiRequest request ){
		UserPratilipiData userPratilipiData = request.getUserPratilipiData();
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		AccessToken accessToken = dataAccessor.getAccessToken( "" );
		return null;
	}
}
