package com.pratilipi.api.impl.notification;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.impl.pratilipi.PratilipiApi;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.type.NotificationState;
import com.pratilipi.common.type.NotificationType;
import com.pratilipi.data.client.NotificationData;

@SuppressWarnings("serial")
@Bind( uri = "/notification" )
public class NotificationApi extends GenericApi {

	@SuppressWarnings("unused")
	public static class Response extends GenericResponse { 

		private Long notificationId;

		private String message;
		private String sourceUrl;
		private String displayImageUrl;

		private NotificationState state;
		
		private NotificationType notificationType;
		private String androidNotificationType;

		private PratilipiApi.Response pratilipiData;

		private Long lastUpdatedMillis;


		private Response() {}

		public Response( NotificationData notification, Class<? extends GenericApi> clazz ) {
			this.notificationId = notification.getId();
			this.message = notification.getMessage();
			this.sourceUrl = notification.getSourceUrl();
			this.displayImageUrl = notification.getDisplayImageUrl();
			this.state = notification.getState();
			this.notificationType = notification.getNotificationType();
			this.androidNotificationType = notification.getNotificationType() != null ? 
					notification.getNotificationType().getAndroidHandler() : null;
			this.lastUpdatedMillis = notification.getLastUpdatedDate().getTime();
		}

		
		public Long getNotificationId() {
			return notificationId;
		}


		public String getMessage() {
			return message;
		}

		public String getSourceUrl() {
			return sourceUrl;
		}

		public String getDisplayImageUrl() {
			return displayImageUrl;
		}


		public NotificationState getState() {
			return state;
		}

		public PratilipiApi.Response getPratilipiData() {
			return pratilipiData;
		}


		public Long getLastUpdatedMillis() {
			return lastUpdatedMillis;
		}

	}
	
}