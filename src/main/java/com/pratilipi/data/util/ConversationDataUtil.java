package com.pratilipi.data.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.type.ContactTeam;
import com.pratilipi.common.type.UserState;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.Conversation;
import com.pratilipi.data.type.ConversationMessage;
import com.pratilipi.data.type.ConversationUser;
import com.pratilipi.data.type.User;

public class ConversationDataUtil {
	
	public static void saveMessage( ContactTeam team, Long userId, String message ) throws InsufficientAccessException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		User user = dataAccessor.getUser( userId );
		if( user == null || ( user.getState() != UserState.ACTIVE || user.getState() != UserState.REGISTERED ) )
			throw new InsufficientAccessException();
		
		Conversation conversation = dataAccessor.getConversation( team, userId );
		if( conversation == null ) {
			conversation = dataAccessor.newConversation( team, userId );
			conversation.setCreationDate( new Date() );
			List<ConversationUser> conversationUserList = new ArrayList<>( team.getUserIds().length + 1 );
			for( Long recipientUserId : team.getUserIds() )
				conversationUserList.add( dataAccessor.newConversationUser( conversation.getId(), recipientUserId ) );
			conversationUserList.add( dataAccessor.newConversationUser( conversation.getId(), userId ) );
			conversation = dataAccessor.createOrUpdateConversation( conversation, conversationUserList );
		}
		
		conversation.setLastUpdated( new Date() );
		
		ConversationMessage conversationMessage = dataAccessor.newConversationMessage();
		conversationMessage.setConversationId( conversation.getId() );
		conversationMessage.setCreatorId( userId );
		conversationMessage.setMessage( message );
		conversationMessage.setCreationDate( new Date() );
		
		conversation = dataAccessor.createOrUpdateConversation( conversation, conversationMessage );
		
	}

}
