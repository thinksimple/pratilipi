package com.pratilipi.data;

import java.util.Map;

import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.util.PratilipiFilter;
import com.pratilipi.data.client.AuthorData;
import com.pratilipi.data.client.PratilipiData;

public interface SearchAccessor {
	
	DataListCursorTuple<Long> searchPratilipi( PratilipiFilter pratilipiFilter, String cursorStr, Integer resultCount );
	
	DataListCursorTuple<Long> searchPratilipi( String searchQuery, PratilipiFilter pratilipiFilter, String cursorStr, Integer resultCount );

	void indexPratilipiData( PratilipiData pratilipiData, String keywords ) throws UnexpectedServerException;

	void indexPratilipiDataList( Map< PratilipiData, String > pratilipiDataList ) throws UnexpectedServerException;

	void deletePratilipiDataIndex( Long pratilipiId );
	
	void indexAuthorData( AuthorData authorData ) throws UnexpectedServerException;
	
}