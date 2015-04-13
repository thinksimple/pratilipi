package com.pratilipi.pagecontent.authordashboard;

import com.claymus.pagecontent.PageContentHelper;
import com.pratilipi.pagecontent.authordashboard.gae.AuthorDashboardContentEntity;
import com.pratilipi.pagecontent.authordashboard.shared.AuthorDashboardContentData;

public class AuthorDashboardContentHelper extends PageContentHelper<
		AuthorDashboardContent,
		AuthorDashboardContentData,
		AuthorDashboardContentProcessor> {
	
	@Override
	public String getModuleName() {
		return "Author Dashboard";
	}

	@Override
	public Double getModuleVersion() {
		return 5.3;
	}

	
	public static AuthorDashboardContent newAuthorDashboardContent( Long authorId ) {
		return new AuthorDashboardContentEntity( authorId );
	}

}
