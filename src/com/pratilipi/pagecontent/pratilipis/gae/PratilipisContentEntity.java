package com.pratilipi.pagecontent.pratilipis.gae;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.claymus.data.access.gae.PageContentEntity;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.pagecontent.pratilipis.PratilipisContent;

@PersistenceCapable
public class PratilipisContentEntity extends PageContentEntity
		implements PratilipisContent {
	
	@Persistent( column = "X_COL_0" )
	private PratilipiType pratilipiType;


	@Override
	public PratilipiType getPratilipiType() {
		return pratilipiType;
	}

	@Override
	public void setPratilipiType( PratilipiType pratilipiType ) {
		this.pratilipiType = pratilipiType;
	}
	
}
