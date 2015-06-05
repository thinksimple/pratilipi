package com.pratilipi.pagecontent.pratilipi.gae;

import javax.jdo.annotations.PersistenceCapable;

import com.claymus.data.access.gae.PageContentEntity;
import com.pratilipi.pagecontent.pratilipi.PratilipiContent;

@SuppressWarnings("serial")
@PersistenceCapable
public class PratilipiContentEntity extends PageContentEntity
		implements PratilipiContent {

	public PratilipiContentEntity( Long pratilipiId ) {
		setId( pratilipiId );
	}

}
