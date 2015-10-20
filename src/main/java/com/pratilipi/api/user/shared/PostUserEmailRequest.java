package com.pratilipi.api.user.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;

public class PostUserEmailRequest extends GenericRequest {
	
	@Validate( required = true )
	private Long userId;
	
	private Boolean sendWelcomeMail;
	
	private Boolean sendEmailVerificationMail;
	
	private Boolean sendPasswordResetMail;
	
	private Boolean sendBirthdayMail;

	
	public Long getUserId() {
		return userId;
	}
	
	public boolean sendWelcomeMail() {
		return sendWelcomeMail == null ? false : sendWelcomeMail;
	}
	
	public boolean sendEmailVerificationMail() {
		return sendEmailVerificationMail == null ? false : sendEmailVerificationMail;
	}
	
	public boolean sendPasswordResetMail() { 
		return this.sendPasswordResetMail == null ? false : sendPasswordResetMail;
	}
	
	public boolean sendBirthdayMail() {
		return sendBirthdayMail == null ? false : sendBirthdayMail;
	}
	
}
