package com.pratilipi.module.pagecontent.managepublishers;

import javax.servlet.http.HttpServletRequest;

import com.claymus.pagecontent.PageContentProcessor;

public class ManagePublishersProcessor extends PageContentProcessor<ManagePublishers> {

	@Override
	protected String generateHtml( ManagePublishers author, HttpServletRequest request ) {
		
		return "<script "
				+ "type='text/javascript' "
				+ "language='javascript' "
				+ "src='/pagecontent.managepublishers/pagecontent.managepublishers.nocache.js'"
				+ "></script>"
				+ "<div id='PageContent-ManagePublishers'></div>";
	}
	
}
