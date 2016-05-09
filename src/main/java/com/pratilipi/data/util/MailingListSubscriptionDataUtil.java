package com.pratilipi.data.util;

import com.google.gson.Gson;
import com.pratilipi.common.type.AccessType;
import com.pratilipi.common.type.MailingList;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.AuditLog;
import com.pratilipi.data.type.MailingListSubscription;
import com.pratilipi.filter.AccessTokenFilter;

public class MailingListSubscriptionDataUtil {
	
	public static void subscribe( MailingList mailingList, String email ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		Gson gson = new Gson();
		
		MailingListSubscription mailingListSubscription = dataAccessor.newMailingListSubscription();
		
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