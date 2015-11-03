package com.pratilipi.data.type.gae;

import java.util.Date;
import java.util.UUID;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.pratilipi.data.type.AccessToken;

@PersistenceCapable( table = "ACCESS_TOKEN" )
public class AccessTokenEntity implements AccessToken {

	private static final long serialVersionUID = 4669082442894372545L;

	@PrimaryKey
	@Persistent( column = "ACCESS_TOKEN_ID" )
	private String id;
	
	@Persistent( column = "PARENT_ACCESS_TOKEN_ID" )
	private String parentId;
	
	@Persistent( column = "USER_ID" )
	private Long userId;
	
	@Deprecated
	@Persistent( column = "PUBLISHER_ID" )
	private Long publisherId;

	@Deprecated
	@Persistent( column = "ACCESS_TOKEN_TYPE" )
	private String type;

	@Persistent( column = "LOGIN_DATE" )
	private Date logInDate;
	
	@Persistent( column = "LOGOUT_DATE" )
	private Date logOutDate;

	@Persistent( column = "EXPIRY" )
	private Date expiry;

	@Persistent( column = "CREATION_DATE" )
	private Date creationDate;

	
	public AccessTokenEntity() {
		this.id = UUID.randomUUID().toString();
	}
	
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getParentId() {
		return parentId;
	}

	@Override
	public void setParentId( String parentId ) {
		this.parentId = parentId;
	}

	@Override
	public Long getUserId() {
		return userId;
	}

	@Override
	public void setUserId( Long userId ) {
		this.userId = userId;
	}

	@Override
	public Date getLogInDate() {
		return logInDate;
	}
	
	@Override
	public void setLogInDate( Date logInDate ) {
		this.logInDate = logInDate;
	}
	
	@Override
	public Date getLogOutDate() {
		return logOutDate;
	}
	
	@Override
	public void setLogOutDate( Date logOutDate ) {
		this.logOutDate = logOutDate;
	}
	
	@Override
	public Date getExpiry() {
		return expiry;
	}

	@Override
	public void setExpiry( Date expiry ) {
		this.expiry = expiry;
	}

	@Override
	public boolean isExpired() {
		return expiry.before( new Date() );
	}

	@Override
	public Date getCreationDate() {
		return creationDate;
	}

	@Override
	public void setCreationDate( Date creationDate ) {
		this.creationDate = creationDate;
	}
	
}
