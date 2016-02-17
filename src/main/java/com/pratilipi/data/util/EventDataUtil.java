package com.pratilipi.data.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.AccessType;
import com.pratilipi.common.type.PageType;
import com.pratilipi.common.util.ImageUtil;
import com.pratilipi.common.util.SystemProperty;
import com.pratilipi.common.util.UriAliasUtil;
import com.pratilipi.common.util.UserAccessUtil;
import com.pratilipi.data.BlobAccessor;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.client.EventData;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.type.AuditLog;
import com.pratilipi.data.type.BlobEntry;
import com.pratilipi.data.type.Event;
import com.pratilipi.data.type.Page;
import com.pratilipi.filter.AccessTokenFilter;


public class EventDataUtil {
	
	private static final Logger logger =
			Logger.getLogger( EventDataUtil.class.getName() );

	
	private static final String BANNER_FOLDER = "event-banner/original";


	public static boolean hasAccessToAddEventData( EventData eventData ) {
		
		// User with EVENT_ADD access can add an Event.
		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		if( UserAccessUtil.hasUserAccess( accessToken.getUserId(), eventData.getLanguage(), AccessType.EVENT_ADD ) )
			return true;
		
		return false;
		
	}

	public static boolean hasAccessToUpdateEventData( Event event, EventData eventData ) {

		// User with EVENT_UPDATE access can update any event.
		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		if( UserAccessUtil.hasUserAccess( accessToken.getUserId(), event.getLanguage(), AccessType.EVENT_UPDATE ) ) {
			if( eventData == null || ! eventData.hasLanguage() || eventData.getLanguage() == event.getLanguage() )
				return true;
			else if( UserAccessUtil.hasUserAccess( accessToken.getUserId(), eventData.getLanguage(), AccessType.EVENT_UPDATE ) )
				return true;
		}
		
		return false;
		
	}
	
	
	public static String createEventBannerUrl( Event event ) {
		String url = "/event/banner" + "?eventId=" + event.getId() + "&version=" + event.getLastUpdated().getTime();
		if( SystemProperty.CDN != null )
			url = SystemProperty.CDN.replace( "*", event.getId() % 10 + "" ) + url;
		return url;
	}

	
	public static EventData createEventData( Event event ) {
		EventData eventData = new EventData();
		eventData.setId( event.getId() );
		eventData.setName( event.getName() );
		eventData.setNameEn( event.getNameEn() );
		eventData.setLanguage( event.getLanguage() );
		eventData.setDescription( event.getDescription() );
		eventData.setPratilipiIdList( event.getPratilipiIdList() );
		if( event.getPratilipiIdList() != null ) {
			DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
			Map<Long,Page> map = dataAccessor.getPages( PageType.PRATILIPI, event.getPratilipiIdList() );
			List<String> pratilipiUrlList = new ArrayList<>( event.getPratilipiIdList().size() );
			for( Long pratilipiId : event.getPratilipiIdList() ) {
				Page page = map.get( pratilipiId );
				pratilipiUrlList.add( page.getUriAlias() == null ? page.getUri() : page.getUriAlias() );
			}
			eventData.setPratilipiUrlList( pratilipiUrlList );
		}
		eventData.setBannerImageUrl( createEventBannerUrl( event ) );
		eventData.setAccessToUpdate( hasAccessToUpdateEventData( event, null ) );
		return eventData;
	}
	
	public static EventData saveEventData( EventData eventData ) throws InsufficientAccessException {
		
		boolean isNew = eventData.getId() == null;
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Event event = isNew ? dataAccessor.newEvent() : dataAccessor.getEvent( eventData.getId() );
		
		if ( isNew && ! hasAccessToAddEventData( eventData ) )
			throw new InsufficientAccessException();
		if( ! isNew && ! hasAccessToUpdateEventData( event, eventData ) )
			throw new InsufficientAccessException();
		

		Gson gson = new Gson();

		
		AuditLog auditLog = dataAccessor.newAuditLog();
		auditLog.setAccessId( AccessTokenFilter.getAccessToken().getId() );
		auditLog.setAccessType( isNew ? AccessType.EVENT_ADD : AccessType.EVENT_UPDATE );
		auditLog.setEventDataOld( gson.toJson( event ) );
		
		
		if( eventData.hasName() )
			event.setName( eventData.getName() );
		if( eventData.hasNameEn() )
			event.setName( eventData.getNameEn() );
		if( eventData.hasLanguage() )
			event.setLanguage( eventData.getLanguage() );
		if( eventData.hasDescription() )
			event.setDescription( eventData.getDescription() );
		if( eventData.hasPratilipiUrlList() ) {
			if( eventData.getPratilipiUrlList() == null ) {
				eventData.setPratilipiIdList( null );
			} else {
				List<Long> pratilipiIdList = new LinkedList<>();
				Map<String,Page> map = dataAccessor.getPages( eventData.getPratilipiUrlList() );
				for( String pratilipiUrl : eventData.getPratilipiUrlList() ) {
					Page page = map.get( pratilipiUrl );
					if( page != null && page.getType() == PageType.PRATILIPI )
						pratilipiIdList.add( page.getPrimaryContentId() );
				}
				event.setPratilipiIdList( pratilipiIdList );
			}
		}
		if( isNew )
			event.setCreationDate( new Date() );
		event.setLastUpdated( new Date() );
		
		
		auditLog.setEventDataNew( gson.toJson( event ) );
		
		
//		TODO: Invoke this method instead
//		event = dataAccessor.createOrUpdateEvent( event, auditLog );
		event = dataAccessor.createOrUpdateEvent( event );
		
		createOrUpdateEventPageUrl( event.getId() );

		return createEventData( event );
		
	}
	
	
	public static BlobEntry getEventBanner( Long eventId, Integer width )
			throws UnexpectedServerException {

		String fileName = BANNER_FOLDER + "/" + eventId;
		BlobEntry blobEntry = DataAccessorFactory.getBlobAccessor().getBlob( fileName );
		if( width != null )
			blobEntry.setData( ImageUtil.resize( blobEntry.getData(), width ) );
		return blobEntry;
		
	}
	
	public static void saveEventBanner( Long eventId, BlobEntry blobEntry )
			throws InsufficientAccessException, UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Event event = dataAccessor.getEvent( eventId );

		if( ! hasAccessToUpdateEventData( event, null ) )
			throw new InsufficientAccessException();

		
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
		blobEntry.setName( BANNER_FOLDER + "/" + eventId );
		blobAccessor.createOrUpdateBlob( blobEntry );
		
		
		Gson gson = new Gson();

		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		AuditLog auditLog = dataAccessor.newAuditLog();
		auditLog.setAccessId( accessToken.getId() );
		auditLog.setAccessType( AccessType.EVENT_UPDATE );
		auditLog.setEventDataOld( gson.toJson( event ) );

		event.setLastUpdated( new Date() );

		auditLog.setEventDataNew( gson.toJson( event ) );
		auditLog.setEventComment( "Uploaded banner image." );
		
//		TODO: Invoke this method instead
//		event = dataAccessor.createOrUpdatePratilipi( event, auditLog );
		event = dataAccessor.createOrUpdateEvent( event );
		
	}
	
	
	public static void createOrUpdateEventPageUrl( Long eventId ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Event event = dataAccessor.getEvent( eventId );
		Page page = dataAccessor.getPage( PageType.EVENT, eventId );

		boolean isNew = page == null;
		
		if( isNew ) {
			page = dataAccessor.newPage();
			page.setType( PageType.EVENT );
			page.setUri( PageType.EVENT.getUrlPrefix() + eventId );
			page.setPrimaryContentId( eventId );
			page.setCreationDate( new Date() );
		}
		
		String uriAlias = UriAliasUtil.generateUriAlias(
				page.getUriAlias(),
				PageType.EVENT.getUrlPrefix(),
				event.getNameEn() == null ? event.getName() : event.getNameEn() );
			
		if( isNew && uriAlias == null ) {
			// Do NOT return.
		} else if( uriAlias == page.getUriAlias()
				|| ( uriAlias != null && uriAlias.equals( page.getUriAlias() ) )
				|| ( page.getUriAlias() != null && page.getUriAlias().equals( uriAlias ) ) ) {
			// Do Nothing.
			return;
		} else {
			logger.log( Level.INFO, "Updating Event Page Url: '" + page.getUriAlias() + "' -> '" + uriAlias + "'" );
			page.setUriAlias( uriAlias );
		}
		
		page = dataAccessor.createOrUpdatePage( page );
	
	}

	
}
