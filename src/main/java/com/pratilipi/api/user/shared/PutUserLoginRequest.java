package com.pratilipi.api.user.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;

public class PutUserLoginRequest extends GenericRequest {

	private static final String EMAIL_REQUIRED_ERR_MSG = "Enter email.";
	private static final String EMAIL_INVALID_ERR_MSG = "Invalid email id.";
	private static final String PASSWORD_REQUIRED_ERR_MSG = "Enter password.";

	
	@Validate( required = true, requiredErrMsg = EMAIL_REQUIRED_ERR_MSG, regEx = REGEX_EMAIL, regExErrMsg = EMAIL_INVALID_ERR_MSG )
	private String email;

	@Validate( required = true, requiredErrMsg = PASSWORD_REQUIRED_ERR_MSG, regEx = REGEX_NON_EMPTY_STRING, regExErrMsg = PASSWORD_REQUIRED_ERR_MSG )
	private String password;

	
	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
	
}
