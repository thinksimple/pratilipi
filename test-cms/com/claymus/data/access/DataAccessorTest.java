package com.claymus.data.access;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.claymus.data.transfer.Page;
import com.claymus.data.transfer.PageLayout;
import com.claymus.data.transfer.Role;
import com.claymus.data.transfer.User;

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
    	
    	user = dataAccessor.createOrUpdateUser( user );
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
