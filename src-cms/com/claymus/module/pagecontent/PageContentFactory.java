package com.claymus.module.pagecontent;

import com.claymus.commons.server.Access;
import com.claymus.data.transfer.PageContent;

public interface PageContentFactory<P extends PageContent, Q extends PageContentProcessor<P>> {
	
	String getModuleName();
	
	Double getModuleVersion();

	Access[] getAccessList();
	
}
