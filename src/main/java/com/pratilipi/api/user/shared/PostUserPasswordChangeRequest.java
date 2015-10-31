package com.pratilipi.api.user.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;

public class PostUserPasswordChangeRequest extends GenericRequest {
	
	@Validate( regEx = REGEX_EMAIL )
	private String email;
	
	private String verificationToken;
	
	private String password;
	
	@Validate( required = true, requiredErrMsg = ERR_PASSWORD_REQUIRED, regEx = REGEX_PASSWORD, regExErrMsg = ERR_PASSWORD_INVALID )
	private String newPassword;
	
	@Validate( required = true, requiredErrMsg = ERR_PASSWORD2_REQUIRED )
	private String newPassword2;
	
	
	public String getEmail() {
		return email;
	}
	
	public String getVerificationToken() {
		return verificationToken;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getNewPassword() {
		return newPassword;
	}
	
	public String getNewPassword2() {
		return newPassword2;
	}
	
}