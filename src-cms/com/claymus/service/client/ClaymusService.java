package com.claymus.service.client;

import com.claymus.service.shared.AddUserRequest;
import com.claymus.service.shared.AddUserResponse;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("../service.claymus")
public interface ClaymusService extends RemoteService {
	
	AddUserResponse addUser( AddUserRequest request );

}
