package com.pratilipi.data.access;

import java.util.ArrayList;
import java.util.List;

import com.claymus.data.access.DataListCursorTuple;
import com.claymus.data.access.Memcache;
import com.pratilipi.commons.shared.PratilipiFilter;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Genre;
import com.pratilipi.data.transfer.Language;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.data.transfer.PratilipiAuthor;
import com.pratilipi.data.transfer.PratilipiGenre;
import com.pratilipi.data.transfer.PratilipiTag;
import com.pratilipi.data.transfer.Publisher;
import com.pratilipi.data.transfer.Tag;
import com.pratilipi.data.transfer.UserPratilipi;

public class DataAccessorWithMemcache
		extends com.claymus.data.access.DataAccessorWithMemcache
		implements DataAccessor {
	
	private static final String PREFIX_PRATILIPI = "Pratilipi-";
	private static final String PREFIX_LANGUAGE = "Language-";
	private static final String PREFIX_LANGUAGE_LIST = "LanguageList-";
	private static final String PREFIX_AUTHOR = "Author-";
	private static final String PREFIX_GENRE = "Genre-";
	private static final String PREFIX_GENRE_LIST = "GenreList-";
	private static final String PREFIX_PRATILIPI_GENRE = "PratilipiGenre-";
	private static final String PREFIX_PRATILIPI_GENRE_LIST = "PratilipiGenreList-";
	private static final String PREFIX_USER_PRATILIPI = "UserPratilipi-";
	private static final String PREFIX_USER_PRATILIPI_LIST = "UserPratilipiList-";

	
	private final DataAccessor dataAccessor;
	private final Memcache memcache;
	
	
	public DataAccessorWithMemcache(
			DataAccessor dataAccessor, Memcache memcache ) {

		super( dataAccessor, memcache );
		this.dataAccessor = dataAccessor;
		this.memcache = memcache;
	}

	
	@Override
	public Pratilipi newPratilipi() {
		return dataAccessor.newPratilipi();
	}

	@Override
	public Pratilipi getPratilipi( Long id ) {
		Pratilipi pratilipi = memcache.get( PREFIX_PRATILIPI + id );
		if( pratilipi == null ) {
			pratilipi = dataAccessor.getPratilipi( id );
			if( pratilipi != null )
				memcache.put( PREFIX_PRATILIPI + id, pratilipi );
		}
		return pratilipi;
	}

	@Override
	public DataListCursorTuple<Pratilipi> getPratilipiList(
			PratilipiFilter pratilipiFilter, String cursorStr, int resultCount ) {
		// TODO: enable caching
		return dataAccessor.getPratilipiList( pratilipiFilter, cursorStr, resultCount );
	}
	
	@Override
	public DataListCursorTuple<Pratilipi> getPratilipiList(
			PratilipiType type, Boolean publicDomain, String cursorStr, int resultCount ) {
		// TODO: enable caching
		return dataAccessor.getPratilipiList( type, publicDomain, cursorStr, resultCount );
	}
	
	@Override
	public DataListCursorTuple<Pratilipi> getPratilipiListByLanguage( 
			PratilipiType type, Long languageId, String cursorStr, int resultCount  ) {
		// TODO: enable caching
		return dataAccessor.getPratilipiListByLanguage( type, languageId, cursorStr, resultCount );
	}

	@Override
	public DataListCursorTuple<Pratilipi> getPratilipiListByAuthor( 
			PratilipiType type, Long authorId, String cursorStr, int resultCount  ) {
		// TODO: enable caching
		return dataAccessor.getPratilipiListByAuthor( type, authorId, cursorStr, resultCount );
	}

	@Override
	public Pratilipi createOrUpdatePratilipi( Pratilipi pratilipi ) {
		pratilipi = dataAccessor.createOrUpdatePratilipi( pratilipi );
		memcache.put( PREFIX_PRATILIPI + pratilipi.getId(), pratilipi );
		return pratilipi;
	}
	
	
	@Override
	public Language newLanguage() {
		return dataAccessor.newLanguage();
	}

	@Override
	public Language getLanguage( Long id ) {
		Language language = memcache.get( PREFIX_LANGUAGE + id );
		if( language == null ) {
			language = dataAccessor.getLanguage( id );
			if( language != null )
				memcache.put( PREFIX_LANGUAGE + id, language );
		}
		return language;
	}

	@Override
	public List<Language> getLanguageList() {
		List<Language> languageList = memcache.get( PREFIX_LANGUAGE_LIST );
		if( languageList == null ) {
			languageList = dataAccessor.getLanguageList();
			memcache.put( PREFIX_LANGUAGE_LIST, new ArrayList<>( languageList ) );
		}
		return languageList;
	}
	
	@Override
	public Language createOrUpdateLanguage( Language language ) {
		language = dataAccessor.createOrUpdateLanguage( language );
		memcache.put( PREFIX_LANGUAGE + language.getId(), language );
		memcache.remove( PREFIX_LANGUAGE_LIST );
		return language;
	}

	
	@Override
	public Author newAuthor() {
		return dataAccessor.newAuthor();
	}

	@Override
	public Author getAuthor( Long id ) {
		Author author = memcache.get( PREFIX_AUTHOR + id );
		if( author == null ) {
			author = dataAccessor.getAuthor( id );
			if( author != null )
				memcache.put( PREFIX_AUTHOR + id, author );
		}
		return author;
	}

	@Override
	public Author getAuthorByUserId(Long userId) {
		//TODO : enable caching
		return dataAccessor.getAuthorByUserId( userId );
	}
	
	@Override
	public DataListCursorTuple<Author> getAuthorList( String cursor, int resultCount ) {
		// TODO: enable caching
		return dataAccessor.getAuthorList( cursor, resultCount );
	}
	
	@Override
	public Author createOrUpdateAuthor( Author author ) {
		author = dataAccessor.createOrUpdateAuthor( author );
		memcache.put( PREFIX_AUTHOR + author.getId(), author );
		return author;
	}

	
	@Override
	public Publisher newPublisher() {
		return dataAccessor.newPublisher();
	}

	@Override
	public Publisher getPublisher( Long id ) {
		// TODO: enable caching
		return dataAccessor.getPublisher( id );
	}
	
	@Override
	public List<Publisher> getPublisherList() {
		// TODO: enable caching
		return dataAccessor.getPublisherList();
	}

	@Override
	public Publisher createOrUpdatePublisher( Publisher publisher ) {
		// TODO: enable caching
		return dataAccessor.createOrUpdatePublisher( publisher );
	}

	
	@Override
	public Genre newGenre() {
		return dataAccessor.newGenre();
	}

	@Override
	public Genre getGenre( Long id ) {
		Genre genre = memcache.get( PREFIX_GENRE + id );
		if( genre == null ) {
			genre = dataAccessor.getGenre( id );
			if( genre != null )
				memcache.put( PREFIX_GENRE + id, genre );
		}
		return genre;
	}

	@Override
	public List<Genre> getGenreList() {
		List<Genre> genreList = memcache.get( PREFIX_GENRE_LIST );
		if( genreList == null ) {
			genreList = dataAccessor.getGenreList();
			memcache.put( PREFIX_GENRE_LIST, new ArrayList<>( genreList ) );
		}
		return genreList;
	}
	
	@Override
	public Genre createOrUpdateGenre( Genre genre ) {
		genre = dataAccessor.createOrUpdateGenre( genre );
		memcache.put( PREFIX_GENRE + genre.getId(), genre );
		memcache.remove( PREFIX_GENRE_LIST );
		return genre;
	}


	@Override
	public Tag newTag() {
		return dataAccessor.newTag();
	}

	@Override
	public Tag getTag( Long id ) {
		// TODO: enable caching
		return dataAccessor.getTag( id );
	}

	@Override
	public Tag createOrUpdateTag( Tag tag ) {
		// TODO: enable caching
		return dataAccessor.createOrUpdateTag( tag );
	}

	
	@Override
	public PratilipiAuthor newPratilipiAuthor() {
		// TODO: enable caching
		return dataAccessor.newPratilipiAuthor();
	}

	@Override
	public PratilipiAuthor createOrUpdatePratilipiAuthor( PratilipiAuthor pratilipiAuthor ) {
		// TODO: enable caching
		return dataAccessor.createOrUpdatePratilipiAuthor( pratilipiAuthor );
	}

	
	@Override
	public PratilipiGenre newPratilipiGenre() {
		return dataAccessor.newPratilipiGenre();
	}

	@Override
	public PratilipiGenre getPratilipiGenre( Long pratilipiId, Long genreId ) {
		PratilipiGenre pratilipiGenre = memcache.get(
				PREFIX_PRATILIPI_GENRE + pratilipiId + "-" + genreId );
		if( pratilipiGenre == null ) {
			pratilipiGenre =
					dataAccessor.getPratilipiGenre( pratilipiId, genreId );
			if( pratilipiGenre != null )
				memcache.put(
						PREFIX_PRATILIPI_GENRE + pratilipiId + "-" + genreId,
						pratilipiGenre );
		}
		return pratilipiGenre;
	}

	@Override
	public List<PratilipiGenre> getPratilipiGenreList( Long pratilipiId ) {
		List<PratilipiGenre> pratilipiGenreList =
				memcache.get( PREFIX_PRATILIPI_GENRE_LIST + pratilipiId );
		if( pratilipiGenreList == null ) {
			pratilipiGenreList =
					dataAccessor.getPratilipiGenreList( pratilipiId );
			memcache.put(
					PREFIX_PRATILIPI_GENRE_LIST + pratilipiId,
					new ArrayList<>( pratilipiGenreList ) );
		}
		return pratilipiGenreList;
	}

	@Override
	public PratilipiGenre createPratilipiGenre( PratilipiGenre pratilipiGenre ) {
		pratilipiGenre = dataAccessor.createPratilipiGenre( pratilipiGenre );
		memcache.put(
				PREFIX_PRATILIPI_GENRE + pratilipiGenre.getId(),
				pratilipiGenre );
		memcache.remove(
				PREFIX_PRATILIPI_GENRE_LIST + pratilipiGenre.getPratilipiId() );
		return pratilipiGenre;
	}

	@Override
	public void deletePratilipiGenre( Long pratilipiId, Long genreId ) {
		dataAccessor.deletePratilipiGenre( pratilipiId, genreId );
		memcache.remove(
				PREFIX_PRATILIPI_GENRE + pratilipiId + "-" + genreId );
		memcache.remove(
				PREFIX_PRATILIPI_GENRE_LIST + pratilipiId );
	}

	
	@Override
	public PratilipiTag newPratilipiTag() {
		// TODO: enable caching
		return dataAccessor.newPratilipiTag();
	}

	@Override
	public PratilipiTag createOrUpdatePratilipiTag( PratilipiTag pratilipiTag ) {
		// TODO: enable caching
		return dataAccessor.createOrUpdatePratilipiTag( pratilipiTag );
	}

	
	@Override
	public UserPratilipi newUserPratilipi() {
		return dataAccessor.newUserPratilipi();
	}
	
	@Override
	public UserPratilipi getUserPratilipi( Long userId, Long pratilipiId ) {
		UserPratilipi userPratilipi = memcache.get(
				PREFIX_USER_PRATILIPI + userId + "-" + pratilipiId );
		if( userPratilipi == null ) {
			userPratilipi =
					dataAccessor.getUserPratilipi( userId, pratilipiId );
			if( userPratilipi != null )
				memcache.put(
						PREFIX_USER_PRATILIPI + userId + "-" + pratilipiId,
						userPratilipi );
		}
		return userPratilipi;
	}

	@Override
	public List<UserPratilipi> getUserPratilipiList( Long pratilipiId ) {
		List<UserPratilipi> userPratilipiList =
				memcache.get( PREFIX_USER_PRATILIPI_LIST + pratilipiId );
		if( userPratilipiList == null ) {
			userPratilipiList =
					dataAccessor.getUserPratilipiList( pratilipiId );
			memcache.put(
					PREFIX_USER_PRATILIPI_LIST + pratilipiId,
					new ArrayList<>( userPratilipiList ) );
		}
		return userPratilipiList;
	}

	@Override
	public UserPratilipi createOrUpdateUserPratilipi( UserPratilipi userPratilipi ) {
		userPratilipi = dataAccessor.createOrUpdateUserPratilipi( userPratilipi );
		memcache.put(
				PREFIX_USER_PRATILIPI + userPratilipi.getId(),
				userPratilipi );
		memcache.remove(
				PREFIX_USER_PRATILIPI_LIST + userPratilipi.getPratilipiId() );
		return userPratilipi;
	}
	
}
