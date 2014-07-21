package com.claymus;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class ClaymusHelper {

	private static final UserService userService = UserServiceFactory.getUserService();

	public static boolean isUserAdmin() {
		return userService.isUserLoggedIn() && userService.isUserAdmin();
	}
	
}
