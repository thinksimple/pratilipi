package com.pratilipi.api.impl.pratilipi;

import com.google.gson.Gson;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.impl.pratilipi.shared.GenericPratilipiResponse;
import com.pratilipi.api.impl.pratilipi.shared.GetPratilipiRequest;
import com.pratilipi.api.impl.pratilipi.shared.PostPratilipiRequest;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.client.PratilipiData;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.util.PratilipiDataUtil;
import com.pratilipi.filter.AccessTokenFilter;
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
@Bind( uri = "/pratilipi" )
public class PratilipiApi extends GenericApi {
	
	@Get
	public GenericPratilipiResponse getPratilipi( GetPratilipiRequest request ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Pratilipi pratilipi = dataAccessor.getPratilipi( request.getPratilipiId() );
		Author author = pratilipi.getAuthorId() == null
				? null
				: dataAccessor.getAuthor( pratilipi.getAuthorId() );
		
		PratilipiData pratilipiData = PratilipiDataUtil.createPratilipiData( pratilipi, author );

		Gson gson = new Gson();

		return gson.fromJson( gson.toJson( pratilipiData ), GenericPratilipiResponse.class );
		
	}

	@Post
	public GenericPratilipiResponse postPratilipi( PostPratilipiRequest request )
			throws InvalidArgumentException, InsufficientAccessException {

		Gson gson = new Gson();

		PratilipiData pratilipiData = gson.fromJson( gson.toJson( request ), PratilipiData.class );
		if( pratilipiData.getId() == null && ! pratilipiData.hasAuthorId() ) {
			DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
			Author author = dataAccessor.getAuthorByUserId( AccessTokenFilter.getAccessToken().getUserId() );
			pratilipiData.setAuthorId( author.getId() );
		}
		pratilipiData = PratilipiDataUtil.savePratilipiData( pratilipiData );
		
		Task task = TaskQueueFactory.newTask()
				.setUrl( "/pratilipi/process" )
				.addParam( "pratilipiId", pratilipiData.getId().toString() )
				.addParam( "processData", "true" );
		TaskQueueFactory.getPratilipiTaskQueue().add( task );
		
		return gson.fromJson( gson.toJson( pratilipiData ), GenericPratilipiResponse.class );
		
	}		

}
