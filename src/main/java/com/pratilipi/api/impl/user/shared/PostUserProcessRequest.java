package com.pratilipi.api.impl.user.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;

public class PostUserProcessRequest extends GenericRequest {

	@Validate( required = true )
	private Long userId;

	private Boolean validateData;
	

	public Long getUserId() {
		return userId;
	}

	public boolean validateData() {
		return validateData == null ? false : validateData;
	}

}
