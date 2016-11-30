package com.pratilipi.api.batchprocess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.AuthorState;
import com.pratilipi.common.type.BatchProcessType;
import com.pratilipi.common.type.Language;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.BatchProcess;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.util.BatchProcessDataUtil;


@SuppressWarnings( "serial" )
@Bind( uri = "/batch-process" )
public class BatchProcessApi extends GenericApi {
	
	public static class PostRequest extends GenericRequest {

		private Language language;

		private String message;

		private String sourceUri;
		
		private BatchProcessType type;

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
			throws InsufficientAccessException, InvalidArgumentException  {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		Page page = dataAccessor.getPage( request.sourceUri );

		if( page == null ) {
			JsonObject errorMessages = new JsonObject();
			errorMessages.addProperty( "uri", "Invalid uri !" );
			throw new InvalidArgumentException( errorMessages );
		}

		Gson gson = new Gson();

		Map<String, Object> authorFilter = new HashMap<String, Object>();
		authorFilter.put( "language", request.language );
		authorFilter.put( "state", AuthorState.ACTIVE );

		Map<String, Object> initDoc = new HashMap<String, Object>();
		initDoc.put( "authorFilter", authorFilter );

		Map<String, Object> execDoc = new HashMap<String, Object>();
		execDoc.put( "message", request.message );
		execDoc.put( "sourceId", page.getPrimaryContentId() );
		execDoc.put( "type", page.getType() );

		BatchProcessDataUtil.createBatchProcess( gson.toJson( initDoc ),
											gson.toJson( execDoc ),
											request.type, 
											request.language );

		return new GenericResponse();

	}

}
