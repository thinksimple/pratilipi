package com.claymus.data.access;

import com.claymus.data.transfer.BlobEntry;
import com.claymus.data.transfer.User;
import com.claymus.data.transfer.UserRole;

public interface DataAccessor {

	User newUser();

	User getUser( String id );

	User createUser( User user );

	User updateUser( User user );

	
	UserRole newUserRole();

	UserRole getUserRole( Long id );

	UserRole createOrUpdateUserRole( UserRole userRole );

	
	BlobEntry newBlobEntry();
	
	BlobEntry getBlobEntry( String name );
	
	BlobEntry createBlobEntry( BlobEntry blobEntry );
	
	
	void destroy();
	
}
