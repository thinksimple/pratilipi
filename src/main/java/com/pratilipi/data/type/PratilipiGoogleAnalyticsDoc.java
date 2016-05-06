package com.pratilipi.data.type;

import java.util.Date;

public interface PratilipiGoogleAnalyticsDoc {
	
	long getTotalPageViews();
	
	void setPageViews( int year, int month, int day, int count );

	
	long getTotalReadPageViews();

	void setReadPageViews( int year, int month, int day, int count );
	
	
	Date getLastUpdated();
	
}
