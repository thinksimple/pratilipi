package com.pratilipi.api.impl.user.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;

public class PostUserVerificationRequest extends GenericRequest {
	
	@Validate( required = true, requiredErrMsg = ERR_EMAIL_REQUIRED, regEx = REGEX_EMAIL, regExErrMsg = ERR_EMAIL_INVALID )
	private String email;
	
	@Validate( required = true )
	private String verificationToken;
	
	
	public String getEmail() {
		return email;
	}
	
	public String getVerificationToken() {
		return verificationToken;
	}
	
}
