package com.pratilipi.pagecontent.reader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.SerializationUtil;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.BlobAccessor;
import com.claymus.data.transfer.BlobEntry;
import com.claymus.pagecontent.PageContentProcessor;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.commons.shared.PratilipiType;
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
	public String generateHtml( ReaderContent pratilipiContent, HttpServletRequest request )
			throws UnexpectedServerException {

		PratilipiType pratilipiType = pratilipiContent.getPratilipiType();

		String url = request.getRequestURI();
		String pratilipiIdStr =
				url.substring( url.lastIndexOf( '/' ) + 1 );
		String pageNoStr =
				request.getParameter( "page" ) == null ? "1" : request.getParameter( "page" );
		

		Long pratilipiId = Long.parseLong( pratilipiIdStr );
		int pageNo = Integer.parseInt( pageNoStr );

		
		// Fetching Pratilipi and Author
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
		Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
		dataAccessor.destroy();

		long pageCount = 0;
		String pageContent = "";
		if( pratilipi.getPageCount() != null && pratilipi.getPageCount() > 0 ) {

			pageCount = pratilipi.getPageCount();
			pageContent = "<img style=\"width:100%;\" src=\"" + PratilipiHelper.getContentImageUrl( pratilipiType, pratilipiId ) + "/" + pageNo + "\">";

		} else {

			// Fetching Pratilipi content
			BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
			BlobEntry blobEntry = null;
			try {
				blobEntry = blobAccessor.getBlob( PratilipiHelper.getContent( pratilipiType, pratilipiId ) );
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// TODO: Remove this as soon as possible
			if( blobEntry == null ) {

				// Hack to create empty Pratilipi content from old Pratilipis
				logger.log( Level.INFO, "Creating Blob Store entry using empty string ..." );
				try {
					blobAccessor.createBlob(
							PratilipiHelper.getContent( pratilipiType, pratilipiId ),
							"text/html",
							"&nbsp;", Charset.forName( "UTF-8" ) );
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					blobEntry = blobAccessor.getBlob( PratilipiHelper.getContent( pratilipiType, pratilipiId ) );
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
		
		//creating pratilipi data
		PratilipiHelper pratilipiHelper = PratilipiHelper.get( request );
		PratilipiData pratilipiData = pratilipiHelper.createPratilipiData(
				pratilipiId, true );


		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		
		dataModel.put( "pratilipi", pratilipi );
		dataModel.put( "author", author );
		dataModel.put( "pageContent", pageContent );
	
		if( pageNo > 1 )
			dataModel.put( "previousPageUrl",
					PratilipiHelper.getReaderPageUrl( pratilipiType, pratilipiId )
							+ "?page=" + ( pageNo -1 ) );
		
		if( pageNo < pageCount )
			dataModel.put( "nextPageUrl",
					PratilipiHelper.getReaderPageUrl( pratilipiType, pratilipiId )
							+ "?page=" + ( pageNo + 1 ) );

		dataModel.put( "pratilipiHomeUrl", PratilipiHelper.getPageUrl( pratilipiType, pratilipiId ) );
		dataModel.put( "authorHomeUrl", PratilipiHelper.URL_AUTHOR_PAGE + pratilipi.getAuthorId() );

		dataModel.put( "showEditOptions",
				PratilipiContentHelper.hasRequestAccessToUpdateData( request, pratilipi ) );
		
		dataModel.put( "pratilipiDataEncodedStr", SerializationUtil.encode( pratilipiData ) );

		
		return super.processTemplate(
				dataModel,
				"com/pratilipi/pagecontent/reader/ReaderContent.ftl" );
	}
	
}
