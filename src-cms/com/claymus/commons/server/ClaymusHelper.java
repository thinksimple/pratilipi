package com.claymus.commons.server;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.transfer.RoleAccess;
import com.claymus.data.transfer.User;
import com.claymus.data.transfer.UserRole;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.apphosting.api.ApiProxy;

public class ClaymusHelper {

	private static final String SESSION_ATTRIB_CURRENT_USER_ID = "CurrentUserId";
	private static final String URL_LOGIN_PAGE = "/login?dest=";
	private static final String URL_LOGOUT_PAGE = "/logout?dest=";
	
	private final HttpServletRequest request;
	private final HttpSession session;
	
	private User currentUser;
	private List<UserRole> currentUserRoleList;

	
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
		return (Long) session.getAttribute( SESSION_ATTRIB_CURRENT_USER_ID );
	}

	public User getCurrentUser() {
		if( currentUser == null ) {
			DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
			currentUser = dataAccessor.getUser( getCurrentUserId() );
			dataAccessor.destroy();
		}
		return currentUser;
	}
	
	public List<UserRole> getCurrentUserRoleList() {
		if( currentUserRoleList == null ) {
			DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
			currentUserRoleList = dataAccessor.getUserRoleList( getCurrentUserId() );
			dataAccessor.destroy();
		}
		return currentUserRoleList;
	}
	
	@Deprecated
	public static boolean isUserAdmin() {
		UserService userService = UserServiceFactory.getUserService();
		return userService.isUserLoggedIn() && userService.isUserAdmin();
	}
	
	public boolean hasUserAccess( String accessId, boolean defaultAccess ) {
		Boolean access = null;

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		for( UserRole userRole : getCurrentUserRoleList() ) {
			RoleAccess roleAccess = dataAccessor
					.getRoleAccess( userRole.getRoleId(), accessId );
			if( roleAccess != null ) {
				access = roleAccess.hasAccess();
				if( access )
					break;
			}
		}
		dataAccessor.destroy();
		
		if( access == null )
			return defaultAccess;
		
		return access;
	}
	
	public String createLoginURL() {
		return URL_LOGIN_PAGE + request.getRequestURI();
	}

	public static String createLoginURL( String destinationURL ) {
		return URL_LOGIN_PAGE + destinationURL;
	}

	public String createLogoutURL() {
		return URL_LOGOUT_PAGE + request.getRequestURI();
	}

	public static String createLogoutURL( String destinationURL ) {
		return URL_LOGOUT_PAGE + destinationURL;
	}

	
	public static String getSystemProperty( String propertyName ) {
		String appId = ApiProxy.getCurrentEnvironment().getAppId();
		if( appId.startsWith("s~") )
			appId = appId.substring( 2 );
		return System.getProperty( appId + "." + propertyName );
	}
	
	public static void performNewUserActions( HttpSession session, User user ) {
		Queue queue = QueueFactory.getQueue( "new-user" );
		queue.add( TaskOptions.Builder.withParam( "userId", user.getId().toString() ) );

		session.setAttribute( SESSION_ATTRIB_CURRENT_USER_ID, user.getId() );
	}
	
	public static void performUserLoginActions( HttpSession session, User user ) {
		session.setAttribute( SESSION_ATTRIB_CURRENT_USER_ID, user.getId() );
	}
	
}
