package com.pratilipi.api.user.shared;

import com.pratilipi.api.shared.GenericResponse;

@SuppressWarnings("unused")
public class UserResponse extends GenericResponse {
	
	private String firstName;
	private String lastName;
	private String penName;

	private String name;
	private String fullName;

	private String email;

	private boolean isGuest;

}
