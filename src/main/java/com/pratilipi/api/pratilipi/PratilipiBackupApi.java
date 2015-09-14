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
	
	private static final String LINE_SEPARATOR = "\n";
	
	@Get
	public GenericResponse get( GenericRequest request ) throws UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
		
		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		String cursor = null;
		int count = 0;
		StringBuilder backup = new StringBuilder();
		Date backupDate = new Date();
		
		// Backing up Pratilipi Table.
		while( true ) {
			DataListCursorTuple<Pratilipi> pratilipiListCursorTupe =
					dataAccessor.getPratilipiList( pratilipiFilter, cursor, 1000 );
			List<Pratilipi> pratilipiList = pratilipiListCursorTupe.getDataList();

			for( Pratilipi pratilipi : pratilipiList )  
                backup.append( new Gson().toJson( pratilipi ) + LINE_SEPARATOR );
			
			count = count + pratilipiList.size();

			if( pratilipiList.size() < 1000 )
				break;
			else
				cursor = pratilipiListCursorTupe.getCursor();
		}
		
		BlobEntry pratilipiBlobEntry = blobAccessor.newBlob( "pratilipi/" + new SimpleDateFormat( "yyyy-MM-dd-HH:mm-z" ).format( backupDate ) + "-backup", null, "text/plain" );
		pratilipiBlobEntry.setData( backup.toString().getBytes( Charset.forName( "UTF-8" ) ) );
		blobAccessor.createOrUpdateBlob( pratilipiBlobEntry );
		
		logger.log( Level.INFO, "Backed up " + count + " Pratilipi Entities." );

		return new GenericResponse();
		
	}
	
}