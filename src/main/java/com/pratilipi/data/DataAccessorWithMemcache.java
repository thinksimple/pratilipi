package com.pratilipi.data;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pratilipi.common.type.AccessType;
import com.pratilipi.common.type.CommentParentType;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.MailingList;
import com.pratilipi.common.type.PageType;
import com.pratilipi.common.type.ReferenceType;
import com.pratilipi.common.type.VoteParentType;
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

public class DataAccessorWithMemcache implements DataAccessor {
	
	private final DataAccessor dataAccessor;
	
	
	public DataAccessorWithMemcache( DataAccessor dataAccessor, Memcache memcache ) {
		this.dataAccessor = dataAccessor;
	}

	
	// APP_PROPERTY Table
	@Override public AppProperty newAppProperty( String id ) { return dataAccessor.newAppProperty( id ); }
	@Override public AppProperty getAppProperty( String id ) { return dataAccessor.getAppProperty( id ); }
	@Override public AppProperty createOrUpdateAppProperty( AppProperty appProperty ) { return dataAccessor.createOrUpdateAppProperty( appProperty ); }


	// USER Table
	@Override public User newUser() { return dataAccessor.newUser(); }
	@Override public User getUser( Long id ) { return dataAccessor.getUser( id ); }
	@Override public User getUserByEmail( String email ) { return dataAccessor.getUserByEmail( email ); }
	@Override public User getUserByFacebookId( String facebookId ) { return dataAccessor.getUserByFacebookId( facebookId ); }
	@Override public List<User> getUserList( List<Long> idList ) { return dataAccessor.getUserList( idList ); }
	@Override public DataListCursorTuple<User> getUserList( String cursor, Integer resultCount ) { return dataAccessor.getUserList( cursor, resultCount ); }
	@Override public User createOrUpdateUser( User user ) { return dataAccessor.createOrUpdateUser( user ); }
	@Override public User createOrUpdateUser( User user, AuditLog auditLog ) { return dataAccessor.createOrUpdateUser( user, auditLog ); }

	// ACCESS_TOKEN Table
	@Override public AccessToken newAccessToken() { return dataAccessor.newAccessToken(); }
	@Override public AccessToken getAccessToken( String accessTokenId ) { return dataAccessor.getAccessToken( accessTokenId ); }
	@Override public DataListCursorTuple<AccessToken> getAccessTokenList( Long userId, Date minExpiry, String cursorStr, Integer resultCount ) { return dataAccessor.getAccessTokenList( userId, minExpiry, cursorStr, resultCount ); }
	@Override public AccessToken createOrUpdateAccessToken( AccessToken accessToken ) { return dataAccessor.createOrUpdateAccessToken( accessToken ); }
	@Override public AccessToken createOrUpdateAccessToken( AccessToken newAccessToken, AccessToken oldAccessToken ) { return dataAccessor.createOrUpdateAccessToken( newAccessToken, oldAccessToken ); }
	
	// AUDIT_LOG Table
	@Override public AuditLog newAuditLogOfy() { return dataAccessor.newAuditLogOfy(); }
	@Override public AuditLog newAuditLogOfy( String accessId, AccessType accessType, Object eventDataOld ) { return dataAccessor.newAuditLogOfy( accessId, accessType, eventDataOld ); }
	
	
	// PAGE Table
	@Override public Page newPage() { return dataAccessor.newPage(); }
	@Override public Page getPage( Long id ) { return dataAccessor.getPage( id ); }
	@Override public Page getPage( String uri ) { return dataAccessor.getPage( uri ); }
	@Override public Page getPage( PageType pageType, Long primaryContentId ) { return dataAccessor.getPage( pageType, primaryContentId ); }
	@Override public Map<String, Page> getPages( List<String> uriList ) { return dataAccessor.getPages( uriList ); }
	@Override public Map<Long, Page> getPages( PageType pageType, List<Long> primaryContentIdList ) { return dataAccessor.getPages( pageType, primaryContentIdList ); }
	@Override public Page createOrUpdatePage( Page page ) { return dataAccessor.createOrUpdatePage( page ); }
	@Override public void deletePage( Page page ) { dataAccessor.deletePage( page ); }

	// PRATILIPI Table & curated/list.<list-name>.<lang>
	@Override public Pratilipi newPratilipi() { return dataAccessor.newPratilipi(); }
	@Override public Pratilipi getPratilipi( Long id ) { return dataAccessor.getPratilipi( id ); }
	@Override public String getPratilipiListTitle( String listName, Language language ) { return dataAccessor.getPratilipiListTitle( listName, language ); }
	@Override public List<Pratilipi> getPratilipiList( List<Long> idList ) { return dataAccessor.getPratilipiList( idList ); }
	@Override public DataListCursorTuple<Long> getPratilipiIdList( PratilipiFilter pratilipiFilter, String cursorStr, Integer resultCount ) { return dataAccessor.getPratilipiIdList( pratilipiFilter, cursorStr, resultCount ); }
	@Override public DataListCursorTuple<Long> getPratilipiIdList( PratilipiFilter pratilipiFilter, String cursorStr, Integer offset, Integer resultCount ) { return dataAccessor.getPratilipiIdList( pratilipiFilter, cursorStr, offset, resultCount ); }
	@Override public DataListCursorTuple<Pratilipi> getPratilipiList( PratilipiFilter pratilipiFilter, String cursorStr, Integer resultCount ) { return dataAccessor.getPratilipiList( pratilipiFilter, cursorStr, resultCount ); }
	@Override public Pratilipi createOrUpdatePratilipi( Pratilipi pratilipi, AuditLog auditLog ) { return dataAccessor.createOrUpdatePratilipi( pratilipi, auditLog ); }
	@Override public Pratilipi createOrUpdatePratilipi( Pratilipi pratilipi, Page page, AuditLog auditLog ) { return dataAccessor.createOrUpdatePratilipi( pratilipi, page, auditLog ); }
	
	// AUTHOR Table
	@Override public Author newAuthor() { return dataAccessor.newAuthor(); }
	@Override public Author getAuthor( Long id ) { return dataAccessor.getAuthor( id ); }
	@Override public Author getAuthorByUserId( Long userId ) { return dataAccessor.getAuthorByUserId( userId ); }
	@Override public List<Author> getAuthorList( List<Long> idList ) { return dataAccessor.getAuthorList( idList ); }
	@Override public DataListCursorTuple<Long> getAuthorIdList( AuthorFilter authorFilter, String cursor, Integer resultCount ) { return dataAccessor.getAuthorIdList( authorFilter, cursor, resultCount ); }
	@Override public DataListCursorTuple<Author> getAuthorList( AuthorFilter authorFilter, String cursor, Integer resultCount ) { return dataAccessor.getAuthorList( authorFilter, cursor, resultCount ); }
	@Override public Author createOrUpdateAuthor( Author author ) { return dataAccessor.createOrUpdateAuthor( author ); }
	@Override public Author createOrUpdateAuthor( Author author, AuditLog auditLog ) { return dataAccessor.createOrUpdateAuthor( author, auditLog ); }

	// EVENT Table
	@Override public Event newEvent() { return dataAccessor.newEvent(); }
	@Override public Event getEvent( Long id ) { return dataAccessor.getEvent( id ); }
	@Override public List<Event> getEventList( Language language ) { return dataAccessor.getEventList( language ); }
	@Override public Event createOrUpdateEvent( Event event, AuditLog auditLog ) { return dataAccessor.createOrUpdateEvent( event, auditLog ); }
	@Override public Event createOrUpdateEvent( Event event, Page page, AuditLog auditLog ) { return dataAccessor.createOrUpdateEvent( event, page, auditLog ); }

	
	// BLOG Table
	@Override public Blog newBlog() { return dataAccessor.newBlog(); }
	@Override public Blog getBlog( Long id ) { return dataAccessor.getBlog( id ); }
	@Override public Blog createOrUpdateBlog( Blog blog, AuditLog auditLog ) { return dataAccessor.createOrUpdateBlog( blog, auditLog ); }

	// BLOG_POST Table
	@Override public BlogPost newBlogPost() { return dataAccessor.newBlogPost(); }
	@Override public BlogPost getBlogPost( Long id ) { return dataAccessor.getBlogPost( id ); }
	@Override public DataListCursorTuple<BlogPost> getBlogPostList( BlogPostFilter blogPostFilter, String cursor, Integer offset, Integer resultCount ) { return dataAccessor.getBlogPostList( blogPostFilter, cursor, offset, resultCount ); }
	@Override public BlogPost createOrUpdateBlogPost( BlogPost blogPost, AuditLog auditLog ) { return dataAccessor.createOrUpdateBlogPost( blogPost, auditLog ); }
	@Override public BlogPost createOrUpdateBlogPost( BlogPost blogPost, Page page, AuditLog auditLog ) { return dataAccessor.createOrUpdateBlogPost( blogPost, page, auditLog ); }
	
	
	// USER_PRATILIPI Table
	@Override public UserPratilipi newUserPratilipi() { return dataAccessor.newUserPratilipi(); }
	@Override public UserPratilipi getUserPratilipi( String userPratilipiId ) { return dataAccessor.getUserPratilipi( userPratilipiId ); }
	@Override public UserPratilipi getUserPratilipi( Long userId, Long pratilipiId ) { return dataAccessor.getUserPratilipi( userId, pratilipiId ); }
	@Override public DataListCursorTuple<Long> getUserLibrary( Long userId, String cursorStr, Integer offset, Integer resultCount ) { return dataAccessor.getUserLibrary( userId, cursorStr, offset, resultCount ); }
	@Override public DataListCursorTuple<UserPratilipi> getUserPratilipiList( Long userId, Long pratilipiId, String cursorStr, Integer resultCount ) { return dataAccessor.getUserPratilipiList( userId, pratilipiId, cursorStr, resultCount ); }
	@Override public UserPratilipi createOrUpdateUserPratilipi( UserPratilipi userPratilipi, AuditLog auditLog ) { return dataAccessor.createOrUpdateUserPratilipi( userPratilipi, auditLog ); }
	
	// USER_AUTHOR Table
	@Override public UserAuthor newUserAuthor() { return dataAccessor.newUserAuthor(); }
	@Override public UserAuthor getUserAuthor( Long userId, Long authorId ) { return dataAccessor.getUserAuthor( userId, authorId ); }
	@Override public DataListCursorTuple<Long> getUserAuthorFollowList( Long userId, Long authorId, String cursor, Integer offset, Integer resultCount ) { return dataAccessor.getUserAuthorFollowList( userId, authorId, cursor, offset, resultCount ); }
	@Override public DataListCursorTuple<UserAuthor> getUserAuthorList( Long userId, Long authorId, String cursor, Integer offset, Integer resultCount ) { return dataAccessor.getUserAuthorList( userId, authorId, cursor, offset, resultCount ); }
	@Override public UserAuthor createOrUpdateUserAuthor( UserAuthor userAuthor, AuditLog auditLog ) { return dataAccessor.createOrUpdateUserAuthor( userAuthor, auditLog ); }
	
	
	// curated/home.<lang>
	@Override public List<String> getHomeSectionList( Language language ) { return dataAccessor.getHomeSectionList( language ); }
	
	// NAVIGATION Table
	@Override public List<Navigation> getNavigationList( Language language ) { return dataAccessor.getNavigationList( language ); }

	// CATEGORY Table
	@Override public List<Category> getCategoryList( Language language ) { return dataAccessor.getCategoryList( language ); }

	
	// COMMENT Table
	@Override public Comment newComment() { return dataAccessor.newComment(); }
	@Override public Comment getComment( Long commentId ) { return dataAccessor.getComment( commentId ); }
	@Override public List<Comment> getCommentList( CommentParentType parentType, Long parentId ) { return dataAccessor.getCommentList( parentType, parentId ); }
	@Override public List<Comment> getCommentList( CommentParentType parentType, String parentId ) { return dataAccessor.getCommentList( parentType, parentId ); }
	@Override public List<Comment> getCommentListByReference( ReferenceType referenceType, Long referenceId ) { return dataAccessor.getCommentListByReference( referenceType, referenceId ); }
	@Override public List<Comment> getCommentListByReference( ReferenceType referenceType, String referenceId ) { return dataAccessor.getCommentListByReference( referenceType, referenceId ); }
	@Override public Comment createOrUpdateComment( Comment comment, AuditLog auditLog ) { return dataAccessor.createOrUpdateComment( comment, auditLog ); }
	
	// USER_VOTE Table
	@Override public Vote newVote() { return dataAccessor.newVote(); }
	@Override public Vote getVote( Long userId, VoteParentType parentType, String parentId ) { return dataAccessor.getVote( userId, parentType, parentId ); }
	@Override public List<Vote> getVoteListByReference( ReferenceType referenceType, Long referenceId ) { return dataAccessor.getVoteListByReference( referenceType, referenceId ); }
	@Override public List<Vote> getVoteListByReference( ReferenceType referenceType, String referenceId ) { return dataAccessor.getVoteListByReference( referenceType, referenceId ); }
	@Override public Vote createOrUpdateVote( Vote vote, AuditLog auditLog ) { return dataAccessor.createOrUpdateVote( vote, auditLog ); }
	
	
	// MAILING_LIST_SUBSCRIPTION Table
	@Override public MailingListSubscription newMailingListSubscription() { return dataAccessor.newMailingListSubscription(); }
	@Override public MailingListSubscription getMailingListSubscription( MailingList mailingList, String email ) { return dataAccessor.getMailingListSubscription( mailingList, email ); }
	@Override public MailingListSubscription createOrUpdateMailingListSubscription( MailingListSubscription mailingListSubscription, AuditLog auditLog ) { return dataAccessor.createOrUpdateMailingListSubscription( mailingListSubscription, auditLog ); }
	
}
