package com.pratilipi.service.shared.data;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AuthorData implements IsSerializable {

	private Long id;
	
	private String firstName;
	
	private String lastName;
	
	private String email;

	private Date registrationDate;
	
	
	public Long getId() {
		return id;
	}

	public void setId( Long id ) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

}
