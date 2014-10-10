package com.claymus.pagecontent.roleaccess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.client.InsufficientAccessException;
import com.claymus.commons.client.UnexpectedServerException;
import com.claymus.module.pagecontent.PageContentFactory;
import com.claymus.module.pagecontent.PageContentProcessor;
import com.claymus.module.pagecontent.PageContentRegistry;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;

public class RoleAccessContentProcessor extends PageContentProcessor<RoleAccessContent> {

	private String[] roleList = new String[]{ "guest", "member", "administrator" };
	
	
	@Override
	protected String generateHtml(
			RoleAccessContent roleAccessContent, HttpServletRequest request )
			throws InsufficientAccessException, UnexpectedServerException {
		
		if( ! RoleAccessContentHelper.hasRequestAccessToList( request ) )
			throw new InsufficientAccessException();
		
		boolean showUpdateOptions =
				RoleAccessContentHelper.hasRequestAccessToUpdate( request );

		List<PageContentFactory> pageContentHelperList =
				PageContentRegistry.getPageContentHelperList();
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "pageContentHelperList", pageContentHelperList );
		dataModel.put( "roleIdList", roleList );
		dataModel.put( "dataAccessor", dataAccessor );
		dataModel.put( "showUpdateOptions", showUpdateOptions );
		

		// Processing template
		String html = super.processTemplate( dataModel, getTemplateName() );

		dataAccessor.destroy();
		
		return html;
	}

}
