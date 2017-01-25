package com.pratilipi.data.type;

import java.util.Date;

import com.pratilipi.common.type.EmailFrequency;
import com.pratilipi.common.type.NotificationType;


public interface UserPreferenceRtdb {

	EmailFrequency getEmailFrequency();

	void setEmailFrequency( EmailFrequency emailFrequency );

	boolean isNotificationSubscribed( NotificationType notificationType );

	void setNotificationSubscription( NotificationType notificationType, boolean subscribed );

	Date getLastUpdated();

	void setLastUpdated( Date lastUpdated );

	Integer getAndroidVersionCode();

	void setAndroidVersionCode( Integer androidVersion );

}
