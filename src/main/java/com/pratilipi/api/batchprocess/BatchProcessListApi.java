package com.pratilipi.api.batchprocess;

import java.util.List;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.BatchProcessState;
import com.pratilipi.common.type.BatchProcessType;
import com.pratilipi.common.type.Language;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.BatchProcess;
import com.pratilipi.data.util.BatchProcessDataUtil;


@SuppressWarnings( "serial" )
@Bind( uri = "/batch-process/list" )
public class BatchProcessListApi extends GenericApi {

	public static class PostRequest extends GenericRequest {

		private Language language;

		private String message;

		private String sourceUri;
		
		private BatchProcessType type;

		private BatchProcessState state;

	}

	public static class Response extends GenericResponse { 

		private List<BatchProcess> batchProcessList;

		private Response() {}
		
		public Response( List<BatchProcess> batchProcessList ) {
			this.batchProcessList = batchProcessList;
		}


		public List<BatchProcess> getBatchProcessList() {
			return batchProcessList;
		}

	}

	@Get
	public Response get( GenericRequest request ) throws UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		List<BatchProcess> batchProcessList = dataAccessor.getAllBatchProcessList();
		return new Response( batchProcessList );

	}

	@Post
	public Response get( PostRequest request ) 
			throws InvalidArgumentException {
		
		BatchProcessDataUtil.createBatchProcess( request.language, request.message, request.sourceUri, request.type, request.state );

		return new Response();
	}

}
