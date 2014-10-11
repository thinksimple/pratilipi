package com.pratilipi.pagecontent.pratilipi;

import com.claymus.commons.server.Access;
import com.claymus.pagecontent.PageContentFactory;
import com.pratilipi.pagecontent.pratilipi.gae.PratilipiContentEntity;
import com.pratilipi.pagecontent.pratilipi.shared.PratilipiContentData;

public class PratilipiContentFactory extends PageContentFactory<
		PratilipiContent,
		PratilipiContentData,
		PratilipiContentProcessor> {
	
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
