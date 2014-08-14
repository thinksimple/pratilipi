package com.claymus;

import javax.servlet.http.HttpSession;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.apphosting.api.ApiProxy;

public class ClaymusHelper {

	private static final UserService userService = UserServiceFactory.getUserService();

	public static User getCurrentUser() {
		if( userService.isUserLoggedIn() )
			return userService.getCurrentUser();
		
		return null;
	}
	
	public static boolean isUserAdmin() {
		return userService.isUserLoggedIn() && userService.isUserAdmin();
	}
	
	public static String createLoginURL() {
		return userService.createLoginURL( "/" );
	}

	public static String createLoginURL( String destinationURL ) {
		return userService.createLoginURL( destinationURL );
	}

	public static String createLogoutURL() {
		return userService.createLogoutURL( "/" );
	}

	public static String createLogoutURL( String destinationURL ) {
		return userService.createLogoutURL( destinationURL );
	}

	public static String getSystemProperty( String propertyName ) {
		String appId = ApiProxy.getCurrentEnvironment().getAppId();
		if( appId.startsWith("s~") )
			appId = appId.substring( 2 );
		return System.getProperty( appId + "." + propertyName );
	}
	
	public static void performNewUserActions( HttpSession session, Long userId ) {
		Queue queue = QueueFactory.getQueue( "new-user" );
		queue.add( TaskOptions.Builder.withParam( "userId", userId.toString() ) );

		session.setAttribute( SessionAttributes.CURRENT_USER_ID, userId );
	}
	
}
