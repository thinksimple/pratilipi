package com.pratilipi.common.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.BlobAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.BlobEntry;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.PratilipiContentDoc;
import com.pratilipi.data.type.doc.PratilipiContentDocImpl.Chapter;
import com.pratilipi.data.type.doc.PratilipiContentDocImpl.Page;
import com.pratilipi.data.type.doc.PratilipiContentDocImpl.Pagelet;


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

	
	private Pratilipi pratilipi;
	private String content;
	private Matcher matcher;
	
	
	public PratilipiContentUtil( String content ) {
		this.content = content;
		matcher = pageBreakPattern.matcher( content );
	}

	public PratilipiContentUtil( Pratilipi pratilipi, String content ) {
		this.pratilipi = pratilipi;
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

	public PratilipiContentDoc toPratilipiContentData() throws UnexpectedServerException {
		
		PratilipiContentDoc pcDoc = DataAccessorFactory.getDocAccessor().getPratilipiContentDoc();
		
		List<Pagelet> pageletList = _createPageletList( Jsoup.parse( content ) );
		
		if( pageletList.get( 0 ).getType() != PratilipiContentDoc.PageletType.HEAD_1 )
			pcDoc.addChapter( new Chapter( pratilipi.getTitle() == null ? pratilipi.getTitle() : pratilipi.getTitleEn() ) );
		
		Chapter chapter = null;
		for( Pagelet pagelet : pageletList ) {
			if( pagelet.getType() == PratilipiContentDoc.PageletType.HEAD_1
					|| pagelet.getType() == PratilipiContentDoc.PageletType.HEAD_2 ) {
				
				Page page = new Page();
				page.addPagelet( pagelet );
				chapter = new Chapter( pratilipi.getTitle() == null ? pratilipi.getTitle() : pratilipi.getTitleEn() );
				chapter.addPage( page );
				pcDoc.addChapter( chapter );
				
			} else {
				
				chapter.getPage( 0 ).addPagelet( pagelet );;
				
			}
		}
		
		return pcDoc;
		
	}
	
	private List<Pagelet> _createPageletList( Node node ) throws UnexpectedServerException {
		
		List<Pagelet> pageletList = new LinkedList<>();
		
		Pagelet pagelet = null;
		for( Node childNode : node.childNodes() ) {
			
			if( childNode.nodeName().equals( "body" )
					|| childNode.nodeName().equals( "div" )
					|| childNode.nodeName().equals( "p" ) ) {
				
				pagelet = null;
				pageletList.addAll( _createPageletList( childNode ) );
				
			} else if( childNode.nodeName().equals( "h1" ) ) {
				
				pagelet = null;
				pageletList.add( new Pagelet( ( (Element) childNode ).text().trim(), PratilipiContentDoc.PageletType.HEAD_1 ) );
				
			} else if( childNode.nodeName().equals( "h2" ) ) {
				
				pagelet = null;
				pageletList.add( new Pagelet( ( (Element) childNode ).text().trim(), PratilipiContentDoc.PageletType.HEAD_2 ) );
				
			} else if( childNode.nodeName().equals( "img" ) ) {
				
				pagelet = null;
				
				BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
				BlobEntry blobEntry = null;
				String imagUrl = childNode.attr( "src" );
				String imageName = null;
				if( imagUrl.startsWith( "http" ) ) {
					imageName = imagUrl.replaceAll( "[:/.?=&+]+", "_" );
					String fileName = "pratilipi/" + pratilipi.getId() + "/images/" + imagUrl.replaceAll( "[:/.?=&+]+", "_" );
					blobEntry = blobAccessor.getBlob( fileName );
					if( blobEntry == null ) {
						blobEntry = HttpUtil.doGet( imagUrl );
						blobEntry.setName( fileName );
						blobAccessor.createOrUpdateBlob( blobEntry );
					}
				} else {
					imageName = imagUrl.substring( imagUrl.indexOf( "name=" ) + 5 );
					if( imageName.indexOf( '&' ) != -1 )
						imageName = imageName.substring( 0, imageName.indexOf( '&' ) );
					String fileName = "pratilipi/" + pratilipi.getId() + "/images/" + imageName;
					blobEntry = blobAccessor.getBlob( fileName );
				}
				
				JsonObject imgData = new JsonObject();
				imgData.addProperty( "name", imageName );
				imgData.addProperty( "height", ImageUtil.getHeight( blobEntry.getData() ) );
				imgData.addProperty( "width", ImageUtil.getWidth( blobEntry.getData() ) );
				
				pageletList.add( new Pagelet( imgData, PratilipiContentDoc.PageletType.IMAGE ) );
				
			} else if( childNode.nodeName().equals( "br" ) ) {
				
				pagelet = null;
				
			} else {
				
				String text  = childNode.getClass() == TextNode.class
						? ( (TextNode) childNode ).text().trim()
						: ( (Element) childNode ).text().trim();
				if( text.isEmpty() )
					continue;
				if( pagelet == null ) {
					pagelet = new Pagelet( text, PratilipiContentDoc.PageletType.TEXT );
					pageletList.add( pagelet );
				} else {
					pagelet = new Pagelet( pagelet.getData() + " " + text, PratilipiContentDoc.PageletType.TEXT );
				}
				
			}
			
		}
		
		return pageletList;
		
	}
	
}
