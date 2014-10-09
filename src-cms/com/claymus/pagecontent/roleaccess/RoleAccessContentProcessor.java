package com.claymus.pagecontent.roleaccess;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.client.UnexpectedServerException;
import com.claymus.module.pagecontent.PageContentFactory;
import com.claymus.module.pagecontent.PageContentProcessor;
import com.claymus.module.pagecontent.PageContentRegistry;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;

public class RoleAccessContentProcessor extends PageContentProcessor<RoleAccessContent> {

	@Override
	protected String generateHtml( RoleAccessContent genreContent, HttpServletRequest request )
			throws IOException, UnexpectedServerException {
		
		List<PageContentFactory> pageContentHelperList =
				PageContentRegistry.getPageContentHelperList();

		
		// Fetching Genre list
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "pageContentHelperList", pageContentHelperList );
		dataModel.put( "dataAccessor", dataAccessor );
		dataModel.put( "roleIdList", new String[]{ "guest", "member", "administrator" } );
		

		// Processing template
		String html = super.processTemplate( dataModel, getTemplateName() );

		dataAccessor.destroy();
		
		return html;
	}

}
