package com.pratilipi.pagecontent.authordashboard.gae;

import com.claymus.data.access.gae.PageContentEntity;
import com.pratilipi.pagecontent.authordashboard.AuthorDashboardContent;

@SuppressWarnings("serial")
public class AuthorDashboardContentEntity extends PageContentEntity
		implements AuthorDashboardContent {

	public AuthorDashboardContentEntity( Long authorId ) {
		super.setId( authorId );
	}
	
}
 