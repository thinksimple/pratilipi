package com.pratilipi.common.util;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.pratilipi.common.type.AuthorState;
import com.pratilipi.common.type.BlogPostState;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.type.Website;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DataAccessorGaeImpl;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.BlogPost;
import com.pratilipi.data.type.Event;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.gae.PageEntity;
import com.pratilipi.site.PratilipiSite;


public class SiteMapUtil {

	private static final String LINE_SEPARATOR = "\n";
	private static final Long PAGE_COUNT = 1000000000000L;
	private static final String SITEMAP_NAMESPACE = "http://www.sitemaps.org/schemas/sitemap/0.9";

	public enum PageTypeSiteMapValues {

//		Change Frequency Values: always, hourly, daily, weekly, monthly, yearly, never
//		priority: ranges from 0-1, Default - 0.5

		PRATILIPI( "daily", "0.7" ),
		READ( "daily", "0.7" ),
		AUTHOR( "daily", "0.6" ),
		EVENT_LIST( "weekly", null ),
		EVENT( "weekly", "0.6" ),
		BLOG( "weekly", null ),
		BLOG_POST( "weekly", "0.6" ),
		CATEGORY_LIST( "hourly", "0.8" ),
		HOME( "hourly", "0.8" ),
		STATIC( "monthly", null )
		;

		private String changeFrequency;

		private String priority;


		private PageTypeSiteMapValues( String changeFrequency, String priority ) {
			this.changeFrequency = changeFrequency;
			this.priority = priority;
		}

		public String getChangeFrequency() {
			return changeFrequency;
		}

		public String getPriority() {
			return priority;
		}

	}

	private static String _getEntityEscapedUrl( String url ) {
		return 	url.replace( "&", "&amp;" )
					.replace( "'", "&apos;" )
					.replace( "\"", "&quot;" )
					.replace( ">", "&gt;" )
					.replace( "<", "&lt;" );
	}

	private static String _getSiteMapSnippet( String loc, Date lastMod ) {	

//		<sitemap>
//			<loc>http://www.example.com/sitemap1.xml.gz</loc>
//			<lastmod>2004-10-01</lastmod>
//		</sitemap>

		DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
		dateFormat.setTimeZone( TimeZone.getTimeZone( "Asia/Kolkata" ) );

		StringBuilder sitemap = new StringBuilder( "<sitemap>" + LINE_SEPARATOR );
		sitemap.append( "<loc>" ).append( _getEntityEscapedUrl( loc ) ).append( "</loc>" ).append( LINE_SEPARATOR );
		if( lastMod != null )
			sitemap.append( "<lastmod>" ).append( dateFormat.format( lastMod ) ).append( "</lastmod>" ).append( LINE_SEPARATOR );
		sitemap.append( "</sitemap>" ).append( LINE_SEPARATOR );

		return sitemap.toString();

	}

	private static String _getUrlSnippet( String loc, Date lastMod, PageTypeSiteMapValues sitemapValue ) {

//		<url>
//			<loc>http://www.example.com/</loc>
//			<lastmod>2005-01-01</lastmod>
//			<changefreq>monthly</changefreq>
//			<priority>0.8</priority>
//		</url>

		DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
		dateFormat.setTimeZone( TimeZone.getTimeZone( "Asia/Kolkata" ) );

		StringBuilder url = new StringBuilder( "<url>" + LINE_SEPARATOR );
		url.append( "<loc>" ).append( _getEntityEscapedUrl( loc ) ).append( "</loc>" ).append( LINE_SEPARATOR );
		if( lastMod != null )
			url.append( "<lastmod>" ).append( dateFormat.format( lastMod ) ).append( "</lastmod>" ).append( LINE_SEPARATOR );
		if( sitemapValue.getChangeFrequency() != null )
			url.append( "<changefreq>" ).append( sitemapValue.getChangeFrequency() ).append( "</changefreq>" ).append( LINE_SEPARATOR );
		if( sitemapValue.getPriority() != null )
			url.append( "<priority>" ).append( sitemapValue.getPriority() ).append( "</priority>" ).append( LINE_SEPARATOR );
		url.append( "</url>" ).append( LINE_SEPARATOR );

		return url.toString();

	}


//	<?xml version="1.0" encoding="UTF-8"?>
//	<sitemapindex xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
//		<sitemap>
//			<loc>http://www.example.com/sitemap1.xml.gz</loc>
//			<lastmod>2004-10-01T18:23:17+00:00</lastmod>
//		</sitemap>
//		<sitemap>
//			<loc>http://www.example.com/sitemap2.xml.gz</loc>
//		</sitemap>
//	</sitemapindex>
	private static String _getSiteMapForTypeIndex( Website website ) {

		Long startIndex = ObjectifyService.ofy().load()
				.type( PageEntity.class )
				.keys()
				.first()
				.now()
				.getId();
		Long endIndex = ObjectifyService.ofy().load()
				.type( PageEntity.class )
				.order( "-__key__" )
				.keys()
				.first()
				.now()
				.getId();

		StringBuilder siteMapIndex = new StringBuilder( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + LINE_SEPARATOR );
		siteMapIndex.append( "<sitemapindex xmlns=\"" + SITEMAP_NAMESPACE + "\">" + LINE_SEPARATOR );

		for( Long i = startIndex; i <= endIndex; i += PAGE_COUNT ) {
			String sitemapLoc = "sitemap-" + "PAGE-" + "startAt_" + i + "-" + "count_" + PAGE_COUNT + ".xml";
			siteMapIndex.append( _getSiteMapSnippet( "http://" + website.getHostName() + "/" + sitemapLoc, null ) );
		}

		// Adding sitemap to index pages without PAGE entites
		siteMapIndex.append( _getSiteMapSnippet( "http://" + website.getHostName() + "/" + "OTHER.xml", null ) );

		siteMapIndex.append( "</sitemapindex>" );
		return siteMapIndex.toString();

	}

//	<?xml version="1.0" encoding="UTF-8"?>
//	<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
//		<url>
//			<loc>http://www.example.com/</loc>
//			<lastmod>2005-01-01</lastmod>
//			<changefreq>monthly</changefreq>
//			<priority>0.8</priority>
//		</url>
//	</urlset>

	private static String _getSiteMapForTypePage( String url, Website website ) {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		//	PAGE-startAt_4503702338535424-count_1000000000000
		Long startAt = Long.parseLong( 
				url.substring( url.indexOf( "startAt_" ) + "startAt_".length(), url.indexOf( "-count" ) ) );
		Long count = Long.parseLong(
				url.substring( url.indexOf( "count_" ) + "count_".length() ) );

		List<PageEntity> pageEntityList = ObjectifyService.ofy().load()
				.type( PageEntity.class )
				.filterKey( ">=", Key.create( PageEntity.class, startAt ) )
				.filterKey( "<", Key.create( PageEntity.class, startAt + count ) )
				.list();

		Map<Long, Page> pages = new HashMap<>( pageEntityList.size() );
		List<Long> pratilipiIdList = new ArrayList<>();
		List<Long> authorIdList = new ArrayList<>();
		List<Long> blogIdList = new ArrayList<>();
		List<Long> blogPostIdList = new ArrayList<>();
		List<Long> eventIdList = new ArrayList<>();

		for( Page page : pageEntityList ) {

			switch( page.getType() ) {
				case PRATILIPI:
					pratilipiIdList.add( page.getPrimaryContentId() );
					break;
				case AUTHOR:
					authorIdList.add( page.getPrimaryContentId() );
					break;
				case BLOG:
					blogIdList.add( page.getPrimaryContentId() );
					break;
				case BLOG_POST:
					blogPostIdList.add( page.getPrimaryContentId() );
					break;
				case EVENT:
					eventIdList.add( page.getPrimaryContentId() );
					break;
				default:
					break;
			}

			pages.put( page.getPrimaryContentId(), page );

		}

		Map<Long, Pratilipi> pratilipis = dataAccessor.getPratilipis( pratilipiIdList );
		Map<Long, Author> authors = dataAccessor.getAuthors( authorIdList );
		Map<Long, BlogPost> blogPosts = dataAccessor.getBlogPosts( blogPostIdList );
		Map<Long, Event> events = dataAccessor.getEvents( eventIdList );

		StringBuilder siteMap = new StringBuilder( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + LINE_SEPARATOR );
		siteMap.append( "<urlset xmlns=\"" + SITEMAP_NAMESPACE + "\">" + LINE_SEPARATOR );
		Language language = website.getFilterLanguage();

		for( Long pratilipiId : pratilipiIdList ) {

			Pratilipi pratilipi = pratilipis.get( pratilipiId );
			if( language != pratilipi.getLanguage() || pratilipi.getState() != PratilipiState.PUBLISHED )
				continue;

			Page page = pages.get( pratilipiId );
			siteMap.append( _getUrlSnippet( 
					"http://" + website.getHostName() + ( page.getUriAlias() != null ? page.getUriAlias() : page.getUri() ), 
					pratilipi.getLastUpdated(), 
					PageTypeSiteMapValues.PRATILIPI 
			));
			siteMap.append( _getUrlSnippet(
					"http://" + website.getHostName() + "/read?id=" + pratilipiId,
					pratilipi.getLastUpdated(), 
					PageTypeSiteMapValues.READ 
			));
		}

		for( Long authorId : authorIdList ) {

			Author author = authors.get( authorId );
			if( language != author.getLanguage() || author.getState() != AuthorState.ACTIVE )
				continue;

			Page page = pages.get( authorId );
			siteMap.append( _getUrlSnippet( 
					"http://" + website.getHostName() + ( page.getUriAlias() != null ? page.getUriAlias() : page.getUri() ), 
					author.getLastUpdated(), 
					PageTypeSiteMapValues.AUTHOR 
			));
		}

		for( Long blogId : blogIdList ) {
			Page page = pages.get( blogId );
			siteMap.append( _getUrlSnippet( 
					"http://" + website.getHostName() + ( page.getUriAlias() != null ? page.getUriAlias() : page.getUri() ), 
					null, 
					PageTypeSiteMapValues.BLOG 
			));
		}

		for( Long blogPostId : blogPostIdList ) {

			BlogPost blogPost = blogPosts.get( blogPostId );
			if( language != blogPost.getLanguage() || blogPost.getState() != BlogPostState.PUBLISHED )
				continue;

			Page page = pages.get( blogPostId );
			siteMap.append( _getUrlSnippet( 
					"http://" + website.getHostName() + ( page.getUriAlias() != null ? page.getUriAlias() : page.getUri() ), 
					blogPost.getLastUpdated(), 
					PageTypeSiteMapValues.BLOG_POST 
			));
		}

		for( Long eventId : eventIdList ) {

			Event event = events.get( eventId );
			if( language != event.getLanguage() )
				continue;

			Page page = pages.get( eventId );
			siteMap.append( _getUrlSnippet( 
					"http://" + website.getHostName() + ( page.getUriAlias() != null ? page.getUriAlias() : page.getUri() ), 
					event.getLastUpdated(), 
					PageTypeSiteMapValues.EVENT 
			));
		}

		siteMap.append( "</urlset>" );
		return siteMap.toString();

	}

	private static String _getUrlSetForTypeCategoryList( Website website ) {

		File folder = new File( DataAccessor.class.getResource( DataAccessorGaeImpl.CURATED_DATA_FOLDER ).getFile() );
		File[] listOfFiles = folder.listFiles();

		StringBuilder siteMap = new StringBuilder();
		String fileNamePrefix = "list." + website.getFilterLanguage().getCode();
		for( int i = 0; i < listOfFiles.length; i++ ) {
			File file = listOfFiles[i];
			if( ! file.isFile() )
				continue;

			if( ! file.getName().startsWith( fileNamePrefix ) )
				continue;

			siteMap.append( _getUrlSnippet(
					"http://" + website.getHostName() + "/" + file.getName().substring( fileNamePrefix.length() + 1 ),
					null,
					PageTypeSiteMapValues.CATEGORY_LIST
			));
		}

		return siteMap.toString();

	}

	private static String _getUrlSetForTypeStatic( Website website ) {

		File folder = new File( PratilipiSite.class.getResource( PratilipiSite.dataFilePrefix ).getFile() );
		File[] listOfFiles = folder.listFiles();

		String fileNamePrefix = "static." + website.getFilterLanguage().getCode();
		String fileNameEnPrefix = "static." + Language.ENGLISH.getCode();
		Set<String> fileNames = new HashSet<>();
		for( int i = 0; i < listOfFiles.length; i++ ) {
			File file = listOfFiles[i];
			if( ! file.isFile() )
				continue;
			if( file.getName().startsWith( fileNamePrefix ) || file.getName().startsWith( fileNameEnPrefix ) )
				fileNames.add( file.getName().substring( file.getName().lastIndexOf( "." ) + 1 ) );
		}

		StringBuilder siteMapUrlList = new StringBuilder();
		for( String fileName : fileNames ) {
			siteMapUrlList.append( _getUrlSnippet(
					"http://" + website.getHostName() + "/" + fileName.replace( "_", "/" ),
					null,
					PageTypeSiteMapValues.STATIC
			));
		}

		return siteMapUrlList.toString();

	}

	private static String _getSiteMapForTypeOther( Website website ) {
		StringBuilder siteMap = new StringBuilder( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + LINE_SEPARATOR );
		siteMap.append( "<urlset xmlns=\"" + SITEMAP_NAMESPACE + "\">" + LINE_SEPARATOR );
		// Home Page
		siteMap.append( _getUrlSnippet(
				"http://" + website.getHostName(),
				null,
				PageTypeSiteMapValues.HOME
		));
		// /events page
		siteMap.append( _getUrlSnippet(
				"http://" + website.getHostName() + "/events",
				null,
				PageTypeSiteMapValues.EVENT_LIST
		));
		// Category List pages
		siteMap.append( _getUrlSetForTypeCategoryList( website ) );
		// Static pages
		siteMap.append( _getUrlSetForTypeStatic( website ) );

		siteMap.append( "</urlset>" );
		return siteMap.toString();
	}

	public static String getSiteMapString( String fileName, Website website ) {

		String loc = fileName.substring( "/sitemap-".length(), fileName.lastIndexOf( "." ) );

		if( loc.equals( "INDEX" ) )
			return _getSiteMapForTypeIndex( website );

		else if( loc.startsWith( "PAGE-" ) )
			return _getSiteMapForTypePage( loc, website );

		else if( loc.equals( "OTHER" ) )
			return _getSiteMapForTypeOther( website );

		else 
			return null;

	}

}
