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
	
	@Persistent( column = "VERIFICATION_TOKEN" )
	private String verificationToken;
	
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
		return id;
	}
	
	public void setId( Long id ) {
		this.id = id;
	}
	
	@Override 
	public String getFacebookId() {
		return facebookId;
	}
	
	@Override
	public void setFacebookId( String facebookId ) {
		this.facebookId = facebookId;
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
	public String getFirstName() {
		return firstName;
	}
	
	@Override
	public void setFirstName( String firstName ) {
		this.firstName = firstName;
	}
	
	@Override
	public String getLastName() {
		return lastName;
	}
	
	@Override
	public void setLastName( String lastName ) {
		this.lastName = lastName;
	}
	
	@Override
	public String getNickName() {
		return nickName;
	}
	
	@Override
	public void setNickName( String nickName ) {
		this.nickName = nickName;
	}
	
	
	@Override
	public Gender getGender() {
		return gender ;
	}
	
	@Override
	public void setGender( Gender gender ) {
		this.gender = gender;
	}
	
	@Override
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	
	@Override
	public void setDateOfBirth( Date dateOfBirth ) {
		this.dateOfBirth = dateOfBirth;
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
	public String getVerificationToken() {
		return verificationToken;
	}
	
	@Override
	public void setVerificationToken( String verificationToken ) {
		this.verificationToken = verificationToken;
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
		return signUpDate;
	}
	
	public void setSignUpDate( Date signUpDate ) {
		this.signUpDate = signUpDate;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public UserSignUpSource getSignUpSource() {
		if( signUpSource != null )
			return signUpSource;
		
		if( status == null )
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
