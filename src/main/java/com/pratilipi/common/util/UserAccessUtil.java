package com.pratilipi.common.util;

import com.pratilipi.common.type.AccessType;

public class UserAccessUtil {

	private enum Role {
		GUEST,
		MEMBER,
		ADMINISTRATOR,
	}
	
	private static Role[] getRoles( long userId ) {
		switch( Long.toString( userId ) ) {
			case "0":
				return new Role[] { Role.GUEST };
			default:
				return new Role[] { Role.MEMBER };
		}
	}

	private static AccessType[] getAccessTypes( Role role ) {
		switch( role ) {
			case ADMINISTRATOR:
				return AccessType.values();
			case MEMBER:
			case GUEST:
			default:
				return new AccessType[] { };
		}
	}

	public static boolean hasUserAccess( Long userId, AccessType access ) {
		Role[] roles = getRoles( userId );
		for( Role role : roles ) {
			AccessType[] accessTypes = getAccessTypes( role );
			for( AccessType accessType : accessTypes ) {
				if( accessType == access )
					return true;
			}
		}
		return false;
	}

}
