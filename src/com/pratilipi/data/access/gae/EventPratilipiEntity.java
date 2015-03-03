package com.pratilipi.data.access.gae;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.pratilipi.data.transfer.EventPratilipi;

@SuppressWarnings("serial")
@PersistenceCapable( table = "EVENT_PRATILIPI" )
public class EventPratilipiEntity implements EventPratilipi {

	@PrimaryKey
	@Persistent( column = "EVENT_PRATILIPI_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;
	
	@Persistent( column = "EVENT_ID" )
	private Long eventId;
	
	@Persistent( column = "PRATILIPI_ID" )
	private Long pratilipiId;

	@Persistent( column = "PRATICIPATION_DATE" )
	private Date praticipationDate;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId( Long id ) {
		this.id = id;
	}

	@Override
	public Long getEventId() {
		return eventId;
	}

	@Override
	public void setEventId( Long eventId ) {
		this.eventId = eventId;
	}

	@Override
	public Long getPratilipiId() {
		return pratilipiId;
	}

	@Override
	public void setPratilipiId( Long pratilipiId ) {
		this.pratilipiId = pratilipiId;
	}

	@Override
	public Date getPraticipationDate() {
		return praticipationDate;
	}

	@Override
	public void setPraticipationDate( Date praticipationDate ) {
		this.praticipationDate = praticipationDate;
	}
	
}
