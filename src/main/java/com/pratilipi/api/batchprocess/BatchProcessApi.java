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
@Bind( uri = "/batch-process" )
public class BatchProcessApi extends GenericApi {
	
	public static class PostRequest extends GenericRequest {

		private Language language;

		private String message;

		private String sourceUri;
		
		private BatchProcessType type;

		private BatchProcessState state;

	}
	
	@Get
	public GenericResponse get( GenericRequest request ) throws UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		List<BatchProcess> batchProcessList = dataAccessor.getIncompleteBatchProcessList();
		for( BatchProcess batchProcess : batchProcessList )
			if( BatchProcessDataUtil.exec( batchProcess.getId() ) )
				break; // Only one execution per iteration as next BatchProcess' state might change by the time it is picked up.

		return new GenericResponse();
		
	}

	@Post
	public GenericResponse get( PostRequest request ) 
			throws InvalidArgumentException {
		
		BatchProcessDataUtil.createBatchProcess( request.language, request.message, request.sourceUri, request.type, request.state );

		return new GenericResponse();
	}
	
}
