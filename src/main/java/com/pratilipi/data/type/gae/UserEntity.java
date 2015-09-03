package com.pratilipi.data.type.gae;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.pratilipi.common.type.Gender;
import com.pratilipi.common.type.UserSignUpSource;
import com.pratilipi.common.type.UserState;
import com.pratilipi.common.type.UserStatus;
import com.pratilipi.data.type.User;

@PersistenceCapable( table = "USER" )
public class UserEntity implements User {
	
	private static final long serialVersionUID = 5942981653445086715L;
	
	@PrimaryKey
	@Persistent( column = "USER_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;
	
	@Persistent( column = "FACEBOOK_ID" )
	private String facebookId;
	
	@Persistent( column = "PASSWORD" )
	private String password;
	
	
	@Persistent( column = "FIRST_NAME" )
	private String firstName;
	
	@Persistent( column = "LAST_NAME" )
	private String lastName;
	
	@Persistent( column = "NICK_NAME" )
	private String nickName;
	
	@Persistent( column = "GENDER" )
	private Gender gender;
	
	@Persistent( column = "DATE_OF_BIRTH" )
	private Date dateOfBirth;
	
	
	@Persistent( column = "EMAIL" )
	private String email;
	
	@Persistent( column = "PHONE" )
	private String phone;
	
	
	@Deprecated
	@Persistent( column = "CAMPAIGN" )
	private String campaign;
	
	@Deprecated
	@Persistent( column = "REFERER" )
	private String referer;
	
	@Deprecated
	@Persistent( column = "STATUS" )
	private UserStatus status;
	
	@Persistent( column = "STATE" )
	private UserState state;
	
	
	@Persistent( column = "SIGN_UP_DATE" )
	private Date signUpDate;
	
	@Persistent( column = "SIGN_UP_SOURCE" )
	private UserSignUpSource signUpSource;
	
	
	public UserEntity() {}
	
	public UserEntity( Long id ) {
		this.id = id;
	}
	
	
	@Override
	public Long getId() {
		return this.id;
	}
	
	public void setId( Long id ) {
		this.id = id;
	}
	
	@Override 
	public String getFacebookId() {
		return this.facebookId == null ? null : this.facebookId;
	}
	
	@Override
	public void setFacebookId( String facebookId ) {
		this.facebookId = facebookId;
	}
	
	@Override
	public String getPassword() {
		return this.password == null ? null : this.password;
	}
	
	@Override
	public void setPassword( String password ) {
		this.password = password;
	}
	
	
	@Override
	public String getFirstName() {
		return this.firstName;
	}
	
	@Override
	public void setFirstName( String firstName ) {
		this.firstName = firstName;
	}
	
	@Override
	public String getLastName() {
		return this.lastName;
	}
	
	@Override
	public void setLastName( String lastName ) {
		this.lastName = lastName;
	}
	
	@Override
	public String getNickName() {
		return this.nickName;
	}
	
	@Override
	public void setNickName( String nickName ) {
		this.nickName = nickName;
	}
	
	@Override
	public Gender getGender() {
		return this.gender == null ? null : this.gender ;
	}
	
	@Override
	public void setGender( Gender gender ) {
		this.gender = gender;
	}
	
	@Override
	public Date getDateOfBirth() {
		return this.dateOfBirth == null ? null : this.dateOfBirth;
	}
	
	@Override
	public void setDateOfBirth( Date dateOfBirth ) {
		this.dateOfBirth = dateOfBirth;
	}
	
	
	@Override
	public String getEmail() {
		return this.email;
	}
	
	@Override
	public void setEmail( String email ) {
		this.email = email;
	}
	
	@Override
	public String getPhone() {
		return this.phone;
	}
	
	@Override
	public void setPhone( String phone ) {
		this.phone = phone;
	}
	
	
	@SuppressWarnings("deprecation")
	@Override
	public UserState getState() {
		if( state != null )
			return state;
		
		switch( status ) {
			case PRELAUNCH_REFERRAL:
			case POSTLAUNCH_REFERRAL:
				return UserState.REFERRAL;
			case PRELAUNCH_SIGNUP:
			case POSTLAUNCH_SIGNUP:
			case POSTLAUNCH_SIGNUP_SOCIALLOGIN:
			case ANDROID_SIGNUP:
			case ANDROID_SIGNUP_FACEBOOK:
			case ANDROID_SIGNUP_GOOGLE:
				return UserState.REGISTERED;
		}
		
		return UserState.REGISTERED;
	}
	
	@Override
	public void setState( UserState state ) {
		this.state = state;
	}
	
	
	@Override
	public Date getSignUpDate() {
		return this.signUpDate;
	}
	
	public void setSignUpDate( Date signUpDate ) {
		this.signUpDate = signUpDate;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public UserSignUpSource getSignUpSource() {
		if( signUpSource != null )
			return this.signUpSource;
		else if( status == null )
			return null;
		switch( status ) {
			case PRELAUNCH_REFERRAL:
			case PRELAUNCH_SIGNUP:
				return UserSignUpSource.PRE_LAUNCH_WEBSITE;
			case POSTLAUNCH_REFERRAL:
			case POSTLAUNCH_SIGNUP:
				return UserSignUpSource.WEBSITE;
			case POSTLAUNCH_SIGNUP_SOCIALLOGIN:
				return UserSignUpSource.WEBSITE_FACEBOOK;
			case ANDROID_SIGNUP:
				return UserSignUpSource.ANDROID_APP;
			case ANDROID_SIGNUP_FACEBOOK:
				return UserSignUpSource.ANDROID_APP_FACEBOOK;
			case ANDROID_SIGNUP_GOOGLE:
				return UserSignUpSource.ANDROID_APP_GOOGLE;
		}
		
		return null;
	}
	
	@Override
	public void setSignUpSource( UserSignUpSource signUpSource ) {
		this.signUpSource = signUpSource;
	}
	
}
