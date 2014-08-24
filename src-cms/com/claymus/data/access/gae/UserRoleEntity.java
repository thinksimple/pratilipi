package com.claymus.data.access.gae;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.claymus.data.transfer.UserRole;

@PersistenceCapable( table = "USER_ROLE" )
public class UserRoleEntity implements UserRole {
	
	@PrimaryKey
	@Persistent( column = "USER_ROLE_ID" )
	private String id;
	
	@Persistent( column = "USER_ID" )
	private Long userId;
	
	@Persistent( column = "ROLE_ID" )
	private Long roleId;
	
	
	@Override
	public String getId() {
		return id;
	}

	public void setId( String id ) {
		this.id = id;
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
	public Long getRoleId() {
		return roleId;
	}

	@Override
	public void setRoleId( Long roleId ) {
		this.roleId = roleId;
	}

}
