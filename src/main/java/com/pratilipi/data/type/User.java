package com.pratilipi.data.type;

import java.io.Serializable;
import java.util.Date;

import com.pratilipi.common.type.Gender;
import com.pratilipi.common.type.UserCampaign;
import com.pratilipi.common.type.UserSignUpSource;
import com.pratilipi.common.type.UserState;

public interface User extends Serializable {
	
	Long getId();
	
	String getFacebookId();
	
	void setFacebookId( String facebookId );
	
	String getPassword();
	
	void setPassword( String password );
	
	
	@Deprecated
	String getFirstName();
	
	@Deprecated
	void setFirstName( String firstName );
	
	@Deprecated
	String getLastName();
	
	@Deprecated
	void setLastName( String lastName );
	
	@Deprecated
	String getNickName();
	
	@Deprecated
	void setNickName( String nickName );
	
	
	@Deprecated
	Gender getGender();
	
	@Deprecated
	void setGender( Gender gender );
	
	@Deprecated
	Date getDateOfBirth();
	
	@Deprecated
	void setDateOfBirth( Date dateOfBirth );
	
	
	String getEmail();
	
	void setEmail( String email );
	
	String getPhone();
	
	void setPhone( String phone );
	
	
	String getVerificationToken();
	
	void setVerificationToken( String verificationToken );
	
	UserState getState();
	
	void setState( UserState userState );
	
	
	UserCampaign getCampaign();
	
	void setCampaign( UserCampaign campaign );
	
	String getReferrer();
	
	void setReferrer( String referrer );
	
	Date getSignUpDate();
	
	void setSignUpDate( Date date );
	
	UserSignUpSource getSignUpSource();
	
	void setSignUpSource( UserSignUpSource signUpSource );
	
	Date getLastUpdated();
	
	void setLastUpdated( Date lastUpdated );
	
}
