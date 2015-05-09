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
		if( request.getLanguageId() == 5130467284090880L ){
			topReadPratilipiIdList.add( 4821869924450304L );
			topReadPratilipiIdList.add( 4843865324388352L );
			topReadPratilipiIdList.add( 4629135213199360L );
			topReadPratilipiIdList.add( 4538710716579840L );
			topReadPratilipiIdList.add( 4571168321306624L );
		} else if( request.getLanguageId() == 5965057007550464L ){
			topReadPratilipiIdList.add( 4818859890573312L );
			topReadPratilipiIdList.add( 4834680419385344L );
			topReadPratilipiIdList.add( 4849784863064064L );
			topReadPratilipiIdList.add( 4869201873338368L );
			topReadPratilipiIdList.add( 4901223018790912L );
		} else if( request.getLanguageId() == 6319546696728576L ){
			topReadPratilipiIdList.add( 4664696015683584L );
			topReadPratilipiIdList.add( 4801061093113856L );
			topReadPratilipiIdList.add( 4685596450619392L );
			topReadPratilipiIdList.add( 4803319927144448L );
			topReadPratilipiIdList.add( 4837289477799936L );
		}
		
		List<PratilipiData> topReadPratilipiDataList = 
					PratilipiContentHelper.createPratilipiDataList( 
								topReadPratilipiIdList,
								true,
								true,
								this.getThreadLocalRequest() );
		
		
		
		return new GetMobileInitResponse( topReadPratilipiDataList );
	}
}
