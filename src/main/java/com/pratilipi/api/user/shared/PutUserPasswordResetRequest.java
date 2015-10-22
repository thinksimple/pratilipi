package com.pratilipi.api.user.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;

public class PutUserPasswordResetRequest extends GenericRequest {
	
	private static final String EMAIL_REQUIRED_ERR_MSG = "Please provide your email.";
	private static final String EMAIL_INVALID_ERR_MSG = "Invalid email.";
	
	@Validate( required = true, requiredErrMsg = EMAIL_REQUIRED_ERR_MSG, regEx = REGEX_EMAIL, regExErrMsg = EMAIL_INVALID_ERR_MSG )
	private String email;
	
	public String getEmail() {
		return this.email;
	}
	
}
