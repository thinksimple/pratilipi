package com.pratilipi.pagecontent.pratilipis;

import com.claymus.module.pagecontent.PageContentFactory;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.pagecontent.pratilipis.gae.PratilipisContentEntity;

public class PratilipisContentFactory
		implements PageContentFactory<PratilipisContent, PratilipisContentProcessor> {
	
	public static PratilipisContent newPratilipisContent( PratilipiType pratilipiType ) {
		PratilipisContent pratilipisContent = new PratilipisContentEntity();
		pratilipisContent.setPratilipiType( pratilipiType );
		return pratilipisContent;
	}
	
}
