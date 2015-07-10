package com.pratilipi.data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.pratilipi.common.type.PageType;
import com.pratilipi.common.util.AuthorFilter;
import com.pratilipi.common.util.PratilipiFilter;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.type.AppProperty;
import com.pratilipi.data.type.AuditLog;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.User;

public class DataAccessorWithMemcache implements DataAccessor {
	
	private final static String PREFIX_APP_PROPERTY = "AppProperty-";
	private final static String PREFIX_USER = "User-";
	private final static String PREFIX_ACCESS_TOKEN = "AccessToken-";
	private final static String PREFIX_PAGE = "Page-";
	private static final String PREFIX_PRATILIPI = "Pratilipi-";
	private static final String PREFIX_AUTHOR = "Author-";
	
	private final DataAccessor dataAccessor;
	private final Memcache memcache;
	
	
	public DataAccessorWithMemcache(
			DataAccessor dataAccessor, Memcache memcache ) {

		this.dataAccessor = dataAccessor;
		this.memcache = memcache;
	}
	
	
	// Transaction Helpers

	public void beginTx() {
		dataAccessor.beginTx();
	}

	public void commitTx() {
		dataAccessor.commitTx();
	}
	
	public void rollbackTx() {
		dataAccessor.rollbackTx();
	}

	public boolean isTxActive() {
		return dataAccessor.isTxActive();
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
	public User createOrUpdateUser( User user ) {
		user = dataAccessor.createOrUpdateUser( user );
		memcache.put( PREFIX_USER + user.getId(), user );
		memcache.put( PREFIX_USER + user.getEmail(), user );
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
	
	public DataListCursorTuple<AccessToken> getAccessTokenList( String cursorStr, Integer resultCount ) {
		return dataAccessor.getAccessTokenList( cursorStr, resultCount );
	}

	@Override
	public AccessToken createAccessToken( AccessToken accessToken ) {
		accessToken = dataAccessor.createAccessToken( accessToken );
		memcache.put( PREFIX_ACCESS_TOKEN + accessToken.getId(), accessToken );
		return accessToken;
	}

	@Override
	public AccessToken updateAccessToken( AccessToken accessToken ) {
		accessToken = dataAccessor.updateAccessToken( accessToken );
		memcache.put( PREFIX_ACCESS_TOKEN + accessToken.getId(), accessToken );
		return accessToken;
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
			if( page != null )
				memcache.put( PREFIX_PAGE + uri, page );
		}
		return page;
	}
	
	@Override
	public Page getPage( PageType pageType, Long primaryContentId ) {
		Page page = memcache.get( PREFIX_PAGE + pageType + "-" + primaryContentId );
		if( page == null ) {
			page = dataAccessor.getPage( pageType, primaryContentId );
			if( page != null )
				memcache.put( PREFIX_PAGE + pageType + "-" + primaryContentId, page );
		}
		return page;
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
			memcache.put( PREFIX_PAGE + page.getType() + "-" + page.getPrimaryContentId(), page );
		return page;
	}


	// PRATILIPI Table

	@Override
	public Pratilipi newPratilipi() {
		return dataAccessor.newPratilipi();
	}

	@Override
	public Pratilipi getPratilipi( Long id ) {
		if( dataAccessor.isTxActive() )
			return dataAccessor.getPratilipi( id );
		
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
	public Author getAuthorByEmailId( String email ) {
		Author author = memcache.get( PREFIX_AUTHOR + email );
		if( author == null ) {
			author = dataAccessor.getAuthorByEmailId( email );
			if( author != null )
				memcache.put( PREFIX_AUTHOR + email, author );
		}
		return author;
	}
	
	@Override
	public Author getAuthorByUserId( Long userId ) {
		Author author = memcache.get( PREFIX_AUTHOR + "User-" + userId );
		if( author == null ) {
			author = dataAccessor.getAuthorByUserId( userId );
			if( author != null )
				memcache.put( PREFIX_AUTHOR + "User-" + userId, author );
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
		return author;
	}

	
	// Destroy
	
	@Override
	public void destroy() {
		dataAccessor.destroy();
	}

}
