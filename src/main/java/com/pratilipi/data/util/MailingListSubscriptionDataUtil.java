package com.pratilipi.data.util;

import java.util.Date;

import com.google.gson.Gson;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.type.AccessType;
import com.pratilipi.common.type.MailingList;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.AuditLog;
import com.pratilipi.data.type.MailingListSubscription;
import com.pratilipi.filter.AccessTokenFilter;

public class MailingListSubscriptionDataUtil {
	
	public static void subscribe( MailingList mailingList, Long userId, String email, String phone, String comment )
			throws InvalidArgumentException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		MailingListSubscription mailingListSubscription = dataAccessor.getMailingListSubscription( mailingList, email );
		if( mailingListSubscription != null && mailingListSubscription.getMailingList() != MailingList.LAUNCH_ANNOUNCEMENT_OTHER )
			throw new InvalidArgumentException( GenericRequest.ERR_MAILING_LIST_EMAIL_SUBSCRIBED_ALREDY );
		
		
		Gson gson = new Gson();
		
		mailingListSubscription = dataAccessor.newMailingListSubscription();
		
		AuditLog auditLog = dataAccessor.newAuditLog();
		auditLog.setAccessId( AccessTokenFilter.getAccessToken().getId() );
		auditLog.setAccessType( AccessType.MAILING_LIST_SUBSCRIPTION_ADD );
		auditLog.setEventDataOld( gson.toJson( mailingListSubscription ) );
		
		mailingListSubscription.setMailingList( mailingList );
		mailingListSubscription.setUserId( userId );
		mailingListSubscription.setEmail( email );
		mailingListSubscription.setPhone( phone );
		mailingListSubscription.setComment( comment );
		mailingListSubscription.setSubscriptionDate( new Date() );
		
		auditLog.setEventDataNew( gson.toJson( mailingListSubscription ) );
		
		dataAccessor.createOrUpdateMailingListSubscription( mailingListSubscription, auditLog );
		
	}
	
}