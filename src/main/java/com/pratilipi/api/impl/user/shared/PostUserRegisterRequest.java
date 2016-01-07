package com.pratilipi.api.impl.user.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;

public class PostUserRegisterRequest extends GenericRequest {

	@Validate( required = true, requiredErrMsg = ERR_NAME_REQUIRED )
	private String name;

	@Validate( required = true, requiredErrMsg = ERR_EMAIL_REQUIRED, regEx = REGEX_EMAIL, regExErrMsg = ERR_EMAIL_INVALID )
	private String email;

	@Validate( required = true, requiredErrMsg = ERR_PASSWORD_REQUIRED, regEx = REGEX_PASSWORD, regExErrMsg = ERR_PASSWORD_INVALID )
	private String password;

	
	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
	
}
