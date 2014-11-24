package com.pratilipi.pagecontent.reader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.FreeMarkerUtil;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.BlobAccessor;
import com.claymus.data.transfer.BlobEntry;
import com.claymus.pagecontent.PageContentProcessor;
import com.pratilipi.commons.server.PratilipiContentUtil;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.commons.shared.PratilipiContentType;
import com.pratilipi.commons.shared.PratilipiState;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;
import com.pratilipi.service.shared.data.PratilipiData;

public class ReaderContentProcessor extends PageContentProcessor<ReaderContent> {
	
	private static final Logger logger =
			Logger.getLogger( ReaderContentProcessor.class.getName() );

	
	@Override
	public String generateTitle( ReaderContent readerContent, HttpServletRequest request ) {
		String pratilipiIdStr = request.getParameter( "id" );
		Long pratilipiId = Long.parseLong( pratilipiIdStr );

		Pratilipi pratilipi = DataAccessorFactory
				.getDataAccessor( request )
				.getPratilipi( pratilipiId );
		
		return "Read | " + pratilipi.getTitle()
				+ ( pratilipi.getTitleEn() == null ? "" : " (" + pratilipi.getTitleEn() + ")" );
	}
	
	@Override
	public String generateHtml( ReaderContent readerContent, HttpServletRequest request )
			throws InsufficientAccessException, UnexpectedServerException {

		String pratilipiIdStr = request.getParameter( "id" );
		Long pratilipiId = Long.parseLong( pratilipiIdStr );

		PratilipiHelper pratilipiHelper = PratilipiHelper.get( request );
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );

		
		// AccessToUpdatePratilipiData ?
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
		boolean showEditOption = PratilipiContentHelper
				.hasRequestAccessToUpdatePratilipiData( request, pratilipi );

		// Pratilipi should either be published OR user should have edit access.
		if( pratilipi.getState() != PratilipiState.PUBLISHED && !showEditOption )
			throw new InsufficientAccessException();

		
		// Creating PratilipiData
		Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
		PratilipiData pratilipiData =
				pratilipiHelper.createPratilipiData( pratilipi, null, author, null );


		// Page # to display
		String pageNoStr = request.getParameter( "page" ) == null ? "1" : request.getParameter( "page" );
		int pageNo = Integer.parseInt( pageNoStr );

		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "pratilipiData", pratilipiData );
		dataModel.put( "pageNo", pageNo );
		
		
		if( pratilipiData.getContentType() == PratilipiContentType.PRATILIPI ) {

			BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
			BlobEntry blobEntry = null;
			try {
				blobEntry = blobAccessor.getBlob( PratilipiHelper.getContent( pratilipiId ) );
			} catch( IOException e ) {
				logger.log( Level.SEVERE, "Failed to fetch pratilipi content.", e );
				throw new UnexpectedServerException();
			}
			
			String content = new String( blobEntry.getData(), Charset.forName( "UTF-8" ) );
			PratilipiContentUtil pratilipiContentUtil = new PratilipiContentUtil( content );
			
			dataModel.put( "pageCount", pratilipiContentUtil.getPageCount() );
			dataModel.put( "pageContent", pratilipiContentUtil.getContent( pageNo ) );
			
		} else { // if( pratilipiData.getContentType() == PratilipiContentType.IMAGE )
			
			dataModel.put( "pageCount", (int) (long) pratilipiData.getPageCount() );
			
		}
		
		
		if( request.getParameter( "ret" ) != null && !request.getParameter( "ret" ).trim().isEmpty()  )
			dataModel.put( "exitUrl", request.getParameter( "ret" ) );

		
//			if( pageNo > 1 )
//				dataModel.put( "previousPageUrl",
//						pratilipiData.getReaderPageUrl()
//								+ "&page=" + ( pageNo -1 ) );
//			
//			if( pageNo < pageCount )
//				dataModel.put( "nextPageUrl",
//						pratilipiData.getReaderPageUrl()
//								+ "&page=" + ( pageNo + 1 ) );
		
//		pageContent = "<img id=\"imageContent\" style=\"width:auto;\" src=\"" + PratilipiHelper.getContentImageUrl( pratilipiId ) + "/" + pageNo + "\">";
		
		
		String templateName = pratilipiHelper.isModeBasic()
				? getTemplateName().replace( ".ftl", "Basic.ftl" )
				: getTemplateName();
		
		return FreeMarkerUtil.processTemplate( dataModel, templateName );
	}
	
}
