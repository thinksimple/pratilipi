package com.pratilipi.data;

import java.util.ArrayList;
import java.util.List;

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
import com.pratilipi.data.type.gae.AccessTokenEntity;
import com.pratilipi.data.type.gae.AppPropertyEntity;
import com.pratilipi.data.type.gae.AuditLogEntity;
import com.pratilipi.data.type.gae.AuthorEntity;
import com.pratilipi.data.type.gae.PageEntity;
import com.pratilipi.data.type.gae.PratilipiEntity;
import com.pratilipi.data.type.gae.UserEntity;

public class DataAccessorMockImpl implements DataAccessor {

	// APP_PROPERTY Table
	
	@Override
	public AppProperty newAppProperty( String id ) {
		AppPropertyEntity appProperty = new AppPropertyEntity();
		appProperty.setId( id );
		return appProperty;
	}

	@Override
	public AppProperty getAppProperty( String id ) {
		// TODO: Implementation
		return null;
	}

	@Override
	public AppProperty createOrUpdateAppProperty( AppProperty appProperty ) {
		// TODO: Implementation
		return null;
	}


	// USER Table
	
	@Override
	public User newUser() {
		return new UserEntity();
	}
	
	@Override
	public User getUser( Long id ) {
		// TODO: Implementation
		return null;
	}
	
	@Override
	public User getUserByEmail( String email ) {
		// TODO: Implementation
		return null;
	}
	
	@Override
	public User createOrUpdateUser( User user ) {
		// TODO: Implementation
		return user;
	}


	// ACCESS_TOKEN Table
	
	@Override
	public AccessToken newAccessToken() {
		return new AccessTokenEntity();
	}

	@Override
	public AccessToken getAccessToken( String accessTokenId ) {
		// TODO: Implementation
		return null;
	}
	
	@Override
	public AccessToken createAccessToken( AccessToken accessToken ) {
		// TODO: Implementation
		return accessToken;
	}

	@Override
	public AccessToken updateAccessToken( AccessToken accessToken ) {
		// TODO: Implementation
		return accessToken;
	}

	
	// AUDIT_LOG Table

	@Override
	public AuditLog newAuditLog() {
		return new AuditLogEntity();
	}

	@Override
	public AuditLog createAuditLog( AuditLog auditLog ) {
		// TODO: Implementation
		return null;
	}
	

	@Override
	public DataListCursorTuple<AuditLog> getAuditLogList( String cursorStr, Integer resultCount) {
		// TODO: Implementation
		return null;
	}

	
	// PAGE Table
	
	@Override
	public Page newPage() {
		return new PageEntity();
	}
	
	@Override
	public Page getPage( Long id ) {
		for( Page page : MockData.PAGE_TABLE )
			if( page.getId().equals( id ) )
				return page;

		return null;
	}
	
	@Override
	public Page getPage( String uri ) {
		for( Page page : MockData.PAGE_TABLE )
			if( uri.equals( page.getUri() ) )
				return page;

		for( Page page : MockData.PAGE_TABLE )
			if( uri.equals( page.getUriAlias() ) )
				return page;

		return null;
	}
	
	@Override
	public Page getPage( PageType pageType, Long primaryContentId ) {
		for( Page page : MockData.PAGE_TABLE )
			if( page.getType().equals( pageType ) && page.getPrimaryContentId().equals( primaryContentId ) )
				return page;

		return null;
	}
	
	@Override
	public Page createOrUpdatePage( Page page ) {
		MockData.PAGE_TABLE.add( page );
		return page;
	}
	
	
	// PRATILIPI Table

	@Override
	public Pratilipi newPratilipi() {
		return new PratilipiEntity();
	}

	@Override
	public Pratilipi getPratilipi( Long id ) {
		for( Pratilipi pratilipi : MockData.PRATILIPI_TABLE )
			if( pratilipi.getId().equals( id ) )
				return pratilipi;
		
		return null;
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
		
		// TODO: Implementation
		return null;
	}
	
	@Override
	public DataListCursorTuple<Pratilipi> getPratilipiList(
			PratilipiFilter pratilipiFilter, String cursorStr, Integer resultCount ) {

		// TODO: Implementation
		return null;
	}
	
	@Override
	public Pratilipi createOrUpdatePratilipi( Pratilipi pratilipi ) {
		MockData.PRATILIPI_TABLE.add( pratilipi );
		return pratilipi;
	}
	
	
	// AUTHOR Table
	
	@Override
	public Author newAuthor() {
		return new AuthorEntity();
	}

	@Override
	public Author getAuthor( Long id ) {
		for( Author author : MockData.AUTHOR_TABLE )
			if( author.getId().equals( id ) )
				return author;
		
		return null;
	}
	
	@Override
	public Author getAuthorByEmailId( String email ) {
		for( Author author : MockData.AUTHOR_TABLE )
			if( author.getEmail().equals( email ) )
				return author;
		
		return null;
	}

	@Override
	public Author getAuthorByUserId( Long userId ) {
		for( Author author : MockData.AUTHOR_TABLE )
			if( author.getUserId().equals( userId ) )
				return author;
		
		return null;
	}
	
	@Override
	public DataListCursorTuple<Long> getAuthorIdList( AuthorFilter authorFilter,
			String cursor, Integer resultCount ) {
		
		// TODO: Implementation
		return null;
	}

	@Override
	public DataListCursorTuple<Author> getAuthorList( AuthorFilter authorFilter,
			String cursor, Integer resultCount ) {
		
		// TODO: Implementation
		return null;
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
		MockData.AUTHOR_TABLE.add( author );
		return author;
	}

	
	// Destroy

	@Override
	public void destroy() {}

}
