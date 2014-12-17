package com.pratilipi.pagecontent.publisher;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.server.FreeMarkerUtil;
import com.claymus.commons.shared.AccessTokenType;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataListCursorTuple;
import com.claymus.data.transfer.AccessToken;
import com.claymus.pagecontent.PageContentProcessor;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.commons.shared.PratilipiFilter;
import com.pratilipi.commons.shared.PratilipiState;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Publisher;
import com.pratilipi.service.shared.data.PratilipiData;
import com.pratilipi.service.shared.data.PublisherData;

public class PublisherContentProcessor extends PageContentProcessor<PublisherContent> {

	@Override
	public String generateTitle( PublisherContent publisherContent, HttpServletRequest request ) {
		PublisherData publisherData = PratilipiHelper.get( request ).createPublisherData( publisherContent.getId() );
		return publisherData.getName() + " (" + publisherData.getNameEn() + ")";
	}
	
	@Override
	public String generateHtml(
			PublisherContent publisherContent,
			HttpServletRequest request ) throws UnexpectedServerException {
		
		PratilipiHelper pratilipiHelper = PratilipiHelper.get( request );
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		AccessToken accessToken = (AccessToken) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN );
		
		Publisher publisher = dataAccessor.getPublisher( publisherContent.getId() );
		PublisherData publisherData = pratilipiHelper.createPublisherData( publisher, null );
		

		List<Long> pratilipiIdList = new LinkedList<>();
		
		
		// Free Pratilipis by this Publisher
		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		pratilipiFilter.setPublisherId( publisher.getId() );
		pratilipiFilter.setState( PratilipiState.PUBLISHED );

		DataListCursorTuple<Long> pratilipiIdListCursorTuple =
				dataAccessor.getPratilipiIdList( pratilipiFilter, null, 1000 ); 
		pratilipiIdList.addAll( pratilipiIdListCursorTuple.getDataList() );

		
		if( accessToken.getType() == AccessTokenType.PUBLISHER && (long) publisher.getId() == (long) accessToken.getPublisherId() ) {
			
			// All paid books by this Publisher
			pratilipiFilter.setState( PratilipiState.PUBLISHED_PAID );
			pratilipiIdListCursorTuple =
					dataAccessor.getPratilipiIdList( pratilipiFilter, null, 1000 ); 
			pratilipiIdList.addAll( pratilipiIdListCursorTuple.getDataList() );
			
		} else if( accessToken.getType() == AccessTokenType.USER
				|| ( accessToken.getType() == AccessTokenType.USER_PUBLISHER && (long) publisher.getId() == (long) accessToken.getPublisherId() ) ) {
			
			// Paid books by this Publisher, purchased by this User
			pratilipiFilter.setState( PratilipiState.PUBLISHED_PAID );
			pratilipiIdListCursorTuple =
					dataAccessor.getPratilipiIdList( pratilipiFilter, null, 1000 ); 
			
			List<Long> purchasedPratilipiIdList =
					dataAccessor.getPurchaseList( accessToken.getUserId() );
			for( Long pratilipiId : pratilipiIdListCursorTuple.getDataList() ) {
				if( purchasedPratilipiIdList.contains( pratilipiId ) )
					pratilipiIdList.add( pratilipiId );
			}
		}
	
		
		List<PratilipiData> pratilipiDataList =
				pratilipiHelper.createPratilipiDataListFromIdList(
						pratilipiIdList, false, false, false );
		
		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "timeZone", pratilipiHelper.getCurrentUserTimeZone() );
		dataModel.put( "publisherData", publisherData );
		dataModel.put( "pratilipiDataList", pratilipiDataList );
		dataModel.put( "readerRetUrl", publisherData.getPageUrlAlias() );
		dataModel.put( "domain", ClaymusHelper.getSystemProperty( "domain" ) );
		
		
		// Processing template
		return FreeMarkerUtil.processTemplate( dataModel, getTemplateName() );
	}
	
}
