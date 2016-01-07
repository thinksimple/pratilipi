package com.pratilipi.api.impl.user.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;

public class PostUserLoginRequest extends GenericRequest {

	@Validate( required = true, requiredErrMsg = ERR_EMAIL_REQUIRED, regEx = REGEX_EMAIL, regExErrMsg = ERR_EMAIL_INVALID )
	private String email;

	@Validate( required = true, requiredErrMsg = ERR_PASSWORD_REQUIRED )
	private String password;

	
	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
	
}
