
package com.claymus.service.server;

import java.util.Date;
import java.util.Random;

import com.claymus.commons.client.IllegalArgumentException;
import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.server.EncryptPassword;
import com.claymus.commons.shared.UserStatus;
import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.transfer.User;
import com.claymus.service.client.ClaymusService;
import com.claymus.service.shared.AddUserRequest;
import com.claymus.service.shared.AddUserResponse;
import com.claymus.service.shared.LoginUserRequest;
import com.claymus.service.shared.LoginUserResponse;
import com.claymus.service.shared.RegisterUserRequest;
import com.claymus.service.shared.RegisterUserResponse;
import com.claymus.service.shared.data.RegistrationData;
import com.claymus.service.shared.data.UserData;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ClaymusServiceImpl
		extends RemoteServiceServlet
		implements ClaymusService {

	@Override
	public AddUserResponse addUser( AddUserRequest request ) {
		
		UserData userData = request.getUser();

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = dataAccessor.getUserByEmail( userData.getEmail() );

		if( user == null )
			user = dataAccessor.newUser();
			
		if( user.getStatus() == null 
				|| user.getStatus() == userData.getStatus()
				|| userData.getStatus() == UserStatus.PRELAUNCH_SIGNUP ) {
			
			user.setFirstName( userData.getFirstName() );
			user.setLastName( userData.getLastName() );
			user.setEmail( userData.getEmail() );
			user.setCampaign( userData.getCampaign() );
			user.setReferer( userData.getReferer() );
			user.setSignUpDate( new Date() );
			user.setStatus( userData.getStatus() );

			user = dataAccessor.createUser( user );
		}
		dataAccessor.destroy();
		
		if( user.getStatus() == userData.getStatus() )
			ClaymusHelper.performNewUserActions(
					this.getThreadLocalRequest().getSession(),
					user );
		
		return new AddUserResponse( user.getId() );
	}
	
	@Override
	public RegisterUserResponse registerUser(RegisterUserRequest request)
		throws IllegalArgumentException,Exception {
		RegistrationData registerData = request.getUser();

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = dataAccessor.getUserByEmail( registerData.getEmail() );

		if( user == null )
			user = dataAccessor.newUser();
		
		//For subscribed and referred users.
		if( user.getStatus() != UserStatus.REGISTERED ){
			
			user.setFirstName( registerData.getFirstName() );
			user.setLastName( registerData.getLastName() );
			user.setEmail( registerData.getEmail() );
			//user.setPassword( registerData.getPassword() );
			
			//Password encryption.
			//Have no idea how to implement try catch thing.
			try {
				user.setPassword( EncryptPassword.getSaltedHash( registerData.getPassword() ));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			user.setCampaign( registerData.getCampaign() );
			user.setReferer( registerData.getReferer() );
			user.setSignUpDate( new Date() );
			user.setStatus( registerData.getStatus() );

			user = dataAccessor.createUser( user );
		}
		else 
			throw new IllegalArgumentException( "This Email Id is already registered" );
		
		
		return new RegisterUserResponse( user.getId() );
	}

	@Override
	public LoginUserResponse loginUser( LoginUserRequest request )
			throws IllegalArgumentException, Exception {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = dataAccessor.getUserByEmail( request.getLoginId() );
		dataAccessor.destroy();
		boolean isValidUser = false;
		
		try {
			isValidUser = EncryptPassword.check( request.getPassword(),  user.getPassword() );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if( ! isValidUser){
			if( user.getPassword().isEmpty() )
				throw new IllegalArgumentException( "You are a subscribed user. Please Sign up to login" );
			else
				throw new IllegalArgumentException( "Invalid email id or password !" );
		}
		//if( ! user.getPassword().equals( request.getPassword() ) )
		//	throw new IllegalArgumentException( "Invalid email id or password !" );
		
		ClaymusHelper.performUserLoginActions(
				this.getThreadLocalRequest().getSession(),
				user );
		
		return new LoginUserResponse();
	}

	@Override
	public void logoutUser()
			throws IllegalArgumentException {
		ClaymusHelper.performUserLogoutActions( this.getThreadLocalRequest().getSession() );
	}

	@Override
	public LoginUserResponse regeneratePassword(LoginUserRequest request)
			throws IllegalArgumentException {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = dataAccessor.getUserByEmail( request.getLoginId() );
		
		if( user == null )
			throw new IllegalArgumentException( "This email id is not registered. Please try again" );
		
		final char[] charList;
		//Array of alphabets and number
		StringBuilder tmp = new StringBuilder();
		for (char ch = '0'; ch <= '9'; ++ch)
		  tmp.append(ch);
		for (char ch = 'A'; ch <= 'z'; ++ch)
		  tmp.append(ch);
		charList = tmp.toString().toCharArray();
		  
		
		char[] newPassword = new char[6];
		final Random random = new Random();
		int range = charList.length;
		
		//generating new password.
		for( int index=0; index<6; ++index)
			newPassword[index] = charList[ random.nextInt( range ) ];
		
		System.out.println( new String(newPassword) );
		//Password Encryption
		try {
			user.setPassword( EncryptPassword.getSaltedHash( new String(newPassword) ));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Update database
		dataAccessor.createUser( user );
		dataAccessor.destroy();
				
		return new LoginUserResponse();
	}

}
