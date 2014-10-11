package com.claymus.pagecontent.html;

import com.claymus.data.transfer.PageContent;

public interface HtmlContent extends PageContent {

	String getHtml();
	
	void setHtml( String html );
	
}
