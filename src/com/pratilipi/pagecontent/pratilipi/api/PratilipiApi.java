package com.pratilipi.pagecontent.pratilipi.api;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Put;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;
import com.pratilipi.pagecontent.pratilipi.api.shared.PutPratilipiRequest;
import com.pratilipi.pagecontent.pratilipi.api.shared.PutPratilipiResponse;
import com.pratilipi.service.shared.data.PratilipiData;

@SuppressWarnings("serial")
@Bind( uri = "/pratilipi" )
public class PratilipiApi extends GenericApi {

	@Put
	public PutPratilipiResponse putPratilipi( PutPratilipiRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		PratilipiData pratilipiData = new PratilipiData();
		pratilipiData.setId( request.getPratilipiId() );
		if( request.hasSummary() )
			pratilipiData.setSummary( request.getSummary() );
		if( request.hasIndex() )
			pratilipiData.setIndex( request.getIndex() );
		
		pratilipiData = PratilipiContentHelper.savePratilipi(
				pratilipiData,
				this.getThreadLocalRequest() );
			
		return new PutPratilipiResponse();
	}		

}
