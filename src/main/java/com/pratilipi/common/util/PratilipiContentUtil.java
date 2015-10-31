package com.pratilipi.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pratilipi.data.client.PratilipiContentData;
import com.pratilipi.data.client.PratilipiContentData.Chapter;
import com.pratilipi.data.client.PratilipiContentData.Page;
import com.pratilipi.data.client.PratilipiContentData.Pagelet;
import com.pratilipi.data.client.PratilipiContentData.PageletType;


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
	private static final Pattern titlePattern = Pattern.compile(
			"<h1.*?>(<.+?>)*(?<title>.+?)(</.+?>)*</h1>"
			+ "|"
			+ "<h2.*?>(<.+?>)*(?<subTitle>.+?)(</.+?>)*</h2>" );
	
	private static final String nonKeywordsPattern = "&nbsp;|&lt;|&gt;|&amp;|&cent;|&pound;|&yen;|&euro;|&copy;|&reg;|<[^>]*>|[!-/:-@\\[-`{-~]|।";

	
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
	
	public String generateIndex() {
		logger.log( Level.INFO, "Content length: " + content.length() );

		matcher.reset();

		int pageCount = 0;
		int startIndex = 0;
		int endIndex = 0;
		JsonArray index = new JsonArray();
		
		while( endIndex < content.length() ) {
			pageCount++;
			startIndex = endIndex;
		
			String pageContent;
			
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

			
			Matcher titleMatcher = titlePattern.matcher( pageContent );
			while( titleMatcher.find() ) {
				logger.log( Level.INFO, titleMatcher.group() );

				String title = titleMatcher.group( "title" );
				String subTitle = titleMatcher.group( "subTitle" );
				
				if( title != null && !( title = title.replaceAll( "&nbsp;|<[/]?strong>", " " ).trim() ).isEmpty() ) {
					logger.log( Level.INFO, "Title Found: " + title );
					JsonObject indexItem = new JsonObject();
					indexItem.addProperty( "title", title );
					indexItem.addProperty( "pageNo", pageCount );
					indexItem.addProperty( "level", 0 );
					index.add( indexItem );
					
				} else if( subTitle != null && !( subTitle = subTitle.replaceAll( "&nbsp;", " " ).trim() ).isEmpty() ) {
					logger.log( Level.INFO, "Sub-Title Found: " + subTitle );
					JsonObject indexItem = new JsonObject();
					indexItem.addProperty( "title", subTitle );
					indexItem.addProperty( "pageNo", pageCount );
					indexItem.addProperty( "level", 1 );
					index.add( indexItem );
				}
			}
			
		}
		
		return new Gson().toJson( index );
	}

	public String generateKeywords() {
		String[] words = this.content
				.replaceAll( nonKeywordsPattern, " " )
				.split( "\\s+(\\w\\s+)*" );
		
		Set<String> wordSet = new HashSet<String>( Arrays.asList( words ) );
		words = wordSet.toArray( new String[ wordSet.size() ] );
		
		String keywords = "";
		for( String word : words  )
			if( word.length() > 1 )
				keywords = keywords + word + " ";
		
		return keywords.trim();
	}

	public PratilipiContentData toPratilipiContentData() {
		
		ArrayList<Chapter> chapterList = new ArrayList<>(0);
		Integer pageCount = getPageCount();

		// Iterating through chapters.
		for( Integer i = 1; i <= pageCount; i++ ) {
			
			ArrayList<Page> pageList = new ArrayList<>(0);
			ArrayList<Pagelet> pageletList = new ArrayList<>(0);
			
			// Getting the chapters.
			String chapter = getContent( i );
			
			// Parsing the String to Jsoup.
			Document document = Jsoup.parse( chapter );
			
			// Iterating through h1 to h6 for getting the title.
			String title = null;
			for( Integer h = 1; h <= 6; h ++ ) {
				Elements elements = document.getElementsByTag( "h" + h ); 
				if( elements == null || elements.isEmpty() )
					continue;
				for( Element element : elements ) {
					if( ! element.text().isEmpty() ) {
						title = element.text().trim();
						i = 7;
						break;
					}
				}
			}
			
			List<Node> nodes = document.body().childNodes();
			
			// Creating Pagelets
			for( Node node : nodes ) {
				if( node.getClass().getSimpleName().equals( "Element" ) ) {
					Element element = (Element) node; 
					if( element.tag().toString().toLowerCase().equals( "img" ) ) 
						pageletList.add( new Pagelet( element.attr( "src" ), PageletType.IMAGE ) ); 
					else  if( ! element.text().trim().isEmpty() && ! element.text().trim().equals( title ) ) 
						pageletList.add( new Pagelet( element.text(), PageletType.TEXT ) );
				}
				else if( node.getClass().getSimpleName().equals( "TextNode" ) && ! node.toString().trim().isEmpty() )
					pageletList.add( new Pagelet( node.toString().trim(), PageletType.TEXT ) );
			}
			
			pageList.add( new Page( pageletList ) );
			chapterList.add( new Chapter( title, pageList ) );

		}
		
		return new PratilipiContentData( chapterList );
		
	}
	
}
