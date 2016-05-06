package com.pratilipi.data;

import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.type.PratilipiGoogleAnalyticsDoc;

public interface DocAccessor {

	PratilipiGoogleAnalyticsDoc getPratilipiGoogleAnalyticsDoc( Long pratilipiId )
			throws UnexpectedServerException;

	void save( Long pratilipiId, PratilipiGoogleAnalyticsDoc gaDoc )
			throws UnexpectedServerException;
	
}
