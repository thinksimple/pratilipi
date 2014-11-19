package com.pratilipi.pagecontent.reader;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.pagecontent.PageContentProcessor;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.commons.shared.PratilipiState;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;
import com.pratilipi.service.shared.data.PratilipiData;

public class ReaderContentProcessor extends PageContentProcessor<ReaderContent> {

	@Override
	public String generateHtml( ReaderContent readerContent, HttpServletRequest request )
			throws InsufficientAccessException, UnexpectedServerException {

		String pratilipiIdStr = request.getParameter( "id" );
		Long pratilipiId = Long.parseLong( pratilipiIdStr );

		PratilipiHelper pratilipiHelper = PratilipiHelper.get( request );
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );

		
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
		boolean showEditOption = PratilipiContentHelper
				.hasRequestAccessToUpdatePratilipiData( request, pratilipi );

		if( pratilipi.getState() != PratilipiState.PUBLISHED && !showEditOption )
			throw new InsufficientAccessException();

		
		Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
		PratilipiData pratilipiData =
				pratilipiHelper.createPratilipiData( pratilipi, null, author, null );

		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "pratilipiData", pratilipiData );

		return super.processTemplate( dataModel, getTemplateName() );
	}
	
}
