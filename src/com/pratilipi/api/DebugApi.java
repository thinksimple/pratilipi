package com.pratilipi.api;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Get;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.pratilipi.api.shared.GetInitRequest;
import com.pratilipi.api.shared.GetInitResponse;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.EventPratilipi;

@SuppressWarnings("serial")
@Bind( uri= "/debug" )
public class DebugApi extends GenericApi {
	

	@Get
	public GetInitResponse getInit( GetInitRequest request )
			throws InvalidArgumentException, InsufficientAccessException {
		
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

		
		
/*		// START :: PRATILIPI TABLE BACKFILL :: START
		
		Memcache memcache = new MemcacheGaeImpl();
		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		String cursor = memcache.get( "InitApi" );
		int pageSize = 100;
		int count = 0;
		int updateCount = 0;
		while( true ) {
			DataListCursorTuple<Pratilipi> pratilipiListCursorTuple
					= dataAccessor.getPratilipiList( pratilipiFilter, cursor, pageSize );

			List<Pratilipi> pratilipiList =	pratilipiListCursorTuple.getDataList();
			cursor = pratilipiListCursorTuple.getCursor();
			
			for( Pratilipi pratilipi : pratilipiList ) {
				count++;
				if( pratilipi.getContentType() == null ) {
					pratilipi.setContentType( 
							pratilipi.getPageCount() == null || pratilipi.getPageCount() == 0L
									? PratilipiContentType.PRATILIPI
									: PratilipiContentType.IMAGE );
					dataAccessor.createOrUpdatePratilipi( pratilipi );
					updateCount++;
				}
			}

			if( pratilipiList.size() < pageSize || cursor == null || updateCount > 100 )
				break;
		}
		memcache.put( "InitApi", cursor );
		return new GetInitResponse( "Entities Checked: " + count + ". Entities Updated: " + updateCount );
		
		// END :: PRATILIPI TABLE BACKFILL :: END							*/
		
		
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
