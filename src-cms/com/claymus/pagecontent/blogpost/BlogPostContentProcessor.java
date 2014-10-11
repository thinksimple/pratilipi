package com.claymus.pagecontent.blogpost;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.client.InsufficientAccessException;
import com.claymus.commons.client.UnexpectedServerException;
import com.claymus.pagecontent.PageContentProcessor;

public class BlogPostContentProcessor extends PageContentProcessor<BlogPostContent> {

	@Override
	protected String generateHtml(
			BlogPostContent blogPostContent, HttpServletRequest request )
			throws InsufficientAccessException, UnexpectedServerException {
		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "blogPostContent", blogPostContent );
		dataModel.put( "showEditOptions", true );
		
		return super.processTemplate( dataModel, getTemplateName() );
	}

}
