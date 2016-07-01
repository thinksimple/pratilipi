package com.pratilipi.api.impl.pratilipi;

import java.util.ArrayList;
import java.util.List;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.type.PratilipiType;
import com.pratilipi.common.util.PratilipiFilter;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.client.PratilipiData;
import com.pratilipi.data.util.PratilipiDataUtil;

@SuppressWarnings("serial")
@Bind( uri = "/pratilipi/list" )
public class PratilipiListApi extends GenericApi {

	public static class GetRequest extends GenericRequest {

		private String searchQuery;
		private Long authorId;
		private Language language;
		private PratilipiType type;
		private String listName;
		private PratilipiState state;

		private Boolean orderByLastUpdated;

		private String cursor;
		private Integer offset;
		private Integer resultCount;


		public void setSearchQuery( String searchQuery ) {
			this.searchQuery = searchQuery;
		}

		public void setAuthorId( Long authorId ) {
			this.authorId = authorId;
		}

		public void setLanguage( Language language ) {
			this.language = language;
		}

		public void setType( PratilipiType type ) {
			this.type = type;
		}

		public void setListName( String listName ) {
			this.listName = listName;
		}

		public void setState( PratilipiState state ) {
			this.state = state;
		}


		public void setOrderByLastUpdate( Boolean orderByLastUpdated ) {
			this.orderByLastUpdated = orderByLastUpdated;
		}


		public void setCursor( String cursor ) {
			this.cursor = cursor;
		}

		public void setOffset( Integer offset ) {
			this.offset = offset;
		}

		public void setResultCount( Integer resultCount ) {
			this.resultCount = resultCount;
		}

	}
	
	public static class Response extends GenericResponse { 

		private List<PratilipiApi.Response> pratilipiList;
		private String cursor;
		private Long numberFound;

		
		@SuppressWarnings("unused")
		private Response() {}
		
		public Response( List<PratilipiData> pratilipiList, String cursor, Long numberFound ) {
			this.pratilipiList = new ArrayList<>( pratilipiList.size() ); 
			for( PratilipiData pratilipi : pratilipiList )
				this.pratilipiList.add( new PratilipiApi.Response( pratilipi, true ) );
			this.cursor = cursor;
			this.numberFound = numberFound;
		}


		public List<PratilipiApi.Response> getPratilipiList() {
			return pratilipiList;
		}

		public String getCursor() {
			return cursor;
		}

		public Long getNumberFound() {
			return numberFound;
		}

	}

	
	@Get
	public Response get( GetRequest request )
			throws InsufficientAccessException {

		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		pratilipiFilter.setAuthorId( request.authorId );
		pratilipiFilter.setLanguage( request.language );
		pratilipiFilter.setType( request.type );
		pratilipiFilter.setListName( request.listName );
		pratilipiFilter.setState( request.state );
		pratilipiFilter.setOrderByLastUpdate( request.orderByLastUpdated );
		
		DataListCursorTuple<PratilipiData> pratilipiListCursorTuple =
				PratilipiDataUtil.getPratilipiDataList(
						request.searchQuery,
						pratilipiFilter,
						request.cursor,
						request.offset,
						request.resultCount == null ? 20 : request.resultCount );

		// Preparing & returning response object.
		return new Response(
				pratilipiListCursorTuple.getDataList(),
				pratilipiListCursorTuple.getCursor(),
				pratilipiListCursorTuple.getNumberFound() );
		
	}

}
