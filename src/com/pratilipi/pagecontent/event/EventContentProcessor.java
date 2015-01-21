package com.pratilipi.pagecontent.event;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.pagecontent.PageContentProcessor;

public class EventContentProcessor extends PageContentProcessor<EventContent> {

	@Override
	public String generateTitle( EventContent eventContent, HttpServletRequest request ) {
		return null; // TODO: implementation
	}
	
	@Override
	public String generateHtml(
			EventContent eventContent,
			HttpServletRequest request ) throws UnexpectedServerException {
		
		return null; // TODO: implementation
	}
	
}
