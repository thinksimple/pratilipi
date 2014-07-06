package com.claymus.data.access.gae;

import java.util.Date;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.claymus.data.transfer.User;

@PersistenceCapable( table = "USER" )
public class UserEntity implements User {

	@PrimaryKey
	@Persistent( column = "USER_ID" )
	private String id;
	
	@Persistent( column = "PASSWORD" )
	private String password;
	
	@Persistent( column = "NAME" )
	private String name;
	
	@Persistent( column = "EMAIL" )
	private String email;
	
	@Persistent( column = "PHONE" )
	private String phone;
	
	@Persistent( column = "SIGN_UP_DATE" )
	private Date signUpDate;

	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId( String id ) {
		this.id = id;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setPassword( String password ) {
		this.password = password;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName( String name ) {
		this.name = name;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public void setEmail( String email ) {
		this.email = email;
	}

	@Override
	public String getPhone() {
		return phone;
	}

	@Override
	public void setPhone( String phone ) {
		this.phone = phone;
	}

	@Override
	public Date getSignUpDate() {
		return signUpDate;
	}

	public void setSignUpDate( Date signUpDate ) {
		this.signUpDate = signUpDate;
	}

}
