package com.pratilipi.commons.server;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PratilipiContentUtil {
	
	private static final Logger logger =
			Logger.getLogger( PratilipiContentUtil.class.getName() );


	private static final String pageBreak = "<div style=\"page-break-after:always\"></div>";
	private static final Pattern pageBreakPattern = Pattern.compile(
			"<div style=\"page-break-after:always\"></div>" // Pratilipi
			+ "|"
			+ "<div\\s+style=\"page-break-(before|after).+?>(.+?)</div>" // CK Editor
			+ "|"
			+ "<hr\\s+style=\"page-break-(before|after).+?>" // MS Word
			);

	
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
			
				if( pageCount == pageNo )
					return content.substring( startIndex, matcher.start() );
			
			} else {
				endIndex = content.length();
				logger.log( Level.INFO, "Page " + pageCount + " length: "
						+ ( endIndex - startIndex )
						+ " (" + startIndex + " - " + endIndex + ")");
				
				if( pageCount == pageNo )
					return content.substring( startIndex, endIndex );

			}
		}
		
		return null;
	}
	
	public String updateContent( int pageNo, String pageContent ) {
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
				
				if( pageCount == pageNo ) {
					logger.log( Level.INFO, "Updating page " + pageCount + ". Page length: "
							+ ( matcher.start() - startIndex )
							+ ". Updated page length: " + pageContent.length() );
					content = content.substring( 0, startIndex )
							+ pageContent
							+ content.substring( matcher.start() );
					matcher = pageBreakPattern.matcher( content );
					break;
				}
				
			} else {
				endIndex = content.length();
				logger.log( Level.INFO, "Page " + pageCount + " length: "
						+ ( endIndex - startIndex )
						+ " (" + startIndex + " - " + endIndex + ")");
			
				if( pageCount == pageNo ) {
					logger.log( Level.INFO, "Updating page " + pageCount + ". Page length: "
							+ ( content.length() - startIndex )
							+ ". Updated page length: " + pageContent.length() );
					content = content.substring( 0, startIndex )
							+ pageContent;
					matcher = pageBreakPattern.matcher( content );
					break;
				}
				
			}
		}
		
		logger.log( Level.INFO, "Updated content length: " + content.length() );

		return content;
	}
	
	public String insertPage( int pageNo ) {
		logger.log( Level.INFO, "Content length: " + content.length() );

		matcher.reset();

		int pageCount = 0;
		int startIndex = 0;
		int endIndex = 0;
		
		while( endIndex < content.length() ) {
			pageCount++;
			startIndex = endIndex;

			if( pageCount == pageNo )
				break;

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
		
		
		if( pageCount == pageNo ) {
			logger.log( Level.INFO, "Inserting page " + pageNo
					+ ". New page length: " + pageBreak.length() );
			content = content.substring( 0, startIndex )
					+ pageBreak
					+ content.substring( startIndex );
			return "";
			
		} else if( pageCount + 1 == pageNo ) {
			logger.log( Level.INFO, "Inserting page "
					+ pageNo + ". New page length: " + pageBreak.length() );
			content = content + pageBreak;
			return "";
		}
		
		logger.log( Level.INFO, "Updated content length: " + content.length() );

		return null;
	}
	
}
