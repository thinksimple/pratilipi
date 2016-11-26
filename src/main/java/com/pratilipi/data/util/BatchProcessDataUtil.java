package com.pratilipi.data.util;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.BatchProcessState;
import com.pratilipi.common.type.NotificationType;
import com.pratilipi.common.util.AuthorFilter;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.DocAccessor;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.BatchProcess;
import com.pratilipi.data.type.BatchProcessDoc;
import com.pratilipi.data.type.Notification;


public class BatchProcessDataUtil {
	
	public static void exec( Long batchProcessId ) throws UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		BatchProcess batchProcess = dataAccessor.getBatchProcess( batchProcessId );
		if( batchProcess.getStateInProgress() != null && ! batchProcess.getStateInProgress().isTimedOut( batchProcess.getLastUpdated() ) )
			return;
		
		BatchProcessState currState = batchProcess.getType().getNextState( batchProcess.getStateCompleted() );
		if( currState == null )
			throw new UnexpectedServerException();
		
		batchProcess.setStateInProgress( currState );
		batchProcess.setLastUpdated( new Date() );
		batchProcess = dataAccessor.createOrUpdateBatchProcess( batchProcess );

		System.out.println( "Process Id: " + batchProcess.getId() );
		System.out.println( "Process Type: " + batchProcess.getType() );
		System.out.println( "Process State: " + batchProcess.getStateInProgress() );
		
		if( currState == BatchProcessState.INIT )
			_execStateInit( batchProcess );
		else if( currState == BatchProcessState.GET_USER_IDS_BY_AUTHOR_FILTER )
			_execStateGetUserIdsByAuthorFilter( batchProcess );
		else if( currState == BatchProcessState.CREATE_NOTIFICATIONS_FOR_USER_IDS )
			_execStateCreateNotificationsForUserIds( batchProcess );
		else if( currState == BatchProcessState.VALIDATE_NOTIFICATION_COUNT )
			_execStateValidateNotificationCount( batchProcess );
		else if( currState == BatchProcessState.COMPLETED )
			batchProcess.setStateCompleted( currState );
		
		batchProcess.setStateInProgress( null );
		batchProcess.setLastUpdated( new Date() );
		batchProcess = dataAccessor.createOrUpdateBatchProcess( batchProcess ); // Saving Entity

	}
	
	
	private static void _execStateInit( BatchProcess batchProcess )
			throws UnexpectedServerException {
		
		DocAccessor docAccessor = DataAccessorFactory.getDocAccessor();

		BatchProcessDoc bpDoc = docAccessor.newBatchProcessDoc();
		JsonObject initDoc = new Gson().fromJson(
				batchProcess.getInitDoc(),
				JsonElement.class ).getAsJsonObject();
		
		for( Entry<String,JsonElement> entry : initDoc.entrySet() )
			bpDoc.setData( entry.getKey(), entry.getValue() );
		docAccessor.save( batchProcess.getId(), bpDoc ); // Saving Doc

		batchProcess.setStateCompleted( BatchProcessState.INIT );

	}
	
	private static void _execStateGetUserIdsByAuthorFilter( BatchProcess batchProcess )
			throws UnexpectedServerException {
	
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		DocAccessor docAccessor = DataAccessorFactory.getDocAccessor();

		BatchProcessDoc processDoc = docAccessor.getBatchProcessDoc( batchProcess.getId() );
		
		AuthorFilter authorFilter = processDoc.getData(
				BatchProcessState.GET_USER_IDS_BY_AUTHOR_FILTER.getInputName(),
				AuthorFilter.class );
		Set<Long> userIdSet = new HashSet<>();
		
		List<Author> tempAuthorList = null;
		String cursor = null;
		int resultCount = 200;
		do {
			DataListCursorTuple<Author> authorListCusorTuple = dataAccessor.getAuthorList( authorFilter, cursor, resultCount );
			tempAuthorList = authorListCusorTuple.getDataList();
			for( Author author : tempAuthorList )
				if( author.getUserId() != null )
					userIdSet.add( author.getUserId() );
			cursor = authorListCusorTuple.getCursor();
		} while( tempAuthorList.size() >= resultCount );
		
		processDoc.setData(
				BatchProcessState.GET_USER_IDS_BY_AUTHOR_FILTER.getOutputName(),
				userIdSet );
		docAccessor.save( batchProcess.getId(), processDoc ); // Saving Doc

		batchProcess.setStateCompleted( BatchProcessState.GET_USER_IDS_BY_AUTHOR_FILTER );
		
	}

	private static void _execStateCreateNotificationsForUserIds( BatchProcess batchProcess )
			throws UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		DocAccessor docAccessor = DataAccessorFactory.getDocAccessor();

		
		BatchProcessDoc processDoc = docAccessor.getBatchProcessDoc( batchProcess.getId() );
		
		Set<Long> userIdSet = processDoc.getData(
				BatchProcessState.CREATE_NOTIFICATIONS_FOR_USER_IDS.getInputName(),
				new TypeToken<Set<Long>>(){}.getType() );

		Map<Long,Long> userIdNotifIdMap = processDoc.getData(
				BatchProcessState.CREATE_NOTIFICATIONS_FOR_USER_IDS.getOutputName(),
				new TypeToken<Map<Long,Long>>(){}.getType() );
		
		if( userIdNotifIdMap == null )
			userIdNotifIdMap = new HashMap<>();
		
		
		JsonObject execDoc = new Gson().fromJson( batchProcess.getExecDoc(), JsonElement.class ).getAsJsonObject();
		NotificationType type = NotificationType.valueOf( execDoc.get( "type" ).getAsString() );
		String sourceId = execDoc.get( "sourceId" ).getAsString();
		String createdBy = "BATCH_PROCESS::" + batchProcess.getId();
		
		
		int count = 0; // 'count' will remain 0 if everything went fine in previous run
		for( Entry<Long, Long> entry : userIdNotifIdMap.entrySet() ) {
			
			if( entry.getValue() != 0L ) {
				userIdSet.remove( entry.getKey() );
				continue;
			}
			
			Notification notification = dataAccessor.getNotification(
					entry.getKey(),
					type,
					sourceId,
					createdBy );
			
			if( notification == null )
				count++;
			else
				entry.setValue( notification.getId() );
			userIdSet.remove( entry.getKey() );
			
		}

		
		for( Long userId : userIdSet ) {
			userIdNotifIdMap.put( userId, 0L ); // Can't put null (instead of 0) here because Gson ignores keys with null values
			if( ++count == 100 ) // Limiting to 100 users per run
				break;
		}

		
		if( count != 0 ) {
			
			processDoc.setData(
					BatchProcessState.CREATE_NOTIFICATIONS_FOR_USER_IDS.getOutputName(),
					userIdNotifIdMap );
			docAccessor.save( batchProcess.getId(), processDoc ); // Saving Doc
			
			for( Entry<Long, Long> entry : userIdNotifIdMap.entrySet() ) {
				
				if( entry.getValue() != 0L )
					continue;
				
				Notification notification = dataAccessor.newNotification(
						entry.getKey(),
						type,
						sourceId,
						createdBy );
				notification = dataAccessor.createOrUpdateNotification( notification );
				
				entry.setValue( notification.getId() );
				
			}
			
		}
		
		
		if( userIdSet.size() == userIdNotifIdMap.size() )
			batchProcess.setStateCompleted( BatchProcessState.CREATE_NOTIFICATIONS_FOR_USER_IDS );

		
		processDoc.setData(
				BatchProcessState.CREATE_NOTIFICATIONS_FOR_USER_IDS.getOutputName(),
				userIdNotifIdMap );
		docAccessor.save( batchProcess.getId(), processDoc ); // Saving Doc

	}
	
	private static void _execStateValidateNotificationCount( BatchProcess batchProcess )
			throws UnexpectedServerException {
	
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		DocAccessor docAccessor = DataAccessorFactory.getDocAccessor();

		
		BatchProcessDoc processDoc = docAccessor.getBatchProcessDoc( batchProcess.getId() );
		String createdBy = "BATCH_PROCESS::" + batchProcess.getId();
		
		Map<Long,Long> userIdNotifIdMap = processDoc.getData(
				BatchProcessState.VALIDATE_NOTIFICATION_COUNT.getInputName(),
				new TypeToken<Map<Long,Long>>(){}.getType() );
		
		int notifCount = dataAccessor.getNotificationCout( createdBy );
		
		
		if( userIdNotifIdMap.size() == notifCount )
			batchProcess.setStateCompleted( BatchProcessState.VALIDATE_NOTIFICATION_COUNT );
		
	}
	
}
