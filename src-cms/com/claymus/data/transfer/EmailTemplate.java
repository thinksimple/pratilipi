package com.claymus.data.transfer;

public interface EmailTemplate {
	
	String getId();

	void setId( String id );

	String getSenderName();

	void setSenderName( String senderName );

	String getSenderEmail();

	void setSenderEmail( String senderEmail );

	String getReplyToName();

	void setReplyToName( String replyToName );

	String getReplyToEmail();

	void setReplyToEmail( String replyToEmail );

	String getSubject();

	void setSubject( String subject );

	String getBody();

	void setBody( String body );
	
}
