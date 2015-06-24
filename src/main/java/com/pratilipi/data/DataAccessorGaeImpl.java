package com.pratilipi.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDODataStoreException;
import javax.jdo.JDOHelper;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JDOCursorHelper;
import com.pratilipi.common.type.PageType;
import com.pratilipi.common.util.PratilipiFilter;
import com.pratilipi.data.GaeQueryBuilder.Operator;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.type.AppProperty;
import com.pratilipi.data.type.AuditLog;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.User;
import com.pratilipi.data.type.gae.AccessTokenEntity;
import com.pratilipi.data.type.gae.AppPropertyEntity;
import com.pratilipi.data.type.gae.AuditLogEntity;
import com.pratilipi.data.type.gae.AuthorEntity;
import com.pratilipi.data.type.gae.PageEntity;
import com.pratilipi.data.type.gae.PratilipiEntity;
import com.pratilipi.data.type.gae.UserEntity;

public class DataAccessorGaeImpl implements DataAccessor {

	private static final Logger logger = Logger.getGlobal();

	private static final PersistenceManagerFactory pmfInstance =
			JDOHelper.getPersistenceManagerFactory( "transactions-optional" );

	private final PersistenceManager pm;
	
	
	// Constructor

	public DataAccessorGaeImpl() {
		this.pm = pmfInstance.getPersistenceManager();
	}

	
	// Helper Methods
	
	private <T> T getEntity( Class<T> clazz, Object id ) {
		T entity = (T) pm.getObjectById( clazz, id );
		return pm.detachCopy( entity );
	}

	private <T> T createOrUpdateEntity( T entity ) {
		entity = pm.makePersistent( entity );
		return pm.detachCopy( entity );
	}
	
	@SuppressWarnings("unused")
	private <T> List<T> createOrUpdateEntityList( List<T> entityList ) {
		entityList = (List<T>) pm.makePersistentAll( entityList );
		return (List<T>) pm.detachCopyAll( entityList );
	}
	
	@SuppressWarnings("unused")
	private <T> void deleteEntity( Class<T> clazz, Object id ) {
		T entity = (T) pm.getObjectById( clazz, id );
		pm.deletePersistent( entity );
	}

	
	// APP_PROPERTY Table
	
	@Override
	public AppProperty newAppProperty( String id ) {
		AppPropertyEntity appProperty = new AppPropertyEntity();
		appProperty.setId( id );
		return appProperty;
	}

	@Override
	public AppProperty getAppProperty( String id ) {
		if( id == null )
			return null;
		
		try{
			return getEntity( AppPropertyEntity.class, id );
		} catch( JDOObjectNotFoundException e ) {
			return null;
		}
	}
	
	@Override
	public AppProperty createOrUpdateAppProperty( AppProperty appProperty ) {
		return createOrUpdateEntity( appProperty );
	}
	
	
	// USER Table
	
	@Override
	public User newUser() {
		return new UserEntity();
	}
	
	@Override
	public User getUser( Long id ) {
		return id == null ? null : getEntity( UserEntity.class, id );
	}
	
	@Override
	public User getUserByEmail( String email ) {
		Query query = new GaeQueryBuilder( pm.newQuery( UserEntity.class ) )
				.addFilter( "email", email )
				.build();
		
		@SuppressWarnings("unchecked")
		List<User> userList = (List<User>) query.execute( email );

		if( userList.size() > 1 )
			logger.log( Level.SEVERE, userList.size() + " Users found with Email Id " + email   + " ." );

		return userList.size() == 0 ? null : pm.detachCopy( userList.get( 0 ) );
	}
	
	@Override
	public User createOrUpdateUser( User user ) {
		return createOrUpdateEntity( user );
	}

	
	// ACCESS_TOKEN Table

	@Override
	public AccessToken newAccessToken() {
		return new AccessTokenEntity();
	}

	@Override
	public AccessToken getAccessToken( String accessTokenId ) {
		if( accessTokenId == null )
			return null;
		
		try{
			return getEntity( AccessTokenEntity.class, accessTokenId );
		} catch( JDOObjectNotFoundException e ) {
			return null;
		}
	}
	
	@Override
	public AccessToken createAccessToken( AccessToken accessToken ) {
		accessToken.setCreationDate( new Date() );
		if( accessToken.getExpiry() == null )
			accessToken.setExpiry( new Date( new Date().getTime() + 86400000 ) ); // 1 Day

		Transaction tx = null;
		while( true ) {
			((AccessTokenEntity) accessToken).setId( UUID.randomUUID().toString() );
			try {
				tx = pm.currentTransaction();
				tx.begin();
				pm.getObjectById( AccessTokenEntity.class, accessToken.getId() );
			} catch( JDOObjectNotFoundException e ) {
				try{
					AccessToken at = pm.makePersistent( accessToken );
					tx.commit();
					return pm.detachCopy( at );
				} catch( JDODataStoreException ex ) {
					logger.log( Level.INFO, "Transaction failed. Retrying ...", ex );
				}
			} finally {
				if( tx.isActive() )
					tx.rollback();
			}
		}
	}
	
	@Override
	public AccessToken updateAccessToken( AccessToken accessToken ) {
		return createOrUpdateEntity( accessToken );
	}
	
	
	// AUDIT_LOG Table
	
	@Override
	public AuditLog newAuditLog() {
		return new AuditLogEntity();
	}

	@Override
	public AuditLog createAuditLog( AuditLog auditLog ) {
		( (AuditLogEntity) auditLog ).setCreationDate( new Date() );
		return createOrUpdateEntity( auditLog );
	}

	@Override
	public DataListCursorTuple<AuditLog> getAuditLogList( String cursorStr, Integer resultCount ) {
		
		GaeQueryBuilder gaeQueryBuilder =
				new GaeQueryBuilder( pm.newQuery( AuditLogEntity.class ) )
						.addOrdering( "creationDate", false );
		
		if( resultCount != null )
			gaeQueryBuilder.setRange( 0, resultCount );
		
		Query query = gaeQueryBuilder.build();

		if( cursorStr != null ) {
			Cursor cursor = Cursor.fromWebSafeString( cursorStr );
			Map<String, Object> extensionMap = new HashMap<String, Object>();
			extensionMap.put( JDOCursorHelper.CURSOR_EXTENSION, cursor );
			query.setExtensions( extensionMap );
		}
		
		@SuppressWarnings("unchecked")
		List<AuditLog> audtiLogList = (List<AuditLog>) query.execute();
		Cursor cursor = JDOCursorHelper.getCursor( audtiLogList );
		
		return new DataListCursorTuple<>( 
				(List<AuditLog>) pm.detachCopyAll( audtiLogList ),
				cursor == null ? null : cursor.toWebSafeString() );
	}
	
	
	// PAGE Table
	
	@Override
	public Page newPage() {
		return new PageEntity();
	}
	
	@Override
	public Page getPage( Long id ) {
		return getEntity( PageEntity.class, id );
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Page getPage( String uri ) {
		Query query = new GaeQueryBuilder( pm.newQuery( PageEntity.class ) )
						.addFilter( "uriAlias", uri )
						.addOrdering( "creationDate", true )
						.build();

		List<Page> pageList = (List<Page>) query.execute( uri );
		if( pageList.size() > 1 )
			logger.log( Level.SEVERE, "More than one page entities found for uri " + uri );
		
		if( pageList.size() != 0 )
			return pm.detachCopy( pageList.get( 0 ) );

		
		query = new GaeQueryBuilder( pm.newQuery( PageEntity.class ) )
						.addFilter( "uri", uri )
						.addOrdering( "creationDate", true )
						.build();

		pageList = (List<Page>) query.execute( uri );
		if( pageList.size() > 1 )
			logger.log( Level.SEVERE, "More than one page entities found for uri alias " + uri );

		return pageList.size() == 0 ? null : pm.detachCopy( pageList.get( 0 ) );
	}
	
	@Override
	public Page getPage( PageType pageType, Long primaryContentId ) {
		Query query =
				new GaeQueryBuilder( pm.newQuery( PageEntity.class ) )
						.addFilter( "type", pageType )
						.addFilter( "primaryContentId", primaryContentId )
						.addOrdering( "creationDate", true )
						.build();
		
		@SuppressWarnings("unchecked")
		List<Page> pageList = (List<Page>) query.execute( pageType, primaryContentId );
		if( pageList.size() > 1 )
			logger.log( Level.SEVERE, "More than one page entities found for PageType " + pageType + ", PageContent " + primaryContentId );
		return pageList.size() == 0 ? null : pm.detachCopy( pageList.get( 0 ) );
	}
	
	@Override
	public Page createOrUpdatePage( Page page ) {
		return createOrUpdateEntity( page );
	}
	
	
	// PRATILIPI Table

	@Override
	public Pratilipi newPratilipi() {
		return new PratilipiEntity();
	}

	@Override
	public Pratilipi getPratilipi( Long id ) {
		try {
			return getEntity( PratilipiEntity.class, id );
		} catch( JDOObjectNotFoundException e ) {
			return null;
		}
	}

	@Override
	public List<Pratilipi> getPratilipiList( List<Long> idList ) {
		List<Pratilipi> pratilipiList = new ArrayList<>( idList.size() );
		for( Long id : idList )
			pratilipiList.add( getPratilipi( id ) );
		return pratilipiList;
	}
	
	@Override
	public DataListCursorTuple<Long> getPratilipiIdList(
			PratilipiFilter pratilipiFilter, String cursorStr, Integer resultCount ) {
		
		return getPratilipiList( pratilipiFilter, cursorStr, resultCount, true );
	}
	
	@Override
	public DataListCursorTuple<Pratilipi> getPratilipiList(
			PratilipiFilter pratilipiFilter, String cursorStr, Integer resultCount ) {
		
		return getPratilipiList( pratilipiFilter, cursorStr, resultCount, false );
	}

	@SuppressWarnings("unchecked")
	private <T> DataListCursorTuple<T> getPratilipiList(
			PratilipiFilter pratilipiFilter, String cursorStr,
			Integer resultCount, boolean idOnly ) {
		
		GaeQueryBuilder gaeQueryBuilder =
				new GaeQueryBuilder( pm.newQuery( PratilipiEntity.class ) );

		if( pratilipiFilter.getType() != null )
			gaeQueryBuilder.addFilter( "type", pratilipiFilter.getType() );
		if( pratilipiFilter.getLanguage() != null )
			gaeQueryBuilder.addFilter( "language", pratilipiFilter.getLanguage() );
		if( pratilipiFilter.getAuthorId() != null )
			gaeQueryBuilder.addFilter( "authorId", pratilipiFilter.getAuthorId() );
		if( pratilipiFilter.getState() != null )
			gaeQueryBuilder.addFilter( "state", pratilipiFilter.getState() );
		if( pratilipiFilter.getNextProcessDateEnd() != null ) {
			gaeQueryBuilder.addFilter( "nextProcessDate", pratilipiFilter.getNextProcessDateEnd(), Operator.LESS_THAN_OR_EQUAL );
			gaeQueryBuilder.addOrdering( "nextProcessDate", true );
		}
		
		if( pratilipiFilter.getOrderByReadCount() != null )
			gaeQueryBuilder.addOrdering( "readCount", pratilipiFilter.getOrderByReadCount() );

		if( resultCount != null )
			gaeQueryBuilder.setRange( 0, resultCount );
		
		Query query = gaeQueryBuilder.build();
		if( cursorStr != null ) {
			Cursor cursor = Cursor.fromWebSafeString( cursorStr );
			Map<String, Object> extensionMap = new HashMap<String, Object>();
			extensionMap.put( JDOCursorHelper.CURSOR_EXTENSION, cursor );
			query.setExtensions( extensionMap );
		}

		if( idOnly )
			query.setResult( "id" );
		
		List<T> pratilipiEntityList =
				(List<T>) query.executeWithMap( gaeQueryBuilder.getParamNameValueMap() );
		Cursor cursor = JDOCursorHelper.getCursor( pratilipiEntityList );
		
		return new DataListCursorTuple<T>(
				idOnly ? pratilipiEntityList : (List<T>) pm.detachCopyAll( pratilipiEntityList ),
				cursor == null ? null : cursor.toWebSafeString() );
	}
	
	@Override
	public Pratilipi createOrUpdatePratilipi( Pratilipi pratilipi ) {
		return createOrUpdateEntity( pratilipi );
	}
	
	
	// AUTHOR Table
	
	@Override
	public Author newAuthor() {
		return new AuthorEntity();
	}

	@Override
	public Author getAuthor( Long id ) {
		return id == null ? null : getEntity( AuthorEntity.class, id );
	}
	
	@Override
	public Author getAuthorByEmailId( String email ) {
		Query query = new GaeQueryBuilder( pm.newQuery( AuthorEntity.class ) )
				.addFilter( "email", email )
				.addFilter( "registrationDate", true )
				.build();

		@SuppressWarnings("unchecked")
		List<Author> authorList = (List<Author>) query.execute( email );

		if( authorList.size() > 1 )
			logger.log( Level.SEVERE, authorList.size() + " Authors found with Email Id " + email   + " ." );

		return authorList.size() == 0 ? null : pm.detachCopy( authorList.get( 0 ) );
	}

	@Override
	public Author getAuthorByUserId( Long userId ) {
		Query query = new GaeQueryBuilder( pm.newQuery( AuthorEntity.class ) )
				.addFilter( "userId", userId )
				.addFilter( "registrationDate", true )
				.build();
		
		@SuppressWarnings("unchecked")
		List<Author> authorList = (List<Author>) query.execute( userId );
		
		if( authorList.size() > 1 )
			logger.log( Level.SEVERE, authorList.size() + " Authors found with User Id " + userId + " ." );
		
		return authorList.size() == 0 ? null : pm.detachCopy( authorList.get( 0 ) );
	}
	
	@Override
	public List<Author> getAuthorList( List<Long> idList ) {
		List<Author> authorList = new ArrayList<>( idList.size() );
		for( Long id : idList )
			authorList.add( getAuthor( id ) );
		return authorList;
	}
	
	@Override
	public Author createOrUpdateAuthor( Author author ) {
		return createOrUpdateEntity( author );
	}


	// Destroy

	@Override
	public void destroy() {
		pm.close();
	}

}
