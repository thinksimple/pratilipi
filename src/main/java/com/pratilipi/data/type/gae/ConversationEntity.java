package com.pratilipi.data.type.gae;

import java.util.Date;
import java.util.UUID;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.pratilipi.data.type.Conversation;

@Cache
@Entity( name = "CONVERSATION" )
public class ConversationEntity implements Conversation {

	@Id
	private String CONVERSATION_ID; // USER_ID_1-USER_ID_2 or UUID
	
	private String TITLE;
	
	private Date CREATION_DATE;
	
	private Date LAST_UPDATED;
	
	
	public ConversationEntity() {
		this.CONVERSATION_ID = UUID.randomUUID().toString();
	}
	
	public ConversationEntity( String id ) {
		this.CONVERSATION_ID = id;
	}
	
	public ConversationEntity( Long userId_1, Long userId_2 ) {
		if( userId_1 < userId_2 )
			this.CONVERSATION_ID = userId_1 + "-" + userId_2;
		else
			this.CONVERSATION_ID = userId_2 + "-" + userId_1;
	}
	
	
	@Override
	public String getId() {
		return CONVERSATION_ID;	
	}
	
	public void setId( String id ) {
		this.CONVERSATION_ID = id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Key<T> getKey() {
		return getId() == null ? null : (Key<T>) Key.create( getClass(), getId() );
	}
	
	@Override
	public <T> void setKey( Key<T> key ) {
		this.CONVERSATION_ID = key.getName();
	}

	
	@Override
	public String getTitle() {
		return TITLE;
	}
	
	@Override
	public void setTitle( String title ) {
		this.TITLE = title;
	}

	@Override
	public Date getCreationDate() {
		return CREATION_DATE;
	}
	
	@Override
	public void setCreationDate( Date creationDate ) {
		this.CREATION_DATE = creationDate;
	}
	
	@Override
	public Date getLastUpdated() {
		return LAST_UPDATED;
	}
	
	@Override
	public void setLastUpdated( Date lastUpdated ) {
		this.LAST_UPDATED = lastUpdated;
	}
	
}