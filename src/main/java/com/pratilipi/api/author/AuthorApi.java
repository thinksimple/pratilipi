package com.pratilipi.api.author;

import com.google.gson.Gson;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Put;
import com.pratilipi.api.author.shared.GetAuthorRequest;
import com.pratilipi.api.author.shared.GetAuthorResponse;
import com.pratilipi.api.author.shared.PutAuthorRequest;
import com.pratilipi.api.author.shared.PutAuthorResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.client.AuthorData;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.util.AuthorDataUtil;
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings( "serial" )
@Bind( uri = "/author" )
public class AuthorApi extends GenericApi {

	@Get
	public GetAuthorResponse getAuthor( GetAuthorRequest request )
			throws InvalidArgumentException, UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Author author = dataAccessor.getAuthor( request.getAuthorId() );
		AuthorData authorData = AuthorDataUtil.createAuthorData( author );
		
		Gson gson = new Gson();
		return gson.fromJson( gson.toJson( authorData ), GetAuthorResponse.class );
	}
	
	@Put
	public PutAuthorResponse putAuthor( PutAuthorRequest request ) 
			throws InvalidArgumentException, InsufficientAccessException {
		
		Gson gson = new Gson();

		AuthorData authorData = gson.fromJson( gson.toJson( request ), AuthorData.class );
		AuthorDataUtil.saveAuthorData( authorData );
		
		Task task = TaskQueueFactory.newTask()
				.setUrl( "/author/process" )
				.addParam( "authorId", authorData.getId().toString() )
				.addParam( "processData", "true" );
		TaskQueueFactory.getAuthorTaskQueue().add( task );

		
		return new PutAuthorResponse();
	}
	
}
