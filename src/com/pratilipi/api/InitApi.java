package com.pratilipi.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Get;
import com.claymus.api.shared.GenericRequest;
import com.claymus.api.shared.GenericResponse;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.pratilipi.commons.shared.PratilipiFilter;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.access.SearchAccessor;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;
import com.pratilipi.pagecontent.pratilipis.PratilipisContent;

@SuppressWarnings("serial")
@Bind( uri= "/init" )
public class InitApi extends GenericApi {
	
	@Get
	public GenericResponse getInit( GenericRequest request ) throws InvalidArgumentException, UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		SearchAccessor searchAccessor = DataAccessorFactory.getSearchAccessor();
		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		
		
		List<Long> bookIdList = new ArrayList<>( 6 );
		pratilipiFilter.setType( PratilipiType.BOOK );

		pratilipiFilter.setLanguageId( 5130467284090880L );
		bookIdList.addAll( searchAccessor.searchPratilipi( pratilipiFilter, null, 2 ).getDataList() );
		pratilipiFilter.setLanguageId( 5965057007550464L );
		bookIdList.addAll( searchAccessor.searchPratilipi( pratilipiFilter, null, 2 ).getDataList() );
		pratilipiFilter.setLanguageId( 6319546696728576L );
		bookIdList.addAll( searchAccessor.searchPratilipi( pratilipiFilter, null, 2 ).getDataList() );

		
		List<Long> storyIdList = new ArrayList<>( 6 );
		pratilipiFilter.setType( PratilipiType.STORY );
		
		pratilipiFilter.setLanguageId( 5130467284090880L );
		storyIdList.addAll( searchAccessor.searchPratilipi( pratilipiFilter, null, 2 ).getDataList() );
		pratilipiFilter.setLanguageId( 5965057007550464L );
		storyIdList.addAll( searchAccessor.searchPratilipi( pratilipiFilter, null, 2 ).getDataList() );
		pratilipiFilter.setLanguageId( 6319546696728576L );
		storyIdList.addAll( searchAccessor.searchPratilipi( pratilipiFilter, null, 2 ).getDataList() );

		
		List<Long> poemIdList = new ArrayList<>( 6 );
		pratilipiFilter.setType( PratilipiType.POEM );
		
		pratilipiFilter.setLanguageId( 5130467284090880L );
		poemIdList.addAll( searchAccessor.searchPratilipi( pratilipiFilter, null, 2 ).getDataList() );
		pratilipiFilter.setLanguageId( 5965057007550464L );
		poemIdList.addAll( searchAccessor.searchPratilipi( pratilipiFilter, null, 2 ).getDataList() );
		pratilipiFilter.setLanguageId( 6319546696728576L );
		poemIdList.addAll( searchAccessor.searchPratilipi( pratilipiFilter, null, 2 ).getDataList() );

		
		PratilipisContent pratilipisContent = (PratilipisContent) dataAccessor.getPageContent( 5197655504322560L );
		pratilipisContent.setPratilipiIdList( bookIdList );
		pratilipisContent.setLastUpdated( new Date() );
		dataAccessor.createOrUpdatePageContent( pratilipisContent );

		pratilipisContent = (PratilipisContent) dataAccessor.getPageContent( 5742692155785216L );
		pratilipisContent.setPratilipiIdList( storyIdList );
		pratilipisContent.setLastUpdated( new Date() );
		dataAccessor.createOrUpdatePageContent( pratilipisContent );

		pratilipisContent = (PratilipisContent) dataAccessor.getPageContent( 5747690960846848L );
		pratilipisContent.setPratilipiIdList( poemIdList );
		pratilipisContent.setLastUpdated( new Date() );
		dataAccessor.createOrUpdatePageContent( pratilipisContent );
		
		
		for( Long pratilipiId : bookIdList ) {
			Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
			pratilipi.setRelevanceOffset( pratilipi.getRelevanceOffset() - 5 );
			dataAccessor.createOrUpdatePratilipi( pratilipi );
			PratilipiContentHelper.updatePratilipiSearchIndex( pratilipi.getId(), null, this.getThreadLocalRequest() );
		}
		
		for( Long pratilipiId : storyIdList ) {
			Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
			pratilipi.setRelevanceOffset( pratilipi.getRelevanceOffset() - 5 );
			dataAccessor.createOrUpdatePratilipi( pratilipi );
			PratilipiContentHelper.updatePratilipiSearchIndex( pratilipi.getId(), null, this.getThreadLocalRequest() );
		}

		for( Long pratilipiId : poemIdList ) {
			Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
			pratilipi.setRelevanceOffset( pratilipi.getRelevanceOffset() - 5 );
			dataAccessor.createOrUpdatePratilipi( pratilipi );
			PratilipiContentHelper.updatePratilipiSearchIndex( pratilipi.getId(), null, this.getThreadLocalRequest() );
		}
		
		
		return new GenericResponse();
	}
	
}
