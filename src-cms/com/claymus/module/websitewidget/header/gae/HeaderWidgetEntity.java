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

	@Persistent( column = "X_COL_2" )
	private String[][] leftLinks;

	@Persistent( column = "X_COL_3" )
	private String[][] rightLinks;

	
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
	
	@Override
	public String[][] getLeftLinks() {
		return leftLinks;
	}

	@Override
	public void setLeftLinks( String[][] leftLinks ) {
		this.leftLinks = leftLinks;
	}

	@Override
	public String[][] getRightLinks() {
		return rightLinks;
	}

	@Override
	public void setRightLinks( String[][] rightLinks ) {
		this.rightLinks = rightLinks;
	}

}
