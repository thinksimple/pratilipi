package com.pratilipi.data.type;

public interface ConversationUser extends GenericOfyType {

	String getId();
	
	String getConversationId();
	
	void setConversationId( String coversationId );
	
	Long getUserId();
	
	void setUserId( Long userId );

	String getName();
	
	void setName( String name );
	
	String getEmail();
	
	void setEmail( String email );
	
	String getPhone();
	
	void setPhone( String phone );
	
}