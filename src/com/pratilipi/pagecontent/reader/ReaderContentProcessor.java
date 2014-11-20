package com.pratilipi.pagecontent.reader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.FreeMarkerUtil;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.BlobAccessor;
import com.claymus.data.transfer.BlobEntry;
import com.claymus.pagecontent.PageContentProcessor;
import com.pratilipi.commons.server.PratilipiHelper;
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
	public String generateHtml( ReaderContent readerContent, HttpServletRequest request )
			throws InsufficientAccessException, UnexpectedServerException {

		String pratilipiIdStr = request.getParameter( "id" );
		Long pratilipiId = Long.parseLong( pratilipiIdStr );

		PratilipiHelper pratilipiHelper = PratilipiHelper.get( request );
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );

		
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
		boolean showEditOption = PratilipiContentHelper
				.hasRequestAccessToUpdatePratilipiData( request, pratilipi );

		if( pratilipi.getState() != PratilipiState.PUBLISHED && !showEditOption )
			throw new InsufficientAccessException();

		
		Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
		PratilipiData pratilipiData =
				pratilipiHelper.createPratilipiData( pratilipi, null, author, null );

		String pageNoStr = request.getParameter( "page" ) == null ? "1" : request.getParameter( "page" );
		int pageNo = Integer.parseInt( pageNoStr );
		
		long pageCount = 0;
		String pageContent = "";
		if( pratilipiData.getPageCount() != null && pratilipiData.getPageCount() > 0 ) {

			pageCount = pratilipiData.getPageCount();
			pageContent = "<img id=\"imageContent\" style=\"width:100%;\" src=\"" + PratilipiHelper.getContentImageUrl( pratilipiId ) + "/" + pageNo + "\">";

		} else {

			// Fetching Pratilipi content
			BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
			BlobEntry blobEntry = null;
			try {
				blobEntry = blobAccessor.getBlob( PratilipiHelper.getContent( pratilipiId ) );
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			String content = new String( blobEntry.getData(), Charset.forName( "UTF-8" ) );

			logger.log( Level.INFO, "Content length: " + content.length() );
			
			Matcher matcher =  PratilipiHelper.REGEX_PAGE_BREAK.matcher( content );
			int startIndex = 0;
			int endIndex = 0;
			while( endIndex < content.length() ) {
				pageCount++;
				startIndex = endIndex;
				if( matcher.find() ) {
					endIndex = matcher.end();
					logger.log( Level.INFO, "Page " + pageCount + " length: "
							+ ( endIndex - startIndex )
							+ " (" + startIndex + " - " + endIndex + ") "
							+ matcher.group() );
				} else {
					endIndex = content.length();
					logger.log( Level.INFO, "Page " + pageCount + " length: "
							+ ( endIndex - startIndex )
							+ " (" + startIndex + " - " + endIndex + ")");
				}
				
				if( pageCount == pageNo )
					pageContent = content.substring( startIndex, endIndex );
			}
		}
		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "pratilipiData", pratilipiData );
		dataModel.put( "pageContent", pageContent );
		
		if( pageNo > 1 )
			dataModel.put( "previousPageUrl",
					pratilipiData.getReaderPageUrl()
							+ "&page=" + ( pageNo -1 ) );
		
		if( pageNo < pageCount )
			dataModel.put( "nextPageUrl",
					pratilipiData.getReaderPageUrl()
							+ "&page=" + ( pageNo + 1 ) );
		
		dataModel.put( "pageNumber", pageNo + " of " + pageCount );

		String templateName = pratilipiHelper.isModeBasic()
				? getTemplateName().replace( ".ftl", "Basic.ftl" )
				: getTemplateName();
		
		return FreeMarkerUtil.processTemplate( dataModel, templateName );
	}
	
}
