package com.pratilipi.api.impl.user.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;

public class PostUserRequest extends GenericRequest {

	@Validate( minLong = 1L )
	private Long userId;
	
	private String name;
	private boolean hasName;

	@Validate( regEx = REGEX_EMAIL, regExErrMsg = ERR_EMAIL_INVALID )
	private String email;
	private boolean hasEmail;
	
	@Validate( regEx = REGEX_PHONE, regExErrMsg = ERR_PHONE_INVALID )
	private String phone;
	private boolean hasPhone;

	
	public Long getId() {
		return userId;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean hasName() {
		return hasName;
	}

	public String getEmail() {
		return email;
	}
	
	public boolean hasEmail() {
		return hasEmail;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public boolean hasPhone() {
		return hasPhone;
	}
	
}
