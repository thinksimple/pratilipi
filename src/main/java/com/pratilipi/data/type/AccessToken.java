package com.pratilipi.data.type;

import java.util.Date;

public interface AccessToken extends GenericOfyType {

	String getId();
	
	Long getUserId();
	
	void setUserId( Long userId );

	String getFcmToken();
	
	void setFcmToken( String fcmToken );
	
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
