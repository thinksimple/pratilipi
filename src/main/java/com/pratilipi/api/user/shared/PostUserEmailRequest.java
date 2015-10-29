package com.pratilipi.api.user.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;

public class PostUserEmailRequest extends GenericRequest {
	
	@Validate( minLong = 1L )
	private Long userId;
	
	@Validate( regEx = REGEX_EMAIL, regExErrMsg = ERR_EMAIL_INVALID )
	private String email;
	
	private Boolean sendWelcomeMail;
	
	private Boolean sendEmailVerificationMail;
	
	private Boolean sendPasswordResetMail;
	
	private Boolean sendBirthdayMail;

	
	public Long getUserId() {
		return userId;
	}
	
	public String getEmail() {
		return email;
	}
	
	public boolean sendWelcomeMail() {
		return sendWelcomeMail == null ? false : sendWelcomeMail;
	}
	
	public boolean sendEmailVerificationMail() {
		return sendEmailVerificationMail == null ? false : sendEmailVerificationMail;
	}
	
	public boolean sendPasswordResetMail() { 
		return sendPasswordResetMail == null ? false : sendPasswordResetMail;
	}
	
	public boolean sendBirthdayMail() {
		return sendBirthdayMail == null ? false : sendBirthdayMail;
	}
	
}
