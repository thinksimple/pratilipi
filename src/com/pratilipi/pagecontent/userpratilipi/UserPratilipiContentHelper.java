package com.pratilipi.pagecontent.userpratilipi;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.data.transfer.AccessToken;
import com.claymus.data.transfer.User;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.UserPratilipi;
import com.pratilipi.service.shared.data.UserPratilipiData;

public class UserPratilipiContentHelper {
	
	public static Boolean hasRequestAccessToAddBookmarks( HttpServletRequest request ){
		AccessToken accessToken = ( AccessToken ) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN );
		if( accessToken.getUserId() == 0L )
			return false;
		else
			return true;
	}

	public static UserPratilipiData createUserPratilipiData( Long pratilipiId, HttpServletRequest request ){
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		AccessToken accessToken = (AccessToken) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN );
		UserPratilipi userPratilipi = dataAccessor.getUserPratilipi( accessToken.getUserId(), pratilipiId );
		
		UserPratilipiData userPratilipiData = createUserPratilipiData( userPratilipi, request );
		
		return userPratilipiData;
	}
	
	public static UserPratilipiData createUserPratilipiData( UserPratilipi userPratilipi, HttpServletRequest request ){
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		User user = dataAccessor.getUser( userPratilipi.getUserId() );
		
		UserPratilipiData userPratilipiData = new UserPratilipiData();
		userPratilipiData.setId( userPratilipi.getId() );
		userPratilipiData.setUserId( user.getId() );
		userPratilipiData.setUserName( user.getFirstName() + " " + user.getLastName() );
		userPratilipiData.setPratilipiId( userPratilipi.getPratilipiId() );
		userPratilipiData.setRating( userPratilipi.getRating() );
		userPratilipiData.setReview( userPratilipi.getReview() );
		userPratilipiData.setReviewState( userPratilipi.getReviewState() );
		userPratilipiData.setReviewDate( userPratilipi.getReviewDate() );
		
		return userPratilipiData;
	}
	
	public static UserPratilipiData saveUserPratilipi( UserPratilipiData userPratilipiData, HttpServletRequest request )
			throws InsufficientAccessException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		UserPratilipi userPratilipi = dataAccessor.getUserPratilipi( userPratilipiData.getUserId(), userPratilipiData.getPratilipiId() );
		
		if( userPratilipi == null ){	//new record
			userPratilipi = dataAccessor.newUserPratilipi();
			userPratilipi.setUserId( userPratilipiData.getUserId() );
			userPratilipi.setPratilipiId( userPratilipiData.getPratilipiId() );
		}
		
		if( userPratilipiData.hasBookmarks() ){
			if( !hasRequestAccessToAddBookmarks( request ))
				throw new InsufficientAccessException();
			
			userPratilipi.setBookmarks( userPratilipiData.getBookmarks() );
		}
		
		userPratilipi = dataAccessor.createOrUpdateUserPratilipi( userPratilipi );
		
		return createUserPratilipiData( userPratilipi, request );
	}
	
}
