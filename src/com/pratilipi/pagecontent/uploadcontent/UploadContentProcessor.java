package com.pratilipi.pagecontent.uploadcontent;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.pagecontent.PageContentProcessor;

public class UploadContentProcessor extends PageContentProcessor<UploadContent> {

	@Override
	public String generateHtml( UploadContent uploadContent, HttpServletRequest request )
			throws UnexpectedServerException {

		//TODO : 
		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();

		// Processing template
		return super.processTemplate( dataModel, getTemplateName() );
	}
	
}
