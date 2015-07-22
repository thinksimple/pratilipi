package com.pratilipi.api.pratilipi;

import java.io.IOException;
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
	
	@Get
	public GenericResponse getBackup( GenericRequest request ) throws IOException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		String cursor = null;
		int count = 0;
		StringBuilder backup = new StringBuilder();

		Date backupDate = new Date();
		while( true ) {
			DataListCursorTuple<Pratilipi> pratilipiListCursorTupe =
					dataAccessor.getPratilipiList( pratilipiFilter, cursor, 1000 );
			List<Pratilipi> pratilipiList = pratilipiListCursorTupe.getDataList();

			for( Pratilipi pratilipi : pratilipiList ) {
				if( pratilipi.getKeywords() != null )
					pratilipi.setKeywords( pratilipi.getKeywords().split( "\\s+" ).length + "" );
				else
					pratilipi.setKeywords( "0" );
				backup.append( new Gson().toJson( pratilipi ) + '\n' );
			}
				
			count = count + pratilipiList.size();

			if( pratilipiList.size() < 1000 )
				break;
			else
				cursor = pratilipiListCursorTupe.getCursor();
		}
		
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
		BlobEntry blobEntry = blobAccessor.newBlob( "pratilipi/pratilipi-" + new SimpleDateFormat( "yyyy-MM-dd-HH:mm" ).format( backupDate ) );
		blobEntry.setData( backup.toString().getBytes( Charset.forName( "UTF-8" ) ) );
		blobAccessor.createOrUpdateBlob( blobEntry );
		
		logger.log( Level.INFO, "Backed up " + count + " Pratilipi Entities." );

		return new GenericResponse();
		
	}
	
}