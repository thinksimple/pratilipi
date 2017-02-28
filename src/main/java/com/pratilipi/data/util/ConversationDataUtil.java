package com.pratilipi.data.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.JsonObject;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.type.ContactTeam;
import com.pratilipi.common.type.UserState;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.Conversation;
import com.pratilipi.data.type.ConversationMessage;
import com.pratilipi.data.type.ConversationUser;
import com.pratilipi.data.type.User;

public class ConversationDataUtil {
	
	public static void saveMessage( ContactTeam team, Long userId,
			String name, String email, String phone,
			String message, JsonObject data ) throws InvalidArgumentException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		User user = dataAccessor.getUser( userId );
		Conversation conversation = dataAccessor.getConversation( team, userId );
		if( conversation == null )
			dataAccessor.getConversation( team, email );
		
		
		if( conversation != null ) {
			// Do Nothing !
		} else if( user != null && ( user.getState() == UserState.ACTIVE || user.getState() == UserState.REGISTERED ) ) { // && conversation == null
			conversation = dataAccessor.newConversation( team, userId );
			conversation.setCreator( userId );
			conversation.setCreatorName( name );
			conversation.setCreatorEmail( email );
			conversation.setCreatorPhone( phone );
			conversation.setCreationDate( new Date() );
			List<ConversationUser> conversationUserList = new ArrayList<>( team.getUserIds().length + 1 );
			conversationUserList.add( dataAccessor.newConversationUser( conversation.getId(), userId ) );
			for( Long recipientUserId : team.getUserIds() )
				conversationUserList.add( dataAccessor.newConversationUser( conversation.getId(), recipientUserId ) );
			conversation = dataAccessor.createOrUpdateConversation( conversation, conversationUserList );
		} else if( email != null ) { // && conversation == null
			conversation = dataAccessor.newConversation( team, email );
			conversation.setCreatorName( name );
			conversation.setCreatorEmail( email );
			conversation.setCreatorPhone( phone );
			conversation.setCreationDate( new Date() );
			List<ConversationUser> conversationUserList = new ArrayList<>( team.getUserIds().length + 1 );
			for( Long recipientUserId : team.getUserIds() )
				conversationUserList.add( dataAccessor.newConversationUser( conversation.getId(), recipientUserId ) );
			conversation = dataAccessor.createOrUpdateConversation( conversation, conversationUserList );
		} else {
			throw new InvalidArgumentException( "Valid 'email' is required." );
		}
		
		conversation.setLastUpdated( new Date() );
		
		ConversationMessage conversationMessage = dataAccessor.newConversationMessage();
		conversationMessage.setConversationId( conversation.getId() );
		conversationMessage.setCreatorId( userId );
		conversationMessage.setMessage( message );
		conversationMessage.setData( data );
		conversationMessage.setCreationDate( new Date() );
		
		conversation = dataAccessor.createOrUpdateConversation( conversation, conversationMessage );
		
		/*
		String language = author != null ? author.getLanguage().getNameEn().toLowerCase() : null;
		Logger.getLogger(ConversationDataUtil.class.getSimpleName())
					.log(Level.SEVERE, "Language : " + language);
		try {
			ArrayList<InternetAddress> receiverList = createReceiversId(team.name().toLowerCase(), language);
			InternetAddress[] receivers = new InternetAddress[receiverList.size()];
			receiverList.toArray(receivers);
			createSupportMailTask(receivers, userId.toString(), name, email, phone, message, data, team.name());
		} catch (UnsupportedEncodingException | UnexpectedServerException e) {
			// TODO Auto-generated catch block
			Logger.getLogger(ConversationDataUtil.class.getSimpleName())
					.log(Level.SEVERE, "Exception while sending Support mail");
			Logger.getLogger(ConversationDataUtil.class.getSimpleName())
					.log(Level.SEVERE, "User ID : " + userId);
			Logger.getLogger(ConversationDataUtil.class.getSimpleName())
					.log(Level.SEVERE, "Conversation Id : " + conversation.getId());
			e.printStackTrace();
		}
		*/
	}
	
	/*
	private static void createSupportMailTask(
			InternetAddress[] receiversList, String userId, String name, 
			String email, String phone, String message, JsonObject data, String team) 
					throws UnsupportedEncodingException, UnexpectedServerException {
		
		// Preparing report data
		String reportString = "", subject = "", type;
		if (data != null) {
			type = data.get(TYPE).getAsString();
			if (type.toLowerCase().equals("pratilipi")) {
				subject = "REPORTED PRATILIPI";
				reportString = "Reported Pratilipi Id : " + data.get(ID).getAsString() + "\n";
			} else {
				subject = "REPORTED AUTHOR";
				reportString = "Reported Author Id : " + data.get(ID).getAsString() + "\n";
			}
		}
		
		if (subject.isEmpty())
			subject = team;
		
		Logger.getLogger(ConversationDataUtil.class.getSimpleName())
				.log(Level.SEVERE, "Subject : " + subject);
		
		String body = "User Name : " + name + "\n"
				+ "Phone Number : " + phone + "\n"
				+ "User Id : " + userId + "\n"
				+ reportString
				+ "Message : " + MimeUtility.encodeText( message, "UTF-8", "B" ) + "\n";
		
		Logger.getLogger(ConversationDataUtil.class.getSimpleName())
				.log(Level.SEVERE, "Body : " + body);
		
		InternetAddress[] cc = new InternetAddress[]{new InternetAddress("ranjeet@pratilipi.com", "Ranjeet Pratap Singh")};
		
		Logger.getLogger(ConversationDataUtil.class.getSimpleName())
				.log(Level.SEVERE, "CC : " + Arrays.toString(cc));

		Logger.getLogger(ConversationDataUtil.class.getSimpleName())
				.log(Level.SEVERE, "CC : " + Arrays.toString(receiversList));
		
		// THIS IS FOR TESTING EMAIL. TASK QUEUE WILL BE ADDED IN PRODUCTION
		
		EmailUtil.sendMail(
				name, 
				email, 
				receiversList, 
				new InternetAddress[]{new InternetAddress("rahul@pratilipi.com", "Rahul Ranjan")}, 
				subject, 
				body
		);
		
	}
	
	private static ArrayList<InternetAddress> createReceiversId(String teamName, String language) 
				throws UnsupportedEncodingException {
		ArrayList<InternetAddress> emailList = new ArrayList<>();
		emailList.add(new InternetAddress( "abhishek@pratilipi.com", "Abhishek Sharma" ));
		emailList.add(new InternetAddress( "shreyansh@pratilipi.com", "Shreyansh Maini" ));
		emailList.add(new InternetAddress( "rahul@pratilipi.com", "Rahul Ranjan" ));
		if (teamName.contains(AEE) || teamName.contains(ISSUES)) {	
			// when contact team name is AEE_* or ANDROID_APP_ISSUES
			if (language != null)
// 				emailList.add(new InternetAddress( language + DOMAIN, language + DOMAIN ));
			Logger.getLogger(ConversationDataUtil.class.getSimpleName())
				.log(Level.SEVERE, "Language Team : " + language + DOMAIN);
		} else if (teamName.contains(TECH_SUPPORT)) {		
			// when contact team name is ANDROID_APP_TECH_SUPPORT 
// 			emailList.add(new InternetAddress( "prashant@pratilipi.com", "Prashant" ));
// 			emailList.add(new InternetAddress( "kshitij@pratilipi.com", "Kshitij Sharma" ));
			Logger.getLogger(ConversationDataUtil.class.getSimpleName())
				.log(Level.SEVERE, "Tech support issue");
		}
		
		return emailList;
	}
	*/
}
