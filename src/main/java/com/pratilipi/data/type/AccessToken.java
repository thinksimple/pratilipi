package com.pratilipi.data.type;

import java.io.Serializable;
import java.util.Date;

import com.pratilipi.common.type.AccessTokenType;

public interface AccessToken extends Serializable {

	String getId();
	
	Long getUserId();
	
	void setUserId( Long userId );
	
	AccessTokenType getType();
	
	void setType( AccessTokenType accessTokenType );
	
	Date getLogInDate();
	
	void setLogInDate( Date logInDate );
	
	Date getLogOutDate();
	
	void setLogOutDate( Date logOutDate );
	
	Date getExpiry();
	
	void setExpiry( Date expiry );
	
	boolean isExpired();
	
	Date getCreationDate();
	
	void setCreationDate( Date creationDate );
	
}
