package com.pratilipi.api.author;

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
	
	private static final String CSV_HEADER = "Email,FirstName,FirstName_en,LastName,LastName_en,PenName,PenName_en,ContentsPublished,AuthorId,Language,UserId";
	private static final String CSV_SEPARATOR = ",";
	private static final String LINE_SEPARATOR = "\n";
	
	@Get
	public GenericResponse get( GenericRequest request ) throws UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
		
		AuthorFilter authorFilter = new AuthorFilter();
		String cursor = null;
		int count = 0;
		StringBuilder backup = new StringBuilder();
		StringBuilder CSV = new StringBuilder();
		CSV.append( CSV_HEADER + LINE_SEPARATOR );
		Date backupDate = new Date();
		
		// Backing up Author Table.
		while( true ) {
			DataListCursorTuple<Author> authorListCursorTupe =
					dataAccessor.getAuthorList( authorFilter, cursor, 1000 );
			List<Author> authorList = authorListCursorTupe.getDataList();

			for( Author author : authorList ) {
				StringBuilder oneLine = new StringBuilder();
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
                
                CSV.append( oneLine.toString() + LINE_SEPARATOR );
                backup.append( new Gson().toJson( author ) + LINE_SEPARATOR );
			}
			
			count = count + authorList.size();

			if( authorList.size() < 1000 )
				break;
			else
				cursor = authorListCursorTupe.getCursor();
		}
		
		BlobEntry authorBlobEntry = blobAccessor.newBlob( "author/" + new SimpleDateFormat( "yyyy-MM-dd-HH:mm-z" ).format( backupDate ) + "-backup", null, "text/plain" );
		BlobEntry authorCsvEntry = blobAccessor.newBlob( "author/" + new SimpleDateFormat( "yyyy-MM-dd-HH:mm-z" ).format( backupDate ) + "-backup-csv.csv", null, "text/plain" );
		
		authorBlobEntry.setData( backup.toString().getBytes( Charset.forName( "UTF-8" ) ) );
		authorCsvEntry.setData( CSV.toString().getBytes( Charset.forName( "UTF-8" ) ) );
		
		blobAccessor.createOrUpdateBlob( authorBlobEntry );
		blobAccessor.createOrUpdateBlob( authorCsvEntry );
		
		logger.log( Level.INFO, "Backed up " + count + " Author Entities." );

		return new GenericResponse();
		
	}
	
}