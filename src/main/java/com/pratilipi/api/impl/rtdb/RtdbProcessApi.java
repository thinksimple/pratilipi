package com.pratilipi.api.impl.rtdb;

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
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DataListIterator;
import com.pratilipi.data.RtdbAccessor;
import com.pratilipi.data.type.AppProperty;
import com.pratilipi.data.type.Email;
import com.pratilipi.data.type.User;
import com.pratilipi.data.type.UserPreferenceRtdb;

@SuppressWarnings("serial")
@Bind( uri = "/rtdb/process" )
public class RtdbProcessApi extends GenericApi {

	private static final Integer RTDB_MAX_ANDROID_VERSION = 24;

	private static final Logger logger = 
			Logger.getLogger( RtdbProcessApi.class.getName() );

	@Get
	public GenericResponse get( GenericRequest request ) 
			throws UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		RtdbAccessor rtdbAccessor = DataAccessorFactory.getRtdbAccessor();

		// Fetching AppProperty
		String appPropertyId = "Api.RtdbProcess";
		AppProperty appProperty = dataAccessor.getAppProperty( appPropertyId );
		if( appProperty == null ) {
			appProperty = dataAccessor.newAppProperty( appPropertyId );
			appProperty.setValue( new Date( 1L ) );
		}


		// Fetching list of UserPreferenceRtdb - for recently updated from Desktop / New Android devices
		Map<Long,UserPreferenceRtdb> userPreferences = rtdbAccessor.getUserPreferences( (Date) appProperty.getValue() );

		// Updating AppProperty
		appProperty.setValue( new Date() );
		appProperty = dataAccessor.createOrUpdateAppProperty( appProperty );

		// Fetching list of UserPreferenceRtdb - for old Android devices
		userPreferences.putAll( rtdbAccessor.getUserPreferences( RTDB_MAX_ANDROID_VERSION ) );


		List<Email> emailList = new ArrayList<>();
		Set<Long> userIdSet = new HashSet<>();

		// Getting Emails with State PENDING
		DataListIterator<Email> itr = dataAccessor.getEmailListIteratorForStatePending();
		while( itr.hasNext() ) {
			Email email = itr.next();
			// User has set / updated the email settings recently
			if( userPreferences.containsKey( email.getUserId() ) ) {
				emailList.add( email );
				userIdSet.add( email.getUserId() );
			}
		}

		Map<Long, User> users = dataAccessor.getUsers( userIdSet );

		// Re-scheduling for all Emails
		for( Email email : emailList ) {

			UserPreferenceRtdb preference = userPreferences.get( email.getUserId() );
			User user = users.get( email.getUserId() );

			if( email.getScheduledDate().equals( preference.getEmailFrequency().getNextSchedule( user.getLastEmailedDate() ) ) ) {
				emailList.remove( email );
				continue;
			}

			logger.log( Level.INFO, "Rescheduling email: " + email.getId() + 
									" from " + email.getScheduledDate() + 
									" to " + preference.getEmailFrequency().getNextSchedule( user.getLastEmailedDate() ) );

			email.setScheduledDate( preference.getEmailFrequency().getNextSchedule( user.getLastEmailedDate() ) );

		}


		// Updating Email Table
		emailList = dataAccessor.createOrUpdateEmailList( emailList );

		return new GenericResponse();

	}

}
