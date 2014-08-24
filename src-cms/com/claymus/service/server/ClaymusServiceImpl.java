
package com.claymus.service.server;

import java.util.Date;

import com.claymus.commons.client.IllegalArgumentException;
import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.shared.UserStatus;
import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.transfer.User;
import com.claymus.service.client.ClaymusService;
import com.claymus.service.shared.AddUserRequest;
import com.claymus.service.shared.AddUserResponse;
import com.claymus.service.shared.LoginUserRequest;
import com.claymus.service.shared.LoginUserResponse;
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
	public LoginUserResponse loginUser( LoginUserRequest request )
			throws IllegalArgumentException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		User user = dataAccessor.getUserByEmail( request.getLoginId() );
		dataAccessor.destroy();
		
		if( ! user.getPassword().equals( request.getPassword() ) )
			throw new IllegalArgumentException( "Invalid email id or password !" );
		
		ClaymusHelper.performUserLoginActions(
				this.getThreadLocalRequest().getSession(),
				user );
		
		return new LoginUserResponse();
	}

}
