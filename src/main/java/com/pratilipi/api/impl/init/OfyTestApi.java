package com.pratilipi.api.impl.init;

import java.util.List;
import java.util.logging.Logger;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.impl.init.shared.GetInitApiRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.util.PratilipiFilter;
import com.pratilipi.data.client.PratilipiData;
import com.pratilipi.data.util.PratilipiDataUtil;

@SuppressWarnings("serial")
@Bind( uri = "/ofy" )
public class OfyTestApi extends GenericApi {
	
	private static final Logger logger =
			Logger.getLogger( OfyTestApi.class.getName() );

	@Get
	public GenericResponse get( GetInitApiRequest request ) throws InvalidArgumentException, InsufficientAccessException {

		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		pratilipiFilter.setAuthorId( 4809728372768768L );
		
		List<PratilipiData> pratilipiDataList = PratilipiDataUtil.getPratilipiDataList( null, pratilipiFilter, null, null, null ).getDataList();
		if( pratilipiDataList.size() == 4 ) {
			for( PratilipiData pratilipiData : pratilipiDataList ) {
				pratilipiData.setAuthorId( 5734588399747072L );
				PratilipiDataUtil.savePratilipiData( pratilipiData );
			}
		}
	
		return new GenericResponse();
		
	}
	
}