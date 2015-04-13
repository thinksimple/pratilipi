package com.pratilipi.servlet;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.data.access.BlobAccessor;
import com.claymus.data.transfer.BlobEntry;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.data.access.DataAccessorFactory;

@SuppressWarnings("serial")
public class QueueHtmlToPratilipiServlet extends HttpServlet {
	
	private static final Logger logger = 
			Logger.getLogger( QueueHtmlToPratilipiServlet.class.getName() );

	
	@Override
	public void doPost(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {

		String pratilipiIdStr = request.getParameter( "pratilipiId" );
		Long pratilipiId = Long.parseLong( pratilipiIdStr );
		
		
		// Fetching html content blob store
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
		String fileName = PratilipiHelper.getContentHtml( pratilipiId );
		BlobEntry blobEntry = blobAccessor.getBlob( fileName );
		String html = new String( blobEntry.getData() );
		
		
		logger.log( Level.INFO, "Raw content length: " + html.length() );


		// Processing html content to Pratilipi content
		Matcher matcher =  PratilipiHelper.REGEX_HTML_BODY.matcher( html );
		if( matcher.matches() ) {
			logger.log( Level.INFO, "Discarding pre-<body> content (lenght: " + matcher.group( 1 ).length() + "):\n" + matcher.group( 1 ) );
			logger.log( Level.INFO, "Discarding post-</body> content (lenght: " + matcher.group( 3 ).length() + "):\n" + matcher.group( 3 ) );
			html = matcher.group( 2);
		}
		
		
		System.out.println( "Processed content lenght: " + html.length() );
				  

		// Saving Pratilipi content to blob store
		blobEntry = blobAccessor.newBlob(
				PratilipiHelper.getContent( pratilipiId ),
				html.getBytes( Charset.forName( "UTF-8" ) ), "text/html" );
		blobAccessor.createOrUpdateBlob( blobEntry );
	}
	
}
