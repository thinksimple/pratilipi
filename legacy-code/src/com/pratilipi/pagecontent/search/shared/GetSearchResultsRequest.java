package com.pratilipi.pagecontent.search.shared;

import com.claymus.api.annotation.Validate;
import com.claymus.api.shared.GenericRequest;

@SuppressWarnings( "serial" )
public class GetSearchResultsRequest extends GenericRequest {

	@Validate( required = true )
	private String query;
	
	private String cursor;
	
	private Integer resultCount;
	
	public String getQuery(){
		return query;
	}
	
	public String getCursor(){
		return cursor;
	}
	
	public Integer getResultCount(){
		return resultCount;
	}
}
