package com.pratilipi.data.client;

public class UserData {

	private transient Long userId;

	private String firstName;
	private transient boolean hasFirstName;
	
	private String lastName;
	private transient boolean hasLastName;
	
	private String penName;
	private transient boolean hasPenName;

	private String name;

	private String fullName;

	private String email;
	private transient boolean hasEmail;

	private boolean isGuest;
	
	
	public UserData( Long userId ) {
		this.userId = userId;
	}

	
	public Long getId() {
		return userId;
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

}