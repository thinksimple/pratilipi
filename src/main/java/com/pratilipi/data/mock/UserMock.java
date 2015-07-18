package com.pratilipi.data.mock;

import java.util.LinkedList;
import java.util.List;

import com.pratilipi.data.type.User;
import com.pratilipi.data.type.gae.UserEntity;

public class UserMock {

	public static final List<User> USER_TABLE = new LinkedList<>();

	public static final User user_1 = new UserEntity( 1L );
	public static final User user_2 = new UserEntity( 2L );
	public static final User user_3 = new UserEntity( 3L );

	
	static {
		USER_TABLE.add( user_1 );
		USER_TABLE.add( user_2 );
		USER_TABLE.add( user_3 );

		user_1.setFirstName( "User" );
		user_1.setLastName( "One" );
		user_1.setEmail( "user-one@pratilipi.com" );
		user_1.setPassword( "8EilZIQ04Vuf5sbYrp16FV4Z+3IE36ciZUyOeUc8mQA=$7iAI9oWvfqriubH34iWulvkQlAtc5bsLpIc6LuHYUlQ=" );
		
		user_2.setFirstName( "User" );
		user_2.setLastName( "Two" );
		user_2.setEmail( "user-two@pratilipi.com" );

		user_3.setFirstName( "User" );
		user_3.setLastName( "Three" );
		user_3.setEmail( "user-three@pratilipi.com" );
	}
	
}
