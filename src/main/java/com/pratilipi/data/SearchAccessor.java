package com.pratilipi.data;

import java.util.List;

import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.util.PratilipiFilter;
import com.pratilipi.data.client.PratilipiData;

public interface SearchAccessor {
	
	DataListCursorTuple<Long> searchPratilipi( PratilipiFilter pratilipiFilter, String cursorStr, Integer resultCount );
	
	void indexPratilipiData( PratilipiData pratilipiData ) throws UnexpectedServerException;

	void indexPratilipiDataList( List<PratilipiData> pratilipiDataList ) throws UnexpectedServerException;

	void deletePratilipiDataIndex( Long pratilipiId );

}