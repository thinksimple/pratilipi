package com.claymus.service.client;

import com.claymus.commons.client.IllegalArgumentException;
import com.claymus.service.shared.AddUserRequest;
import com.claymus.service.shared.AddUserResponse;
import com.claymus.service.shared.LoginUserRequest;
import com.claymus.service.shared.LoginUserResponse;
import com.claymus.service.shared.RegisterUserRequest;
import com.claymus.service.shared.RegisterUserResponse;
import com.claymus.service.shared.ResetUserPasswordRequest;
import com.claymus.service.shared.ResetUserPasswordResponse;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("../service.claymus")
public interface ClaymusService extends RemoteService {
	
	AddUserResponse addUser( AddUserRequest request );
	
	RegisterUserResponse registerUser( RegisterUserRequest request )
			throws IllegalArgumentException, Exception;

	LoginUserResponse loginUser( LoginUserRequest request )
			throws IllegalArgumentException, Exception;
	
	void logoutUser()
			throws IllegalArgumentException;
	
	ResetUserPasswordResponse resetUserPassword( ResetUserPasswordRequest request )
				throws IllegalArgumentException;
	
}
