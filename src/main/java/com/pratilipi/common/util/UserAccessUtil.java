package com.pratilipi.common.util;

import com.pratilipi.common.type.AccessType;
import com.pratilipi.common.type.Language;

public class UserAccessUtil {
	
	private static final AccessType[] ADMIN_ACCESS = {
			AccessType.PRATILIPI_LIST, AccessType.PRATILIPI_ADD, AccessType.PRATILIPI_UPDATE, AccessType.PRATILIPI_READ_META, AccessType.PRATILIPI_READ_CONTENT,
			AccessType.AUTHOR_LIST, AccessType.AUTHOR_ADD, AccessType.AUTHOR_UPDATE,
			AccessType.EVENT_ADD, AccessType.EVENT_UPDATE };

	private enum Role {

		GUEST			( null ),
		MEMBER			( null,					AccessType.PRATILIPI_ADD_REVIEW ),

		ADMIN			( null,					AccessType.PRATILIPI_LIST ),
		ADMIN_BENGALI	( Language.BENGALI,		ADMIN_ACCESS ),
		ADMIN_HINDI		( Language.HINDI,		ADMIN_ACCESS ),
		ADMIN_GUJARATI	( Language.GUJARATI,	ADMIN_ACCESS ),
		ADMIN_MALAYALAM	( Language.MALAYALAM,	ADMIN_ACCESS ),
		ADMIN_MARATHI	( Language.MARATHI,		ADMIN_ACCESS ),
		ADMIN_TAMIL		( Language.TAMIL,		ADMIN_ACCESS ),
		ADMIN_TELUGU	( Language.TELUGU,		ADMIN_ACCESS ),
		
		ADMINISTRATOR	( null,					AccessType.values() );

		
		private Language language;
		private AccessType[] accessTypes;
		
		
		private Role( Language language, AccessType ...accessTypes ) {
			this.language = language;
			this.accessTypes = accessTypes;
		}

		
		public boolean hasAccess( Language language, AccessType accessType ) {
			if( this.language != null && this.language != language )
				return false;
			if( this.accessTypes == null )
				return false;
			for( AccessType at : accessTypes )
				if( accessType == at )
					return true;
			return false;
		}
		
	}
	
	private static Role[] getRoles( Long userId ) { // TODO: Migrate this data to DataStore/CloudStore
		switch( userId.toString() ) {
			case "0":
				return new Role[] { Role.GUEST };
				
			case "6243664397336576": // moumita@
				return new Role[] { Role.MEMBER, Role.ADMIN, Role.ADMIN_BENGALI };
			case "5644707593977856": // nimisha@
				return new Role[] { Role.MEMBER, Role.ADMIN, Role.ADMIN_GUJARATI };
			case "4790800105865216": // veena@
				return new Role[] { Role.MEMBER, Role.ADMIN, Role.ADMIN_HINDI };
			case "4900189601005568": // vrushali@
				return new Role[] { Role.MEMBER, Role.ADMIN, Role.ADMIN_MARATHI };
			case "5664902681198592": // shally@
				return new Role[] { Role.MEMBER, Role.ADMIN, Role.ADMIN_BENGALI, Role.ADMIN_HINDI, Role.ADMIN_GUJARATI, Role.ADMIN_MARATHI };
			case "5743817900687360": // jitesh@
				return new Role[] { Role.MEMBER, Role.ADMIN, Role.ADMIN_BENGALI, Role.ADMIN_HINDI, Role.ADMIN_GUJARATI, Role.ADMIN_MARATHI };

			case "4900071594262528": // dileepan@
				return new Role[] { Role.MEMBER, Role.ADMIN, Role.ADMIN_TAMIL };
			case "5674672871964672": // krithiha@
				return new Role[] { Role.MEMBER, Role.ADMIN, Role.ADMIN_TAMIL };
			case "5991416564023296": // sankar@
				return new Role[] { Role.MEMBER, Role.ADMIN, Role.ADMIN_MALAYALAM, Role.ADMIN_TAMIL };

			case "6196244602945536": // raghu@
				return new Role[] { Role.MEMBER, Role.ADMIN, Role.ADMIN_TAMIL };
			case "5705241014042624": // prashant@
				return new Role[] { Role.MEMBER, Role.ADMIN, Role.ADMIN_HINDI };
				
			case "5156503382130688" : // babu@
				return new Role[] { Role.MEMBER, Role.ADMIN, Role.ADMIN_TELUGU };
				
			case "5666355716030464" : // vaisakh@
				return new Role[] { Role.MEMBER, Role.ADMIN, Role.ADMIN_MALAYALAM };
			
			default:
				return new Role[] { Role.MEMBER };
		}
	}

	public static boolean hasUserAccess( Long userId, Language language, AccessType access ) {
		for( Role role : getRoles( userId ) )
			if( role.hasAccess( language, access ) )
				return true;
		return false;
	}

}
