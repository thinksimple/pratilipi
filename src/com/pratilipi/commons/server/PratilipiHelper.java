package com.pratilipi.commons.server;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.ClaymusHelper;
import com.pratilipi.commons.shared.PratilipiType;

public class PratilipiHelper extends ClaymusHelper {

	public static final Pattern REGEX_PAGE_BREAK = Pattern.compile(
			"(<hr\\s+style=\"page-break-(before|after).+?>)"
			+ "|"
			+ "(<div\\s+style=\"page-break-(before|after).+?>(.+?)</div>)" );

	public static final Pattern REGEX_HTML_BODY = Pattern.compile(
			"(.*?<body\\s.*?>)(.*?)(</body>.*?)" );

	
	private static final String URL_RESOURCE = ClaymusHelper.URL_RESOURCE;
	private static final String URL_RESOURCE_STATIC = ClaymusHelper.URL_RESOURCE_STATIC;

	
	public static final String URL_AUTHOR_PAGE = "/author/";
	public static final String URL_AUTHOR_IMAGE = "/resource.author-image/original/";

	public static final String URL_LANGUAGE_PAGE = "/language/";

	public static final String URL_GENRE_PAGE = "/genre/";


	public PratilipiHelper( HttpServletRequest request ) {
		super( request );
	}
	

	public static String getPageUrl(
			PratilipiType pratilipiType, Long pratilipiId ) {
		
		return "/" + pratilipiType.getName().toLowerCase() + "/" +
				( pratilipiId == null ? "" : pratilipiId );
	}
	
	public static String getReaderPageUrl(
			PratilipiType pratilipiType, Long pratilipiId ) {
		
		return "/read/" + pratilipiType.getName().toLowerCase() + "/" +
				( pratilipiId == null ? "" : pratilipiId );
	}
	
	
	public static String getCoverImage(
			PratilipiType pratilipiType, Long pratilipiId ) {
		
		return pratilipiType.getName().toLowerCase() + "-cover/original/" +
				( pratilipiId == null ? "" : pratilipiId );
	}
	
	public static String getCoverImage300(
			PratilipiType pratilipiType, Long pratilipiId ) {
		
		return pratilipiType.getName().toLowerCase() + "-cover/300/" +
				( pratilipiId == null ? "" : pratilipiId );
	}
	

	public static String getCoverImageUrl(
			PratilipiType pratilipiType, Long pratilipiId ) {
		
		return getCoverImageUrl( pratilipiType, pratilipiId, true );
	}
	
	public static String getCoverImageUrl(
			PratilipiType pratilipiType, Long pratilipiId, boolean dynamic ) {
		
		return ( dynamic ? URL_RESOURCE : URL_RESOURCE_STATIC ) +
				getCoverImage( pratilipiType, pratilipiId );
	}
	
	public static String getCoverImage300Url(
			PratilipiType pratilipiType, Long pratilipiId ) {
		
		return getCoverImage300Url( pratilipiType, pratilipiId, true );
	}
	
	public static String getCoverImage300Url(
			PratilipiType pratilipiType, Long pratilipiId, boolean dynamic ) {
		
		return ( dynamic ? URL_RESOURCE : URL_RESOURCE_STATIC ) +
				getCoverImage300( pratilipiType, pratilipiId );
	}

	
	public static String getContent(
			PratilipiType pratilipiType, Long pratilipiId ) {
		
		return pratilipiType.getName().toLowerCase() + "-content/pratilipi/" +
				( pratilipiId == null ? "" : pratilipiId );
	}
	
	public static String getContentHtml(
			PratilipiType pratilipiType, Long pratilipiId ) {
		
		return pratilipiType.getName().toLowerCase() + "-content/html/" +
				( pratilipiId == null ? "" : pratilipiId );
	}
	
	public static String getContentWord(
			PratilipiType pratilipiType, Long pratilipiId ) {
		
		return pratilipiType.getName().toLowerCase() + "-content/word/" +
				( pratilipiId == null ? "" : pratilipiId );
	}
	
	public static String getContentImage(
			PratilipiType pratilipiType, Long pratilipiId ) {
		
		return pratilipiType.getName().toLowerCase() + "-content/image/" +
				( pratilipiId == null ? "" : pratilipiId );
	}


	public static String getContentUrl(
			PratilipiType pratilipiType, Long pratilipiId ) {
		
		return getContentUrl( pratilipiType, pratilipiId, true );
	}
	
	public static String getContentUrl(
			PratilipiType pratilipiType, Long pratilipiId, boolean dynamic ) {
		
		return ( dynamic ? URL_RESOURCE : URL_RESOURCE_STATIC ) +
				getContent( pratilipiType, pratilipiId );
	}
	
	public static String getContentHtmlUrl(
			PratilipiType pratilipiType, Long pratilipiId ) {
		
		return getContentHtmlUrl( pratilipiType, pratilipiId, true );
	}
	
	public static String getContentHtmlUrl(
			PratilipiType pratilipiType, Long pratilipiId, boolean dynamic ) {
		
		return ( dynamic ? URL_RESOURCE : URL_RESOURCE_STATIC ) +
				getContentHtml( pratilipiType, pratilipiId );
	}
	
	public static String getContentWordUrl(
			PratilipiType pratilipiType, Long pratilipiId ) {
		
		return getContentWordUrl( pratilipiType, pratilipiId, true );
	}
	
	public static String getContentWordUrl(
			PratilipiType pratilipiType, Long pratilipiId, boolean dynamic ) {
		
		return ( dynamic ? URL_RESOURCE : URL_RESOURCE_STATIC ) +
				getContentWord( pratilipiType, pratilipiId );
	}
	
	public static String getContentImageUrl(
			PratilipiType pratilipiType, Long pratilipiId ) {
		
		return getContentImageUrl( pratilipiType, pratilipiId, true );
	}

	public static String getContentImageUrl(
			PratilipiType pratilipiType, Long pratilipiId, boolean dynamic ) {
		
		return ( dynamic ? URL_RESOURCE : URL_RESOURCE_STATIC ) +
				getContentImage( pratilipiType, pratilipiId );
	}

	
	public static String getAuthorPageUrl( Long authorId ) {
		return "/author/" + authorId;
	}
	
}
