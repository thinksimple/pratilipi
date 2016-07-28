package com.pratilipi.api.impl.notification;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.data.DataAccessorGaeImpl;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.client.NotificationData;
import com.pratilipi.data.util.NotificationDataUtil;
import com.pratilipi.filter.AccessTokenFilter;

@SuppressWarnings("serial")
@Bind( uri = "/notification/list" )
public class NotificationListApi extends GenericApi {

	private static final Logger logger =
			Logger.getLogger( DataAccessorGaeImpl.class.getName() );
	
	
	public static class GetRequest extends GenericRequest {

		private String cursor;
		private Integer resultCount;

	}
	
	public static class Response extends GenericResponse { 

		private List<NotificationApi.Response> notificationList;
		private String cursor;

		
		@SuppressWarnings("unused")
		private Response() {}
		
		public Response( List<NotificationData> notificationList, String cursor ) {
			this.notificationList = new ArrayList<>( notificationList.size() ); 
			for( NotificationData notification : notificationList )
				this.notificationList.add( new NotificationApi.Response( notification, NotificationListApi.class ) );
			this.cursor = cursor;
		}

	}
	
	
	@Get
	public Response get( GetRequest request ) throws InsufficientAccessException {
		
		DataListCursorTuple<NotificationData> notificationListCursorTuple = NotificationDataUtil.getNotificationList(
				AccessTokenFilter.getAccessToken().getUserId(),
				request.cursor,
				request.resultCount );
		
		return new Response(
				notificationListCursorTuple.getDataList(),
				notificationListCursorTuple.getCursor() );
		
	}
	
}