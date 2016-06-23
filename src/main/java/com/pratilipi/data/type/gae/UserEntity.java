package com.pratilipi.data.type.gae;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.pratilipi.common.type.UserCampaign;
import com.pratilipi.common.type.UserSignUpSource;
import com.pratilipi.common.type.UserState;
import com.pratilipi.common.type.UserStatus;
import com.pratilipi.data.type.User;

@SuppressWarnings("serial")
@Cache
@Entity( name = "USER" )
public class UserEntity implements User {
	
	@PrimaryKey
	@Persistent( column = "USER_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long USER_ID;
	
	@Persistent( column = "FACEBOOK_ID" )
	private String FACEBOOK_ID;
	
	@Persistent( column = "PASSWORD" )
	private String PASSWORD;
	

	@Persistent( column = "EMAIL" )
	private String EMAIL;
	
	@Persistent( column = "PHONE" )
	private String PHONE;
	
	
	@Persistent( column = "VERIFICATION_TOKEN" )
	private String VERIFICATION_TOKEN;
	
	@Deprecated
	@Persistent( column = "STATUS" )
	private UserStatus STATUS;
	
	@Persistent( column = "STATE" )
	private UserState state;
	
	
	@Persistent( column = "CAMPAIGN" )
	private String CAMPAIGN;
	
	@Persistent( column = "REFERER" )
	private String REFERER;
	
	@Persistent( column = "SIGN_UP_DATE" )
	private Date SIGN_UP_DATE;
	
	@Persistent( column = "SIGN_UP_SOURCE" )
	private UserSignUpSource SIGN_UP_SOURCE;
	
	@Persistent( column = "LAST_UPDATED" )
	private Date LAST_UPDATED;

	
	public UserEntity() {}
	
	public UserEntity( Long id ) {
		this.USER_ID = id;
	}
	
	
	@Override
	public Long getId() {
		return USER_ID;
	}
	
	public void setId( Long id ) {
		this.USER_ID = id;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> Key<T> getKey() {
		return getId() == null ? null : (Key<T>) Key.create( getClass(), getId() );
	}

	@Override
	public <T> void setKey( Key<T> key ) {
		this.USER_ID = key.getId();
	}

	@Override
	public String getFacebookId() {
		return FACEBOOK_ID;
	}
	
	@Override
	public void setFacebookId( String facebookId ) {
		this.FACEBOOK_ID = facebookId;
	}
	
	@Override
	public String getPassword() {
		return PASSWORD;
	}
	
	@Override
	public void setPassword( String password ) {
		this.PASSWORD = password;
	}
	
	
	@Override
	public String getEmail() {
		return EMAIL;
	}
	
	@Override
	public void setEmail( String email ) {
		this.EMAIL = email;
	}
	
	@Override
	public String getPhone() {
		return PHONE;
	}
	
	@Override
	public void setPhone( String phone ) {
		this.PHONE = phone;
	}
	
	
	@Override 
	public String getVerificationToken() {
		return VERIFICATION_TOKEN;
	}
	
	@Override
	public void setVerificationToken( String verificationToken ) {
		this.VERIFICATION_TOKEN = verificationToken;
	}
	
	@Override
	public UserState getState() {

		if( CAMPAIGN != null && CAMPAIGN.equals( "Publisher:5684064812007424" ) ) {
			state = UserState.DELETED;
		} else if( state == null && STATUS != null ) {
			switch( STATUS ) {
				case PRELAUNCH_REFERRAL:
				case POSTLAUNCH_REFERRAL:
					state = UserState.REFERRAL;
					break;
				case PRELAUNCH_SIGNUP:
				case POSTLAUNCH_SIGNUP:
				case POSTLAUNCH_SIGNUP_SOCIALLOGIN:
				case ANDROID_SIGNUP:
				case ANDROID_SIGNUP_FACEBOOK:
				case ANDROID_SIGNUP_GOOGLE:
					state = UserState.REGISTERED;
					break;
			}
		}
		
		return state;
		
	}
	
	@Override
	public void setState( UserState state ) {
		this.state = state;
	}
	
	
	@Override
	public UserCampaign getCampaign() {
		return CAMPAIGN == null ? null : UserCampaign.fromString( CAMPAIGN );
	}
	
	@Override
	public void setCampaign( UserCampaign campaign ) {
		this.CAMPAIGN = campaign == null ? null : campaign.toString();
	}
	
	@Override
	public String getReferrer() {
		return REFERER;
	}
	
	@Override
	public void setReferrer( String referrer ) {
		this.REFERER = referrer;
	}
	
	@Override
	public Date getSignUpDate() {
		return SIGN_UP_DATE;
	}
	
	@Override
	public void setSignUpDate( Date signUpDate ) {
		this.SIGN_UP_DATE = signUpDate;
	}
	
	@Override
	public UserSignUpSource getSignUpSource() {

		if( CAMPAIGN != null && CAMPAIGN.equals( "Publisher:5684064812007424" ) ) {
			SIGN_UP_SOURCE = null;
		} else if( SIGN_UP_SOURCE == null && STATUS != null ) {
			switch( STATUS ) {
				case PRELAUNCH_REFERRAL:
				case PRELAUNCH_SIGNUP:
					SIGN_UP_SOURCE = UserSignUpSource.PRE_LAUNCH_WEBSITE;
					break;
				case POSTLAUNCH_REFERRAL:
				case POSTLAUNCH_SIGNUP:
					SIGN_UP_SOURCE = UserSignUpSource.WEBSITE;
					break;
				case POSTLAUNCH_SIGNUP_SOCIALLOGIN:
					SIGN_UP_SOURCE = UserSignUpSource.WEBSITE_FACEBOOK;
					break;
				case ANDROID_SIGNUP:
					SIGN_UP_SOURCE = UserSignUpSource.ANDROID_APP;
					break;
				case ANDROID_SIGNUP_FACEBOOK:
					SIGN_UP_SOURCE = UserSignUpSource.ANDROID_APP_FACEBOOK;
					break;
				case ANDROID_SIGNUP_GOOGLE:
					SIGN_UP_SOURCE = UserSignUpSource.ANDROID_APP_GOOGLE;
					break;
			}
		}
		
		return SIGN_UP_SOURCE;
		
	}
	
	@Override
	public void setSignUpSource( UserSignUpSource signUpSource ) {
		this.SIGN_UP_SOURCE = signUpSource;
	}
		
	@Override
	public Date getLastUpdated() {
		return LAST_UPDATED == null ? SIGN_UP_DATE : LAST_UPDATED;
	}
	
	@Override
	public void setLastUpdated( Date lastUpdated ) {
		this.LAST_UPDATED = lastUpdated;
	}

}
