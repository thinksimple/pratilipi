package com.pratilipi.pagecontent.pratilipis;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.module.pagecontent.PageContentProcessor;

public class PratilipisContentProcessor extends PageContentProcessor<PratilipisContent> {

	public static final String ACCESS_ID_PRATILIPI_LIST = "pratilipi_list";
	public static final String ACCESS_ID_PRATILIPI_READ_META_DATA = "pratilipi_read_meta_data";
	public static final String ACCESS_ID_PRATILIPI_ADD = "pratilipi_add";
	public static final String ACCESS_ID_PRATILIPI_UPDATE = "pratilipi_update";
	
	
	@Override
	public String getHtml( PratilipisContent pratilipisContent,
			HttpServletRequest request, HttpServletResponse response ) {
		
		ClaymusHelper claymusHelper = new ClaymusHelper( request );
		@SuppressWarnings("unused")
		boolean showMetaData =
				claymusHelper.hasUserAccess( ACCESS_ID_PRATILIPI_READ_META_DATA, false );
		boolean showAddOption =
				claymusHelper.hasUserAccess( ACCESS_ID_PRATILIPI_ADD, false );

		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "pratilipiType", pratilipisContent.getPratilipiType().getName() );
		dataModel.put( "showAddOption", showAddOption );
		

		// Processing template
		return super.processTemplate( dataModel, getTemplateName() );
	}
	
	protected String getTemplateName() {
		return "com/pratilipi/pagecontent/pratilipis/PratilipisContent.ftl";
	}
	
}
