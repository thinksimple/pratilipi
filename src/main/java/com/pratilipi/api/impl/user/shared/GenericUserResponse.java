package com.pratilipi.api.impl.user.shared;

import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.type.UserState;
import com.pratilipi.data.client.UserData;

public class GenericUserResponse extends GenericResponse {
	
	private String displayName;
	private String email;
	private String phone;
	private UserState state;

	@Deprecated
	private Boolean isGuest;
	private Boolean isEmailVerified;
	
	private String profilePageUrl;
	private String profileImageUrl;

	
	public GenericUserResponse( UserData userData ) {
		this.displayName = userData.getDisplayName();
		this.email = userData.getEmail();
		this.phone = userData.getPhone();
		this.state = userData.getState();
		this.isGuest = userData.getState() == UserState.GUEST;
		this.isEmailVerified = userData.getState() == UserState.ACTIVE;
		this.profilePageUrl = userData.getProfilePageUrl();
		this.profileImageUrl = userData.getProfileImageUrl();
	}
	
	
	public String getDisplayName() {
		return displayName;
	}

	public String getEmail() {
		return email;
	}
	
	public String getPhone() {
		return phone;
	}

	public UserState getState() {
		return state;
	}


	public Boolean getIsGuest() {
		return isGuest;
	}

	public Boolean getIsEmailVerified() {
		return isEmailVerified;
	}


	public String getProfilePageUrl() {
		return profilePageUrl;
	}
	
	public String getProfileImageUrl() {
		return profileImageUrl;
	}
	
}
