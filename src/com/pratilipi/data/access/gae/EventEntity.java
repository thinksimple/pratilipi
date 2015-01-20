package com.pratilipi.data.access.gae;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.pratilipi.data.transfer.Event;

@SuppressWarnings("serial")
@PersistenceCapable( table = "EVENT" )
public class EventEntity implements Event {

	@PrimaryKey
	@Persistent( column = "EVENT_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;
	
	@Persistent( column = "USER_ID" )
	private Long userId;
	
	@Persistent( column = "LANGUAGE_ID" )
	private Long languageId;

	@Persistent( column = "NAME" )
	private String name;
	
	@Persistent( column = "NAME_EN" )
	private String nameEn;

	@Persistent( column = "EMAIL" )
	private String email;
	
	@Persistent( column = "CREATION_DATE" )
	private Date creationDate;

	@Persistent( column = "START_DATE" )
	private Date startDate;

	@Persistent( column = "END_DATE" )
	private Date endDate;


	@Override
	public Long getId() {
		return id;
	}

	@Override
	public Long getUserId() {
		return userId;
	}

	@Override
	public void setUserId( Long userId ) {
		this.userId = userId;
	}

	@Override
	public Long getLanguageId() {
		return languageId;
	}

	@Override
	public void setLanguageId( Long languageId ) {
		this.languageId = languageId;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName( String name ) {
		this.name = name;
	}

	@Override
	public String getNameEn() {
		return nameEn;
	}

	@Override
	public void setNameEn( String nameEn ) {
		this.nameEn = nameEn;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public void setEmail( String email ) {
		this.email = email;
	}

	@Override
	public Date getCreationDate() {
		return creationDate;
	}

	@Override
	public void setCreationDate( Date creationDate ) {
		this.creationDate = creationDate;
	}

	@Override
	public Date getStartDate() {
		return startDate;
	}

	@Override
	public void setStartDate( Date startDate ) {
		this.startDate = startDate;
	}

	@Override
	public Date getEndDate() {
		return endDate;
	}

	@Override
	public void setEndDate( Date endDate ) {
		this.endDate = endDate;
	}
	
}
