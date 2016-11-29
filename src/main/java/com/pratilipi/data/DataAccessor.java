package com.pratilipi.data;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pratilipi.common.type.AccessType;
import com.pratilipi.common.type.CommentParentType;
import com.pratilipi.common.type.ContactTeam;
import com.pratilipi.common.type.I18nGroup;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.MailingList;
import com.pratilipi.common.type.NotificationType;
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
import com.pratilipi.data.type.BatchProcess;
import com.pratilipi.data.type.Blog;
import com.pratilipi.data.type.BlogPost;
import com.pratilipi.data.type.Category;
import com.pratilipi.data.type.Comment;
import com.pratilipi.data.type.Conversation;
import com.pratilipi.data.type.ConversationMessage;
import com.pratilipi.data.type.ConversationUser;
import com.pratilipi.data.type.Event;
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
	User getUserByGoogleId( String googleId );
	Map<Long, User> getUsers( List<Long> idList );
	List<User> getUserList( List<Long> idList );
	DataListCursorTuple<User> getUserList( String cursorStr, Integer resultCount );
	@Deprecated
	User createOrUpdateUser( User user );
	User createOrUpdateUser( User user, AuditLog auditLog );

	// ACCESS_TOKEN Table
	AccessToken newAccessToken();
	AccessToken getAccessToken( String accessTokenId );
	List<String> getFcmTokenList( Long userId );
	AccessToken createOrUpdateAccessToken( AccessToken accessToken );

	// AUDIT_LOG Table
	AuditLog newAuditLog();
	AuditLog newAuditLog( AccessToken accessToken, AccessType accessType, Object eventDataOld );
	DataListCursorTuple<AuditLog> getAuditLogList( Date minCreationDate, String cursor, Integer resultCount );

	
	// PAGE Table
	Page newPage();
	Page getPage( Long id );
	Page getPage( String uri );
	Page getPage( PageType pageType, Long primaryContentId );
	Map<String, Page> getPages( List<String> uriList );
	Map<Long, Page> getPages( PageType pageType, List<Long> primaryContentIdList );
	Page createOrUpdatePage( Page page );
	void deletePage( Page page );

	// PRATILIPI Table & curated/list.<list-name>.<lang>
	Pratilipi newPratilipi();
	Pratilipi getPratilipi( Long id );
	String getPratilipiListTitle( String listName, Language language );
	List<Pratilipi> getPratilipiList( List<Long> idList );
	DataListCursorTuple<Long> getPratilipiIdList( PratilipiFilter pratilipiFilter, String cursorStr, Integer offset, Integer resultCount );
	DataListCursorTuple<Pratilipi> getPratilipiList( PratilipiFilter pratilipiFilter, String cursorStr, Integer resultCount );
	Pratilipi createOrUpdatePratilipi( Pratilipi pratilipi, AuditLog auditLog );
	Pratilipi createOrUpdatePratilipi( Pratilipi pratilipi, Page page, AuditLog auditLog );
	
	// AUTHOR Table
	Author newAuthor();
	Author getAuthor( Long id );
	Author getAuthorByUserId( Long userId );
	List<Author> getAuthorListByUserIdList( List<Long> userIdList );
	List<Author> getAuthorList( List<Long> idList );
	DataListCursorTuple<Long> getAuthorIdList( AuthorFilter authorFilter, String cursor, Integer resultCount );
	DataListCursorTuple<Author> getAuthorList( AuthorFilter authorFilter, String cursor, Integer resultCount );
	@Deprecated
	Author createOrUpdateAuthor( Author author );
	Author createOrUpdateAuthor( Author author, AuditLog auditLog );
	Author createOrUpdateAuthor( Author author, Page page, AuditLog auditLog );

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
	List<UserPratilipi> getUserPratilipiList( Long userId, List<Long> pratilipiIdList );
	DataListCursorTuple<Long> getUserLibrary( Long userId, String cursor, Integer offset, Integer resultCount );
	DataListCursorTuple<UserPratilipi> getUserPratilipiList( Long userId, Long pratilipiId, String cursor, Integer resultCount );
	UserPratilipi createOrUpdateUserPratilipi( UserPratilipi userPratilipi, AuditLog auditLog );
	
	// USER_AUTHOR Table
	UserAuthor newUserAuthor();
	UserAuthor getUserAuthor( Long userId, Long authorId );
	List<UserAuthor> getUserAuthorList( Long userId, List<Long> authorIdList );
	List<UserAuthor> getUserAuthorList( List<Long> userIdList, Long authorId );
	DataListCursorTuple<Long> getUserAuthorFollowList( Long userId, Long authorId, String cursor, Integer offset, Integer resultCount );
	DataListCursorTuple<UserAuthor> getUserAuthorList( Long userId, Long authorId, String cursor, Integer offset, Integer resultCount );
	UserAuthor createOrUpdateUserAuthor( UserAuthor userAuthor, AuditLog auditLog );


	// curated/home.<lang>
	List<String> getHomeSectionList( Language language );
	
	// curated/recommend.<lang>
	List<String> getRecommendSectionList( Language language );
	
	// curated/navigation.<lang>
	List<Navigation> getNavigationList( Language language );
	
	// curated/category.<lang>
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
	Vote getVote( Long userId, VoteParentType parentType, String parentId );
	List<Vote> getVoteListByReference( ReferenceType referenceType, Long referenceId );
	List<Vote> getVoteListByReference( ReferenceType referenceType, String referenceId );
	Vote createOrUpdateVote( Vote vote, AuditLog auditLog );
	
	
	// CONVERSATION* Tables
	Conversation newConversation( ContactTeam team, Long userId );
	Conversation newConversation( ContactTeam team, String email );
	Conversation getConversation( ContactTeam team, Long userId );
	Conversation getConversation( ContactTeam team, String email );

	ConversationUser newConversationUser( String conversationId, Long userid );
	
	ConversationMessage newConversationMessage();
	Conversation createOrUpdateConversation( Conversation conversation, List<ConversationUser> conversationUserList );
	Conversation createOrUpdateConversation( Conversation conversation, ConversationMessage conversationMessage );
	
	
	// MAILING_LIST_SUBSCRIPTION Table
	MailingListSubscription newMailingListSubscription();
	List<MailingListSubscription> getMailingListSubscriptionList( MailingList mailingList, String email, String phone );
	MailingListSubscription createOrUpdateMailingListSubscription( MailingListSubscription mailingListSubscription, AuditLog auditLog );
	
	
	// NOTIFICATION Table
	Notification newNotification();
	Notification newNotification( Long userId, NotificationType type, Long sourceId );
	Notification newNotification( Long userId, NotificationType type, String sourceId, String createdBy );
	Notification getNotification( Long notificationId );
	Notification getNotification( Long userId, NotificationType type, Long sourceId );
	Notification getNotification( Long userId, NotificationType type, String sourceId, String createdBy );
	int getNotificationCout( String createdBy );
	List<Notification> getNotificationList( List<Long> notificationIdList );
	List<Notification> getNotificationListWithFcmPending( Integer resultCount );
	DataListIterator<Notification> getNotificationListIterator( Long userId, NotificationType type, Long sourceId, String cursor, Integer resultCount  );
	DataListIterator<Notification> getNotificationListIterator( Long userId, NotificationType type, String sourceId, String cursor, Integer resultCount  );
	Notification createOrUpdateNotification( Notification notification );
	List<Notification> createOrUpdateNotificationList( List<Notification> notificationList );
	
	
	// I18N Table
	I18n newI18n( String i18nId );
	List<I18n> getI18nList( I18nGroup i18nGroup );
	Map<String, String> getI18nStrings( I18nGroup i18nGroup, Language language );
	I18n createOrUpdateI18n( I18n i18n );
	
	
	// BatchProcess Table
	BatchProcess newBatchProcess();
	BatchProcess getBatchProcess( Long batchProcessId );
	List<BatchProcess> getAllBatchProcessList();
	List<BatchProcess> getIncompleteBatchProcessList();
	BatchProcess createOrUpdateBatchProcess( BatchProcess batchProcess );
	
}
