package com.claymus.module.websitewidget.header;

import com.claymus.data.transfer.WebsiteWidget;

public interface HeaderWidget extends WebsiteWidget {
	
	String getTitle();
	
	void setTitle( String title );
	
	String getTagLine();
	
	void setTagLine( String tagLine );
	
}
