package com.pratilipi.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.jdo.PersistenceManager;

import com.pratilipi.common.type.AuthorState;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PageType;
import com.pratilipi.common.type.UserState;
import com.pratilipi.common.util.AuthorFilter;
import com.pratilipi.common.util.PratilipiFilter;
import com.pratilipi.common.util.UserPratilipiFilter;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.type.AppProperty;
import com.pratilipi.data.type.AuditLog;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Category;
import com.pratilipi.data.type.Event;
import com.pratilipi.data.type.Navigation;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.PratilipiCategory;
import com.pratilipi.data.type.User;
import com.pratilipi.data.type.UserAuthor;
import com.pratilipi.data.type.UserPratilipi;

public class DataAccessorWithMemcache implements DataAccessor {
	
	private final static String PREFIX_APP_PROPERTY = "AppProperty-";
	private final static String PREFIX_USER = "User-";
	private final static String PREFIX_ACCESS_TOKEN = "AccessToken-";
	private final static String PREFIX_PAGE = "Page-";
	private static final String PREFIX_PRATILIPI = "Pratilipi-";
	private static final String PREFIX_AUTHOR = "Author-";
	private static final String PREFIX_USER_PRATILIPI = "UserPratilipi-";
	private static final String PREFIX_NAVIGATION_LIST = "NavigationList-";
	private static final String PREFIX_CATEGORY = "Category-";
	private static final String PREFIX_CATEGORY_LIST = "CategoryList-";
	private static final String PREFIX_PRATILIPI_CATEGORY_LIST = "PratilipiCategoryList-";
	
	private final DataAccessor dataAccessor;
	private final Memcache memcache;
	
	
	public DataAccessorWithMemcache(
			DataAccessor dataAccessor, Memcache memcache ) {

		this.dataAccessor = dataAccessor;
		this.memcache = memcache;
	}
	
	public PersistenceManager getPersistenceManager() {
		return dataAccessor.getPersistenceManager();
	}

	
	// APP_PROPERTY Table
	
	@Override
	public AppProperty newAppProperty( String id ) {
		return dataAccessor.newAppProperty( id );
	}

	@Override
	public AppProperty getAppProperty( String id ) {
		AppProperty appProperty = memcache.get( PREFIX_APP_PROPERTY + id );
		if( appProperty == null ) {
			appProperty = dataAccessor.getAppProperty( id );
			if( appProperty != null )
				memcache.put( PREFIX_APP_PROPERTY + id, appProperty );
		}
		return appProperty;
	}

	@Override
	public AppProperty createOrUpdateAppProperty( AppProperty appProperty ) {
		appProperty = dataAccessor.createOrUpdateAppProperty( appProperty );
		memcache.put( PREFIX_APP_PROPERTY + appProperty.getId(), appProperty );
		return appProperty;
	}


	// USER Table
	
	@Override
	public User newUser() {
		return dataAccessor.newUser();
	}
	
	@Override
	public User getUser( Long id ) {
		User user = memcache.get( PREFIX_USER + id );
		if( user == null ) {
			user = dataAccessor.getUser( id );
			if( user != null )
				memcache.put( PREFIX_USER + id, user );
		}
		return user;
	}
	
	@Override
	public User getUserByEmail( String email ) {
		User user = memcache.get( PREFIX_USER + email );
		if( user == null ) {
			user = dataAccessor.getUserByEmail( email );
			if( user != null )
				memcache.put( PREFIX_USER + email, user );
		}
		return user;
	}
	
	@Override
	public User getUserByFacebookId( String facebookId ) {
		User user = memcache.get( PREFIX_USER + "fb::" + facebookId );
		if( user == null ) {
			user = dataAccessor.getUserByFacebookId( facebookId );
			if( user != null )
				memcache.put( PREFIX_USER + "fb::" + facebookId, user );
		}
		return user;
	}
	
	@Override
	public Map<Long, User> getUsers( List<Long> idList ) {
		
		Map<Long, User> keyValueMap = new HashMap<>( idList.size() );
		if( idList.size() == 0 )
			return keyValueMap;
		
		List<String> memcacheIdList = new ArrayList<>( idList.size() );
		for( Long id : idList )
			memcacheIdList.add( PREFIX_USER + id );

		// Fetching entities from Memcache.
		Map<String, User> keyValueMap1 = memcache.getAll( memcacheIdList );
		
		List<Long> missingIdList = new LinkedList<>();
		for( Long id : idList ) {
			String memcacheId = PREFIX_USER + id;
			if( keyValueMap1.get( memcacheId ) == null )
				missingIdList.add( id );
			else
				keyValueMap.put( id, keyValueMap1.get( memcacheId ) );
		}
		
		// Fetching remaining entities from DataStore
		Map<Long, User> keyValueMap2 = dataAccessor.getUsers( missingIdList );
		
		for( Long id : missingIdList ) {
			User user = keyValueMap2.get( id );
			memcache.put( PREFIX_USER + id, user );
			keyValueMap.put( id, user );
		}
		
		return keyValueMap;
		
	}
	
	@Override
	public DataListCursorTuple<User> getUserList( String cursor, Integer resultCount ) {
		return dataAccessor.getUserList( cursor, resultCount );
	}

	@Override
	public User createOrUpdateUser( User user ) {
		user = dataAccessor.createOrUpdateUser( user );
		memcache.put( PREFIX_USER + user.getId(), user );
		if( user.getEmail() != null ) {
			if( user.getState() == UserState.DELETED )
				memcache.remove( PREFIX_USER + user.getEmail() );
			else
				memcache.put( PREFIX_USER + user.getEmail(), user );
		}
		if( user.getFacebookId() != null ) {
			if( user.getState() == UserState.DELETED )
				memcache.remove( PREFIX_USER + "fb::" + user.getFacebookId() );
			else
				memcache.put( PREFIX_USER + "fb::" + user.getFacebookId(), user );
		}
		return user;
	}
	
	@Override
	public User createOrUpdateUser( User user, AuditLog auditLog ) {
		user = dataAccessor.createOrUpdateUser( user, auditLog );
		memcache.put( PREFIX_USER + user.getId(), user );
		if( user.getEmail() != null ) {
			if( user.getState() == UserState.DELETED )
				memcache.remove( PREFIX_USER + user.getEmail() );
			else
				memcache.put( PREFIX_USER + user.getEmail(), user );
		}
		if( user.getFacebookId() != null ) {
			if( user.getState() == UserState.DELETED )
				memcache.remove( PREFIX_USER + "fb::" + user.getFacebookId() );
			else
				memcache.put( PREFIX_USER + "fb::" + user.getFacebookId(), user );
		}
		return user;
	}
	

	// ACCESS_TOKEN Table
	
	@Override
	public AccessToken newAccessToken() {
		return dataAccessor.newAccessToken();
	}

	@Override
	public AccessToken getAccessToken( String accessTokenId ) {
		if( accessTokenId == null )
			return null;
		
		AccessToken accessToken = memcache.get( PREFIX_ACCESS_TOKEN + accessTokenId );
		if( accessToken == null ) {
			accessToken = dataAccessor.getAccessToken( accessTokenId );
			if( accessToken != null )
				memcache.put( PREFIX_ACCESS_TOKEN + accessToken.getId(), accessToken );
		}
		return accessToken;
	}
	
	@Override
	public DataListCursorTuple<AccessToken> getAccessTokenList( String cursorStr, Integer resultCount ) {
		return dataAccessor.getAccessTokenList( cursorStr, resultCount );
	}

	@Override
	public DataListCursorTuple<AccessToken> getAccessTokenList( Long userId, Date minExpiry, String cursorStr, Integer resultCount ) {
		return dataAccessor.getAccessTokenList( userId, minExpiry, cursorStr, resultCount );
	}

	@Override
	public AccessToken createOrUpdateAccessToken( AccessToken accessToken ) {
		accessToken = dataAccessor.createOrUpdateAccessToken( accessToken );
		memcache.put( PREFIX_ACCESS_TOKEN + accessToken.getId(), accessToken );
		return accessToken;
	}

	@Override
	public AccessToken createOrUpdateAccessToken( AccessToken newAccessToken, AccessToken oldAccessToken ) {
		newAccessToken = dataAccessor.createOrUpdateAccessToken( newAccessToken, oldAccessToken );

		Map<String, AccessToken> keyValueMap = new HashMap<>();
		keyValueMap.put( PREFIX_ACCESS_TOKEN + newAccessToken.getId(), newAccessToken );
		keyValueMap.put( PREFIX_ACCESS_TOKEN + oldAccessToken.getId(), oldAccessToken );
		memcache.putAll( keyValueMap );
		
		return newAccessToken;
	}

	@Override
	public void deleteAccessToken( AccessToken accessToken ) {
		dataAccessor.deleteAccessToken( accessToken );
		memcache.remove( PREFIX_ACCESS_TOKEN + accessToken.getId() );
	}

	
	// AUDIT_LOG Table
	
	@Override
	public AuditLog newAuditLog() {
		return dataAccessor.newAuditLog();
	}

	@Override
	public AuditLog createAuditLog( AuditLog auditLog ) {
		return dataAccessor.createAuditLog( auditLog );
	}
	
	@Override
	public DataListCursorTuple<AuditLog> getAuditLogList( String cursorStr, Integer resultCount) {
		return dataAccessor.getAuditLogList( cursorStr, resultCount );
	}

	@Override
	public DataListCursorTuple<AuditLog> getAuditLogList( String accessId, String cursorStr, Integer resultCount) {
		return dataAccessor.getAuditLogList( accessId, cursorStr, resultCount );
	}

	
	// PAGE Table
	
	@Override
	public Page newPage() {
		return dataAccessor.newPage();
	}
	
	@Override
	public Page getPage( Long id ) {
		Page page = memcache.get( PREFIX_PAGE + id );
		if( page == null ) {
			page = dataAccessor.getPage( id );
			if( page != null )
				memcache.put( PREFIX_PAGE + id, page );
		}
		return page;
	}
	
	@Override
	public Page getPage( String uri ) {
		Page page = memcache.get( PREFIX_PAGE + uri );
		if( page == null ) {
			page = dataAccessor.getPage( uri );
			if( page == null ) // Hack: This will save lot of DB queries.
				page = dataAccessor.newPage();
			memcache.put( PREFIX_PAGE + uri, page );
		}
		return page.getId() == null ? null : page;
	}
	
	@Override
	public Page getPage( PageType pageType, Long primaryContentId ) {
		Page page = memcache.get( createPageEntityMemcacheId( pageType, primaryContentId ) );
		if( page == null ) {
			page = dataAccessor.getPage( pageType, primaryContentId );
			if( page != null )
				memcache.put( createPageEntityMemcacheId( pageType, primaryContentId ), page );
		}
		return page;
	}
	
	@Override
	public Map<String, Page> getPages( List<String> uriList ) {
		
		Map<String, Page> keyValueMap = new HashMap<>( uriList.size() );
		if( uriList.size() == 0 )
			return keyValueMap;
		
		List<String> memcacheIdList = new ArrayList<>( uriList.size() );
		for( String uri : uriList )
			memcacheIdList.add( createPageEntityMemcacheId( uri ) );

		// Fetching entities from Memcache.
		Map<String, Page> keyValueMap1 = memcache.getAll( memcacheIdList );
		
		List<String> missingUriList = new LinkedList<>();
		for( String uri : uriList ) {
			String memcacheId = createPageEntityMemcacheId( uri );
			if( keyValueMap1.get( memcacheId ) == null )
				missingUriList.add( uri );
			else
				keyValueMap.put( uri, keyValueMap1.get( memcacheId ) );
		}
		
		// Fetching remaining entities from DataStore
		Map<String, Page> keyValueMap2 = dataAccessor.getPages( missingUriList );
		
		for( String uri : missingUriList ) {
			Page page = keyValueMap2.get( uri );
			memcache.put( createPageEntityMemcacheId( uri ), page );
			keyValueMap.put( uri, page );
		}
		
		return keyValueMap;
		
	}
	
	@Override
	public Map<Long, Page> getPages( PageType pageType, List<Long> primaryContentIdList ) {
		
		Map<Long, Page> keyValueMap = new HashMap<>();
		if( primaryContentIdList.size() == 0 )
			return keyValueMap;
		
		List<String> memcacheIdList = new ArrayList<>( primaryContentIdList.size() );
		for( Long primaryContentId : primaryContentIdList )
			memcacheIdList.add( createPageEntityMemcacheId( pageType, primaryContentId ) );

		// Fetching entities from Memcache.
		Map<String, Page> keyValueMap1 = memcache.getAll( memcacheIdList );
		
		List<Long> missingPrimaryContentIdList = new LinkedList<>();
		for( Long primaryContentId : primaryContentIdList ) {
			String memcacheId = createPageEntityMemcacheId( pageType, primaryContentId );
			if( keyValueMap1.get( memcacheId ) == null )
				missingPrimaryContentIdList.add( primaryContentId );
			else
				keyValueMap.put( primaryContentId, keyValueMap1.get( memcacheId ) );
		}
		
		// Fetching remaining entities from DataStore
		Map<Long, Page> keyValueMap2 = dataAccessor.getPages( pageType, missingPrimaryContentIdList );
		
		for( Long primaryContentId : missingPrimaryContentIdList ) {
			Page page = keyValueMap2.get( primaryContentId );
			memcache.put( createPageEntityMemcacheId( pageType, primaryContentId ), page );
			keyValueMap.put( primaryContentId, page );
		}
		
		return keyValueMap;
		
	}

	private String createPageEntityMemcacheId( String uri ) {
		return PREFIX_PAGE + uri;
	}
	
	private String createPageEntityMemcacheId( PageType pageType, Long primaryContentId ) {
		return PREFIX_PAGE + pageType + "::" + primaryContentId;
	}
	
	@Override
	public Page createOrUpdatePage( Page page ) {
		page = dataAccessor.createOrUpdatePage( page );
		memcache.put( PREFIX_PAGE + page.getId(), page );
		if( page.getUri() != null )
			memcache.put( PREFIX_PAGE + page.getUri(), page );
		if( page.getUriAlias() != null )
			memcache.put( PREFIX_PAGE + page.getUriAlias(), page );
		if( page.getPrimaryContentId() != null )
			memcache.put( PREFIX_PAGE + page.getType() + "::" + page.getPrimaryContentId(), page );
		return page;
	}
	
	@Override
	public void deletePage( Page page ) {
		dataAccessor.deletePage( page );
		memcache.remove( PREFIX_PAGE + page.getId() );
		if( page.getUri() != null )
			memcache.remove( PREFIX_PAGE + page.getUri() );
		if( page.getUriAlias() != null )
			memcache.remove( PREFIX_PAGE + page.getUriAlias() );
		if( page.getPrimaryContentId() != null )
			memcache.remove( PREFIX_PAGE + page.getType() + "::" + page.getPrimaryContentId() );
	}


	// PRATILIPI Table

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
	public List<Pratilipi> getPratilipiList( List<Long> idList ) {
		if( idList.size() == 0 )
			return new ArrayList<>( 0 );
		
		
		List<String> memcacheKeyList = new ArrayList<>( idList.size() );
		for( Long id : idList )
			memcacheKeyList.add( PREFIX_PRATILIPI + id );
		Map<String, Pratilipi> memcacheKeyEntityMap = memcache.getAll( memcacheKeyList );

		
		List<Long> missingIdList = new LinkedList<>();
		for( Long id : idList )
			if( memcacheKeyEntityMap.get( PREFIX_PRATILIPI + id ) == null )
				missingIdList.add( id );
		List<Pratilipi> missingPratilipiList = dataAccessor.getPratilipiList( missingIdList );

		
		List<Pratilipi> pratilipiList = new ArrayList<>( idList.size() );
		for( Long id : idList ) {
			Pratilipi pratilipi = memcacheKeyEntityMap.get( PREFIX_PRATILIPI + id );
			if( pratilipi == null ) {
				pratilipi = missingPratilipiList.remove( 0 );
				if( pratilipi != null )
					memcache.put( PREFIX_PRATILIPI + id, pratilipi );
			}
			pratilipiList.add( pratilipi );
		}
		
		
		return pratilipiList;
	}
	
	@Override
	public DataListCursorTuple<Long> getPratilipiIdList(
			PratilipiFilter pratilipiFilter, String cursorStr, Integer resultCount ) {
		
		return dataAccessor.getPratilipiIdList( pratilipiFilter, cursorStr, resultCount );
		
	}

	@Override
	public DataListCursorTuple<Long> getPratilipiIdList(
			PratilipiFilter pratilipiFilter, String cursorStr, Integer offset, Integer resultCount ) {
		
		return dataAccessor.getPratilipiIdList( pratilipiFilter, cursorStr, offset, resultCount );
		
	}
	
	@Override
	public DataListCursorTuple<Pratilipi> getPratilipiList(
			PratilipiFilter pratilipiFilter, String cursorStr, Integer resultCount ) {

		return dataAccessor.getPratilipiList( pratilipiFilter, cursorStr, resultCount );
		
	}
	
	@Override
	public Pratilipi createOrUpdatePratilipi( Pratilipi pratilipi ) {
		pratilipi = dataAccessor.createOrUpdatePratilipi( pratilipi );
		memcache.put( PREFIX_PRATILIPI + pratilipi.getId(), pratilipi );
		return pratilipi;
	}
	
	@Override
	public Pratilipi createOrUpdatePratilipi( Pratilipi pratilipi, AuditLog auditLog ) {
		pratilipi = dataAccessor.createOrUpdatePratilipi( pratilipi, auditLog );
		memcache.put( PREFIX_PRATILIPI + pratilipi.getId(), pratilipi );
		return pratilipi;
	}

	
	// AUTHOR Table
	
	@Override
	public Author newAuthor() {
		return dataAccessor.newAuthor();
	}

	@Override
	public Author getAuthor( Long id ) {
		if( id == null )
			return null;
		
		Author author = memcache.get( PREFIX_AUTHOR + id );
		if( author == null ) {
			author = dataAccessor.getAuthor( id );
			if( author != null )
				memcache.put( PREFIX_AUTHOR + id, author );
		}
		return author;
	}

	@Override
	public Author getAuthorByUserId( Long userId ) {
		Author author = memcache.get( PREFIX_AUTHOR + "USER::" + userId );
		if( author == null ) {
			author = dataAccessor.getAuthorByUserId( userId );
			if( author != null )
				memcache.put( PREFIX_AUTHOR + "USER::" + userId, author );
		}
		return author;
	}
	
	@Override
	public List<Author> getAuthorList( List<Long> idList ) {
		if( idList.size() == 0 )
			return new ArrayList<>( 0 );

		
		List<String> memcacheKeyList = new ArrayList<>( idList.size() );
		for( Long id : idList )
			memcacheKeyList.add( PREFIX_AUTHOR + id );
		Map<String, Author> memcacheKeyEntityMap = memcache.getAll( memcacheKeyList );

		
		List<Long> missingIdList = new LinkedList<>();
		for( Long id : idList )
			if( memcacheKeyEntityMap.get( PREFIX_AUTHOR + id ) == null ) 
				missingIdList.add( id );
		List<Author> missingAuthorList = dataAccessor.getAuthorList( missingIdList );

		
		List<Author> authorList = new ArrayList<>( idList.size() );
		for( Long id : idList ) {
			Author author = memcacheKeyEntityMap.get( PREFIX_AUTHOR + id );
			if( author == null ) {
				author = missingAuthorList.remove( 0 );
				if( author != null )
					memcache.put( PREFIX_AUTHOR + id, author );
			}
			authorList.add( author );
		}
		
		
		return authorList;
	}
	
	@Override
	public DataListCursorTuple<Long> getAuthorIdList( AuthorFilter authorFilter,
			String cursor, Integer resultCount ) {
		
		return dataAccessor.getAuthorIdList( authorFilter, cursor, resultCount );
	}

	@Override
	public DataListCursorTuple<Author> getAuthorList( AuthorFilter authorFilter,
			String cursor, Integer resultCount ) {
		
		return dataAccessor.getAuthorList( authorFilter, cursor, resultCount );
	}

	@Override
	public Author createOrUpdateAuthor( Author author ) {
		author = dataAccessor.createOrUpdateAuthor( author );
		memcache.put( PREFIX_AUTHOR + author.getId(), author );
		if( author.getEmail() != null ) {
			if( author.getState() == AuthorState.DELETED )
				memcache.remove( PREFIX_AUTHOR + author.getEmail() );
			else
				memcache.put( PREFIX_AUTHOR + author.getEmail(), author );
		}
		if( author.getUserId() != null ) {
			if( author.getState() == AuthorState.DELETED )
				memcache.remove( PREFIX_AUTHOR + "USER::" + author.getUserId() );
			else
				memcache.put( PREFIX_AUTHOR + "USER::" + author.getUserId(), author );
		}
		return author;
	}

	@Override
	public Author createOrUpdateAuthor( Author author, AuditLog auditLog ) {
		author = dataAccessor.createOrUpdateAuthor( author, auditLog );
		memcache.put( PREFIX_AUTHOR + author.getId(), author );
		if( author.getEmail() != null ) {
			if( author.getState() == AuthorState.DELETED )
				memcache.remove( PREFIX_AUTHOR + author.getEmail() );
			else
				memcache.put( PREFIX_AUTHOR + author.getEmail(), author );
		}
		if( author.getUserId() != null ) {
			if( author.getState() == AuthorState.DELETED )
				memcache.remove( PREFIX_AUTHOR + "USER::" + author.getUserId() );
			else
				memcache.put( PREFIX_AUTHOR + "USER::" + author.getUserId(), author );
		}
		return author;
	}

	
	// EVENT Table
	
	@Override
	public Event newEvent() {
		return dataAccessor.newEvent();
	}

	@Override
	public Event getEvent( Long id ) {
		// Counting on Objectify Global and Session cache
		return dataAccessor.getEvent( id );
	}
	
	@Override
	public Event createOrUpdateEvent( Event event ) {
		// Counting on Objectify Global and Session cache
		return dataAccessor.createOrUpdateEvent( event );
	}
	
	
	// USER_PRATILIPI Table
	
	@Override
	public UserPratilipi newUserPratilipi() {
		return dataAccessor.newUserPratilipi();
	}
	
	@Override
	public UserPratilipi getUserPratilipi( Long userId, Long pratilipiId ) {
		if( userId == null || userId.equals( 0L ) || pratilipiId == null || pratilipiId.equals( 0L ) )
			return null;
		
		UserPratilipi userPratilipi = memcache.get( PREFIX_USER_PRATILIPI + userId + "-" + pratilipiId );
		if( userPratilipi == null ) {
			userPratilipi = dataAccessor.getUserPratilipi( userId, pratilipiId );
			if( userPratilipi != null )
				memcache.put( PREFIX_USER_PRATILIPI + userId + "-" + pratilipiId, userPratilipi );
		}
		return userPratilipi;
	}

	@Override
	public DataListCursorTuple<UserPratilipi> getPratilipiReviewList(
			Long pratilipiId, String cursor, Integer offset, Integer resultCount ) {

		return dataAccessor.getPratilipiReviewList( pratilipiId, cursor, offset, resultCount );
	}

	@Override
	public DataListCursorTuple<Long> getPratilipiIdList(
			UserPratilipiFilter userPratilipiFilter, String cursorStr,
			Integer offset, Integer resultCount ) {
		
		return dataAccessor.getPratilipiIdList( userPratilipiFilter, cursorStr, offset, resultCount );
	}
	
	@Override
	public DataListCursorTuple<UserPratilipi> getUserPratilipiList( Long userId,
			Long pratilipiId, String cursorStr, Integer resultCount ) {
		
		return dataAccessor.getUserPratilipiList( userId, pratilipiId, cursorStr, resultCount );
	}

	@Override
	public UserPratilipi createOrUpdateUserPratilipi( UserPratilipi userPratilipi ) {
		userPratilipi = dataAccessor.createOrUpdateUserPratilipi( userPratilipi );
		memcache.put( PREFIX_USER_PRATILIPI + userPratilipi.getId(), userPratilipi );
		return userPratilipi;
	}
	
	
	// USER_AUTHOR Table
	
	@Override
	public UserAuthor newUserAuthor() {
		return dataAccessor.newUserAuthor();
	}
	
	@Override
	public UserAuthor getUserAuthor( Long userId, Long authorId ) {
		// Counting on Objectify Global and Session cache
		return dataAccessor.getUserAuthor( userId, authorId );
	}

	@Override
	public DataListCursorTuple<UserAuthor> getUserAuthorList( Long userId, Long authorId, String cursorStr, Integer resultCount ) {
		// Counting on Objectify Global and Session cache
		return dataAccessor.getUserAuthorList( userId, authorId, cursorStr, resultCount );
	}

	@Override
	public UserAuthor createOrUpdateUserAuthor( UserAuthor userAuthor ) {
		// Counting on Objectify Global and Session cache
		return dataAccessor.createOrUpdateUserAuthor( userAuthor );
	}
	
	
	// NAVIGATION Table
	
	@Override
	public List<Navigation> getNavigationList( Language language ) {
		String memcacheId = PREFIX_NAVIGATION_LIST + language.getCode()
				+ "?" + ( new Date().getTime() / TimeUnit.MINUTES.toMillis( 5 ) );
		List<Navigation> navigationList = memcache.get( memcacheId );
		if( navigationList == null ) {
			navigationList = dataAccessor.getNavigationList( language );
			if( navigationList != null )
				memcache.put( memcacheId, new ArrayList<>( navigationList ) );
		}
		return navigationList;
	}

	
	// CATEGORY Table
	
	@Override
	public Category getCategory( Long id ) {
		Category category = memcache.get( PREFIX_CATEGORY + id );
		if( category == null ) {
			category = dataAccessor.getCategory( id );
			if( category != null )
				memcache.put( PREFIX_CATEGORY + id, category );
		}
		return category;
	}
	
	@Override
	public List<Category> getCategoryList( Language language ) {
		String memcacheId = PREFIX_CATEGORY_LIST + language.getCode()
				+ "?" + ( new Date().getTime() / TimeUnit.MINUTES.toMillis( 5 ) );
		List<Category> categoryList = memcache.get( memcacheId );
		if( categoryList == null ) {
			categoryList = dataAccessor.getCategoryList( language );
			if( categoryList != null )
				memcache.put( memcacheId, new ArrayList<>( categoryList ) );
		}
		return categoryList;
	}

	
	// PRATILIPI_CATEGORY Table
	
	@Override
	public List<PratilipiCategory> getPratilipiCategoryList( Long pratilipiId ){
		List<PratilipiCategory> pratilipiCategoryList =
				memcache.get( PREFIX_PRATILIPI_CATEGORY_LIST + pratilipiId );
		if( pratilipiCategoryList == null ) {
			pratilipiCategoryList =
					dataAccessor.getPratilipiCategoryList( pratilipiId );
			memcache.put(
					PREFIX_PRATILIPI_CATEGORY_LIST + pratilipiId,
					new ArrayList<>( pratilipiCategoryList ) );
		}
		return pratilipiCategoryList;
	}
	
	
	// Destroy
	
	@Override
	public void destroy() {
		dataAccessor.destroy();
	}

}
