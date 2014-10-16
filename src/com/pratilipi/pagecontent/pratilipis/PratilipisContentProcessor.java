package com.pratilipi.pagecontent.pratilipis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataListCursorTuple;
import com.claymus.pagecontent.PageContentProcessor;
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

	@Override
	public String generateHtml( PratilipisContent pratilipisContent, HttpServletRequest request )
			throws UnexpectedServerException {
		
		PratilipiHelper pratilipiHelper = PratilipiHelper.get( request );
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		
		PratilipiType pratilipiType = pratilipisContent.getPratilipiType();
		PratilipiFilter pratilipiFilter = pratilipisContent.toFilter();
		
		DataListCursorTuple<Pratilipi> pratilipiListCursorTuple =
				dataAccessor.getPratilipiList( pratilipiFilter, null, 25 );
		List<PratilipiData> pratilipiDataList = new ArrayList<>( 25 );
		for( Pratilipi pratilipi : pratilipiListCursorTuple.getDataList() ) {
			Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
			pratilipiDataList.add(
					pratilipiHelper.createPratilipiData( pratilipi, null, author, null ) );
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
		

		dataAccessor.destroy();
		
		
		// Processing template
		return super.processTemplate( dataModel, getTemplateName() );
	}
	
}
