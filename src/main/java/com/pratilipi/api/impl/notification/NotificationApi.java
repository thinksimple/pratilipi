package com.pratilipi.api.impl.notification;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.data.client.NotificationData;

@SuppressWarnings("serial")
@Bind( uri = "/notification" )
public class NotificationApi extends GenericApi {

	public static class Response extends GenericResponse { 

		private Long notificationId;
		
		private String message;
		
		private String sourceUrl;
		
		
		@SuppressWarnings("unused")
		private Response() {}
		
		public Response( NotificationData notification, Class<? extends GenericApi> clazz ) {
			this.notificationId = notification.getId();
			this.message = notification.getMessage();
			this.sourceUrl = notification.getSourceUrl();
		}

	}
	
}