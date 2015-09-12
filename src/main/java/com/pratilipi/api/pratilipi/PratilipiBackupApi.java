package com.pratilipi.api.pratilipi;

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
import com.pratilipi.common.util.AuthorFilter;
import com.pratilipi.common.util.PratilipiFilter;
import com.pratilipi.data.BlobAccessor;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.BlobEntry;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.User;

@SuppressWarnings("serial")
@Bind( uri = "/pratilipi/backup" )
public class PratilipiBackupApi extends GenericApi {

	private static final Logger logger =
			Logger.getLogger( PratilipiBackupApi.class.getName() );
	
	private static final String CSV_SEPARATOR = ",";
	
	@Get
	public GenericResponse get( GenericRequest request ) throws UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		AuthorFilter authorFilter = new AuthorFilter();
		String cursor;
		int pratilipiCount = 0, authorCount = 0, userCount = 0;
		StringBuilder pratilipiBackup = new StringBuilder(), authorBackup = new StringBuilder(), userBackup = new StringBuilder();
		StringBuilder pratilipiCSV = new StringBuilder(), authorCSV = new StringBuilder(), userCSV = new StringBuilder();
		Date backupDate = new Date();
		
		cursor = null;
		while( true ) {
			DataListCursorTuple<Pratilipi> pratilipiListCursorTupe =
					dataAccessor.getPratilipiList( pratilipiFilter, cursor, 1000 );
			List<Pratilipi> pratilipiList = pratilipiListCursorTupe.getDataList();

			for( Pratilipi pratilipi : pratilipiList )  {
				StringBuffer oneLine = new StringBuffer();
                oneLine.append( pratilipi.getIndex() );
                oneLine.append( CSV_SEPARATOR );
                oneLine.append( pratilipi.getSummary() );
                oneLine.append( CSV_SEPARATOR );
                oneLine.append( pratilipi.getTitle() );
                oneLine.append( CSV_SEPARATOR );
                oneLine.append( pratilipi.getTitleEn() );
                oneLine.append( CSV_SEPARATOR );
                oneLine.append( pratilipi.getAuthorId() );
                oneLine.append( CSV_SEPARATOR );
                oneLine.append( pratilipi.getContentType().toString() );
                oneLine.append( CSV_SEPARATOR );
                oneLine.append( pratilipi.getFbLikeShareCount().toString() );
                oneLine.append( CSV_SEPARATOR );
                oneLine.append( pratilipi.getId().toString() );
                oneLine.append( CSV_SEPARATOR );
                oneLine.append( pratilipi.getLanguage().getName() );
                oneLine.append( CSV_SEPARATOR );
                oneLine.append( pratilipi.getPageCount().toString() );
                oneLine.append( CSV_SEPARATOR );
                oneLine.append( pratilipi.getPublicationYear().toString() );
                oneLine.append( CSV_SEPARATOR );
                oneLine.append( pratilipi.getRatingCount().toString() );
                oneLine.append( CSV_SEPARATOR );
                oneLine.append( pratilipi.getReadCount().toString() );
                oneLine.append( CSV_SEPARATOR );
                oneLine.append( pratilipi.getReviewCount().toString() );
                oneLine.append( CSV_SEPARATOR );
                oneLine.append( pratilipi.getState().toString() );
                oneLine.append( CSV_SEPARATOR );
                oneLine.append( pratilipi.getType().toString() );
                oneLine.append( CSV_SEPARATOR );
                oneLine.append( pratilipi.getWordCount().toString() );
                oneLine.append( CSV_SEPARATOR );
                
                pratilipiCSV.append( oneLine.toString() );
                pratilipiBackup.append( new Gson().toJson( pratilipi ) + '\n' );
			}
				
				
			pratilipiCount = pratilipiCount + pratilipiList.size();

			if( pratilipiList.size() < 1000 )
				break;
			else
				cursor = pratilipiListCursorTupe.getCursor();
		}
		
		cursor = null;
		while( true ) {
			DataListCursorTuple<Author> authorListCursorTupe =
					dataAccessor.getAuthorList( authorFilter, cursor, 1000 );
			List<Author> authorList = authorListCursorTupe.getDataList();

			for( Author author : authorList ) {
				StringBuffer oneLine = new StringBuffer();
                oneLine.append( author.getEmail() );
                oneLine.append( CSV_SEPARATOR );
                oneLine.append( author.getFirstName() );
                oneLine.append( CSV_SEPARATOR );
                oneLine.append( author.getFirstNameEn() );
                oneLine.append( CSV_SEPARATOR );
                oneLine.append( author.getLastName() );
                oneLine.append( CSV_SEPARATOR );
                oneLine.append( author.getLastNameEn() );
                oneLine.append( CSV_SEPARATOR );
                oneLine.append( author.getPenName() );
                oneLine.append( CSV_SEPARATOR );
                oneLine.append( author.getPenNameEn() );
                oneLine.append( CSV_SEPARATOR );
                oneLine.append( author.getContentPublished().toString() );
                oneLine.append( CSV_SEPARATOR );
                oneLine.append( author.getId().toString() );
                oneLine.append( CSV_SEPARATOR );
                oneLine.append( author.getLanguage().toString() );
                oneLine.append( CSV_SEPARATOR );
                oneLine.append( author.getUserId().toString() );
                oneLine.append( CSV_SEPARATOR );
                
                authorCSV.append( oneLine.toString() );
                authorBackup.append( new Gson().toJson( author ) + '\n' );
			}
				
				
			authorCount = authorCount + authorList.size();

			if( authorList.size() < 1000 )
				break;
			else
				cursor = authorListCursorTupe.getCursor();
		}
		
		cursor = null;
		while( true ) {
			DataListCursorTuple<User> userListCursorTupe = 
					dataAccessor.getUserList( cursor, 1000 );
			List<User> userList = userListCursorTupe.getDataList();

			for( User user : userList ) {
				StringBuffer oneLine = new StringBuffer();
                oneLine.append( user.getEmail() );
                oneLine.append( CSV_SEPARATOR );
                oneLine.append( user.getFacebookId() );
                oneLine.append( CSV_SEPARATOR );
                oneLine.append( user.getFirstName() );
                oneLine.append( CSV_SEPARATOR );
                oneLine.append( user.getLastName() );
                oneLine.append( CSV_SEPARATOR );
                oneLine.append( user.getNickName() );
                oneLine.append( CSV_SEPARATOR );
                oneLine.append( user.getId() );
                oneLine.append( CSV_SEPARATOR );
                
                userCSV.append( oneLine.toString() );
				userBackup.append( new Gson().toJson( user ) + '\n' );
			}
				
				
			userCount = userCount + userList.size();

			if( userList.size() < 1000 )
				break;
			else
				cursor = userListCursorTupe.getCursor();
		}
		
		
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
		
		BlobEntry pratilipiBlobEntry = blobAccessor.newBlob( "pratilipi/" + new SimpleDateFormat( "yyyy-MM-dd-HH:mm-z" ).format( backupDate ) + "-backup", null, "text/plain" );
		BlobEntry authorBlobEntry = blobAccessor.newBlob( "author/" + new SimpleDateFormat( "yyyy-MM-dd-HH:mm-z" ).format( backupDate ) + "-backup", null, "text/plain" );
		BlobEntry userBlobEntry = blobAccessor.newBlob( "user" + new SimpleDateFormat( "yyyy-MM-dd-HH:mm-z" ).format( backupDate ) + "-backup", null, "text/plain" );
		
		pratilipiBlobEntry.setData( pratilipiBackup.toString().getBytes( Charset.forName( "UTF-8" ) ) );
		authorBlobEntry.setData( authorBackup.toString().getBytes( Charset.forName( "UTF-8" ) ) );
		userBlobEntry.setData( userBackup.toString().getBytes( Charset.forName( "UTF-8" ) ) );
		
		blobAccessor.createOrUpdateBlob( pratilipiBlobEntry );
		blobAccessor.createOrUpdateBlob( authorBlobEntry );
		blobAccessor.createOrUpdateBlob( userBlobEntry );
		
		
		BlobEntry pratilipiCsvEntry = blobAccessor.newBlob( "pratilipi/" + new SimpleDateFormat( "yyyy-MM-dd-HH:mm-z" ).format( backupDate ) + "-backup-csv.csv", null, "text/plain" );
		BlobEntry authorCsvEntry = blobAccessor.newBlob( "author/" + new SimpleDateFormat( "yyyy-MM-dd-HH:mm-z" ).format( backupDate ) + "-backup-csv.csv", null, "text/plain" );
		BlobEntry userCsvEntry = blobAccessor.newBlob( "user/" + new SimpleDateFormat( "yyyy-MM-dd-HH:mm-z" ).format( backupDate ) + "-backup-csv.csv", null, "text/plain" );
		
		pratilipiCsvEntry.setData( pratilipiCSV.toString().getBytes( Charset.forName( "UTF-8" ) ) );
		authorCsvEntry.setData( authorCSV.toString().getBytes( Charset.forName( "UTF-8" ) ) );
		userCsvEntry.setData( userCSV.toString().getBytes( Charset.forName( "UTF-8" ) ) );
		
		blobAccessor.createOrUpdateBlob( pratilipiCsvEntry );
		blobAccessor.createOrUpdateBlob( authorCsvEntry );
		blobAccessor.createOrUpdateBlob( userCsvEntry );
		
		
		logger.log( Level.INFO, "Backed up " + pratilipiCount + " Pratilipi Entities." );
		logger.log( Level.INFO, "Backed up " + authorCount + " Author Entities." );
		logger.log( Level.INFO, "Backed up " + userCount + " User Entities." );

		return new GenericResponse();
		
	}
	
}