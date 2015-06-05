package com.pratilipi.data.access;

import java.util.List;

import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataListCursorTuple;
import com.pratilipi.commons.shared.PratilipiFilter;
import com.pratilipi.data.transfer.shared.AuthorData;
import com.pratilipi.data.transfer.shared.PratilipiData;

public interface SearchAccessor extends com.claymus.data.access.SearchAccessor {
	
	DataListCursorTuple<Long> searchPratilipi( PratilipiFilter pratilipiFilter, String cursorStr, Integer resultCount );
	
	DataListCursorTuple<Long> searchQuery( String query, String cursorStr, Integer resultCount );
		
	void indexPratilipiData( PratilipiData pratilipiData ) throws UnexpectedServerException;

	void indexPratilipiDataList( List<PratilipiData> pratilipiDataList ) throws UnexpectedServerException;

	void indexAuthorData( AuthorData authorData ) throws UnexpectedServerException;

	void deletePratilipiDataIndex( Long pratilipiId );

}