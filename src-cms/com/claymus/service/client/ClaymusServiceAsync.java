package com.claymus.service.client;

import com.claymus.service.shared.AddUserRequest;
import com.claymus.service.shared.AddUserResponse;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ClaymusServiceAsync {
	
	void addUser(
			AddUserRequest request,
			AsyncCallback<AddUserResponse> callback );

}
