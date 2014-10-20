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
import com.pratilipi.commons.shared.PratilipiFilter;
import com.pratilipi.data.access.gae.AuthorEntity;
import com.pratilipi.data.access.gae.GenreEntity;
import com.pratilipi.data.access.gae.LanguageEntity;
import com.pratilipi.data.access.gae.PratilipiAuthorEntity;
import com.pratilipi.data.access.gae.PratilipiEntity;
import com.pratilipi.data.access.gae.PratilipiGenreEntity;
import com.pratilipi.data.access.gae.PratilipiTagEntity;
import com.pratilipi.data.access.gae.PublisherEntity;
import com.pratilipi.data.access.gae.TagEntity;
import com.pratilipi.data.access.gae.UserPratilipiEntity;
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

public class DataAccessorGaeImpl
		extends com.claymus.data.access.DataAccessorGaeImpl
		implements DataAccessor {

	
	@Override
	public Pratilipi newPratilipi() {
		return new PratilipiEntity();
	}


	@Override
	public Pratilipi getPratilipi( Long id ) {
		return getEntity( PratilipiEntity.class, id );
	}

	@Override
	public DataListCursorTuple<Pratilipi> getPratilipiList(
			PratilipiFilter pratilipiFilter, String cursorStr, int resultCount ) {
		
		GaeQueryBuilder gaeQueryBuilder =
				new GaeQueryBuilder( pm.newQuery( PratilipiEntity.class ) )
						.addOrdering( "title", true )
						.setRange( 0, resultCount );

		if( pratilipiFilter.getType() != null )
			gaeQueryBuilder.addFilter( "type", pratilipiFilter.getType() );
		if( pratilipiFilter.getPublicDomain() != null )
			gaeQueryBuilder.addFilter( "publicDomain", pratilipiFilter.getPublicDomain() );
		if( pratilipiFilter.getLanguageId() != null )
			gaeQueryBuilder.addFilter( "languageId", pratilipiFilter.getLanguageId() );
		if( pratilipiFilter.getAuthorId() != null )
			gaeQueryBuilder.addFilter( "authorId", pratilipiFilter.getAuthorId() );
		
		Query query = gaeQueryBuilder.build();
		if( cursorStr != null ) {
			Cursor cursor = Cursor.fromWebSafeString( cursorStr );
			Map<String, Object> extensionMap = new HashMap<String, Object>();
			extensionMap.put( JDOCursorHelper.CURSOR_EXTENSION, cursor );
			query.setExtensions(extensionMap);
		}
		
		@SuppressWarnings("unchecked")
		List<Pratilipi> pratilipiEntityList =
				(List<Pratilipi>) query.executeWithMap( gaeQueryBuilder.getParamNameValueMap() );
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
	public Author getAuthorByUserId( Long userId ) {
		Query query = new GaeQueryBuilder( pm.newQuery( AuthorEntity.class ) )
							.addFilter( "userId", userId )
							.build();
		
		@SuppressWarnings("unchecked")
		List<Author> authorList = (List<Author>) query.execute( userId );
		
		return authorList.size() == 0 ? null : pm.detachCopy( authorList.get( 0 ) );
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
	public PratilipiGenre getPratilipiGenre( Long pratilipiId, Long genreId ) {
		try {
			return getEntity( PratilipiGenreEntity.class, pratilipiId + "-" + genreId );
		} catch( JDOObjectNotFoundException e ) {
			return null;
		}
	}
	
	@Override
	public List<PratilipiGenre> getPratilipiGenreList( Long pratilipiId ) {
		Query query =
				new GaeQueryBuilder( pm.newQuery( PratilipiGenreEntity.class ) )
						.addFilter( "pratilipiId", pratilipiId )
						.build();
		
		@SuppressWarnings("unchecked")
		List<PratilipiGenre> pratilipiGenreEntityList = (List<PratilipiGenre>) query.execute( pratilipiId );
		return (List<PratilipiGenre>) pm.detachCopyAll( pratilipiGenreEntityList );
	}
	
	@Override
	public PratilipiGenre createPratilipiGenre( PratilipiGenre pratilipiGenre ) {
		( (PratilipiGenreEntity) pratilipiGenre ).setId( pratilipiGenre.getPratilipiId() + "-" + pratilipiGenre.getGenreId() );
		return createOrUpdateEntity( pratilipiGenre );
	}

	@Override
	public void deletePratilipiGenre( Long pratilipiId, Long genreId ) {
		try {
			deleteEntity( PratilipiGenreEntity.class, pratilipiId + "-" + genreId );
		} catch( JDOObjectNotFoundException e ) {
			// Do nothing
		}
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
	public UserPratilipi createOrUpdateUserPratilipi( UserPratilipi userPratilipi ) {
		( (UserPratilipiEntity) userPratilipi ).setId( userPratilipi.getUserId() + "-" + userPratilipi.getPratilipiId() );
		return createOrUpdateEntity( userPratilipi );
	}

}
