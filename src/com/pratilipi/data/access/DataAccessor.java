package com.pratilipi.data.access;

import java.util.List;

import com.claymus.data.access.DataListCursorTuple;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.data.transfer.Article;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Book;
import com.pratilipi.data.transfer.Genre;
import com.pratilipi.data.transfer.Language;
import com.pratilipi.data.transfer.Poem;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.data.transfer.PratilipiAuthor;
import com.pratilipi.data.transfer.PratilipiGenre;
import com.pratilipi.data.transfer.PratilipiTag;
import com.pratilipi.data.transfer.Publisher;
import com.pratilipi.data.transfer.Story;
import com.pratilipi.data.transfer.Tag;
import com.pratilipi.data.transfer.UserPratilipi;


public interface DataAccessor extends com.claymus.data.access.DataAccessor {
	
	Book newBook();

	Poem newPoem();

	Story newStory();

	Article newArticle();

	Pratilipi getPratilipi( Long id );

	Pratilipi getPratilipi( Long id, PratilipiType type );
	
	DataListCursorTuple<Pratilipi> getPratilipiList(
			String cursorStr, int resultCount );
	
	DataListCursorTuple<Pratilipi> getPratilipiList(
			PratilipiType type, Boolean publicDomain, String cursorStr, int resultCount );

	DataListCursorTuple<Pratilipi> getPratilipiListByLanguage( 
			PratilipiType type, Long languageId, String cursorStr, int resultCount );

	DataListCursorTuple<Pratilipi> getPratilipiListByAuthor( 
			PratilipiType type, Long authorId, String cursorStr, int resultCount );

	Pratilipi createOrUpdatePratilipi( Pratilipi pratilipi );
	

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
	

	PratilipiAuthor newPratilipiAuthor();

	PratilipiAuthor createOrUpdatePratilipiAuthor( PratilipiAuthor pratilipiAuthor );
	

	PratilipiGenre newPratilipiGenre();

	PratilipiGenre createOrUpdatePratilipiGenre( PratilipiGenre pratilipiGenere );
	

	PratilipiTag newPratilipiTag();

	PratilipiTag createOrUpdatePratilipiTag( PratilipiTag pratilipiTag );
	
	
	UserPratilipi newUserPratilipi();
	
	UserPratilipi getUserPratilipi( Long userId, Long pratilipiId );

	List<UserPratilipi> getUserPratilipiList( Long pratilipiId );
	
	UserPratilipi createOrUpdateUserBook( UserPratilipi userPratilipi );

}
