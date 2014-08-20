package com.pratilipi.data.access;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Book;
import com.pratilipi.data.transfer.Genere;
import com.pratilipi.data.transfer.Language;
import com.pratilipi.data.transfer.Publisher;
import com.pratilipi.data.transfer.Tag;
import com.pratilipi.data.transfer.UserBook;
import com.pratilipi.data.transfer.UserBook.ReviewState;

public abstract class DataAccessorTest {

    @Test
    public void testBook() {
    	
    	Long id = null;
    	String title = "title";
    	Long languageId = 123L;
    	Long authorId = 234L;
    	Long publisherId = 345L;
    	Long publicationYear = 456L;
    	Date listingDate = new Date();
    	Long wordCount = 567L;
    	
    	DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
    	
    	Book book = dataAccessor.newBook();
    	book.setTitle( title );
    	book.setLanguageId( languageId );
    	book.setAuthorId( authorId );
    	book.setPublisherId( publisherId );
    	book.setPublicationYear( publicationYear );
    	book.setListingDate( listingDate );
    	book.setWordCount( wordCount );
    	
    	book = dataAccessor.createOrUpdateBook( book );
    	id = book.getId();
    	dataAccessor.destroy();
    	
    	dataAccessor = DataAccessorFactory.getDataAccessor();
    	book = dataAccessor.getBook( id );
    	
    	Assert.assertNotNull( book );
    	Assert.assertEquals( title, book.getTitle() );
    	Assert.assertEquals( languageId, book.getLanguageId() );
    	Assert.assertEquals( authorId, book.getAuthorId() );
    	Assert.assertEquals( publisherId, book.getPublisherId() );
    	Assert.assertEquals( publicationYear, book.getPublicationYear() );
    	Assert.assertEquals( listingDate, book.getListingDate() );
    	Assert.assertEquals( wordCount, book.getWordCount() );
    	
    	dataAccessor.destroy();
    	
    }
    
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
    	Long bookId = 123L;
    	Long rating = 234L;
    	ReviewState reviewState = ReviewState.APPROVED;
    	Date reviewDate = new Date();
    	
    	DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
    	
    	UserBook userBook = dataAccessor.newUserBook();
    	userBook.setUserId( userId );
    	userBook.setBookId( bookId );
    	userBook.setRating( rating );
    	userBook.setReviewState( reviewState );
    	userBook.setReviewDate( reviewDate );
    	
    	userBook = dataAccessor.createOrUpdateUserBook( userBook );
    	dataAccessor.destroy();
    	
    	dataAccessor = DataAccessorFactory.getDataAccessor();
    	userBook = dataAccessor.getUserBook( userId, bookId );
    	
    	Assert.assertNotNull( userBook );
    	Assert.assertEquals( userId, userBook.getUserId() );
    	Assert.assertEquals( bookId, userBook.getBookId() );
    	Assert.assertEquals( rating, userBook.getRating() );
    	Assert.assertEquals( reviewState, userBook.getReviewState() );
    	Assert.assertEquals( reviewDate, userBook.getReviewDate() );
    	
    	dataAccessor.destroy();
    	
    }
    
}
