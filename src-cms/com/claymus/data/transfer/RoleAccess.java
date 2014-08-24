package com.claymus.data.transfer;

public interface RoleAccess {

	String getId();
	
	Long getRoleId();

	void setRoleId( Long id );
	
	String getAccessId();

	void setAccessId( String id );
	
}
