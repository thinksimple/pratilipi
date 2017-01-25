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
import com.pratilipi.data.DataAccessorGaeImpl;
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

		throw new InvalidArgumentException( (JsonObject) null ); // TODO

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
			pages.put( page.getPrimaryContentId(), page ); // FIX: Global uniqueness of primaryContentId is not yet gauranteed
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
			sitemap.append( _getSitemapEntry( hostName, pages.get( pratilipiId ), pratilipi.getLastUpdated(), null, null ) ); // TODO
			sitemap.append( _getSitemapEntry( hostName, "/read?pratilipiId=" + pratilipiId, pratilipi.getLastUpdated(), null, null ) ); // TODO
		}

		for( Long authorId : authorIdList ) {
			Author author = authors.get( authorId );
			if( author.getLanguage() != language )
				continue;
			if( author.getState() != AuthorState.ACTIVE )
				continue;
			sitemap.append( _getSitemapEntry( hostName, pages.get( authorId ), author.getLastUpdated(), null, null ) ); // TODO
		}

		for( Long blogId : blogIdList )
			sitemap.append( _getSitemapEntry( hostName, pages.get( blogId ), null, null, null ) ); // TODO

		for( Long blogPostId : blogPostIdList ) {
			BlogPost blogPost = blogPosts.get( blogPostId );
			if( blogPost.getLanguage() != language )
				continue;
			if( blogPost.getState() != BlogPostState.PUBLISHED )
				continue;
			sitemap.append( _getSitemapEntry( hostName, pages.get( blogPostId ), blogPost.getLastUpdated(), null, null ) ); // TODO
		}

		for( Long eventId : eventIdList ) {
			Event event = events.get( eventId );
			if( event.getLanguage() != language )
				continue;
			sitemap.append( _getSitemapEntry( hostName, pages.get( eventId ), event.getLastUpdated(), null, null ) ); // TODO
		}

		return sitemap.toString();

	}

	private static String _getSitemapForTypeOther( String hostName, Language language ) {

		StringBuilder sitemap = new StringBuilder( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + LINE_SEPARATOR );
		sitemap.append( "<urlset xmlns=\"" + SITEMAP_NAMESPACE + "\">" + LINE_SEPARATOR );

		// Home page
		sitemap.append( _getSitemapEntry( hostName, "/", null, "hourly", "0.9" ) ); // TODO

		// Event list page
		sitemap.append( _getSitemapEntry( hostName, "/events", null, "weekly", null ) ); // TODO

		// Category pages
		for( String categoryName : _getCategoryNameList( language ) )
			sitemap.append( _getSitemapEntry( hostName, "/" + categoryName, null, null, null ) ); // TODO

		// Static pages
		for( String categoryName : _getStaticPageList( language ) )
			sitemap.append( _getSitemapEntry( hostName, "/" + categoryName, null, null, null ) ); // TODO
		
		sitemap.append( "</urlset>" );

		return sitemap.toString();

	}

	
	private static List<String> _getCategoryNameList( Language language ) {
		return null; // TODO
	}
	
	private static List<String> _getStaticPageList( Language language ) {
		return null; // TODO
	}
	
	
	private static String _getSitemapEntry( String hostName, Page page, Date lastUpdated, String changeFrequency, String priority ) {
		return _getSitemapEntry(
				hostName,
				page.getUriAlias() == null ? page.getUri() : page.getUriAlias(),
				lastUpdated,
				changeFrequency,
				priority );
	}
	
	private static String _getSitemapEntry( String hostName, String uri, Date lastUpdated, String changeFrequency, String priority ) {
		return null; // TODO
	}
	
	
	
	
	
	
	
	
	
	
	
	private static String _getEntityEscapedUrl( String url ) {
		return 	url.replace( "&", "&amp;" )
					.replace( "'", "&apos;" )
					.replace( "\"", "&quot;" )
					.replace( ">", "&gt;" )
					.replace( "<", "&lt;" );
	}
	
	private static String _getSitemapString( List<SitemapUrl> sitemapUrlList ) {

		//	<?xml version="1.0" encoding="UTF-8"?>
		//	<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
		//		<url>
		//			<loc>http://www.example.com/</loc>
		//			<lastmod>2005-01-01</lastmod>
		//			<changefreq>monthly</changefreq>
		//			<priority>0.8</priority>
		//		</url>
		//	</urlset>

		StringBuilder sitemap = new StringBuilder( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + LINE_SEPARATOR );
		sitemap.append( "<urlset xmlns=\"" + SITEMAP_NAMESPACE + "\">" + LINE_SEPARATOR );

		for( SitemapUrl sitemapUrl : sitemapUrlList ) {

			StringBuilder url = new StringBuilder( "<url>" + LINE_SEPARATOR );
			url.append( "<loc>" ).append( sitemapUrl.getLoc() ).append( "</loc>" ).append( LINE_SEPARATOR );
			if( sitemapUrl.getLastMod() != null )
				url.append( "<lastmod>" ).append( sitemapUrl.getLastMod() ).append( "</lastmod>" ).append( LINE_SEPARATOR );
			if( sitemapUrl.getChangeFreq() != null )
				url.append( "<changefreq>" ).append( sitemapUrl.getChangeFreq() ).append( "</changefreq>" ).append( LINE_SEPARATOR );
			if( sitemapUrl.getPriority() != null )
				url.append( "<priority>" ).append( sitemapUrl.getPriority() ).append( "</priority>" ).append( LINE_SEPARATOR );
			url.append( "</url>" ).append( LINE_SEPARATOR );

			sitemap.append( url );

		}

		sitemap.append( "</urlset>" );

		return sitemap.toString();

	}

	private static List<SitemapUrl> _getUrlSetForTypeCategoryList( Website website, boolean basicMode ) {

		List<SitemapUrl> sitemapUrlList = new ArrayList<>();

		File folder = new File( DataAccessor.class.getResource( DataAccessorGaeImpl.CURATED_DATA_FOLDER ).getFile() );
		File[] listOfFiles = folder.listFiles();

		String fileNamePrefix = "list." + website.getFilterLanguage().getCode();
		for( int i = 0; i < listOfFiles.length; i++ ) {

			File file = listOfFiles[i];
			if( ! file.isFile() )
				continue;

			if( ! file.getName().startsWith( fileNamePrefix ) )
				continue;

			Page page = DataAccessorFactory.getDataAccessor().newPage();
			page.setType( PageType.CATEGORY_LIST );
			page.setUri( "/" + file.getName().substring( fileNamePrefix.length() + 1 ) );
			sitemapUrlList.add( new SitemapUrl( page, null, website, basicMode ) );
		}

		return sitemapUrlList;

	}

	private static List<SitemapUrl> _getUrlSetForTypeStatic( Website website, boolean basicMode ) {

		List<SitemapUrl> sitemapUrlList = new ArrayList<>();

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

		for( String fileName : fileNames ) {
			Page page = DataAccessorFactory.getDataAccessor().newPage();
			page.setType( PageType.STATIC );
			page.setUri( "/" + fileName.replace( "_", "/" ) );
			sitemapUrlList.add( new SitemapUrl( page, null, website, basicMode ) );
		}

		return sitemapUrlList;

	}

}
