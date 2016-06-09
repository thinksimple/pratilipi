package com.pratilipi.data;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;

import com.pratilipi.common.type.CommentParentType;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.MailingList;
import com.pratilipi.common.type.PageType;
import com.pratilipi.common.type.ReferenceType;
import com.pratilipi.common.util.AuthorFilter;
import com.pratilipi.common.util.BlogPostFilter;
import com.pratilipi.common.util.PratilipiFilter;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.type.AppProperty;
import com.pratilipi.data.type.AuditLog;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Blog;
import com.pratilipi.data.type.BlogPost;
import com.pratilipi.data.type.Category;
import com.pratilipi.data.type.Comment;
import com.pratilipi.data.type.Event;
import com.pratilipi.data.type.MailingListSubscription;
import com.pratilipi.data.type.Navigation;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.User;
import com.pratilipi.data.type.UserAuthor;
import com.pratilipi.data.type.UserPratilipi;
import com.pratilipi.data.type.Vote;

public interface DataAccessor {

	PersistenceManager getPersistenceManager();

	
	// APP_PROPERTY Table
	AppProperty newAppProperty( String id );
	AppProperty getAppProperty( String id );
	AppProperty createOrUpdateAppProperty( AppProperty appProperty );

	
	// USER Table
	User newUser();
	User getUser( Long id );
	User getUserByEmail( String email );
	User getUserByFacebookId( String facebookId );
	Map<Long, User> getUsers( List<Long> idList );
	@Deprecated
	DataListCursorTuple<User> getUserList( String cursorStr, Integer resultCount );
	User createOrUpdateUser( User user );
	User createOrUpdateUser( User user, AuditLog auditLog );

	// ACCESS_TOKEN Table
	AccessToken newAccessToken();
	AccessToken getAccessToken( String accessTokenId );
	DataListCursorTuple<AccessToken> getAccessTokenList( String cursorStr, Integer resultCount );
	DataListCursorTuple<AccessToken> getAccessTokenList( Long userId, Date minExpiry, String cursorStr, Integer resultCount );
	AccessToken createOrUpdateAccessToken( AccessToken accessToken );
	AccessToken createOrUpdateAccessToken( AccessToken newAccessToken, AccessToken oldAccessToken );
	void deleteAccessToken( AccessToken accessToken );

	// AUDIT_LOG Table
	AuditLog newAuditLogOfy();
	@Deprecated
	AuditLog newAuditLog();
	@Deprecated
	AuditLog createAuditLog( AuditLog auditLog );
	@Deprecated
	DataListCursorTuple<AuditLog> getAuditLogList( String cursor, Integer resultCount );
	@Deprecated
	DataListCursorTuple<AuditLog> getAuditLogList( String accessId, String cursor, Integer resultCount );

	
	// PAGE Table
	Page newPage();
	Page getPage( Long id );
	Page getPage( String uri );
	Page getPage( PageType pageType, Long primaryContentId );
	Map<String, Page> getPages( List<String> uriList );
	Map<Long, Page> getPages( PageType pageType, List<Long> primaryContentIdList );
	Page createOrUpdatePage( Page page );
	void deletePage( Page page );

	// PRATILIPI Table
	Pratilipi newPratilipi();
	Pratilipi getPratilipi( Long id );
	List<Pratilipi> getPratilipiList( List<Long> idList );
	@Deprecated
	DataListCursorTuple<Long> getPratilipiIdList( PratilipiFilter pratilipiFilter, String cursorStr, Integer resultCount );
	DataListCursorTuple<Long> getPratilipiIdList( PratilipiFilter pratilipiFilter, String cursorStr, Integer offset, Integer resultCount );
	DataListCursorTuple<Pratilipi> getPratilipiList( PratilipiFilter pratilipiFilter, String cursorStr, Integer resultCount );
	Pratilipi createOrUpdatePratilipi( Pratilipi pratilipi, AuditLog auditLog );
	Pratilipi createOrUpdatePratilipi( Pratilipi pratilipi, Page page, AuditLog auditLog );
	
	// AUTHOR Table
	Author newAuthor();
	Author getAuthor( Long id );
	Author getAuthorByUserId( Long userId );
	List<Author> getAuthorList( List<Long> idList );
	DataListCursorTuple<Long> getAuthorIdList( AuthorFilter authorFilter, String cursor, Integer resultCount );
	DataListCursorTuple<Author> getAuthorList( AuthorFilter authorFilter, String cursor, Integer resultCount );
	@Deprecated
	Author createOrUpdateAuthor( Author author );
	Author createOrUpdateAuthor( Author author, AuditLog auditLog );

	// EVENT Table
	Event newEvent();
	Event getEvent( Long id );
	List<Event> getEventList( Language language );
	Event createOrUpdateEvent( Event event, AuditLog auditLog );
	Event createOrUpdateEvent( Event event, Page page, AuditLog auditLog );


	// BLOG Table
	Blog newBlog();
	Blog getBlog( Long id );
	Blog createOrUpdateBlog( Blog blog, AuditLog auditLog );
	
	// BLOG_POST Table
	BlogPost newBlogPost();
	BlogPost getBlogPost( Long id );
	DataListCursorTuple<BlogPost> getBlogPostList( BlogPostFilter blogPostFilter, String cursor, Integer offset, Integer resultCount );
	BlogPost createOrUpdateBlogPost( BlogPost blogPost, AuditLog auditLog );
	BlogPost createOrUpdateBlogPost( BlogPost blogPost, Page page, AuditLog auditLog );
	
	
	// USER_PRATILIPI Table
	UserPratilipi newUserPratilipi();
	UserPratilipi getUserPratilipi( String userPratilipiId );
	UserPratilipi getUserPratilipi( Long userId, Long pratilipiId );
	DataListCursorTuple<Long> getUserLibrary( Long userId, String cursorStr, Integer offset, Integer resultCount );
	DataListCursorTuple<UserPratilipi> getUserPratilipiList( Long userId, Long pratilipiId, String cursor, Integer resultCount );
	UserPratilipi createOrUpdateUserPratilipi( UserPratilipi userPratilipi );
	
	// USER_AUTHOR Table
	UserAuthor newUserAuthor();
	UserAuthor getUserAuthor( Long userId, Long authorId );
	DataListCursorTuple<UserAuthor> getUserAuthorList( Long userId, Long authorId, String cursor, Integer offset, Integer resultCount );
	UserAuthor createOrUpdateUserAuthor( UserAuthor userAuthor );
//	UserAuthor createOrUpdateUserAuthor( UserAuthor userAuthor, AuditLog auditLog ); // TODO

	
	// NAVIGATION Table
	List<Navigation> getNavigationList( Language language );
	
	
	// CATEGORY Table
	Category getCategory( Long categoryId );
	List<Category> getCategoryList( Language language );

	
	// COMMENT Table
	Comment newComment();
	Comment getComment( Long commentId );
	List<Comment> getCommentList( CommentParentType parentType, Long parentId );
	List<Comment> getCommentList( CommentParentType parentType, String parentId );
	List<Comment> getCommentListByReference( ReferenceType referenceType, Long referenceId );
	List<Comment> getCommentListByReference( ReferenceType referenceType, String referenceId );
	Comment createOrUpdateComment( Comment comment, AuditLog auditLog );
	
	// VOTE Table
	Vote newVote();
	Vote createOrUpdateVote( Vote vote, AuditLog auditLog );
	List<Vote> getVoteListByReference( ReferenceType referenceType, Long referenceId );
	List<Vote> getVoteListByReference( ReferenceType referenceType, String referenceId );
	
	
	// MAILING_LIST_SUBSCRIPTION Table
	MailingListSubscription newMailingListSubscription();
	MailingListSubscription getMailingListSubscription( MailingList mailingList, String email );
	MailingListSubscription createOrUpdateMailingListSubscription( MailingListSubscription mailingListSubscription, AuditLog auditLog );
	
	
	// Destroy
	void destroy();
	
}
