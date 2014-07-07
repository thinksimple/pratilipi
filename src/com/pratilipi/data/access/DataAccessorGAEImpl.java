package com.pratilipi.data.access;

import com.pratilipi.data.access.gae.AuthorEntity;
import com.pratilipi.data.access.gae.BookAuthorEntity;
import com.pratilipi.data.access.gae.BookEntity;
import com.pratilipi.data.access.gae.BookGenereEntity;
import com.pratilipi.data.access.gae.BookTagEntity;
import com.pratilipi.data.access.gae.GenereEntity;
import com.pratilipi.data.access.gae.PublisherEntity;
import com.pratilipi.data.access.gae.TagEntity;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Book;
import com.pratilipi.data.transfer.BookAuthor;
import com.pratilipi.data.transfer.BookGenere;
import com.pratilipi.data.transfer.BookTag;
import com.pratilipi.data.transfer.Genere;
import com.pratilipi.data.transfer.Publisher;
import com.pratilipi.data.transfer.Tag;

public class DataAccessorGAEImpl
		extends com.claymus.data.access.DataAccessorGAEImpl
		implements DataAccessor {

	@Override
	public Book newBook() {
		return new BookEntity();
	}

	@Override
	public Book getBook( String isbn ) {
		return getEntity( BookEntity.class, isbn );
	}

	@Override
	public Book createOrUpdateBook( Book book ) {
		return createOrUpdateEntity( book );
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

}
