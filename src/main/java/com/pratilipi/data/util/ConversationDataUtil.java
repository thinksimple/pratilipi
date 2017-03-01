package com.pratilipi.data.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;

import com.google.gson.JsonObject;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.ContactTeam;
import com.pratilipi.common.type.UserState;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Conversation;
import com.pratilipi.data.type.ConversationMessage;
import com.pratilipi.data.type.ConversationUser;
import com.pratilipi.data.type.User;
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

public class ConversationDataUtil {

	private static final String DOMAIN = "@pratilipi.com";
	private static final String AEE = "aee";
	private static final String TECH_SUPPORT = "tech_support";
	private static final String ISSUES = "issues";
	private static final String FEATURE_REQUEST = "feature_request";
	private static final String FEEDBACK = "feedback";
	private static final String TYPE = "type";
	private static final String ID = "id";

	private static final Logger logger = Logger.getLogger(ConversationDataUtil.class.getName());

	public static void saveMessage(ContactTeam team, Long userId, String name, String email, String phone,
			String message, JsonObject data) throws InvalidArgumentException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		User user = dataAccessor.getUser(userId);
		Author author = dataAccessor.getAuthorByUserId(userId);
		Conversation conversation = dataAccessor.getConversation(team, userId);
		if (conversation == null)
			dataAccessor.getConversation(team, email);

		if (conversation != null) {
			// Do Nothing !
		} else if (user != null && (user.getState() == UserState.ACTIVE || user.getState() == UserState.REGISTERED)) { // &&
																														// conversation
																														// ==
																														// null
			conversation = dataAccessor.newConversation(team, userId);
			conversation.setCreator(userId);
			conversation.setCreatorName(name);
			conversation.setCreatorEmail(email);
			conversation.setCreatorPhone(phone);
			conversation.setCreationDate(new Date());
			List<ConversationUser> conversationUserList = new ArrayList<>(team.getUserIds().length + 1);
			conversationUserList.add(dataAccessor.newConversationUser(conversation.getId(), userId));
			for (Long recipientUserId : team.getUserIds())
				conversationUserList.add(dataAccessor.newConversationUser(conversation.getId(), recipientUserId));
			conversation = dataAccessor.createOrUpdateConversation(conversation, conversationUserList);
		} else if (email != null) { // && conversation == null
			conversation = dataAccessor.newConversation(team, email);
			conversation.setCreatorName(name);
			conversation.setCreatorEmail(email);
			conversation.setCreatorPhone(phone);
			conversation.setCreationDate(new Date());
			List<ConversationUser> conversationUserList = new ArrayList<>(team.getUserIds().length + 1);
			for (Long recipientUserId : team.getUserIds())
				conversationUserList.add(dataAccessor.newConversationUser(conversation.getId(), recipientUserId));
			conversation = dataAccessor.createOrUpdateConversation(conversation, conversationUserList);
		} else {
			throw new InvalidArgumentException("Valid 'email' is required.");
		}

		conversation.setLastUpdated(new Date());

		ConversationMessage conversationMessage = dataAccessor.newConversationMessage();
		conversationMessage.setConversationId(conversation.getId());
		conversationMessage.setCreatorId(userId);
		conversationMessage.setMessage(message);
		conversationMessage.setData(data);
		conversationMessage.setCreationDate(new Date());

		conversation = dataAccessor.createOrUpdateConversation(conversation, conversationMessage);

		String language = author != null ? author.getLanguage().getNameEn().toLowerCase() : null;
		try {
			ArrayList<InternetAddress> receiverList = createReceiversId(team.name().toLowerCase(), language);
			InternetAddress[] receivers = new InternetAddress[receiverList.size()];
			receiverList.toArray(receivers);
			createSupportMailTask(receivers, userId.toString(), name, email, phone, message, data, team.name());
		} catch (UnsupportedEncodingException | UnexpectedServerException e) {
			// TODO Auto-generated catch block
			logger.log(Level.SEVERE, "Exception while creating conversation mail task");
			logger.log(Level.SEVERE, "User ID : " + userId);
			logger.log(Level.SEVERE, "Conversation Id : " + conversation.getId());
			e.printStackTrace();
		}

	}

	private static void createSupportMailTask(InternetAddress[] receiversList, String userId, String name, String email,
			String phone, String message, JsonObject data, String team)
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
		
		subject = subject + " :: " + name + " :: " + email;

		Logger.getLogger(ConversationDataUtil.class.getSimpleName()).log(Level.SEVERE, "Subject : " + subject);

		String body = "User Name : " + name + "\n" + "Phone Number : " + phone + "\n" + "User Id : " + userId + "\n"
				+ reportString + "Message : " + MimeUtility.encodeText(message, "UTF-8", "B") + "\n";

		Logger.getLogger(ConversationDataUtil.class.getSimpleName()).log(Level.SEVERE, "Body : " + body);

		InternetAddress[] cc = new InternetAddress[] {
				new InternetAddress("ranjeet@pratilipi.com", "Ranjeet Pratap Singh") };

		Logger.getLogger(ConversationDataUtil.class.getSimpleName()).log(Level.SEVERE, "CC : " + Arrays.toString(cc));

		Logger.getLogger(ConversationDataUtil.class.getSimpleName()).log(Level.SEVERE,
				"CC : " + Arrays.toString(receiversList));

		// CREATING TASK QUEUE FOR EMAIL.
		Task task = TaskQueueFactory.newTask().setUrl("/contact/email").addParam("body", body)
				.addParam("subject", subject).addParam("receivers", Arrays.toString(receiversList))
				.addParam("cc", Arrays.toString(cc));

		TaskQueueFactory.getConversationTaskQueue().add(task);

	}

	private static ArrayList<InternetAddress> createReceiversId(String teamName, String language)
			throws UnsupportedEncodingException {
		ArrayList<InternetAddress> emailList = new ArrayList<>();
		emailList.add(new InternetAddress("abhishek@pratilipi.com", "Abhishek Sharma"));
		emailList.add(new InternetAddress("shreyans@pratilipi.com", "Shreyans Maini"));
		if (teamName.contains(AEE) || teamName.contains(ISSUES)) {
			// when contact team name is AEE_* or ANDROID_APP_ISSUES
			if (language != null)
				emailList.add(new InternetAddress(language + DOMAIN, language + DOMAIN));
			logger.log(Level.SEVERE, "Language Team : " + language + DOMAIN);
		} else if (teamName.contains(TECH_SUPPORT)) {
			// when contact team name is ANDROID_APP_TECH_SUPPORT
			emailList.add(new InternetAddress("prashant@pratilipi.com", "Prashant"));
			emailList.add(new InternetAddress("kshitij@pratilipi.com", "Kshitij Sharma"));
		}
		// For ANDROID_APP_FEATURE_REQUEST and ANDROID_APP_FEEDBACK, mail is
		// sent to abhishek and shreyansh only

		return emailList;
	}

}
