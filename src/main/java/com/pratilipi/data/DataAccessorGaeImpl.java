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
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import com.pratilipi.common.type.AccessType;
import com.pratilipi.common.type.AuthorState;
import com.pratilipi.common.type.CommentParentType;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.MailingList;
import com.pratilipi.common.type.PageType;
import com.pratilipi.common.type.ReferenceType;
import com.pratilipi.common.type.UserState;
import com.pratilipi.common.type.VoteParentType;
import com.pratilipi.common.util.AuthorFilter;
import com.pratilipi.common.util.BlogPostFilter;
import com.pratilipi.common.util.PratilipiFilter;
import com.pratilipi.common.util.SystemProperty;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.type.AppProperty;
import com.pratilipi.data.type.AuditLog;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Blog;
import com.pratilipi.data.type.BlogPost;
import com.pratilipi.data.type.Category;
import com.pratilipi.data.type.Comment;
import com.pratilipi.data.type.Event;
import com.pratilipi.data.type.GenericOfyType;
import com.pratilipi.data.type.MailingListSubscription;
import com.pratilipi.data.type.Navigation;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.User;
import com.pratilipi.data.type.UserAuthor;
import com.pratilipi.data.type.UserPratilipi;
import com.pratilipi.data.type.Vote;
import com.pratilipi.data.type.gae.AccessTokenEntity;
import com.pratilipi.data.type.gae.AppPropertyEntity;
import com.pratilipi.data.type.gae.AuditLogEntityOfy;
import com.pratilipi.data.type.gae.AuthorEntity;
import com.pratilipi.data.type.gae.BlogEntity;
import com.pratilipi.data.type.gae.BlogPostEntity;
import com.pratilipi.data.type.gae.CategoryEntity;
import com.pratilipi.data.type.gae.CommentEntity;
import com.pratilipi.data.type.gae.EventEntity;
import com.pratilipi.data.type.gae.MailingListSubscriptionEntity;
import com.pratilipi.data.type.gae.NavigationEntity;
import com.pratilipi.data.type.gae.PageEntity;
import com.pratilipi.data.type.gae.PratilipiEntity;
import com.pratilipi.data.type.gae.UserAuthorEntity;
import com.pratilipi.data.type.gae.UserEntity;
import com.pratilipi.data.type.gae.UserPratilipiEntity;
import com.pratilipi.data.type.gae.VoteEntity;

public class DataAccessorGaeImpl implements DataAccessor {

	private static final Logger logger =
			Logger.getLogger( DataAccessorGaeImpl.class.getName() );
	
	private static final Memcache memcache = new MemcacheGaeImpl();

	private static final String CURATED_DATA_FOLDER = "curated";
	
	
	// Registering Entities
	
	static {

		ObjectifyService.register( AppPropertyEntity.class );
		
		ObjectifyService.register( UserEntity.class );
		ObjectifyService.register( AccessTokenEntity.class );
		ObjectifyService.register( AuditLogEntityOfy.class );
		
		ObjectifyService.register( PageEntity.class );
		ObjectifyService.register( PratilipiEntity.class );
		ObjectifyService.register( AuthorEntity.class );
		ObjectifyService.register( EventEntity.class );
		
		ObjectifyService.register( BlogEntity.class );
		ObjectifyService.register( BlogPostEntity.class );
		
		ObjectifyService.register( UserPratilipiEntity.class );
		ObjectifyService.register( UserAuthorEntity.class );
		
		ObjectifyService.register( CommentEntity.class );
		ObjectifyService.register( VoteEntity.class );
		
		ObjectifyService.register( MailingListSubscriptionEntity.class );
		
	}
	
	
	// Objectify Helper Methods
	
	private <T extends GenericOfyType> T getEntityOfy( Class<T> clazz, Long id ) {
		return id == null ? null : ObjectifyService.ofy().load().type( clazz ).id( id ).now();
	}
	
	private <T extends GenericOfyType> T getEntityOfy( Class<T> clazz, String id ) {
		return id == null ? null : ObjectifyService.ofy().load().type( clazz ).id( id ).now();
	}
	
	private <P extends GenericOfyType,Q extends P> List<P> getEntityListOfy( Class<Q> clazz, List<Long> idList ) {
		Map<Long, Q> entityMap = ObjectifyService.ofy().load().type( clazz ).ids( idList );
		List<P> entityList = new ArrayList<>();
		for( Long id : idList )
			entityList.add( entityMap.get( id ) );
		return entityList;
	}
	
	private <T extends GenericOfyType> T createOrUpdateEntityOfy( T entity ) {
		Key<T> key = ObjectifyService.ofy().save().entity( entity ).now();
		entity.setKey( key );
		return entity;
	}
	
	@SuppressWarnings("unchecked")
	private <T extends GenericOfyType> T createOrUpdateEntityOfy( T entityA, T entityB ) {
		Map<Key<T>, T> map = ObjectifyService.ofy().save().entities( entityA, entityB ).now();
		for( Key<T> key : map.keySet() )
			map.get( key ).setKey( key );
		return entityA;
	}
	
	private <T extends GenericOfyType> T createOrUpdateEntityOfy( T entity, AuditLog auditLog ) {
		return createOrUpdateEntityOfy( entity, null, auditLog );
	}
	
	private <T extends GenericOfyType> T createOrUpdateEntityOfy( T entity, Page page, AuditLog auditLog ) {
		
		if( entity.getKey() == null ) {
			
			if( page == null ) {
				Key<T> key = ObjectifyService.ofy().save().entity( entity ).now();
				entity.setKey( key );
			} else {
				Map<Key<GenericOfyType>, GenericOfyType> map = ObjectifyService.ofy().save().entities( entity, page ).now();
				for( Key<GenericOfyType> key : map.keySet() )
					map.get( key ).setKey( key );
			}
			
			auditLog.setEventDataNew( entity );
			auditLog.setCreationDate( new Date() );
			
			Key<AuditLog> key = ObjectifyService.ofy().save().entity( auditLog ).now();
			auditLog.setKey( key );
			
		} else {
			
			auditLog.setEventDataNew( entity );
			auditLog.setCreationDate( new Date() );
			
			Map<Key<GenericOfyType>, GenericOfyType> map = page == null
					? ObjectifyService.ofy().save().entities( entity, auditLog ).now()
					: ObjectifyService.ofy().save().entities( entity, page, auditLog ).now();
			for( Key<GenericOfyType> key : map.keySet() )
				map.get( key ).setKey( key );
			
		}

		
		if( page != null )
			_createOrUpdatePageMemcache( page ); // Updating additional page memcache ids

		
		return entity;
		
	}

	private void deleteEntityOfy( GenericOfyType entity ) {
		ObjectifyService.ofy().delete().entity( entity ).now();
	}

	
	// APP_PROPERTY Table
	
	@Override
	public AppProperty newAppProperty( String id ) {
		return new AppPropertyEntity( id );
	}

	@Override
	public AppProperty getAppProperty( String id ) {
		return getEntityOfy( AppPropertyEntity.class, id );
	}
	
	@Override
	public AppProperty createOrUpdateAppProperty( AppProperty appProperty ) {
		return createOrUpdateEntityOfy( appProperty );
	}
	
	
	// USER Table
	
	@Override
	public User newUser() {
		return new UserEntity();
	}
	
	@Override
	public User getUser( Long id ) {
		return getEntityOfy( UserEntity.class, id );
	}
	
	@Override
	public User getUserByEmail( String email ) {
		
		String memcacheId = "DataStore.User-" + email;
		
		User user = memcache.get( memcacheId );
		if( user != null )
			return user;
		
		
		user = ObjectifyService.ofy().load()
				.type( UserEntity.class )
				.filter( "EMAIL", email )
				.filter( "STATE !=", UserState.DELETED )
				.order( "STATE" )
				.order( "SIGN_UP_DATE" )
				.first().now();
		
		if( user != null )
			memcache.put( memcacheId, user );
		
		
		return user;
		
	}
	
	@Override
	public User getUserByFacebookId( String facebookId ) {
		
		String memcacheId = "DataStore.User-" + "fb::" + facebookId;
		
		User user = memcache.get( memcacheId );
		if( user != null )
			return user;
		
		
		user = ObjectifyService.ofy().load()
				.type( UserEntity.class )
				.filter( "FACEBOOK_ID", facebookId )
				.filter( "STATE !=", UserState.DELETED )
				.order( "STATE" )
				.order( "SIGN_UP_DATE" )
				.first().now();
		
		if( user != null )
			memcache.put( memcacheId, user );
		
		
		return user;
		
	}

	@Override
	public List<User> getUserList( List<Long> idList ) {
		return getEntityListOfy( UserEntity.class, idList );
	}
	
	@Override
	public DataListCursorTuple<User> getUserList( String cursor, Integer resultCount ) {
		return getUserList( cursor, resultCount, false );
	}
	
	@SuppressWarnings("unchecked")
	private <T> DataListCursorTuple<T> getUserList( String cursorStr, Integer resultCount, boolean idOnly ) {
		
		Query<UserEntity> query = ObjectifyService.ofy().load().type( UserEntity.class );
		
		if( cursorStr != null )
			query = query.startAt( Cursor.fromWebSafeString( cursorStr ) );
		
		if( resultCount != null && resultCount > 0 )
			query = query.limit( resultCount );

		
		List<T> responseList = resultCount == null ? new ArrayList<T>() : new ArrayList<T>( resultCount );
		Cursor cursor = null;
		if( idOnly ) {
			QueryResultIterator <Key<UserEntity>> iterator = query.keys().iterator();
			while( iterator.hasNext() )
				responseList.add( (T) (Long) iterator.next().getId() );
			cursor = iterator.getCursor();
		} else {
			QueryResultIterator <UserEntity> iterator = query.iterator();
			while( iterator.hasNext() )
				responseList.add( (T) iterator.next() );
			cursor = iterator.getCursor();
		}
		
		
		return new DataListCursorTuple<T>( responseList, cursor == null ? null : cursor.toWebSafeString() );
		
	}

	@Override
	public User createOrUpdateUser( User user ) {
		return createOrUpdateEntityOfy( user, null );
	}

	@Override
	public User createOrUpdateUser( User user, AuditLog auditLog ) {
		
		user = auditLog == null
				? createOrUpdateEntityOfy( user )
				: createOrUpdateEntityOfy( user, auditLog );
		
		if( user.getEmail() != null ) {
			String memcacheId = "DataStore.User-" + user.getEmail();
			if( user.getState() == UserState.DELETED )
				memcache.remove( memcacheId );
			else
				memcache.put( memcacheId, user );
		}
		
		if( user.getFacebookId() != null ) {
			String memcacheId = "DataStore.User-" + "fb::" + user.getFacebookId();
			if( user.getState() == UserState.DELETED )
				memcache.remove( memcacheId );
			else
				memcache.put(memcacheId, user );
		}
		
		return user;

	}
	
	
	// ACCESS_TOKEN Table

	@Override
	public AccessToken newAccessToken() {
		return new AccessTokenEntity();
	}

	@Override
	public AccessToken getAccessToken( String accessTokenId ) {
		return getEntityOfy( AccessTokenEntity.class, accessTokenId );
	}
	
	@Override
	public DataListCursorTuple<AccessToken> getAccessTokenList( Long userId, Date minExpiry, String cursorStr, Integer resultCount ) {
		
		com.googlecode.objectify.cmd.Query<AccessTokenEntity> query
				= ObjectifyService.ofy().load().type( AccessTokenEntity.class );
		
		if( userId != null )
			query = query.filter( "USER_ID", userId );
		
		if( minExpiry != null ) {
			query = query.filter( "EXPIRY >=", minExpiry );
			query = query.order( "EXPIRY" );
		}
		
		query = query.order( "CREATION_DATE" );
		
		
		if( cursorStr != null )
			query = query.startAt( Cursor.fromWebSafeString( cursorStr ) );
		
		if( resultCount != null && resultCount > 0 )
			query = query.limit( resultCount );

		
		List<AccessToken> accessTokenList = resultCount == null
				? new ArrayList<AccessToken>()
				: new ArrayList<AccessToken>( resultCount );
		
		
		QueryResultIterator <AccessTokenEntity> iterator = query.iterator();
		while( iterator.hasNext() )
			accessTokenList.add( iterator.next() );
		Cursor cursor = iterator.getCursor();
				
		
		return new DataListCursorTuple<AccessToken>( accessTokenList, cursor == null ? null : cursor.toWebSafeString() );
		
	}
	
	@Override
	public AccessToken createOrUpdateAccessToken( AccessToken accessToken ) {
		return createOrUpdateEntityOfy( accessToken );
	}
	
	@Override
	public AccessToken createOrUpdateAccessToken( AccessToken newAccessToken, AccessToken oldAccessToken ) {
		return createOrUpdateEntityOfy( newAccessToken, oldAccessToken );
	}

	
	// AUDIT_LOG Table
	
	@Override
	public AuditLog newAuditLogOfy() {
		return new AuditLogEntityOfy();
	}
	
	@Override
	public AuditLog newAuditLogOfy( String accessId, AccessType accessType, Object eventDataOld ) {
		return new AuditLogEntityOfy( accessId, accessType, eventDataOld );
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
		page = createOrUpdateEntityOfy( page );
		_createOrUpdatePageMemcache( page );
		return page;
	}
	
	public void deletePage( Page page ) {
		deleteEntityOfy( page );
		if( page.getUri() != null )
			memcache.remove( _createPageEntityMemcacheId( page.getUri() ) );
		if( page.getUriAlias() != null )
			memcache.remove( _createPageEntityMemcacheId( page.getUriAlias() ) );
		if( page.getPrimaryContentId() != null )
			memcache.remove( _createPageEntityMemcacheId( page.getType(), page.getPrimaryContentId() ) );
	}
	
	private void _createOrUpdatePageMemcache( Page page ) {
		if( page.getUri() != null )
			memcache.put( _createPageEntityMemcacheId( page.getUri() ), page );
		if( page.getUriAlias() != null )
			memcache.put( _createPageEntityMemcacheId( page.getUriAlias() ), page );
		if( page.getPrimaryContentId() != null )
			memcache.put( _createPageEntityMemcacheId( page.getType(), page.getPrimaryContentId() ), page );
	}
	
	private String _createPageEntityMemcacheId( String uri ) {
		return "DataStore.Page-" + uri;
	}
	
	private String _createPageEntityMemcacheId( PageType pageType, Long primaryContentId ) {
		return "DataStore.Page-" + pageType + "::" + primaryContentId;
	}
	
	
	// PRATILIPI Table & curated/list.<list-name>.<lang>

	@Override
	public Pratilipi newPratilipi() {
		return new PratilipiEntity();
	}

	@Override
	public Pratilipi getPratilipi( Long id ) {
		return getEntityOfy( PratilipiEntity.class, id );
	}

	@Override
	public String getPratilipiListTitle( String listName, Language lang ) {
		String fileName = "list." + lang.getCode() + "." + listName;
		String listTitle = null;
		try {
			InputStream inputStream = DataAccessor.class.getResource( CURATED_DATA_FOLDER + "/" + fileName ).openStream();
			LineIterator it = IOUtils.lineIterator( inputStream, "UTF-8" );
			listTitle = it.nextLine().trim();
			LineIterator.closeQuietly( it );
		} catch( NullPointerException | IOException e ) {
			logger.log( Level.SEVERE, "Exception while reading from " + listName + " .", e );
		}
		return listTitle;
	}
	
	@Override
	public List<Pratilipi> getPratilipiList( List<Long> idList ) {
		return getEntityListOfy( PratilipiEntity.class, idList );
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
			PratilipiFilter pratilipiFilter, String cursorStr, Integer offset, Integer resultCount, boolean idOnly ) {
		
		if( pratilipiFilter.getListName() == null ) {
		
			com.googlecode.objectify.cmd.Query<PratilipiEntity> query
					= ObjectifyService.ofy().load().type( PratilipiEntity.class );
	
			if( pratilipiFilter.getAuthorId() != null )
				query = query.filter( "AUTHOR_ID", pratilipiFilter.getAuthorId() );
			if( pratilipiFilter.getLanguage() != null )
				query = query.filter( "LANGUAGE", pratilipiFilter.getLanguage() );
			if( pratilipiFilter.getType() != null )
				query = query.filter( "PRATILIPI_TYPE", pratilipiFilter.getType() );
			if( pratilipiFilter.getState() != null )
				query = query.filter( "STATE", pratilipiFilter.getState() );
	
			if( pratilipiFilter.getMinLastUpdated() != null ) {
				query = query.filter(
						pratilipiFilter.isMinLastUpdatedInclusive() ? "LAST_UPDATED >= " : "LAST_UPDATED > ",
						pratilipiFilter.getMinLastUpdated() );
				query = query.order( "LAST_UPDATED" );
			}
			
			if( pratilipiFilter.getMaxNextProcessDate() != null ) {
				query = query.filter(
						pratilipiFilter.isMaxNextProcessDateInclusive() ? "NEXT_PROCESS_DATE <= " : "NEXT_PROCESS_DATE < ",
						pratilipiFilter.getMaxNextProcessDate() );
				query = query.order( "NEXT_PROCESS_DATE" );
			}
			
			if( pratilipiFilter.getOrderByReadCount() != null )
				query = query.order( pratilipiFilter.getOrderByReadCount() ? "READ_COUNT" : "-READ_COUNT" );
			if( pratilipiFilter.getOrderByLastUpdate() != null )
				query = query.order( pratilipiFilter.getOrderByLastUpdate() ? "LAST_UPDATED" : "-LAST_UPDATED" );
	
			
			if( cursorStr != null )
				query = query.startAt( Cursor.fromWebSafeString( cursorStr ) );
			if( offset != null && offset > 0 )
				query = query.offset( offset );
			if( resultCount != null && resultCount > 0 )
				query = query.limit( resultCount );
				
			
			List<T> responseList = resultCount == null ? new ArrayList<T>() : new ArrayList<T>( resultCount );
			Cursor cursor = null;
			if( idOnly ) {
				QueryResultIterator <Key<PratilipiEntity>> iterator = query.keys().iterator();
				while( iterator.hasNext() )
					responseList.add( (T) (Long) iterator.next().getId() );
				cursor = iterator.getCursor();
			} else {
				QueryResultIterator <PratilipiEntity> iterator = query.iterator();
				while( iterator.hasNext() )
					responseList.add( (T) iterator.next() );
				cursor = iterator.getCursor();
			}
			
			return new DataListCursorTuple<T>( responseList, cursor == null ? null : cursor.toWebSafeString() );
			
		} else {
			
			try {
				
				String fileName = CURATED_DATA_FOLDER + "/list." + pratilipiFilter.getLanguage().getCode() + "." + pratilipiFilter.getListName();
				InputStream inputStream = DataAccessor.class.getResource( fileName ).openStream();
				List<String> uriList = IOUtils.readLines( inputStream, "UTF-8" );
				inputStream.close();

				// Removing the first line having title.
				uriList.remove( 0 );
				
				// Removing empty lines.
				for( int i = 0; i < uriList.size(); i++ ) {
					String uri = uriList.get( i ).trim();
					if( uri.isEmpty() ) {
						uriList.remove( i );
						i--;
						continue;
					} else {
						uriList.set( i, uri );
					}
				}

				// Fetching Pratilipi pages.
				Map<String, Page> pratilipiPages = getPages( uriList );
				
				// Pratilipi id list.
				List<Long> pratilipiIdList = new ArrayList<>( uriList.size() );
				for( int i = 0; i < uriList.size(); i++ ) {
					Page page = pratilipiPages.get( uriList.get( i ) );
					if( page != null && page.getType() == PageType.PRATILIPI && ! pratilipiIdList.contains( page.getPrimaryContentId() ) )
						pratilipiIdList.add( page.getPrimaryContentId() );
				}

				
				offset = cursorStr == null ? 0 : Integer.parseInt( cursorStr )
						+ ( offset == null || offset < 0 ? 0 : offset );
				
				offset = Math.min( offset, pratilipiIdList.size() );
				
				resultCount = resultCount == null || resultCount > pratilipiIdList.size() - offset
						? pratilipiIdList.size() - offset : resultCount;
				
				
				List<T> responseList = idOnly
						? (List<T>) pratilipiIdList.subList( offset, offset + resultCount )
						: (List<T>) getPratilipiList( pratilipiIdList.subList( offset, offset + resultCount ) );
				
				return new DataListCursorTuple<T>( responseList, offset + resultCount + "", (long) pratilipiIdList.size() );
				
			} catch( NullPointerException | IOException e ) {
				logger.log( Level.SEVERE, "Failed to fetch " + pratilipiFilter.getListName() + " list for " + pratilipiFilter.getLanguage() + ".", e );
				return new DataListCursorTuple<T>( new ArrayList<T>( 0 ), "0", 0L );
			}

		}
		
	}
	
	@Override
	public Pratilipi createOrUpdatePratilipi( Pratilipi pratilipi, AuditLog auditLog ) {
		return createOrUpdatePratilipi( pratilipi, null, auditLog );
	}

	@Override
	public Pratilipi createOrUpdatePratilipi( Pratilipi pratilipi, Page page, AuditLog auditLog ) {
		pratilipi = createOrUpdateEntityOfy( pratilipi, page, auditLog );
		// Hack for backward compatibility with Mark-4.
		// TODO: Remove it as soon as mark-4 is deprecated.
		memcache.remove( "Pratilipi-" + pratilipi.getId() );
		return pratilipi;
	}

	
	// AUTHOR Table
	
	@Override
	public Author newAuthor() {
		return new AuthorEntity();
	}

	@Override
	public Author getAuthor( Long id ) {
		return getEntityOfy( AuthorEntity.class, id );
	}
	
	@Override
	public Author getAuthorByUserId( Long userId ) {
		
		com.googlecode.objectify.cmd.Query<AuthorEntity> query
				= ObjectifyService.ofy().load().type( AuthorEntity.class );
		
		query = query.filter( "USER_ID", userId );
		query = query.filter( "STATE !=", AuthorState.DELETED );
		query = query.order( "STATE" );
		query = query.order( "REGISTRATION_DATE" );
		
		return query.first().now();
		
	}
	
	@Override
	public List<Author> getAuthorList( List<Long> idList ) {
		return getEntityListOfy( AuthorEntity.class, idList );
	}
	
	@Override
	public DataListCursorTuple<Long> getAuthorIdList( AuthorFilter authorFilter, String cursorStr, Integer resultCount ) {
		return _getAuthorList( authorFilter, cursorStr, resultCount, true );
	}
	
	@Override
	public DataListCursorTuple<Author> getAuthorList( AuthorFilter authorFilter, String cursorStr, Integer resultCount ) {
		return _getAuthorList( authorFilter, cursorStr, resultCount, false );
	}

	@SuppressWarnings("unchecked")
	private <T> DataListCursorTuple<T> _getAuthorList( AuthorFilter authorFilter, String cursorStr, Integer resultCount, boolean idOnly ) {

		com.googlecode.objectify.cmd.Query<AuthorEntity> query
				= ObjectifyService.ofy().load().type( AuthorEntity.class );
		
		if( authorFilter.getLanguage() != null )
			query = query.filter( "LANGUAGE", authorFilter.getLanguage() );

		if( authorFilter.getMinLastUpdated() != null ) {
			query = query.filter(
					authorFilter.isMinLastUpdatedInclusive() ? "LAST_UPDATED >=" : "LAST_UPDATED >",
					authorFilter.getMinLastUpdated() );
			query = query.order( "LAST_UPDATED" );
		}
		
		if( authorFilter.getOrderByContentPublished() != null )
			query = query.order( authorFilter.getOrderByContentPublished() ? "CONTENT_PUBLISHED" : "-CONTENT_PUBLISHED" );
	
		if( resultCount != null )
			query = query.limit( resultCount );
	
		if( cursorStr != null )
			query = query.startAt( Cursor.fromWebSafeString( cursorStr ) );

		
		List<T> responseList = resultCount == null ? new ArrayList<T>() : new ArrayList<T>( resultCount );
		Cursor cursor = null;
		if( idOnly ) {
			QueryResultIterator <Key<AuthorEntity>> iterator = query.keys().iterator();
			while( iterator.hasNext() )
				responseList.add( (T) (Long) iterator.next().getId() );
			cursor = iterator.getCursor();
		} else {
			QueryResultIterator <AuthorEntity> iterator = query.iterator();
			while( iterator.hasNext() )
				responseList.add( (T) iterator.next() );
			cursor = iterator.getCursor();
		}

		return new DataListCursorTuple<T>( responseList, cursor == null ? null : cursor.toWebSafeString() );
		
	}
	
	@Override
	public Author createOrUpdateAuthor( Author author ) {
		return createOrUpdateEntityOfy( author );
	}

	@Override
	public Author createOrUpdateAuthor( Author author, AuditLog auditLog ) {
		return createOrUpdateEntityOfy( author, auditLog );
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
	public Event createOrUpdateEvent( Event event, AuditLog auditLog ) {
		return createOrUpdateEvent( event, null, auditLog );
	}
	
	@Override
	public Event createOrUpdateEvent( Event event, Page page, AuditLog auditLog ) {
		return createOrUpdateEntityOfy( event, page, auditLog );
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
	public Blog createOrUpdateBlog( Blog blog, AuditLog auditLog ) {
		return createOrUpdateEntityOfy( blog, auditLog );
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
	public BlogPost createOrUpdateBlogPost( BlogPost blogPost, AuditLog auditLog ) {
		return createOrUpdateBlogPost( blogPost, null, auditLog );
	}
	
	@Override
	public BlogPost createOrUpdateBlogPost( BlogPost blogPost, Page page, AuditLog auditLog ) {
		return createOrUpdateEntityOfy( blogPost, page, auditLog );
	}

	
	// USER_PRATILIPI Table
	
	@Override
	public UserPratilipi newUserPratilipi() {
		return new UserPratilipiEntity();
	}
	
	@Override
	public UserPratilipi getUserPratilipi( String userPratilipiId ) {
		return getEntityOfy( UserPratilipiEntity.class, userPratilipiId );
	}
	
	@Override
	public UserPratilipi getUserPratilipi( Long userId, Long pratilipiId ) {
		if( userId == null || userId.equals( 0L ) || pratilipiId == null || pratilipiId.equals( 0L ) )
			return null;
		return getEntityOfy( UserPratilipiEntity.class, userId + "-" + pratilipiId );
	}

	@Override
	public DataListCursorTuple<Long> getUserLibrary( Long userId, String cursorStr, Integer offset, Integer resultCount ) {
		
		com.googlecode.objectify.cmd.Query<UserPratilipiEntity> query
				= ObjectifyService.ofy().load().type( UserPratilipiEntity.class );
				
		query = query.filter( "USER_ID", userId );
		query = query.filter( "ADDED_TO_LIB", true );
		query = query.order( "-ADDED_TO_LIB" );
		
		if( cursorStr != null )
			query = query.startAt( Cursor.fromWebSafeString( cursorStr ) );
		
		if( offset != null && offset > 0 )
			query = query.offset( offset );
		
		if( resultCount != null && resultCount > 0 )
			query = query.limit( resultCount );
		
		
		QueryResultIterator<UserPratilipiEntity> iterator = query.iterator();
		
		
		// Pratilipi id list
		ArrayList<Long> pratilipiIdList = resultCount == null
				? new ArrayList<Long>()
				: new ArrayList<Long>( resultCount );
		
		while( iterator.hasNext() )
			pratilipiIdList.add( iterator.next().getPratilipiId() );
		
		
		// Cursor
		Cursor cursor = iterator.getCursor();
		
		
		return new DataListCursorTuple<Long>(
				pratilipiIdList,
				cursor == null ? null : cursor.toWebSafeString() );
		
	}
	
	@Override
	public DataListCursorTuple<UserPratilipi> getUserPratilipiList( Long userId,
			Long pratilipiId, String cursorStr, Integer resultCount ) {
		
		com.googlecode.objectify.cmd.Query<UserPratilipiEntity> query
				= ObjectifyService.ofy().load().type( UserPratilipiEntity.class );
				
		if( userId != null )
			query = query.filter( "USER_ID", userId );

		if( pratilipiId != null )
			query = query.filter( "PRATILIPI_ID", pratilipiId );
		
		if( cursorStr != null )
			query = query.startAt( Cursor.fromWebSafeString( cursorStr ) );
		
		if( resultCount != null && resultCount > 0 )
			query = query.limit( resultCount );
		
		
		QueryResultIterator<UserPratilipiEntity> iterator = query.iterator();

		
		// UserPratilipi List
		ArrayList<UserPratilipi> userPratilipiList = resultCount == null
				? new ArrayList<UserPratilipi>()
				: new ArrayList<UserPratilipi>( resultCount );
		
		while( iterator.hasNext() )
			userPratilipiList.add( iterator.next() );

		
		// Cursor
		Cursor cursor = iterator.getCursor();

		
		return new DataListCursorTuple<UserPratilipi>(
				userPratilipiList,
				cursor == null ? null : cursor.toWebSafeString() );

	}

	@Override
	public UserPratilipi createOrUpdateUserPratilipi( UserPratilipi userPratilipi, AuditLog auditLog ) {
		( (UserPratilipiEntity) userPratilipi ).setId( userPratilipi.getUserId() + "-" + userPratilipi.getPratilipiId() );
		return createOrUpdateEntityOfy( userPratilipi, auditLog );
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
	public DataListCursorTuple<Long> getUserAuthorFollowList( Long userId, Long authorId, String cursorStr, Integer offset, Integer resultCount ) {

		com.googlecode.objectify.cmd.Query<UserAuthorEntity> query
				= ObjectifyService.ofy().load().type( UserAuthorEntity.class );
			
		if( userId != null )
			query = query.filter( "USER_ID", userId );

		if( authorId != null )
			query = query.filter( "AUTHOR_ID", authorId );
		
		query = query.filter( "FOLLOWING", true );
		query = query.order( "-FOLLOWING" );
		
		if( cursorStr != null )
			query = query.startAt( Cursor.fromWebSafeString( cursorStr ) );
		
		if( offset != null && offset > 0 )
			query = query.offset( offset );
		
		if( resultCount != null && resultCount > 0 )
			query = query.limit( resultCount );
		
		
		QueryResultIterator<UserAuthorEntity> iterator = query.iterator();

		
		// UserAuthor List
		ArrayList<Long> idList = resultCount == null
				? new ArrayList<Long>()
				: new ArrayList<Long>( resultCount );
		
		while( iterator.hasNext() ) {
			if( userId != null )
				idList.add( iterator.next().getAuthorId() );
			else if( authorId != null )
				idList.add( iterator.next().getUserId() );
		}
		
		
		// Cursor
		Cursor cursor = iterator.getCursor();

		
		return new DataListCursorTuple<Long>(
				idList,
				cursor == null ? null : cursor.toWebSafeString() );
		
	}

	@Override
	public DataListCursorTuple<UserAuthor> getUserAuthorList(
			Long userId, Long authorId, String cursorStr, Integer offset, Integer resultCount ) {

		com.googlecode.objectify.cmd.Query<UserAuthorEntity> query
				= ObjectifyService.ofy().load().type( UserAuthorEntity.class );
				
		if( userId != null )
			query = query.filter( "USER_ID", userId );

		if( authorId != null )
			query = query.filter( "AUTHOR_ID", authorId );
		
		if( cursorStr != null )
			query = query.startAt( Cursor.fromWebSafeString( cursorStr ) );
		
		if( offset != null && offset > 0 )
			query = query.offset( offset );
		
		if( resultCount != null && resultCount > 0 )
			query = query.limit( resultCount );
		
		
		QueryResultIterator<UserAuthorEntity> iterator = query.iterator();

		
		// UserAuthor List
		ArrayList<UserAuthor> userAuthorList = resultCount == null
				? new ArrayList<UserAuthor>()
				: new ArrayList<UserAuthor>( resultCount );
		
		while( iterator.hasNext() )
			userAuthorList.add( iterator.next() );

		
		// Cursor
		Cursor cursor = iterator.getCursor();

		
		return new DataListCursorTuple<UserAuthor>(
				userAuthorList,
				cursor == null ? null : cursor.toWebSafeString() );
		
	}

	@Override
	public UserAuthor createOrUpdateUserAuthor( UserAuthor userAuthor, AuditLog auditLog ) {
		( (UserAuthorEntity) userAuthor ).setId( userAuthor.getUserId() + "-" + userAuthor.getAuthorId() );
		return createOrUpdateEntityOfy( userAuthor, auditLog );
	}
	
	
	// curated/home.<lang>
	
	@Override
	public List<String> getHomeSectionList( Language language ) {

		List<String> sectionList = new LinkedList<>();

		try {
			File file = new File( DataAccessor.class.getResource( CURATED_DATA_FOLDER + "/home." + language.getCode() ).toURI() );
			LineIterator it = FileUtils.lineIterator( file, "UTF-8" );
			while( it.hasNext() ) {
				String listName = it.next().trim();
				if( ! listName.isEmpty() )
					sectionList.add( listName );
			}
			LineIterator.closeQuietly( it );
		} catch( URISyntaxException | NullPointerException | IOException e ) {
			logger.log( Level.SEVERE, "Exception while reading from home." + language.getNameEn() + " .", e );
		}
		
		return sectionList;
	
	}
	
	
	// NAVIGATION Table
	
	@Override
	public List<Navigation> getNavigationList( Language language ) {

		String memcacheId = "CuratedData.NavigationList-" + language.getCode()
				+ "?" + ( new Date().getTime() / TimeUnit.MINUTES.toMillis( 15 ) )
				+ "/" + SystemProperty.STAGE;
		
		ArrayList<Navigation> navigationList = memcache.get( memcacheId );
		if( navigationList != null )
			return navigationList;
		

		List<String> lines = null;
		try {
			String fileName = CURATED_DATA_FOLDER + "/navigation." + language.getCode();
			InputStream inputStream = DataAccessor.class.getResource( fileName ).openStream();
			lines = IOUtils.readLines( inputStream, "UTF-8" );
			inputStream.close();
		} catch( NullPointerException | IOException e ) {
			logger.log( Level.SEVERE, "Failed to fetch " + language.getNameEn() + " navigation list.", e );
			lines = new ArrayList<>( 0 );
		}


		navigationList = new ArrayList<>( lines.size() );
		
		Navigation navigation = new NavigationEntity();
		for( String line : lines ) {
			
			line = line.trim();
			
			if( navigation.getTitle() == null && line.isEmpty() )
				continue;
			
			else if( navigation.getTitle() == null && ! line.isEmpty() )
				navigation.setTitle( line );
			
			else if( navigation.getTitle() != null && line.isEmpty() ) {
				navigationList.add( navigation );
				navigation = new NavigationEntity();
			
			} else if( navigation.getTitle() != null && ! line.isEmpty() ) {
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
		
		
		memcache.put( memcacheId, navigationList );

		
		return navigationList;
	
	}


	// CATEGORY Table
	
	@Override
	public List<Category> getCategoryList( Language language ) {
		
		String memcacheId = "CuratedData.CategoryList-" + language.getCode()
				+ "?" + ( new Date().getTime() / TimeUnit.MINUTES.toMillis( 15 ) )
				+ "/" + SystemProperty.STAGE;

		ArrayList<Category> categoryList = memcache.get( memcacheId );
		if( categoryList != null )
			return categoryList;
		
		
		List<String> lines = null;
		try {
			String fileName = CURATED_DATA_FOLDER + "/category." + language.getCode();
			InputStream inputStream = DataAccessor.class.getResource( fileName ).openStream();
			lines = IOUtils.readLines( inputStream, "UTF-8" );
			inputStream.close();
		} catch( NullPointerException | IOException e ) {
			logger.log( Level.SEVERE, "Failed to fetch " + language.getNameEn() + " navigation list.", e );
			lines = new ArrayList<>( 0 );
		}

		
		Gson gson = new Gson();
		
		categoryList = new ArrayList<Category>( lines.size() );
		for( String categoryStr : lines ) {
			if( categoryStr.trim().isEmpty() )
				continue;
			String categoryName = categoryStr.substring( 0, categoryStr.indexOf( '{' ) ).trim();
			String pratilipiFilterJson = categoryStr.substring( categoryStr.indexOf( '{' ) ).trim();
			PratilipiFilter pratilipiFilter = gson.fromJson( pratilipiFilterJson, PratilipiFilter.class );
			categoryList.add( new CategoryEntity( categoryName, pratilipiFilter ) );
		}

		
		memcache.put( memcacheId, categoryList );

		return categoryList;
	
	}
	
	
	// COMMENT Table
	
	@Override
	public Comment newComment() {
		return new CommentEntity();
	}
	
	@Override
	public Comment getComment( Long commentId ) {
		return getEntityOfy( CommentEntity.class, commentId );
	}
	
	@Override
	public List<Comment> getCommentList( CommentParentType parentType, Long parentId ) {
		return getCommentList( parentType, parentId.toString() );
	}
	
	@Override
	public List<Comment> getCommentList( CommentParentType parentType, String parentId ) {
		
		com.googlecode.objectify.cmd.Query<CommentEntity> query
				= ObjectifyService.ofy().load().type( CommentEntity.class );
		
		query = query.filter( "PARENT_TYPE", parentType );
		query = query.filter( "PARENT_ID", parentId );
		query = query.order( "-CREATION_DATE" );
		
		return new ArrayList<Comment>( query.list() );
		
	}
	
	@Override
	public List<Comment> getCommentListByReference( ReferenceType referenceType, Long referenceId ) {
		return getCommentListByReference( referenceType, referenceId.toString() );
	}
	
	@Override
	public List<Comment> getCommentListByReference( ReferenceType referenceType, String referenceId ) {
		
		com.googlecode.objectify.cmd.Query<CommentEntity> query
				= ObjectifyService.ofy().load().type( CommentEntity.class );
		
		query = query.filter( "REFERENCE_TYPE", referenceType );
		query = query.filter( "REFERENCE_ID", referenceId );
		query = query.order( "-CREATION_DATE" );
		
		return new ArrayList<Comment>( query.list() );
		
	}

	@Override
	public Comment createOrUpdateComment( Comment comment, AuditLog auditLog ) {
		return createOrUpdateEntityOfy( comment, auditLog );
	}
	
	
	// USER_VOTE Table
	
	@Override
	public Vote newVote() {
		return new VoteEntity();
	}
	
	@Override
	public Vote getVote( Long userId, VoteParentType parentType, String parentId ) {
		if( userId == null || userId.equals( 0L ) || parentType == null || parentId == null || parentId.isEmpty() )
			return null;
		return getEntityOfy( VoteEntity.class, userId + "-" + parentType + "::" + parentId );
	}
	
	@Override
	public List<Vote> getVoteListByReference( ReferenceType referenceType, Long referenceId ) {
		return getVoteListByReference( referenceType, referenceId.toString() );
	}
	
	@Override
	public List<Vote> getVoteListByReference( ReferenceType referenceType, String referenceId ) {
		
		com.googlecode.objectify.cmd.Query<VoteEntity> query
				= ObjectifyService.ofy().load().type( VoteEntity.class );
		
		query = query.filter( "REFERENCE_TYPE", referenceType );
		query = query.filter( "REFERENCE_ID", referenceId );
		query = query.order( "-CREATION_DATE" );
		
		return new ArrayList<Vote>( query.list() );
		
	}

	@Override
	public Vote createOrUpdateVote( Vote vote, AuditLog auditLog ) {
		( (VoteEntity) vote ).setId( vote.getUserId() + "-" + vote.getParentType() + "::" + vote.getParentId() );
		return createOrUpdateEntityOfy( vote, auditLog );
	}

	
	// MAILING_LIST_SUBSCRIPTION Table

	@Override
	public MailingListSubscription newMailingListSubscription() {
		return new MailingListSubscriptionEntity();
	}
	
	@Override
	public MailingListSubscription getMailingListSubscription( MailingList mailingList, String email ) {
		
		com.googlecode.objectify.cmd.Query<MailingListSubscriptionEntity> query
				= ObjectifyService.ofy().load().type( MailingListSubscriptionEntity.class );
		
		query = query.filter( "MAILING_LIST", mailingList );
		query = query.filter( "EMAIL", email );
		query = query.order( "SUBSCRIPTION_DATE" );
		
		return query.first().now();
		
	}
	
	@Override
	public MailingListSubscription createOrUpdateMailingListSubscription( MailingListSubscription mailingListSubscription, AuditLog auditLog ) {
		return createOrUpdateEntityOfy( mailingListSubscription, auditLog );
	}
	
}
