package com.pratilipi.api.impl.user;

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.googlecode.objectify.ObjectifyService;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.util.GsonLongDateAdapter;
import com.pratilipi.data.BlobAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.BlobEntry;
import com.pratilipi.data.type.User;
import com.pratilipi.data.type.gae.UserEntity;


@SuppressWarnings("serial")
@Bind( uri = "/user/backup" )
public class UserBackupApi extends GenericApi {

	private static final Logger logger =
			Logger.getLogger( UserBackupApi.class.getName() );
	
	private static final String CSV_HEADER = "UserId,FacebookId,Email,SignUpDate";
	private static final String CSV_SEPARATOR = ",";
	private static final String LINE_SEPARATOR = "\n";
	
	
	public static class GetRequest extends GenericRequest {
		
		private Boolean generateCsv;
			
		public boolean generateCsv() {
			return generateCsv == null ? false : generateCsv;
		}
		
	}

	
	@Get
	public GenericResponse get( GetRequest request ) throws UnexpectedServerException {
		
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessorBackup();
		
		
		Date backupDate = new Date();

		
		DateFormat yearFormat = new SimpleDateFormat( "yyyy" );
		DateFormat dayFormat = new SimpleDateFormat( "dd" );
		DateFormat hourFormat = new SimpleDateFormat( "HH" );
		DateFormat csvDateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm" );
		yearFormat.setTimeZone( TimeZone.getTimeZone( "Asia/Kolkata" ) );
		dayFormat.setTimeZone( TimeZone.getTimeZone( "Asia/Kolkata" ) );
		hourFormat.setTimeZone( TimeZone.getTimeZone( "Asia/Kolkata" ) );
		csvDateFormat.setTimeZone( TimeZone.getTimeZone( "Asia/Kolkata" ) );

		
		StringBuilder backup = new StringBuilder();
		StringBuilder csv = new StringBuilder( CSV_HEADER + LINE_SEPARATOR );

		
		Gson gson = new GsonBuilder()
				.registerTypeAdapter( Date.class, new GsonLongDateAdapter() )
				.create();

		
		int count = 0;
		int batchSize = 1000;

		QueryResultIterator<UserEntity> itr = ObjectifyService.ofy().load()
				.type( UserEntity.class )
				.chunk( batchSize )
				.iterator();
		
		while( itr.hasNext() ) {
			User user = itr.next();
			backup.append( gson.toJson( user ) + LINE_SEPARATOR );
			if( request.generateCsv() )
				csv.append( "'" + user.getId().toString() )
						.append( CSV_SEPARATOR ).append( user.getFacebookId() == null ? "" : "'" + user.getFacebookId() )
						.append( CSV_SEPARATOR ).append( user.getEmail()	  == null ? "" : user.getEmail() )
						.append( CSV_SEPARATOR ).append( csvDateFormat.format( user.getSignUpDate() ) )
						.append( LINE_SEPARATOR );
			count++;
			if( count % batchSize == 0 )
				try { Thread.sleep( batchSize / 10 ); } catch( InterruptedException e ) {} // Datastore operation will time out without this
		}

		
		String year = yearFormat.format( backupDate );
		String day = dayFormat.format( backupDate );
		String hour = hourFormat.format( backupDate );
		
		String fileName = "datastore.user/"
				+ year + "-mm-" + day + "/"
				+ "user-" + year + "-mm-" + day + "-" + hour + ":xx-IST";

		BlobEntry userBackupEntry = blobAccessor.newBlob(
				fileName,
				backup.toString().getBytes( Charset.forName( "UTF-8" ) ),
				"text/plain" );
		
		blobAccessor.createOrUpdateBlob( userBackupEntry );

		
		if( request.generateCsv() ) {
			BlobEntry userCsvEntry = blobAccessor.newBlob(
					"datastore/user.csv",
					csv.toString().getBytes( Charset.forName( "UTF-8" ) ),
					"text/csv" );
			blobAccessor.createOrUpdateBlob( userCsvEntry );
		}

		
		logger.log( Level.INFO, "Backed up " + count + " User Entities." );

		return new GenericResponse();
		
	}
	
}