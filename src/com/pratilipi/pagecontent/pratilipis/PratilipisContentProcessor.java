package com.pratilipi.pagecontent.pratilipis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.FreeMarkerUtil;
import com.claymus.commons.server.SerializationUtil;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataListCursorTuple;
import com.claymus.pagecontent.PageContentProcessor;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Language;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.service.shared.data.PratilipiData;

public class PratilipisContentProcessor extends PageContentProcessor<PratilipisContent> {

	@Override
	public String generateTitle( PratilipisContent pratilipisContent, HttpServletRequest request ) {
		if( pratilipisContent.getTitle() != null )
			return pratilipisContent.getTitle();
		
		PratilipiType pratilipiType = pratilipisContent.getPratilipiType();
		if( pratilipiType != null && pratilipisContent.getPublicDomain() != null && pratilipisContent.getPublicDomain() ) {
			return "Classic " + pratilipiType.getNamePlural();
			
		} else if( pratilipiType != null && pratilipisContent.getLanguageId() != null ) {
			DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
			Language language = dataAccessor.getLanguage( pratilipisContent.getLanguageId() );
			return language.getNameEn() + " " + pratilipiType.getNamePlural();

		} else if( pratilipiType != null ) {
			return pratilipiType.getNamePlural();
			
		} else {
			return null;
		}
	
	}

	@Override
	public String generateHtml( PratilipisContent pratilipisContent, HttpServletRequest request )
			throws UnexpectedServerException {
		
		PratilipiHelper pratilipiHelper = PratilipiHelper.get( request );
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		
		Map<String, Object> dataModel = new HashMap<>();

		List<PratilipiData> pratilipiDataList;
		if( pratilipisContent.getPratilipiIdList() != null ) {

			pratilipiDataList = pratilipiHelper.createPratilipiDataListFromIdList(
					pratilipisContent.getPratilipiIdList(),
					false, true, false );

		} else {

			DataListCursorTuple<Pratilipi> pratilipiListCursorTuple =
					dataAccessor.getPratilipiList( pratilipisContent.toFilter(), null, 20 );
			pratilipiDataList = pratilipiHelper.createPratilipiDataList(
					pratilipiListCursorTuple.getDataList(), false, true, false );
		
			dataModel.put( "pratilipiFilterEncodedStr", SerializationUtil.encode( pratilipisContent.toFilter() ) );
		
		}
		

		// Creating data model required for template processing
		dataModel.put( "title", getTitle( pratilipisContent, request ) );
		dataModel.put( "pratilipiDataList", pratilipiDataList );
		

		// Processing template
		return FreeMarkerUtil.processTemplate( dataModel, getTemplateName() );
	}
	
}
