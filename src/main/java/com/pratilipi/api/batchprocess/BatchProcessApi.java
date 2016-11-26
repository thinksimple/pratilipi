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
import com.pratilipi.data.util.BatchProcessDataUtil;


@SuppressWarnings( "serial" )
@Bind( uri = "/batch-process" )
public class BatchProcessApi extends GenericApi {
	
	@Get
	public GenericResponse get( GenericRequest request ) throws UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		List<BatchProcess> batchProcessList = dataAccessor.getIncompleteBatchProcessList();
		for( BatchProcess batchProcess : batchProcessList )
			if( BatchProcessDataUtil.exec( batchProcess.getId() ) )
				break; // Only one execution per iteration as next BatchProcess' state might change by the time it is picked up.

		return new GenericResponse();
		
	}
	
}
