package com.pratilipi.api;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Get;
import com.claymus.commons.server.ImageUtil;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.data.access.BlobAccessor;
import com.claymus.data.transfer.BlobEntry;
import com.claymus.taskqueue.Task;
import com.pratilipi.api.shared.GetInitRequest;
import com.pratilipi.api.shared.GetInitResponse;
import com.pratilipi.commons.shared.PratilipiFilter;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.EventPratilipi;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
@Bind( uri= "/debug" )
public class DebugApi extends GenericApi {
	

	@Get
	public GetInitResponse getInit( GetInitRequest request )
			throws InvalidArgumentException, InsufficientAccessException, IOException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );

		
		
/*		// START :: CREATE NEW EVENT :: START
		
		Event event = dataAccessor.newEvent();
		event.setName( "New Event" );
		event.setNameEn( "New Event" );
		event.setStartDate( new Date() );
		event.setEndDate( new Date() );
		event.setCreationDate( new Date() );
		event = dataAccessor.createOrUpdateEvent( event );
		
		Page page = dataAccessor.newPage();
		page.setType( PratilipiPageType.EVENT.toString() );
		page.setUri( PratilipiPageType.EVENT.getUrlPrefix() + event.getId() );
		page.setPrimaryContentId( event.getId() );
		page.setCreationDate( new Date() );
		page = dataAccessor.createOrUpdatePage( page );

		return new GetInitResponse( page.getUri() );

		// END :: CREATE NEW EVENT :: END									*/

		

/*		// START :: CREATE NEW PAGECONTENT :: START

		Page page = dataAccessor.getPage( "/pages" );
		if( page == null ) {
			PageContent pageContent = PagesContentHelper.newPagesContent();
			pageContent.setCreationDate( new Date() );
			pageContent.setLastUpdated( new Date() );
			pageContent = dataAccessor.createOrUpdatePageContent( pageContent );
			
			page = dataAccessor.newPage();
			page.setType( ClaymusPageType.GENERIC.toString() );
			page.setUriAlias( "/pages" );
			page.setPrimaryContentId( pageContent.getId() );
			page.setCreationDate( new Date() );
			page = dataAccessor.createOrUpdatePage( page );
			
			pageContent.setPageId( page.getId() );
			pageContent = dataAccessor.createOrUpdatePageContent( pageContent );
		}
		return new GetInitResponse( page.getId().toString() );
		
		// END :: CREATE NEW PAGECONTENT :: END								*/

		
		
/*		// START :: RESIZING PRATILIPI COVER IMAGES :: START

		String[] coverImages = new String[] {
				"pratilipi-cover/original/pratilipi",
				"pratilipi-cover/original/pratilipi-classic-5130467284090880",
				"pratilipi-cover/original/pratilipi-classic-5965057007550464",
				"pratilipi-cover/original/pratilipi-classic-6213615354904576",
				"pratilipi-cover/original/pratilipi-classic-6319546696728576",
		};
		
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
		BlobAccessor blobAccessorAsia = DataAccessorFactory.getBlobAccessorAsia();
		for( String coverImage : coverImages ) {
			BlobEntry blobEntry = blobAccessor.getBlob( coverImage );
			blobEntry.setName( coverImage.replace( "/original/", "/150/" ) );
			blobEntry.setData( ImageUtil.resize( blobEntry.getData(), 150, 1500 ) );
			blobAccessorAsia.createOrUpdateBlob( blobEntry );
		}

		// END :: RESIZING PRATILIPI COVER IMAGES :: END							*/

		
		
/*		//START :: EVENT_PRATILIPI TABLE BACKFILL :: START
		
		List<Long> pratilipiIdList = new LinkedList<>();
		pratilipiIdList.add( 5705015998021632L );
		pratilipiIdList.add( 5653401597640704L );
		pratilipiIdList.add( 5688274483937280L );
		pratilipiIdList.add( 5717073850269696L );
		pratilipiIdList.add( 5651817190916096L );
		pratilipiIdList.add( 5723456272334848L );
		pratilipiIdList.add( 5695064290361344L );
		pratilipiIdList.add( 5647744857276416L );
		pratilipiIdList.add( 5722400918339584L );
		
		int counter = 0;
		
		for( Long pratilipiId : pratilipiIdList ){
			EventPratilipi eventPratilipi = dataAccessor.newEventPratilipi();
			eventPratilipi.setEventId( 5641434644348928L );
			eventPratilipi.setPratilipiId( pratilipiId );
			eventPratilipi.setPraticipationDate( new Date() );
			
			dataAccessor.createOrUpdateEventPratilipi( eventPratilipi );
			counter++;
		}
		
		//END :: EVENT_PRATILIPI TABLE BACKFILL :: END
*/
		
		
		return new GetInitResponse( "No String passed" );
	}
	
}
