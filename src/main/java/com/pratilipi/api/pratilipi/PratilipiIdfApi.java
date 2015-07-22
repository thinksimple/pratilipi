package com.pratilipi.api.pratilipi;

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
@Bind( uri = "/pratilipi/idf" )
public class PratilipiIdfApi extends GenericApi {

	private static final Logger logger =
			Logger.getLogger( PratilipiIdfApi.class.getName() );
	
	@Get
	public GenericResponse getInit( GenericRequest request ) throws IOException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		String cursor = null;
		final HashMap<String, Integer> keywordFrequencyMap = new HashMap<>();
		
		Date idfGenerationDate = new Date();
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
		TreeMap<String, Integer> sortedKeywordFrequencyMap = new TreeMap<>( comparator );
		sortedKeywordFrequencyMap.putAll( keywordFrequencyMap );
		
		// Transform sorted map to csv string
		StringBuilder csv = new StringBuilder();
		for( Map.Entry<String, Integer> entry : sortedKeywordFrequencyMap.entrySet() ) {
			csv.append( entry.getKey() + "," );
			csv.append( entry.getValue().toString() + "," );
			csv.append( "\n" );
		}

		// Persist csv string in BlobStore
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
		BlobEntry blobEntry = blobAccessor.newBlob( "pratilipi-keywords/" + new SimpleDateFormat( "yyyy-MM-dd-HH:mm" ).format( idfGenerationDate ) + ".csv" );
		blobEntry.setData( csv.toString().getBytes( Charset.forName( "UTF-8" ) ) );
		blobAccessor.createOrUpdateBlob( blobEntry );
		
		logger.log( Level.INFO, "Generated IDF with " + keywordFrequencyMap.size() + " keywords." );

		return new GenericResponse();
	}
}