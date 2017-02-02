package com.pratilipi.api.impl.email;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.EmailFrequency;
import com.pratilipi.common.type.EmailState;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DataListIterator;
import com.pratilipi.data.RtdbAccessor;
import com.pratilipi.data.type.AppProperty;
import com.pratilipi.data.type.Email;
import com.pratilipi.data.type.User;
import com.pratilipi.data.type.UserPreferenceRtdb;
import com.pratilipi.data.util.EmailDataUtil;
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
@Bind( uri = "/email/process" )
public class EmailProcessApi extends GenericApi {

	private static final Integer MAX_ANDROID_VERSION = 24;
	private static final Integer PENDING_EMAIL_THRESHOLD = 10000;

	private static final Logger logger = 
			Logger.getLogger( EmailProcessApi.class.getName() );


	public static class PostRequest extends GenericRequest {

		@Validate( minLong = 1L )
		private Long emailId;

		@Validate( minLong = 1L )
		private Long userId;

	}


	@Get
	public GenericResponse get( GenericRequest request ) 
			throws UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		RtdbAccessor rtdbAccessor = DataAccessorFactory.getRtdbAccessor();

		/* Hybrid of two possible solutions:
		 * 
		 *  1. Get all PENDING e-mails
		 *  2. Get all users from firebase android version code less than 25
		 *  
		 */

		// AppProperty will keep on updating regardless of any approach
		String appPropertyId = "Api.EmailProcess";
		AppProperty appProperty = dataAccessor.getAppProperty( appPropertyId );
		if( appProperty == null ) {
			appProperty = dataAccessor.newAppProperty( appPropertyId );
			appProperty.setValue( new Date( 1L ) );
		}


		boolean pendingEmailsThresholdExceeded = 
				dataAccessor.getEmailCount( null, null, EmailState.PENDING, PENDING_EMAIL_THRESHOLD ) == PENDING_EMAIL_THRESHOLD;


		Map<Long, UserPreferenceRtdb> userPreferences = null;
		List<Email> emailList = new ArrayList<>();

		if( ! pendingEmailsThresholdExceeded ) { // Get all PENDING e-mails

			DataListIterator<Email> it = dataAccessor.getEmailListIteratorForStatePending( null, false );
			Set<Long> userIds = new HashSet<>();

			while( it.hasNext() ) {
				Email email = it.next();
				emailList.add( email );
				userIds.add( email.getUserId() );
			}

			userPreferences = rtdbAccessor.getUserPreferences( userIds );

		} else { // Get all users from firebase android version code less than 25

			userPreferences = rtdbAccessor.getUserPreferences( MAX_ANDROID_VERSION );
			userPreferences.putAll( rtdbAccessor.getUserPreferences( (Date) appProperty.getValue() ) );

			for( Long userId : userPreferences.keySet() )
				emailList.addAll( dataAccessor.getEmailList( userId, null, (String) null, EmailState.PENDING, null ) );

		}

		// Updating AppProperty
		appProperty.setValue( new Date() );
		appProperty = dataAccessor.createOrUpdateAppProperty( appProperty );

		Map<Long, User> users = dataAccessor.getUsers( userPreferences.keySet() );

		// Re-scheduling for all Emails
		List<Email> rescheduledEmails = new ArrayList<>();
		for( Email email : emailList ) {

			UserPreferenceRtdb preference = userPreferences.get( email.getUserId() );
			User user = users.get( email.getUserId() );

			if( email.getScheduledDate().equals( preference.getEmailFrequency().getNextSchedule( user.getLastEmailedDate() ) ) )
				continue;

			logger.log( Level.INFO, "Rescheduling email: " + email.getId() + 
									" from " + email.getScheduledDate() + 
									" to " + preference.getEmailFrequency().getNextSchedule( user.getLastEmailedDate() ) );

			email.setScheduledDate( preference.getEmailFrequency().getNextSchedule( user.getLastEmailedDate() ) );
			email.setLastUpdated( new Date() );

			rescheduledEmails.add( email );

		}

		rescheduledEmails = dataAccessor.createOrUpdateEmailList( rescheduledEmails );



		/*
		 * pendingEmailsThresholdExceeded => false, we have all PENDING mails in memory
		 * pendingEmailsThresholdExceeded => true, we should get all PENDING and scheduled emails
		 * 
		 * */

		if( pendingEmailsThresholdExceeded ) {

			emailList = new ArrayList<>();

			DataListIterator<Email> itr = dataAccessor.getEmailListIteratorForStatePending( null, true );
			while( itr.hasNext() )
				emailList.add( itr.next() );

			List<Long> missingUserIds = new ArrayList<>();
			for( Email email : emailList )
				missingUserIds.add( email.getUserId() );

			missingUserIds.removeAll( userPreferences.keySet() );
			userPreferences.putAll( rtdbAccessor.getUserPreferences( missingUserIds ) );
		}


		// Creating Tasks
		Set<Long> emailIdTaskSet = new HashSet<>();
		Set<Long> userIdTaskSet = new HashSet<>();
		for( Email email : emailList ) {
			if( email.getScheduledDate().after( new Date() ) ) // Since we got all PENDING emails in case 1
				continue;
			UserPreferenceRtdb preference = userPreferences.get( email.getUserId() );
			if( preference.getEmailFrequency() == EmailFrequency.IMMEDIATELY )
				emailIdTaskSet.add( email.getId() );
			else
				userIdTaskSet.add( email.getUserId() );
		}

		List<Task> taskList = new ArrayList<>();
		for( Long emailId : emailIdTaskSet )
			taskList.add( TaskQueueFactory.newTask()
					.setUrl( "/email/process" )
					.addParam( "emailId", emailId.toString() ) );
		for( Long userId : userIdTaskSet )
			taskList.add( TaskQueueFactory.newTask()
					.setUrl( "/email/process" )
					.addParam( "userId", userId.toString() ) );

		TaskQueueFactory.getEmailHpTaskQueue().addAll( taskList );

		return new GenericResponse();

	}

	@Post
	public GenericResponse post( PostRequest request ) throws UnexpectedServerException {

		if( request.emailId != null )
			EmailDataUtil.sendEmail( request.emailId );

		if( request.userId != null )
			EmailDataUtil.sendEmailsToUser( request.userId );

		return new GenericResponse();

	}

}
