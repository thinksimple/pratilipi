package com.claymus.data.access;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.claymus.data.transfer.User;
import com.claymus.data.transfer.UserRole;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class DataAccessorTest {

	private final LocalServiceTestHelper helper =
			new LocalServiceTestHelper( new LocalDatastoreServiceTestConfig() );
	
	@Before
    public void setUp() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }
	

    @Test
    public void testUser() {
    	
    	String id = "id";
    	String password = "password";
    	String name = "name";
    	String email = "email";
    	String phone = "phone";
    	Date signUpDate = new Date();
    	
    	DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
    	
    	User user = dataAccessor.newUser();
    	user.setId( id );
    	user.setPassword( password );
    	user.setName( name );
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
    	Assert.assertEquals( name, user.getName() );
    	Assert.assertEquals( email, user.getEmail() );
    	Assert.assertEquals( phone, user.getPhone() );
    	Assert.assertEquals( signUpDate, user.getSignUpDate() );
    	
    	dataAccessor.destroy();
    	
    }
    
    @Test
    public void testUserRole() {
    	
    	Long id = null;
    	String name = "name";
    	
    	DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
    	
    	UserRole userRole = dataAccessor.newUserRole();
    	userRole.setName( name );
    	
    	userRole = dataAccessor.createOrUpdateUserRole( userRole );
    	id = userRole.getId();
    	dataAccessor.destroy();
    	
    	dataAccessor = DataAccessorFactory.getDataAccessor();
    	userRole = dataAccessor.getUserRole( id );
    	
    	Assert.assertNotNull( userRole );
    	Assert.assertEquals( id, userRole.getId() );
    	Assert.assertEquals( name, userRole.getName() );
    	
    	dataAccessor.destroy();
    	
    }
	
}
