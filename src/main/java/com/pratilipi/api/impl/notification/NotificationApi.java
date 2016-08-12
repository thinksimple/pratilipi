package com.pratilipi.api.impl.notification;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.type.NotificationState;
import com.pratilipi.data.client.NotificationData;

@SuppressWarnings("serial")
@Bind( uri = "/notification" )
public class NotificationApi extends GenericApi {

	public static class Response extends GenericResponse { 

		private Long notificationId;

		private String message;

		private String sourceUrl;

		private NotificationState state;

		private Long lastUpdatedMillis;


		@SuppressWarnings("unused")
		private Response() {}

		public Response( NotificationData notification, Class<? extends GenericApi> clazz ) {
			this.notificationId = notification.getId();
			this.message = notification.getMessage();
			this.sourceUrl = notification.getSourceUrl();
			this.state = notification.getState();
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

		public NotificationState getState() {
			return state;
		}

		public Long getLastUpdatedMillis() {
			return lastUpdatedMillis;
		}

	}
	
}