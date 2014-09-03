package com.claymus.service.server;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.claymus.commons.client.IllegalArgumentException;
import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.server.EncryptPassword;
import com.claymus.commons.shared.UserStatus;
import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.transfer.User;
import com.claymus.data.transfer.UserRole;
import com.claymus.service.client.ClaymusService;
import com.claymus.service.shared.ChangePasswordRequest;
import com.claymus.service.shared.ChangePasswordResponse;
import com.claymus.service.shared.InviteUserRequest;
import com.claymus.service.shared.InviteUserResponse;
import com.claymus.service.shared.LoginUserRequest;
import com.claymus.service.shared.LoginUserResponse;
import com.claymus.service.shared.RegisterUserRequest;
import com.claymus.service.shared.RegisterUserResponse;
import com.claymus.service.shared.ResetUserPasswordRequest;
import com.claymus.service.shared.ResetUserPasswordResponse;
import com.claymus.service.shared.data.UserData;
import com.claymus.taskqueue.Task;
import com.claymus.taskqueue.TaskQueue;
import com.claymus.taskqueue.TaskQueueFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ClaymusServiceImpl extends RemoteServiceServlet
		implements ClaymusService {

	private static final Logger logger = 
			Logger.getLogger( ClaymusServiceImpl.class.getName() );

	
	@Override
	public InviteUserResponse inviteUser( InviteUserRequest request )
			throws IllegalArgumentException {
		
		UserData userData = request.getUser();

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = dataAccessor.getUserByEmail( userData.getEmail() );

		if( user == null ) {
			user = dataAccessor.newUser();
			user.setEmail( userData.getEmail() );

			user.setCampaign( userData.getCampaign() );
			user.setReferer( userData.getReferer() );
			user.setSignUpDate( new Date() );
		
		} else if( user.getStatus() == UserStatus.PRELAUNCH_REFERRAL) {
			user.setCampaign( userData.getCampaign() );
			user.setReferer( userData.getReferer() );
			user.setSignUpDate( new Date() );
			
		} else if( userData.getStatus() == UserStatus.PRELAUNCH_SIGNUP ) {
			dataAccessor.destroy();
			throw new IllegalArgumentException( "User registered already !" );
			
		} else if( userData.getStatus() == UserStatus.POSTLAUNCH_REFERRAL ) {
			user.setCampaign( userData.getCampaign() );
			user.setReferer( userData.getReferer() );
			user.setSignUpDate( new Date() );
			
		} else if( userData.getStatus() == UserStatus.POSTLAUNCH_SIGNUP ) {
			dataAccessor.destroy();
			throw new IllegalArgumentException( "User registered alread !" );
	
		} else {
			dataAccessor.destroy();
			logger.log( Level.SEVERE,
					"User status " + user.getStatus() + " is not handeled !"  );
			throw new IllegalArgumentException( "Invitation failed ! "
					+ "Kindly contact the administrator." );
		}
			
		user.setFirstName( userData.getFirstName() );
		user.setLastName( userData.getLastName() );
		user.setStatus( UserStatus.POSTLAUNCH_REFERRAL );

		user = dataAccessor.createOrUpdateUser( user );
		dataAccessor.destroy();
		
		Task task = TaskQueueFactory.newTask();
		task.addParam( "userId", user.getId().toString() );
		
		TaskQueue taskQueue = TaskQueueFactory.getInviteUserTaskQueue();
		taskQueue.add( task );
		
		return new InviteUserResponse( user.getId() );
	}
	
	@Override
	public RegisterUserResponse registerUser( RegisterUserRequest request )
			throws IllegalArgumentException {

		UserData userData = request.getUser();

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = dataAccessor.getUserByEmail( userData.getEmail() );

		if( user == null ) {
			user = dataAccessor.newUser();
			user.setEmail( userData.getEmail() );
			user.setSignUpDate( new Date() );

			user.setCampaign( userData.getCampaign() );
			user.setReferer( userData.getReferer() );
			
		} else if( user.getStatus() == UserStatus.PRELAUNCH_REFERRAL ) {
			user.setCampaign( userData.getCampaign() );
			if( userData.getReferer() != null )
				user.setReferer( userData.getReferer() );
			user.setSignUpDate( new Date() );

		} else if( user.getStatus() == UserStatus.PRELAUNCH_SIGNUP ) {
			
		} else if( user.getStatus() == UserStatus.POSTLAUNCH_REFERRAL ) {
			user.setCampaign( userData.getCampaign() );
			if( userData.getReferer() != null )
				user.setReferer( userData.getReferer() );
			user.setSignUpDate( new Date() );

		} else if( user.getStatus() == UserStatus.POSTLAUNCH_SIGNUP ) {
			dataAccessor.destroy();
			throw new IllegalArgumentException( "This email id is already registered !" );

		} else {
			dataAccessor.destroy();
			logger.log( Level.SEVERE,
					"User status " + user.getStatus() + " is not handeled !"  );
			throw new IllegalArgumentException( "User registration failed ! "
					+ "Kindly contact the administrator." );
		}
		
		user.setFirstName( userData.getFirstName() );
		user.setLastName( userData.getLastName() );
		user.setPassword( EncryptPassword.getSaltedHash( userData.getPassword() ) );
		user.setStatus( UserStatus.POSTLAUNCH_SIGNUP );

		user = dataAccessor.createOrUpdateUser( user );
		
		UserRole userRole = dataAccessor.newUserRole();
		userRole.setUserId( user.getId() );
		userRole.setRoleId( "member" );
		dataAccessor.createOrUpdateUserRole( userRole );
		
		dataAccessor.destroy();

		Task task = TaskQueueFactory.newTask();
		task.addParam( "userId", user.getId().toString() );
		
		TaskQueue taskQueue = TaskQueueFactory.getWelcomeUserTaskQueue();
		taskQueue.add( task );
		
		return new RegisterUserResponse( user.getId() );
	}

	@Override
	public LoginUserResponse loginUser( LoginUserRequest request )
			throws IllegalArgumentException {

		ClaymusHelper claymusHelper =
				new ClaymusHelper( this.getThreadLocalRequest() );
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = dataAccessor.getUserByEmail( request.getLoginId() );
		dataAccessor.destroy();
		
		if( user == null
				|| user.getStatus() == UserStatus.PRELAUNCH_REFERRAL
				|| user.getStatus() == UserStatus.PRELAUNCH_SIGNUP
				|| user.getStatus() == UserStatus.POSTLAUNCH_REFERRAL ) {
			throw new IllegalArgumentException(
					"This email id is not yet registered. Kindly "
					+ "<a href='" + claymusHelper.createRegisterURL() + "' class='alert-link'>register</a>"
					+ " or try again with a different email id." );
			
		} else if( user.getStatus() == UserStatus.POSTLAUNCH_SIGNUP ) {

		} else {
			logger.log( Level.SEVERE,
					"User status " + user.getStatus() + " is not handeled !"  );
			throw new IllegalArgumentException( "Login failed ! "
					+ "Kindly contact the administrator." );
		}

		if( ! EncryptPassword.check( request.getPassword(), user.getPassword() ) )
			throw new IllegalArgumentException( "Incorrect password !" );

		this.getThreadLocalRequest().getSession().setAttribute(
				ClaymusHelper.SESSION_ATTRIB_CURRENT_USER_ID, user.getId() );
		
		return new LoginUserResponse();
	}

	@Override
	public void logoutUser() {
		this.getThreadLocalRequest().getSession().invalidate();
	}

	@Override
	public ResetUserPasswordResponse resetUserPassword(
			ResetUserPasswordRequest request ) throws IllegalArgumentException {
		
		ClaymusHelper claymusHelper =
				new ClaymusHelper( this.getThreadLocalRequest() );
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = dataAccessor.getUserByEmail( request.getUserEmail() );
		dataAccessor.destroy();
		
		if( user == null
				|| user.getStatus() == UserStatus.PRELAUNCH_REFERRAL
				|| user.getStatus() == UserStatus.PRELAUNCH_SIGNUP
				|| user.getStatus() == UserStatus.POSTLAUNCH_REFERRAL ) {
			throw new IllegalArgumentException(
					"This email id is not yet registered. Kindly "
					+ "<a href='" + claymusHelper.createRegisterURL() + "' class='alert-link'>register</a>"
					+ " or try again with a different email id." );
			
		} else if( user.getStatus() == UserStatus.POSTLAUNCH_SIGNUP ) {

		} else {
			logger.log( Level.SEVERE,
					"User status " + user.getStatus() + " is not handeled !"  );
			throw new IllegalArgumentException( "Password reset failed ! "
					+ "Kindly contact the administrator." );
		}

		Task task = TaskQueueFactory.newTask();
		task.addParam( "userId", user.getId().toString() );
		
		TaskQueue taskQueue = TaskQueueFactory.getResetPasswordTaskQueue();
		taskQueue.add( task );

		return new ResetUserPasswordResponse();
	}
	
	@Override
	public ChangePasswordResponse changeUserPassword(ChangePasswordRequest request)
			throws IllegalArgumentException {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		ClaymusHelper claymusHelper =
				new ClaymusHelper( this.getThreadLocalRequest() );
		
		String tempEmail = request.getEmail();
		
		//if request object does not contain email, try retrieving it from session.
		if( request.getEmail() == null || request.getEmail().isEmpty() ){
			User tempUser = claymusHelper.getCurrentUser();
			tempEmail = tempUser.getEmail();
		}	
		
		User user = dataAccessor.getUserByEmail( tempEmail );
		
		//User is not logged in and trying to change password by using direct URL
		if( user != null ){
			if( request.getPassInUrl() != null && !user.getPassword().equals( request.getPassInUrl() ) )
				throw new IllegalArgumentException( "URL used is not valid. Please check the URL and try again" );
			else if( !request.getCurrentPassword().isEmpty() && !EncryptPassword.check( request.getCurrentPassword(),  user.getPassword() ) )
				throw new IllegalArgumentException( "Current Password is not correct. Please try again" );
		}
		
		if(user == null ){
			if( request.getPassInUrl() != null )
				throw new IllegalArgumentException( "URL used is not valid. Please check and try again" );
			if( claymusHelper.isUserLoggedIn() )
				throw new IllegalArgumentException( 
					"You are not logged in. In case you forgot your password "
					+ "<a href='" + claymusHelper.createForgotPasswordURL() + " ' class='alert-link'>Click Here</a>" );
		}
		
		user.setPassword( EncryptPassword.getSaltedHash( request.getNewPassword() ));
		dataAccessor.createOrUpdateUser( user );
		
		dataAccessor.destroy();
		
		return new ChangePasswordResponse();
		
	}

}