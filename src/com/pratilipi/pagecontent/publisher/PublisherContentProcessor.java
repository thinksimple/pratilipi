package com.pratilipi.pagecontent.publisher;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.server.FreeMarkerUtil;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataListCursorTuple;
import com.claymus.pagecontent.PageContentProcessor;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.commons.shared.PratilipiFilter;
import com.pratilipi.commons.shared.PratilipiState;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.service.shared.data.PratilipiData;
import com.pratilipi.service.shared.data.PublisherData;

public class PublisherContentProcessor extends PageContentProcessor<PublisherContent> {

	@Override
	public String generateTitle( PublisherContent publisherContent, HttpServletRequest request ) {
		PublisherData authorData = PratilipiHelper.get( request ).createPublisherData( publisherContent.getId() );
		return authorData.getName() + " (" + authorData.getNameEn() + ")";
	}
	
	@Override
	public String generateHtml(
			PublisherContent publisherContent,
			HttpServletRequest request ) throws UnexpectedServerException {
		
		PratilipiHelper pratilipiHelper = PratilipiHelper.get( request );
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		
		
		Long userId = pratilipiHelper.getCurrentUserId();
		Long publisherId = publisherContent.getId();


		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		pratilipiFilter.setPublisherId( publisherId );
		pratilipiFilter.setState( PratilipiState.PUBLISHED );

		DataListCursorTuple<Long> publisherPratilipiIdListCursorTuple =
				dataAccessor.getPratilipiIdList( pratilipiFilter, null, 1000 );  

		
		List<Long> publisherPratilipiIdList =
				publisherPratilipiIdListCursorTuple.getDataList();
		List<Long> purchasedPratilipiIdList =
				dataAccessor.getPurchaseList( userId );
		
		
		// List of books by "publisherId", purchased by "userId"
		List<Long> pratilipiIdList = new LinkedList<>();
		for( Long pratilipiId : publisherPratilipiIdList ) {
			if( purchasedPratilipiIdList.contains( pratilipiId ) )
				pratilipiIdList.add( pratilipiId );
		}
		
		
		List<PratilipiData> pratilipiDataList =
				pratilipiHelper.createPratilipiDataListFromIdList(
						pratilipiIdList, false, false, false );
		
		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "timeZone", pratilipiHelper.getCurrentUserTimeZone() );
		dataModel.put( "pratilipiDataList", pratilipiDataList );
		dataModel.put( "domain", ClaymusHelper.getSystemProperty( "domain" ) );
		
		
		// Processing template
		return FreeMarkerUtil.processTemplate( dataModel, getTemplateName() );
	}
	
}
