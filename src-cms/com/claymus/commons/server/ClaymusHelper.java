package com.claymus.commons.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.transfer.User;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.apphosting.api.ApiProxy;

public class ClaymusHelper {

	public static final String CURRENT_USER_ID = "CurrentUserId";

	public static final String LOGIN_URL = "/login?dest=";
	public static final String LOGOUT_URL = "/logout?dest=";
	
	private final HttpServletRequest request;
	private final HttpSession session;
	
	private User currentUser;
	
	
	@Deprecated
	public ClaymusHelper() {
		this.request = null;
		this.session = null;
	}
	
	public ClaymusHelper( HttpServletRequest request ) {
		this.request = request;
		this.session = request.getSession();
	}
	
	
	public Long getCurrentUserId() {
		return (Long) session.getAttribute( CURRENT_USER_ID );
	}

	public User getCurrentUser() {
		if( currentUser == null ) {
			DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
			currentUser = dataAccessor.getUser( getCurrentUserId() );
			dataAccessor.destroy();
		}
		return currentUser;
	}
	
	@Deprecated
	public static boolean isUserAdmin() {
		UserService userService = UserServiceFactory.getUserService();
		return userService.isUserLoggedIn() && userService.isUserAdmin();
	}
	
	public String createLoginURL() {
		return LOGIN_URL + request.getRequestURI();
	}

	public static String createLoginURL( String destinationURL ) {
		return LOGIN_URL + destinationURL;
	}

	public String createLogoutURL() {
		return LOGOUT_URL + request.getRequestURI();
	}

	public static String createLogoutURL( String destinationURL ) {
		return LOGOUT_URL + destinationURL;
	}

	
	public static String getSystemProperty( String propertyName ) {
		String appId = ApiProxy.getCurrentEnvironment().getAppId();
		if( appId.startsWith("s~") )
			appId = appId.substring( 2 );
		return System.getProperty( appId + "." + propertyName );
	}
	
	public static void performNewUserActions( HttpSession session, com.claymus.data.transfer.User user ) {
		Queue queue = QueueFactory.getQueue( "new-user" );
		queue.add( TaskOptions.Builder.withParam( "userId", user.getId().toString() ) );

		session.setAttribute( CURRENT_USER_ID, user.getId() );
	}
	
}
