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

	
	public Date getNextSchedule( Date lastMailDate ) {
		
		if( this == NEVER )
			return null;

		while( lastMailDate.before( new Date() ) )
			lastMailDate = new Date( lastMailDate.getTime() + delayMillis );
		
		return lastMailDate;
	
	}
	
}
