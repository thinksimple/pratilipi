package com.pratilipi.api;

import java.util.ArrayList;
import java.util.List;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Get;
import com.pratilipi.api.shared.GetMobileInitRequest;
import com.pratilipi.api.shared.GetMobileInitResponse;
import com.pratilipi.data.transfer.shared.PratilipiData;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;

@SuppressWarnings( "serial" )
@Bind( uri= "/mobileinit" )
public class MobileInitApi extends GenericApi {

	@Get
	public GetMobileInitResponse getMobileInit( GetMobileInitRequest request ){
		
		List<Long> topReadPratilipiIdList = new ArrayList<Long>();
		topReadPratilipiIdList.add( 4843865324388352L );
		topReadPratilipiIdList.add( 5639221461123072L );
		topReadPratilipiIdList.add( 5382523345436672L );
		topReadPratilipiIdList.add( 5157903266742272L );
		topReadPratilipiIdList.add( 4872221772218368L );
		topReadPratilipiIdList.add( 6233002837278720L );
		
		List<PratilipiData> topReadPratilipiDataList = 
					PratilipiContentHelper.createPratilipiDataList( 
								topReadPratilipiIdList,
								true,
								true,
								this.getThreadLocalRequest() );
		
		
		
		return new GetMobileInitResponse( topReadPratilipiDataList );
	}
}
