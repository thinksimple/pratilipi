package com.claymus.service.server;

import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.transfer.User;
import com.claymus.service.client.ClaymusService;
import com.claymus.service.shared.AddUserRequest;
import com.claymus.service.shared.AddUserResponse;
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
		if( user == null ) {
			user = dataAccessor.newUser();
			user.setFirstName( userData.getFirstName() );
			user.setLastName( user.getLastName() );
			user.setEmail( userData.getEmail() );
		
			user = dataAccessor.createUser( user );
		}
		dataAccessor.destroy();
		
		return new AddUserResponse();
	}

}
