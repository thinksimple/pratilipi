package com.pratilipi.api.impl.pratilipi;

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
import com.pratilipi.api.impl.author.shared.GetPratilipiBackupRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.util.GsonIstDateAdapter;
import com.pratilipi.common.util.PratilipiFilter;
import com.pratilipi.data.BlobAccessor;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.type.BlobEntry;
import com.pratilipi.data.type.Pratilipi;

@SuppressWarnings("serial")
@Bind( uri = "/pratilipi/backup" )
public class PratilipiBackupApi extends GenericApi {

	private static final Logger logger =
			Logger.getLogger( PratilipiBackupApi.class.getName() );
	
	private static final String CSV_HEADER = "PratilipiId,AuthorId,"
			+ "Title,TitleEN,Language,HasSummary,"
			+ "Type,ContentType,State,HasCover,ListingDate";
	private static final String CSV_SEPARATOR = ",";
	private static final String LINE_SEPARATOR = "\n";


	@Get
	public GenericResponse get( GetPratilipiBackupRequest request ) throws UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessorBackup();
		
		Date backupDate = new Date();

		DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
		DateFormat csvDateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm" );
		DateFormat dateTimeFormat = new SimpleDateFormat( "yyyy-MM-dd-HH:mm-z" );
		dateFormat.setTimeZone( TimeZone.getTimeZone( "Asia/Kolkata" ) );
		csvDateFormat.setTimeZone( TimeZone.getTimeZone( "Asia/Kolkata" ) );
		dateTimeFormat.setTimeZone( TimeZone.getTimeZone( "Asia/Kolkata" ) );

		StringBuilder backup = new StringBuilder();
		StringBuilder csv = new StringBuilder( CSV_HEADER + LINE_SEPARATOR );

		int count = 0;
		String cursor = null;
		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		Gson gson = new GsonBuilder().registerTypeAdapter( Date.class, new GsonIstDateAdapter() ).create();
		
		while( true ) {
			DataListCursorTuple<Pratilipi> pratilipiListCursorTupe = dataAccessor.getPratilipiList( pratilipiFilter, cursor, 1000 );
			List<Pratilipi> pratilipiList = pratilipiListCursorTupe.getDataList();

			for( Pratilipi pratilipi : pratilipiList ) {
                backup.append( gson.toJson( pratilipi ) + LINE_SEPARATOR );

				if( request.generateCsv() )
					csv.append( "'" + pratilipi.getId() )
							.append( CSV_SEPARATOR ).append( pratilipi.getAuthorId() == null ? "" : "'" + pratilipi.getAuthorId() )
							.append( CSV_SEPARATOR ).append( pratilipi.getTitle()	 == null ? "" : pratilipi.getTitle() )
							.append( CSV_SEPARATOR ).append( pratilipi.getTitleEn()	 == null ? "" : pratilipi.getTitleEn() )
							.append( CSV_SEPARATOR ).append( pratilipi.getLanguage() )
							.append( CSV_SEPARATOR ).append( pratilipi.getSummary() != null && pratilipi.getSummary().trim().length() != 0 )
							.append( CSV_SEPARATOR ).append( pratilipi.getType() )
							.append( CSV_SEPARATOR ).append( pratilipi.getContentType() )
							.append( CSV_SEPARATOR ).append( pratilipi.getState() )
							.append( CSV_SEPARATOR ).append( pratilipi.hasCustomCover() )
							.append( CSV_SEPARATOR ).append( csvDateFormat.format( pratilipi.getListingDate() ) )
							.append( LINE_SEPARATOR );
				
			}
			
			count = count + pratilipiList.size();

			if( pratilipiList.size() < 1000 )
				break;
			else
				cursor = pratilipiListCursorTupe.getCursor();
		}
		

		String fileName = "datastore.pratilipi/"
				+ dateFormat.format( backupDate ) + "/"
				+ "pratilipi-" + dateTimeFormat.format( backupDate );

		BlobEntry pratilipiBackupEntry = blobAccessor.newBlob(
				fileName,
				backup.toString().getBytes( Charset.forName( "UTF-8" ) ),
				"text/plain" );
		
		blobAccessor.createOrUpdateBlob( pratilipiBackupEntry );
		
		
		if( request.generateCsv() ) {
			BlobEntry authorCsvEntry = blobAccessor.newBlob(
					"datastore/pratilipi.csv",
					csv.toString().getBytes( Charset.forName( "UTF-8" ) ),
					"text/csv" );
			blobAccessor.createOrUpdateBlob( authorCsvEntry );
		}
		

		logger.log( Level.INFO, "Backed up " + count + " Pratilipi Entities." );

		return new GenericResponse();
		
	}
	
}