package com.pratilipi.data.transfer;

import java.io.Serializable;
import java.util.Date;

public interface EventPratilipi extends Serializable{

	public Long getId();

	public void setId( Long id ) ;

	public Long getEventId();

	public void setEventId( Long eventId );

	public Long getPratilipiId();

	public void setPratilipiId( Long pratilipiId );

	public Date getPraticipationDate();

	public void setPraticipationDate( Date praticipationDate ) ;
	
}
