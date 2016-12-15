package com.pratilipi.data;

import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.type.UserPreferenceRtdb;

public interface RtdbAccessor {

	// PREFERENCE Table
	UserPreferenceRtdb getUserPreference( Long userId ) throws UnexpectedServerException;
	
}
