package com.claymus.service.shared.data;

public class ChangePasswordData {
	private String email;
	private String currentPassword;
	private String newPassword;
	private String passInUrl;
	
	//Setters and getters
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCurrentPassword() {
		return currentPassword;
	}
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getPassInUrl() {
		return passInUrl;
	}
	public void setPassInUrl(String passInUrl) {
		this.passInUrl = passInUrl;
	}
	
}
