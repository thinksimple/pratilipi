package com.pratilipi.api.impl.init;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.impl.init.shared.GetOfyRequest;
import com.pratilipi.api.impl.init.shared.GetOfyResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.util.GoogleAnalyticsApi;
import com.pratilipi.data.BlobAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.BlobEntry;

@SuppressWarnings("serial")
@Bind( uri = "/ofy" )
public class OfyTestApi extends GenericApi {
	
	private static final Logger logger =
			Logger.getLogger( OfyTestApi.class.getName() );

	@Get
	public GetOfyResponse get( GetOfyRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		String date = request.getDate();
		Gson gson = new Gson();
		
		Map<String, Integer> oldPageViewsMap, newPageViewsMap, diffPageViewsMap;
		
		
		String fileName = "google-analytics/page-views/" + date;
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
		BlobEntry blobEntry = blobAccessor.getBlob( fileName );
		if( blobEntry == null ) {
			blobEntry = blobAccessor.newBlob( fileName );
			oldPageViewsMap = new HashMap<>();
		} else {
			oldPageViewsMap = gson.fromJson(
					new String( blobEntry.getData(), Charset.forName( "UTF-8" ) ),
					new TypeToken<Map<String, Integer>>(){}.getType() );
		}
		
		newPageViewsMap = GoogleAnalyticsApi.getPageViews( request.getDate() );
		
		diffPageViewsMap = new HashMap<>();
		for( Entry<String, Integer> entry : newPageViewsMap.entrySet() ) {
			if( oldPageViewsMap.get( entry.getKey() ) == null ||
					oldPageViewsMap.get( entry.getKey() ).equals( entry.getValue() ) )
				diffPageViewsMap.put( entry.getKey(), entry.getValue() );
		}
		
		try {
			blobEntry.setData( gson.toJson( newPageViewsMap ).getBytes( "UTF-8" ) );
		} catch( UnsupportedEncodingException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new UnexpectedServerException();
		}
		
		return new GetOfyResponse( diffPageViewsMap.size() );
		
	}
	
}
