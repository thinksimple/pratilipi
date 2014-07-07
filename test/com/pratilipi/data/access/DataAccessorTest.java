package com.pratilipi.data.access;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.pratilipi.common.Language;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Book;
import com.pratilipi.data.transfer.Genere;
import com.pratilipi.data.transfer.Publisher;
import com.pratilipi.data.transfer.Tag;
import com.pratilipi.data.transfer.UserBook;
import com.pratilipi.data.transfer.UserBook.ReviewState;

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
    public void testBook() {
    	
    	String isbn = "isbn";
    	String title = "title";
    	Language language = Language.IN_HINDI;
    	Long authorId = 123L;
    	Long publisherId = 456L;
    	Date publicationDate = new Date();
    	Date listingDate = new Date();
    	Long wordCount = 789L;
    	
    	DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
    	
    	Book book = dataAccessor.newBook();
    	book.setIsbn( isbn );
    	book.setTitle( title );
    	book.setLanguage( language );
    	book.setAuthorId( authorId );
    	book.setPublisherId( publisherId );
    	book.setPublicationDate( publicationDate );
    	book.setListingDate( listingDate );
    	book.setWordCount( wordCount );
    	
    	book = dataAccessor.createOrUpdateBook( book );
    	dataAccessor.destroy();
    	
    	dataAccessor = DataAccessorFactory.getDataAccessor();
    	book = dataAccessor.getBook( isbn );
    	
    	Assert.assertNotNull( book );
    	Assert.assertEquals( isbn, book.getIsbn() );
    	Assert.assertEquals( title, book.getTitle() );
    	Assert.assertEquals( language, book.getLanguage() );
    	Assert.assertEquals( authorId, book.getAuthorId() );
    	Assert.assertEquals( publisherId, book.getPublisherId() );
    	Assert.assertEquals( publicationDate, book.getPublicationDate() );
    	Assert.assertEquals( listingDate, book.getListingDate() );
    	Assert.assertEquals( wordCount, book.getWordCount() );
    	
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
    	String firstName = "firstName";
    	String lastName = "lastName";
    	String email = "email";
    	Date registrationDate = new Date();
    	
    	DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
    	
    	Publisher publisher = dataAccessor.newPublisher();
    	publisher.setFirstName( firstName );
    	publisher.setLastName( lastName );
    	publisher.setEmail( email );
    	publisher.setRegistrationDate( registrationDate );
    	
    	publisher = dataAccessor.createOrUpdatePublisher( publisher );
    	id = publisher.getId();
    	dataAccessor.destroy();
    	
    	dataAccessor = DataAccessorFactory.getDataAccessor();
    	publisher = dataAccessor.getPublisher( id );
    	
    	Assert.assertNotNull( publisher );
    	Assert.assertEquals( firstName, publisher.getFirstName() );
    	Assert.assertEquals( lastName, publisher.getLastName() );
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
    	
    	Genere genere = dataAccessor.newGenere();
    	genere.setName( name );
    	genere.setCreationDate( creationDate );
    	
    	genere = dataAccessor.createOrUpdateGenere( genere );
    	id = genere.getId();
    	dataAccessor.destroy();
    	
    	dataAccessor = DataAccessorFactory.getDataAccessor();
    	genere = dataAccessor.getGenere( id );
    	
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
    	
    	String userId = "userId";
    	String isbn = "isbn";
    	Long rating = 123L;
    	ReviewState reviewState = ReviewState.APPROVED;
    	Date reviewDate = new Date();
    	
    	DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
    	
    	UserBook userBook = dataAccessor.newUserBook();
    	userBook.setUserId( userId );
    	userBook.setIsbn( isbn );
    	userBook.setRating( rating );
    	userBook.setReviewState( reviewState );
    	userBook.setReviewDate( reviewDate );
    	
    	userBook = dataAccessor.createOrUpdateUserBook( userBook );
    	dataAccessor.destroy();
    	
    	dataAccessor = DataAccessorFactory.getDataAccessor();
    	userBook = dataAccessor.getUserBook( userId, isbn );
    	
    	Assert.assertNotNull( userBook );
    	Assert.assertEquals( userId, userBook.getUserId() );
    	Assert.assertEquals( isbn, userBook.getIsbn() );
    	Assert.assertEquals( rating, userBook.getRating() );
    	Assert.assertEquals( reviewState, userBook.getReviewState() );
    	Assert.assertEquals( reviewDate, userBook.getReviewDate() );
    	
    	dataAccessor.destroy();
    	
    }
    
}
