package com.pratilipi.common.type;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.pratilipi.data.type.User;

public enum EmailFrequency {

	IMMEDIATELY	( 0 ),
	DAILY		( TimeUnit.DAYS.toMillis( 1 ) ),
	WEEKLY		( TimeUnit.DAYS.toMillis( 7 ) ),
	MONTHLY		( TimeUnit.DAYS.toMillis( 30 ) ),
	NEVER		( -1 ),
	;
	
	
	private long delayMillis;
	
	private EmailFrequency( long delayMillis ) {
		this.delayMillis = delayMillis;
	}

	
	public Date getNextSchedule( User user ) {

		if( this == NEVER )
			return null;

		if( this == IMMEDIATELY )
			return new Date();

		Date lastEmailDate = user.getLastEmailedDate() != null ? user.getLastEmailedDate() : user.getLastUpdated();

		while( lastEmailDate.before( new Date() ) )
			lastEmailDate = new Date( lastEmailDate.getTime() + delayMillis );

		return lastEmailDate;

	}

}
