package com.pratilipi.data.type;

import java.io.Serializable;
import java.util.Date;

public interface AccessToken extends Serializable {

	String getId();
	
	String getParentId();

	void setParentId( String parentId );

	Long getUserId();
	
	void setUserId( Long userId );

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
