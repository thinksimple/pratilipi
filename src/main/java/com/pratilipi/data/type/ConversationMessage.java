package com.pratilipi.data.type;

import java.util.Date;

public interface ConversationMessage extends GenericOfyType {

	Long getId();
	
	String getConversationId();
	
	void setConversationId( String name );
	
	Long getCreatorId();
	
	void setCreatorId( Long userId );
	
	String getMessage();

	void setMessage( String message );
	
	Date getCreationDate();
	
	void setCreationDate( Date creationDate );
	
}