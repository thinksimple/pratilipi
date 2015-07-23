package com.pratilipi.api.user.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.common.type.UserSignUpSource;

public class PutUserRegisterRequest extends GenericRequest {

	private static final String NAME_REQUIRED_ERR_MSG = "Please enter your name.";

	private static final String EMAIL_REQUIRED_ERR_MSG = "Please provide your email.";
	private static final String EMAIL_INVALID_ERR_MSG = "Invalid email.";

	private static final String PASSWORD_REQUIRED_ERR_MSG = "Enter password.";
	private static final String PASSWORD_INVALID_ERR_MSG = "Password must contain at least 6 characters, no spaces.";

	private static final String PASSWORD2_REQUIRED_ERR_MSG = "Re-enter password.";

	
	@Validate( required = true, requiredErrMsg = NAME_REQUIRED_ERR_MSG )
	private String name;

	@Validate( required = true, requiredErrMsg = EMAIL_REQUIRED_ERR_MSG, regEx = REGEX_EMAIL, regExErrMsg = EMAIL_INVALID_ERR_MSG )
	private String email;

	@Validate( required = true, requiredErrMsg = PASSWORD_REQUIRED_ERR_MSG, regEx = REGEX_PASSWORD, regExErrMsg = PASSWORD_INVALID_ERR_MSG )
	private String password;

	@Validate( required = true, requiredErrMsg = PASSWORD2_REQUIRED_ERR_MSG )
	private String password2;

	@Validate( required = true )
	private UserSignUpSource source;

	
	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
	
	public String getPassword2() {
		return password2;
	}
	
	public UserSignUpSource getSignUpSource() {
		return source;
	}
	
}
