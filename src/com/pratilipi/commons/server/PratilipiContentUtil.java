package com.pratilipi.commons.server;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PratilipiContentUtil {
	
	private static final Logger logger =
			Logger.getLogger( PratilipiContentUtil.class.getName() );

	private static final Pattern pageBreakPattern = Pattern.compile(
			"(<hr\\s+style=\"page-break-(before|after).+?>)"
			+ "|"
			+ "(<div\\s+style=\"page-break-(before|after).+?>(.+?)</div>)" );

	
	private String content;
	private Matcher matcher;
	

	public PratilipiContentUtil( String content ) {
		this.content = content;
		matcher = pageBreakPattern.matcher( content );
	}

	public int getPageCount() {
		logger.log( Level.INFO, "Content length: " + content.length() );

		matcher.reset();
		
		int pageCount = 0;
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
		}
		
		return pageCount;
	}

	public String getContent( int pageNo ) {
		logger.log( Level.INFO, "Content length: " + content.length() );

		matcher.reset();

		int pageCount = 0;
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
				return content.substring( startIndex, endIndex );
		}
		
		return null;
	}
	
}
