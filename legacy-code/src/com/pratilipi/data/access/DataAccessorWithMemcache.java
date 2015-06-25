package com.pratilipi.data.access;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.claymus.data.access.DataListCursorTuple;
import com.claymus.data.access.Memcache;
import com.pratilipi.commons.shared.AuthorFilter;
import com.pratilipi.commons.shared.PratilipiFilter;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Event;
import com.pratilipi.data.transfer.EventPratilipi;
import com.pratilipi.data.transfer.Genre;
import com.pratilipi.data.transfer.Language;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.data.transfer.PratilipiAuthor;
import com.pratilipi.data.transfer.PratilipiGenre;
import com.pratilipi.data.transfer.PratilipiTag;
import com.pratilipi.data.transfer.Publisher;
import com.pratilipi.data.transfer.Tag;
import com.pratilipi.data.transfer.UserPratilipi;

@SuppressWarnings("serial")
public class DataAccessorWithMemcache
		extends com.claymus.data.access.DataAccessorWithMemcache
		implements DataAccessor {
	
	private static final String PREFIX_EVENT = "Event-";
	private static final String PREFIX_USER_PRATILIPI = "UserPratilipi-";
	private static final String PREFIX_USER_PRATILIPI_LIST = "UserPratilipiList-";
	private static final String PREFIX_USER_PRATILIPI_PURCHASE_LIST = "UserPratilipiPurchaseList-";

	
	@Override
	public Event newEvent() {
		return dataAccessor.newEvent();
	}

	@Override
	public Event getEvent( Long id ) {
		Event event = memcache.get( PREFIX_EVENT + id );
		if( event == null ) {
			event = dataAccessor.getEvent( id );
			if( event != null )
				memcache.put( PREFIX_EVENT + id, event );
		}
		return event;
	}
	
	@Override
	public Event createOrUpdateEvent( Event event ) {
		event = dataAccessor.createOrUpdateEvent( event );
		memcache.put( PREFIX_EVENT + event.getId(), event );
		return event;
	}
	
	
	@Override
	public UserPratilipi newUserPratilipi() {
		return dataAccessor.newUserPratilipi();
	}
	
	@Override
	public UserPratilipi getUserPratilipiById( String userPratilipiId ){
		UserPratilipi userPratilipi = memcache.get(
				PREFIX_USER_PRATILIPI + userPratilipiId );
		if( userPratilipi == null ){
			userPratilipi = 
					dataAccessor.getUserPratilipiById( userPratilipiId );
			if( userPratilipi != null )
				memcache.put( PREFIX_USER_PRATILIPI + userPratilipiId, userPratilipi );
		}
		
		return userPratilipi;
	}
	
	@Override
	public UserPratilipi getUserPratilipi( Long userId, Long pratilipiId ) {
		UserPratilipi userPratilipi = memcache.get(
				PREFIX_USER_PRATILIPI + userId + "-" + pratilipiId );
		if( userPratilipi == null ) {
			userPratilipi =
					dataAccessor.getUserPratilipi( userId, pratilipiId );
			if( userPratilipi != null )
				memcache.put(
						PREFIX_USER_PRATILIPI + userId + "-" + pratilipiId,
						userPratilipi );
		}
		return userPratilipi;
	}

	@Override
	public List<UserPratilipi> getUserPratilipiList( Long pratilipiId ) {
		List<UserPratilipi> userPratilipiList =
				memcache.get( PREFIX_USER_PRATILIPI_LIST + pratilipiId );
		if( userPratilipiList == null ) {
			userPratilipiList =
					dataAccessor.getUserPratilipiList( pratilipiId );
			memcache.put(
					PREFIX_USER_PRATILIPI_LIST + pratilipiId,
					new ArrayList<>( userPratilipiList ) );
		}
		return userPratilipiList;
	}

	@Override
	public List<Long> getPurchaseList( Long userId ) {
		List<Long> purchaseList =
				memcache.get( PREFIX_USER_PRATILIPI_PURCHASE_LIST + userId );
		if( purchaseList == null ) {
			purchaseList =
					dataAccessor.getPurchaseList( userId );
			memcache.put(
					PREFIX_USER_PRATILIPI_PURCHASE_LIST + userId,
					new ArrayList<>( purchaseList ) );
		}
		return purchaseList;
	}

	@Override
	public UserPratilipi createOrUpdateUserPratilipi( UserPratilipi userPratilipi ) {
		userPratilipi = dataAccessor.createOrUpdateUserPratilipi( userPratilipi );
		memcache.put(
				PREFIX_USER_PRATILIPI + userPratilipi.getId(),
				userPratilipi );
		memcache.remove(
				PREFIX_USER_PRATILIPI_LIST + userPratilipi.getPratilipiId() );
		memcache.remove(
				PREFIX_USER_PRATILIPI_PURCHASE_LIST + userPratilipi.getUserId() );
		return userPratilipi;
	}


}
