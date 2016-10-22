package com.pratilipi.api.impl.init;

import java.util.LinkedList;
import java.util.List;

import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.util.PratilipiFilter;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.client.PratilipiData;
import com.pratilipi.data.util.PratilipiDataUtil;

@SuppressWarnings("serial")
@Bind( uri = "/init", ver = "2" )
public class InitV2Api extends InitV1Api {
	
	@Get
	public Response get( GetRequest request ) throws UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		List<Response.Section> sectionList = new LinkedList<>();
		List<Long> pratilipiIdMasterList = new LinkedList<>();
		
		for( String listName : dataAccessor.getHomeSectionList( request.language ) ) {
			
			String title = dataAccessor.getPratilipiListTitle( listName, request.language );
			if( title == null )
				continue;
			
			if( title.indexOf( '|' ) != -1 )
				title = title.substring( 0, title.indexOf( '|' ) ).trim();
			
			PratilipiFilter pratilipiFilter = new PratilipiFilter();
			pratilipiFilter.setLanguage( request.language );
			pratilipiFilter.setListName( listName );
			pratilipiFilter.setState( PratilipiState.PUBLISHED );
			
			List<Long> pratilipiIdList = dataAccessor.getPratilipiIdList( pratilipiFilter, null, null, 6 ).getDataList();
			if( pratilipiIdList.size() < 6 )
				continue;
			
			sectionList.add( new Response.Section( title, "/" + listName ) );
			pratilipiIdMasterList.addAll( pratilipiIdList );

		}
		
		List<PratilipiData> pratilipiDataMasterList = PratilipiDataUtil.createPratilipiDataList( pratilipiIdMasterList, true );
		for( int i = 0; i < sectionList.size(); i++ )
			sectionList.get( i ).setPratilipiList( pratilipiDataMasterList.subList( i * 6, i * 6 + 6 ) );
		
		return new Response( sectionList );
		
	}
	
}
