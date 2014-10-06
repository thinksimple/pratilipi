package com.pratilipi.pagecontent.pratilipi.gae;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.claymus.data.access.gae.PageContentEntity;
import com.pratilipi.pagecontent.pratilipi.PratilipiContent;

@SuppressWarnings("serial")
@PersistenceCapable
public class PratilipiContentEntity extends PageContentEntity
		implements PratilipiContent {

	@Persistent( column = "X_COL_0" )
	private Long pratilipiId;

	
	@Override
	public Long getPratilipiId() {
		return pratilipiId;
	}

	@Override
	public void setPratilipiId( Long pratilipiId ) {
		this.pratilipiId = pratilipiId;
	} 

}
