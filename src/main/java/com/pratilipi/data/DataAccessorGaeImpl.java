package com.pratilipi.data;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.appengine.datanucleus.query.JDOCursorHelper;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.pratilipi.common.type.AuthorState;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PageType;
import com.pratilipi.common.type.UserReviewState;
import com.pratilipi.common.type.UserState;
import com.pratilipi.common.util.AuthorFilter;
import com.pratilipi.common.util.BlogPostFilter;
import com.pratilipi.common.util.PratilipiFilter;
import com.pratilipi.common.util.UserPratilipiFilter;
import com.pratilipi.data.GaeQueryBuilder.Operator;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.type.AppProperty;
import com.pratilipi.data.type.AuditLog;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Blog;
import com.pratilipi.data.type.BlogPost;
import com.pratilipi.data.type.Category;
import com.pratilipi.data.type.Event;
import com.pratilipi.data.type.Navigation;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.PratilipiCategory;
import com.pratilipi.data.type.User;
import com.pratilipi.data.type.UserAuthor;
import com.pratilipi.data.type.UserPratilipi;
import com.pratilipi.data.type.gae.AccessTokenEntity;
import com.pratilipi.data.type.gae.AppPropertyEntity;
import com.pratilipi.data.type.gae.AuditLogEntity;
import com.pratilipi.data.type.gae.AuthorEntity;
import com.pratilipi.data.type.gae.BlogEntity;
import com.pratilipi.data.type.gae.BlogPostEntity;
import com.pratilipi.data.type.gae.CategoryEntity;
import com.pratilipi.data.type.gae.EventEntity;
import com.pratilipi.data.type.gae.GenericOfyEntity;
import com.pratilipi.data.type.gae.NavigationEntity;
import com.pratilipi.data.type.gae.PageEntity;
import com.pratilipi.data.type.gae.PratilipiCategoryEntity;
import com.pratilipi.data.type.gae.PratilipiEntity;
import com.pratilipi.data.type.gae.UserAuthorEntity;
import com.pratilipi.data.type.gae.UserEntity;
import com.pratilipi.data.type.gae.UserPratilipiEntity;

public class DataAccessorGaeImpl implements DataAccessor {

	private static final Logger logger =
			Logger.getLogger( DataAccessorGaeImpl.class.getName() );
	
	private static final Memcache memcache = new MemcacheGaeImpl();

	private static final String NAVIGATION_DATA_FILE_PREFIX = "curated/navigation.";
	private static final String CATEGORY_DATA_FILE_PREFIX = "curated/category.";
	private static final String CURATED_DATA_FOLDER = "curated";
	
	private static final PersistenceManagerFactory pmfInstance =
			JDOHelper.getPersistenceManagerFactory( "transactions-optional" );

	private final PersistenceManager pm;
	
	
	// Registering Entities
	
	static {
		ObjectifyService.register( PageEntity.class );
		ObjectifyService.register( EventEntity.class );
		ObjectifyService.register( BlogEntity.class );
		ObjectifyService.register( BlogPostEntity.class );
		ObjectifyService.register( UserAuthorEntity.class );
	}
	
	
	// Constructor

	public DataAccessorGaeImpl() {
		this.pm = pmfInstance.getPersistenceManager();
	}

	@Override
	public PersistenceManager getPersistenceManager() {
		return pm;
	}
	
	
	// JDO Helper Methods
	
	private <T> T getEntity( Class<T> clazz, Object id ) {
		T entity = (T) pm.getObjectById( clazz, id );
		return pm.detachCopy( entity );
	}

	private <T> T createOrUpdateEntity( T entity ) {
		entity = pm.makePersistent( entity );
		return pm.detachCopy( entity );
	}
	
	private Object[] createOrUpdateEntities( Object... entities ) {
		entities = pm.makePersistentAll( entities );
		return pm.detachCopyAll( entities );
	}
	
	@SuppressWarnings("unused")
	private <T> List<T> createOrUpdateEntityList( List<T> entityList ) {
		entityList = (List<T>) pm.makePersistentAll( entityList );
		return (List<T>) pm.detachCopyAll( entityList );
	}
	
	private <T> void deleteEntity( Class<T> clazz, Object id ) {
		T entity = (T) pm.getObjectById( clazz, id );
		pm.deletePersistent( entity );
	}

	
	// Objectify Helper Methods
	
	private <T> T getEntityOfy( Class<T> clazz, Long id ) {
		return id == null ? null : ObjectifyService.ofy().load().type( clazz ).id( id ).now();
	}
	
	private <T> T getEntityOfy( Class<T> clazz, String id ) {
		return id == null ? null : ObjectifyService.ofy().load().type( clazz ).id( id ).now();
	}
	
	private <T extends GenericOfyEntity> T createOrUpdateEntityOfy( T entity ) {
		Key<T> key = ObjectifyService.ofy().save().entity( entity ).now();
		entity.setKey( key );
		return entity;
	}
	
	private Map<Key<Object>,Object> createOrUpdateEntitiesOfy( Object... entities ) {
		return ObjectifyService.ofy().save().entities( entities ).now();
	}

	
	// APP_PROPERTY Table
	
	@Override
	public AppProperty newAppProperty( String id ) {
		return new AppPropertyEntity( id );
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
				.addFilter( "state", UserState.DELETED, Operator.NOT_EQUALS )
				.addOrdering( "state", true )
				.addOrdering( "signUpDate", true )
				.build();
		
		@SuppressWarnings("unchecked")
		List<User> userList = (List<User>) query.execute( email, UserState.DELETED );

		if( userList.size() > 1 )
			logger.log( Level.SEVERE, userList.size() + " Users found with Email Id " + email   + " ." );

		return userList.size() == 0 ? null : pm.detachCopy( userList.get( 0 ) );
	}
	
	@Override
	public User getUserByFacebookId( String facebookId ) {
		Query query = new GaeQueryBuilder( pm.newQuery( UserEntity.class ) )
				.addFilter( "facebookId", facebookId )
				.addFilter( "state", UserState.DELETED, Operator.NOT_EQUALS )
				.addOrdering( "state", true )
				.addOrdering( "signUpDate", true )
				.build();
		
		@SuppressWarnings("unchecked")
		List<User> userList = (List<User>) query.execute( facebookId, UserState.DELETED );

		if( userList.size() > 1 )
			logger.log( Level.SEVERE, userList.size() + " Users found with facebook Id " + facebookId   + " ." );

		return userList.size() == 0 ? null : pm.detachCopy( userList.get( 0 ) );
	}

	@Override
	public Map<Long, User> getUsers( List<Long> idList ) {
		Map<Long, User> keyValueMap = new HashMap<>( idList.size() );
		for( Long userId : idList )
			keyValueMap.put( userId, getUser( userId ) );
		return keyValueMap;
	}
	
	@Override
	public DataListCursorTuple<User> getUserList( String cursor, Integer resultCount ) {
		return getUserList( cursor, resultCount, false );
	}
	
	@SuppressWarnings("unchecked")
	private <T> DataListCursorTuple<T> getUserList( String cursorStr, Integer resultCount, boolean idOnly ) {

		GaeQueryBuilder gaeQueryBuilder =
				new GaeQueryBuilder( pm.newQuery( UserEntity.class ) );
		
		gaeQueryBuilder.setCursor( cursorStr );
		if( resultCount != null )
			gaeQueryBuilder.setRange( 0, resultCount );
		if( idOnly )
			gaeQueryBuilder.setResult( "id" );
	
		Query query = gaeQueryBuilder.build();
		List<T> userEntityList = (List<T>) query.executeWithMap( gaeQueryBuilder.getParamNameValueMap() );
		Cursor cursor = JDOCursorHelper.getCursor( userEntityList );
		
		return new DataListCursorTuple<T>(
				idOnly ? userEntityList : (List<T>) pm.detachCopyAll( userEntityList ),
				cursor == null ? null : cursor.toWebSafeString() );
	}

	@Override
	public User createOrUpdateUser( User user ) {
		return createOrUpdateEntity( user );
	}

	public User createOrUpdateUser( User user, AuditLog auditLog ) {
		return (User) createOrUpdateEntities( user, auditLog )[ 0 ];
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
	public DataListCursorTuple<AccessToken> getAccessTokenList( String cursorStr, Integer resultCount ) {
		return getAccessTokenList( null, null, cursorStr, resultCount );
	}
	
	@Override
	public DataListCursorTuple<AccessToken> getAccessTokenList( Long userId, Date minExpiry, String cursorStr, Integer resultCount ) {
		GaeQueryBuilder gaeQueryBuilder = new GaeQueryBuilder( pm.newQuery( AccessTokenEntity.class ) );
		if( userId != null )
			gaeQueryBuilder.addFilter( "userId", userId );
		if( minExpiry != null ) {
			gaeQueryBuilder.addFilter( "expiry", minExpiry, Operator.GREATER_THAN_OR_EQUAL );
			gaeQueryBuilder.addOrdering( "expiry", true );
		}
		gaeQueryBuilder.addOrdering( "creationDate", true );

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
		List<AccessToken> accessTokenEntityList =
				(List<AccessToken>) query.executeWithMap( gaeQueryBuilder.getParamNameValueMap() );
		Cursor cursor = JDOCursorHelper.getCursor( accessTokenEntityList );
		
		return new DataListCursorTuple<AccessToken>( (List<AccessToken>) pm.detachCopyAll( accessTokenEntityList ), cursor.toWebSafeString() );
	}
	
	@Override
	public AccessToken createOrUpdateAccessToken( AccessToken accessToken ) {
		return createOrUpdateEntity( accessToken );
	}
	
	@Override
	public AccessToken createOrUpdateAccessToken( AccessToken newAccessToken, AccessToken oldAccessToken ) {
		return (AccessToken) createOrUpdateEntities( newAccessToken, oldAccessToken )[ 0 ];
	}
	
	@Override
	public void deleteAccessToken( AccessToken accessToken ) {
		deleteEntity( AccessTokenEntity.class, accessToken.getId() );
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
		return getAuditLogList( null, cursorStr, resultCount );
	}

	@Override
	public DataListCursorTuple<AuditLog> getAuditLogList( String accessId, String cursorStr, Integer resultCount ) {
		GaeQueryBuilder gaeQueryBuilder = new GaeQueryBuilder( pm.newQuery( AuditLogEntity.class ) )
				.addOrdering( "creationDate", false );

		if( accessId != null )
			gaeQueryBuilder.addFilter( "accessId", accessId );

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
		List<AuditLog> audtiLogList = (List<AuditLog>) query.executeWithMap( gaeQueryBuilder.getParamNameValueMap() );
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
		return getEntityOfy( PageEntity.class, id );
	}
	
	@Override
	public Page getPage( String uri ) {
		
		String memcacheId = _createPageEntityMemcacheId( uri );
		
		Page page = memcache.get( memcacheId );
		
		if( page == null ) {
			page = ObjectifyService.ofy().load()
					.type( PageEntity.class )
					.filter( "URI_ALIAS", uri )
					.order( "CREATION_DATE" )
					.first().now();
			if( page != null )
				memcache.put( memcacheId, page );
		}

		if( page == null ) {
			page = ObjectifyService.ofy().load()
					.type( PageEntity.class )
					.filter( "URI", uri )
					.order( "CREATION_DATE" )
					.first().now();
			if( page != null )
				memcache.put( memcacheId, page );
		}
		
		if( page == null ) { // Hack: This will save lots of DB queries.
			page = newPage();
			memcache.put( memcacheId, page );
		}
		
		return page.getId() == null ? null : page;
		
	}
	
	@Override
	public Page getPage( PageType pageType, Long primaryContentId ) {
		
		String memcacheId = _createPageEntityMemcacheId( pageType, primaryContentId );
		
		Page page = memcache.get( memcacheId );
		
		if( page == null ) {
			page = ObjectifyService.ofy().load()
					.type( PageEntity.class )
					.filter( "PAGE_TYPE", pageType )
					.filter( "PRIMARY_CONTENT_ID", primaryContentId )
					.order( "CREATION_DATE" )
					.first().now();
			if( page != null )
				memcache.put( memcacheId, page );
		}
		
		return page;
		
	}
	
	@Override
	public Map<String, Page> getPages( List<String> uriList ) {
		
		Map<String, Page> pages = new HashMap<>( uriList.size() );
		if( uriList.size() == 0 )
			return pages;
		
		List<String> memcacheIdList = new ArrayList<>( uriList.size() );
		for( String uri : uriList )
			memcacheIdList.add( _createPageEntityMemcacheId( uri ) );

		// Fetching entities from Memcache.
		Map<String, Page> keyValueMap = memcache.getAll( memcacheIdList );

		// Fetching missing entities from DataStore
		for( String uri : uriList ) {
			Page page = keyValueMap.get( _createPageEntityMemcacheId( uri ) );
			if( page == null )
				page = getPage( uri );
			pages.put( uri, page );
		}
		
		return pages;
		
	}

	@Override
	public Map<Long, Page> getPages( PageType pageType, List<Long> primaryContentIdList ) {

		Map<Long, Page> pages = new HashMap<>( primaryContentIdList.size() );
		if( primaryContentIdList.size() == 0 )
			return pages;
		
		List<String> memcacheIdList = new ArrayList<>( primaryContentIdList.size() );
		for( Long primaryContentId : primaryContentIdList )
			memcacheIdList.add( _createPageEntityMemcacheId( pageType, primaryContentId ) );

		// Fetching entities from Memcache.
		Map<String, Page> keyValueMap = memcache.getAll( memcacheIdList );
		
		// Fetching missing entities from DataStore
		for( Long primaryContentId : primaryContentIdList ) {
			Page page = keyValueMap.get( _createPageEntityMemcacheId( pageType, primaryContentId ) );
			if( page == null )
				page = getPage( pageType, primaryContentId );
			pages.put( primaryContentId, page );
		}
		
		return pages;
		
	}

	@Override
	public Page createOrUpdatePage( Page page ) {
		page = createOrUpdateEntity( page );
		if( page.getUri() != null )
			memcache.put( _createPageEntityMemcacheId( page.getUri() ), page );
		if( page.getUriAlias() != null )
			memcache.put( _createPageEntityMemcacheId( page.getUriAlias() ), page );
		if( page.getPrimaryContentId() != null )
			memcache.put( _createPageEntityMemcacheId( page.getType(), page.getPrimaryContentId() ), page );
		return page;
	}
	
	public void deletePage( Page page ) {
		deleteEntity( PageEntity.class, page.getId() );
		if( page.getUri() != null )
			memcache.remove( _createPageEntityMemcacheId( page.getUri() ) );
		if( page.getUriAlias() != null )
			memcache.remove( _createPageEntityMemcacheId( page.getUriAlias() ) );
		if( page.getPrimaryContentId() != null )
			memcache.remove( _createPageEntityMemcacheId( page.getType(), page.getPrimaryContentId() ) );
	}
	
	private String _createPageEntityMemcacheId( String uri ) {
		return "DataStore.Page-" + uri;
	}
	
	private String _createPageEntityMemcacheId( PageType pageType, Long primaryContentId ) {
		return "DataStore.Page-" + pageType + "::" + primaryContentId;
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
		
		return getPratilipiList( pratilipiFilter, cursorStr, null, resultCount, true );
	}
	
	@Override
	public DataListCursorTuple<Long> getPratilipiIdList(
			PratilipiFilter pratilipiFilter, String cursorStr, Integer offset, Integer resultCount ) {
		
		return getPratilipiList( pratilipiFilter, cursorStr, offset, resultCount, true );
	}
	
	@Override
	public DataListCursorTuple<Pratilipi> getPratilipiList(
			PratilipiFilter pratilipiFilter, String cursorStr, Integer resultCount ) {
		
		return getPratilipiList( pratilipiFilter, cursorStr, null, resultCount, false );
	}

	@SuppressWarnings("unchecked")
	private <T> DataListCursorTuple<T> getPratilipiList(
			PratilipiFilter pratilipiFilter, String cursorStr, Integer offset,
			Integer resultCount, boolean idOnly ) {
		
		if( pratilipiFilter.getListName() == null ) {
		
			GaeQueryBuilder gaeQueryBuilder =
					new GaeQueryBuilder( pm.newQuery( PratilipiEntity.class ) );
	
			if( pratilipiFilter.getAuthorId() != null )
				gaeQueryBuilder.addFilter( "authorId", pratilipiFilter.getAuthorId() );
			if( pratilipiFilter.getLanguage() != null )
				gaeQueryBuilder.addFilter( "language", pratilipiFilter.getLanguage() );
			if( pratilipiFilter.getType() != null )
				gaeQueryBuilder.addFilter( "type", pratilipiFilter.getType() );
			if( pratilipiFilter.getState() != null )
				gaeQueryBuilder.addFilter( "state", pratilipiFilter.getState() );
	
			if( pratilipiFilter.getNextProcessDateEnd() != null ) {
				gaeQueryBuilder.addFilter( "nextProcessDate", pratilipiFilter.getNextProcessDateEnd(), Operator.LESS_THAN_OR_EQUAL );
				gaeQueryBuilder.addOrdering( "nextProcessDate", true );
			}
			
			if( pratilipiFilter.getOrderByReadCount() != null )
				gaeQueryBuilder.addOrdering( "readCount", pratilipiFilter.getOrderByReadCount() );
			if( pratilipiFilter.getOrderByLastUpdate() != null )
				gaeQueryBuilder.addOrdering( "lastUpdated", pratilipiFilter.getOrderByLastUpdate() );
	
			if( idOnly )
				gaeQueryBuilder.setResult( "id" );
			
			gaeQueryBuilder.setCursor( cursorStr );
			
			if( offset != null && resultCount != null )
				gaeQueryBuilder.setRange( offset, offset + resultCount );
			else if( resultCount != null )
				gaeQueryBuilder.setRange( 0, resultCount );
				
			
			Query query = gaeQueryBuilder.build();
	
			
			List<T> pratilipiEntityList =
					(List<T>) query.executeWithMap( gaeQueryBuilder.getParamNameValueMap() );
			Cursor cursor = JDOCursorHelper.getCursor( pratilipiEntityList );
			
			return new DataListCursorTuple<T>(
					idOnly ? pratilipiEntityList : (List<T>) pm.detachCopyAll( pratilipiEntityList ),
					cursor == null ? null : cursor.toWebSafeString() );
			
		} else {
			
			int startIndex = cursorStr == null ? 0 : Integer.parseInt( cursorStr );
			List<T> responseList = new LinkedList<>();
			int numberFound = 0;
			
			int entitiesToSkip = startIndex + ( offset == null ? 0 : offset );
			try {
				
				String fileName = CURATED_DATA_FOLDER + "/list." + pratilipiFilter.getLanguage().getCode() + "." + pratilipiFilter.getListName();
				InputStream inputStream = DataAccessor.class.getResource( fileName ).openStream();
				List<String> uriList = IOUtils.readLines( inputStream, "UTF-8" );
				inputStream.close();

				uriList.remove( 0 ); // Removing the first line having title.
				for( String uri : uriList ) {
					
					uri = uri.trim();
					if( uri.isEmpty() )
						continue;
					
					Page page = getPage( uri );
					if( page == null || page.getType() != PageType.PRATILIPI )
						continue;
					
					numberFound++;
					
					if( entitiesToSkip > 0 ) {
						entitiesToSkip--;
						continue;
					}
					
					if( responseList.size() != resultCount ) { // resultCount could be null
						responseList.add( idOnly
								? (T) page.getPrimaryContentId()
								: (T) getPratilipi( page.getPrimaryContentId() ) );
					}
					
				}
				
			} catch( NullPointerException | IOException e ) {
				logger.log( Level.SEVERE, "Failed to fetch " + pratilipiFilter.getListName() + " list for " + pratilipiFilter.getLanguage() + ".", e );
			}

			cursorStr = resultCount == null || responseList.size() < resultCount ? null : startIndex + responseList.size() + "";
			
			return new DataListCursorTuple<T>( responseList, cursorStr, (long) numberFound );
			
		}
		
	}
	
	@Override
	public Pratilipi createOrUpdatePratilipi( Pratilipi pratilipi ) {
		return createOrUpdateEntity( pratilipi );
	}
	
	@Override
	public Pratilipi createOrUpdatePratilipi( Pratilipi pratilipi, AuditLog auditLog ) {
		return (Pratilipi) createOrUpdateEntities( pratilipi, auditLog )[ 0 ];
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
	public Author getAuthorByUserId( Long userId ) {
		Query query = new GaeQueryBuilder( pm.newQuery( AuthorEntity.class ) )
				.addFilter( "userId", userId )
				.addFilter( "state", AuthorState.DELETED, Operator.NOT_EQUALS )
				.addOrdering( "state", true )
				.addOrdering( "registrationDate", true )
				.build();
		
		@SuppressWarnings("unchecked")
		List<Author> authorList = (List<Author>) query.execute( userId, AuthorState.DELETED );
		
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
	public DataListCursorTuple<Long> getAuthorIdList( AuthorFilter authorFilter,
			String cursorStr, Integer resultCount ) {
		
		return getAuthorList( authorFilter, cursorStr, resultCount, true );
	}
	
	@Override
	public DataListCursorTuple<Author> getAuthorList( AuthorFilter authorFilter,
			String cursorStr, Integer resultCount ) {
		
		return getAuthorList( authorFilter, cursorStr, resultCount, false );
	}

	@SuppressWarnings("unchecked")
	private <T> DataListCursorTuple<T> getAuthorList( AuthorFilter authorFilter,
			String cursorStr, Integer resultCount, boolean idOnly ) {

		GaeQueryBuilder gaeQueryBuilder =
				new GaeQueryBuilder( pm.newQuery( AuthorEntity.class ) );
		
		if( authorFilter.getLanguage() != null )
			gaeQueryBuilder.addFilter( "language", authorFilter.getLanguage() );

		if( authorFilter.getMinLastUpdated() != null ) {
			gaeQueryBuilder.addFilter( "lastUpdated",
					authorFilter.getMinLastUpdated(),
					authorFilter.isMinLastUpdatedInclusive() ? Operator.GREATER_THAN_OR_EQUAL : Operator.GREATER_THAN );
			gaeQueryBuilder.addOrdering( "lastUpdated", true );
		}
		
		if( authorFilter.getNextProcessDateEnd() != null ) {
			gaeQueryBuilder.addFilter( "nextProcessDate", authorFilter.getNextProcessDateEnd(), Operator.LESS_THAN_OR_EQUAL );
			gaeQueryBuilder.addOrdering( "nextProcessDate", true );
		}
	
		if( authorFilter.getOrderByContentPublished() != null )
			gaeQueryBuilder.addOrdering( "contentPublished", authorFilter.getOrderByContentPublished() );
	
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
		
		List<T> authorEntityList = (List<T>) query.executeWithMap( gaeQueryBuilder.getParamNameValueMap() );
		Cursor cursor = JDOCursorHelper.getCursor( authorEntityList );
		
		return new DataListCursorTuple<T>(
				idOnly ? authorEntityList : (List<T>) pm.detachCopyAll( authorEntityList ),
				cursor == null ? null : cursor.toWebSafeString() );
	}
	
	@Override
	public Author createOrUpdateAuthor( Author author ) {
		return createOrUpdateEntity( author );
	}

	@Override
	public Author createOrUpdateAuthor( Author author, AuditLog auditLog ) {
		return (Author) createOrUpdateEntities( author, auditLog )[ 0 ];
	}

	
	// EVENT Table
	
	@Override
	public Event newEvent() {
		return new EventEntity();
	}

	@Override
	public Event getEvent( Long id ) {
		return getEntityOfy( EventEntity.class, id );
	}
	
	@Override
	public List<Event> getEventList( Language language ) {
		
		com.googlecode.objectify.cmd.Query<EventEntity> query
				= ObjectifyService.ofy().load().type( EventEntity.class );
		
		if( language != null )
			query = query.filter( "LANGUAGE", language );
		
		query = query.order( "-CREATION_DATE" );
		
		return new ArrayList<Event>( query.list() );
		
	}
	
	@Override
	public Event createOrUpdateEvent( Event event ) {
		return createOrUpdateEntityOfy( (EventEntity) event );
	}

	
	// BLOG Table
	
	@Override
	public Blog newBlog() {
		return new BlogEntity();
	}
	
	@Override
	public Blog getBlog( Long id ) {
		return getEntityOfy( BlogEntity.class, id );
	}
	
	@Override
	public Blog createOrUpdateBlog( Blog blog ) {
		return createOrUpdateEntityOfy( (BlogEntity) blog );
	}

	
	// BLOG_POST Table
	
	@Override
	public BlogPost newBlogPost() {
		return new BlogPostEntity();
	}
	
	@Override
	public BlogPost getBlogPost( Long id ) {
		return getEntityOfy( BlogPostEntity.class, id );
	}
	
	@Override
	public DataListCursorTuple<BlogPost> getBlogPostList(
			BlogPostFilter blogPostFilter, String cursorStr, Integer offset, Integer resultCount ) {

		com.googlecode.objectify.cmd.Query<BlogPostEntity> query
				= ObjectifyService.ofy().load().type( BlogPostEntity.class );
		
		if( blogPostFilter.getBlogId() != null )
			query = query.filter( "BLOG_ID", blogPostFilter.getBlogId() );
		
		if( blogPostFilter.getLanguage() != null )
			query = query.filter( "LANGUAGE", blogPostFilter.getLanguage() );
		
		if( blogPostFilter.getState() != null )
			query = query.filter( "STATE", blogPostFilter.getState() );
		
		query = query.order( "-CREATION_DATE" );
		
		if( cursorStr != null )
			query = query.startAt( Cursor.fromWebSafeString( cursorStr ) );
		
		if( offset != null && offset > 0 )
			query = query.offset( offset );
		
		if( resultCount != null && resultCount > 0 )
			query = query.limit( resultCount );
		

		QueryResultIterator<BlogPostEntity> iterator = query.iterator();

		
		// BlogPost List
		ArrayList<BlogPost> blogPostList = resultCount == null
				? new ArrayList<BlogPost>()
				: new ArrayList<BlogPost>( resultCount );
		
		while( iterator.hasNext() )
			blogPostList.add( iterator.next() );

		
		// Cursor
		Cursor cursor = iterator.getCursor();

		
		return new DataListCursorTuple<BlogPost>(
				blogPostList,
				cursor == null ? null : cursor.toWebSafeString() );
		
	}
	
	@Override
	public BlogPost createOrUpdateBlogPost( BlogPost blogPost ) {
		return createOrUpdateEntityOfy( (BlogPostEntity) blogPost );
	}

	
	// USER_PRATILIPI Table
	
	@Override
	public UserPratilipi newUserPratilipi() {
		return new UserPratilipiEntity();
	}
	
	@Override
	public UserPratilipi getUserPratilipi( Long userId, Long pratilipiId ) {
		if( userId == null || userId.equals( 0L ) || pratilipiId == null || pratilipiId.equals( 0L ) )
			return null;
		
		try {
			return getEntity( UserPratilipiEntity.class, userId + "-" + pratilipiId );
		} catch( JDOObjectNotFoundException e ) {
			return null;
		}
	}

	@Override
	public DataListCursorTuple<UserPratilipi> getPratilipiReviewList(
			Long pratilipiId, String cursorStr, Integer offset, Integer resultCount ) {
		
		GaeQueryBuilder queryBuilder =
				new GaeQueryBuilder( pm.newQuery( UserPratilipiEntity.class ) )
						.addFilter( "pratilipiId", pratilipiId )
						.addFilter( "reviewState", UserReviewState.PUBLISHED )
						.addOrdering( "reviewDate", false );
		
		if( cursorStr != null )
			queryBuilder.setCursor( cursorStr );
		
		if( offset != null && resultCount != null )
			queryBuilder.setRange( offset, offset + resultCount );
		else if( resultCount != null )
			queryBuilder.setRange( 0, resultCount );
		
		Query query = queryBuilder.build();
		
		@SuppressWarnings("unchecked")
		List<UserPratilipi> userPratilipiList =
				(List<UserPratilipi>) query.executeWithMap( queryBuilder.getParamNameValueMap() );
		Cursor cursor = JDOCursorHelper.getCursor( userPratilipiList );
		
		return new DataListCursorTuple<UserPratilipi>(
				(List<UserPratilipi>) pm.detachCopyAll( userPratilipiList ),
				cursor == null ? null : cursor.toWebSafeString() );
	}

	@Override
	public DataListCursorTuple<Long> getPratilipiIdList(
			UserPratilipiFilter userPratilipiFilter, String cursorStr,
			Integer offset, Integer resultCount ) {
		
		GaeQueryBuilder queryBuilder =
				new GaeQueryBuilder( pm.newQuery( UserPratilipiEntity.class ) );
		
		if( userPratilipiFilter.getUserId() != null )
			queryBuilder.addFilter( "userId", userPratilipiFilter.getUserId() );
		if( userPratilipiFilter.getPratilipiId() != null )
			queryBuilder.addFilter( "pratilipiId", userPratilipiFilter.getPratilipiId() );
		if( userPratilipiFilter.getAddedToLib() != null )
			queryBuilder.addFilter( "addedToLib", userPratilipiFilter.getAddedToLib() );

		if( userPratilipiFilter.getOrderByAddedToLibDate() != null )
			queryBuilder.addOrdering( "addedToLib", userPratilipiFilter.getOrderByAddedToLibDate() );

		queryBuilder.setCursor( cursorStr );
		
		if( offset != null && resultCount != null )
			queryBuilder.setRange( offset, offset + resultCount );
		else if( resultCount != null )
			queryBuilder.setRange( 0, resultCount );
		
		queryBuilder.setResult( "pratilipiId" );
		
		Query query = queryBuilder.build();
		
		@SuppressWarnings("unchecked")
		List<Long> pratilipiIdList =
				(List<Long>) query.executeWithMap( queryBuilder.getParamNameValueMap() );
		Cursor cursor = JDOCursorHelper.getCursor( pratilipiIdList );
		
		return new DataListCursorTuple<Long>(
				pratilipiIdList,
				cursor == null ? null : cursor.toWebSafeString() );
		
	}
	
	@Override
	public DataListCursorTuple<UserPratilipi> getUserPratilipiList( Long userId,
			Long pratilipiId, String cursorStr, Integer resultCount ) {
		
		GaeQueryBuilder queryBuilder =
				new GaeQueryBuilder( pm.newQuery( UserPratilipiEntity.class ) );
		
		if( userId != null )
			queryBuilder.addFilter( "userId", userId );
		if( pratilipiId != null )
			queryBuilder.addFilter( "pratilipiId", pratilipiId );
		if( cursorStr != null )
			queryBuilder.setCursor( cursorStr );
		if( resultCount != null )
			queryBuilder.setRange( 0, resultCount );
		
		Query query = queryBuilder.build();
		
		@SuppressWarnings("unchecked")
		List<UserPratilipi> userPratilipiList =
				(List<UserPratilipi>) query.executeWithMap( queryBuilder.getParamNameValueMap() );
		Cursor cursor = JDOCursorHelper.getCursor( userPratilipiList );
		
		return new DataListCursorTuple<UserPratilipi>(
				(List<UserPratilipi>) pm.detachCopyAll( userPratilipiList ),
				cursor == null ? null : cursor.toWebSafeString() );
	}

	@Override
	public UserPratilipi createOrUpdateUserPratilipi( UserPratilipi userPratilipi ) {
		( (UserPratilipiEntity) userPratilipi ).setId( userPratilipi.getUserId() + "-" + userPratilipi.getPratilipiId() );
		return createOrUpdateEntity( userPratilipi );
	}
	
	
	// USER_AUTHOR Table
	
	@Override
	public UserAuthor newUserAuthor() {
		return new UserAuthorEntity();
	}
	
	@Override
	public UserAuthor getUserAuthor( Long userId, Long authorId ) {
		if( userId == null || userId.equals( 0L ) || authorId == null || authorId.equals( 0L ) )
			return null;
		return getEntityOfy( UserAuthorEntity.class, userId + "-" + authorId );
	}

	@Override
	public DataListCursorTuple<UserAuthor> getUserAuthorList( Long userId, Long authorId, String cursorStr, Integer resultCount ) {

		GaeQueryBuilder queryBuilder =
				new GaeQueryBuilder( pm.newQuery( UserAuthorEntity.class ) );
		
		if( userId != null )
			queryBuilder.addFilter( "userId", userId );
		if( authorId != null )
			queryBuilder.addFilter( "authorId", authorId );
		if( cursorStr != null )
			queryBuilder.setCursor( cursorStr );
		if( resultCount != null )
			queryBuilder.setRange( 0, resultCount );
		
		Query query = queryBuilder.build();
		
		@SuppressWarnings("unchecked")
		List<UserAuthor> userPratilipiList =
				(List<UserAuthor>) query.executeWithMap( queryBuilder.getParamNameValueMap() );
		Cursor cursor = JDOCursorHelper.getCursor( userPratilipiList );
		
		return new DataListCursorTuple<UserAuthor>(
				(List<UserAuthor>) pm.detachCopyAll( userPratilipiList ),
				cursor == null ? null : cursor.toWebSafeString() );
		
	}

	@Override
	public UserAuthor createOrUpdateUserAuthor( UserAuthor userAuthor ) {
		UserAuthorEntity userAuthorEntity = (UserAuthorEntity) userAuthor;
		userAuthorEntity.setId( userAuthor.getUserId() + "-" + userAuthor.getAuthorId() );
		return createOrUpdateEntityOfy( userAuthorEntity );
	}
	
	
	// NAVIGATION Table
	
	@Override
	public List<Navigation> getNavigationList( Language language ) {

		List<Navigation> navigationList = new LinkedList<>();

		try {
			File file = new File( DataAccessor.class.getResource( NAVIGATION_DATA_FILE_PREFIX + language.getCode() ).toURI() );
			LineIterator it = FileUtils.lineIterator( file, "UTF-8" );
			
			Navigation navigation = new NavigationEntity();
			while( it.hasNext() ) {
				String line = it.nextLine().trim();
				
				if( navigation.getTitle() == null && line.isEmpty() )
					continue;
				
				else if( navigation.getTitle() == null && ! line.isEmpty() )
					navigation.setTitle( line );
				
				else if( navigation.getTitle() != null && line.isEmpty() ) {
					navigationList.add( navigation );
					navigation = new NavigationEntity();
				}
				
				else if( navigation.getTitle() != null && ! line.isEmpty() ) {
					Navigation.Link link = null;
					if( line.indexOf( ' ' ) == -1 )
						link = new Navigation.Link( line, line );
					else
						link = new Navigation.Link( 
								line.substring( line.indexOf( ' ' ) + 1 ).trim(),
								line.substring( 0, line.indexOf( ' ' ) )
						);
					navigation.addLink( link );
				}
			}
			
			if( navigation.getTitle() != null )
				navigationList.add( navigation );
			
			LineIterator.closeQuietly( it );
		} catch( URISyntaxException | NullPointerException | IOException e ) {
			logger.log( Level.SEVERE, "Failed to fetch " + language.getNameEn() + " navigation list.", e );
		}
		
		return navigationList;
	
	}


	// CATEGORY Table
	
	@Override
	public Category getCategory( Long id ) {
		if( id == null )
			return null;
		
		try {
			return getEntity( CategoryEntity.class, id );
		} catch ( JDOObjectNotFoundException e ) {
			logger.log( Level.SEVERE, "Category Id " + id + " is not found!" );
		}
		return null;
	}
	
	@Override
	public List<Category> getCategoryList( Language language ) {

		List<Category> categoryList = new LinkedList<>();

		try {
			Gson gson = new Gson();
			File file = new File( DataAccessor.class.getResource( CATEGORY_DATA_FILE_PREFIX + language.getCode() ).toURI() );
			LineIterator it = FileUtils.lineIterator( file, "UTF-8" );
			while( it.hasNext() ) {
				String categoryStr = it.nextLine();
				String categoryName = categoryStr.substring( 0, categoryStr.indexOf( '{' ) ).trim();
				String pratilipiFilterJson = categoryStr.substring( categoryStr.indexOf( '{' ) ).trim();
				PratilipiFilter pratilipiFilter = gson.fromJson( pratilipiFilterJson, PratilipiFilter.class );
				categoryList.add( new CategoryEntity( categoryName, pratilipiFilter ) );
			}
			LineIterator.closeQuietly( it );
		} catch( URISyntaxException | NullPointerException | JsonSyntaxException | IOException e ) {
			logger.log( Level.SEVERE, "Failed to fetch " + language.getNameEn() + " category list.", e );
		}
		
		return categoryList;
	
	}
	
	
	//PRATILIPI_CATEGORY Table
	
	@Override
	public List<PratilipiCategory> getPratilipiCategoryList( Long pratilipiId ){
		if( pratilipiId == null )
			return null;
		
		try {
			Query query = 
					new GaeQueryBuilder( pm.newQuery( PratilipiCategoryEntity.class ))
			.addFilter( "pratilipiId", pratilipiId )
			.build();
			
			@SuppressWarnings( "unchecked" )
			List<PratilipiCategory> pratilipiCategoryEntityList = ( List<PratilipiCategory> ) query.execute( pratilipiId );
			return (List<PratilipiCategory>) pm.detachCopyAll( pratilipiCategoryEntityList );
		} catch( JDOObjectNotFoundException e ) {
			logger.log( Level.INFO, "No Category is present for pratilipi id " + pratilipiId );
		}
		return null;
	}

	
	// Destroy

	@Override
	public void destroy() {
		pm.close();
	}

}
