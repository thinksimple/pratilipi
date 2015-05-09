package com.pratilipi.pagecontent.userpratilipi.api;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Put;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.pratilipi.commons.shared.BookmarkRequestType;
import com.pratilipi.data.transfer.shared.UserPratilipiData;
import com.pratilipi.pagecontent.userpratilipi.UserPratilipiContentHelper;
import com.pratilipi.pagecontent.userpratilipi.api.shared.PutUserPratilipiRequest;
import com.pratilipi.pagecontent.userpratilipi.api.shared.PutUserPratilipiResponse;

@SuppressWarnings( "serial" )
@Bind( uri = "/userpratilipi" )
public class UserPratilipiApi extends GenericApi {
	
	@Put
	public PutUserPratilipiResponse addUserPratilipi( PutUserPratilipiRequest request )
			throws InvalidArgumentException, InsufficientAccessException{
		
		UserPratilipiData userPratilipiData = new UserPratilipiData();
		userPratilipiData.setPratilipiId( request.getPratilipiId() );
		
		if( request.hasBookmark() ){
			String bookmark = request.getBookmark().toString();
			userPratilipiData.setBookmarks( bookmark );
			if( request.getRequestType().equals( "add" ))
				userPratilipiData.setBookmarkRequestType( BookmarkRequestType.ADD );
			else if( request.getRequestType().equals( "remove" ))
				userPratilipiData.setBookmarkRequestType( BookmarkRequestType.REMOVE );
		}
		
		if( request.hasAddedToLib() ){
			userPratilipiData.setAddedToLib( request.isAddedToLib() );
		}
		
		userPratilipiData = UserPratilipiContentHelper.saveUserPratilipi( userPratilipiData, this.getThreadLocalRequest() );
		
		PutUserPratilipiResponse putUserPratilipiResponse = new PutUserPratilipiResponse();
		putUserPratilipiResponse.setBookmarks( userPratilipiData.getBookmarks() );
		
		return putUserPratilipiResponse;
	}
	
}
