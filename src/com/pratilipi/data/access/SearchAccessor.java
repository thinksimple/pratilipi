package com.pratilipi.data.access;

import java.util.List;

import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.pratilipi.service.shared.data.PratilipiData;

public interface SearchAccessor extends com.claymus.data.access.SearchAccessor {
	
	void indexPratilipiData( PratilipiData pratilipiData ) throws UnexpectedServerException;

	void indexPratilipiDataList( List<PratilipiData> pratilipiDataList ) throws UnexpectedServerException;

	void deletePratilipiDataIndex( Long pratilipiId );

}