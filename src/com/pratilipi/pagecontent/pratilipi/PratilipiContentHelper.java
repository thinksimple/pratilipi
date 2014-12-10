package com.pratilipi.pagecontent.pratilipi;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.Access;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.BlobAccessor;
import com.claymus.data.transfer.BlobEntry;
import com.claymus.pagecontent.PageContentHelper;
import com.pratilipi.api.shared.GetPratilipiContentRequest;
import com.pratilipi.api.shared.GetPratilipiContentResponse;
import com.pratilipi.commons.server.PratilipiContentUtil;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.commons.shared.PratilipiContentType;
import com.pratilipi.commons.shared.PratilipiState;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.pagecontent.pratilipi.gae.PratilipiContentEntity;
import com.pratilipi.pagecontent.pratilipi.shared.PratilipiContentData;
import com.pratilipi.service.shared.data.PratilipiData;

public class PratilipiContentHelper extends PageContentHelper<
		PratilipiContent,
		PratilipiContentData,
		PratilipiContentProcessor> {
	
	private static final Logger logger =
			Logger.getLogger( PratilipiContentHelper.class.getName() );

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
		
		if( pratilipi.getState() == PratilipiState.DELETED )
			return false;

		if( PratilipiHelper.get( request ).hasUserAccess( ACCESS_TO_ADD_PRATILIPI_DATA ) )
			return true;
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
		
		return PratilipiHelper.get( request ).getCurrentUserId().equals( author.getUserId() );
	}
	
	public static boolean hasRequestAccessToUpdatePratilipiData(
			HttpServletRequest request, Pratilipi pratilipi ) {
		
		if( pratilipi.getState() == PratilipiState.DELETED )
			return false;
		
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
	
	public static GetPratilipiContentResponse getPratilipiContent(
			GetPratilipiContentRequest apiRequest, HttpServletRequest httpRequest )
			throws UnexpectedServerException {
		
		PratilipiHelper pratilipiHelper = PratilipiHelper.get( httpRequest );
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( httpRequest );
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();

		Pratilipi pratilipi = dataAccessor.getPratilipi( apiRequest.getPratilipiId() );
		PratilipiData pratilipiData = pratilipiHelper.createPratilipiData( pratilipi, null, null, null, true );
		
		Object pageContent = null;
		String pageContentMimeType = null;
		
		
		if( apiRequest.getContentType() == PratilipiContentType.PRATILIPI ) {
			BlobEntry blobEntry = null;
			try {
				blobEntry = blobAccessor.getBlob( pratilipiData.getPratilipiContentName() );
			} catch( IOException e ) {
				logger.log( Level.SEVERE, "Failed to fetch pratilipi content.", e );
				throw new UnexpectedServerException();
			}
			
			String content = new String( blobEntry.getData(), Charset.forName( "UTF-8" ) );
			PratilipiContentUtil pratilipiContentUtil = new PratilipiContentUtil( content );
			pageContent = pratilipiContentUtil.getContent( apiRequest.getPageNumber() );
			pageContentMimeType = blobEntry.getMimeType();
		
			
		} else if( apiRequest.getContentType() == PratilipiContentType.IMAGE ) {
			BlobEntry blobEntry = null;
			try {
				blobEntry = blobAccessor.getBlob( pratilipiData.getPratilipiContentImageName( apiRequest.getPageNumber() ) );
			} catch( IOException e ) {
				logger.log( Level.SEVERE, "Failed to fetch pratilipi content.", e );
				throw new UnexpectedServerException();
			}
			
			pageContent = blobEntry.getData();
			pageContentMimeType = blobEntry.getMimeType();
		}

		
		return new GetPratilipiContentResponse(
				apiRequest.getPratilipiId(),
				apiRequest.getPageNumber(),
				apiRequest.getContentType(),
				pageContent, pageContentMimeType );
	}
	
}
