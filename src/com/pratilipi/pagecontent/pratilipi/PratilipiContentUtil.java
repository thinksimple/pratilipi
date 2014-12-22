package com.pratilipi.pagecontent.pratilipi;

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
		
			if( matcher.find() )
				endIndex = matcher.end();
			else
				endIndex = content.length();

			logger.log( Level.INFO,
					"Page " + pageCount + " length: " + ( endIndex - startIndex )
					+ " (" + startIndex + " - " + endIndex + ") "
					+ ( endIndex == content.length() ? "" : matcher.group() ) );
		}
		
		return pageCount;
	}

	public String getContent( int pageNo ) {
		logger.log( Level.INFO, "Content length: " + content.length() );

		matcher.reset();

		int pageCount = 0;
		int startIndex = 0;
		int endIndex = 0;
		String pageContent = null;

		while( endIndex < content.length() ) {
			pageCount++;
			startIndex = endIndex;
		
			if( matcher.find() ) {
				endIndex = matcher.end();
				pageContent = content.substring( startIndex, matcher.start() );
			} else {
				endIndex = content.length();
				pageContent = content.substring( startIndex );
			}
		
			logger.log( Level.INFO,
					"Page " + pageCount + " length: " + ( endIndex - startIndex )
					+ " (" + startIndex + " - " + endIndex + ") "
					+ ( endIndex == content.length() ? "" : matcher.group() ) );

			if( pageCount == pageNo )
				break;
		}

		return pageContent;
	}
	
	public String updateContent( int pageNo, String pageContent ) {
		return updateContent( pageNo, pageContent, false );
	}
	
	public String updateContent( int pageNo, String pageContent, boolean insertNew ) {
		matcher.reset();

		int pageCount = 0;
		int startIndex = 0;
		int endIndex = 0;
		
		while( endIndex < content.length() ) {
			pageCount++;
			startIndex = endIndex;

			if( matcher.find() ) {
				endIndex = matcher.end();
				logger.log( Level.INFO,
						"Page " + pageCount + " length: " + ( endIndex - startIndex )
						+ " (" + startIndex + " - " + endIndex + ") " + matcher.group() );
			
			} else {
				content = content + pageBreak;
				endIndex = content.length();
				logger.log( Level.INFO,
						"Page " + pageCount + " length: " + ( endIndex - startIndex )
						+ " (" + startIndex + " - " + endIndex + ") " + pageBreak );
			}
			

			if( pageCount == pageNo )
				break;
		}
		
		logger.log( Level.INFO, "Content length: " + content.length() );

		if( pageCount == pageNo ) {
			if( insertNew ) {
				pageContent = pageContent + pageBreak;
				logger.log( Level.INFO,
						"Inserting page " + pageNo + ". "
						+ "New page length: " + pageContent.length() + "." );
				content = content.substring( 0, startIndex ) + pageContent + content.substring( startIndex );

			} else if( !pageContent.isEmpty() ) { // && !insertNew
				pageContent = pageContent + pageBreak;
				logger.log( Level.INFO,
						"Updating page " + pageNo + ". "
						+ "Page length: " + ( endIndex - startIndex ) + ". "
						+ "Updated page length: " + pageContent.length() + "." );
				content = content.substring( 0, startIndex ) + pageContent + content.substring( endIndex );

			} else { //  if( pageContent.isEmpty() && !insertNew )
				logger.log( Level.INFO, "Deleting page " + pageNo + "." );
				content = content.substring( 0, startIndex ) + content.substring( endIndex );

			}
			matcher = pageBreakPattern.matcher( content );

		} else if( insertNew && pageCount + 1 == pageNo ) {
			pageContent = pageContent + pageBreak;
			logger.log( Level.INFO,
					"Inserting page " + pageNo + ". "
					+ "New page length: " + pageContent.length() + "." );
			content = content + pageContent;
			matcher = pageBreakPattern.matcher( content );
		}
		
		logger.log( Level.INFO, "Updated content length: " + content.length() );

		return content;
	}
	
}
