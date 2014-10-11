package com.claymus.service.client;

import com.claymus.commons.client.IllegalArgumentException;
import com.claymus.commons.client.InsufficientAccessException;
import com.claymus.commons.client.UnexpectedServerException;
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
import com.claymus.service.shared.SavePageContentRequest;
import com.claymus.service.shared.SavePageContentResponse;
import com.claymus.service.shared.SaveRoleAccessRequest;
import com.claymus.service.shared.SaveRoleAccessResponse;
import com.claymus.service.shared.SendQueryRequest;
import com.claymus.service.shared.SendQueryResponse;
import com.claymus.service.shared.UpdateUserPasswordRequest;
import com.claymus.service.shared.UpdateUserPasswordResponse;
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
	
	FacebookLoginUserResponse facebookLogin( FacebookLoginUserRequest request )
			throws IllegalArgumentException;
	
	void logoutUser();
	
	ResetUserPasswordResponse resetUserPassword( ResetUserPasswordRequest request )
			throws IllegalArgumentException;
	
	UpdateUserPasswordResponse updateUserPassword( UpdateUserPasswordRequest request )
			throws IllegalArgumentException;
	
	SendQueryResponse sendQuery( SendQueryRequest request )
			throws UnexpectedServerException;

	
	SavePageContentResponse savePageContent( SavePageContentRequest request )
			throws InsufficientAccessException;

	SaveRoleAccessResponse saveRoleAccess( SaveRoleAccessRequest request )
			throws InsufficientAccessException;
	
}