package com.claymus.service.client;

import com.claymus.commons.client.IllegalArgumentException;
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
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("../service.claymus")
public interface ClaymusService extends RemoteService {
	
	InviteUserResponse inviteUser( InviteUserRequest request )
		throws IllegalArgumentException;
	
	RegisterUserResponse registerUser( RegisterUserRequest request )
			throws IllegalArgumentException;

	LoginUserResponse loginUser( LoginUserRequest request )
			throws IllegalArgumentException;
	
	void logoutUser();
	
	ResetUserPasswordResponse resetUserPassword( ResetUserPasswordRequest request )
				throws IllegalArgumentException;
	
	ChangePasswordResponse changeUserPassword( ChangePasswordRequest request )
			throws IllegalArgumentException;
	
}