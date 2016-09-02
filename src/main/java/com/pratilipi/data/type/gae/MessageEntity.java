package com.pratilipi.data.type.gae;

import com.googlecode.objectify.Key;
import com.pratilipi.data.type.Message;

public class MessageEntity implements Message {

	private Long MESSAGE_ID;
	
	private String NAME;
	
	private String EMAIL;
	
	private String SUBJECT;
	
	private String BODY;
	
	
	@Override
	public Long getId() {
		return MESSAGE_ID;	
	}
	
	public void setId( Long id ) {
		this.MESSAGE_ID = id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Key<T> getKey() {
		return getId() == null ? null : (Key<T>) Key.create( getClass(), getId() );
	}
	
	@Override
	public <T> void setKey( Key<T> key ) {
		this.MESSAGE_ID = key.getId();
	}

	
	@Override
	public String getName() {
		return NAME;
	}
	
	@Override
	public void setName( String name ) {
		this.NAME = name;
	}
	
	@Override
	public String getEmail() {
		return EMAIL;
	}
	
	@Override
	public void setEmail( String email ) {
		this.EMAIL = email;
	}
	
	@Override
	public String getSubject() {
		return SUBJECT;
	}

	@Override
	public void setSubject( String subject ) {
		this.SUBJECT = subject;
	}
	
	@Override
	public String getBody() {
		return BODY;
	}

	@Override
	public void setBody( String body ) {
		this.BODY = body;
	}
	
}