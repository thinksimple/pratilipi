package com.pratilipi.pagecontent.author;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.Access;
import com.claymus.pagecontent.PageContentHelper;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.pagecontent.author.gae.AuthorContentEntity;
import com.pratilipi.pagecontent.author.shared.AuthorContentData;

public class AuthorContentHelper extends PageContentHelper<
		AuthorContent,
		AuthorContentData,
		AuthorContentProcessor> {
	
	public static final Access ACCESS_TO_ADD_AUTHOR_DATA =
			new Access( "author_data_add", false, "Add Author Data" );
	public static final Access ACCESS_TO_UPDATE_AUTHOR_DATA =
			new Access( "author_data_update", false, "Update Author Data" );
	
	
	@Override
	public String getModuleName() {
		return "Author";
	}

	@Override
	public Double getModuleVersion() {
		return 3.0;
	}

	@Override
	public Access[] getAccessList() {
		return new Access[] {
				ACCESS_TO_ADD_AUTHOR_DATA,
				ACCESS_TO_UPDATE_AUTHOR_DATA
		};
	}
	
	
	public static AuthorContent newAuthorContent( Long authorId ) {
		AuthorContent authorContent = new AuthorContentEntity();
		authorContent.setAuthorId( authorId );
		return authorContent;
	}

	
	public static boolean hasRequestAccessToAddData( HttpServletRequest request ) {
		return PratilipiHelper.get( request ).hasUserAccess( ACCESS_TO_ADD_AUTHOR_DATA );
	}
	
	public static boolean hasRequestAccessToUpdateData(
			HttpServletRequest request, Author author ) {
		
		return PratilipiHelper.get( request ).hasUserAccess( ACCESS_TO_UPDATE_AUTHOR_DATA ) ||
				(
					hasRequestAccessToAddData( request ) &&
					author.getUserId().equals( PratilipiHelper.get( request ).getCurrentUserId() )
				);
	}
		
}
