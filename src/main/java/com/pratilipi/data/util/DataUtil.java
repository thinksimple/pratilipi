package com.pratilipi.data.util;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;
import com.google.appengine.tools.remoteapi.RemoteApiInstaller;
import com.google.appengine.tools.remoteapi.RemoteApiOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.AuthorState;
import com.pratilipi.common.type.BatchProcessType;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.NotificationType;
import com.pratilipi.common.type.UserState;
import com.pratilipi.common.util.AuthorFilter;
import com.pratilipi.data.BlobAccessor;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DocAccessor;
import com.pratilipi.data.Memcache;
import com.pratilipi.data.SearchAccessor;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.BatchProcess;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.User;
import com.pratilipi.data.type.gae.AccessTokenEntity;
import com.pratilipi.data.type.gae.AuditLogEntity;
import com.pratilipi.data.type.gae.AuthorEntity;
import com.pratilipi.data.type.gae.PageEntity;
import com.pratilipi.data.type.gae.PratilipiEntity;
import com.pratilipi.data.type.gae.UserAuthorEntity;
import com.pratilipi.data.type.gae.UserEntity;
import com.pratilipi.data.type.gae.UserPratilipiEntity;
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

public class DataUtil {

	public static void main( String ... args ) throws IOException, UnexpectedServerException, InterruptedException, ParseException {
		
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
		
		
/*		Long bpId = 5106528659963904L;
		
		BatchProcessDoc processDoc = docAccessor.getBatchProcessDoc( bpId );
		
		Set<Long> userIdSet = processDoc.getData(
				BatchProcessState.CREATE_NOTIFICATIONS_FOR_USER_IDS.getInputName(),
				new TypeToken<Set<Long>>(){}.getType() );
		System.out.println( userIdSet.size() );
		
		Map<Long,Long> userIdNotifIdMap = processDoc.getData(
				BatchProcessState.CREATE_NOTIFICATIONS_FOR_USER_IDS.getOutputName(),
				new TypeToken<Map<Long,Long>>(){}.getType() );
		System.out.println( userIdNotifIdMap.size() );

		System.out.println(
				ObjectifyService.ofy().load()
						.type( NotificationEntity.class )
						.filter( "CREATED_BY", "BATCH_PROCESS::" + bpId )
						.keys()
						.list()
						.size()
				);
*/		
		
		
/*		System.out.println(
				ObjectifyService.ofy().load()
						.type( NotificationEntity.class )
						.filter( "FCM_PENDING", true )
						.keys()
						.list()
						.size()
				);
*/
		
/*		String[] messages = {
				"நீ ..எந்நாளும் என் நிச்சயிக்கப்பட்ட நண்பன்..யார் என்ன பேசுனா நமக்கென்ன ..' - புனிதமான நட்பின் கதையை படியுங்கள் - 'கவி'",
				"காலை வணக்கம் வினு. 07.06.2098ம் நாளான இன்று தங்களின் ஐம்பதாவது பிறந்தநாள் என்பதை நினைவூட்டுகிறேன்...' - படியுங்கள் அறிவியல் புனைகதை - 'பிறந்தநாள் பரிசு'",
				"வசதி ஒத்து வராதுங்கறாங்க. உங்க வீட்ல தோட்டம் காடு இருக்கற எடமா பாக்கறாங்க - ஒரு பெண்ணின் காதல் கதையை படியுங்கள் - 'ஒரு பாம்பும் சில தட்டான்களும்'",
				"நெற்றியில் முத்தமிட்டு நானும் சித்த கண்ணயர்கிறேன்' - காமம் கடந்த காதல் கதையை படியுங்கள் - 'ஒரு பாஸ்கல் அழுத்தம்'",
		};
		
		String[] sourceUrls = {
				"/karthick/kavi",
				"/benny/pirandha-naal-parisu",
				"/shanmugam-k-shan/oru-paambum-sila-thattangalum",
				"/balagurunathan-murugesan/oru-pascal-azhutham",
		};
				
			
		String[] schedules = {
				"27-NOV-2016 09",
				"27-NOV-2016 11",
				"27-NOV-2016 13",
				"27-NOV-2016 15",
		};
		

		for( int i = 0; i < 4; i++ ) {
			Long bpId = _sendBulkFcm(
					Language.TAMIL,
					messages[i],
					DataAccessorFactory.getDataAccessor().getPage( sourceUrls[i]).getPrimaryContentId(),
					schedules[i] );
			System.out.println( bpId );
		}*/
		
		
/*		User user = dataAccessor.getUserByEmail( "abhishek@pratilipi.com" );
		List<NotificationEntity> notifList = ObjectifyService.ofy().load()
				.type( NotificationEntity.class )
				.filter( "USER_ID", user.getId() )
				.order( "-LAST_UPDATED" )
				.list();
		for( Notification notif : notifList ) {
			if( notif.getCreatedBy() == null )
				continue;
			System.out.println( notif.getId() );
			notif.setFcmPending( true );
			dataAccessor.createOrUpdateNotification( notif );
		}*/
		
		
		/*QueryResultIterator<Key<AuditLogEntity>> itr = ObjectifyService.ofy().load()
				.type( AuditLogEntity.class )
				.filter( "CREATION_DATE", null )
				.chunk( 1000 )
				.keys()
				.iterator();
		
		List<Key<AuditLogEntity>> list = new ArrayList<>( 1000 );
		while( itr.hasNext() ) {
			if( list.size() == 1000 ) {
				
				final List<Key<AuditLogEntity>> alist = list;
				list = new ArrayList<>( 1000 );
				new Thread() {
					public void run() {
						RemoteApiOptions options = new RemoteApiOptions()
								.server( "m.gamma.pratilipi.com", 80 )
								.useServiceAccountCredential(
										"prod-pratilipi@appspot.gserviceaccount.com",
										"PrivateKey.p12" )
							    .remoteApiPath( "/remote_api" );
						RemoteApiInstaller installer = new RemoteApiInstaller();
						try {
							installer.install( options );
							ObjectifyService.begin();
							ObjectifyService.ofy().delete().keys( alist ).now();
							System.out.println( new Date() + ": deleted " + alist.size() + " audit logs ..." );
							installer.uninstall();
						} catch( Exception e ) {
							System.out.println( e.getClass().getName() );
//							e.printStackTrace();
						}
				   }
				}.start();
				
			} else {
				
				list.add( itr.next() );
				
			}
		}*/
		
		
		System.out.println( "Done !" );
		
		installer.uninstall();
		
	}
	
	
	private static Long _sendBulkFcm( Language language, String message, Long pratilipiId, String schedule ) throws ParseException {
		
		AuthorFilter authorFilter = new AuthorFilter();
		authorFilter.setLanguage( language );
		authorFilter.setState( AuthorState.ACTIVE );
		
		JsonObject initDoc = new JsonObject();
		initDoc.add( "authorFilter", new Gson().toJsonTree( authorFilter ) );
 		
		JsonObject execDoc = new JsonObject();
		execDoc.addProperty( "message", message );
		execDoc.addProperty( "sourceId", pratilipiId.toString() );
		execDoc.addProperty( "type", NotificationType.PRATILIPI.toString() );

		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		BatchProcess batchProcess = dataAccessor.newBatchProcess();
		batchProcess.setType( BatchProcessType.ANDROID_NOTIFACTION_BY_AUTHOR_FILTER );
		batchProcess.setCreationDate( new Date() );
		batchProcess.setInitDoc( initDoc.toString() );
		batchProcess.setExecDoc( execDoc.toString() );
		batchProcess.setStartAt( new SimpleDateFormat("dd-MMM-yyyy HH").parse( schedule ) );
		batchProcess.setStateInProgress( null );
		batchProcess.setStateCompleted( null );
		batchProcess = dataAccessor.createOrUpdateBatchProcess( batchProcess );

		return batchProcess.getId();
		
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
