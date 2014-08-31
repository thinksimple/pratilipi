package com.claymus.service.client;

import com.claymus.service.shared.InviteUserRequest;
import com.claymus.service.shared.InviteUserResponse;
import com.claymus.service.shared.LoginUserRequest;
import com.claymus.service.shared.LoginUserResponse;
import com.claymus.service.shared.RegisterUserRequest;
import com.claymus.service.shared.RegisterUserResponse;
import com.claymus.service.shared.ResetUserPasswordRequest;
import com.claymus.service.shared.ResetUserPasswordResponse;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ClaymusServiceAsync {
	
	void inviteUser(
			InviteUserRequest request,
			AsyncCallback<InviteUserResponse> callback );
	
	void registerUser(
			RegisterUserRequest request,
			AsyncCallback<RegisterUserResponse> callback );

	void loginUser(
			LoginUserRequest request,
			AsyncCallback<LoginUserResponse> callback );
	
	void logoutUser( AsyncCallback<Void> callback );
	
	void resetUserPassword(
			ResetUserPasswordRequest request,
			AsyncCallback<ResetUserPasswordResponse> callback );

}
