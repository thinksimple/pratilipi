package com.pratilipi.api.user.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;

public class PostUserPasswordChangeRequest extends GenericRequest {
	
	private static final String PASSWORD_REQUIRED_ERR_MSG = "Enter password.";
	private static final String PASSWORD_INVALID_ERR_MSG = "Password must contain at least 6 characters, no spaces.";
	private static final String PASSWORD2_REQUIRED_ERR_MSG = "Re-enter password.";

	
	@Validate( required = true, regEx = REGEX_EMAIL )
	private String email;
	
	private String token;
	
	private String currentPassword;
	
	@Validate( required = true, requiredErrMsg = PASSWORD_REQUIRED_ERR_MSG, regEx = REGEX_PASSWORD, regExErrMsg = PASSWORD_INVALID_ERR_MSG )
	private String password;
	
	@Validate( required = true, requiredErrMsg = PASSWORD2_REQUIRED_ERR_MSG )
	private String password2;
	
	
	public String getEmail() {
		return this.email;
	}
	
	public String getToken() {
		return this.token;
	}
	
	public String getCurrentPassword() {
		return this.currentPassword;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String getPassword2() {
		return this.password2;
	}
	
}