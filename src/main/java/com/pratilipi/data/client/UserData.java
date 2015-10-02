package com.pratilipi.data.client;

import java.util.Date;

import com.pratilipi.common.type.Gender;

public class UserData {

	private transient Long userId;

	private transient String facebookId;

	private String firstName;
	private transient boolean hasFirstName;
	
	private String lastName;
	private transient boolean hasLastName;
	
	private String penName;
	private transient boolean hasPenName;

	private String name;

	private String fullName;

	
	private Gender gender;
	private transient boolean hasGender;

	private Date dateOfBirth;
	private transient boolean hasDateOfBirth;
	
	
	private String email;
	private transient boolean hasEmail;

	private boolean isGuest;
	
	
	private Date signUpDate;

	
	public UserData( Long userId ) {
		this.userId = userId;
	}

	public UserData( String facebookId ) {
		this.facebookId = facebookId;
	}

	
	public Long getId() {
		return userId;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName( String firstName ) {
		this.firstName = firstName;
		this.hasFirstName = true;
	}

	public boolean hasFirstName() {
		return hasFirstName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName( String lastName ) {
		this.lastName = lastName;
		this.hasLastName = true;
	}
	
	public boolean hasLastName() {
		return hasLastName;
	}

	public String getPenName() {
		return penName;
	}

	public void setPenName( String penName ) {
		this.penName = penName;
		this.hasPenName = true;
	}
	
	public boolean hasPenName() {
		return hasPenName;
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
		this.hasGender = true;
	}
	
	public boolean hasGender() {
		return hasGender;
	}
	
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth( Date dateOfBirth ) {
		this.dateOfBirth = dateOfBirth;
		this.hasDateOfBirth = true;
	}
	
	public boolean hasDateOfBirth() {
		return hasDateOfBirth;
	}
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail( String email ) {
		this.email = email;
		this.hasEmail = true;
	}
	
	public boolean hasEmail() {
		return hasEmail;
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