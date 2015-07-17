package com.pratilipi.api.user.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;

public class UserLoginRequest extends GenericRequest {

	@Validate( regEx = REGEX_EMAIL )
	private String email;

	@Validate( regEx = REGEX_NON_EMPTY_STRING )
	private String password;

	
	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
	
}
