package com.claymus.module.websitewidget.header.gae;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.claymus.data.access.gae.WebsiteWidgetEntity;
import com.claymus.module.websitewidget.header.HeaderWidget;

@PersistenceCapable
public class HeaderWidgetEntity extends WebsiteWidgetEntity implements HeaderWidget {

	@Persistent( column = "X_COL_0" )
	private String title;
	
	@Persistent( column = "X_COL_1" )
	private String tagLine;

	
	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle( String title ) {
		this.title = title;
	}

	@Override
	public String getTagLine() {
		return tagLine;
	}

	@Override
	public void setTagLine( String tagLine ) {
		this.tagLine = tagLine;
	}

}
