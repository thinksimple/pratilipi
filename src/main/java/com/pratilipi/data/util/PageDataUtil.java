package com.pratilipi.data.util;

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

import com.google.gson.JsonObject;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.type.AuthorState;
import com.pratilipi.common.type.BlogPostState;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PageType;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.type.RequestParameter;
import com.pratilipi.common.type.Website;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.BlogPost;
import com.pratilipi.data.type.Event;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.gae.PageEntity;
import com.pratilipi.site.PratilipiSite;


public class PageDataUtil {

	private static final String LINE_SEPARATOR = "\n";
	private static final Long SITEMAP_PAGE_COUNT = 1000000000000L;
	private static final String SITEMAP_NAMESPACE = "http://www.sitemaps.org/schemas/sitemap/0.9";


	public static String getSitemap( String type, String cursor, Website website, boolean basicMode ) throws InvalidArgumentException {

		if( type == null ) // Sitemap Index
			return _getSitemapIndex( basicMode ? website.getMobileHostName() : website.getHostName() );

		if( type.equals( "page" ) )
			return _getSitemapForTypePage(
					Long.parseLong( cursor ),
					basicMode ? website.getMobileHostName() : website.getHostName(),
					website.getFilterLanguage() );

		if( type.equals( "other" ) )
			return _getSitemapForTypeOther(
					basicMode ? website.getMobileHostName() : website.getHostName(),
					website.getFilterLanguage() );

		JsonObject errMessage = new JsonObject();
		errMessage.addProperty( "location", "Invalid Location" );
		throw new InvalidArgumentException( errMessage );

	}


	private static String _getSitemapIndex( String hostName ) {

/*		<?xml version="1.0" encoding="UTF-8"?>
		<sitemapindex xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
			<sitemap>
				<loc>http://www.example.com/sitemap1.xml.gz</loc>
				<lastmod>2004-10-01T18:23:17+00:00</lastmod>
			</sitemap>
			<sitemap>
				<loc>http://www.example.com/sitemap2.xml.gz</loc>
			</sitemap>
		</sitemapindex> */


		Long pageIdFirst = ObjectifyService.ofy().load()
				.type( PageEntity.class )
				.keys()
				.first()
				.now()
				.getId();

		Long pageIdLast = ObjectifyService.ofy().load()
				.type( PageEntity.class )
				.order( "-__key__" )
				.keys()
				.first()
				.now()
				.getId();


		StringBuilder sitemapIndex = new StringBuilder( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + LINE_SEPARATOR );
		sitemapIndex.append( "<sitemapindex xmlns=\"" + SITEMAP_NAMESPACE + "\">" + LINE_SEPARATOR );

		// Pages without PAGE entity
		sitemapIndex.append( _getSitemapIndexEntry( hostName, "other", null, null ) );

		// Pages with PAGE entity
		for( long i = pageIdFirst; i <= pageIdLast; i += SITEMAP_PAGE_COUNT )
			sitemapIndex.append( _getSitemapIndexEntry( hostName, "page", i, null ) );

		sitemapIndex.append( "</sitemapindex>" );

		return sitemapIndex.toString();

	}

	private static String _getSitemapIndexEntry( String hostName, String type, Long cursor, Date lastModified ) {	

/*		<sitemap>
			<loc>http://www.example.com/sitemap.xml.gz</loc>
			<lastmod>2004-10-01</lastmod>
		</sitemap> */

		StringBuilder sitemap = new StringBuilder( "<sitemap>" + LINE_SEPARATOR );

		String loc = "http://" + hostName + "/sitemap";
		if( type != null ) {
			loc = loc + "?" + RequestParameter.SITEMAP_TYPE.getName() + "=" + type;
			if( cursor != null )
				loc = loc + "&" + RequestParameter.SITEMAP_CURSOR.getName() + "=" + cursor;
		}
		sitemap.append( "<loc>" ).append( _getEntityEscapedUrl( loc ) ).append( "</loc>" ).append( LINE_SEPARATOR );

		if( lastModified != null ) {
			DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
			dateFormat.setTimeZone( TimeZone.getTimeZone( "Asia/Kolkata" ) );
			sitemap.append( "<lastmod>" ).append( dateFormat.format( lastModified ) ).append( "</lastmod>" ).append( LINE_SEPARATOR );
		}
		sitemap.append( "</sitemap>" ).append( LINE_SEPARATOR );

		return sitemap.toString();

	}

	
	private static String _getSitemapForTypePage( Long cursor, String hostName, Language language ) {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		List<PageEntity> pageEntityList = ObjectifyService.ofy()
				.load()
				.type( PageEntity.class )
				.filterKey( ">=", Key.create( PageEntity.class, cursor ) )
				.filterKey( "<", Key.create( PageEntity.class, cursor + SITEMAP_PAGE_COUNT ) )
				.list();

		Map<String, String> pageUrlMap = new HashMap<>( pageEntityList.size() );
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
			pageUrlMap.put( page.getType().name() + page.getPrimaryContentId(), page.getUriAlias() != null ? page.getUriAlias() : page.getUri() );
		}


		Map<Long, Pratilipi> pratilipis = dataAccessor.getPratilipis( pratilipiIdList );
		Map<Long, Author> authors = dataAccessor.getAuthors( authorIdList );
		Map<Long, BlogPost> blogPosts = dataAccessor.getBlogPosts( blogPostIdList );
		Map<Long, Event> events = dataAccessor.getEvents( eventIdList );


		StringBuilder sitemap = new StringBuilder( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + LINE_SEPARATOR );
		sitemap.append( "<urlset xmlns=\"" + SITEMAP_NAMESPACE + "\">" + LINE_SEPARATOR );

		for( Long pratilipiId : pratilipiIdList ) {
			Pratilipi pratilipi = pratilipis.get( pratilipiId );
			if( pratilipi.getLanguage() != language )
				continue;
			if( pratilipi.getState() != PratilipiState.PUBLISHED )
				continue;
			sitemap.append( _getSitemapEntry( hostName, pageUrlMap.get( PageType.PRATILIPI.name() + pratilipiId ), pratilipi.getLastUpdated(), "daily", "0.7" ) );
			sitemap.append( _getSitemapEntry( hostName, "/read?pratilipiId=" + pratilipiId, pratilipi.getLastUpdated(), "daily", "0.7" ) );
		}

		for( Long authorId : authorIdList ) {
			Author author = authors.get( authorId );
			if( author.getLanguage() != language )
				continue;
			if( author.getState() != AuthorState.ACTIVE )
				continue;
			sitemap.append( _getSitemapEntry( hostName, pageUrlMap.get( PageType.AUTHOR.name() + authorId ), author.getLastUpdated(), "daily", "0.6" ) );
		}

		for( Long blogId : blogIdList )
			sitemap.append( _getSitemapEntry( hostName, pageUrlMap.get( PageType.BLOG.name() + blogId ), null, "weekly", null ) );

		for( Long blogPostId : blogPostIdList ) {
			BlogPost blogPost = blogPosts.get( blogPostId );
			if( blogPost.getLanguage() != language )
				continue;
			if( blogPost.getState() != BlogPostState.PUBLISHED )
				continue;
			sitemap.append( _getSitemapEntry( hostName, pageUrlMap.get( PageType.BLOG_POST.name() + blogPostId ), blogPost.getLastUpdated(), "weekly", "0.6" ) );
		}

		for( Long eventId : eventIdList ) {
			Event event = events.get( eventId );
			if( event.getLanguage() != language )
				continue;
			sitemap.append( _getSitemapEntry( hostName, pageUrlMap.get( PageType.EVENT.name() + eventId ), event.getLastUpdated(), "weekly", "0.6" ) );
		}

		sitemap.append( "</urlset>" );
		return sitemap.toString();

	}

	private static String _getSitemapForTypeOther( String hostName, Language language ) {

		StringBuilder sitemap = new StringBuilder( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + LINE_SEPARATOR );
		sitemap.append( "<urlset xmlns=\"" + SITEMAP_NAMESPACE + "\">" + LINE_SEPARATOR );

		// Home page
		sitemap.append( _getSitemapEntry( hostName, "/", null, "hourly", "0.9" ) );

		// Event list page
		sitemap.append( _getSitemapEntry( hostName, "/events", null, "weekly", null ) );

		// Category pages
		for( String categoryName : _getCategoryNameList( language ) )
			sitemap.append( _getSitemapEntry( hostName, "/" + categoryName, null, "hourly", "0.9" ) );

		// Static pages
		for( String staticPage : _getStaticPageList( language ) )
			sitemap.append( _getSitemapEntry( hostName, "/" + staticPage.replace( "_", "/" ), null, "monthly", null ) );

		sitemap.append( "</urlset>" );

		return sitemap.toString();

	}

	
	private static List<String> _getCategoryNameList( Language language ) {

		File folder = new File( DataAccessor.class.getResource( "curated" ).getFile() );
		File[] listOfFiles = folder.listFiles();

		List<String> fileNameList = new ArrayList<>();
		String fileNamePrefix = "list." + language.getCode();
		for( int i = 0; i < listOfFiles.length; i++ ) {

			File file = listOfFiles[i];
			if( ! file.isFile() )
				continue;

			if( ! file.getName().startsWith( fileNamePrefix ) )
				continue;

			fileNameList.add( file.getName().substring( fileNamePrefix.length() + 1 ) );

		}

		return fileNameList;
	}
	
	private static List<String> _getStaticPageList( Language language ) {

		File folder = new File( PratilipiSite.class.getResource( PratilipiSite.dataFilePrefix ).getFile() );
		File[] listOfFiles = folder.listFiles();

		String fileNamePrefix = "static." + language.getCode();
		String fileNameEnPrefix = "static." + Language.ENGLISH.getCode();

		Set<String> fileNames = new HashSet<>();
		for( int i = 0; i < listOfFiles.length; i++ ) {
			File file = listOfFiles[i];
			if( ! file.isFile() )
				continue;
			if( file.getName().startsWith( fileNamePrefix ) || file.getName().startsWith( fileNameEnPrefix ) )
				fileNames.add( file.getName().substring( file.getName().lastIndexOf( "." ) + 1 ) );
		}

		return new ArrayList<String>( fileNames );

	}

	private static String _getSitemapEntry( String hostName, String uri, Date lastUpdated, String changeFrequency, String priority ) {
/*		<url>
			<loc>http://www.example.com/</loc>
			<lastmod>2005-01-01</lastmod>
			<changefreq>monthly</changefreq>
			<priority>0.8</priority>
		</url>
*/
		String loc = "http://" + hostName + uri;
		StringBuilder sitemapUrl = new StringBuilder( "<url>" + LINE_SEPARATOR );
		sitemapUrl.append( "<loc>" ).append( _getEntityEscapedUrl( loc ) ).append( "</loc>" ).append( LINE_SEPARATOR );
		if( lastUpdated != null ) {
			DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
			dateFormat.setTimeZone( TimeZone.getTimeZone( "Asia/Kolkata" ) );
			sitemapUrl.append( "<lastmod>" ).append( dateFormat.format( lastUpdated ) ).append( "</lastmod>" ).append( LINE_SEPARATOR );
		}
		if( changeFrequency != null )
			sitemapUrl.append( "<changefreq>" ).append( changeFrequency ).append( "</changefreq>" ).append( LINE_SEPARATOR );
		if( priority != null )
			sitemapUrl.append( "<priority>" ).append( priority ).append( "</priority>" ).append( LINE_SEPARATOR );

		sitemapUrl.append( "</url>" ).append( LINE_SEPARATOR );

		return sitemapUrl.toString();

	}

	private static String _getEntityEscapedUrl( String url ) {
		return 	url.replace( "&", "&amp;" )
					.replace( "'", "&apos;" )
					.replace( "\"", "&quot;" )
					.replace( ">", "&gt;" )
					.replace( "<", "&lt;" );
	}

}
