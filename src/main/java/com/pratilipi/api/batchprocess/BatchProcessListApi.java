package com.pratilipi.api.batchprocess;

import java.util.ArrayList;
import java.util.List;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.type.BatchProcessType;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.client.BatchProcessData;
import com.pratilipi.data.util.BatchProcessDataUtil;


@SuppressWarnings( "serial" )
@Bind( uri = "/batch-process/list" )
public class BatchProcessListApi extends GenericApi {

	public static class GetRequest extends GenericRequest {

		private BatchProcessType type;

		private String cursor;

		private Integer resultCount;


		public void setType( BatchProcessType type ) {
			this.type = type;
		}

		public void setCursor( String cursor ) {
			this.cursor = cursor;
		}

		public void setResultCount( Integer resultCount ) {
			this.resultCount = resultCount;
		}

	}

	public static class Response extends GenericResponse {

		private List<BatchProcessApi.Response> batchProcessList;

		private String cursor;

		
		@SuppressWarnings("unused")
		private Response() {}

		public Response( List<BatchProcessData> batchProcessList, String cursor ) {
			this.batchProcessList = new ArrayList<>( batchProcessList.size() );
			for( BatchProcessData batchProcess : batchProcessList )
				this.batchProcessList.add( new BatchProcessApi.Response( batchProcess ) );
			this.cursor = cursor;
		}


		public List<BatchProcessApi.Response> getBatchProcessList() {
			return batchProcessList;
		}

		public String getCursor() {
			return cursor;
		}

	}
	

	@Get
	public Response get( GetRequest request ) throws InsufficientAccessException {

		DataListCursorTuple<BatchProcessData> batchProcessListCursorTuple = BatchProcessDataUtil.getBatchProcessList(
				request.type,
				request.cursor,
				request.resultCount );
		
		return new Response(
				batchProcessListCursorTuple.getDataList(),
				batchProcessListCursorTuple.getCursor() );

	}

}
