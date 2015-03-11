package com.pratilipi.pagecontent.pratilipis;

import java.util.List;

import com.claymus.data.transfer.PageContent;
import com.pratilipi.commons.shared.PratilipiFilter;
import com.pratilipi.commons.shared.PratilipiState;
import com.pratilipi.commons.shared.PratilipiType;

public interface PratilipisContent extends PageContent {
	
	String getTitle();
	
	void setTitle( String title );
	
	List<Long> getPratilipiIdList();

	void setPratilipiIdList( List<Long> pratilipIdList );
	

	PratilipiType getPratilipiType();

	void setPratilipiType( PratilipiType pratilipiType );

	Boolean getPublicDomain();

	void setPublicDomain( Boolean isPublicDomain );
	
	Long getLanguageId();

	void setLanguageId( Long languageId );
	
	PratilipiState getPratilipiState();

	void setPratilipiState( PratilipiState state );
	
	PratilipiFilter toFilter();

}
