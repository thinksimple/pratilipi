package com.pratilipi.pagecontent.pratilipi;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.Access;
import com.claymus.pagecontent.PageContentHelper;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.pagecontent.pratilipi.gae.PratilipiContentEntity;
import com.pratilipi.pagecontent.pratilipi.shared.PratilipiContentData;

public class PratilipiContentHelper extends PageContentHelper<
		PratilipiContent,
		PratilipiContentData,
		PratilipiContentProcessor> {
	
	public static final Access ACCESS_TO_ADD_PRATILIPI_DATA =
			new Access( "pratilipi_data_add", false, "Add Pratilipi Data" );
	public static final Access ACCESS_TO_UPDATE_PRATILIPI_DATA =
			new Access( "pratilipi_data_update", false, "Update Pratilipi Data" );
	
	public static final Access ACCESS_TO_READ_PRATILIPI_DATA_META =
			new Access( "pratilipi_data_read_meta", false, "View Pratilipi Meta Data" );
	public static final Access ACCESS_TO_UPDATE_PRATILIPI_DATA_META =
			new Access( "pratilipi_data_update_meta", false, "Update Pratilipi Meta Data" );

	public static final Access ACCESS_TO_ADD_PRATILIPI_REVIEW =
			new Access( "pratilipi_data_add_review", false, "Add Pratilipi Review" );

	
	@Override
	public String getModuleName() {
		return "Pratilipi";
	}

	@Override
	public Double getModuleVersion() {
		return 4.0;
	}

	@Override
	public Access[] getAccessList() {
		return new Access[] {
				ACCESS_TO_ADD_PRATILIPI_DATA,
				ACCESS_TO_UPDATE_PRATILIPI_DATA,
				ACCESS_TO_READ_PRATILIPI_DATA_META,
				ACCESS_TO_UPDATE_PRATILIPI_DATA_META,
				ACCESS_TO_ADD_PRATILIPI_REVIEW
		};
	}
	
	
	public static PratilipiContent newPratilipiContent( Long pratilipiId ) {
		return new PratilipiContentEntity( pratilipiId );
	}

	
	public static boolean hasRequestAccessToAddPratilipiData(
			HttpServletRequest request, Pratilipi pratilipi ) {
		
		if( PratilipiHelper.get( request ).hasUserAccess( ACCESS_TO_ADD_PRATILIPI_DATA ) )
			return true;
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
		
		return PratilipiHelper.get( request ).getCurrentUserId().equals( author.getUserId() );
	}
	
	public static boolean hasRequestAccessToUpdatePratilipiData(
			HttpServletRequest request, Pratilipi pratilipi ) {
		
		if( PratilipiHelper.get( request ).hasUserAccess( ACCESS_TO_UPDATE_PRATILIPI_DATA ) )
			return true;
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
		
		return PratilipiHelper.get( request ).getCurrentUserId().equals( author.getUserId() );
	}
		
	public static boolean hasRequestAccessToReadPratilipiMetaData( HttpServletRequest request ) {
		return PratilipiHelper.get( request ).hasUserAccess( ACCESS_TO_READ_PRATILIPI_DATA_META );
	}
	
	public static boolean hasRequestAccessToUpdatePratilipiMetaData( HttpServletRequest request ) {
		return PratilipiHelper.get( request ).hasUserAccess( ACCESS_TO_UPDATE_PRATILIPI_DATA_META );
	}
	
	public static boolean hasRequestAccessToAddPratilipiReview(
			HttpServletRequest request, Pratilipi pratilipi ) {
		
		if( ! PratilipiHelper.get( request ).hasUserAccess( ACCESS_TO_ADD_PRATILIPI_REVIEW ) )
			return false;
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
		
		return ! PratilipiHelper.get( request ).getCurrentUserId().equals( author.getUserId() );
	}
	
}
