package com.pratilipi.pagecontent.pratilipi.gae;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.claymus.data.access.gae.PageContentEntity;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.pagecontent.pratilipi.PratilipiContent;

@SuppressWarnings("serial")
@PersistenceCapable
public class PratilipiContentEntity extends PageContentEntity
		implements PratilipiContent {

	@Persistent( column = "X_COL_0" )
	private Long pratilipiId;

	
	@Persistent( column = "X_COL_1" )
	private PratilipiType pratilipiType;

	
	@Override
	public Long getPratilipiId() {
		return pratilipiId;
	}

	@Override
	public void setPratilipiId( Long pratilipiId ) {
		this.pratilipiId = pratilipiId;
	} 

	@Override
	public PratilipiType getPratilipiType() {
		return pratilipiType;
	}

	@Override
	public void setPratilipiType( PratilipiType pratilipiType ) {
		this.pratilipiType = pratilipiType;
	} 

}
