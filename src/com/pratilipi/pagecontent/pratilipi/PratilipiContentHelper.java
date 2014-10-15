package com.pratilipi.pagecontent.pratilipi;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.Access;
import com.claymus.pagecontent.PageContentHelper;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.pagecontent.pratilipi.gae.PratilipiContentEntity;
import com.pratilipi.pagecontent.pratilipi.shared.PratilipiContentData;
import com.pratilipi.service.shared.data.PratilipiData;

public class PratilipiContentHelper extends PageContentHelper<
		PratilipiContent,
		PratilipiContentData,
		PratilipiContentProcessor> {
	
	public static final Access ACCESS_TO_ADD_PRATILIPI_DATA =
			new Access( "pratilipi_data_add", false, "Add Pratilipi Data" );
	public static final Access ACCESS_TO_UPDATE_PRATILIPI_DATA =
			new Access( "pratilipi_data_update", false, "Update Pratilipi Data" );
	
	public static final Access ACCESS_TO_READ_PRATILIPI_META_DATA =
			new Access( "pratilipi_data_read_meta", false, "View Pratilipi Meta Data" );
	public static final Access ACCESS_TO_ADD_PRATILIPI_DATA_META =
			new Access( "pratilipi_data_add_meta", false, "Add Pratilipi Meta Data" );
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
		return 3.0;
	}

	@Override
	public Access[] getAccessList() {
		return new Access[] {
				ACCESS_TO_ADD_PRATILIPI_DATA,
				ACCESS_TO_UPDATE_PRATILIPI_DATA,
				ACCESS_TO_READ_PRATILIPI_META_DATA,
				ACCESS_TO_ADD_PRATILIPI_DATA_META,
				ACCESS_TO_UPDATE_PRATILIPI_DATA_META,
				ACCESS_TO_ADD_PRATILIPI_REVIEW
		};
	}
	
	
	public static PratilipiContent newPratilipiContent( Long pratilipiId ) {
		PratilipiContent pratilipiContent = new PratilipiContentEntity();
		pratilipiContent.setPratilipiId( pratilipiId );
		return pratilipiContent;
	}

	
	public static boolean hasRequestAccessToAddData( HttpServletRequest request ) {
		return PratilipiHelper.get( request ).hasUserAccess( ACCESS_TO_ADD_PRATILIPI_DATA );
	}
	
	public static boolean hasRequestAccessToUpdateData(
			HttpServletRequest request, Pratilipi pratilipi ) {
		
		return PratilipiHelper.get( request ).hasUserAccess( ACCESS_TO_UPDATE_PRATILIPI_DATA ) ||
				(
					hasRequestAccessToAddData( request ) &&
					pratilipi.getAuthorId().equals( PratilipiHelper.get( request ).getCurrentUserId() )
				);
	}
		
	public static boolean hasRequestAccessToUpdateData(
			HttpServletRequest request, PratilipiData pratilipiData ) {
		
		return PratilipiHelper.get( request ).hasUserAccess( ACCESS_TO_UPDATE_PRATILIPI_DATA ) ||
				(
					hasRequestAccessToAddData( request ) &&
					pratilipiData.getAuthorId().equals( PratilipiHelper.get( request ).getCurrentUserId() )
				);
	}

	public static boolean hasRequestAccessToReadMetaData( HttpServletRequest request ) {
		return PratilipiHelper.get( request ).hasUserAccess( ACCESS_TO_READ_PRATILIPI_META_DATA );
	}
	
}
