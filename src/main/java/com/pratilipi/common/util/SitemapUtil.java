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


public class SitemapUtil {

	private static final String LINE_SEPARATOR = "\n";
	private static final Long SITEMAP_PAGE_COUNT = 1000000000000L;
	private static final String SITEMAP_NAMESPACE = "http://www.sitemaps.org/schemas/sitemap/0.9";

	private static String _getEntityEscapedUrl( String url ) {
		return 	url.replace( "&", "&amp;" )
					.replace( "'", "&apos;" )
					.replace( "\"", "&quot;" )
					.replace( ">", "&gt;" )
					.replace( "<", "&lt;" );
	}

	private static class SitemapUrl {

		//	<url>
		//		<loc>http://www.example.com/</loc>
		//		<lastmod>2005-01-01</lastmod>
		//		<changefreq>monthly</changefreq>
		//		<priority>0.8</priority>
		//	</url>

		private String location;

		private String lastModified;

		private String changeFrequency;

		private String priority;

		public SitemapUrl( Page page, Date lastMod, Website website ) {

			this.location = _getEntityEscapedUrl( 
					"http://" + website.getHostName() + 
					( page.getUriAlias() != null ? page.getUriAlias() : page.getUri() ) );

			if( lastMod != null ) {
				DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
				dateFormat.setTimeZone( TimeZone.getTimeZone( "Asia/Kolkata" ) );
				this.lastModified = dateFormat.format( lastMod );
			}

			// Change Frequency Values: always, hourly, daily, weekly, monthly, yearly, never
			// priority: ranges from 0-1, Default - 0.5

			switch ( page.getType() ) {
				case AUTHOR:
					this.changeFrequency = "daily";
					this.priority = "0.6";
					break;
				case BLOG:
					this.changeFrequency = "weekly";
					break;
				case BLOG_POST:
					this.changeFrequency = "weekly";
					this.priority = "0.6";
					break;
				case CATEGORY_LIST:
					this.changeFrequency = "hourly";
					this.priority = "0.8";
					break;
				case EVENT_LIST:
					this.changeFrequency = "weekly";
					break;
				case EVENT:
					this.changeFrequency = "weekly";
					this.priority = "0.6";
					break;
				case HOME:
					this.changeFrequency = "hourly";
					this.priority = "0.8";
					break;
				case PRATILIPI:
					this.changeFrequency = "daily";
					this.priority = "0.7";
					break;
				case READ:
					this.changeFrequency = "daily";
					this.priority = "0.7";
					break;
				case STATIC: 
					this.changeFrequency = "monthly";
					break;
				default:
					break;
			}

		}

		public String getLoc() {
			return location;
		}

		public String getLastMod() {
			return lastModified;
		}

		public String getChangeFreq() {
			return changeFrequency;
		}

		public String getPriority() {
			return priority;
		}

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


	private static List<SitemapUrl> _getSitemapForTypePage( Long startAt, Website website ) {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		List<PageEntity> pageEntityList = ObjectifyService.ofy().load()
				.type( PageEntity.class )
				.filterKey( ">=", Key.create( PageEntity.class, startAt ) )
				.filterKey( "<", Key.create( PageEntity.class, startAt + SITEMAP_PAGE_COUNT ) )
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

		List<SitemapUrl> sitemapUrlList = new ArrayList<>();

		for( Long pratilipiId : pratilipiIdList ) {

			Pratilipi pratilipi = pratilipis.get( pratilipiId );
			if( website.getFilterLanguage() != pratilipi.getLanguage() )
				continue;
			if( pratilipi.getState() != PratilipiState.PUBLISHED )
				continue;

			Page pratilipiPage = pages.get( pratilipiId );
			Page readerPage = dataAccessor.newPage();
			readerPage.setPrimaryContentId( pratilipiId );
			readerPage.setType( PageType.READ );
			readerPage.setUri( "/read?id=" + pratilipiId );

			sitemapUrlList.add( new SitemapUrl( pratilipiPage, pratilipi.getLastUpdated(), website ) );
			sitemapUrlList.add( new SitemapUrl( readerPage, pratilipi.getLastUpdated(), website ) );

		}

		for( Long authorId : authorIdList ) {

			Author author = authors.get( authorId );
			if( website.getFilterLanguage() != author.getLanguage() )
				continue;
			if( author.getState() != AuthorState.ACTIVE )
				continue;

			Page page = pages.get( authorId );
			sitemapUrlList.add( new SitemapUrl( page, author.getLastUpdated(), website ) );

		}

		for( Long blogId : blogIdList ) {
			Page page = pages.get( blogId );
			sitemapUrlList.add( new SitemapUrl( page, null, website ) );
		}

		for( Long blogPostId : blogPostIdList ) {

			BlogPost blogPost = blogPosts.get( blogPostId );
			if( website.getFilterLanguage() != blogPost.getLanguage() )
				continue;
			if( blogPost.getState() != BlogPostState.PUBLISHED )
				continue;

			Page page = pages.get( blogPostId );
			sitemapUrlList.add( new SitemapUrl( page, blogPost.getLastUpdated(), website ) );

		}

		for( Long eventId : eventIdList ) {

			Event event = events.get( eventId );
			if( website.getFilterLanguage() != event.getLanguage() )
				continue;

			Page page = pages.get( eventId );
			sitemapUrlList.add( new SitemapUrl( page, event.getLastUpdated(), website ) );

		}

		return sitemapUrlList;

	}

	private static List<SitemapUrl> _getUrlSetForTypeCategoryList( Website website ) {

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
			sitemapUrlList.add( new SitemapUrl( page, null, website ) );
		}

		return sitemapUrlList;

	}

	private static List<SitemapUrl> _getUrlSetForTypeStatic( Website website ) {

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
			sitemapUrlList.add( new SitemapUrl( page, null, website ) );
		}

		return sitemapUrlList;

	}

	private static List<SitemapUrl> _getSitemapForTypeOther( Website website ) {

		List<SitemapUrl> sitemapUrlList = new ArrayList<>();
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		// Home Page
		Page homePage = dataAccessor.newPage();
		homePage.setType( PageType.HOME );
		homePage.setUri( "/" );
		sitemapUrlList.add( new SitemapUrl( homePage, null, website ) );

		// /events page
		Page eventListPage = dataAccessor.newPage();
		eventListPage.setType( PageType.EVENT_LIST );
		eventListPage.setUri( "/events" );
		sitemapUrlList.add( new SitemapUrl( eventListPage, null, website ) );

		// Category List pages
		sitemapUrlList.addAll( _getUrlSetForTypeCategoryList( website ) );

		// Static pages
		sitemapUrlList.addAll( _getUrlSetForTypeStatic( website ) );

		return sitemapUrlList;

	}

	private static String _getSitemapSnippet( Website website, String type, String cursor, Date lastMod ) {	

//		<sitemap>
//			<loc>http://www.example.com/sitemap1.xml.gz</loc>
//			<lastmod>2004-10-01</lastmod>
//		</sitemap>

		StringBuilder sitemap = new StringBuilder( "<sitemap>" + LINE_SEPARATOR );

		String loc = "http://" + website.getHostName() + "/sitemap";
		if( type != null ) {
			loc = loc + "?" + RequestParameter.SITEMAP_TYPE.getName() + "=" + type;
			if( cursor != null )
				loc = loc + "&" + RequestParameter.SITEMAP_CURSOR.getName() + "=" + cursor;
		}

		sitemap.append( "<loc>" ).append( _getEntityEscapedUrl( loc ) ).append( "</loc>" ).append( LINE_SEPARATOR );

		if( lastMod != null ) {
			DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
			dateFormat.setTimeZone( TimeZone.getTimeZone( "Asia/Kolkata" ) );
			sitemap.append( "<lastmod>" ).append( dateFormat.format( lastMod ) ).append( "</lastmod>" ).append( LINE_SEPARATOR );
		}

		sitemap.append( "</sitemap>" ).append( LINE_SEPARATOR );

		return sitemap.toString();

	}

	private static String _getSitemapIndex( Website website ) {

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

		StringBuilder sitemapIndex = new StringBuilder( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + LINE_SEPARATOR );
		sitemapIndex.append( "<sitemapindex xmlns=\"" + SITEMAP_NAMESPACE + "\">" + LINE_SEPARATOR );

		// Adding sitemap to index pages without PAGE entites
		sitemapIndex.append( _getSitemapSnippet( website, "other", null, null ) );

		for( Long i = startIndex; i <= endIndex; i += SITEMAP_PAGE_COUNT )
			sitemapIndex.append( _getSitemapSnippet( website, "page", i + "", null ) );

		sitemapIndex.append( "</sitemapindex>" );
		return sitemapIndex.toString();

	}


	public static String getSitemap( String type, String cursor, Website website ) {

		if( type == null ) // Sitemap Index file
			return _getSitemapIndex( website );

		if( type.equals( "other" ) )
			return _getSitemapString( _getSitemapForTypeOther( website ) );

		if( type.equals( "page" ) )
			return _getSitemapString( _getSitemapForTypePage( Long.parseLong( cursor ), website ) );

		return null;

	}

}
