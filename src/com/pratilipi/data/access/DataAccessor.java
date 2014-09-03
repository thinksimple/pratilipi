package com.pratilipi.data.access;

import java.util.List;

import com.claymus.data.access.DataListCursorTuple;
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


public interface DataAccessor extends com.claymus.data.access.DataAccessor {
	
	Book newBook();

	Book getBook( Long id );
	
	List<Book> getBookList();

	Book createOrUpdateBook( Book book );

	
	Language newLanguage();

	Language getLanguage( Long id );

	List<Language> getLanguageList();

	Language createOrUpdateLanguage( Language language );

	
	Author newAuthor();

	Author getAuthor( Long id );

	DataListCursorTuple<Author> getAuthorList( String cursor, int resultCount );

	Author createOrUpdateAuthor( Author author );

	
	Publisher newPublisher();

	Publisher getPublisher( Long id );
	
	List<Publisher> getPublisherList();

	Publisher createOrUpdatePublisher( Publisher publisher );

	
	Genre newGenre();

	Genre getGenre( Long id );

	List<Genre> getGenreList();

	Genre createOrUpdateGenre( Genre genre );


	Tag newTag();

	Tag getTag( Long id );

	Tag createOrUpdateTag( Tag tag );
	

	BookAuthor newBookAuthor();

	BookAuthor createOrUpdateBookAuthor( BookAuthor bookAuthor );
	

	BookGenre newBookGenere();

	BookGenre createOrUpdateBookGenere( BookGenre bookGenere );
	

	BookTag newBookTag();

	BookTag createOrUpdateBookTag( BookTag bookTag );
	
	
	UserBook newUserBook();
	
	UserBook getUserBook(Long userId, Long bookId);

	List<UserBook> getUserBookList( Long bookId );

	UserBook createOrUpdateUserBook( UserBook userBook );

}
