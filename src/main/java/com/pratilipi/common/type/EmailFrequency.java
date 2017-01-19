package com.pratilipi.common.type;

import java.util.Date;
import java.util.concurrent.TimeUnit;

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

	
	public Date getNextSchedule( Date lastEmailDate ) {

		if( this == NEVER )
			return null;

		if( lastEmailDate == null )
			return new Date( new Date().getTime() + delayMillis );

		while( lastEmailDate.before( new Date() ) )
			lastEmailDate = new Date( lastEmailDate.getTime() + delayMillis );

		return lastEmailDate;

	}

}
