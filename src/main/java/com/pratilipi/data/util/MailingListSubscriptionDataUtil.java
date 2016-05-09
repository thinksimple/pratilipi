package com.pratilipi.data.util;

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
	
	public static void subscribe( MailingList mailingList, String email )
			throws InvalidArgumentException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		MailingListSubscription mailingListSubscription = dataAccessor.getMailingListSubscription( mailingList, email );
		if( mailingListSubscription != null )
			throw new InvalidArgumentException( GenericRequest.ERR_MAILING_LIST_EMAIL_SUBSCRIBED_ALREDY );
		
		
		Gson gson = new Gson();
		
		mailingListSubscription = dataAccessor.newMailingListSubscription();
		
		AuditLog auditLog = dataAccessor.newAuditLogOfy();
		auditLog.setAccessId( AccessTokenFilter.getAccessToken().getId() );
		auditLog.setAccessType( AccessType.MAILING_LIST_SUBSCRIPTION_ADD );
		auditLog.setEventDataOld( gson.toJson( mailingListSubscription ) );
		
		mailingListSubscription.setMailingList( mailingList );
		mailingListSubscription.setEmail( email );
		
		auditLog.setEventDataNew( gson.toJson( mailingListSubscription ) );
		
		dataAccessor.createOrUpdateMailingListSubscription( mailingListSubscription, auditLog );
		
	}
	
}