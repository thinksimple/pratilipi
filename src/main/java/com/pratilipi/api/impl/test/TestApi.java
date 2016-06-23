package com.pratilipi.api.impl.test;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.googlecode.objectify.ObjectifyService;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.type.UserState;
import com.pratilipi.data.DataAccessorGaeImpl;
import com.pratilipi.data.type.User;
import com.pratilipi.data.type.gae.UserEntity;

@SuppressWarnings("serial")
@Bind( uri = "/test" )
public class TestApi extends GenericApi {
	
	private static final Logger logger = Logger.getLogger( TestApi.class.getName() );
	
	@Get
	public GenericResponse get( GenericResponse request ) throws InsufficientAccessException {
		
		List<UserEntity> userList = ObjectifyService.ofy().load()
				.type( UserEntity.class )
				.filter( "EMAIL", "saurabh2395@rocketmail.com" )
				.filter( "STATE !=", UserState.DELETED )
				.order( "STATE" )
				.order( "SIGN_UP_DATE" )
				.list();
		
		logger.log( Level.INFO, userList.size() + " users found.");
		for( User user : userList )
			logger.log( Level.INFO, user.getId() + "");

		
		userList = ObjectifyService.ofy().load()
				.type( UserEntity.class )
				.filter( "EMAIL", "saurabh2395@rocketmail.com" )
				.filter( "STATE", UserState.DELETED )
				.order( "STATE" )
				.order( "SIGN_UP_DATE" )
				.list();

		logger.log( Level.INFO, userList.size() + " users found.");
		for( User user : userList )
			logger.log( Level.INFO, user.getId() + "");

		
		userList = ObjectifyService.ofy().load()
				.type( UserEntity.class )
				.filter( "EMAIL", "saurabh2395@rocketmail.com" )
				.list();

		logger.log( Level.INFO, userList.size() + " users found.");
		for( User user : userList )
			logger.log( Level.INFO, user.getId() + "");

		
		return new GenericResponse();
		
	}
	
}
