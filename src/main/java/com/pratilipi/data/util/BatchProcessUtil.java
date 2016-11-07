package com.pratilipi.data.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

public class BatchProcessUtil {
	
	 public static void exec( Long batchProcessId ) throws UnexpectedServerException {
		 
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		BatchProcess batchProcess = dataAccessor.getBatchProcess( batchProcessId );
		if( batchProcess.getStateInProgress() != null )
			return;
		
		BatchProcessState currState = batchProcess.getType().getNextState( batchProcess.getStateCompleted() );
		if( currState == null )
			throw new UnexpectedServerException();
		
		batchProcess.setStateInProgress( currState );
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
		else if( currState == BatchProcessState.COMPLETED )
			batchProcess.setStateCompleted( currState );
		
		batchProcess.setStateInProgress( null );
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
		List<Long> userIdList = new LinkedList<>();
		
		List<Author> tempAuthorList = null;
		String cursor = null;
		int resultCount = 200;
		do {
			DataListCursorTuple<Author> authorListCusorTuple = dataAccessor.getAuthorList( authorFilter, cursor, resultCount );
			tempAuthorList = authorListCusorTuple.getDataList();
			for( Author author : tempAuthorList )
				if( author.getUserId() != null )
					userIdList.add( author.getUserId() );
			cursor = authorListCusorTuple.getCursor();
		} while( tempAuthorList.size() >= resultCount );
		
		processDoc.setData(
				BatchProcessState.GET_USER_IDS_BY_AUTHOR_FILTER.getOutputName(),
				userIdList );
		docAccessor.save( batchProcess.getId(), processDoc ); // Saving Doc

		batchProcess.setStateCompleted( BatchProcessState.GET_USER_IDS_BY_AUTHOR_FILTER );
		
	}

	private static void _execStateCreateNotificationsForUserIds( BatchProcess batchProcess )
			throws UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		DocAccessor docAccessor = DataAccessorFactory.getDocAccessor();

		BatchProcessDoc processDoc = docAccessor.getBatchProcessDoc( batchProcess.getId() );
		
		List<Long> userIdList = processDoc.getData(
				BatchProcessState.CREATE_NOTIFICATIONS_FOR_USER_IDS.getInputName(),
				new TypeToken<List<Long>>(){}.getType() );

		Map<Long,Long> processedUserIdList = processDoc.getData(
				BatchProcessState.CREATE_NOTIFICATIONS_FOR_USER_IDS.getOutputName(),
				new TypeToken<List<Long>>(){}.getType() );
		
		
		int count = 0; // 'count' will remain 0 if everything went fine in previous run
		for( Entry<Long, Long> entry : processedUserIdList.entrySet() ) {
			
			if( entry.getValue() != null ) {
				userIdList.remove( entry.getKey() );
				continue;
			}
			
			Notification notification = dataAccessor.getNotification(
					entry.getKey(),
					NotificationType.GENERIC,
					batchProcess.getId() );
			
			if( notification == null ) {
				count++;
			} else {
				entry.setValue( notification.getId() );
				userIdList.remove( entry.getKey() );
			}
			
		}
		
		for( Long userId : userIdList ) {
			processedUserIdList.put( userId, null );
			if( ++count == 100 ) // Limiting to 100 users per iteration
				break;
		}

		if( count != 0 ) {
			processDoc.setData(
					BatchProcessState.CREATE_NOTIFICATIONS_FOR_USER_IDS.getOutputName(),
					processedUserIdList );
			for( Entry<Long, Long> entry : processedUserIdList.entrySet() ) {
				
				if( entry.getValue() != null )
					continue;
				
				Notification notification = dataAccessor.newNotification(
						entry.getKey(),
						NotificationType.GENERIC,
						batchProcess.getId() );
				
				notification = dataAccessor.createOrUpdateNotification( notification );
				entry.setValue( notification.getId() );
				
			}
		}
		
		
		if( count < 100 )
			batchProcess.setStateCompleted( BatchProcessState.CREATE_NOTIFICATIONS_FOR_USER_IDS );
		
		processDoc.setData(
				BatchProcessState.CREATE_NOTIFICATIONS_FOR_USER_IDS.getOutputName(),
				processedUserIdList );
		docAccessor.save( batchProcess.getId(), processDoc ); // Saving Doc

	}
	
}
