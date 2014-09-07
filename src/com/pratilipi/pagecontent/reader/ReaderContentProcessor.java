package com.pratilipi.pagecontent.reader;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.module.pagecontent.PageContentProcessor;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Pratilipi;

public class ReaderContentProcessor extends PageContentProcessor<ReaderContent> {

	@Override
	public String getHtml( ReaderContent pratilipiContent,
			HttpServletRequest request, HttpServletResponse response ) {

		PratilipiType pratilipiType = pratilipiContent.getPratilipiType();

		String url = request.getRequestURI();
		String pratilipiIdStr = url.substring( pratilipiType.getReaderPageUrl().length() );
		String pageNoStr = request.getParameter( "page" );

		Long pratilipiId =Long.parseLong( pratilipiIdStr );
		int pageNo = pageNoStr == null ? 1 : Integer.parseInt( pageNoStr );
		
		
		// Fetching Pratilipi
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId,
				pratilipiContent.getPratilipiType() );
		dataAccessor.destroy();
		

		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "pageContentJpegUrl",
				pratilipiType.getContentJpegUrl() + pratilipiId + "/" + pageNo );
		if( pageNo > 1 )
			dataModel.put( "previousPageUrl",
					pratilipiType.getReaderPageUrl() + pratilipiIdStr + "&page=" + ( pageNo -1 ) );
		if( pageNo < pratilipi.getPageCount() )
			dataModel.put( "nextPageUrl",
					pratilipiType.getReaderPageUrl() + pratilipiIdStr + "&page=" + ( pageNo +1 ) );


		return super.processTemplate(
				dataModel,
				"com/pratilipi/pagecontent/reader/ReaderContent.ftl" );
	}
	
}
