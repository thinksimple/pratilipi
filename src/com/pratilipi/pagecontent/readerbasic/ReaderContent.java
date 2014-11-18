package com.pratilipi.pagecontent.readerbasic;

import com.claymus.data.transfer.PageContent;
import com.pratilipi.commons.shared.PratilipiType;

public interface ReaderContent extends PageContent {

	PratilipiType getPratilipiType();
	
	void setPratilipiType( PratilipiType pratilipiType );

}
