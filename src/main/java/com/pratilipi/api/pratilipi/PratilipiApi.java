package com.pratilipi.api.pratilipi;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Put;
import com.pratilipi.api.author.shared.GetAuthorResponse;
import com.pratilipi.api.pratilipi.shared.GetPratilipiRequest;
import com.pratilipi.api.pratilipi.shared.GetPratilipiResponse;
import com.pratilipi.api.pratilipi.shared.PutPratilipiRequest;
import com.pratilipi.api.pratilipi.shared.PutPratilipiResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.client.PratilipiData;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.util.PratilipiDataUtil;
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
@Bind( uri = "/pratilipi" )
public class PratilipiApi extends GenericApi {
	
	@Get
	public GetPratilipiResponse getPratilipi( GetPratilipiRequest request )
			throws InvalidArgumentException, UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Pratilipi pratilipi = dataAccessor.getPratilipi( request.getPratilipiId() );
		Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
		
		PratilipiData pratilipiData = PratilipiDataUtil.createPratilipiData( pratilipi, author );
		
		GetPratilipiResponse response = new GetPratilipiResponse();
		response.setPratilipiId( pratilipiData.getId( ) );
		response.setTitle( pratilipiData.getTitle() );
		response.setTitleEn( pratilipiData.getTitle() );
		response.setLanguage( pratilipiData.getLanguage() );
		response.setAuthorId( pratilipiData.getAuthorId() );
		response.setAuthor( new GetAuthorResponse( pratilipiData.getAuthor() ) );
		response.setSummary( pratilipiData.getSummary() );
		response.setPublicationYear( pratilipiData.getPublicationYear() );
		response.setPageUrl( pratilipiData.getPageUrl() );
		response.setPageUrlAlias( pratilipiData.getPageUrlAlias() );
		response.setCoverImageUrl( pratilipiData.getCoverImageUrl() );
		response.setReaderPageUrl( pratilipiData.getReaderPageUrl() );
		response.setWriterPageUrl( pratilipiData.getWriterPageUrl() );
		response.setType( pratilipiData.getType() );
		response.setState( pratilipiData.getState() );
		response.setListingDate( pratilipiData.getListingDate() );
		response.setLastUpdated( pratilipiData.getLastUpdated() );
		response.setIndex( pratilipiData.getIndex() );
		response.setWordCount( pratilipiData.getWordCount() );
		response.setPageCount( pratilipiData.getPageCount() );
		response.setReviewCount( pratilipiData.getReviewCount() );
		response.setRatingCount( pratilipiData.getRatingCount() );
		response.setAverageRating( pratilipiData.getAverageRating() );
		response.setRelevance( pratilipiData.getRelevance() );
		response.setReadCount( pratilipiData.getReadCount() );
		
		return response;
	}

	@Put
	public PutPratilipiResponse putPratilipi( PutPratilipiRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		PratilipiData pratilipiData = new PratilipiData();
		pratilipiData.setId( request.getPratilipiId() );
		if( request.hasSummary() )
			pratilipiData.setSummary( request.getSummary() );
		if( request.hasIndex() )
			pratilipiData.setIndex( request.getIndex() );
		
		pratilipiData = PratilipiDataUtil.savePratilipiData( pratilipiData);
		
		
		Task task = TaskQueueFactory.newTask()
				.setUrl( "/pratilipi/process" )
				.addParam( "pratilipiId", pratilipiData.getId().toString() )
				.addParam( "processData", "true" );
		TaskQueueFactory.getPratilipiTaskQueue().add( task );
		
		
		return new PutPratilipiResponse();
	}		

}
