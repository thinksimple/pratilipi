package com.pratilipi.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import com.pratilipi.common.type.BatchProcessState;
import com.pratilipi.common.type.BatchProcessType;
import com.pratilipi.common.type.CommentParentType;
import com.pratilipi.common.type.ContactTeam;
import com.pratilipi.common.type.EmailState;
import com.pratilipi.common.type.EmailType;
import com.pratilipi.common.type.I18nGroup;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.MailingList;
import com.pratilipi.common.type.NotificationState;
import com.pratilipi.common.type.NotificationType;
import com.pratilipi.common.type.PageType;
import com.pratilipi.common.type.ReferenceType;
import com.pratilipi.common.type.UserFollowState;
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
import com.pratilipi.data.type.BatchProcess;
import com.pratilipi.data.type.Blog;
import com.pratilipi.data.type.BlogPost;
import com.pratilipi.data.type.Category;
import com.pratilipi.data.type.Comment;
import com.pratilipi.data.type.Conversation;
import com.pratilipi.data.type.ConversationMessage;
import com.pratilipi.data.type.ConversationUser;
import com.pratilipi.data.type.Email;
import com.pratilipi.data.type.Event;
import com.pratilipi.data.type.GenericOfyType;
import com.pratilipi.data.type.I18n;
import com.pratilipi.data.type.MailingListSubscription;
import com.pratilipi.data.type.Navigation;
import com.pratilipi.data.type.Notification;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.User;
import com.pratilipi.data.type.UserAuthor;
import com.pratilipi.data.type.UserPratilipi;
import com.pratilipi.data.type.Vote;
import com.pratilipi.data.type.gae.AccessTokenEntity;
import com.pratilipi.data.type.gae.AppPropertyEntity;
import com.pratilipi.data.type.gae.AuditLogEntity;
import com.pratilipi.data.type.gae.AuthorEntity;
import com.pratilipi.data.type.gae.BatchProcessEntity;
import com.pratilipi.data.type.gae.BlogEntity;
import com.pratilipi.data.type.gae.BlogPostEntity;
import com.pratilipi.data.type.gae.CategoryEntity;
import com.pratilipi.data.type.gae.CommentEntity;
import com.pratilipi.data.type.gae.ConversationEntity;
import com.pratilipi.data.type.gae.ConversationMessageEntity;
import com.pratilipi.data.type.gae.ConversationUserEntity;
import com.pratilipi.data.type.gae.EmailEntity;
import com.pratilipi.data.type.gae.EventEntity;
import com.pratilipi.data.type.gae.I18nEntity;
import com.pratilipi.data.type.gae.MailingListSubscriptionEntity;
import com.pratilipi.data.type.gae.NavigationEntity;
import com.pratilipi.data.type.gae.NotificationEntity;
import com.pratilipi.data.type.gae.PageEntity;
import com.pratilipi.data.type.gae.PratilipiEntity;
import com.pratilipi.data.type.gae.UserAuthorEntity;
import com.pratilipi.data.type.gae.UserEntity;
import com.pratilipi.data.type.gae.UserPratilipiEntity;
import com.pratilipi.data.type.gae.VoteEntity;
import com.pratilipi.filter.UxModeFilter;

public class DataAccessorGaeImpl implements DataAccessor {

	private static final Logger logger =
			Logger.getLogger( DataAccessorGaeImpl.class.getName() );
	
	private static final String CURATED_DATA_FOLDER = "curated";
	
	private final Memcache memcache;
	
	
	// Registering Entities
	
	static {

		ObjectifyService.register( AppPropertyEntity.class );
		
		ObjectifyService.register( UserEntity.class );
		ObjectifyService.register( AccessTokenEntity.class );
		ObjectifyService.register( AuditLogEntity.class );
		
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
		
		ObjectifyService.register( ConversationEntity.class );
		ObjectifyService.register( ConversationUserEntity.class );
		ObjectifyService.register( ConversationMessageEntity.class );
		
		ObjectifyService.register( MailingListSubscriptionEntity.class );
		
		ObjectifyService.register( NotificationEntity.class );
		
		ObjectifyService.register( I18nEntity.class );
		
		ObjectifyService.register( BatchProcessEntity.class );
		
		ObjectifyService.register( EmailEntity.class );
		
	}
	
	
	public DataAccessorGaeImpl( Memcache memcache ) {
		this.memcache = memcache;
	}

	
	// Objectify Helper Methods
	
	private <T extends GenericOfyType> T getEntity( Class<T> clazz, Long id ) {
		return id == null || id == 0L ? null : ObjectifyService.ofy().load().type( clazz ).id( id ).now();
	}
	
	private <T extends GenericOfyType> T getEntity( Class<T> clazz, String id ) {
		return id == null || id.isEmpty() ? null : ObjectifyService.ofy().load().type( clazz ).id( id ).now();
	}
	
	private <P extends GenericOfyType, Q extends P, R> Map<R, P> getEntities( Class<Q> clazz, Collection<R> ids ) {
		Map<R, Q> entityMap = ObjectifyService.ofy().load().type( clazz ).ids( ids );
		Map<R, P> returnMap = new HashMap<>( entityMap.size() );
		for( R id : ids )
			returnMap.put( id, entityMap.get( id ) );
		return returnMap;
	}
	
	private <P extends GenericOfyType, Q extends P, R> List<P> getEntityList( Class<Q> clazz, List<R> idList ) {
		Map<R, Q> entityMap = ObjectifyService.ofy().load().type( clazz ).ids( idList );
		List<P> entityList = new ArrayList<>();
		for( R id : idList )
			entityList.add( entityMap.get( id ) );
		return entityList;
	}
	
	private <T extends GenericOfyType> T createOrUpdateEntity( T entity ) {
		Key<T> key = ObjectifyService.ofy().save().entity( entity ).now();
		entity.setKey( key );
		return entity;
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	private <T extends GenericOfyType> T createOrUpdateEntity( T entityA, T entityB ) {
		Map<Key<T>, T> map = ObjectifyService.ofy().save().entities( entityA, entityB ).now();
		for( Key<T> key : map.keySet() )
			map.get( key ).setKey( key );
		return entityA;
	}
	
	private <T extends GenericOfyType> T createOrUpdateEntity( T entity, AuditLog auditLog ) {
		return createOrUpdateEntity( entity, null, auditLog );
	}
	
	private <T extends GenericOfyType> T createOrUpdateEntity( T entity, Page page, AuditLog auditLog ) {
		
		if( entity.getKey() == null ) {
			
			if( page == null ) {
				Key<T> key = ObjectifyService.ofy().save().entity( entity ).now();
				entity.setKey( key );
			} else {
				Map<Key<GenericOfyType>, GenericOfyType> map = ObjectifyService.ofy().save().entities( entity, page ).now();
				for( Key<GenericOfyType> key : map.keySet() )
					map.get( key ).setKey( key );
			}
			
			if( entity.getKey().getName() == null )
				auditLog.setPrimaryContentId( entity.getKey().getId() );
			else
				auditLog.setPrimaryContentId( entity.getKey().getName() );
			
			auditLog.setEventDataNew( entity );
			auditLog.setCreationDate( new Date() );
			
			Key<AuditLog> key = ObjectifyService.ofy().save().entity( auditLog ).now();
			auditLog.setKey( key );
			
		} else {
			
			if( entity.getKey().getName() == null )
				auditLog.setPrimaryContentId( entity.getKey().getId() );
			else
				auditLog.setPrimaryContentId( entity.getKey().getName() );
			
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

	private <T extends GenericOfyType> List<T> createOrUpdateEntityList( List<T> entityList ) {
		
		if( entityList.size() != 0 ) {
			Map<Key<T>, T> map = ObjectifyService.ofy().save().entities( entityList ).now();
			for( Key<T> key : map.keySet() )
				map.get( key ).setKey( key );
		}
		
		return entityList;
		
	}
	
	private void deleteEntity( GenericOfyType entity ) {
		ObjectifyService.ofy().delete().entity( entity ).now();
	}

	private void deleteEntityList( List<? extends Key<? extends GenericOfyType>> keyList ) {
		ObjectifyService.ofy().delete().keys( keyList ).now();
	}

	
	// APP_PROPERTY Table
	
	@Override
	public AppProperty newAppProperty( String id ) {
		return new AppPropertyEntity( id );
	}

	@Override
	public AppProperty getAppProperty( String id ) {
		return getEntity( AppPropertyEntity.class, id );
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
		return getEntity( UserEntity.class, id );
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
		
		String memcacheId = "DataStore.User-fb::" + facebookId;
		
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
	public User getUserByGoogleId( String googleId ) {
		
		String memcacheId = "DataStore.User-google::" + googleId;
		
		User user = memcache.get( memcacheId );
		if( user != null )
			return user;
		
		
		user = ObjectifyService.ofy().load()
				.type( UserEntity.class )
				.filter( "GOOGLE_ID", googleId )
				.filter( "STATE !=", UserState.DELETED )
				.order( "STATE" )
				.order( "SIGN_UP_DATE" )
				.first().now();
		
		if( user != null )
			memcache.put( memcacheId, user );
		
		
		return user;
		
	}

	@Override
	public Map<Long, User> getUsers( Collection<Long> idList ) {
		return getEntities( UserEntity.class, idList );
	}
	
	@Override
	public List<User> getUserList( List<Long> idList ) {
		return getEntityList( UserEntity.class, idList );
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

		
		List<T> dataList = resultCount == null ? new ArrayList<T>() : new ArrayList<T>( resultCount );
		Cursor cursor = null;
		if( idOnly ) {
			QueryResultIterator <Key<UserEntity>> iterator = query.keys().iterator();
			while( iterator.hasNext() )
				dataList.add( (T) (Long) iterator.next().getId() );
			cursor = iterator.getCursor();
		} else {
			QueryResultIterator <UserEntity> iterator = query.iterator();
			while( iterator.hasNext() )
				dataList.add( (T) iterator.next() );
			cursor = iterator.getCursor();
		}
		
		
		return new DataListCursorTuple<T>( dataList, cursor == null ? null : cursor.toWebSafeString() );
		
	}

	@Override
	public User createOrUpdateUser( User user ) {
		return createOrUpdateUser( user, null );
	}

	@Override
	public User createOrUpdateUser( User user, AuditLog auditLog ) {
		
		user = auditLog == null // TODO: Remove this condition as soon as "User createOrUpdateUser( User user )" method is removed.
				? createOrUpdateEntity( user )
				: createOrUpdateEntity( user, auditLog );
		
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
		return getEntity( AccessTokenEntity.class, accessTokenId );
	}
	
	@Override
	public List<String> getFcmTokenList( Long userId ) {
	
		if( userId == null || userId == 0L )
			return null;
		
		List<AccessTokenEntity> accessTokenList = ObjectifyService.ofy().load()
				.type( AccessTokenEntity.class )
				.filter( "USER_ID", userId )
				.filter( "EXPIRY >", new Date() )
				.list();
		
		List<String> fcmTokenList = new ArrayList<>( accessTokenList.size() );
		
		for( AccessToken accessToken : accessTokenList )
			if( accessToken.getFcmToken() != null && ! fcmTokenList.contains( accessToken.getFcmToken() ) )
				fcmTokenList.add( accessToken.getFcmToken() );
		
		return fcmTokenList;

	}

	
	@Override
	public AccessToken createOrUpdateAccessToken( AccessToken accessToken ) {
		return createOrUpdateEntity( accessToken );
	}
	
	public int deleteExpiredAccessTokenList( Integer count ) {
		
		QueryResultIterator<Key<AccessTokenEntity>> itr = ObjectifyService.ofy().load()
				.type( AccessTokenEntity.class )
				.filter( "EXPIRY <", new Date() )
				.chunk( count == null || count > 1000 ? 1000 : count )
				.keys()
				.iterator();

		List<Key<AccessTokenEntity>> list = count == null
				? new LinkedList<Key<AccessTokenEntity>>()
				: new ArrayList<Key<AccessTokenEntity>>( count );
				
		while( itr.hasNext() && ( count == null || list.size() < count ) )
			list.add( itr.next() );

		deleteEntityList( list );
		
		return list.size();
		
	}
	
	
	// AUDIT_LOG Table
	
	@Override
	public AuditLog newAuditLog( AccessToken accessToken, AccessType accessType, Object eventDataOld ) {
		return new AuditLogEntity( accessToken.getUserId(), accessToken.getId(), accessType, eventDataOld );
	}
	
	@Override
	public DataListCursorTuple<AuditLog> getAuditLogList( Date minCreationDate, String cursorStr, Integer resultCount ) {
		
		Query<AuditLogEntity> query = ObjectifyService.ofy().load().type( AuditLogEntity.class );
		
		if( minCreationDate != null ) {
			query = query.filter( "CREATION_DATE >=", minCreationDate );
			query = query.order( "CREATION_DATE" );
		}
		
		if( cursorStr != null )
			query = query.startAt( Cursor.fromWebSafeString( cursorStr ) );

		if( resultCount != null )
			query = query.limit( resultCount );
	
		
		List<AuditLog> responseList = resultCount == null ? new ArrayList<AuditLog>() : new ArrayList<AuditLog>( resultCount );
		QueryResultIterator <AuditLogEntity> iterator = query.iterator();
		while( iterator.hasNext() )
			responseList.add( iterator.next() );
		Cursor cursor = iterator.getCursor();

		return new DataListCursorTuple<AuditLog>(
				responseList,
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
		if( page != null )
			return page;
		
		page = ObjectifyService.ofy().load()
				.type( PageEntity.class )
				.filter( "PAGE_TYPE", pageType )
				.filter( "PRIMARY_CONTENT_ID", primaryContentId )
				.order( "CREATION_DATE" )
				.first().now();
		
		if( page != null )
			memcache.put( memcacheId, page );
		
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
		_createOrUpdatePageMemcache( page );
		return page;
	}
	
	public void deletePage( Page page ) {
		deleteEntity( page );
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
		return getEntity( PratilipiEntity.class, id );
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
	public Map<Long, Pratilipi> getPratilipis( Collection<Long> pratilipiIds ) {
		return getEntities( PratilipiEntity.class, pratilipiIds );
	}
	
	@Override
	public List<Pratilipi> getPratilipiList( List<Long> idList ) {
		return getEntityList( PratilipiEntity.class, idList );
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
		
			Query<PratilipiEntity> query = ObjectifyService.ofy().load().type( PratilipiEntity.class );
	
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
						pratilipiFilter.isMinLastUpdatedInclusive() ? "LAST_UPDATED >=" : "LAST_UPDATED >",
						pratilipiFilter.getMinLastUpdated() );
				query = query.order( "LAST_UPDATED" );
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
			
			List<String> uriList = null;
			try {
				String fileName = CURATED_DATA_FOLDER + "/list." + pratilipiFilter.getLanguage().getCode() + "." + pratilipiFilter.getListName();
				InputStream inputStream = DataAccessor.class.getResource( fileName ).openStream();
				uriList = IOUtils.readLines( inputStream, "UTF-8" );
				inputStream.close();
			} catch( NullPointerException | IOException e ) {
				logger.log( Level.SEVERE, "Failed to fetch " + pratilipiFilter.getListName() + " list for " + pratilipiFilter.getLanguage() + ".", e );
				return new DataListCursorTuple<T>( new ArrayList<T>( 0 ), "0", 0L );
			}

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

			
			offset = ( cursorStr == null ? 0 : Integer.parseInt( cursorStr ) )
					+ ( offset == null || offset < 0 ? 0 : offset );
			
			offset = Math.min( offset, pratilipiIdList.size() );
			
			resultCount = resultCount == null || resultCount > pratilipiIdList.size() - offset
					? pratilipiIdList.size() - offset : resultCount;
			
			
			List<T> responseList = idOnly
					? (List<T>) pratilipiIdList.subList( offset, offset + resultCount )
					: (List<T>) getPratilipiList( pratilipiIdList.subList( offset, offset + resultCount ) );
			
			return new DataListCursorTuple<T>( responseList, offset + resultCount + "", (long) pratilipiIdList.size() );
			
		}
		
	}
	
	@Override
	public Pratilipi createOrUpdatePratilipi( Pratilipi pratilipi, AuditLog auditLog ) {
		return createOrUpdatePratilipi( pratilipi, null, auditLog );
	}

	@Override
	public Pratilipi createOrUpdatePratilipi( Pratilipi pratilipi, Page page, AuditLog auditLog ) {
		pratilipi = createOrUpdateEntity( pratilipi, page, auditLog );
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
		return getEntity( AuthorEntity.class, id );
	}
	
	@Override
	public Author getAuthorByUserId( Long userId ) {
		
		if( userId == null || userId == 0L )
			return null;
		
		
		String memcacheId = _createAuthorEntityMemcacheId( userId );
		
		Author author = memcache.get( memcacheId );
		if( author != null )
			return author;
		
		
		author = ObjectifyService.ofy().load()
				.type( AuthorEntity.class )
				.filter( "USER_ID", userId )
				.filter( "STATE !=", AuthorState.DELETED )
				.order( "STATE" )
				.order( "REGISTRATION_DATE" )
				.first().now();
		
		if( author != null )
			memcache.put( memcacheId, author );
		
		return author;
		
	}
	
	@Override
	public Map<Long, Author> getAuthors( Collection<Long> authorIds ) {
		return getEntities( AuthorEntity.class, authorIds );	
	}
	
	@Override
	public Map<Long, Author> getAuthorsByUserIds( Collection<Long> userIds ) {

		Set<String> memcacheIds = new HashSet<>( userIds.size() );
		for( Long userId : userIds )
			memcacheIds.add( _createAuthorEntityMemcacheId( userId ) );
		
		Map<String, Author> memcacheAuthors = memcache.getAll( memcacheIds );
		
		Map<Long, Author> authors = new HashMap<>();
		for( Long userId : userIds ) {
			Author author = memcacheAuthors.get( _createAuthorEntityMemcacheId( userId ) );
			if( author == null )
				author = getAuthorByUserId( userId );
			authors.put( userId, author );
		}

		return authors;
		
	}

	@Override
	public List<Author> getAuthorList( List<Long> idList ) {
		return getEntityList( AuthorEntity.class, idList );
	}

	@Override
	public List<Long> getAuthorIdListWithMaxReadCount( Language language, Long minReadCount, Integer resultCount ) {

		if( resultCount == null )
			resultCount = 1000;
		
		
		QueryResultIterator<Key<AuthorEntity>> readCountIterator = ObjectifyService.ofy().load()
				.type( AuthorEntity.class )
				.filter( "LANGUAGE", language )
				.filter( "STATE", AuthorState.ACTIVE )
				.filter( "TOTAL_READ_COUNT >=", minReadCount )
				.order( "-TOTAL_READ_COUNT" )
				.chunk( resultCount < 1000 ? resultCount : 1000 )
//				.limit( resultCount ) // .limit(int) is not honored in case of .list() / .iterator() for .keys()
				.keys()
				.iterator();

		List<Long> tempAuthorIdList = new ArrayList<>( resultCount );
		while( readCountIterator.hasNext() && tempAuthorIdList.size() < resultCount )
			tempAuthorIdList.add( readCountIterator.next().getId() );

		
		QueryResultIterator<Key<AuthorEntity>> followCountIterator = ObjectifyService.ofy().load()
				.type( AuthorEntity.class )
				.filter( "LANGUAGE", language )
				.filter( "STATE", AuthorState.ACTIVE )
				.filter( "FOLLOW_COUNT >=", 1 )
				.order( "-FOLLOW_COUNT" )
				.chunk( 1000 )
				.keys()
				.iterator();

		List<Long> authorIdList = new ArrayList<>( resultCount );
		while( ! tempAuthorIdList.isEmpty() && followCountIterator.hasNext() ) {
			Long authorId = followCountIterator.next().getId();
			if( ! tempAuthorIdList.contains( authorId ) )
				continue;
			authorIdList.add( authorId );
			tempAuthorIdList.remove( authorId );
		}

		
		authorIdList.addAll( tempAuthorIdList );
		
		return authorIdList;

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

		Query<AuthorEntity> query = ObjectifyService.ofy().load().type( AuthorEntity.class );
		
		if( authorFilter.getLanguage() != null )
			query = query.filter( "LANGUAGE", authorFilter.getLanguage() );

		if( authorFilter.getState() != null )
			query = query.filter( "STATE", authorFilter.getState() );

		if( authorFilter.getMinLastUpdated() != null ) {
			query = query.filter(
					authorFilter.isMinLastUpdatedInclusive() ? "LAST_UPDATED >=" : "LAST_UPDATED >",
					authorFilter.getMinLastUpdated() );
			query = query.order( "LAST_UPDATED" );
		}
		
		if( authorFilter.getOrderByContentPublished() != null )
			query = query.order( authorFilter.getOrderByContentPublished() ? "CONTENT_PUBLISHED" : "-CONTENT_PUBLISHED" );
	
		if( cursorStr != null )
			query = query.startAt( Cursor.fromWebSafeString( cursorStr ) );

		if( resultCount != null )
			query = query.limit( resultCount );
	
		
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
		return createOrUpdateEntity( author );
	}

	@Override
	public Author createOrUpdateAuthor( Author author, AuditLog auditLog ) {
		return createOrUpdateAuthor( author, null, auditLog );
	}

	@Override
	public Author createOrUpdateAuthor( Author author, Page page, AuditLog auditLog ) {
		author = createOrUpdateEntity( author, page, auditLog );
		if( author.getUserId() != null ) {
			String memcacheId = _createAuthorEntityMemcacheId( author.getUserId() );
			if( author.getState() == AuthorState.DELETED )
				memcache.remove( memcacheId );
			else
				memcache.put( memcacheId, author );
		}
		return author;
	}

	private String _createAuthorEntityMemcacheId( Long userId ) {
		return "DataStore.Author-USER::" + userId;
	}
	
	
	// EVENT Table
	
	@Override
	public Event newEvent() {
		return new EventEntity();
	}

	@Override
	public Event getEvent( Long id ) {
		return getEntity( EventEntity.class, id );
	}
	
	@Override
	public List<Event> getEventList( Language language ) {
		
		Query<EventEntity> query = ObjectifyService.ofy().load().type( EventEntity.class );
		
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
		return createOrUpdateEntity( event, page, auditLog );
	}

	
	// BLOG Table
	
	@Override
	public Blog newBlog() {
		return new BlogEntity();
	}
	
	@Override
	public Blog getBlog( Long id ) {
		return getEntity( BlogEntity.class, id );
	}
	
	@Override
	public Blog createOrUpdateBlog( Blog blog, AuditLog auditLog ) {
		return createOrUpdateEntity( blog, auditLog );
	}

	
	// BLOG_POST Table
	
	@Override
	public BlogPost newBlogPost() {
		return new BlogPostEntity();
	}
	
	@Override
	public BlogPost getBlogPost( Long id ) {
		return getEntity( BlogPostEntity.class, id );
	}
	
	@Override
	public DataListCursorTuple<BlogPost> getBlogPostList(
			BlogPostFilter blogPostFilter, String cursorStr, Integer offset, Integer resultCount ) {

		Query<BlogPostEntity> query = ObjectifyService.ofy().load().type( BlogPostEntity.class );
		
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
		return createOrUpdateEntity( blogPost, page, auditLog );
	}

	
	// USER_PRATILIPI Table
	
	@Override
	public UserPratilipi newUserPratilipi() {
		return new UserPratilipiEntity();
	}
	
	@Override
	public UserPratilipi getUserPratilipi( String userPratilipiId ) {
		return getEntity( UserPratilipiEntity.class, userPratilipiId );
	}
	
	@Override
	public UserPratilipi getUserPratilipi( Long userId, Long pratilipiId ) {
		if( userId == null || userId.equals( 0L ) || pratilipiId == null || pratilipiId.equals( 0L ) )
			return null;
		return getEntity( UserPratilipiEntity.class, userId + "-" + pratilipiId );
	}

	@Override
	public List<UserPratilipi> getUserPratilipiList( Long userId, List<Long> pratilipiIdList ) {
		
		if( userId == null || userId == 0L || pratilipiIdList.size() == 0 )
			return new ArrayList<>( 0 );
		
		List<String> idList = new ArrayList<>( pratilipiIdList.size() );
		for( Long pratilipiId : pratilipiIdList )
			if( pratilipiId != null && pratilipiId != 0L )
				idList.add( userId + "-" + pratilipiId );
		
		return getEntityList( UserPratilipiEntity.class, idList );
	
	}
	
	@Override
	public DataListCursorTuple<Long> getUserLibrary( Long userId, String cursorStr, Integer offset, Integer resultCount ) {
		
		Query<UserPratilipiEntity> query = ObjectifyService.ofy().load()
				.type( UserPratilipiEntity.class )
				.filter( "USER_ID", userId )
				.filter( "ADDED_TO_LIB", true );
		if( UxModeFilter.isAndroidApp() )
			query = query.order( "ADDED_TO_LIB_DATE" );
		else
			query = query.order( "-ADDED_TO_LIB_DATE" );

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
		
		Query<UserPratilipiEntity> query = ObjectifyService.ofy().load().type( UserPratilipiEntity.class );
				
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
	public Map<String, UserPratilipi> getUserPratilipis( Collection<String> userPratilipiIds ) {
		return getEntities( UserPratilipiEntity.class, userPratilipiIds );
	}

	@Override
	public UserPratilipi createOrUpdateUserPratilipi( UserPratilipi userPratilipi, AuditLog auditLog ) {
		( (UserPratilipiEntity) userPratilipi ).setId( userPratilipi.getUserId() + "-" + userPratilipi.getPratilipiId() );
		return createOrUpdateEntity( userPratilipi, auditLog );
	}
	
	
	// USER_AUTHOR Table
	
	@Override
	public UserAuthor newUserAuthor() {
		return new UserAuthorEntity();
	}
	
	@Override
	public UserAuthor getUserAuthor( String userAuthorId ) {
		return getEntity( UserAuthorEntity.class, userAuthorId );
	}
	
	@Override
	public UserAuthor getUserAuthor( Long userId, Long authorId ) {
		if( userId == null || userId.equals( 0L ) || authorId == null || authorId.equals( 0L ) )
			return null;
		return getEntity( UserAuthorEntity.class, userId + "-" + authorId );
	}

	@Override
	public Map<String, UserAuthor> getUserAuthors( Collection<String> userAuthoriIds ) {
		return getEntities( UserAuthorEntity.class, userAuthoriIds );
	}
	
	@Override
	public List<UserAuthor> getUserAuthorList( Long userId, List<Long> authorIdList ) {

		if( userId == null || userId.equals( 0L ) || authorIdList.size() == 0 )
			return new ArrayList<>( 0 );
		
		List<String> idList = new ArrayList<>( authorIdList.size() );
		for( Long authorId : authorIdList )
			if( authorId != null && authorId != 0L )
				idList.add( userId + "-" + authorId );
		
		return getEntityList( UserAuthorEntity.class, idList );
		
	}
	
	@Override
	public List<UserAuthor> getUserAuthorList( List<Long> userIdList, Long authorId ) {
		
		if( userIdList.size() == 0 || authorId == null || authorId.equals( 0L ) )
			return null;
		
		List<String> idList = new ArrayList<>( userIdList.size() );
		for( Long userId : userIdList )
			if( userId != null && userId != 0L )
				idList.add( userId + "-" + authorId );
		
		return getEntityList( UserAuthorEntity.class, idList );
		
	}

	@Override
	public DataListCursorTuple<Long> getUserAuthorFollowList( Long userId, Long authorId, String cursorStr, Integer offset, Integer resultCount ) {

		Query<UserAuthorEntity> query = ObjectifyService.ofy().load().type( UserAuthorEntity.class );
		
		if( userId != null )
			query = query.filter( "USER_ID", userId );

		if( authorId != null )
			query = query.filter( "AUTHOR_ID", authorId );
		
		query = query.filter( "FOLLOW_STATE", UserFollowState.FOLLOWING );
		query = query.order( "-FOLLOW_DATE" );
		
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

		Query<UserAuthorEntity> query = ObjectifyService.ofy().load().type( UserAuthorEntity.class );
		
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
	public DataListIterator<UserAuthor> getUserAuthorListIterator(
			Long userId, Long authorId, String cursorStr, Integer offset, Integer resultCount ) {

		Query<UserAuthorEntity> query = ObjectifyService.ofy().load().type( UserAuthorEntity.class );
		
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
		
		query = query.chunk( resultCount == null || resultCount > 1000 ? 1000 : resultCount );
		
		return new DataListIterator<UserAuthor>( query.iterator() );
		
	}

	@Override
	public UserAuthor createOrUpdateUserAuthor( UserAuthor userAuthor, AuditLog auditLog ) {
		( (UserAuthorEntity) userAuthor ).setId( userAuthor.getUserId() + "-" + userAuthor.getAuthorId() );
		return createOrUpdateEntity( userAuthor, auditLog );
	}

	@Override
	public List<UserAuthor> createOrUpdateUserAuthorList( List<UserAuthor> userAuthorList ) {
		return createOrUpdateEntityList( userAuthorList );
	}
	
	
	// curated/home.<lang>
	
	@Override
	public List<String> getHomeSectionList( Language language ) {

		List<String> sectionList = new LinkedList<>();

		try {
			String fileName = CURATED_DATA_FOLDER + "/home." + language.getCode();
			InputStream inputStream = DataAccessor.class.getResource( fileName ).openStream();
			for( String listName : IOUtils.readLines( inputStream, "UTF-8" ) ) {
				listName = listName.trim();
				if( ! listName.isEmpty() )
					sectionList.add( listName );
			}
			inputStream.close();
		} catch( NullPointerException | IOException e ) {
			logger.log( Level.SEVERE, "Exception while reading from home." + language.getNameEn(), e );
		}
		
		return sectionList;
	
	}
	
	
	// curated/recommend.<lang>
	
	@Override
	public List<String> getRecommendSectionList( Language language ) {

		List<String> sectionList = new LinkedList<>();

		try {
			String fileName = CURATED_DATA_FOLDER + "/recommend." + language.getCode();
			InputStream inputStream = DataAccessor.class.getResource( fileName ).openStream();
			for( String listName : IOUtils.readLines( inputStream, "UTF-8" ) ) {
				listName = listName.trim();
				if( ! listName.isEmpty() )
					sectionList.add( listName );
			}
			inputStream.close();
		} catch( NullPointerException | IOException e ) {
			logger.log( Level.SEVERE, "Exception while reading from home." + language.getNameEn(), e );
		}
		
		return sectionList;
	
	}
	
	
	// curated/navigation.<lang>
	
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
		
		Navigation navigation = null;
		for( String line : lines ) {
			
			line = line.trim();
			
			if( navigation == null && line.isEmpty() )
				continue;
			
			else if( navigation == null && ! line.isEmpty() )
				navigation = new NavigationEntity( line );
			
			else if( navigation != null && line.isEmpty() ) {
				navigationList.add( navigation );
				navigation = null;
			
			} else if( navigation != null && ! line.isEmpty() ) {
				String url = null;
				String title = null;
				String categoryName = null;
				String imageName = null;
				String apiName = null;
				String apiRequest = null;

				if( line.contains( "App#" ) ) {
					String appInfo = line.substring( line.indexOf( "App#" ) ).trim();
					imageName = appInfo.substring( "App#imageName::".length(), appInfo.lastIndexOf( "App#" ) ).trim();
					apiName = appInfo.substring( appInfo.lastIndexOf( "App#" ) + 4, appInfo.lastIndexOf( "::" ) ) + "Api";
					apiRequest = line.substring( line.indexOf( '{' ), line.indexOf( '}' ) + 1 );
					line = line.substring( 0, line.indexOf( "App#" ) ).trim();
				}
				if( line.contains( "Analytics#" ) ) {
					String analyticsInfo = line.substring( line.indexOf( "Analytics#" ) ).trim();
					categoryName = analyticsInfo.substring( "Analytics#categoryName::".length() ).trim();
					line = line.substring( 0, line.indexOf( "Analytics#" ) ).trim();
				}
				if( line.indexOf( ' ' ) != -1 ) {
					url = line.substring( 0, line.indexOf( ' ' ) );
					title = line.substring( line.indexOf( ' ' ) + 1 ).trim();
				}

				navigation.addLink( new Navigation.Link( title, url, apiName, categoryName, apiRequest, imageName ) );
			}
			
		}

		if( navigation != null )
			navigationList.add( navigation );
		
		
		memcache.put( memcacheId, navigationList );

		
		return navigationList;
	
	}


	// curated/category.<lang>
	
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
		return getEntity( CommentEntity.class, commentId );
	}

	@Override
	public Map<Long, Comment> getComments( Collection<Long> commentIds ) {
		return getEntities( CommentEntity.class, commentIds );
	}

	@Override
	public List<Comment> getCommentList( CommentParentType parentType, Long parentId ) {
		return getCommentList( parentType, parentId.toString() );
	}
	
	@Override
	public List<Comment> getCommentList( CommentParentType parentType, String parentId ) {
		
		Query<CommentEntity> query = ObjectifyService.ofy().load()
				.type( CommentEntity.class )
				.filter( "PARENT_TYPE", parentType )
				.filter( "PARENT_ID", parentId )
				.order( "-CREATION_DATE" );
		
		return new ArrayList<Comment>( query.list() );
		
	}
	
	@Override
	public List<Comment> getCommentListByReference( ReferenceType referenceType, Long referenceId ) {
		return getCommentListByReference( referenceType, referenceId.toString() );
	}
	
	@Override
	public List<Comment> getCommentListByReference( ReferenceType referenceType, String referenceId ) {
		
		Query<CommentEntity> query = ObjectifyService.ofy().load()
				.type( CommentEntity.class )
				.filter( "REFERENCE_TYPE", referenceType )
				.filter( "REFERENCE_ID", referenceId )
				.order( "-CREATION_DATE" );
		
		return new ArrayList<Comment>( query.list() );
		
	}

	@Override
	public Comment createOrUpdateComment( Comment comment, AuditLog auditLog ) {
		return createOrUpdateEntity( comment, auditLog );
	}
	
	
	// USER_VOTE Table
	
	@Override
	public Vote newVote() {
		return new VoteEntity();
	}
	
	@Override
	public Vote getVote( String voteId ) {
		return getEntity( VoteEntity.class, voteId );
	}

	@Override
	public Vote getVote( Long userId, VoteParentType parentType, String parentId ) {
		if( userId == null || userId.equals( 0L ) || parentType == null || parentId == null || parentId.isEmpty() )
			return null;
		return getEntity( VoteEntity.class, userId + "-" + parentType + "::" + parentId );
	}
	
	@Override
	public Map<String, Vote> getVotes( Collection<String> voteIds ) {
		return getEntities( VoteEntity.class, voteIds );
	}
	
	@Override
	public List<Vote> getVoteListByReference( ReferenceType referenceType, Long referenceId ) {
		return getVoteListByReference( referenceType, referenceId.toString() );
	}
	
	@Override
	public List<Vote> getVoteListByReference( ReferenceType referenceType, String referenceId ) {
		
		Query<VoteEntity> query = ObjectifyService.ofy().load()
				.type( VoteEntity.class )
				.filter( "REFERENCE_TYPE", referenceType )
				.filter( "REFERENCE_ID", referenceId )
				.order( "-CREATION_DATE" );
		
		return new ArrayList<Vote>( query.list() );
		
	}

	@Override
	public Vote createOrUpdateVote( Vote vote, AuditLog auditLog ) {
		( (VoteEntity) vote ).setId( vote.getUserId() + "-" + vote.getParentType() + "::" + vote.getParentId() );
		return createOrUpdateEntity( vote, auditLog );
	}

	
	// CONVERSATION* Tables
	
	@Override
	public Conversation newConversation( ContactTeam team, Long userId ) {
		return new ConversationEntity( team + "-" + userId );
	}
	
	@Override
	public Conversation newConversation( ContactTeam team, String email ) {
		return new ConversationEntity( team + "-" + email );
	}
	
	@Override
	public Conversation getConversation( ContactTeam team, Long userId ) {
		return getEntity( ConversationEntity.class, team + "-" + userId );
	}
	
	@Override
	public Conversation getConversation( ContactTeam team, String email ) {
		return getEntity( ConversationEntity.class, team + "-" + email );
	}
	
	
	@Override
	public ConversationUser newConversationUser( String conversationId, Long userId ) {
		return new ConversationUserEntity( conversationId, userId );
	}
	
	
	@Override
	public ConversationMessage newConversationMessage() {
		return new ConversationMessageEntity();
	}
	
	@Override
	public Conversation createOrUpdateConversation( Conversation conversation, List<ConversationUser> conversationUserList ) {
		List<GenericOfyType> entityList = new ArrayList<>( 1 + conversationUserList.size() );
		entityList.add( conversation );
		entityList.addAll( conversationUserList );
		entityList = createOrUpdateEntityList( entityList );
		return (Conversation) entityList.get( 0 );
	}
	
	@Override
	public Conversation createOrUpdateConversation( Conversation conversation, ConversationMessage conversationMessage ) {
		List<GenericOfyType> entityList = new ArrayList<>( 2 );
		entityList.add( conversation );
		entityList.add( conversationMessage );
		entityList = createOrUpdateEntityList( entityList );
		return (Conversation) entityList.get( 0 );
	}
	
	
	// MAILING_LIST_SUBSCRIPTION Table

	@Override
	public MailingListSubscription newMailingListSubscription() {
		return new MailingListSubscriptionEntity();
	}
	
	@Override
	public List<MailingListSubscription> getMailingListSubscriptionList( MailingList mailingList, String email, String phone ) {
		
		Query<MailingListSubscriptionEntity> query = ObjectifyService.ofy().load().type( MailingListSubscriptionEntity.class );
		
		query = query.filter( "MAILING_LIST", mailingList );
		
		if( email != null )
			query = query.filter( "EMAIL", email );
		
		if( phone != null )
			query = query.filter( "PHONE", phone );
		
		return new ArrayList<MailingListSubscription>( query.order( "SUBSCRIPTION_DATE" ).list() );
		
	}
	
	@Override
	public MailingListSubscription createOrUpdateMailingListSubscription( MailingListSubscription mailingListSubscription, AuditLog auditLog ) {
		return createOrUpdateEntity( mailingListSubscription, auditLog );
	}
	
	
	// NOTIFICATION Table
	
	@Override
	public Notification newNotification( Long userId, NotificationType type, Long sourceId ) {
		return newNotification( userId, type, sourceId.toString(), null );
	}

	@Override
	public Notification newNotification( Long userId, NotificationType type, String sourceId, String createdBy ) {
		Notification notification = new NotificationEntity();
		notification.setUserId( userId );
		notification.setType( type );
		notification.setSourceId( sourceId );
		notification.setState( NotificationState.UNREAD );
		notification.setFcmPending( true );
		notification.setCreatedBy( createdBy );
		notification.setCreationDate( new Date() );
		notification.setLastUpdated( new Date() );
		return notification;
	}

	@Override
	public Notification getNotification( Long notificationId ) {
		return getEntity( NotificationEntity.class, notificationId );
	}
	
	@Override
	public Notification getNotification( Long userId, NotificationType type, Long sourceId ) {

		return ObjectifyService.ofy().load()
				.type( NotificationEntity.class )
				.filter( "USER_ID", userId )
				.filter( "TYPE", type )
				.filter( "SOURCE_ID", sourceId.toString() )
				.order( "-LAST_UPDATED" )
				.first().now();
	
	}
	
	@Override
	public Notification getNotification( Long userId, NotificationType type, String sourceId, String createdBy ) {

		return ObjectifyService.ofy().load()
				.type( NotificationEntity.class )
				.filter( "USER_ID", userId )
				.filter( "TYPE", type )
				.filter( "SOURCE_ID", sourceId )
				.filter( "CREATED_BY", createdBy )
				.order( "-LAST_UPDATED" )
				.first().now();
	
	}
	
	@Override
	public int getNotificationCout( String createdBy ) {
		return ObjectifyService.ofy().load()
				.type( NotificationEntity.class )
				.filter( "CREATED_BY", createdBy )
				.keys()
				.list()
				.size();
	}
	
	@Override
	public List<Notification> getNotificationList( List<Long> notificationIdList ) {
		return getEntityList( NotificationEntity.class, notificationIdList );
	}
	
	@Override
	public List<Notification> getNotificationListWithFcmPending( Integer resultCount ) {
		List<NotificationEntity> notificationList = ObjectifyService.ofy().load()
				.type( NotificationEntity.class )
				.filter( "FCM_PENDING", true )
				.order( "LAST_UPDATED" )
				.limit( resultCount )
				.list();
		return new ArrayList<Notification>( notificationList );
	}
	
	@Override
	public DataListIterator<Notification> getNotificationListIterator( Long userId, NotificationType type, Long sourceId, String cursorStr, Integer resultCount ) {
		return getNotificationListIterator( userId, type, sourceId.toString(), cursorStr, resultCount );
	}
	
	@Override
	public DataListIterator<Notification> getNotificationListIterator( Long userId, NotificationType type, String sourceId, String cursorStr, Integer resultCount ) {
	
		Query<NotificationEntity> query = ObjectifyService.ofy().load().type( NotificationEntity.class );
		
		if( userId != null )
			query = query.filter( "USER_ID", userId );
		
		if( type != null )
			query = query.filter( "TYPE", type );
		
		if( sourceId != null )
			query = query.filter( "SOURCE_ID", sourceId );
		
		query = query.order( "-LAST_UPDATED" );
		
		if( cursorStr != null )
			query = query.startAt( Cursor.fromWebSafeString( cursorStr ) );
		
		if( resultCount != null && resultCount > 0 )
			query = query.limit( resultCount );
		

		return new DataListIterator<Notification>( query.iterator() );
		
	}
	
	@Override
	public Notification createOrUpdateNotification( Notification notification ) {
		return createOrUpdateEntity( notification );
	}

	@Override
	public List<Notification> createOrUpdateNotificationList( List<Notification> notificationList ) {
		return createOrUpdateEntityList( notificationList );
	}
	
	
	// EMAIL Table

	@Override
	public Email newEmail( Long userId, EmailType type, Long primaryContentId ) {
		return newEmail( userId, type, primaryContentId.toString() );
	}
	
	@Override
	public Email newEmail( Long userId, EmailType type, String primaryContentId ) {
		Email email = new EmailEntity();
		email.setUserId( userId );
		email.setType( type );
		email.setPrimaryContentId( primaryContentId );
		email.setState( EmailState.PENDING );
		email.setCreationDate( new Date() );
		email.setLastUpdated( new Date() );
		return email;
	}

	@Override
	public Email getEmail( Long emailId ) {
		return getEntity( EmailEntity.class, emailId );
	}

	@Override
	public Email getEmail( Long userId, EmailType type, Long primaryContentId ) {
		return getEmail( userId, type, primaryContentId.toString() );
	}
	
	@Override
	public Email getEmail( Long userId, EmailType type, String primaryContentId ) {
		
		return ObjectifyService.ofy().load()
				.type( EmailEntity.class )
				.filter( "USER_ID", userId )
				.filter( "TYPE", type )
				.filter( "PRIMARY_CONTENT_ID", primaryContentId )
				.order( "-LAST_UPDATED" )
				.first().now();

	}

	@Override
	public DataIdListIterator<Email> getIdListIteratorForPendingEmails() {

		QueryResultIterator<Key<EmailEntity>> iterator = ObjectifyService.ofy().load()
				.type( EmailEntity.class )
				.filter( "STATE", EmailState.PENDING )
				.filter( "SCHEDULED_DATE <", new Date() )
				.order( "SCHEDULED_DATE" )
				.chunk( 1000 )
				.keys()
				.iterator();

		return new DataIdListIterator<Email>( iterator );

	}

	@Override
	public List<Email> getEmailList( Long userId, EmailType type, Long primaryContentId, EmailState state, Integer resultCount ) {
		return getEmailList( userId, type, primaryContentId.toString(), state, resultCount );
	}
	
	@Override
	public List<Email> getEmailList( Long userId, EmailType type, String primaryContentId, EmailState state, Integer resultCount ) {

		Query<EmailEntity> query = ObjectifyService.ofy().load().type( EmailEntity.class );

		if( userId != null )
			query = query.filter( "USER_ID", userId );
		if( type != null )
			query = query.filter( "TYPE", type );
		if( primaryContentId != null )
			query = query.filter( "PRIMARY_CONTENT_ID", primaryContentId );
		if( state != null )
			query = query.filter( "STATE", state );

		if( resultCount != null && resultCount > 0 )
			query = query.limit( resultCount );

		return new ArrayList<Email>( query.list() );

	}

	@Override
	public Email createOrUpdateEmail( Email email ) {
		return createOrUpdateEntity( email );
	}

	@Override
	public List<Email> createOrUpdateEmailList( List<Email> emailList ) {
		return createOrUpdateEntityList( emailList );
	}
	
	
	// I18N Table
	
	@Override
	public I18n newI18n( String i18nId ) {
		return new I18nEntity( i18nId );
	}
	
	@Override
	public List<I18n> getI18nList( I18nGroup i18nGroup ) {
		List<I18nEntity> i18nList = ObjectifyService.ofy().load().type( I18nEntity.class )
				.filter( "GROUP", i18nGroup )
				.list();
		return new ArrayList<I18n>( i18nList );
	}
	
	@Override
	public Map<String, String> getI18nStrings( I18nGroup i18nGroup, Language language ) {
		
		List<I18nEntity> i18nList = ObjectifyService.ofy().load().type( I18nEntity.class )
				.filter( "GROUP", i18nGroup )
				.list();
		
		Map<String, String> i18nStrings = new HashMap<>( i18nList.size() );
		for( I18n i18n : i18nList )
			i18nStrings.put( i18n.getId(), i18n.getI18nString( language ) );
		
		return i18nStrings;
		
	}
	
	@Override
	public I18n createOrUpdateI18n( I18n i18n ) {
		return createOrUpdateEntity( i18n );
	}

	@Override
	public List<I18n> createOrUpdateI18nList( List<I18n> i18nList ) {
		return createOrUpdateEntityList( i18nList );
	}


	// BatchProcess Table
	@Override
	public BatchProcess newBatchProcess() {
		return new BatchProcessEntity();
	}

	@Override
	public BatchProcess getBatchProcess( Long batchProcessId ) {
		return getEntity( BatchProcessEntity.class, batchProcessId );
	}

	@Override
	public DataListCursorTuple<BatchProcess> getBatchProcessList( BatchProcessType type,
			BatchProcessState stateInProgress,BatchProcessState stateCompleted,
			String cursor, Integer resultCount ) {

		Query<BatchProcessEntity> query = ObjectifyService.ofy().load().type( BatchProcessEntity.class );

		if( type != null )
			query = query.filter( "TYPE", type );
		if( stateInProgress != null )
			query = query.filter( "STATE_IN_PROGRESS", stateInProgress );
		if( stateCompleted != null )
			query = query.filter( "STATE_COMPLETED", stateCompleted );

		query = query.order( "-CREATION_DATE" );
		
		if( cursor != null )
			query = query.startAt( Cursor.fromWebSafeString( cursor ) );
		if( resultCount != null && resultCount > 0 )
			query = query.limit( resultCount );

		List<BatchProcess> dataList = resultCount == null
				? new ArrayList<BatchProcess>()
				: new ArrayList<BatchProcess>( resultCount );
		
		QueryResultIterator <BatchProcessEntity> iterator = query.iterator();
		while( iterator.hasNext() )
			dataList.add( iterator.next() );

		return new DataListCursorTuple<BatchProcess>(
				dataList,
				cursor == null ? null : iterator.getCursor().toWebSafeString() );

	}

	@Override
	public List<BatchProcess> getIncompleteBatchProcessList() {
		List<BatchProcessEntity> batchProcessList = ObjectifyService.ofy().load()
				.type( BatchProcessEntity.class )
				.filter( "STATE_COMPLETED !=", BatchProcessState.COMPLETED )
				.list();
		return new ArrayList<BatchProcess>( batchProcessList );
	}

	@Override
	public BatchProcess createOrUpdateBatchProcess( BatchProcess batchProcess ) {
		return createOrUpdateEntity( batchProcess );
	}

}
