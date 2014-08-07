package com.pratilipi.data.access;

import java.util.List;

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


public interface DataAccessor extends com.claymus.data.access.DataAccessor {
	
	Book newBook();

	Book getBook( Long id );
	
	List<Book> getBookList();

	Book createOrUpdateBook( Book book );

	
	Language newLanguage();

	Language getLanguage( Long id );

	Language createOrUpdateLanguage( Language language );

	
	Author newAuthor();

	Author getAuthor( Long id );

	Author createOrUpdateAuthor( Author author );

	
	Publisher newPublisher();

	Publisher getPublisher( Long id );

	Publisher createOrUpdatePublisher( Publisher publisher );

	
	Genere newGenere();

	Genere getGenere( Long id );

	Genere createOrUpdateGenere( Genere genere );


	Tag newTag();

	Tag getTag( Long id );

	Tag createOrUpdateTag( Tag tag );
	

	BookAuthor newBookAuthor();

	BookAuthor createOrUpdateBookAuthor( BookAuthor bookAuthor );
	

	BookGenere newBookGenere();

	BookGenere createOrUpdateBookGenere( BookGenere bookGenere );
	

	BookTag newBookTag();

	BookTag createOrUpdateBookTag( BookTag bookTag );
	
	
	UserBook newUserBook();

	UserBook getUserBook( String userId, Long bookId );

	UserBook createOrUpdateUserBook( UserBook userBook );

}
