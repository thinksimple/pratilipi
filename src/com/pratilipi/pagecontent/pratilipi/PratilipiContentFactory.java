package com.pratilipi.pagecontent.pratilipi;

import com.claymus.commons.server.Access;
import com.claymus.module.pagecontent.PageContentFactory;
import com.pratilipi.pagecontent.pratilipi.gae.PratilipiContentEntity;

public class PratilipiContentFactory
		implements PageContentFactory<PratilipiContent, PratilipiContentProcessor> {
	
	@Override
	public String getModuleName() {
		return "Pratilipi Content";
	}

	@Override
	public Double getModuleVersion() {
		return 2.0;
	}

	@Override
	public Access[] getAccessList() {
		return new Access[] {};
	}
	
	
	public static PratilipiContent newPratilipiContent( Long pratilipiId ) {
		
		PratilipiContent pratilipiContent = new PratilipiContentEntity();
		pratilipiContent.setPratilipiId( pratilipiId );
		return pratilipiContent;
	}
	
}
