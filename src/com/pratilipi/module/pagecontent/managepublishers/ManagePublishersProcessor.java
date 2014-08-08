package com.pratilipi.module.pagecontent.managepublishers;

import com.claymus.module.pagecontent.PageContentProcessor;

public class ManagePublishersProcessor extends PageContentProcessor<ManagePublishers> {

	@Override
	public String getHtml( ManagePublishers author ) {
		return "<script "
				+ "type='text/javascript' "
				+ "language='javascript' "
				+ "src='/pagecontent.managepublishers/pagecontent.managepublishers.nocache.js'"
				+ "></script>"
				+ "<div id='PageContent-ManagePublishers'></div>";
	}
	
}
