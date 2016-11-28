package com.pratilipi.api.batchprocess;

import java.util.List;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.BatchProcess;


@SuppressWarnings( "serial" )
@Bind( uri = "/batch-process/list" )
public class BatchProcessListApi extends GenericApi {
	
	@SuppressWarnings("unused")
	public static class Response extends GenericResponse { 

		private List<BatchProcess> batchProcessList;

		private Response() {}
		
		public Response( List<BatchProcess> batchProcessList ) {
			this.batchProcessList = batchProcessList;
		}
	}

	@Get
	public Response get( GenericRequest request ) throws UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		List<BatchProcess> batchProcessList = dataAccessor.getAllBatchProcessList();
		return new Response( batchProcessList );

	}

}
