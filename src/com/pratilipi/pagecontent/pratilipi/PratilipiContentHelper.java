package com.pratilipi.pagecontent.pratilipi;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.Access;
import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.server.UserAccessHelper;
import com.claymus.commons.shared.ClaymusAccessTokenType;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.BlobAccessor;
import com.claymus.data.transfer.AccessToken;
import com.claymus.data.transfer.BlobEntry;
import com.claymus.pagecontent.PageContentHelper;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.commons.shared.PratilipiAccessTokenType;
import com.pratilipi.commons.shared.PratilipiContentType;
import com.pratilipi.commons.shared.PratilipiState;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.data.transfer.Publisher;
import com.pratilipi.data.transfer.UserPratilipi;
import com.pratilipi.pagecontent.pratilipi.gae.PratilipiContentEntity;
import com.pratilipi.pagecontent.pratilipi.shared.PratilipiContentData;
import com.pratilipi.pagecontent.pratilipi.util.PratilipiContentUtil;
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

	public static final Access ACCESS_TO_READ_PRATILIPI_CONTENT =
			new Access( "pratilipi_data_read_content", false, "View Pratilipi Content" );


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
				ACCESS_TO_ADD_PRATILIPI_REVIEW,
				ACCESS_TO_READ_PRATILIPI_CONTENT
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
		Publisher publisher = dataAccessor.getPublisher( pratilipi.getPublisherId() );
		
		if( author == null && publisher == null )
			return false;
		else if( author != null && !PratilipiHelper.get( request ).getCurrentUserId().equals( author.getUserId() ) )
			return false;
		else if( publisher != null && !PratilipiHelper.get( request ).getCurrentUserId().equals( publisher.getUserId() ) )
			return false;
		else
			return true;
	}
	
	public static boolean hasRequestAccessToUpdatePratilipiData(
			HttpServletRequest request, Pratilipi pratilipi ) {
		
		if( pratilipi.getState() == PratilipiState.DELETED )
			return false;
		
		if( PratilipiHelper.get( request ).hasUserAccess( ACCESS_TO_UPDATE_PRATILIPI_DATA ) )
			return true;
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
		Publisher publisher = dataAccessor.getPublisher( pratilipi.getPublisherId() );

		if( author == null && publisher == null )
			return false;
		else if( author != null && !PratilipiHelper.get( request ).getCurrentUserId().equals( author.getUserId() ) )
			return false;
		else if( publisher != null && !PratilipiHelper.get( request ).getCurrentUserId().equals( publisher.getUserId() ) )
			return false;
		else
			return true;
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
		Publisher publisher = dataAccessor.getPublisher( pratilipi.getPublisherId() );

		if( author != null && PratilipiHelper.get( request ).getCurrentUserId().equals( author.getUserId() ) )
			return false;
		else if( publisher != null && PratilipiHelper.get( request ).getCurrentUserId().equals( publisher.getUserId() ) )
			return false;
		else
			return true;
	}
	
	public static boolean hasRequestAccessToReadPratilipiContent(
			HttpServletRequest request, Pratilipi pratilipi ) {
		
		if( pratilipi.getState() == PratilipiState.PUBLISHED )
			return true;
		
		if( pratilipi.getState() == PratilipiState.DELETED )
			return false;
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		AccessToken accessToken = (AccessToken) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN ); ;
		
		if( pratilipi.getState() == PratilipiState.DRAFTED
				|| pratilipi.getState() == PratilipiState.PUBLISHED_PAID
				|| pratilipi.getState() == PratilipiState.PUBLISHED_DISCONTINUED ) {
			
			if( accessToken.getType().equals( ClaymusAccessTokenType.USER.toString() ) ) {
				if( UserAccessHelper.hasUserAccess( accessToken.getUserId(), ACCESS_TO_READ_PRATILIPI_CONTENT, request ) )
					return true;
				UserPratilipi userPratilipi = dataAccessor.getUserPratilipi( accessToken.getUserId(), pratilipi.getId() );
				return userPratilipi != null && userPratilipi.getPurchasedFrom() != null;
				
			} else if( accessToken.getType().equals( PratilipiAccessTokenType.PUBLISHER.toString() ) ) {
				return (long) accessToken.getPublisherId() == (long) pratilipi.getPublisherId();
				
			} else if( accessToken.getType().equals( PratilipiAccessTokenType.USER_PUBLISHER.toString() ) ) {
				if( (long) accessToken.getPublisherId() != (long) pratilipi.getPublisherId() )
					return false;
				UserPratilipi userPratilipi = dataAccessor.getUserPratilipi( accessToken.getUserId(), pratilipi.getId() );
				return userPratilipi != null && userPratilipi.getPurchasedFrom() != null;
				
			} else {
				return false;
			}
			
		}
		
		return false;
	}

	public static boolean hasRequestAccessToUpdatePratilipiContent(
			HttpServletRequest request, Pratilipi pratilipi ) {
		
		return hasRequestAccessToUpdatePratilipiData( request, pratilipi );
	}
	

	public static Object getPratilipiContent(
			long pratilipiId, int pageNo, PratilipiContentType contentType,
			HttpServletRequest request ) throws InvalidArgumentException,
			InsufficientAccessException, UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
		
		if( !PratilipiContentHelper.hasRequestAccessToReadPratilipiContent( request, pratilipi ) )
			throw new InsufficientAccessException();

		
		PratilipiHelper pratilipiHelper = PratilipiHelper.get( request );
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();

		PratilipiData pratilipiData = pratilipiHelper.createPratilipiData( pratilipi, null, null, null, true );
		
		if( contentType == PratilipiContentType.PRATILIPI ) {
			BlobEntry blobEntry = null;
			try {
				blobEntry = blobAccessor.getBlob( pratilipiData.getPratilipiContentName() );
			} catch( IOException e ) {
				logger.log( Level.SEVERE, "Failed to fetch pratilipi content.", e );
				throw new UnexpectedServerException();
			}
			
			String content = new String( blobEntry.getData(), Charset.forName( "UTF-8" ) );
			PratilipiContentUtil pratilipiContentUtil = new PratilipiContentUtil( content );
			return pratilipiContentUtil.getContent( pageNo );
			
		} else if( contentType == PratilipiContentType.IMAGE ) {
			try {
				return blobAccessor.getBlob( pratilipiData.getPratilipiContentImageName( pageNo ) );
			} catch( IOException e ) {
				logger.log( Level.SEVERE, "Failed to fetch pratilipi content.", e );
				throw new UnexpectedServerException();
			}
		
		} else {
			throw new InvalidArgumentException( contentType + " content type is not yet supported." );
		}
		
	}
	
	public static void updatePratilipiContent(
			long pratilipiId, int pageNo, PratilipiContentType contentType,
			Object pageContent, HttpServletRequest request )
			throws InvalidArgumentException, InsufficientAccessException,
			UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );

		if( !PratilipiContentHelper.hasRequestAccessToUpdatePratilipiContent( request, pratilipi ) )
			throw new InsufficientAccessException();

		
		pratilipi.setLastUpdated( new Date() );
		pratilipi = dataAccessor.createOrUpdatePratilipi( pratilipi );

		
		PratilipiHelper pratilipiHelper = PratilipiHelper.get( request );
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
		
		PratilipiData pratilipiData = pratilipiHelper.createPratilipiData( pratilipi, null, null, null, true );
		
		if( contentType == PratilipiContentType.PRATILIPI ) {
			BlobEntry blobEntry = null;
			try {
				blobEntry = blobAccessor.getBlob( pratilipiData.getPratilipiContentName() );
			} catch( IOException e ) {
				logger.log( Level.SEVERE, "Failed to fetch pratilipi content.", e );
				throw new UnexpectedServerException();
			}
			
			String content = new String( blobEntry.getData(), Charset.forName( "UTF-8" ) );
			PratilipiContentUtil pratilipiContentUtil = new PratilipiContentUtil( content );
			content = pratilipiContentUtil.updateContent( pageNo, (String) pageContent );
			
			blobEntry.setData( content.getBytes( Charset.forName( "UTF-8" ) ) );
			try {
				blobAccessor.updateBlob( blobEntry );
			} catch( IOException e ) {
				logger.log( Level.SEVERE, "Failed to update pratilipi content.", e );
				throw new UnexpectedServerException();
			}
			
		} else if( contentType == PratilipiContentType.IMAGE ) {
			// TODO: implementation
		
		} else {
			throw new InvalidArgumentException( contentType + " content type is not yet supported." );
		}
		
	}
	
	public static void insertPratilipiContentPage(
			long pratilipiId, int pageNo, PratilipiContentType contentType,
			HttpServletRequest request ) throws InvalidArgumentException,
			InsufficientAccessException, UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );

		if( !PratilipiContentHelper.hasRequestAccessToUpdatePratilipiContent( request, pratilipi ) )
			throw new InsufficientAccessException();

		
		pratilipi.setLastUpdated( new Date() );
		pratilipi = dataAccessor.createOrUpdatePratilipi( pratilipi );

		
		PratilipiHelper pratilipiHelper = PratilipiHelper.get( request );
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
		
		PratilipiData pratilipiData = pratilipiHelper.createPratilipiData( pratilipi, null, null, null, true );
		
		if( contentType == PratilipiContentType.PRATILIPI ) {
			BlobEntry blobEntry = null;
			try {
				blobEntry = blobAccessor.getBlob( pratilipiData.getPratilipiContentName() );
			} catch( IOException e ) {
				logger.log( Level.SEVERE, "Failed to fetch pratilipi content.", e );
				throw new UnexpectedServerException();
			}
			
			String content = new String( blobEntry.getData(), Charset.forName( "UTF-8" ) );
			PratilipiContentUtil pratilipiContentUtil = new PratilipiContentUtil( content );
			content = pratilipiContentUtil.insertPage( pageNo );
			
			blobEntry.setData( content.getBytes( Charset.forName( "UTF-8" ) ) );
			try {
				blobAccessor.updateBlob( blobEntry );
			} catch( IOException e ) {
				logger.log( Level.SEVERE, "Failed to update pratilipi content.", e );
				throw new UnexpectedServerException();
			}
			
		} else if( contentType == PratilipiContentType.IMAGE ) {
			// TODO: implementation
		
		} else {
			throw new InvalidArgumentException( contentType + " content type is not yet supported." );
		}
		
	}
	
}
