package com.pratilipi.api;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Get;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.pratilipi.api.shared.GetInitRequest;
import com.pratilipi.api.shared.GetInitResponse;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;

@SuppressWarnings("serial")
@Bind( uri= "/init" )
public class InitApi extends GenericApi {
	

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
		
		// END :: CREATE NEW PAGECONTENT :: END	*/

		// Upadte ContentType function call
//		String pratilipiList = updatePratilipi( this.getThreadLocalRequest(), null );
		
		return new GetInitResponse( "No String passed" );
	}
	
/*	// START :: UPDATE PRATILIPI CONTENTTYPE :: START	
	private String updatePratilipi( HttpServletRequest request, String cursorStr ) 
			throws InvalidArgumentException, InsufficientAccessException{
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		
		DataListCursorTuple<Pratilipi> pratilipiListCursorTuple =
				dataAccessor.getPratilipiList( pratilipiFilter, cursorStr, 200 );
		
		String cursorString = pratilipiListCursorTuple.getCursor();
		
		List<Pratilipi> pratilipiList =	pratilipiListCursorTuple.getDataList();
		
		for( Pratilipi pratilipi : pratilipiList ){
			if( pratilipi.getContentType() == null || pratilipi.getContentType().name().isEmpty() ){
				pratilipi.setContentType( 
						pratilipi.getPageCount() == null || pratilipi.getPageCount() == 0L ? 
								PratilipiContentType.PRATILIPI : PratilipiContentType.IMAGE );
				PratilipiData pratilipiData = new PratilipiData();
				pratilipiData.setId( pratilipi.getId() );
				pratilipiData.setContentType( pratilipi.getContentType() );
				PratilipiContentHelper.savePratilipi( pratilipiData, request );
			}
		}
		
		String pratilipiListString = updatePratilipi( request, cursorString );
		
		return pratilipiListString;
	}
	
	// END :: UPDATE PRATILIPI CONTENTTYPE :: END
	*/
	
}
