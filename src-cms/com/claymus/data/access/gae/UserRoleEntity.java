package com.claymus.data.access.gae;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.claymus.data.transfer.UserRole;

@PersistenceCapable( table = "USER_ROLE" )
public class UserRoleEntity implements UserRole {

	@PrimaryKey
	@Persistent( column = "USER_ROLE_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;
	
	@Persistent( column = "NAME" )
	private String name;

	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName( String name ) {
		this.name = name;
	}
	
}
