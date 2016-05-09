package com.pratilipi.data.type;

import java.util.Date;

public interface MailingListSubscription extends GenericOfyType {

	String getMailingListName();
	
	void setMailingListName( String mailingListName );
	
	String getEmail();
	
	void setEmail( String email );
	
	Long getUserId();
	
	void setUserId( Long userId );

	Date getSubscriptionDate();
	
	void setSubscriptionDate( Date date );
	
}
