package com.pratilipi.api.init;

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
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.type.AppProperty;
import com.pratilipi.data.type.BlobEntry;
import com.pratilipi.data.type.Pratilipi;

@SuppressWarnings("serial")
@Bind( uri = "/init" )
public class InitApi extends GenericApi {

	private static final Logger logger =
			Logger.getLogger( InitApi.class.getName() );
	
	
	@Get
	public GenericResponse getInit( GenericRequest request ) throws IOException {
		
		// Cleaning up ACCESS_TOKEN table

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		AppProperty appProperty = dataAccessor.getAppProperty( "Init.AccessToken.Cursor" );
		if( appProperty == null )
			appProperty = dataAccessor.newAppProperty( "Init.AccessToken.Cursor" );

		DataListCursorTuple<AccessToken> accessTokenListCursorTuple =
				dataAccessor.getAccessTokenList( (String) appProperty.getValue(), 1000 );

		Date maxDate = new Date( new Date().getTime() - 30 * 24 * 60 * 60 * 1000 );
		int cleared = 0;
		
		List<AccessToken> accessTokenList = accessTokenListCursorTuple.getDataList();
		for( AccessToken accessToken : accessTokenList ) {
			if( accessToken.getCreationDate().after( maxDate ) )
				new GenericResponse();
			
			if( accessToken.getUserId() != null && accessToken.getUserId() != 0L )
				continue;
			
			if( dataAccessor.getAuditLogList( accessToken.getId(), null, 1 ).getDataList().size() != 0 )
				continue;
			
			dataAccessor.deleteAccessToken( accessToken );

			cleared++;
		}
		
		appProperty.setValue( accessTokenListCursorTuple.getCursor() );
		dataAccessor.createOrUpdateAppProperty( appProperty );

		logger.log( Level.INFO, "Cleared " + cleared + " access token entities." );
		
		
		// Backing up PRATILIPI Table
		appProperty = dataAccessor.getAppProperty( AppProperty.DATASTORE_PRATILIPI_LAST_BACKUP );
		if( appProperty == null )
			appProperty = dataAccessor.newAppProperty( AppProperty.DATASTORE_PRATILIPI_LAST_BACKUP );

		Date currDate = new Date();
		Date nextBackup = appProperty.getValue() == null
				? new Date( currDate.getTime() - 1 )
				: new Date( ( (Date) appProperty.getValue() ).getTime() + 60 * 60 * 1000 ); // Last backup time + 1Hr

		if( currDate.after( nextBackup ) ) {
			PratilipiFilter pratilipiFilter = new PratilipiFilter();
			String cursor = null;
			int count = 0;
			StringBuilder backup = new StringBuilder();

			while( true ) {
				DataListCursorTuple<Pratilipi> pratilipiListCursorTupe =
						dataAccessor.getPratilipiList( pratilipiFilter, cursor, 1000 );
				List<Pratilipi> pratilipiList = pratilipiListCursorTupe.getDataList();

				for( Pratilipi pratilipi : pratilipiList )
					backup.append( new Gson().toJson( pratilipi ) + '\n' );
				
				count = count + pratilipiList.size();

				if( pratilipiList.size() < 1000 )
					break;
				else
					cursor = pratilipiListCursorTupe.getCursor();
			}
			
			BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
			BlobEntry blobEntry = blobAccessor.newBlob( "pratilipi-" + new SimpleDateFormat( "yyyyMMddHHmm" ).format( currDate ) );
			blobEntry.setData( backup.toString().getBytes( Charset.forName( "UTF-8" ) ) );
			blobAccessor.createOrUpdateBlob( blobEntry );
			
			appProperty.setValue( currDate );
			dataAccessor.createOrUpdateAppProperty( appProperty );

			logger.log( Level.INFO, "Backed up " + count + " Pratilipi Entities." );
		}
		
		return new GenericResponse();
	}
	
}