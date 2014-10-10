package com.claymus.pagecontent.roleaccess;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.Access;
import com.claymus.module.pagecontent.PageContentFactory;
import com.claymus.pagecontent.roleaccess.gae.RoleAccessContentEntity;
import com.pratilipi.commons.server.PratilipiHelper;

public class RoleAccessContentHelper
		implements PageContentFactory<RoleAccessContent, RoleAccessContentProcessor> {
	
	private static final Access ACCESS_TO_LIST =
			new Access( "access_list", false, "View" );
	private static final Access ACCESS_TO_UPDATE =
			new Access( "access_update", false, "Update" );

	
	@Override
	public String getModuleName() {
		return "Role Access";
	}

	@Override
	public Double getModuleVersion() {
		return 3.0;
	}

	@Override
	public Access[] getAccessList() {
		return new Access[] {
				ACCESS_TO_LIST,
				ACCESS_TO_UPDATE };
	}
	
	
	public static RoleAccessContent newAccessContent() {
		return new RoleAccessContentEntity();
	}

	public static boolean hasRequestAccessToList( HttpServletRequest request ) {
		return PratilipiHelper.get( request ).hasUserAccess( ACCESS_TO_LIST );
	}
	
	public static boolean hasRequestAccessToUpdate( HttpServletRequest request ) {
		return PratilipiHelper.get( request ).hasUserAccess( ACCESS_TO_UPDATE );
	}

}
