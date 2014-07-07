package com.pratilipi.data.access;

import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Book;
import com.pratilipi.data.transfer.Genere;
import com.pratilipi.data.transfer.Publisher;
import com.pratilipi.data.transfer.Tag;


public interface DataAccessor extends com.claymus.data.access.DataAccessor {
	
	Book newBook();

	Book getBook( String isbn );

	Book createOrUpdateBook( Book book );

	
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
	
}
