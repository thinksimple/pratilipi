package com.pratilipi.pagecontent.pratilipis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.data.access.DataListCursorTuple;
import com.claymus.module.pagecontent.PageContentProcessor;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.commons.shared.PratilipiFilter;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Language;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.service.shared.data.PratilipiData;

public class PratilipisContentProcessor extends PageContentProcessor<PratilipisContent> {

	public static final String ACCESS_ID_PRATILIPI_LIST = "pratilipi_list";
	public static final String ACCESS_ID_PRATILIPI_READ_META_DATA = "pratilipi_read_meta_data";
	public static final String ACCESS_ID_PRATILIPI_ADD = "pratilipi_add";
	public static final String ACCESS_ID_PRATILIPI_UPDATE = "pratilipi_update";
	
	
	@Override
	public String getHtml( PratilipisContent pratilipisContent,
			HttpServletRequest request, HttpServletResponse response ) {
		
		PratilipiHelper pratilipiHelper = PratilipiHelper.get( request );
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		
		@SuppressWarnings("unused")
		boolean showMetaData =
				pratilipiHelper.hasUserAccess( ACCESS_ID_PRATILIPI_READ_META_DATA, false );
		boolean showAddOption =
				pratilipiHelper.hasUserAccess( ACCESS_ID_PRATILIPI_ADD, false );

		
		PratilipiType pratilipiType = pratilipisContent.getPratilipiType();
		PratilipiFilter pratilipiFilter = pratilipisContent.toFilter();
		
		DataListCursorTuple<Pratilipi> pratilipiListCursorTuple =
				dataAccessor.getPratilipiList( pratilipiFilter, null, 20 );
		List<PratilipiData> pratilipiDataList = new ArrayList<>( 20 );
		for( Pratilipi pratilipi : pratilipiListCursorTuple.getDataList() ) {
			Language language = dataAccessor.getLanguage( pratilipi.getLanguageId() );
			Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
			
			pratilipiDataList.add( pratilipiHelper.createPratilipiData( pratilipi, language, author ) );
		}

		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "pratilipiType", pratilipiType.getName() );
		dataModel.put( "pratilipisType", pratilipiType.getNamePlural() );
		if( pratilipisContent.getPublicDomain() != null && pratilipisContent.getPublicDomain() ) {
			dataModel.put( "pratilipisType", "Classic " + dataModel.get( "pratilipisType" ) );
			
		} else if( pratilipisContent.getLanguageId() != null ) {
			Language language = dataAccessor.getLanguage( pratilipisContent.getLanguageId() );
			dataModel.put( "pratilipisType",  language.getNameEn() + " " + dataModel.get( "pratilipisType" ) );

		}
		dataModel.put( "pratilipiFilters", pratilipiFilter.toString() );
		dataModel.put( "pratilipiDataList", pratilipiDataList );
		dataModel.put( "showAddOption", showAddOption );
		

		dataAccessor.destroy();
		
		
		// Processing template
		return super.processTemplate( dataModel, getTemplateName() );
	}
	
	protected String getTemplateName() {
		return "com/pratilipi/pagecontent/pratilipis/PratilipisContent.ftl";
	}
	
}
