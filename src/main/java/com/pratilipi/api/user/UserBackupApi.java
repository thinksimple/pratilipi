package com.pratilipi.api.user;

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.api.user.shared.GetUserBackupRequest;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.util.GsonIstDateAdapter;
import com.pratilipi.data.BlobAccessor;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.type.BlobEntry;
import com.pratilipi.data.type.User;

@SuppressWarnings("serial")
@Bind( uri = "/user/backup" )
public class UserBackupApi extends GenericApi {

	private static final Logger logger =
			Logger.getLogger( UserBackupApi.class.getName() );
	
	private static final String CSV_HEADER = "UserId,FacebookId,FirstName,LastName,NickName,Email";
	private static final String CSV_SEPARATOR = ",";
	private static final String LINE_SEPARATOR = "\n";
	
	
	@Get
	public GenericResponse get( GetUserBackupRequest request ) throws UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessorBackup();
		
		Date backupDate = new Date();

		StringBuilder backup = new StringBuilder();
		StringBuilder csv = new StringBuilder( CSV_HEADER + LINE_SEPARATOR );
		
		int count = 0;
		String cursor = null;
		Gson gson = new GsonBuilder().registerTypeAdapter( Date.class, new GsonIstDateAdapter() ).create();
		
		while( true ) {
			DataListCursorTuple<User> userListCursorTupe = dataAccessor.getUserList( cursor, 1000 );
			List<User> userList = userListCursorTupe.getDataList();

			for( User user : userList ) {
				backup.append( gson.toJson( user ) + LINE_SEPARATOR );
				
				if( request.generateCsv() )
					csv.append( user.getId() ).append( CSV_SEPARATOR )
							.append( user.getFacebookId() ).append( CSV_SEPARATOR )
							.append( user.getFirstName() ).append( CSV_SEPARATOR )
							.append( user.getLastName() ).append( CSV_SEPARATOR )
							.append( user.getNickName() ).append( CSV_SEPARATOR )
							.append( user.getEmail() ).append( LINE_SEPARATOR );
				
			}
			
			count = count + userList.size();

			if( userList.size() < 1000 )
				break;
			else
				cursor = userListCursorTupe.getCursor();
		}
		

		DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
		DateFormat dateTimeFormat = new SimpleDateFormat( "yyyy-MM-dd-HH:mm-z" );
		dateFormat.setTimeZone( TimeZone.getTimeZone( "Asia/Kolkata" ) );
		dateTimeFormat.setTimeZone( TimeZone.getTimeZone( "Asia/Kolkata" ) );
	
		String fileName = "datastore.user/"
				+ dateFormat.format( backupDate ) + "/"
				+ "user-" + dateTimeFormat.format( backupDate );

		BlobEntry userBackupEntry = blobAccessor.newBlob(
				fileName,
				backup.toString().getBytes( Charset.forName( "UTF-8" ) ),
				"text/plain" );
		
		blobAccessor.createOrUpdateBlob( userBackupEntry );

		
		if( request.generateCsv() ) {
			BlobEntry userCsvEntry = blobAccessor.newBlob(
					fileName + ".csv",
					csv.toString().getBytes( Charset.forName( "UTF-8" ) ),
					"text/plain" );
			blobAccessor.createOrUpdateBlob( userCsvEntry );
		}

		
		logger.log( Level.INFO, "Backed up " + count + " User Entities." );

		return new GenericResponse();
		
	}
	
}