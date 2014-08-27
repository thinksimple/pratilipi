package com.claymus.service.shared.data;

import com.claymus.commons.shared.UserStatus;
import com.google.gwt.user.client.rpc.IsSerializable;

public class RegistrationData implements IsSerializable {

	private String firstName;
	
	private String lastName;

	private String email;
	
	private String password;
	
	private String campaign;
	
	private String referer;
	
	private UserStatus status;
	
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName( String firstName ) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName( String lastName ) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail( String email ) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCampaign() {
		return campaign;
	}

	public void setCampaign( String campaign ) {
		this.campaign = campaign;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer( String referer ) {
		this.referer = referer;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus( UserStatus status ) {
		this.status = status;
	}

}
