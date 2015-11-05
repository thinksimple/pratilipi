package com.pratilipi.api.author;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.author.shared.GetAuthorBackupRequest;
import com.pratilipi.api.init.gsonUTCdateAdapter;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.util.AuthorFilter;
import com.pratilipi.data.BlobAccessor;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.BlobEntry;

@SuppressWarnings("serial")
@Bind( uri = "/author/backup" )
public class AuthorBackupApi extends GenericApi {

	private static final Logger logger =
			Logger.getLogger( AuthorBackupApi.class.getName() );
	
	private static final String CSV_HEADER = "AuthorId,UserId,FirstName,LastName,PenName,FirstNameEN,LastNameEN,PenNameEN,Email,Language,ContentsPublished";
	private static final String CSV_SEPARATOR = ",";
	private static final String LINE_SEPARATOR = "\n";
	
	@Get
	public GenericResponse get( GetAuthorBackupRequest request ) throws UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
		
		Date backupDate = new Date();

		StringBuilder backup = new StringBuilder();
		StringBuilder csv = new StringBuilder( CSV_HEADER + LINE_SEPARATOR );
		
		int count = 0;
		AuthorFilter authorFilter = new AuthorFilter();
		String cursor = null;
		Gson gson = new GsonBuilder().registerTypeAdapter( Date.class, new gsonUTCdateAdapter() ).create();
		
		while( true ) {
			DataListCursorTuple<Author> authorListCursorTupe = dataAccessor.getAuthorList( authorFilter, cursor, 1000 );
			List<Author> authorList = authorListCursorTupe.getDataList();
			
			for( Author author : authorList ) {
				backup.append( gson.toJson( author ) + LINE_SEPARATOR );

				if( request.getCsv() ) 
				    csv.append( author.getId().toString() ).append( CSV_SEPARATOR )
	                		.append( author.getUserId() == null ? "" : author.getUserId().toString() ).append( CSV_SEPARATOR )
	                		.append( author.getFirstName() ).append( CSV_SEPARATOR )
	                		.append( author.getLastName() ).append( CSV_SEPARATOR )
	                		.append( author.getPenName() ).append( CSV_SEPARATOR )
	                		.append( author.getFirstNameEn() ).append( CSV_SEPARATOR )
	                		.append( author.getLastNameEn() ).append( CSV_SEPARATOR )
	                		.append( author.getPenNameEn() ).append( CSV_SEPARATOR )
	                		.append( author.getEmail() ).append( CSV_SEPARATOR )
	                		.append( author.getLanguage().toString() ).append( CSV_SEPARATOR )
	                		.append( author.getContentPublished().toString() ).append( LINE_SEPARATOR );
				
			}
			
			count = count + authorList.size();

			if( authorList.size() < 1000 )
				break;
			else
				cursor = authorListCursorTupe.getCursor();
		}

		
		String fileName = "author/"
				+ new SimpleDateFormat( "yyyy-MM-dd" ).format( backupDate ) + "/"
				+ "author-" + new SimpleDateFormat( "yyyy-MM-dd-HH:mm-z" ).format( backupDate );

		BlobEntry authorBackupEntry = blobAccessor.newBlob(
				fileName,
				backup.toString().getBytes( Charset.forName( "UTF-8" ) ),
				"text/plain" );
		
		blobAccessor.createOrUpdateBlob( authorBackupEntry );
		
		if( request.getCsv() ) {
			
			BlobEntry authorCsvEntry = blobAccessor.newBlob(
					fileName + ".csv",
					csv.toString().getBytes( Charset.forName( "UTF-8" ) ),
					"text/plain" );
			
			blobAccessor.createOrUpdateBlob( authorCsvEntry );
		}
		
		logger.log( Level.INFO, "Backed up " + count + " Author Entities." );

		return new GenericResponse();
		
	}
	
}