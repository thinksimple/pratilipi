package com.pratilipi.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JDOCursorHelper;
import com.pratilipi.common.type.PageType;
import com.pratilipi.common.util.AuthorFilter;
import com.pratilipi.common.util.PratilipiFilter;
import com.pratilipi.data.GaeQueryBuilder.Operator;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.type.AppProperty;
import com.pratilipi.data.type.AuditLog;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Category;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.PratilipiCategory;
import com.pratilipi.data.type.User;
import com.pratilipi.data.type.UserPratilipi;
import com.pratilipi.data.type.gae.AccessTokenEntity;
import com.pratilipi.data.type.gae.AppPropertyEntity;
import com.pratilipi.data.type.gae.AuditLogEntity;
import com.pratilipi.data.type.gae.AuthorEntity;
import com.pratilipi.data.type.gae.CategoryEntity;
import com.pratilipi.data.type.gae.PageEntity;
import com.pratilipi.data.type.gae.PratilipiCategoryEntity;
import com.pratilipi.data.type.gae.PratilipiEntity;
import com.pratilipi.data.type.gae.UserEntity;
import com.pratilipi.data.type.gae.UserPratilipiEntity;

public class DataAccessorGaeImpl implements DataAccessor {

	private static final Logger logger =
			Logger.getLogger( DataAccessorGaeImpl.class.getName() );

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
	public User getUserByFacebookId( String facebookId ) {
		Query query = new GaeQueryBuilder( pm.newQuery( UserEntity.class ) )
				.addFilter( "facebookId", facebookId )
				.build();
		
		@SuppressWarnings("unchecked")
		List<User> userList = (List<User>) query.execute( facebookId );

		if( userList.size() > 1 )
			logger.log( Level.SEVERE, userList.size() + " Users found with facebook Id " + facebookId   + " ." );

		return userList.size() == 0 ? null : pm.detachCopy( userList.get( 0 ) );
	}

	@Override
	public User createOrUpdateUser( User user ) {
		return createOrUpdateEntity( user );
	}

	@Override
	public DataListCursorTuple<User> getUserList( String cursor, Integer resultCount ) {
		return getUserList( cursor, resultCount, true);
	}
	
	@SuppressWarnings("unchecked")
	private <T> DataListCursorTuple<T> getUserList( String cursorStr, Integer resultCount, boolean idOnly ) {

		GaeQueryBuilder gaeQueryBuilder =
				new GaeQueryBuilder( pm.newQuery( UserEntity.class ) );
		
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
		
		List<T> userEntityList = (List<T>) query.executeWithMap( gaeQueryBuilder.getParamNameValueMap() );
		Cursor cursor = JDOCursorHelper.getCursor( userEntityList );
		
		return new DataListCursorTuple<T>(
				idOnly ? userEntityList : (List<T>) pm.detachCopyAll( userEntityList ),
				cursor == null ? null : cursor.toWebSafeString() );
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
		GaeQueryBuilder gaeQueryBuilder = new GaeQueryBuilder( pm.newQuery( AccessTokenEntity.class ) );
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
			Long pratilipiId, String cursorStr, Integer resultCount ) {
		
		GaeQueryBuilder queryBuilder =
				new GaeQueryBuilder( pm.newQuery( UserPratilipiEntity.class ) )
						.addFilter( "pratilipiId", pratilipiId )
						.addFilter( "reviewDate", null, Operator.NOT_NULL )
						.addOrdering( "reviewDate", false );
		
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
	

	//CATEGORY Table
	
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
