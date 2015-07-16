package com.pratilipi.api.init;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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
import com.pratilipi.data.type.AppProperty;
import com.pratilipi.data.type.BlobEntry;
import com.pratilipi.data.type.Pratilipi;


@SuppressWarnings("serial")
@Bind( uri = "/init" )
public class InitApi extends GenericApi {

	private static final Logger logger =
			Logger.getLogger( InitApi.class.getName() );
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	private static final String FILE_HEADER = "words,frequency";
	
	@Get
	public GenericResponse getInit( GenericRequest request ) throws IOException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		// Backing up PRATILIPI Table
		AppProperty appProperty = dataAccessor.getAppProperty( AppProperty.DATASTORE_PRATILIPI_LAST_BACKUP );
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
			BlobEntry blobEntry = blobAccessor.newBlob( "pratilipi/pratilipi-" + new SimpleDateFormat( "yyyyMMddHHmm" ).format( currDate ) );
			blobEntry.setData( backup.toString().getBytes( Charset.forName( "UTF-8" ) ) );
			blobAccessor.createOrUpdateBlob( blobEntry );
			
			appProperty.setValue( currDate );
			dataAccessor.createOrUpdateAppProperty( appProperty );

			logger.log( Level.INFO, "Backed up " + count + " Pratilipi Entities." );
		}

		
		// Update the Inverse frequency table.
		appProperty = dataAccessor.getAppProperty( AppProperty.DATASTORE_PRATILIPI_IDF_LAST_UPDATE );
		if( appProperty == null )
			appProperty = dataAccessor.newAppProperty( AppProperty.DATASTORE_PRATILIPI_IDF_LAST_UPDATE );
		
		currDate = new Date();
		Date nextUpdate = appProperty.getValue() == null
				? new Date( currDate.getTime() - 1 )
				: new Date( ( (Date) appProperty.getValue() ).getTime() + 60 * 60 * 1000 * 6 ); // Last update time + 6 Hrs

		if( currDate.after( nextUpdate ) ) {
			PratilipiFilter pratilipiFilter = new PratilipiFilter();
			String cursor = null;
			int count = 0;
			
			final HashMap< String, Integer > keywordFrequencyMap = new HashMap< String, Integer >();
			
			while( true ) {
				DataListCursorTuple<Pratilipi> pratilipiListCursorTupe =
						dataAccessor.getPratilipiList( pratilipiFilter, cursor, 1000 );
				List<Pratilipi> pratilipiList = pratilipiListCursorTupe.getDataList();
				
				// Populate Keyword-Frequency map.
				for( Pratilipi pratilipi : pratilipiList ) {
					String keywords = pratilipi.getKeywords();
					if( keywords == null ) 
						continue;
					
					String[] words = keywords.split( "\\s+" );
					for( String word : words ) {
						if( keywordFrequencyMap.containsKey( word ) )
							keywordFrequencyMap.put( word, keywordFrequencyMap.get( word ) + 1 );
						else
							keywordFrequencyMap.put( word, 1 );
					}
				} 

				count = count + pratilipiList.size();

				if( pratilipiList.size() < 1000 )
					break;
				else
					cursor = pratilipiListCursorTupe.getCursor();
			}
			
			// Sort Keyword-Frequency map in descending order of frequency
			Comparator<String> comparator =  new Comparator<String>() {
				@Override
				public int compare( String a, String b ) {
					return keywordFrequencyMap.get( a ) >= keywordFrequencyMap.get( b ) ? -1 : 1; 
				}
				
			};
			
			TreeMap<String,Integer> sortedMap = new TreeMap<>( comparator );
			sortedMap.putAll( keywordFrequencyMap );
				
			// Transform sorted map to csv string
			StringBuilder csv = new StringBuilder();
			csv.append( FILE_HEADER );
			csv.append( NEW_LINE_SEPARATOR );
			for( Map.Entry<String, Integer> entry : sortedMap.entrySet() ) {
				csv.append( entry.getKey() );
				csv.append( COMMA_DELIMITER );
				csv.append( entry.getValue().toString() );
				csv.append( COMMA_DELIMITER );
				csv.append( NEW_LINE_SEPARATOR );
			}

			// Persist csv string in BlobStore
			BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
			BlobEntry blobEntry = blobAccessor.newBlob( "pratilipi-keywords/" + new SimpleDateFormat( "yyyyMMddHHmm" ).format( currDate ) + ".csv" );
			blobEntry.setData( csv.toString().getBytes( Charset.forName( "UTF-8" ) ) );
			blobAccessor.createOrUpdateBlob( blobEntry );
			
			appProperty.setValue( currDate );
			dataAccessor.createOrUpdateAppProperty( appProperty );

			logger.log( Level.INFO, "Generated IDF with " + count + " keywords." );
		}

		return new GenericResponse();
	}
}