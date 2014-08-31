package com.claymus.data.access;

import java.util.List;

import com.claymus.data.transfer.BlobEntry;
import com.claymus.data.transfer.EmailTemplate;
import com.claymus.data.transfer.Page;
import com.claymus.data.transfer.PageContent;
import com.claymus.data.transfer.PageLayout;
import com.claymus.data.transfer.Role;
import com.claymus.data.transfer.RoleAccess;
import com.claymus.data.transfer.User;
import com.claymus.data.transfer.UserRole;

public interface DataAccessor {

	User newUser();

	User getUser( Long id );

	User getUserByEmail( String email );

	User createOrUpdateUser( User user );

	
	Role newRole();

	Role getRole( Long id );

	Role createOrUpdateRole( Role role );

	
	UserRole newUserRole();
	
	List<UserRole> getUserRoleList( Long userId );

	UserRole createOrUpdateUserRole( UserRole userRole );

	
	RoleAccess newRoleAccess();
	
	RoleAccess getRoleAccess( String roleId, String accessId );
	
	RoleAccess createOrUpdateRoleAccess( RoleAccess roleAccess );

	
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

	
	EmailTemplate newEmailTemplate();
	
	
	void destroy();
	
}
