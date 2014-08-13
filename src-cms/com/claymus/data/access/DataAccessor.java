package com.claymus.data.access;

import java.util.List;

import com.claymus.data.transfer.BlobEntry;
import com.claymus.data.transfer.Page;
import com.claymus.data.transfer.PageContent;
import com.claymus.data.transfer.PageLayout;
import com.claymus.data.transfer.User;
import com.claymus.data.transfer.Role;

public interface DataAccessor {

	User newUser();

	User getUser( Long id );

	User getUserByEmail( String email );

	User createUser( User user );

	User updateUser( User user );

	
	Role newRole();

	Role getRole( Long id );

	Role createOrUpdateRole( Role role );

	
	BlobEntry newBlobEntry();
	
	BlobEntry getBlobEntry( String name );
	
	BlobEntry createBlobEntry( BlobEntry blobEntry );
	
	
	Page newPage();
	
	Page getPage( String uri );
	
	Page createOrUpdatePage( Page page );
	

	List<PageContent> getPageContentList( Long pageId );

	PageContent createOrUpdatePageContent( PageContent pageContent );
	
	
	PageLayout newPageLayout();
	
	PageLayout getPageLayout( Long id );
	
	PageLayout createOrUpdatePageLayout( PageLayout pageLayout );

	
	void destroy();
	
}
