package com.pratilipi.api.impl.notification;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.type.NotificationState;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.client.NotificationData;
import com.pratilipi.data.type.Notification;
import com.pratilipi.filter.AccessTokenFilter;

@SuppressWarnings("serial")
@Bind( uri = "/notification" )
public class NotificationApi extends GenericApi {

	public static class PostRequest extends GenericRequest {

		private Long notificationId;
		private NotificationState state;

	}

	@SuppressWarnings("unused")
	public static class Response extends GenericResponse { 

		private Long notificationId;

		private String message;
		private String displayImageUrl;

		private NotificationState state;
		private String androidHandler;

		private Long sourceId;
		private String sourceUrl;
		private String sourceImageUrl;

		private Long lastUpdatedMillis;


		private Response() {}

		public Response( NotificationData notification, Class<? extends GenericApi> clazz ) {
			this.notificationId = notification.getId();
			this.message = notification.getMessage();
			this.sourceUrl = notification.getSourceUrl();
			this.displayImageUrl = notification.getDisplayImageUrl();
			this.state = notification.getState();
			this.lastUpdatedMillis = notification.getLastUpdatedDate().getTime();
			this.androidHandler = notification.getNotificationType() != null ? 
					notification.getNotificationType().getAndroidHandler() : null;
			if( notification.getPratilipiData() != null ) {
				this.sourceId = notification.getPratilipiData().getId();
				this.sourceImageUrl = notification.getPratilipiData().getCoverImageUrl();
			}
		}

		
		public Long getNotificationId() {
			return notificationId;
		}


		public String getMessage() {
			return message;
		}

		public String getDisplayImageUrl() {
			return displayImageUrl;
		}


		public NotificationState getState() {
			return state;
		}


		public String getSourceUrl() {
			return sourceUrl;
		}

		public String getSourceImageUrl() {
			return sourceImageUrl;
		}


		public Long getLastUpdatedMillis() {
			return lastUpdatedMillis;
		}

	}


	@Post
	public Response post( PostRequest request ) throws InsufficientAccessException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Notification notification = dataAccessor.getNotification( request.notificationId );
		if( !AccessTokenFilter.getAccessToken().getUserId().equals( notification.getUserId() ) )
			throw new InsufficientAccessException();

		notification.setState( request.state );
		notification = dataAccessor.createOrUpdateNotification( notification );
		return new Response();

	}
	
}