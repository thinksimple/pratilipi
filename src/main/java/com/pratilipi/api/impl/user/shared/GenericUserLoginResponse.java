package com.pratilipi.api.impl.user.shared;

import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.type.UserState;
import com.pratilipi.data.client.AuthorData;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.type.Page;

@SuppressWarnings("unused")
public class GenericUserLoginResponse extends GenericResponse {
	
	private String displayName;
	private String email;

	@Deprecated
	private Boolean isGuest;
	private Boolean isEmailVerified;
	
	private String profilePageUrl;

	
	public GenericUserLoginResponse( UserData userData ) {
		this.displayName = userData.getDisplayName();
		this.email = userData.getEmail();
		this.isGuest = userData.getState() == UserState.GUEST;
		this.isEmailVerified = userData.getState() == UserState.ACTIVE;
		this.profilePageUrl = userData.getProfilePageUrl();
	}
	
}
