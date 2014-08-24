package com.claymus.data.transfer;

public interface RoleAccess {

	String getId();
	
	String getRoleId();

	void setRoleId( String id );
	
	String getAccessId();

	void setAccessId( String id );

	boolean hasAccess();
	
	void setAccess( boolean access );
	
}
