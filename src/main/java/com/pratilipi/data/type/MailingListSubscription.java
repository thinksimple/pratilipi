package com.pratilipi.data.type;

import java.util.Date;

import com.pratilipi.common.type.MailingList;

public interface MailingListSubscription extends GenericOfyType {

	MailingList getMailingList();
	
	void setMailingList( MailingList mailingList );
	
	String getEmail();
	
	void setEmail( String email );
	
	Long getUserId();
	
	void setUserId( Long userId );

	Date getSubscriptionDate();
	
	void setSubscriptionDate( Date date );
	
}
