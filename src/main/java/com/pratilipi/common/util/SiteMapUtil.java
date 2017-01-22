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

	private static String _getUrlSnippet( String loc, Date lastMod, String changeFreq, String priority ) {

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
		if( changeFreq != null )
			url.append( "<changefreq>" ).append( changeFreq ).append( "</changefreq>" ).append( LINE_SEPARATOR );
		if( priority != null )
			url.append( "<priority>" ).append( priority ).append( "</priority>" ).append( LINE_SEPARATOR );
		url.append( "</url>" ).append( LINE_SEPARATOR );

		return url.toString();

	}

	private static String _getSiteMapIndex( Website website ) {

//		<?xml version="1.0" encoding="UTF-8"?>
//		<sitemapindex xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
//			<sitemap>
//				<loc>http://www.example.com/sitemap1.xml.gz</loc>
//				<lastmod>2004-10-01T18:23:17+00:00</lastmod>
//			</sitemap>
//			<sitemap>
//				<loc>http://www.example.com/sitemap2.xml.gz</loc>
//			</sitemap>
//		</sitemapindex>

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
			break;
		}

		// Adding LIST sitemap
		siteMapIndex.append( _getSiteMapSnippet( "http://" + website.getHostName() + "/" + "LIST.xml", null ) );

		// Adding STATIC sitemap
		siteMapIndex.append( _getSiteMapSnippet( "http://" + website.getHostName() + "/" + "STATIC.xml", null ) );

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

	private static String _getSiteMapPage( String url, Website website ) {

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
					"weekly", 
					"0.6" 
			));
			siteMap.append( _getUrlSnippet(
					"http://" + website.getHostName() + "/read?id=" + pratilipiId,
					pratilipi.getLastUpdated(), 
					"weekly", 
					"0.6" 
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
					"weekly", 
					"0.6" 
			));
		}

		for( Long blogId : blogIdList ) {
			Page page = pages.get( blogId );
			siteMap.append( _getUrlSnippet( 
					"http://" + website.getHostName() + ( page.getUriAlias() != null ? page.getUriAlias() : page.getUri() ), 
					null, 
					"weekly", 
					null 
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
					"weekly", 
					"0.6" 
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
					"weekly", 
					"0.6" 
			));
		}

		siteMap.append( "</urlset>" );
		return siteMap.toString();

	}

	private static String _getSiteMapList( Website website ) {

		File folder = new File( DataAccessor.class.getResource( DataAccessorGaeImpl.CURATED_DATA_FOLDER ).getFile() );
		File[] listOfFiles = folder.listFiles();

		StringBuilder siteMap = new StringBuilder( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + LINE_SEPARATOR );
		siteMap.append( "<urlset xmlns=\"" + SITEMAP_NAMESPACE + "\">" + LINE_SEPARATOR );

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
					"daily",
					"0.9"
			));
		}

		siteMap.append( "</urlset>" );
		return siteMap.toString();

	}

	private static String _getSiteMapStatic( Website website ) {

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

		StringBuilder siteMap = new StringBuilder( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + LINE_SEPARATOR );
		siteMap.append( "<urlset xmlns=\"" + SITEMAP_NAMESPACE + "\">" + LINE_SEPARATOR );
		for( String fileName : fileNames ) {
			siteMap.append( _getUrlSnippet(
					"http://" + website.getHostName() + "/" + fileName.replace( "_", "/" ),
					null,
					"monthly",
					"0.4"
			));
		}

		siteMap.append( "</urlset>" );
		return siteMap.toString();

	}

	public static String getSiteMapString( String fileName, Website website ) {

		String loc = fileName.substring( "sitemap-".length(), fileName.lastIndexOf( "." ) );

		if( loc.equals( "INDEX" ) )
			return _getSiteMapIndex( website );

		else if( loc.equals( "LIST" ) )
			return _getSiteMapList( website );

		else if( loc.equals( "STATIC" ) )
			return _getSiteMapStatic( website );

		else if( loc.startsWith( "PAGE-" ) )
			return _getSiteMapPage( loc, website );


		return null;

	}

}
