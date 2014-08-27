package com.claymus.service.client;

import com.claymus.service.shared.AddUserRequest;
import com.claymus.service.shared.AddUserResponse;
import com.claymus.service.shared.LoginUserRequest;
import com.claymus.service.shared.LoginUserResponse;
import com.claymus.service.shared.RegisterUserRequest;
import com.claymus.service.shared.RegisterUserResponse;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ClaymusServiceAsync {
	
	void addUser(
			AddUserRequest request,
			AsyncCallback<AddUserResponse> callback );
	
	void registerUser(
			RegisterUserRequest request,
			AsyncCallback<RegisterUserResponse> callback );

	void loginUser(
			LoginUserRequest request,
			AsyncCallback<LoginUserResponse> callback );

}
