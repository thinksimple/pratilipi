package com.claymus.module.pagecontent;

import com.claymus.data.transfer.PageContent;

public interface PageContentProcessor<T extends PageContent> {

	String getHtml( T pageContent );
	
}
