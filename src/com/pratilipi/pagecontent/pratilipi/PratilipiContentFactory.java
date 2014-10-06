package com.pratilipi.pagecontent.pratilipi;

import com.claymus.module.pagecontent.PageContentFactory;
import com.pratilipi.pagecontent.pratilipi.gae.PratilipiContentEntity;

public class PratilipiContentFactory
		implements PageContentFactory<PratilipiContent, PratilipiContentProcessor> {
	
	public static PratilipiContent newPratilipiContent( Long pratilipiId ) {
		
		PratilipiContent pratilipiContent = new PratilipiContentEntity();
		pratilipiContent.setPratilipiId( pratilipiId );
		return pratilipiContent;
	}
	
}
