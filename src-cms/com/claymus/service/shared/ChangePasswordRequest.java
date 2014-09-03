package com.claymus.service.shared;

import com.claymus.service.shared.data.ChangePasswordData;
import com.google.gwt.user.client.rpc.IsSerializable;

public class ChangePasswordRequest implements IsSerializable {
	private String email;
	private String currentPassword;
	private String newPassword;
	private String passInUrl;

	public ChangePasswordRequest(){}
	
	public ChangePasswordRequest(ChangePasswordData changePasswordData){
		this.email = changePasswordData.getEmail();
		this.currentPassword = changePasswordData.getCurrentPassword();
		this.newPassword = changePasswordData.getNewPassword();
		this.passInUrl = changePasswordData.getPassInUrl();
	}

	public String getEmail() {
		return email;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public String getPassInUrl() {
		return passInUrl;
	}
	
}
