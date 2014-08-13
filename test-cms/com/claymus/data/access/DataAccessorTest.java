package com.claymus.data.access;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.claymus.data.transfer.BlobEntry;
import com.claymus.data.transfer.BlobEntry.Source;
import com.claymus.data.transfer.BlobEntry.Type;
import com.claymus.data.transfer.Page;
import com.claymus.data.transfer.PageLayout;
import com.claymus.data.transfer.User;
import com.claymus.data.transfer.Role;

public abstract class DataAccessorTest {

    @Test
    public void testUser() {
    	
    	Long id = 123L;
    	String password = "password";
    	String firstName = "firstName";
    	String lastName = "lastName";
    	String nickName = "nickName";
    	String email = "email";
    	String phone = "phone";
    	Date signUpDate = new Date();
    	
    	DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
    	
    	User user = dataAccessor.newUser();
    	user.setId( id );
    	user.setPassword( password );
    	user.setFirstName( firstName );
    	user.setLastName( lastName );
    	user.setNickName( nickName );
    	user.setEmail( email );
    	user.setPhone( phone );
    	user.setSignUpDate( signUpDate );
    	
    	user = dataAccessor.createUser( user );
    	dataAccessor.destroy();
    	
    	dataAccessor = DataAccessorFactory.getDataAccessor();
    	user = dataAccessor.getUser( id );
    	
    	Assert.assertNotNull( user );
    	Assert.assertEquals( id, user.getId() );
    	Assert.assertEquals( password, user.getPassword() );
    	Assert.assertEquals( firstName, user.getFirstName() );
    	Assert.assertEquals( lastName, user.getLastName() );
    	Assert.assertEquals( nickName, user.getNickName() );
    	Assert.assertEquals( email, user.getEmail() );
    	Assert.assertEquals( phone, user.getPhone() );
    	Assert.assertEquals( signUpDate, user.getSignUpDate() );
    	
    	dataAccessor.destroy();
    	
    }
    
    @Test
    public void testRole() {
    	
    	Long id = null;
    	String name = "name";
    	
    	DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
    	
    	Role userRole = dataAccessor.newRole();
    	userRole.setName( name );
    	
    	userRole = dataAccessor.createOrUpdateRole( userRole );
    	id = userRole.getId();
    	dataAccessor.destroy();
    	
    	dataAccessor = DataAccessorFactory.getDataAccessor();
    	userRole = dataAccessor.getRole( id );
    	
    	Assert.assertNotNull( userRole );
    	Assert.assertEquals( id, userRole.getId() );
    	Assert.assertEquals( name, userRole.getName() );
    	
    	dataAccessor.destroy();
    	
    }
    
    @Test
    public void testBlobEntry() {

    	String name = "name";
    	
    	Type type_1 = Type.TXT;
    	Long size_1 = 123L;
    	Source source_1 = Source.GOOGLE_APP_ENGINE;
    	String blobId_1 = "bolbId_1";
    	Date creationDate_1 = new Date();
    	
    	Type type_2 = Type.PDF;
    	Long size_2 = 456L;
    	Source source_2 = Source.GOOGLE_CLOUD_STORAGE;
    	String blobId_2 = "bolbId_2";
    	Date creationDate_2 = new Date( new Date().getTime() + 100 );
    	
    	DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

    	BlobEntry blobEntry = dataAccessor.newBlobEntry();
    	blobEntry.setName( name );
    	blobEntry.setType( type_1 );
    	blobEntry.setSize( size_1 );
    	blobEntry.setSource( source_1 );
    	blobEntry.setBlobId( blobId_1 );
    	blobEntry.setCreationDate( creationDate_1 );
    	
    	blobEntry = dataAccessor.createBlobEntry( blobEntry );

    	blobEntry = dataAccessor.newBlobEntry();
    	blobEntry.setName( name );
    	blobEntry.setType( type_2 );
    	blobEntry.setSize( size_2 );
    	blobEntry.setSource( source_2 );
    	blobEntry.setBlobId( blobId_2 );
    	blobEntry.setCreationDate( creationDate_2 );
    	
    	blobEntry = dataAccessor.createBlobEntry( blobEntry );
    	dataAccessor.destroy();

    	dataAccessor = DataAccessorFactory.getDataAccessor();
    	blobEntry = dataAccessor.getBlobEntry( name );
    	
    	Assert.assertNotNull( blobEntry );
    	Assert.assertEquals( name, blobEntry.getName() );
    	Assert.assertEquals( type_2, blobEntry.getType() );
    	Assert.assertEquals( size_2, blobEntry.getSize() );
    	Assert.assertEquals( source_2, blobEntry.getSource() );
    	Assert.assertEquals( blobId_2, blobEntry.getBlobId() );
    	Assert.assertEquals( creationDate_2, blobEntry.getCreationDate() );
    	
    	dataAccessor.destroy();
    	
    }
	
    @Test
    public void testPage() {
    	
    	Long id;
    	String uri = "uri";
    	String title = "title";
    	Long layoutId = 123L;
    	Date creationDate = new Date();
    	
    	DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

    	Page page = dataAccessor.newPage();
    	page.setUri( uri );
    	page.setTitle( title );
    	page.setLayout( layoutId );
    	page.setCreationDate( creationDate );
    	
    	page = dataAccessor.createOrUpdatePage( page );
    	id = page.getId();
    	dataAccessor.destroy();

    	dataAccessor = DataAccessorFactory.getDataAccessor();
    	page = dataAccessor.getPage( uri );
    	
    	Assert.assertNotNull( page );
    	Assert.assertEquals( id, page.getId() );
    	Assert.assertEquals( uri, page.getUri() );
    	Assert.assertEquals( title, page.getTitle() );
    	Assert.assertEquals( layoutId, page.getLayoutId() );
    	Assert.assertEquals( creationDate, page.getCreationDate() );
    	
    	dataAccessor.destroy();
    	
    }
    
    @Test
    public void testPageLayout() {
    	
    	Long id;
    	String name = "name";
    	String template = "template";
    	
    	DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

    	PageLayout pageLayout = dataAccessor.newPageLayout();
    	pageLayout.setName( name );
    	pageLayout.setTemplate( template );
    	
    	pageLayout = dataAccessor.createOrUpdatePageLayout( pageLayout );
    	id = pageLayout.getId();
    	dataAccessor.destroy();

    	dataAccessor = DataAccessorFactory.getDataAccessor();
    	pageLayout = dataAccessor.getPageLayout( id );
    	
    	Assert.assertNotNull( pageLayout );
    	Assert.assertEquals( id, pageLayout.getId() );
    	Assert.assertEquals( name, pageLayout.getName() );
    	Assert.assertEquals( template, pageLayout.getTemplate() );
    	
    	dataAccessor.destroy();
    	
    }
}
