package com.claymus;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

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
	
}
