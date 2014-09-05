package com.pratilipi.pagecontent.pratilipi;

import com.claymus.data.transfer.PageContent;
import com.pratilipi.commons.shared.PratilipiType;

public interface PratilipiContent extends PageContent {
	
	Long getPratilipiId();
	
	void setPratilipiId( Long pratilipiId );
	
	PratilipiType getPratilipiType();
	
	void setPratilipiType( PratilipiType pratilipiType );
	
}
