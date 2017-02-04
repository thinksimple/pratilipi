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

		if( this == IMMEDIATELY )
			return new Date();

		if( lastEmailDate == null )
			lastEmailDate = new Date( 1483209000000L ); // 2017-01-01 00:00:00 IST

		while( lastEmailDate.before( new Date() ) )
			lastEmailDate = new Date( lastEmailDate.getTime() + delayMillis );

		return lastEmailDate;

	}

}
