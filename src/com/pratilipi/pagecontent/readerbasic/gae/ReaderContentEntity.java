package com.pratilipi.pagecontent.readerbasic.gae;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.claymus.data.access.gae.PageContentEntity;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.pagecontent.readerbasic.ReaderContent;

@SuppressWarnings("serial")
@PersistenceCapable
public class ReaderContentEntity extends PageContentEntity
		implements ReaderContent {
	
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
