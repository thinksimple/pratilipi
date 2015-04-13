package com.pratilipi.pagecontent.pratilipis;

import com.claymus.commons.server.Access;
import com.claymus.pagecontent.PageContentHelper;
import com.pratilipi.commons.shared.PratilipiState;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.pagecontent.pratilipis.gae.PratilipisContentEntity;
import com.pratilipi.pagecontent.pratilipis.shared.PratilipisContentData;

public class PratilipisContentHelper extends PageContentHelper<
		PratilipisContent,
		PratilipisContentData,
		PratilipisContentProcessor> {
	
	@Override
	public String getModuleName() {
		return "Pratilipi List";
	}

	@Override
	public Double getModuleVersion() {
		return 4.0;
	}

	@Override
	public Access[] getAccessList() {
		return new Access[] {};
	}
	
	
	public static PratilipisContent newPratilipisContent() {
		return new PratilipisContentEntity();
	}
	
	public static PratilipisContent newPratilipisContent(
			PratilipiType type, PratilipiState state ) {
		
		return newPratilipisContent( type, null, state );
	}
	
	public static PratilipisContent newPratilipisContent(
			PratilipiType type, Long languageId, PratilipiState state ) {
		
		PratilipisContent pratilipisContent = new PratilipisContentEntity();
		pratilipisContent.setPratilipiType( type );
		pratilipisContent.setLanguageId( languageId );
		pratilipisContent.setPratilipiState( state );
		return pratilipisContent;
	}

}
