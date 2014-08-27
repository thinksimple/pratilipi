package com.claymus.service.shared;

import com.claymus.service.shared.data.RegistrationData;
import com.google.gwt.user.client.rpc.IsSerializable;

public class RegisterUserRequest implements IsSerializable {
	private RegistrationData registerData;


	@SuppressWarnings("unused")
	private RegisterUserRequest() {}
	
	public RegisterUserRequest( RegistrationData registerData ) {
		this.registerData = registerData;
	}
	
	
	public RegistrationData getUser() {
		return this.registerData;
	}
}
