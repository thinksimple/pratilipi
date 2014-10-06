package com.claymus.service.server;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;

import com.claymus.commons.client.IllegalArgumentException;
import com.claymus.commons.client.UnexpectedServerException;
import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.server.EncryptPassword;
import com.claymus.commons.server.ValidateFbAccessToken;
import com.claymus.commons.shared.UserStatus;
import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.transfer.EmailTemplate;
import com.claymus.data.transfer.User;
import com.claymus.data.transfer.UserRole;
import com.claymus.email.EmailUtil;
import com.claymus.service.client.ClaymusService;
import com.claymus.service.shared.FacebookLoginUserRequest;
import com.claymus.service.shared.FacebookLoginUserResponse;
import com.claymus.service.shared.InviteUserRequest;
import com.claymus.service.shared.InviteUserResponse;
import com.claymus.service.shared.LoginUserRequest;
import com.claymus.service.shared.LoginUserResponse;
import com.claymus.service.shared.RegisterUserRequest;
import com.claymus.service.shared.RegisterUserResponse;
import com.claymus.service.shared.ResetUserPasswordRequest;
import com.claymus.service.shared.ResetUserPasswordResponse;
import com.claymus.service.shared.SendQueryRequest;
import com.claymus.service.shared.SendQueryResponse;
import com.claymus.service.shared.UpdateUserPasswordRequest;
import com.claymus.service.shared.UpdateUserPasswordResponse;
import com.claymus.service.shared.data.FacebookLoginData;
import com.claymus.service.shared.data.UserData;
import com.claymus.taskqueue.Task;
import com.claymus.taskqueue.TaskQueue;
import com.claymus.taskqueue.TaskQueueFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import freemarker.template.TemplateException;

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
		User user = dataAccessor.getUserByEmail( userData.getEmail().toLowerCase() );

		if( user == null ) {
			user = dataAccessor.newUser();
			user.setEmail( userData.getEmail().toLowerCase() );

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
		
		return new InviteUserResponse();
	}
	
	@Override
	public RegisterUserResponse registerUser( RegisterUserRequest request )
			throws IllegalArgumentException {

		UserData userData = request.getUser();

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = dataAccessor.getUserByEmail( userData.getEmail().toLowerCase() );

		if( user == null ) {
			user = dataAccessor.newUser();
			user.setEmail( userData.getEmail().toLowerCase() );
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
		
		this.getThreadLocalRequest().getSession().setAttribute(
				ClaymusHelper.SESSION_ATTRIB_CURRENT_USER_ID, user.getId() );
		
		String message = "SignUp successful! ";
		
		return new RegisterUserResponse( message );
	}

	@Override
	public LoginUserResponse loginUser( LoginUserRequest request )
			throws IllegalArgumentException {

		ClaymusHelper claymusHelper =
				ClaymusHelper.get( this.getThreadLocalRequest() );
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = dataAccessor.getUserByEmail( request.getLoginId().toLowerCase() );
		dataAccessor.destroy();
		
		if( user != null && user.getStatus() == UserStatus.POSTLAUNCH_SIGNUP_SOCIALLOGIN ) 
			throw new IllegalArgumentException(
					"You used social media account to login. Please click "
					+ "<a href='" + claymusHelper.createForgotPasswordURL() + "' class='alert-link'>here</a>"
					+ " to generate your password or kindly use social login to login again." );
		
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
				ClaymusHelper.get( this.getThreadLocalRequest() );
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = dataAccessor.getUserByEmail( request.getUserEmail().toLowerCase() );
		dataAccessor.destroy();
		
		if( user == null
				|| user.getStatus() == UserStatus.PRELAUNCH_REFERRAL
				|| user.getStatus() == UserStatus.PRELAUNCH_SIGNUP
				|| user.getStatus() == UserStatus.POSTLAUNCH_REFERRAL ) {
			throw new IllegalArgumentException(
					"This email id is not yet registered. Kindly "
					+ "<a href='" + claymusHelper.createRegisterURL() + "' class='alert-link'>register</a>"
					+ " or try again with a different email id." );
			
		} else if( user.getStatus() == UserStatus.POSTLAUNCH_SIGNUP || user.getStatus() == UserStatus.POSTLAUNCH_SIGNUP_SOCIALLOGIN ) {

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
		
		String message = "<Strong>Reset password link generated successfully. </strong>"
							+ "Please check your email for the link.";

		return new ResetUserPasswordResponse( message );
	}
	
	@Override
	public UpdateUserPasswordResponse updateUserPassword(
			UpdateUserPasswordRequest request ) throws IllegalArgumentException {

		ClaymusHelper claymusHelper =
				ClaymusHelper.get( this.getThreadLocalRequest() );

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		String userEmail = request.getUserEmail();
		User user = null;
		
		try {
			// Request from user profile
			if( userEmail == null ) {
				
				if( ! claymusHelper.isUserLoggedIn() )
					throw new IllegalArgumentException(
							"You are not logged in. Please "
							+ "<a href='" + claymusHelper.createLoginURL() + "' class='alert-link'>login</a> and try again. "
							+ "If you have forgotten your password, you can reset your password "
							+ "<a href='" + claymusHelper.createForgotPasswordURL() + " ' class='alert-link'>here</a>." );

				user = claymusHelper.getCurrentUser();

				if( request.getCurrentPassword() == null
						||  ! EncryptPassword.check( request.getCurrentPassword(),  user.getPassword() ) )
					throw new IllegalArgumentException( "Current password is not correct. Please try again." );


			// Request via password reset link
			} else {
				
				user = dataAccessor.getUserByEmail( userEmail );
				
				if( request.getToken() == null
						|| ! user.getPassword().equals( request.getToken() ) )
					throw new IllegalArgumentException(
							"URL used is invalid or expired. "
							+ "Please check the URL and try again." );
				
				if( user.getStatus() == UserStatus.POSTLAUNCH_SIGNUP_SOCIALLOGIN )
					user.setStatus( UserStatus.POSTLAUNCH_SIGNUP );
			}
			
			user.setPassword( EncryptPassword.getSaltedHash( request.getNewPassword() ));
			dataAccessor.createOrUpdateUser( user );

		} finally {
			dataAccessor.destroy();
		}
		
		String message = "<strong>Password changed successfully</strong>";
		
		return new UpdateUserPasswordResponse( message );
	}

	@Override
	public SendQueryResponse sendQuery( final SendQueryRequest request )
			throws UnexpectedServerException {
		
		EmailTemplate emailTemplate = new EmailTemplate() {

			@Override
			public String getId() {
				return null;
			}

			@Override
			public void setId( String id ) {}

			@Override
			public String getSenderName() {
				return "Query Form";
			}

			@Override
			public void setSenderName( String senderName ) {}

			@Override
			public String getSenderEmail() {
				return ClaymusHelper.getSystemProperty( "email.noreply" );
			}

			@Override
			public void setSenderEmail( String senderEmail ) {}

			@Override
			public String getReplyToName() {
				return request.getName();
			}

			@Override
			public void setReplyToName( String replyToName ) {}

			@Override
			public String getReplyToEmail() {
				return request.getEmail();
			}

			@Override
			public void setReplyToEmail( String replyToEmail ) {}

			@Override
			public String getSubject() {
				return "Query from " + request.getName();
			}

			@Override
			public void setSubject( String subject ) {}

			@Override
			public String getBody() {
				return request.getQuery();
			}

			@Override
			public void setBody( String body ) {}
			
		};
		
		try {
			EmailUtil.sendMail(
					null, ClaymusHelper.getSystemProperty( "email.contact" ),
					emailTemplate, null );
			return new SendQueryResponse( "Query submitted successfully !" );
			
		} catch ( MessagingException | IOException | TemplateException e ) {
			logger.log( Level.SEVERE, "Failed to e-mail contact query.", e );
			throw new UnexpectedServerException();
		}
		
	}

	
	@Override
	public FacebookLoginUserResponse facebookLogin(
			FacebookLoginUserRequest request)
			throws IllegalArgumentException {
		
		FacebookLoginData fbLoginUserData = request.getFacebookLoginData();
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = null;
		
		if( fbLoginUserData.getEmail() != null )
			user = dataAccessor.getUserByEmail( fbLoginUserData.getEmail() );
		else 
			throw new IllegalArgumentException( "Your Email address is required to complete basic login activities. Please provide provide access to your facebook email address" );
		
		ValidateFbAccessToken validateToken = new ValidateFbAccessToken( fbLoginUserData.getAccessToken() );
		try {
			if( validateToken.isValid() ) {
				if( user == null ) {
					user = dataAccessor.newUser();
					user.setEmail( fbLoginUserData.getEmail() );
					user.setFirstName( fbLoginUserData.getFirstName() );
					user.setLastName( fbLoginUserData.getLastName() );
					user.setCampaign( fbLoginUserData.getCampaign() );
					user.setReferer( fbLoginUserData.getReferer() );
					user.setStatus( UserStatus.POSTLAUNCH_SIGNUP_SOCIALLOGIN );
					user.setSignUpDate( new Date() );
					
					dataAccessor.createOrUpdateUser( user );
					//Getting new user.
					user = dataAccessor.getUserByEmail( user.getEmail() );
				}
				//Setting session for the user.
				this.getThreadLocalRequest().getSession().setAttribute(
						ClaymusHelper.SESSION_ATTRIB_CURRENT_USER_ID, user.getId() );
			}
			else
				throw new IllegalArgumentException( "Not a valid access token" );
		} catch (Exception e) {
			logger.log( Level.SEVERE, "" , e);
		} 
		
		return new FacebookLoginUserResponse();
	}

	
	
}
