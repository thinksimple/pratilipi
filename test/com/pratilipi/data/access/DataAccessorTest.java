package com.pratilipi.data.access;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.pratilipi.commons.shared.UserReviewState;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Book;
import com.pratilipi.data.transfer.Genre;
import com.pratilipi.data.transfer.Language;
import com.pratilipi.data.transfer.Publisher;
import com.pratilipi.data.transfer.Tag;
import com.pratilipi.data.transfer.UserPratilipi;

public abstract class DataAccessorTest {

    @Test
    public void testLanguage() {
    	
    	Long id = null;
    	String name = "name";
    	
    	DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

    	Language language = dataAccessor.newLanguage();
    	language.setName( name );
    	
    	language = dataAccessor.createOrUpdateLanguage( language );
    	id = language.getId();
    	dataAccessor.destroy();
    	
    	dataAccessor = DataAccessorFactory.getDataAccessor();
    	language = dataAccessor.getLanguage( id );
    	
    	Assert.assertNotNull( language );
    	Assert.assertEquals( name, language.getName() );
    	
    	dataAccessor.destroy();
    	
    }
    
    @Test
    public void testAuthor() {
    	
    	Long id = null;
    	String firstName = "firstName";
    	String lastName = "lastName";
    	String email = "email";
    	Date registrationDate = new Date();
    	
    	DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
    	
    	Author author = dataAccessor.newAuthor();
    	author.setFirstName( firstName );
    	author.setLastName( lastName );
    	author.setEmail( email );
    	author.setRegistrationDate( registrationDate );
    	
    	author = dataAccessor.createOrUpdateAuthor( author );
    	id = author.getId();
    	dataAccessor.destroy();
    	
    	dataAccessor = DataAccessorFactory.getDataAccessor();
    	author = dataAccessor.getAuthor( id );
    	
    	Assert.assertNotNull( author );
    	Assert.assertEquals( firstName, author.getFirstName() );
    	Assert.assertEquals( lastName, author.getLastName() );
    	Assert.assertEquals( email, author.getEmail() );
    	Assert.assertEquals( registrationDate, author.getRegistrationDate() );
    	
    	dataAccessor.destroy();
    	
    }
    
    @Test
    public void testPublisher() {
    	
    	Long id = null;
    	String name = "name";
    	String email = "email";
    	Date registrationDate = new Date();
    	
    	DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
    	
    	Publisher publisher = dataAccessor.newPublisher();
    	publisher.setName( name );
    	publisher.setEmail( email );
    	publisher.setRegistrationDate( registrationDate );
    	
    	publisher = dataAccessor.createOrUpdatePublisher( publisher );
    	id = publisher.getId();
    	dataAccessor.destroy();
    	
    	dataAccessor = DataAccessorFactory.getDataAccessor();
    	publisher = dataAccessor.getPublisher( id );
    	
    	Assert.assertNotNull( publisher );
    	Assert.assertEquals( name, publisher.getName() );
    	Assert.assertEquals( email, publisher.getEmail() );
    	Assert.assertEquals( registrationDate, publisher.getRegistrationDate() );
    	
    	dataAccessor.destroy();
    	
    }
    
    @Test
    public void testGenere() {
    	
    	Long id = null;
    	String name = "name";
    	Date creationDate = new Date();
    	
    	DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
    	
    	Genre genere = dataAccessor.newGenre();
    	genere.setName( name );
    	genere.setCreationDate( creationDate );
    	
    	genere = dataAccessor.createOrUpdateGenre( genere );
    	id = genere.getId();
    	dataAccessor.destroy();
    	
    	dataAccessor = DataAccessorFactory.getDataAccessor();
    	genere = dataAccessor.getGenre( id );
    	
    	Assert.assertNotNull( genere );
    	Assert.assertEquals( name, genere.getName() );
    	Assert.assertEquals( creationDate, genere.getCreationDate() );
    	
    	dataAccessor.destroy();
    	
    }
    
    @Test
    public void testTag() {
    	
    	Long id = null;
    	String name = "name";
    	Date creationDate = new Date();
    	
    	DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
    	
    	Tag tag = dataAccessor.newTag();
    	tag.setName( name );
    	tag.setCreationDate( creationDate );
    	
    	tag = dataAccessor.createOrUpdateTag( tag );
    	id = tag.getId();
    	dataAccessor.destroy();
    	
    	dataAccessor = DataAccessorFactory.getDataAccessor();
    	tag = dataAccessor.getTag( id );
    	
    	Assert.assertNotNull( tag );
    	Assert.assertEquals( name, tag.getName() );
    	Assert.assertEquals( creationDate, tag.getCreationDate() );
    	
    	dataAccessor.destroy();
    	
    }
    
    @Test
    public void testUserBook() {
    	
    	Long userId = 1223L;
    	Long bookId = 123L;
    	Long rating = 234L;
    	UserReviewState reviewState = UserReviewState.APPROVED;
    	Date reviewDate = new Date();
    	
    	DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
    	
    	UserPratilipi userBook = dataAccessor.newUserPratilipi();
    	userBook.setUserId( userId );
    	userBook.setPratilipiId( bookId );
    	userBook.setRating( rating );
    	userBook.setReviewState( reviewState );
    	userBook.setReviewDate( reviewDate );
    	
    	userBook = dataAccessor.createOrUpdateUserBook( userBook );
    	dataAccessor.destroy();
    	
    	dataAccessor = DataAccessorFactory.getDataAccessor();
    	userBook = dataAccessor.getUserPratilipi( userId, bookId );
    	
    	Assert.assertNotNull( userBook );
    	Assert.assertEquals( userId, userBook.getUserId() );
    	Assert.assertEquals( bookId, userBook.getPratilipiId() );
    	Assert.assertEquals( rating, userBook.getRating() );
    	Assert.assertEquals( reviewState, userBook.getReviewState() );
    	Assert.assertEquals( reviewDate, userBook.getReviewDate() );
    	
    	dataAccessor.destroy();
    	
    }
    
}
