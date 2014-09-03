package com.pratilipi.data.access;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.Query;

import com.claymus.data.access.DataListCursorTuple;
import com.claymus.data.access.GaeQueryBuilder;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JDOCursorHelper;
import com.pratilipi.data.access.gae.AuthorEntity;
import com.pratilipi.data.access.gae.BookAuthorEntity;
import com.pratilipi.data.access.gae.BookEntity;
import com.pratilipi.data.access.gae.BookGenreEntity;
import com.pratilipi.data.access.gae.BookTagEntity;
import com.pratilipi.data.access.gae.GenreEntity;
import com.pratilipi.data.access.gae.LanguageEntity;
import com.pratilipi.data.access.gae.PublisherEntity;
import com.pratilipi.data.access.gae.TagEntity;
import com.pratilipi.data.access.gae.UserBookEntity;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Book;
import com.pratilipi.data.transfer.BookAuthor;
import com.pratilipi.data.transfer.BookGenre;
import com.pratilipi.data.transfer.BookTag;
import com.pratilipi.data.transfer.Genre;
import com.pratilipi.data.transfer.Language;
import com.pratilipi.data.transfer.Publisher;
import com.pratilipi.data.transfer.Tag;
import com.pratilipi.data.transfer.UserBook;

public class DataAccessorGaeImpl
		extends com.claymus.data.access.DataAccessorGaeImpl
		implements DataAccessor {

	@Override
	public Book newBook() {
		return new BookEntity();
	}

	@Override
	public Book getBook( Long id ) {
		return getEntity( BookEntity.class, id );
	}

	@Override
	public List<Book> getBookList() {
		Query query =
				new GaeQueryBuilder( pm.newQuery( BookEntity.class ) )
						.build();
		
		@SuppressWarnings("unchecked")
		List<Book> bookEntityList = (List<Book>) query.execute();
		return (List<Book>) pm.detachCopyAll( bookEntityList );
	}
	
	@Override
	public Book createOrUpdateBook( Book book ) {
		return createOrUpdateEntity( book );
	}

	
	@Override
	public Language newLanguage() {
		return new LanguageEntity();
	}

	@Override
	public Language getLanguage( Long id ) {
		return getEntity( LanguageEntity.class, id );
	}

	@Override
	public List<Language> getLanguageList() {
		Query query =
				new GaeQueryBuilder( pm.newQuery( LanguageEntity.class ) )
						.addOrdering( "name", false )
						.build();
		
		@SuppressWarnings("unchecked")
		List<Language> languageEntityList = (List<Language>) query.execute();
		return (List<Language>) pm.detachCopyAll( languageEntityList );
	}
	
	@Override
	public Language createOrUpdateLanguage( Language language ) {
		return createOrUpdateEntity( language );
	}

	
	@Override
	public Author newAuthor() {
		return new AuthorEntity();
	}

	@Override
	public Author getAuthor( Long id ) {
		return getEntity( AuthorEntity.class, id );
	}

	@Override
	public DataListCursorTuple<Author> getAuthorList( String cursorStr, int resultCount ) {

		Query query =
				new GaeQueryBuilder( pm.newQuery( AuthorEntity.class ) )
						.addOrdering( "firstNameEn", true )
						.addOrdering( "lastNameEn", true )
						.addOrdering( "penNameEn", true )
						.setRange( 0, resultCount )
						.build();

		if( cursorStr != null ) {
			Cursor cursor = Cursor.fromWebSafeString( cursorStr );
			Map<String, Object> extensionMap = new HashMap<String, Object>();
			extensionMap.put( JDOCursorHelper.CURSOR_EXTENSION, cursor );
			query.setExtensions(extensionMap);
		}
		
		@SuppressWarnings("unchecked")
		List<Author> authorEntityList = (List<Author>) query.execute();
		Cursor cursor = JDOCursorHelper.getCursor( authorEntityList );
		
		return new DataListCursorTuple<>(
				(List<Author>) pm.detachCopyAll( authorEntityList ),
				cursor.toWebSafeString() );
	
	}
	
	@Override
	public Author createOrUpdateAuthor( Author author ) {
		return createOrUpdateEntity( author );
	}

	
	@Override
	public Publisher newPublisher() {
		return new PublisherEntity();
	}

	@Override
	public Publisher getPublisher( Long id ) {
		return getEntity( PublisherEntity.class, id );
	}
	
	@Override
	public List<Publisher> getPublisherList() {
		Query query =
				new GaeQueryBuilder( pm.newQuery( PublisherEntity.class ) )
						.build();
		
		@SuppressWarnings("unchecked")
		List<Publisher> publisherEntityList = (List<Publisher>) query.execute();
		return (List<Publisher>) pm.detachCopyAll( publisherEntityList );
	}

	@Override
	public Publisher createOrUpdatePublisher( Publisher publisher ) {
		return createOrUpdateEntity( publisher );
	}

	
	@Override
	public Genre newGenre() {
		return new GenreEntity();
	}

	@Override
	public Genre getGenre( Long id ) {
		return getEntity( GenreEntity.class, id );
	}

	@Override
	public List<Genre> getGenreList() {
		Query query =
				new GaeQueryBuilder( pm.newQuery( GenreEntity.class ) )
						.addOrdering( "name", false )
						.build();
		
		@SuppressWarnings("unchecked")
		List<Genre> genreEntityList = (List<Genre>) query.execute();
		return (List<Genre>) pm.detachCopyAll( genreEntityList );
	}
	
	@Override
	public Genre createOrUpdateGenre( Genre genre ) {
		return createOrUpdateEntity( genre );
	}


	@Override
	public Tag newTag() {
		return new TagEntity();
	}

	@Override
	public Tag getTag( Long id ) {
		return getEntity( TagEntity.class, id );
	}

	@Override
	public Tag createOrUpdateTag( Tag tag ) {
		return createOrUpdateEntity( tag );
	}

	
	@Override
	public BookAuthor newBookAuthor() {
		return new BookAuthorEntity();
	}

	@Override
	public BookAuthor createOrUpdateBookAuthor( BookAuthor bookAuthor ) {
		return createOrUpdateEntity( bookAuthor );
	}

	
	@Override
	public BookGenre newBookGenere() {
		return new BookGenreEntity();
	}

	@Override
	public BookGenre createOrUpdateBookGenere( BookGenre bookGenere ) {
		return createOrUpdateEntity( bookGenere );
	}


	@Override
	public BookTag newBookTag() {
		return new BookTagEntity();
	}

	@Override
	public BookTag createOrUpdateBookTag( BookTag bookTag ) {
		return createOrUpdateEntity( bookTag );
	}

	
	@Override
	public UserBook newUserBook() {
		return new UserBookEntity();
	}
	
	@Override
	public UserBook getUserBook( Long userId, Long bookId ) {
		try {
			return getEntity( UserBookEntity.class, userId + "-" + bookId );
		} catch( JDOObjectNotFoundException e ) {
			return null;
		}
	}

	@Override
	public List<UserBook> getUserBookList( Long bookId ) {
		
		Query query =
				new GaeQueryBuilder( pm.newQuery( UserBookEntity.class ) )
						.addFilter( "bookId", bookId )
						.build();
		
		@SuppressWarnings("unchecked")
		List<UserBook> userBookList = (List<UserBook>) query.execute( bookId );
		return (List<UserBook>) pm.detachCopyAll( userBookList );
		
	}

	@Override
	public UserBook createOrUpdateUserBook( UserBook userBook ) {
		( (UserBookEntity) userBook ).setId( userBook.getUserId() + "-" + userBook.getBookId() );
		return createOrUpdateEntity( userBook );
	}
	
}
