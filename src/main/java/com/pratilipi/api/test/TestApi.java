package com.pratilipi.api.test;

import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.api.test.shared.PostTestRequest;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.AppProperty;
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings( "serial" )
@Bind( uri = "/test/frontend" )
public class TestApi extends GenericApi {
	
	private Logger logger = Logger.getLogger( TestApi.class.getName() );
	
	@Get
	public GenericResponse get( GenericRequest request ) throws InterruptedException {
		Logger.getLogger( TestApi.class.getName() ).log( Level.INFO, "Get method called" );
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		AppProperty appProperty = dataAccessor.getAppProperty( "DataStore.update.test" );
		if( appProperty == null ) {
			appProperty = dataAccessor.newAppProperty( "DataStore.update.test" );
			
		}
		Map<String, String> map = appProperty.getValue();
		if( map.containsKey( "value" )) {
			int value = Integer.valueOf( map.get( "value" ) );
			map.put( "value", String.valueOf( value+1 ) );
		} else {
			map.put( "value", String.valueOf( 0 ));
		}
		logger.log( Level.INFO, "Value for front end task : " + map.get( "value" ));
		appProperty.setValue( map );
		appProperty = dataAccessor.createOrUpdateAppProperty( appProperty );
		
		Random random = new Random();
		int randomNumber = random.nextInt( 5 );
		
		Thread.sleep( randomNumber * 1000 );
		
		Task task = TaskQueueFactory.newTask()
				.setUrl( "/test/backend" )
				.addParam( "value", map.get( "value" ) );
		TaskQueueFactory.getTestTaskQueue().add( task );
		
		return new GenericResponse();
	}
	
	@Post
	public GenericResponse post( PostTestRequest request ) {
		
		Logger.getLogger( TestApi.class.getName() ).log( Level.INFO, "Post method called" );
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		AppProperty appProperty = dataAccessor.getAppProperty( "DataStore.update.test" );
		Map<String, String> map = appProperty.getValue();
		if( request.getValue().equals( map.get( "value" )))
			logger.log( Level.INFO, "Latest Value found" );
		else {
			logger.log( Level.SEVERE, "Old Value found" );
			logger.log( Level.SEVERE, "Latest Value : " + request.getValue() );
			logger.log( Level.SEVERE, "Data Store Value : " + map.get( "value" ) );
		}
		
		return new GenericResponse();
	}

}