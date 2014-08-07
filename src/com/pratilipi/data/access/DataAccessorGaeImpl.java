package com.pratilipi.data.access;

import java.util.List;

import javax.jdo.Query;

import com.claymus.data.access.GaeQueryBuilder;
import com.pratilipi.data.access.gae.AuthorEntity;
import com.pratilipi.data.access.gae.BookAuthorEntity;
import com.pratilipi.data.access.gae.BookEntity;
import com.pratilipi.data.access.gae.BookGenereEntity;
import com.pratilipi.data.access.gae.BookTagEntity;
import com.pratilipi.data.access.gae.GenereEntity;
import com.pratilipi.data.access.gae.LanguageEntity;
import com.pratilipi.data.access.gae.PublisherEntity;
import com.pratilipi.data.access.gae.TagEntity;
import com.pratilipi.data.access.gae.UserBookEntity;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Book;
import com.pratilipi.data.transfer.BookAuthor;
import com.pratilipi.data.transfer.BookGenere;
import com.pratilipi.data.transfer.BookTag;
import com.pratilipi.data.transfer.Genere;
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
	public Publisher createOrUpdatePublisher( Publisher publisher ) {
		return createOrUpdateEntity( publisher );
	}

	
	@Override
	public Genere newGenere() {
		return new GenereEntity();
	}

	@Override
	public Genere getGenere( Long id ) {
		return getEntity( GenereEntity.class, id );
	}

	@Override
	public Genere createOrUpdateGenere( Genere genere ) {
		return createOrUpdateEntity( genere );
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
	public BookGenere newBookGenere() {
		return new BookGenereEntity();
	}

	@Override
	public BookGenere createOrUpdateBookGenere( BookGenere bookGenere ) {
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
	public UserBook getUserBook( String userId, Long bookId ) {
		return getEntity( UserBookEntity.class, userId + "-" + bookId );
	}

	@Override
	public UserBook createOrUpdateUserBook( UserBook userBook ) {
		( (UserBookEntity) userBook ).setId( userBook.getUserId() + "-" + userBook.getBookId() );
		return createOrUpdateEntity( userBook );
	}
	
}
