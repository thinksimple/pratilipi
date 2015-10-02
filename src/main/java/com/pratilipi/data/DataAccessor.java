package com.pratilipi.data;

import java.util.List;

import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PageType;
import com.pratilipi.common.util.AuthorFilter;
import com.pratilipi.common.util.PratilipiFilter;
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

public interface DataAccessor {

	// APP_PROPERTY Table
	AppProperty newAppProperty( String id );
	AppProperty getAppProperty( String id );
	AppProperty createOrUpdateAppProperty( AppProperty appProperty );

	// USER Table
	User newUser();
	User getUser( Long id );
	User getUserByEmail( String email );
	User getUserByFacebookId( String facebookId );
	DataListCursorTuple<User> getUserList( String cursorStr, Integer resultCount );
	User createOrUpdateUser( User user );

	// ACCESS_TOKEN Table
	AccessToken newAccessToken();
	AccessToken getAccessToken( String accessTokenId );
	DataListCursorTuple<AccessToken> getAccessTokenList( String cursorStr, Integer resultCount );
	AccessToken createOrUpdateAccessToken( AccessToken accessToken );
	void deleteAccessToken( AccessToken accessToken );

	// AUDIT_LOG Table
	AuditLog newAuditLog();
	AuditLog createAuditLog( AuditLog auditLog );
	DataListCursorTuple<AuditLog> getAuditLogList( String cursor, Integer resultCount );
	DataListCursorTuple<AuditLog> getAuditLogList( String accessId, String cursor, Integer resultCount );

	
	// PAGE Table
	Page newPage();
	Page getPage( Long id );
	Page getPage( String uri );
	Page getPage( PageType pageType, Long primaryContentId );
	Page createOrUpdatePage( Page page );

	// PRATILIPI Table
	Pratilipi newPratilipi();
	Pratilipi getPratilipi( Long id );
	List<Pratilipi> getPratilipiList( List<Long> idList );
	DataListCursorTuple<Long> getPratilipiIdList( PratilipiFilter pratilipiFilter, String cursorStr, Integer resultCount );
	DataListCursorTuple<Pratilipi> getPratilipiList( PratilipiFilter pratilipiFilter, String cursorStr, Integer resultCount );
	Pratilipi createOrUpdatePratilipi( Pratilipi pratilipi );
	
	// AUTHOR Table
	Author newAuthor();
	Author getAuthor( Long id );
	Author getAuthorByEmailId( String email );
	Author getAuthorByUserId( Long userId );
	List<Author> getAuthorList( List<Long> idList );
	DataListCursorTuple<Long> getAuthorIdList( AuthorFilter authorFilter, String cursor, Integer resultCount );
	DataListCursorTuple<Author> getAuthorList( AuthorFilter authorFilter, String cursor, Integer resultCount );
	Author createOrUpdateAuthor( Author author );

	
	// USER_PRATILIPI Table
	UserPratilipi newUserPratilipi();
	UserPratilipi getUserPratilipi( Long userId, Long pratilipiId );
	DataListCursorTuple<UserPratilipi> getPratilipiReviewList( Long pratilipiId, String cursor, Integer resultCount );
	DataListCursorTuple<UserPratilipi> getUserPratilipiList( Long userId, Long pratilipiId, String cursor, Integer resultCount );
	UserPratilipi createOrUpdateUserPratilipi( UserPratilipi userPratilipi );
	
	
	//CATEGORY Table
	Category getCategory( Long categoryId );
	List<Category> getCategoryList( Language language ) throws UnexpectedServerException;
	
	
	//PRATILIPI_CATEGORY Table
	List<PratilipiCategory> getPratilipiCategoryList( Long pratilipiId );

	
	// Destroy
	void destroy();
	
}
