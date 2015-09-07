package com.pratilipi.api.pratilipi;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.pratilipi.shared.GetPratilipiCategoryRequest;
import com.pratilipi.api.pratilipi.shared.GetPratilipiListRequest;
import com.pratilipi.api.pratilipi.shared.GetPratilipiListResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;


@SuppressWarnings("serial")
@Bind( uri = "/pratilipi/category" )
public class PratilipiCategoryApi extends GenericApi {
	
	private static final String dataFilePrefix = "data/";
	private static final String CATEGORY_LIST = "CategoryList";
	
	public String getContents( String fileName ) {
		StringBuilder stringBuilder = new StringBuilder();
		try {
		File file = new File( getClass().getResource( dataFilePrefix + fileName ).toURI() );
		LineIterator it = FileUtils.lineIterator( file, "UTF-8" );
		while( it.hasNext() ) 
			stringBuilder.append( it.nextLine() );
		LineIterator.closeQuietly( it );
		} catch( Exception e ) {
			e.printStackTrace();
		}
		return stringBuilder.toString();
	}
	
	@Get
	public GetPratilipiListResponse getPratilipiCategory( GetPratilipiCategoryRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		Gson gson = new Gson();
		String fileName = CATEGORY_LIST + "." + request.getLanguage().getCode();
		String category = request.getCategory().toString();
		String jsonString = getContents( fileName );
		JsonElement responseJson = new Gson().fromJson( jsonString, JsonElement.class ).getAsJsonObject().get( category );
		GetPratilipiListRequest  getPratilipiListRequest = gson.fromJson( responseJson, GetPratilipiListRequest.class );
		PratilipiListApi pratilipiListApi = new PratilipiListApi();
		return pratilipiListApi.getPratilipiList( getPratilipiListRequest );	
		
	}

}
