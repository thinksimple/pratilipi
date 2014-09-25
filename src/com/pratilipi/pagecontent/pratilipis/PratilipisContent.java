package com.pratilipi.pagecontent.pratilipis;

import com.claymus.data.transfer.PageContent;
import com.pratilipi.commons.shared.PratilipiFilter;
import com.pratilipi.commons.shared.PratilipiType;

public interface PratilipisContent extends PageContent {

	PratilipiType getPratilipiType();

	void setPratilipiType( PratilipiType pratilipiType );

	Boolean getPublicDomain();

	void setPublicDomain( Boolean isPublicDomain );
	
	Long getLanguageId();

	void setLanguageId( Long languageId );
	
	PratilipiFilter toFilter();

}
