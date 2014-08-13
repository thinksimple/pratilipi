package com.claymus.service.shared.data;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserData implements IsSerializable {

	private String firstName;
	
	private String lastName;

	private String email;
	
	
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
	
}
