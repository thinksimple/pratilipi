package com.pratilipi.common.util;

import java.util.Date;
import java.util.List;

import com.googlecode.objectify.ObjectifyService;
import com.pratilipi.common.type.AuthorState;
import com.pratilipi.common.type.CommentState;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.type.UserReviewState;
import com.pratilipi.common.type.UserState;
import com.pratilipi.common.type.VoteType;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Comment;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.User;
import com.pratilipi.data.type.UserAuthor;
import com.pratilipi.data.type.UserPratilipi;
import com.pratilipi.data.type.Vote;
import com.pratilipi.data.type.gae.AccessTokenEntity;
import com.pratilipi.data.type.gae.AuthorEntity;
import com.pratilipi.data.type.gae.CommentEntity;
import com.pratilipi.data.type.gae.PageEntity;
import com.pratilipi.data.type.gae.PratilipiEntity;
import com.pratilipi.data.type.gae.UserAuthorEntity;
import com.pratilipi.data.type.gae.UserEntity;
import com.pratilipi.data.type.gae.UserPratilipiEntity;
import com.pratilipi.data.type.gae.VoteEntity;

public class DataStoreCleanupUtil {

	public static void delete( String email, boolean delete ) {

		// USER Table
		
		List<UserEntity> userList = ObjectifyService.ofy().load()
				.type( UserEntity.class )
				.filter( "EMAIL", email )
				.list();
		
		System.out.println( "UserEntity # " + userList.size() );
		
		if( userList.size() == 0 )
			return;

		for( User user : userList )
			delete( user, delete );
		
	}
	
	public static void delete( User user, boolean delete ) {

		System.out.println();
		System.out.println( "User id: " + user.getId() + ", state: " + user.getState() );


		if( delete && user.getState() != UserState.DELETED && user.getState() != UserState.BLOCKED ) {
			user.setState( UserState.DELETED );
			ObjectifyService.ofy().save().entity( user ); // Save
		}

		
		// ACCESS_TOKEN Table
		
		List<AccessTokenEntity> accessTokenList = ObjectifyService.ofy().load()
				.type( AccessTokenEntity.class )
				.filter( "USER_ID", user.getId() )
				.filter( "EXPIRY >", new Date() )
				.list();

		System.out.println( "AccessTokenEntity # " + accessTokenList.size() );
		
		if( delete ) {
			for( AccessToken accessToken : accessTokenList ) {
				accessToken.setExpiry( new Date() );
				ObjectifyService.ofy().save().entity( accessToken ); // Save
			}
		}
		
		
		// USER_PRATILIPI Table
		
		List<UserPratilipiEntity> userPratilipiList = ObjectifyService.ofy().load()
				.type( UserPratilipiEntity.class )
				.filter( "USER_ID", user.getId() )
				.list();
		
		System.out.println( "UserPratilipiEntity # " + userPratilipiList.size() );

		int reviewCount = 0;
		for( UserPratilipi userPratilipi : userPratilipiList )
			if( userPratilipi.getReviewState() != UserReviewState.DELETED && userPratilipi.getReviewState() != UserReviewState.BLOCKED );
				reviewCount++;
		
		System.out.println( "Review ## " + reviewCount );

		if( delete ) {
			for( UserPratilipi userPratilipi : userPratilipiList ) {
				if( userPratilipi.getReviewState() != UserReviewState.DELETED && userPratilipi.getReviewState() != UserReviewState.BLOCKED ) {
					userPratilipi.setReviewState( UserReviewState.DELETED );
					ObjectifyService.ofy().save().entity( userPratilipi ); // Save
				}
			}
		}
		
		
		// USER_AUTHOR Table
		
		List<UserAuthorEntity> userAuthorList = ObjectifyService.ofy().load()
				.type( UserAuthorEntity.class )
				.filter( "USER_ID", user.getId() )
				.list();

		System.out.println( "UserAuthorEntity # " + userAuthorList.size() );

		int followCount = 0;
		for( UserAuthor userAuthor : userAuthorList )
			if( userAuthor.isFollowing() )
				followCount++;
		
		System.out.println( "Follow ## " + followCount );

		if( delete ) {
			for( UserAuthor userAuthor : userAuthorList ) {
				if( userAuthor.isFollowing() ) {
					userAuthor.setFollowing( false );
					ObjectifyService.ofy().save().entity( userAuthor ); // Save
				}
			}
		}
		
		
		// COMMENT Table
		
		List<CommentEntity> commentList = ObjectifyService.ofy().load()
				.type( CommentEntity.class )
				.filter( "USER_ID", user.getId() )
				.list();
		
		System.out.println( "CommentEntity # " + commentList.size() );

		int commentCount = 0;
		for( Comment comment : commentList )
			if( comment.getState() == CommentState.ACTIVE )
				commentCount++;
		
		System.out.println( "Comment ## " + commentCount );

		if( delete ) {
			for( Comment comment : commentList ) {
				if( comment.getState() == CommentState.ACTIVE ) {
					comment.setState( CommentState.DELETED );
					ObjectifyService.ofy().save().entity( comment ); // Save
				}
			}
		}
		
		
		// VOTE Table
		
		List<VoteEntity> voteList = ObjectifyService.ofy().load()
				.type( VoteEntity.class )
				.filter( "USER_ID", user.getId() )
				.list();
		
		System.out.println( "VoteEntity # " + voteList.size() );

		int voteCount = 0;
		for( Vote vote : voteList )
			if( vote.getType() != VoteType.NONE )
				voteCount++;
		
		System.out.println( "Vote count: " + voteCount );

		if( delete )
			for( Vote vote : voteList )
				ObjectifyService.ofy().delete().entity( vote ); // Delete
		
		
		// AUTHOR Table
		
		List<AuthorEntity> authorList = ObjectifyService.ofy().load()
				.type( AuthorEntity.class )
				.filter( "USER_ID", user.getId() )
				.list();
		
		System.out.println( "AuthorEntity # " + authorList.size() );
		
		if( authorList.size() == 0 )
			return;

		for( Author author : authorList )
			delete( author, delete );

	}
	
	private static void delete( Author author, boolean delete ) {
		
		System.out.println();
		System.out.println( "Author id: " + author.getId() + ", state: " + author.getState() );
		
		
		if( delete && author.getState() != AuthorState.DELETED && author.getState() != AuthorState.BLOCKED ) {
			author.setState( AuthorState.DELETED );
			ObjectifyService.ofy().save().entity( author ); // Save
		}
		
		
		// USER_AUTHOR Table
		
		List<UserAuthorEntity> userAuthorList = ObjectifyService.ofy().load()
				.type( UserAuthorEntity.class )
				.filter( "AUTHOR_ID", author.getId() )
				.list();

		System.out.println( "UserAuthorEntity # " + userAuthorList.size() );

		int followerCount = 0;
		for( UserAuthor userAuthor : userAuthorList )
			if( userAuthor.isFollowing() )
				followerCount++;
		
		System.out.println( "Follower ## " + followerCount );

		if( delete ) {
			for( UserAuthor userAuthor : userAuthorList ) {
				if( userAuthor.isFollowing() ) {
					userAuthor.setFollowing( false );
					ObjectifyService.ofy().save().entity( userAuthor ); // Save
				}
			}
		}


		// PAGE Table

		List<PageEntity> pageList = ObjectifyService.ofy().load()
				.type( PageEntity.class )
				.filter( "PAGE_TYPE", "AUTHOR" )
				.filter( "PRIMARY_CONTENT_ID", author.getId() )
				.list();
		
		System.out.println( "PageEntity # " + pageList );

		if( delete ) {
			for( Page page : pageList )
				ObjectifyService.ofy().delete().entity( page ); // Delete
		}

		
		// PRATILIPI Table
		
		List<PratilipiEntity> pratilipiList = ObjectifyService.ofy().load()
				.type( PratilipiEntity.class )
				.filter( "AUTHOR_ID", author.getId() )
				.list();
		
		System.out.println( "PratilipiEntity # " + pageList );
		
		for( Pratilipi pratilipi : pratilipiList )
			delete( pratilipi, delete );
	
	}
	
	public static void delete( Pratilipi pratilipi, boolean delete ) {
		
		if( pratilipi.getState() != PratilipiState.DELETED && pratilipi.getState() != PratilipiState.BLOCKED ) {
			pratilipi.setState( PratilipiState.DELETED );
			ObjectifyService.ofy().save().entity( pratilipi ); // Save
		}
		
		
		// PAGE Table

		List<PageEntity> pageList = ObjectifyService.ofy().load()
				.type( PageEntity.class )
				.filter( "PAGE_TYPE", "PRATILIPI" )
				.filter( "PRIMARY_CONTENT_ID", pratilipi.getId() )
				.list();
		
		System.out.println( "PageEntity # " + pageList );

		if( delete )
			for( Page page : pageList )
				ObjectifyService.ofy().delete().entity( page ); // Delete
		
	}

}
