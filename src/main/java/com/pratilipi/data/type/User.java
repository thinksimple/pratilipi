package com.pratilipi.data.type;

import java.io.Serializable;
import java.util.Date;

import com.pratilipi.common.type.UserSignUpSource;
import com.pratilipi.common.type.UserState;

public interface User extends Serializable {

	Long getId();

	String getPassword();

	void setPassword( String password );

	
	String getFirstName();

	void setFirstName( String firstName );

	String getLastName();

	void setLastName( String lastName );

	String getNickName();

	void setNickName( String nickName );

	
	String getEmail();

	void setEmail( String email );

	String getPhone();

	void setPhone( String phone );
	
	
	Date getDateOfBirth();
	
	void setDateOfBirth( Date dateOfBirth );
	
	String getGender();
	
	void setGender( String gender );
	
	
	UserState getState();

	void setState( UserState userState );

	
	Date getSignUpDate();
	
	void setSignUpDate( Date date );

	UserSignUpSource getSignUpSource();

	void setSignUpSource( UserSignUpSource signUpSource );
	
	
	String getSocialId();
	
	void setSocialId( String socialId );

}
