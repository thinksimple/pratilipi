package com.pratilipi.pagecontent.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.FreeMarkerUtil;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.pagecontent.PageContentProcessor;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.commons.server.GlobalSearch;

public class SearchContentProcessor extends PageContentProcessor<SearchContent> {

	@Override
	public String generateTitle( SearchContent searchContent, HttpServletRequest request ) {
		return "Search";
	}
	
	@Override
	public String generateHtml( SearchContent searchContent, HttpServletRequest request )
			throws UnexpectedServerException {
		
		String query = request.getParameter( "q" );
		String docType = request.getParameter( "dt" );

		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "pageUrl", request.getRequestURI() );
		dataModel.put( "query", query );
		dataModel.put( "docType", docType );
		
		if( ( query != null && !query.isEmpty() ) || ( docType != null && !docType.isEmpty() ) ) {
			GlobalSearch globalSearch = new GlobalSearch( request );
			globalSearch.setResultCount( 20 );
			
			List<IsSerializable> dataList = docType == null || docType.isEmpty()
					? globalSearch.search( query )
					: globalSearch.search( query, "docType:" + docType );

			dataModel.put( "dataList", dataList );
			dataModel.put( "cursor", globalSearch.getCursor() );
			dataModel.put( "resultCount", globalSearch.getResultCout() );
		}
		
		// Processing template
		return FreeMarkerUtil.processTemplate( dataModel, getTemplateName() );
	}
	
}
