package com.pratilipi.pagecontent.pratilipis;

import com.claymus.commons.server.Access;
import com.claymus.pagecontent.PageContentHelper;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.pagecontent.pratilipis.gae.PratilipisContentEntity;
import com.pratilipi.pagecontent.pratilipis.shared.PratilipisContentData;

public class PratilipisContentFactory extends PageContentHelper<
		PratilipisContent,
		PratilipisContentData,
		PratilipisContentProcessor> {
	
	@Override
	public String getModuleName() {
		return "Pratilipi List";
	}

	@Override
	public Double getModuleVersion() {
		return 2.0;
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
