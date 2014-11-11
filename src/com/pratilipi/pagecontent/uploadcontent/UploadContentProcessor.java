package com.pratilipi.pagecontent.uploadcontent;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.SerializationUtil;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.pagecontent.PageContentProcessor;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.service.shared.data.PratilipiData;

public class UploadContentProcessor extends PageContentProcessor<UploadContent> {

	@Override
	public String generateHtml( UploadContent uploadContent, HttpServletRequest request )
			throws UnexpectedServerException {

		PratilipiHelper pratilipiHelper = PratilipiHelper.get( request );
		
		PratilipiData pratilipiData = pratilipiHelper.createPratilipiData( Long.parseLong( request.getParameter( "id" ))); 
		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "pratilipiDataEncodedStr", SerializationUtil.encode( pratilipiData ) );
		// Processing template
		return super.processTemplate( dataModel, getTemplateName() );
	}
	
}
