package com.pratilipi.pagecontent.pratilipis;

import com.claymus.commons.server.Access;
import com.claymus.module.pagecontent.PageContentFactory;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.pagecontent.pratilipis.gae.PratilipisContentEntity;

public class PratilipisContentFactory
		implements PageContentFactory<PratilipisContent, PratilipisContentProcessor> {
	
	@Override
	public String getModuleName() {
		return "Pratilipi List";
	}

	@Override
	public Access[] getAccessList() {
		return new Access[] {};
	}
	
	
	public static PratilipisContent newPratilipisContent( PratilipiType pratilipiType ) {
		return newPratilipisContent( pratilipiType, (Boolean) null );
	}
	
	public static PratilipisContent newPratilipisContent(
			PratilipiType pratilipiType, Boolean publicDomain ) {
		
		PratilipisContent pratilipisContent = new PratilipisContentEntity();
		pratilipisContent.setPratilipiType( pratilipiType );
		pratilipisContent.setPublicDomain( publicDomain );
		return pratilipisContent;
	}

	public static PratilipisContent newPratilipisContent(
			PratilipiType pratilipiType, Long languageId ) {
		
		PratilipisContent pratilipisContent = new PratilipisContentEntity();
		pratilipisContent.setPratilipiType( pratilipiType );
		pratilipisContent.setLanguageId( languageId );
		return pratilipisContent;
	}

}
