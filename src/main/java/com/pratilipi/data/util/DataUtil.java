package com.pratilipi.data.util;
import java.io.IOException;
import java.util.List;

import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;
import com.google.appengine.tools.remoteapi.RemoteApiInstaller;
import com.google.appengine.tools.remoteapi.RemoteApiOptions;
import com.googlecode.objectify.ObjectifyService;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.AuthorState;
import com.pratilipi.common.type.UserState;
import com.pratilipi.data.BlobAccessor;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DocAccessor;
import com.pratilipi.data.Memcache;
import com.pratilipi.data.SearchAccessor;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.User;
import com.pratilipi.data.type.gae.AccessTokenEntity;
import com.pratilipi.data.type.gae.AuthorEntity;
import com.pratilipi.data.type.gae.PageEntity;
import com.pratilipi.data.type.gae.PratilipiEntity;
import com.pratilipi.data.type.gae.UserAuthorEntity;
import com.pratilipi.data.type.gae.UserEntity;
import com.pratilipi.data.type.gae.UserPratilipiEntity;
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

public class DataUtil {

	public static void main( String ... args ) throws IOException, UnexpectedServerException, InterruptedException {
		
		RemoteApiOptions options = new RemoteApiOptions()
				.server( "m.gamma.pratilipi.com", 80 )
				.useServiceAccountCredential(
						"prod-pratilipi@appspot.gserviceaccount.com",
						"PrivateKey.p12" )
			    .remoteApiPath( "/remote_api" );
		RemoteApiInstaller installer = new RemoteApiInstaller();
		installer.install( options );
		
		ObjectifyService.begin();
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		DocAccessor docAccessor = DataAccessorFactory.getDocAccessor();
		SearchAccessor searchAccessor = DataAccessorFactory.getSearchAccessor();
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
		Memcache memcache = DataAccessorFactory.getL2CacheAccessor();
		GcsService gcsService = GcsServiceFactory.createGcsService( RetryParams.getDefaultInstance() );
		
		
		// START
		
		
/*		QueryResultIterator<Key<AuthorEntity>> iterator = ObjectifyService.ofy().load()
				.type( AuthorEntity.class )
				.chunk( 1000 )
				.keys()
				.iterator();

		List<Task> taskList = new LinkedList<>();
		while( iterator.hasNext() ) {
			Task task = TaskQueueFactory.newTask()
					.setUrl( "/author/process" )
					.addParam( "authorId", iterator.next().getId() + "" )
					.addParam( "processData", "true" );
			taskList.add( task );
		}
		TaskQueueFactory.getAuthorOfflineTaskQueue().addAll( taskList );

		System.out.println( taskList.size() ); */


/*		QueryResultIterator<Key<PratilipiEntity>> iterator = ObjectifyService.ofy().load()
				.type( PratilipiEntity.class )
				.chunk( 1000 )
				.keys()
				.iterator();
		
		List<Task> taskList = new LinkedList<>();
		while( iterator.hasNext() ) {
			Task task = TaskQueueFactory.newTask()
					.setUrl( "/pratilipi/process" )
					.addParam( "pratilipiId", iterator.next().getId() + "" )
					.addParam( "processContent", "true" );
			taskList.add( task );
		}
		TaskQueueFactory.getPratilipiOfflineTaskQueue().addAll( taskList );
		
		System.out.println( taskList.size() ); */

		
		/*String bucketName = "static.pratilipi.com";
		String filePrefix = null;
		
		ListOptions.Builder listOptions = new ListOptions.Builder();
		listOptions.setPrefix( filePrefix );
		
		System.out.println( filePrefix );
		
		ListResult result = gcsService.list( bucketName, listOptions.build() );

		int count = 0;
		while( result.hasNext() ) {
			try {
				
				ListItem source = result.next();
				if( source.isDirectory() )
					continue;
				
				count++;
				
				String[] tokens = source.getName().split( "/" );
				if( tokens.length != 4 ) {
					System.out.println( "\n" + count + "> e " + source.getName() );
					continue;
				}
				
				String destName = "pratilipi/" + tokens[2] + "/images/" + tokens[3];
				
				if( tokens[3].equals( "Thumbs.db" ) ) {
					System.out.println( "\n" + count + "> x " + source.getName() );
					continue;
				}
				
				GcsFileMetadata dest = gcsService.getMetadata( new GcsFilename( bucketName, destName ) );
				if( dest != null && dest.getLength() == source.getLength() && dest.getEtag().equals( source.getEtag() ) ) {
//					System.out.println( count + "> = " + source.getName() );
					System.out.print( "." );
					if( count % 100 == 0 )
						System.out.println();
					continue;
				}
				
				if( dest != null ) {
					System.out.println( "\n" + count + "> o " + source.getName() );
					continue;
				}
	
				System.out.println( "\n" + count + "> c " + source.getName() );
//				gcsService.copy(
//						new GcsFilename( "static.pratilipi.com", source.getName() ),
//						new GcsFilename( "static.pratilipi.com", destName ) );
				
			} catch( Exception e ) {
				System.out.println( "\n" + count + "> r " + e.getMessage() );
			}
		}

		System.out.println( "\nDone !" );*/
		
		
		// END
		
		
		installer.uninstall();
		
	}
	
	
	
	private static void _migratePratilipi( Long fromAuthorId, Long toAuthorId ) {
		
		QueryResultIterator<PratilipiEntity> iterator = ObjectifyService.ofy().load()
				.type( PratilipiEntity.class )
				.filter( "AUTHOR_ID", fromAuthorId )
				.chunk( 100 )
				.iterator();
		
		while( iterator.hasNext() ) {
			Pratilipi pratilipi = iterator.next();
			pratilipi.setAuthorId( toAuthorId );
			ObjectifyService.ofy().save().entity( pratilipi ).now();
			Task task = TaskQueueFactory.newTask()
					.setUrl( "/pratilipi/process" )
					.addParam( "pratilipiId", pratilipi.getId().toString() )
					.addParam( "processData", "true" );
			TaskQueueFactory.getPratilipiTaskQueue().add( task );
			System.out.println( "Migrating Pratilipi: " + pratilipi.getId() );
		}
		
		
	}
	
	
	private static void _deleteUser( Long userId ) {
		
		if( ! _canDeleteUser( userId ) ) {
			System.out.print( "Can't delete user !" );
			return;
		}
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		SearchAccessor searchAccessor = DataAccessorFactory.getSearchAccessor();
		
		List<AuthorEntity> authorList = ObjectifyService.ofy().load()
				.type( AuthorEntity.class )
				.filter( "USER_ID", userId )
				.filter( "STATE !=", AuthorState.DELETED )
				.order( "STATE" )
				.order( "REGISTRATION_DATE" )
				.list();
		
		for( Author author : authorList ) {
			List<PageEntity> pageList = ObjectifyService.ofy().load()
					.type( PageEntity.class )
					.filter( "PAGE_TYPE", "AUTHOR" )
					.filter( "PRIMARY_CONTENT_ID", author.getId() )
					.list();
			for( Page page : pageList )
				dataAccessor.deletePage( page );
			author.setState( AuthorState.DELETED );
			ObjectifyService.ofy().save().entity( author );
			// Deleting search index entry
			searchAccessor.deleteAuthorDataIndex( author.getId() );
		}
		
		User user = ObjectifyService.ofy().load()
				.type( UserEntity.class )
				.id( userId )
				.now();
		user.setState( UserState.DELETED );
		ObjectifyService.ofy().save().entity( user );

	}
	
	
	private static boolean _canDeleteUser( Long userId ) {
		
		List<AuthorEntity> authorList = ObjectifyService.ofy().load()
				.type( AuthorEntity.class )
				.filter( "USER_ID", userId )
				.filter( "STATE !=", AuthorState.DELETED )
				.order( "STATE" )
				.order( "REGISTRATION_DATE" )
				.list();
		
		List<UserPratilipiEntity> userPratilipiList = ObjectifyService.ofy().load()
				.type( UserPratilipiEntity.class )
				.filter( "USER_ID", userId )
				.list();
		
		List<UserAuthorEntity> userAuthorList = ObjectifyService.ofy().load()
				.type( UserAuthorEntity.class )
				.filter( "USER_ID", userId )
				.list();
		
		List<AccessTokenEntity> accessTokenList = ObjectifyService.ofy().load()
				.type( AccessTokenEntity.class )
				.filter( "USER_ID", userId )
				.list();
		
		return ( authorList.size() == 0 || ( authorList.size() == 1 && _canDeleteAuthor( authorList.get( 0 ).getId() ) ) )
				&& userPratilipiList.size() == 0
				&& userAuthorList.size() == 0
				&& accessTokenList.size() == 0;
		
	}
	
	private static boolean _canDeleteAuthor( Long authorId ) {
		
		List<PratilipiEntity> pratilipiList = ObjectifyService.ofy().load()
				.type( PratilipiEntity.class )
				.filter( "AUTHOR_ID", authorId )
				.list();
		
		List<PageEntity> pageList = ObjectifyService.ofy().load()
				.type( PageEntity.class )
				.filter( "PAGE_TYPE", "AUTHOR" )
				.filter( "PRIMARY_CONTENT_ID", authorId )
				.list();
		
		return pratilipiList.size() == 0 && pageList.size() <= 1;
		
	}
	
}
