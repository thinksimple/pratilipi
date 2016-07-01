package com.pratilipi.api.impl.init;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.impl.pratilipi.PratilipiApi;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.util.PratilipiFilter;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.client.PratilipiData;
import com.pratilipi.data.util.PratilipiDataUtil;

@SuppressWarnings("serial")
@Bind( uri = "/init" )
public class InitApi extends GenericApi {
	
	public static class GetRequest extends GenericRequest {
		
		@Validate( required = true )
		private Language language;
		
	}
	
	public static class Response extends GenericResponse {
		
		@SuppressWarnings("unused")
		public static class Section {
		
			private String title;
			private String listPageUrl;
			private List<PratilipiApi.Response> pratilipiList;
			
			private Section() {}
		
			private Section( String title, String listPageUrl, List<PratilipiData> pratilipiList ) {
				this.title = title;
				this.listPageUrl = listPageUrl;
				this.pratilipiList = new ArrayList<PratilipiApi.Response>( pratilipiList.size() );
				for( PratilipiData pratilipiData : pratilipiList )
					this.pratilipiList.add( new PratilipiApi.Response( pratilipiData, true ) );
			}
		
		}
		
		private List<Section> sections = new LinkedList<>();
	
		Response() {};
		
		void addSection( String title, String listPageUrl, List<PratilipiData> pratilipiList ) {
			sections.add( new Section( title, listPageUrl, pratilipiList ) );
		}
		
	}
	
	
	@Get
	public GenericResponse get( GetRequest request ) throws InsufficientAccessException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		Response response = new Response();
		
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
			
			DataListCursorTuple<PratilipiData> pratilipiDataListCursorTuple =
					PratilipiDataUtil.getPratilipiDataList( null, pratilipiFilter, null, null, 6 );

			if( pratilipiDataListCursorTuple.getDataList().size() == 0 )
				continue;
			
			response.addSection( title, "/" + listName, pratilipiDataListCursorTuple.getDataList() );
			
		}
		
		return response;
		
	}
	
}
