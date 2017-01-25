package com.pratilipi.data;

import java.util.Date;
import java.util.Map;

import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.type.UserPreferenceRtdb;

public interface RtdbAccessor {

	// PREFERENCE Table
	UserPreferenceRtdb getUserPreference( Long userId ) throws UnexpectedServerException;

	Map<Long, UserPreferenceRtdb> getUserPreferences( Date lastUpdated, String operator ) throws UnexpectedServerException;

	Map<Long, UserPreferenceRtdb> getUserPreferences( String androidVersion, String operator ) throws UnexpectedServerException;

}
