package com.pratilipi.api.user;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.UnexpectedServerException;
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
	
	private static final String CSV_HEADER = "Email,FacebookId,FirstName,LastName,UserId";
	private static final String CSV_SEPARATOR = ",";
	private static final String LINE_SEPARATOR = "\n";
	
	@Get
	public GenericResponse get( GenericRequest request ) throws UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
		
		String cursor = null;
		int count = 0;
		StringBuilder backup = new StringBuilder();
		StringBuilder CSV = new StringBuilder();
		CSV.append( CSV_HEADER + LINE_SEPARATOR );
		Date backupDate = new Date();
		
		// Backing up User Table.
		while( true ) {
			DataListCursorTuple<User> userListCursorTupe = 
					dataAccessor.getUserList( cursor, 1000 );
			List<User> userList = userListCursorTupe.getDataList();

			for( User user : userList ) {
				StringBuilder oneLine = new StringBuilder();
                oneLine.append( user.getEmail() );
                oneLine.append( CSV_SEPARATOR );
                oneLine.append( user.getFacebookId() );
                oneLine.append( CSV_SEPARATOR );
                oneLine.append( user.getFirstName() );
                oneLine.append( CSV_SEPARATOR );
                oneLine.append( user.getLastName() );
                oneLine.append( CSV_SEPARATOR );
                oneLine.append( user.getId() );
                
                CSV.append( oneLine.toString() + LINE_SEPARATOR );
				backup.append( new Gson().toJson( user ) + LINE_SEPARATOR );
			}
				
			count = count + userList.size();

			if( userList.size() < 1000 )
				break;
			else
				cursor = userListCursorTupe.getCursor();
		}
		
		BlobEntry userBlobEntry = blobAccessor.newBlob( "user/" + new SimpleDateFormat( "yyyy-MM-dd-HH:mm-z" ).format( backupDate ) + "-backup", null, "text/plain" );
		BlobEntry userCsvEntry = blobAccessor.newBlob( "user/" + new SimpleDateFormat( "yyyy-MM-dd-HH:mm-z" ).format( backupDate ) + "-backup-csv.csv", null, "text/plain" );
		
		userBlobEntry.setData( backup.toString().getBytes( Charset.forName( "UTF-8" ) ) );
		userCsvEntry.setData( CSV.toString().getBytes( Charset.forName( "UTF-8" ) ) );
		
		blobAccessor.createOrUpdateBlob( userBlobEntry );
		blobAccessor.createOrUpdateBlob( userCsvEntry );
		
		logger.log( Level.INFO, "Backed up " + count + " User Entities." );

		return new GenericResponse();
		
	}
	
}