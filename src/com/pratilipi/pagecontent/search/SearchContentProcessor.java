package com.pratilipi.pagecontent.search;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.pagecontent.PageContentProcessor;

public class SearchContentProcessor extends PageContentProcessor<SearchContent> {

	@Override
	public String generateHtml( SearchContent searchContent, HttpServletRequest request )
			throws UnexpectedServerException {
		
		//TODO: get list of pratilipis in search result.
		
		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();

		// Processing template
		return super.processTemplate( dataModel, getTemplateName() );
	}
	
}
