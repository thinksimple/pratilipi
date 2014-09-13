package com.pratilipi.data.access;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.Query;

import com.claymus.data.access.DataListCursorTuple;
import com.claymus.data.access.GaeQueryBuilder;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JDOCursorHelper;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.data.access.gae.ArticleEntity;
import com.pratilipi.data.access.gae.AuthorEntity;
import com.pratilipi.data.access.gae.BookEntity;
import com.pratilipi.data.access.gae.GenreEntity;
import com.pratilipi.data.access.gae.LanguageEntity;
import com.pratilipi.data.access.gae.PoemEntity;
import com.pratilipi.data.access.gae.PratilipiAuthorEntity;
import com.pratilipi.data.access.gae.PratilipiEntity;
import com.pratilipi.data.access.gae.PratilipiGenreEntity;
import com.pratilipi.data.access.gae.PratilipiTagEntity;
import com.pratilipi.data.access.gae.PublisherEntity;
import com.pratilipi.data.access.gae.StoryEntity;
import com.pratilipi.data.access.gae.TagEntity;
import com.pratilipi.data.access.gae.UserPratilipiEntity;
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

public class DataAccessorGaeImpl
		extends com.claymus.data.access.DataAccessorGaeImpl
		implements DataAccessor {

	@Override
	public Pratilipi getPratilipi( Long id ) {
		return getEntity( PratilipiEntity.class, id );
	}

	@Override
	public Pratilipi getPratilipi( Long id, PratilipiType type ) {
		if( type == PratilipiType.BOOK )
			return getEntity( BookEntity.class, id );
		
		else if( type == PratilipiType.POEM )
			return getEntity( PoemEntity.class, id );
		
		else if( type == PratilipiType.STORY )
			return getEntity( StoryEntity.class, id );

		else if( type == PratilipiType.ARTICLE )
			return getEntity( ArticleEntity.class, id );
		
		return null;
	}
	
	@Override
	public DataListCursorTuple<Pratilipi> getPratilipiListByAuthor( 
			Long authorId, PratilipiType type, String cursorStr, int resultCount  ) {
		Query query = null;
		
		if( type == PratilipiType.BOOK )
			query = pm.newQuery( BookEntity.class );
		
		else if( type == PratilipiType.POEM )
			query = pm.newQuery( PoemEntity.class );

		else if( type == PratilipiType.STORY )
			query = pm.newQuery( StoryEntity.class );

		else if( type == PratilipiType.ARTICLE )
			query = pm.newQuery( ArticleEntity.class );
		
		query = new GaeQueryBuilder( query )
						.addFilter( "type", type )
						.addFilter( "authorId", authorId )
						.addOrdering( "title", true )
						.setRange( 0, resultCount )
						.build();

		if( cursorStr != null ) {
			Cursor cursor = Cursor.fromWebSafeString( cursorStr );
			Map<String, Object> extensionMap = new HashMap<String, Object>();
			extensionMap.put( JDOCursorHelper.CURSOR_EXTENSION, cursor );
			query.setExtensions(extensionMap);
		}
		
		@SuppressWarnings("unchecked")
		List<Pratilipi> pratilipiEntityList = (List<Pratilipi>) query.execute( type, authorId );
		Cursor cursor = JDOCursorHelper.getCursor( pratilipiEntityList );
		
		return new DataListCursorTuple<Pratilipi>(
				(List<Pratilipi>) pm.detachCopyAll( pratilipiEntityList ),
				cursor == null ? null : cursor.toWebSafeString() );
		
	}

	@Override
	public DataListCursorTuple<Pratilipi> getPratilipiList(
			String cursorStr, int resultCount ) {
		
		Query query = new GaeQueryBuilder( pm.newQuery( PratilipiEntity.class ) )
						.addOrdering( "title", true )
						.setRange( 0, resultCount )
						.build();

		if( cursorStr != null ) {
			Cursor cursor = Cursor.fromWebSafeString( cursorStr );
			Map<String, Object> extensionMap = new HashMap<String, Object>();
			extensionMap.put( JDOCursorHelper.CURSOR_EXTENSION, cursor );
			query.setExtensions(extensionMap);
		}
		
		@SuppressWarnings("unchecked")
		List<Pratilipi> pratilipiEntityList = (List<Pratilipi>) query.execute();
		Cursor cursor = JDOCursorHelper.getCursor( pratilipiEntityList );
		
		return new DataListCursorTuple<Pratilipi>(
				(List<Pratilipi>) pm.detachCopyAll( pratilipiEntityList ),
				cursor == null ? null : cursor.toWebSafeString() );
	}
	
	@Override
	public DataListCursorTuple<Pratilipi> getPratilipiList(
			PratilipiType type, Boolean publicDomain, String cursorStr, int resultCount ) {
		
		Query query = null;
		
		if( type == PratilipiType.BOOK )
			query = pm.newQuery( BookEntity.class );
		
		else if( type == PratilipiType.POEM )
			query = pm.newQuery( PoemEntity.class );

		else if( type == PratilipiType.STORY )
			query = pm.newQuery( StoryEntity.class );

		else if( type == PratilipiType.ARTICLE )
			query = pm.newQuery( ArticleEntity.class );
		
		if( publicDomain != null )
			query = new GaeQueryBuilder( query )
							.addFilter( "type", type )
							.addFilter( "publicDomain", publicDomain )
							.addOrdering( "title", true )
							.setRange( 0, resultCount )
							.build();
		else
			query = new GaeQueryBuilder( query )
							.addFilter( "type", type )
							.addOrdering( "title", true )
							.setRange( 0, resultCount )
							.build();

		
		if( cursorStr != null ) {
			Cursor cursor = Cursor.fromWebSafeString( cursorStr );
			Map<String, Object> extensionMap = new HashMap<String, Object>();
			extensionMap.put( JDOCursorHelper.CURSOR_EXTENSION, cursor );
			query.setExtensions(extensionMap);
		}
		
		List<Pratilipi> pratilipiEntityList;
		if( publicDomain == null )
			pratilipiEntityList = (List<Pratilipi>) query.execute( type );
		else
			pratilipiEntityList = (List<Pratilipi>) query.execute( type, publicDomain );
		Cursor cursor = JDOCursorHelper.getCursor( pratilipiEntityList );
		
		return new DataListCursorTuple<Pratilipi>(
				(List<Pratilipi>) pm.detachCopyAll( pratilipiEntityList ),
				cursor == null ? null : cursor.toWebSafeString() );
	}
	
	@Override
	public Pratilipi createOrUpdatePratilipi( Pratilipi pratilipi ) {
		return createOrUpdateEntity( pratilipi );
	}
	
	
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
		List<Pratilipi> pratilipiList =
				getPratilipiList( PratilipiType.BOOK, null, null , 100 ).getDataList();
		ArrayList<Book> bookList = new ArrayList<>( pratilipiList.size() );
		for( Pratilipi pratilipi : pratilipiList )
			bookList.add( (Book) pratilipi );
		return bookList;
	}
	
	@Override
	public Book createOrUpdateBook( Book book ) {
		return createOrUpdateEntity( book );
	}

	
	@Override
	public Poem newPoem() {
		return new PoemEntity();
	}


	@Override
	public Story newStory() {
		return new StoryEntity();
	}


	@Override
	public Article newArticle() {
		return new ArticleEntity();
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
						.addOrdering( "nameEn", true )
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
	public PratilipiAuthor newPratilipiAuthor() {
		return new PratilipiAuthorEntity();
	}

	@Override
	public PratilipiAuthor createOrUpdatePratilipiAuthor( PratilipiAuthor pratilipiAuthor ) {
		return createOrUpdateEntity( pratilipiAuthor );
	}

	
	@Override
	public PratilipiGenre newPratilipiGenre() {
		return new PratilipiGenreEntity();
	}

	@Override
	public PratilipiGenre createOrUpdatePratilipiGenre( PratilipiGenre pratilipiGenre ) {
		return createOrUpdateEntity( pratilipiGenre );
	}


	@Override
	public PratilipiTag newPratilipiTag() {
		return new PratilipiTagEntity();
	}

	@Override
	public PratilipiTag createOrUpdatePratilipiTag( PratilipiTag pratilipiTag ) {
		return createOrUpdateEntity( pratilipiTag );
	}

	
	@Override
	public UserPratilipi newUserPratilipi() {
		return new UserPratilipiEntity();
	}
	
	@Override
	public UserPratilipi getUserPratilipi( Long userId, Long pratilipiId ) {
		try {
			return getEntity( UserPratilipiEntity.class, userId + "-" + pratilipiId );
		} catch( JDOObjectNotFoundException e ) {
			return null;
		}
	}

	@Override
	public List<UserPratilipi> getUserPratilipiList( Long pratilipiId ) {
		
		Query query =
				new GaeQueryBuilder( pm.newQuery( UserPratilipiEntity.class ) )
						.addFilter( "pratilipiId", pratilipiId )
						.build();
		
		@SuppressWarnings("unchecked")
		List<UserPratilipi> userBookList = (List<UserPratilipi>) query.execute( pratilipiId );
		return (List<UserPratilipi>) pm.detachCopyAll( userBookList );
		
	}

	@Override
	public UserPratilipi createOrUpdateUserBook( UserPratilipi userPratilipi ) {
		( (UserPratilipiEntity) userPratilipi ).setId( userPratilipi.getUserId() + "-" + userPratilipi.getPratilipiId() );
		return createOrUpdateEntity( userPratilipi );
	}

}
