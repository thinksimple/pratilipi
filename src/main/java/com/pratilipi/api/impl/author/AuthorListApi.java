package com.pratilipi.api.impl.author;

import java.util.ArrayList;
import java.util.List;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.util.AuthorFilter;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.client.AuthorData;
import com.pratilipi.data.util.AuthorDataUtil;

@SuppressWarnings("serial")
@Bind( uri = "/author/list" )
public class AuthorListApi extends GenericApi {

	public static class GetRequest extends GenericRequest {

		private String searchQuery;
		private Language language;

		private Boolean orderByContentPublished;

		private String cursor;
		private Integer resultCount;
		
		
		public String getSearchQuery() {
			return searchQuery;
		}

		public Language getLanguage() {
			return language;
		}
		
		public Boolean getOrderByContentPublished() {
			return orderByContentPublished;
		}

		public String getCursor() {
			return cursor;
		}
		
		public Integer getResultCount() {
			return resultCount;
		}
		
	}

	@SuppressWarnings("unused")
	public class Response extends GenericResponse {

		private List<AuthorApi.Response> authorList;
		private String cursor;

		
		private Response() {}
		
		private Response( List<AuthorData> authorList, String cursor ) {
			this.authorList = new ArrayList<>( authorList.size() );
			for( AuthorData authorData : authorList )
				this.authorList.add( new AuthorApi.Response( authorData, AuthorListApi.class ) );
			this.cursor = cursor;
		}
		
	}
	
	
	@Get
	public Response get( GetRequest request )
			throws InsufficientAccessException {
		
		AuthorFilter authorFilter = new AuthorFilter();
		authorFilter.setLanguage( request.getLanguage() );
		authorFilter.setOrderByContentPublished( request.getOrderByContentPublished() );

		DataListCursorTuple<AuthorData> authorListCursorTuple =
				AuthorDataUtil.getAuthorDataList(
						request.getSearchQuery(),
						authorFilter,
						request.getCursor(),
						request.getResultCount() == null ? 20 : request.getResultCount() );
		
		return new Response(
				authorListCursorTuple.getDataList(),
				authorListCursorTuple.getCursor() );
		
	}

}
