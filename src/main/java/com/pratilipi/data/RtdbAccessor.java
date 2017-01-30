package com.pratilipi.data;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.type.UserPreferenceRtdb;

public interface RtdbAccessor {

	// PREFERENCE Table
	
	UserPreferenceRtdb getUserPreference( Long userId ) throws UnexpectedServerException;

	Map<Long, UserPreferenceRtdb> getUserPreferences( Collection<Long> userIds ) throws UnexpectedServerException;

	Map<Long, UserPreferenceRtdb> getUserPreferences( Date minLastUpdated ) throws UnexpectedServerException;

	Map<Long, UserPreferenceRtdb> getUserPreferences( Integer maxAndroidVersionCode ) throws UnexpectedServerException;

	// TODO: Remove the function after POC
	Map<Long, UserPreferenceRtdb> getUserPreferences( String method, Integer limitTo ) throws UnexpectedServerException;

}
