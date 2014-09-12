package com.pratilipi.pagecontent.reader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.data.access.BlobAccessor;
import com.claymus.data.transfer.BlobEntry;
import com.claymus.module.pagecontent.PageContentProcessor;
import com.pratilipi.commons.shared.PratilipiHelper;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Pratilipi;

public class ReaderContentProcessor extends PageContentProcessor<ReaderContent> {

	private static final Logger logger =
			Logger.getLogger( ReaderContentProcessor.class.getName() );

	
	@Override
	public String getHtml( ReaderContent pratilipiContent,
			HttpServletRequest request, HttpServletResponse response ) throws IOException {

		PratilipiType pratilipiType = pratilipiContent.getPratilipiType();

		String url = request.getRequestURI();
		String pratilipiIdStr =
				url.substring( pratilipiType.getReaderPageUrl().length() );
		String pageNoStr =
				request.getParameter( "page" ) == null ? "1" : request.getParameter( "page" );
		String readerType =
				request.getParameter( "reader" ) == null ? "html" : request.getParameter( "reader" );
		

		Long pratilipiId = Long.parseLong( pratilipiIdStr );
		int pageNo = Integer.parseInt( pageNoStr );
		

		long pageCount = 1;
		String pageContent = "";
		if( readerType.equals( "jpeg" ) ) {
			
			// Fetching Pratilipi
			DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
			Pratilipi pratilipi = dataAccessor.getPratilipi(
					pratilipiId, pratilipiContent.getPratilipiType() );
			dataAccessor.destroy();

			pageCount = pratilipi.getPageCount();
			pageContent = "<img style=\"width:100%; max-width:700px\" src=\"" + pratilipiType.getContentJpegUrl() + pratilipiId + "/" + pageNo + "\">";

		} else if( readerType.equals( "html" ) ) {

			// Fetching Pratilipi content
			BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
			BlobEntry blobEntry = blobAccessor.getBlob( pratilipiType.getContentResource() + pratilipiIdStr );
			String content = new String( blobEntry.getData(), Charset.forName( "UTF-8" ) );

			logger.log( Level.INFO, "Content length: " + content.length() );
			
			Matcher matcher =  PratilipiHelper.REGEX_PAGE_BREAK.matcher( content );
			int startIndex = 0;
			int endIndex = 0;
			while( endIndex < content.length() ) {
				startIndex = endIndex;
				if( matcher.find() )
					endIndex = matcher.end();
				else
					endIndex = content.length();

				System.out.println( "Page " + pageCount + " length: "
						+ ( endIndex - startIndex )
						+ " (" + startIndex + " - " + endIndex + ") "
						+ matcher.group() );

				if( pageCount == pageNo ) {
					content = content.substring( startIndex, endIndex );
				}

				pageCount++;
			}
		}


		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		
		dataModel.put( "pageContent", pageContent );
	
		if( pageNo > 1 )
			dataModel.put( "previousPageUrl",
					pratilipiType.getReaderPageUrl() + pratilipiIdStr
							+ "?&reader=" + readerType 
							+ "&page=" + ( pageNo -1 ) );
		
		if( pageNo < pageCount )
			dataModel.put( "nextPageUrl",
					pratilipiType.getReaderPageUrl() + pratilipiIdStr
							+ "?&reader=" + readerType
							+ "&page=" + ( pageNo +1 ) );

		
		return super.processTemplate(
				dataModel,
				"com/pratilipi/pagecontent/reader/ReaderContent.ftl" );
	}
	
}
