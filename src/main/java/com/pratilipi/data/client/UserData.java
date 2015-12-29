package com.pratilipi.data.client;

import java.util.Date;

import com.pratilipi.common.type.Gender;
import com.pratilipi.common.type.UserState;

public class UserData {

	private transient Long userId;

	private transient String facebookId;

	private String firstName;
	
	private String lastName;
	
	private String penName;

	private String name;

	private String fullName;

	
	private Gender gender;

	private Date dateOfBirth;
	
	
	private String email;
	private transient boolean hasEmail;

	private UserState state;
	
	
	private boolean isGuest;
	
	
	private Date signUpDate;

	
	
	public UserData() {}

	public UserData( Long userId ) {
		this.userId = userId;
	}

	
	public Long getId() {
		return userId;
	}

	public void setId( Long id ) {
		this.userId = id;
	}
	
	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId( String facebookId ) {
		this.facebookId = facebookId;
	}
	
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
	
	public String getPenName() {
		return penName;
	}

	public void setPenName( String penName ) {
		this.penName = penName;
	}
	
	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName( String fullName ) {
		this.fullName = fullName;
	}

	
	public Gender getGender() {
		return gender;
	}

	public void setGender( Gender gender ) {
		this.gender = gender;
	}
	
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth( Date dateOfBirth ) {
		this.dateOfBirth = dateOfBirth;
	}
	
	
	public String getEmail() {
		return email == null || email.trim().isEmpty() ? null : email.trim().toLowerCase();
	}

	public void setEmail( String email ) {
		this.email = email;
		this.hasEmail = true;
	}
	
	public boolean hasEmail() {
		return hasEmail;
	}

	
	public UserState getState() {
		return state;
	}

	public void setState( UserState state ) {
		this.state = state;
	}
	

	public boolean isGuest() {
		return isGuest;
	}

	public void setIsGuest( boolean isGuest ) {
		this.isGuest = isGuest;
	}

	
	public Date getSignUpDate() {
		return signUpDate;
	}

	public void setSignUpDate( Date signUpDate ) {
		this.signUpDate = signUpDate;
	}


}